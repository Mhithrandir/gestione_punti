package ghimdalasgmail_com.gestionepuntinippon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterUtente extends ArrayAdapter<User> implements Filterable {
    private SelecteUserListener onUtenteSelezionato;
    private Filter filtro;
    public AdapterUtente(Context context, int textViewResourceId, ArrayList<User> objects){
        super(context, textViewResourceId, objects);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_users, null);
        final User user = getItem(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ha selezionato un utente, adesso devo aprire un'activity per mostrare i dettagli dell'utente
                getSelecteUserListener().SelecteUserListener(user);
            }
        });
        TextView nome_utente = (TextView)convertView.findViewById(R.id.nome_utente);
        nome_utente.setText(user.getName() + " " + user.getCognome());
        TextView numero_tessera = (TextView)convertView.findViewById(R.id.numero_tessera);
        numero_tessera.setText(user.getNumero_tessera());
        TextView punti = (TextView)convertView.findViewById(R.id.punti);
        punti.setText(user.getPunti());
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.argb(255,214,228,249));
        } else {
            convertView.setBackgroundColor(Color.argb(255,249,225,214));
        }
        return convertView;
    }
    public SelecteUserListener getSelecteUserListener(){ return onUtenteSelezionato; }
    public void setSelecteUserListener(SelecteUserListener value){
        onUtenteSelezionato = value;
    }
    @Override
    public Filter getFilter() {
        return filtro;
    }
    public void setFilter(Filter value){
        this.filtro = value;
    }
}

