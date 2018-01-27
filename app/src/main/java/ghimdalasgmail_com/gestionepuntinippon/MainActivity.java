package ghimdalasgmail_com.gestionepuntinippon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> utenti;
    private ArrayList<User> utenti_origin;
    private AdapterUtente adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        EditText cerca = (EditText)findViewById(R.id.txt_cerca);
        cerca.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                adapter.getFilter().filter(value);

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent();
                intent.putExtra("Utenti", utenti);
                intent.setClass(context, AddUser.class);
                startActivity(intent);
            }
        });
        utenti = new ArrayList<>();
        utenti_origin = new ArrayList<>();
        adapter = new AdapterUtente(this, R.layout.adapter_users, utenti);
        ListView lista_utenti = (ListView) findViewById(R.id.lista_utenti);
        adapter.setSelecteUserListener(new SelecteUserListener() {
            @Override
            public void SelecteUserListener(User utente) {
                Intent intent = new Intent();
                intent.putExtra("Utente", utente);
                intent.setClass(context, EditUtenteActivity.class);
                startActivity(intent);
            }
        });
        lista_utenti.setAdapter(adapter);
        Filter filtro = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                ArrayList<User> FilteredUsers = new ArrayList<>();
                charSequence = charSequence.toString().toLowerCase();
                for(int i = 0;i < utenti_origin.size();i++){
                    User user = utenti_origin.get(i);
                    if(user.contains(charSequence.toString())){
                        FilteredUsers.add(user);
                    }
                }
                results.count = FilteredUsers.size();
                results.values = FilteredUsers;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                utenti = (ArrayList<User>) filterResults.values;
                adapter.notifyDataSetChanged();
                adapter.clear();
                for(User u : utenti)
                    adapter.add(u);
                adapter.notifyDataSetChanged();
            }
        };
        adapter.setFilter(filtro);
        //this.AggiornaUtenti();
    }

    @Override
    protected void onResume(){
        this.AggiornaUtenti();
        super.onResume();
    }

    private void AggiornaUtenti(){
        utenti.clear();
        utenti_origin.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://gestpunti.altervista.org/?operation=list_all", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Quando ha letto con successo il json di tutti gli utenti
                String value = new String(responseBody);
                value = value.substring(value.indexOf("<body>") + "<body>".length());
                value = value.substring(0, value.indexOf("</body>"));
                JSONObject obj = null;
                try {
                    obj = new JSONObject(value);
                    Iterator<String> chiavi = obj.keys();
                    while(chiavi.hasNext()){
                        String key = chiavi.next();
                        utenti.add(new User(key, obj.getJSONObject(key)));
                        utenti_origin.add(new User(key, obj.getJSONObject(key)));
                    }
                } catch (JSONException e) {
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Gestisce eventuali errori tipo connessione fallita
                System.out.print(error.getMessage());
                error.printStackTrace();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCercaClick(View view){
        //filtro la listview
        EditText cerca = (EditText)findViewById(R.id.txt_cerca);
        adapter.getFilter().filter(cerca.getText());
    }
}
