package mx.cinvestav.gdl.psalas.sockets;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cinves.whitt.LastLocation;
import com.cinves.whitt.Update;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private TextView textView;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView2);
        //new AsyncClient().execute();
        try {
            new Thread(new SenderSocketClient()).start();
            new Thread(new RecieverSocketServer()).start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scan) {
            new AsyncClient().execute();
            return true;
        } else if (id == R.id.action_config) {

        }

        return super.onOptionsItemSelected(item);
    }

    private String getAccountName() {
        String accountName = null;

        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();
        for (Account account : list) {
            if (account.type.equalsIgnoreCase("com.google")) {
                accountName = account.name;
                break;
            }
        }
        return accountName;
    }

    class AsyncClient extends AsyncTask<Void, Void, List<String>> {

        private final String SERVERIP = "10.0.5.192";
        //private final String SERVERIP = "192.168.1.77";
        private final int SERVERPORT = 5000;
        private Socket socket;
        private String response = "";
        private int color;

        @Override
        protected void onPreExecute() {
            MainActivity.this.textView.setTextColor(Color.YELLOW);
            MainActivity.this.textView.setText("Conectando...");
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (result != null) {
                MainActivity.this.textView.setTextColor(Color.GREEN);
                MainActivity.this.textView.setText("Conectado!");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, result);
                MainActivity.this.listView.setAdapter(adapter);
            } else {
                MainActivity.this.textView.setTextColor(color);
                MainActivity.this.textView.setText(response);
            }
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            InetAddress inetAddress;
            List<String> list = null;
            try {
                inetAddress = InetAddress.getByName(SERVERIP);
                socket = new Socket(inetAddress, SERVERPORT);
                System.out.println("Recibiendo lista");
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object o = inputStream.readObject();
                System.out.println("o = " + o);
                list = (List<String>) o;
                if (list.size() == 0) {
                    list.add("No hay clientes conectados");
                }
                System.out.println("Enviando nombre de cliente");
                if (!list.contains(MainActivity.this.getAccountName())) {
                    System.out.println("Envio nombre por ke no existe");
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(MainActivity.this.getAccountName());
                }
            } catch (UnknownHostException e) {
                color = Color.RED;
                response += "Host Desconocido";
            } catch (IOException e) {
                color = Color.RED;
                response += "Error de Conexion";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        color = Color.RED;
                        response += "Error al cerrar la conexion";
                    }
                }
            }
            return list;
        }
    }

    class SenderSocketClient implements Runnable {

        private DatagramSocket socket;
        private InetAddress group;

        SenderSocketClient() throws SocketException, UnknownHostException {
            socket = new DatagramSocket();
            group = InetAddress.getByName("228.5.6.7");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    byte[] message = Serialize.serialize(new Update(3,getLocalAddress()));
                    DatagramPacket packet = new DatagramPacket(message,message.length,group,5000);
                    socket.send(packet);
                    Thread.sleep(30000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void finalize() throws Throwable {
            if (socket != null){ socket.close();
            System.out.println("Se cerro el socket");}
            super.finalize();
        }

        private String getLocalAddress() {
            String localAddress = "";
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                            localAddress += inetAddress.getHostAddress() + ",";
                        }

                    }
                }
            } catch (SocketException ex) {
                Log.e("LOG_TAG", ex.toString());
            }
            String[] cadenas = localAddress.split(",");
            if (cadenas.length == 1) {
                return cadenas[0];
            } else return "Sin ip";
        }

    }

    class RecieverSocketServer implements Runnable {

        private MulticastSocket socket;
        private InetAddress group;
        private LastLocation me;

        RecieverSocketServer() throws IOException {
            socket = new MulticastSocket(5000);
            group = InetAddress.getByName("228.5.6.7");
            me = new LastLocation(getLocalAddress(),new Date(),"home");
        }

        @Override
        public void run() {

            try {
                socket.joinGroup(group);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    byte[] bytes = new byte[1000];
                    DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
                    socket.receive(packet);
                    Object obj = Serialize.deserialize(packet.getData());

                    if(obj instanceof Update){
                        Update update = (Update) obj;
                        if(!update.getName().equals(me.getName())){
                            System.out.println("Update recibido por: "+ packet.getAddress());
                            me.setUpdate(new Date());
                            System.out.println("aki debo regresar el mensaje");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void finalize() throws Throwable {
            if (socket != null){ socket.close();
                System.out.println("Se cerro el socket");}
            super.finalize();
        }

        private String getLocalAddress() {
            String localAddress = "";
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                            localAddress += inetAddress.getHostAddress() + ",";
                        }

                    }
                }
            } catch (SocketException ex) {
                Log.e("LOG_TAG", ex.toString());
            }
            String[] cadenas = localAddress.split(",");
            if (cadenas.length == 1) {
                return cadenas[0];
            } else return "Sin ip";
        }

    }

}
