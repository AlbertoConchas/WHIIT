package mx.cinvestav.gdl.psalas.sockets;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private final String SERVERIP = "10.0.5.192";
    private final int SERVERPORT = 5000;
    private Socket socket;
    private ArrayList<String> list;
    private ListView listView;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        t = new Thread(new ClientThread());
        t.start();
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
            if(socket!=null) {
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    Object o = inputStream.read();
                    list = (ArrayList<String>) o;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(adapter);
                } catch (IOException e) {
                    Toast.makeText(this, "No InputStream", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    class ClientThread implements Runnable{

        @Override
        public void run() {
            try {
                InetAddress inetAddress = InetAddress.getByName(SERVERIP);
                socket = new Socket(inetAddress,SERVERPORT);
            } catch (UnknownHostException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
