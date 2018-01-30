package ghimdalasgmail_com.gestionepuntinippon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class EditUtenteActivity extends AppCompatActivity {

    private String id = "";
    private User utente;

    private View localView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_utente);
        Intent intent = getIntent();
        utente = (User) intent.getSerializableExtra("Utente");
        id = utente.getId();
        EditText nome = (EditText) findViewById(R.id.nome);
        localView = nome;
        nome.setText(utente.getName());
        EditText cognome = (EditText) findViewById(R.id.cognome);
        cognome.setText(utente.getCognome());
        EditText mail = (EditText) findViewById(R.id.mail);
        mail.setText(utente.getMail());
        EditText cell = (EditText) findViewById(R.id.cell);
        cell.setText(utente.getCell());
        EditText scadenza = (EditText) findViewById(R.id.scadenza);
        scadenza.setText(utente.getScadenza());
        EditText numero_tessera = (EditText) findViewById(R.id.numero_tessera);
        numero_tessera.setText(utente.getNumero_tessera());
        EditText registrazione = (EditText) findViewById(R.id.registrazione);
        registrazione.setText(utente.getRegistrazione());
        EditText indirizzo = (EditText) findViewById(R.id.indirizzo);
        indirizzo.setText(utente.getIndirizzo());
        EditText nascita = (EditText) findViewById(R.id.nascita);
        nascita.setText(utente.getNascita());
        EditText cap = (EditText) findViewById(R.id.cap);
        cap.setText(utente.getCap());
        EditText comune = (EditText) findViewById(R.id.comune);
        comune.setText(utente.getComune());
        EditText provincia = (EditText) findViewById(R.id.provincia);
        provincia.setText(utente.getProvincia());
        //TextView punti = (TextView) findViewById(R.id.punti);
        //punti.setText(utente.getPunti());
        TextView numero = (TextView)this.findViewById(R.id.numero);
        numero.setText(utente.getPunti());
        TextView label_punti = (TextView)this.findViewById(R.id.label_punti);
        label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(utente.getPunti()) / 102) + " € di sconto)");
    }
    public void onPiuClick(View view) {
        TextView numero = (TextView)this.findViewById(R.id.numero);
        if(numero.getText() == "")
            numero.setText("1");
        else {
            numero.setText(String.valueOf(Integer.parseInt(numero.getText().toString()) + 1));
            TextView label_punti = (TextView)this.findViewById(R.id.label_punti);
            label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(numero.getText().toString()) / 102) + " € di sconto)");
        }
    }
    public void onMenoClick(View view) {
        TextView numero = (TextView)this.findViewById(R.id.numero);
        if((numero.getText() == "")||(numero.getText() == "1")||(numero.getText() == "0"))
            numero.setText("0");
        else {
            numero.setText(String.valueOf(Integer.parseInt(numero.getText().toString()) - 1));
            TextView label_punti = (TextView)this.findViewById(R.id.label_punti);
            label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(numero.getText().toString()) / 102) + " € di sconto)");
        }
    }
    public void onMenoManyClick(View view) {
        TextView numero = (TextView)this.findViewById(R.id.numero);
        this.mostraInputDialog("Modifica il totale", "Modifica il totale dei punti", numero.getText().toString());
    }
    public void onMenoEuroClick(View view) {
        TextView numero = (TextView)this.findViewById(R.id.numero);
        if(Integer.parseInt(numero.getText().toString())<120)return;
        if((numero.getText() == "")||(numero.getText() == "1")||(numero.getText() == "0"))
            numero.setText("0");
        else {
            numero.setText(String.valueOf(Integer.parseInt(numero.getText().toString()) - 102));
            TextView label_punti = (TextView)this.findViewById(R.id.label_punti);
            label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(numero.getText().toString()) / 102) + " € di sconto)");
        }
    }
    public void onAddSpesa(View view){
        EditText spesa = (EditText) findViewById(R.id.spesa);
        if(spesa.getText().toString().equals(""))return;
        //ogni 102 punti sono un label_punti per la retro conversione
        double punti = Double.parseDouble(spesa.getText().toString()) * 3;
        utente.setPunti(String.valueOf(Integer.parseInt(utente.getPunti()) + new Double(punti).intValue()));
        TextView numero = (TextView)this.findViewById(R.id.numero);
        numero.setText(utente.getPunti());
        TextView label_punti = (TextView)this.findViewById(R.id.label_punti);
        label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(numero.getText().toString()) / 102) + " € di sconto)");
        spesa.setText("");
    }
    public void onDeleteSpesa(View view){
        EditText spesa = (EditText) findViewById(R.id.spesa_rem);
        if(spesa.getText().toString().equals(""))return;
        //ogni 102 punti sono un label_punti per la retro conversione
        double punti = Double.parseDouble(spesa.getText().toString()) * 102;
        double puntiTot = Double.parseDouble(utente.getPunti() + ".0") / 102;
        if(new Double(punti).intValue() > Integer.parseInt(utente.getPunti()))
            this.mostraDialog("Errore!", "Il massimo sconto imponibile è : " + String.valueOf(puntiTot).substring(0, String.valueOf(puntiTot).indexOf(".") + 3));
        else {
            utente.setPunti(String.valueOf(Integer.parseInt(utente.getPunti()) - new Double(punti).intValue()));
            TextView numero = (TextView) this.findViewById(R.id.numero);
            numero.setText(utente.getPunti());
            TextView label_punti = (TextView) this.findViewById(R.id.label_punti);
            label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(numero.getText().toString()) / 102) + " € di sconto)");
            spesa.setText("");
        }
    }
    public void onSalvaClick(View view) {
        //Aggiorna i punti dell'utente che sta visualizzando
        EditText nome = (EditText) findViewById(R.id.nome);
        EditText cognome = (EditText) findViewById(R.id.cognome);
        EditText mail = (EditText) findViewById(R.id.mail);
        EditText cell = (EditText) findViewById(R.id.cell);
        EditText registrazione = (EditText) findViewById(R.id.registrazione);
        EditText indirizzo = (EditText) findViewById(R.id.indirizzo);
        EditText nascita = (EditText) findViewById(R.id.nascita);
        EditText cap = (EditText) findViewById(R.id.cap);
        EditText comune = (EditText) findViewById(R.id.comune);
        EditText provincia = (EditText) findViewById(R.id.provincia);
        EditText scadenza = (EditText) findViewById(R.id.scadenza);
        EditText numero_tessera = (EditText) findViewById(R.id.numero_tessera);
        TextView punti = (TextView) findViewById(R.id.numero);
        TextView numero = (TextView)this.findViewById(R.id.numero);
        if(!nome.getText().toString().equals(utente.getName()))
            this.updateField("nome", nome.getText().toString());
        if(!cognome.getText().toString().equals(utente.getCognome()))
            this.updateField("cognome", cognome.getText().toString());
        if(!mail.getText().toString().equals(utente.getMail()))
            this.updateField("mail", mail.getText().toString());
        if(!cell.getText().toString().equals(utente.getCell()))
            this.updateField("cell", cell.getText().toString());
        if(!registrazione.getText().toString().equals(utente.getRegistrazione()))
            this.updateField("registrazione", registrazione.getText().toString());
        if(!indirizzo.getText().toString().equals(utente.getIndirizzo()))
            this.updateField("indirizzo", indirizzo.getText().toString());
        if(!nascita.getText().toString().equals(utente.getNascita()))
            this.updateField("nascita", nascita.getText().toString());
        if(!cap.getText().toString().equals(utente.getCap()))
            this.updateField("cap", cap.getText().toString());
        if(!comune.getText().toString().equals(utente.getComune()))
            this.updateField("comune", comune.getText().toString());
        if(!provincia.getText().toString().equals(utente.getProvincia()))
            this.updateField("provincia", provincia.getText().toString());
        if(!scadenza.getText().toString().equals(utente.getScadenza()))
            this.updateField("scadenza", scadenza.getText().toString());
        this.updateField("punti", punti.getText().toString());
        if(!numero_tessera.getText().toString().equals(utente.getNumero_tessera()))
            this.updateField("numero_tessera", numero_tessera.getText().toString());
        this.finish();
    }
    public void onDeleteClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attenzione");
        builder.setMessage("Sei sicuro di voler cancellare definitivamente l'utente?");
        // add the buttons
        builder.setPositiveButton("Si",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://gestpunti.altervista.org/?operation=elimina&id="
                        + id, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String value = new String(responseBody);
                        //controllare se c'è scritto che l'aggiornamento ha aviuto successo
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //Gestisce eventuali errori tipo connessione fallita
                        Snackbar.make(localView, "Errore nell'update:", Snackbar.LENGTH_LONG)
                                .setAction(error.getMessage(), null).show();
                        System.out.print(error.getMessage());
                        error.printStackTrace();
                    }
                });
                finish();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("no",null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updateField(String field, String value){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://gestpunti.altervista.org/?operation=update&id="
                + id
                + "&campo="
                + field + "&value="
                + value, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String value = new String(responseBody);
                //controllare se c'è scritto che l'aggiornamento ha aviuto successo
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Gestisce eventuali errori tipo connessione fallita
                Snackbar.make(localView, "Errore nell'update:", Snackbar.LENGTH_LONG)
                        .setAction(error.getMessage(), null).show();
                System.out.print(error.getMessage());
                error.printStackTrace();
            }
        });
    }
    private void mostraInputDialog(String title, String messaggio, String totale){
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).create();
        dialog.setTitle(title);
        final EditText input = new EditText(this);
        input.setText(totale);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialog.setView(input);
        dialog.setMessage(messaggio);
        //String punti = "";
        dialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setMenoPunti(Integer.parseInt(input.getText().toString()));
                        //Ha cliccato ok
                        dialog.dismiss();
                    }
                });
        dialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
    private void setMenoPunti(int meno_punti){
        TextView numero = (TextView)this.findViewById(R.id.numero);
        numero.setText(String.valueOf(meno_punti));
        TextView label_punti = (TextView)this.findViewById(R.id.label_punti);
        label_punti.setText("Modifica punti (" + String.valueOf(Integer.parseInt(numero.getText().toString()) / 102) + " € di sconto)");
    }
    private void mostraDialog(String title, String messaggio){
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).create();
        dialog.setTitle(title);
        dialog.setMessage(messaggio);
        dialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Ha cliccato ok
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
}
