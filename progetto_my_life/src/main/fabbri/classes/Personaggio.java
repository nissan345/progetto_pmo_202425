package main.fabbri.classes;

import java.util.HashMap;
import java.util.Map;

public class Personaggio {
    private String nome;
    private int livello;
    private Vestito vestiti;
    private Dieta dieta;
    private PreferenzeGusto preferenzeGusto;
    private Capelli capelli;
    private int fame;
    private int sete;
    private int energia;
    private int igiene;
    // private Stanza stanzaCorrente;   // Posizione
    

    // COSTRUTTORE ------------------------------------------------------------------------
    public Personaggio(String nome, Vestito vestiti, Dieta dieta, Capelli capelli) {
        this.nome = nome;
        this.livello = 1;
        this.vestiti = vestiti;
        this.dieta = dieta;
        this.capelli = capelli;
        this.preferenzeGusto = new PreferenzeGusto();
        
        // Valori iniziali
        this.fame = 100;
        this.sete = 100;
        this.energia = 100;
        this.igiene = 100;
        
        // this.stanzaCorrente = null;
    }

    // GETTER E SETTER -------------------------------------------------------------------
    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public int getLivello() { 
        return livello; 
    }
    
    public void setLivello(int livello) { 
        this.livello = livello; 
    }
    
    public Vestito getVestiti() { 
        return vestiti; 
    }

    public String getPreferenze() { 
        return preferenzeGusto.toString(); 
    }
    
    public void setPreferenza(Gusto gusto, PreferenzeGusto.Reazione reazione) {
        this.preferenzeGusto.setPreferenza(gusto, reazione);
    }

    public Dieta getDieta() { 
        return dieta; 
    }

    public Capelli getCapelli() { 
        return capelli; 
    }
    
    public int getFame() { 
        return fame; 
    }
    
    public int getSete() { 
        return sete; 
    }
    
    public int getEnergia() { 
        return energia; 
    }
    
    public int getIgiene() { 
        return igiene; 
    }

    // METODI PRINCIPALI ----------------------------------------------------------------
    public String stampaStato() {
        StringBuilder stato = new StringBuilder();
        
        stato.append("\n STATO DI ").append(nome.toUpperCase()).append("\n");
        stato.append("Livello: ").append(livello).append("\n");
        // stato.append("Vestiti: ").append(vestiti.getNome()).append("\n");
        // stato.append("Capelli: ").append(capelli.getNome()).append("\n");
        stato.append("Dieta: ").append(dieta.getNome()).append(" - ").append(dieta.getDescrizione()).append("\n");
        stato.append("Preferenze di gusto:\n").append(preferenzeGusto.toString());
        stato.append("Fame: ").append(fame).append("/100\n");
        stato.append("Sete: ").append(sete).append("/100\n");
        stato.append("Energia: ").append(energia).append("/100\n");
        stato.append("Igiene: ").append(igiene).append("/100\n");
        
        return stato.toString();
    }

    // METODI PER LE ATTIVITA' QUOTIDIANE ------------------------------------------------ 
    
    // METODO PER MANGIARE
    public String mangia(TipoCibo cibo) {
        // Controllo dieta
        if (!dieta.puoMangiare(cibo.getDietaMinima())) {
            return "Non puoi mangiare " + cibo.getNome() + " con la tua dieta " + dieta.getNome();
        }

        int sazietaEffettiva = cibo.getValoreNutritivo();
        String messaggio = "Hai mangiato " + cibo.getNome() + "[ Fame: +"+ cibo.getValoreNutritivo() +"]. ";

        // Modifica in base al gusto
        int modificaFame = preferenzeGusto.getModificaFame(cibo.getGusto());
        sazietaEffettiva += modificaFame;

        if (modificaFame > 0) {
            messaggio += "Ti è piaciuto! ";
        } else if (modificaFame < 0) {
            messaggio += "Non ti è piaciuto. ";
        }

        // Bonus se il cibo è dolce
        if (cibo.getGusto() == Gusto.DOLCE) {
            sazietaEffettiva += 5;
            messaggio += "Bonus dolcezza! ";
        }

        // Aggiorna fame
        fame = Math.min(100, fame + sazietaEffettiva);

        return messaggio;
    }

    // METODO PER BERE
    public String bevi(Bevanda bevanda) {
        int dissetamentoEffettivo = bevanda.getDissetamento();
        int energiaBevanda = bevanda.getEnergiaBevanda();

        this.sete = Math.min(100, this.sete + dissetamentoEffettivo);
        this.energia = Math.min(100, this.energia + energiaBevanda);

        StringBuilder messaggio = new StringBuilder("Hai bevuto " + bevanda.getNome() + " [Sete: +" + dissetamentoEffettivo);

        if (energiaBevanda > 0) {
            messaggio.append(", Energia: +").append(energiaBevanda);
        }

        messaggio.append("]");

        return messaggio.toString();
    }

    // METODO PER DORMIRE (LETTO)
    public String dormi() {
        int energiaRecuperata = 70;
        this.energia = Math.min(100, this.energia + energiaRecuperata);
        return "Hai dormito e recuperato " + energiaRecuperata + " di energia.";
    }

    // METODO PER FARE UN PISOLINO (DIVANO)
    public String faiPisolino(){
        int energiaRecuperata = 40;
        this.energia = Math.min(100, this.energia + energiaRecuperata);
        return "Hai fatto un pisolino e recuperato " + energiaRecuperata + " di energia.";
    }

    // METODO PER FARE LA DOCCIA
    public String faiDoccia() {
        int igieneRecuperata = 40;
        this.igiene = Math.min(100, this.igiene + igieneRecuperata);
        return "Hai fatto la doccia e recuperato " + igieneRecuperata + " di igiene.";
    }

    // METODI PER LE MISSIONI ------------------------------------------------
    /*
    public void interagisciNPC(NPC npc) {

    }

    public String interagisciOggetto(OggettoGioco oggetto) {
        if (oggetto != null && stanzaCorrente != null && 
            stanzaCorrente.contieneOggetto(oggetto)) {
            oggetto.interagisci(this);
            return "Hai interagito con " + oggetto.getNome();
        }
    }

    public String aumentaLivello(Missione missione) {
        if (missione != null && missione.isCompletata()) {
            livello++;
            return "Livello aumentato a: " + livello;
        }
    }
    */

    // METODI PER LA POSIZIONE ---------------------------------------------------------------
    
    /*
    public String getPosizione() {
        return stanzaCorrente.getNome();
    }

    public String scegliStanza(Stanza stanza) {
        this.stanzaCorrente = stanza;
        return "Sei entrato in: " + stanza.getNome();
    }
    */

    // METODI PER LA PERSONALIZZAZIONE -------------------------------------------------------

    // METODO PER CAMBIARE VESTITI
    public String cambiaVestiti(Vestito nuoviVestiti) {
        this.vestiti = nuoviVestiti;
        return "Hai cambiato i vestiti in: " + nuoviVestiti.getNome();
    }

    // METODO PER CAMBIARE CAPELLI
    public String cambiaCapelli(Capelli nuoviCapelli) {
        this.capelli = nuoviCapelli;
        return "Hai cambiato i capelli in: " + nuoviCapelli.getNome();
    }

    // METODO PER MAPPARE LO STATO COMPLETO
    public Map<String, Integer> getStatoCompleto() {
        Map<String, Integer> stato = new HashMap<>();
        stato.put("fame", fame);
        stato.put("sete", sete);
        stato.put("energia", energia);
        stato.put("igiene", igiene);
        return stato;
    }
    
}