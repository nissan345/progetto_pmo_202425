package main.fabbri.classes;

// import java.util.HashMap;
// import java.util.Map;

public class Personaggio {
    private String nome;
    private int livello;
    private String vestiti;
    private String preferenze;
    private String capelli;
    private int fame;
    private int sete;
    private int energia;
    private int igiene;
    // private Stanza stanzaCorrente;   // Posizione
    

    // COSTRUTTORE ------------------------------------------------------------------------
    public Personaggio(String nome, String vestiti, String preferenze, String capelli) {
        this.nome = nome;
        this.livello = 1;
        this.vestiti = vestiti;
        this.preferenze = preferenze;
        this.capelli = capelli;
        
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
    
    public String getVestiti() { 
        return vestiti; 
    }
    
    public void setVestiti(String vestiti) { 
        this.vestiti = vestiti; 
    }
    
    public String getPreferenze() { 
        return preferenze; 
    }
    
    public void setPreferenze(String preferenze) { 
        this.preferenze = preferenze; 
    }
    
    public String getCapelli() { 
        return capelli; 
    }
    
    public void setCapelli(String capelli) { 
        this.capelli = capelli; 
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

    // METODI ---------------------------------------------------------------

    public int getStatistiche() {
        return livello;
    }
    
    /*
    public String getPosizione() {
        return stanzaCorrente.getNome();
    }

    public void scegliStanza(Stanza stanza) {
        this.stanzaCorrente = stanza;
        System.out.println("Sei entrato in: " + stanza.getNome());
    }

    public void aumentaLivello(Missione missione) {
        if (missione != null && missione.isCompletata()) {
            livello++;
            System.out.println("Livello aumentato a: " + livello);
        }
    }

    public String interagisci(OggettoGioco oggetto) {
        if (oggetto != null && stanzaCorrente != null && 
            stanzaCorrente.contieneOggetto(oggetto)) {
            oggetto.interagisci(this);
            return "Hai interagito con " + oggetto.getNome();
        }
    }
    */

    //FUNZIONE AGGIUNTA DA ALI, NON SO SE SERVE
    /* // Metodo per applicare gli effetti delle azioni
    public void applicaEffetti(RisultatoAzione risultato) {
        this.fame = Math.max(0, Math.min(100, this.fame + risultato.getDeltaFame()));
        this.sete = Math.max(0, Math.min(100, this.sete + risultato.getDeltaSete()));
        this.energia = Math.max(0, Math.min(100, this.energia + risultato.getDeltaEnergia()));
        this.igiene = Math.max(0, Math.min(100, this.igiene + risultato.getDeltaIgiene()));
    }  */

    
    // FUNZIONI PRINCIPALI ----------------------------------------------------------------

    public void stampaStato() {
        System.out.println("\n STATO DI " + nome.toUpperCase());
        System.out.println("Livello: " + livello);
        System.out.println("Vestiti: " + vestiti);
        System.out.println("Capelli: " + capelli);
        System.out.println("Fame: " + fame + "/100");
        System.out.println("Sete: " + sete + "/100");
        System.out.println("Energia: " + energia + "/100");
        System.out.println("Igiene: " + igiene + "/100");
        // System.out.println("Posizione: " + getPosizione());
    }

    /*
    public Map<String, Integer> getStatoCompleto() {
        Map<String, Integer> stato = new HashMap<>();
        stato.put("fame", fame);
        stato.put("sete", sete);
        stato.put("energia", energia);
        stato.put("igiene", igiene);
        return stato;
    }
    */
}