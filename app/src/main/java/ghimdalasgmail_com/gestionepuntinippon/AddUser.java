package ghimdalasgmail_com.gestionepuntinippon;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class AddUser extends AppCompatActivity {
    private User _utente;

    private ArrayList<User> utenti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        _utente = new User();
        _utente.setId(UUID.randomUUID().toString());
        Intent intent = getIntent();
        utenti = (ArrayList<User>) intent.getSerializableExtra("Utenti");
    }
    public void onAggiungiClick(View view) {
        EditText nome = (EditText)this.findViewById(R.id.add_nome);
        if(nome.getText().toString().equals("")) {
            this.mostraDialog("Errore", "Attenzione!: Il campo nome è vuoto!");
            return;
        }
        EditText cognome = (EditText)this.findViewById(R.id.add_cognome);
        if(cognome.getText().toString().equals("")){
            this.mostraDialog("Errore", "Attenzione!: Il campo cognome è vuoto!");
            return;
        }
        EditText mail = (EditText)this.findViewById(R.id.add_mail);
        if(mail.getText().toString().equals("")){
            this.mostraDialog("Errore", "Attenzione!: Il campo mail è vuoto!");
            return;
        }
        EditText cell = (EditText)this.findViewById(R.id.add_cell);
        EditText numero_tessera = (EditText)this.findViewById(R.id.add_numero_tessera);
        EditText indirizzo = (EditText)this.findViewById(R.id.add_indirizzo);
        EditText nascita = (EditText)this.findViewById(R.id.add_data_nascita);
        EditText cap = (EditText)this.findViewById(R.id.add_cap);
        EditText comune = (EditText)this.findViewById(R.id.add_comune);
        EditText provincia = (EditText)this.findViewById(R.id.add_provincia);

        User utente = new User();
        utente.setName(nome.getText().toString());
        utente.setCognome(cognome.getText().toString());
        utente.setMail(mail.getText().toString());
        utente.setCell(cell.getText().toString());
        utente.setNumero_tessera(numero_tessera.getText().toString());
        utente.setIndirizzo(indirizzo.getText().toString());
        utente.setNascita(nascita.getText().toString());
        utente.setCap(cap.getText().toString());
        utente.setComune(comune.getText().toString());
        utente.setProvincia(provincia.getText().toString());
        Calendar app = Calendar.getInstance();
        utente.setScadenza(String.valueOf(app.get(Calendar.YEAR)) + "-12-31");
        CheckBox tipo = (CheckBox) this.findViewById(R.id.add_tipo);
        if(tipo.isChecked())
            utente.setTipologia(Tipo.Gratuito);
        else
            utente.setTipologia(Tipo.Ordinario);
        utente.setId(UUID.randomUUID().toString());
        utente.setPunti("0");
        utente.setRegistrazione(String.valueOf(app.get(Calendar.YEAR)) + "-" + String.valueOf(app.get(Calendar.MONTH)) + "-" + String.valueOf(app.get(Calendar.DAY_OF_MONTH)));
        AsyncHttpClient client = new AsyncHttpClient();
        String get = "http://gestpunti.altervista.org/?operation=add&" + utente.getAdd();
        client.get(get, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Quando ha letto con successo il json di tutti gli utenti
                String value = new String(responseBody);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Gestisce eventuali errori tipo connessione fallita
                System.out.print(error.getMessage());
                error.printStackTrace();
            }
        });

        /*EditText scadenza = (EditText)this.findViewById(R.id.add_scadenza);
        EditText tipologia = (EditText)this.findViewById(R.id.add_tipo);
        if((nome.getText().toString() == "") ||
                (cognome.getText().toString() == "") ||
                (mail.getText().toString() == "")){
            //Segnala errore
        }
        else if(this.ContieneUtente(nome.getText().toString(), cognome.getText().toString())){
            //avvisare che c'è un ononimo
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Avviso");
            dialog.setMessage("Attenzione!: \n"+ nome.getText().toString() + " " + cognome.getText().toString() + "\n è gia presente!\nContinuare?");
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //Ha cliccato ok
                            dialog.dismiss();
                        }
                    });
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //ha cliccato cancel
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }
        else{
            // tutto regolare, aggiunge l'utente
            this.finish();
        }*/
    }
    private boolean ContieneUtente(String nome, String cognome){
        for(User u : utenti)
            if((u.getName().equals(nome))&&(u.getCognome().equals(cognome)))
                return true;
        return false;
    }
    private void mostraDialog(String title, String messaggio){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(title);
        dialog.setMessage(messaggio);
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Ha cliccato ok
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
}
