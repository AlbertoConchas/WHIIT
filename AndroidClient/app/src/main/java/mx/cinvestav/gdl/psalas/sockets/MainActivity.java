package mx.cinvestav.gdl.psalas.sockets;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    public TextView textView;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        textView = (TextView)findViewById(R.id.textView2);
        new AsyncClient().execute();
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
        }

        return super.onOptionsItemSelected(item);
    }

    class AsyncClient extends AsyncTask<Void, Void, List<String> > {

        //private final String SERVERIP = "10.0.5.192";
        private final String SERVERIP = "192.168.1.77";
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
            if(result!=null){
                MainActivity.this.textView.setTextColor(Color.GREEN);
                MainActivity.this.textView.setText("Conectado!");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, result);
                MainActivity.this.listView.setAdapter(adapter);
            }
            else{
                MainActivity.this.textView.setTextColor(color);
                MainActivity.this.textView.setText(response);
            }
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            InetAddress inetAddress = null;
            List<String> list =  null;
            try {
                inetAddress = InetAddress.getByName(SERVERIP);
                socket = new Socket(inetAddress,SERVERPORT);
                System.out.println("Recibiendo lista");
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object o = inputStream.readObject();
                System.out.println("o = " + o);
                list = (List<String>) o;
                if(list.size()==0){
                    list.add("No hay clientes conectados");
                }
                System.out.println("Enviando nombre de cliente");
                if(!list.contains(MainActivity.this.getAccountName())) {
                    System.out.println("Envio nombre por ke no existe");
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(MainActivity.this.getAccountName());
                }
            } catch (UnknownHostException e) {
                color = Color.RED;
                response+= "Host Desconocido";
            } catch (IOException e) {
                color = Color.RED;
                response+= "Error de Conexion";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        color = Color.RED;
                        response+= "Error al cerrar la conexion";
                    }
                }
            }
            return list;
        }
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
}
