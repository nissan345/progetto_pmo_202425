package main.fabbri.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import main.aboufaris.interfaces.Stanza;
import main.giuseppetti.classes.Missione;
import main.giuseppetti.classes.NPC;
import main.neri.classes.OggettoGioco;
import main.neri.classes.RisultatoAzione;

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
    private Stanza stanzaCorrente;
    
    // Modifiche per missioni 
    private List<Missione> missioniAttive;
    private List<String> oggettiUsati; // Traccia gli oggetti usati

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

        this.stanzaCorrente = null; // Inizialmente nessuna stanza
        
        this.missioniAttive = new ArrayList<>();
        this.oggettiUsati = new ArrayList<>();
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
    
    public void setFame(int fame) {
        this.fame = Math.max(0, Math.min(100, fame));
    }
    
    public int getSete() { 
        return sete; 
    }
    
    public void setSete(int sete) {
        this.sete = Math.max(0, Math.min(100, sete));
    }
    
    public int getEnergia() { 
        return energia; 
    }
    
    public void setEnergia(int energia) {
        this.energia = Math.max(0, Math.min(100, energia));
    }
    
    public int getIgiene() { 
        return igiene; 
    }
    
    public void setIgiene(int igiene) {
        this.igiene = Math.max(0, Math.min(100, igiene));
    }
    
    // GETTER E SETTER PER LA POSIZIONE
    public Stanza getStanzaCorrente() {
        return stanzaCorrente;
    }
    
    public void setStanzaCorrente(Stanza stanzaCorrente) {
        this.stanzaCorrente = stanzaCorrente;
    }

    // METODI PRINCIPALI ----------------------------------------------------------------
    public String stampaStato() {
        StringBuilder stato = new StringBuilder();
        
        stato.append("\n STATO DI ").append(nome.toUpperCase()).append("\n");
        stato.append("Livello: ").append(livello).append("\n");
        stato.append("Vestiti: ").append(vestiti.getNome()).append("\n");
        stato.append("Capelli: ").append(capelli.getNome()).append("\n");
        stato.append("Dieta: ").append(dieta.getNome()).append(" - ").append(dieta.getDescrizione()).append("\n");
        stato.append("Preferenze di gusto:\n").append(preferenzeGusto.toString());
        stato.append("Fame: ").append(fame).append("/100\n");
        stato.append("Sete: ").append(sete).append("/100\n");
        stato.append("Energia: ").append(energia).append("/100\n");
        stato.append("Igiene: ").append(igiene).append("/100\n");
        stato.append("Posizione: ").append(getPosizione()).append("\n"); // AGGIUNTO
        
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

    // METODI PER LA POSIZIONE ---------------------------------------------------------------

    public String getPosizione() {
        if (stanzaCorrente != null) {
            return stanzaCorrente.getNomeStanza();
        } else {
            return "Nessuna stanza";
        }
    }

    public String scegliStanza(Stanza stanza) {
        this.stanzaCorrente = stanza;
        return "Sei entrato in: " + stanza.getNomeStanza();
    }

    // METODO PER INTERAGIRE CON GLI OGGETTI
    public String interagisciOggetto(OggettoGioco oggetto) {
        	oggetto.usa(this);
            return "Hai interagito con " + oggetto.getNome() + " in " + getPosizione();
        
    }



    // METODI PER LE MISSIONI -----------------------------------------------------------------------

    //Aggiunge una missione alla lista delle missioni attive
    public void aggiungiMissione(Missione missione) {
        if (missione != null && !missioniAttive.contains(missione)) {
            missioniAttive.add(missione);
        }
    }
    

    // Rimuove una missione completata dalla lista

    public void rimuoviMissione(Missione missione) {
        missioniAttive.remove(missione);
    }
    

    // Verifica se il personaggio ha missioni attive con un NPC specifico
    public boolean haMissioneAttivaConNPC(NPC npc) {
        return missioniAttive.stream()
            .anyMatch(missione -> missione.getNPCAssegnatore().equals(npc));
    }
    

    // Ottiene tutte le missioni attive del personaggio
    public List<Missione> getMissioniAttive() {
        return new ArrayList<>(missioniAttive);
    }
    
    // Ottiene le missioni attive con un NPC specifico
    public List<Missione> getMissioniAttiveConNPC(NPC npc) {
        return missioniAttive.stream()
            .filter(missione -> missione.getNPCAssegnatore().equals(npc))
            .collect(Collectors.toList());
    }
    

    // Verifica automaticamente il completamento di tutte le missioni attive (Utile per aggiornamenti in tempo reale)
    public List<Missione> getMissioniCompletate() {
        return missioniAttive.stream()
            .filter(missione -> missione.verificaCompletamento(this))
            .collect(Collectors.toList());
    }
    
    // Pulisce le missioni completate (opzionale - utile per pulizia periodica)
    public void rimuoviMissioniCompletate() {
        missioniAttive.removeAll(getMissioniCompletate());
    }
    
    /**
     * Registra che un oggetto è stato usato
     */
    public void registraUsoOggetto(String nomeOggetto) {
        if (!oggettiUsati.contains(nomeOggetto)) {
            oggettiUsati.add(nomeOggetto);
        }
    }
    
    // Verifica se un oggetto è stato usato
    public boolean haUsatoOggetto(String nomeOggetto) {
        return oggettiUsati.contains(nomeOggetto);
    }
    
    public String interagisci(OggettoGioco oggetto) {
        if (oggetto != null && stanzaCorrente != null && 
            stanzaCorrente.hasOggettoStanza(oggetto)) {
            
            // Esegui l'azione sull'oggetto
            RisultatoAzione risultato = oggetto.usa(this);
            
            // REGISTRA CHE L'OGGETTO È STATO USATO 
            registraUsoOggetto(oggetto.getNome());
            
            // Applica gli effetti del risultato al personaggio
            this.setFame(this.getFame() + risultato.getDeltaFame());
            this.setSete(this.getSete() + risultato.getDeltaSete());
            this.setEnergia(this.getEnergia() + risultato.getDeltaEnergia());
            this.setIgiene(this.getIgiene() + risultato.getDeltaIgiene());
            
            return risultato.getMessaggio();
        }
        return "Non puoi interagire con questo oggetto.";
    }  

    public void decadimentoStato(){
        this.fame = Math.max(0, this.fame-2);
        this.sete = Math.max(0,this.sete-3);
        this.energia = Math.max(0, this.energia-1);
        this.igiene = Math.max(0, this.igiene-1);
    }
}

    