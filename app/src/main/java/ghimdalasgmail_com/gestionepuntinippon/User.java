package ghimdalasgmail_com.gestionepuntinippon;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String cognome;
    private String mail;
    private String cell;
    private String scadenza;
    private String punti;
    private String numero_tessera;
    private Tipo tipologia;
    private String registrazione;
    private String indirizzo;
    private String nascita;
    private String cap;
    private String comune;
    private String provincia;
    private boolean privacy;
    private boolean statuto;
    private boolean newsletter;
    public User(){

    }
    public User(String key, JSONObject obj){
        this.setId(key);
        try {
            this.setName(obj.get("nome").toString());
            this.setCognome(obj.get("cognome").toString());
            this.setMail(obj.get("mail").toString());
            this.setCell(obj.get("cell").toString());
            this.setScadenza(obj.get("scadenza").toString());
            this.setPunti(obj.get("punti").toString());
            this.setNumero_tessera(obj.get("numero_tessera").toString());
            if(obj.get("tipologia").toString().equals("Ordinaria"))
                this.setTipologia(Tipo.Ordinario);
            else this.setTipologia(Tipo.Gratuito);
            this.setRegistrazione(obj.get("registrazione").toString());
            this.setIndirizzo(obj.get("indirizzo").toString());
            this.setNascita(obj.get("nascita").toString());
            this.setCap(obj.get("cap").toString());
            this.setComune(obj.get("comune").toString());
            this.setProvincia(obj.get("provincia").toString());
            this.setPrivacy(Boolean.parseBoolean(obj.get("privacy").toString()));
            this.setStatuto(Boolean.parseBoolean(obj.get("statuto").toString()));
            this.setNewsletter(Boolean.parseBoolean(obj.get("newsletter").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCell() {
        return cell;
    }
    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getScadenza() {
        return scadenza;
    }
    public void setScadenza(String scadenza) {
        this.scadenza = scadenza;
    }

    public String getPunti() {
        if((punti.equals(""))||(punti == null))
            return "0";
        return punti;
    }
    public void setPunti(String punti) {
        this.punti = punti;
    }

    public String getNumero_tessera() {
        return numero_tessera;
    }
    public void setNumero_tessera(String numero_tessera) {
        this.numero_tessera = numero_tessera;
    }

    public Tipo getTipologia() {
        return tipologia;
    }
    public void setTipologia(Tipo tipologia) {
        this.tipologia = tipologia;
    }

    public String getRegistrazione() {
        return registrazione;
    }
    public void setRegistrazione(String value) {
        this.registrazione = value;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNascita() {
        return nascita;
    }
    public void setNascita(String nascita) {
        this.nascita = nascita;
    }

    public String getCap() {
        return cap;
    }
    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }
    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getPrivacy() {
        return privacy;
    }
    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public boolean getStatuto() {
        return statuto;
    }
    public void setStatuto(boolean statuto) {
        this.statuto = statuto;
    }

    public boolean getNewsletter() {
        return newsletter;
    }
    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public String getAdd() {
        String news_letter = "0";
        if(this.getNewsletter())
            news_letter = "1";
        return "id=" + this.getId()
                + "&nome=" + this.getName()
                + "&cognome=" + this.getCognome()
                + "&mail=" + this.getMail()
                + "&cell=" + this.getCell()
                + "&scadenza=" + this.getScadenza()
                + "&punti=" + this.getPunti()
                + "&numero_tessera=" + this.getNumero_tessera()
                + "&tipologia=" + this.getTipologia().toString()
                + "&registrazione=" + this.getRegistrazione()
                + "&indirizzo=" + this.getIndirizzo()
                + "&nascita=" + this.getNascita()
                + "&cap=" + this.getCap()
                + "&comune=" + this.getComune()
                + "&provincia=" + this.getProvincia()
                + "&privacy=1&statuto=1"
                + "&newsletter=" + news_letter;
    }
    public boolean contains(CharSequence value){
        return ((this.getName().contains(value))||
                (this.getCognome().contains(value))||
                (this.getMail().contains(value))||
                (this.getCell().contains(value))||
                (this.getScadenza().contains(value))||
                (this.getPunti().contains(value))||
                (this.getNumero_tessera().contains(value))||
                (this.getRegistrazione().contains(value))||
                (this.getIndirizzo().contains(value))||
                (this.getNascita().contains(value))||
                (this.getCap().contains(value))||
                (this.getComune().contains(value))||
                (this.getProvincia().contains(value)));
    }
}
