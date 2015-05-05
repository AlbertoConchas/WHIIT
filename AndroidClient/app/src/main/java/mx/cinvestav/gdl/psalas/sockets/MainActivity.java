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
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private TextView textView;
    private ArrayList<String> list;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView2);
        try {
            new Thread(new SenderSocketClient()).start();
            new Thread(new RecieverSocketServer()).start();
            new Thread(new ReceiverSocketClient()).start();
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
                    System.out.println("Se envio un paquete por el puerto 5000");
                    Thread.sleep(3000);
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

    class ReceiverSocketClient implements Runnable{

        private MulticastSocket socket;
        private InetAddress group;

        public ReceiverSocketClient() throws IOException {
            socket = new MulticastSocket(6000);
            group = InetAddress.getByName("228.5.6.7");
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
                    System.out.println("Espera a recibir un packete por el puerto 6000");
                    socket.receive(packet);
                    Object obj = Serialize.deserialize(packet.getData());
                    if(obj instanceof LastLocation){
                        if(!list.contains(obj)){
                            list.add(obj.toString());
                            ArrayAdapter<String>adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,list);
                            MainActivity.this.listView.setAdapter(adapter);
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
    }

    class RecieverSocketServer implements Runnable {

        private MulticastSocket socket;
        private InetAddress group;
        private LastLocation me;

        RecieverSocketServer() throws IOException {
            socket = new MulticastSocket(5000);
            group = InetAddress.getByName("228.5.6.7");
            me = new LastLocation(getLocalAddress(),new Date(),getLocation());
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
                    System.out.println("Esperando a recibir del puerto 5000");
                    socket.receive(packet);
                    Object obj = Serialize.deserialize(packet.getData());

                    if(obj instanceof Update){
                        Update update = (Update) obj;
                        if(!update.getName().equals(me.getName())){
                            System.out.println("Update recibido por: " + packet.getAddress());
                            me.setUpdate(new Date());
                            byte[] message = Serialize.serialize(me);
                            new Thread(new SenderSocketServer(message,packet.getAddress())).start();
                            System.out.println("aki debo regresar el mensaje");
                        }
                    }
                    else System.out.println(obj);

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

    private String getLocation() {
        return "Cubo38";
    }

    class SenderSocketServer implements Runnable{

        private DatagramSocket socket;
        private InetAddress address;
        private byte[] msg;

        public SenderSocketServer(byte[] message, InetAddress address) throws SocketException, UnknownHostException {
            socket = new DatagramSocket();
            this.address = address;
            msg = message;
        }

        @Override
        public void run() {
            try {
                DatagramPacket packet = new DatagramPacket(msg,msg.length,address,6000);
                System.out.println("Enviando mensaje por el puerto 6000 a la direccion: "+address);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (socket != null){ socket.close();
                    System.out.println("Se cerro el socket");}
            }
        }
    }

}
