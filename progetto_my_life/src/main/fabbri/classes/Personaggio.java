package main.fabbri.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.aboufaris.interfaces.Stanza;
import main.giuseppetti.classes.Missione;
import main.giuseppetti.classes.NPC;
import main.neri.classes.Inventario;
import main.neri.classes.OggettoGioco;
import main.neri.classes.RisultatoAzione;

public class Personaggio {
	private static final int STATO_MAX = 100;
    private static final int STATO_MIN = 0;
    private Inventario inventory = new Inventario(30);
    private String nome;
    private Vestito vestiti;
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
    public Personaggio(String nome, Vestito vestiti, Capelli capelli) {
        this.nome = nome;
        this.vestiti = vestiti;
        this.capelli = capelli;  
        // Valori iniziali
        this.fame = 100;
        this.sete = 100;
        this.energia = 100;
        this.igiene = 100;

        this.stanzaCorrente = null; // Inizialmente nessuna stanza
        
        this.missioniAttive = new ArrayList<>();
        this.oggettiUsati = new ArrayList<>();
    }
    // Rimuovi setters per stati, livello, nome, preferenze, cibo, capelli ecc... sono tutti gestiti
    // in altri metodi 
    // GETTER E SETTER -------------------------------------------------------------------
    public String getNome() { 
        return nome; 
    }
    
    public Vestito getVestiti() { 
        return vestiti; 
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
    // IN CASO CI DIMENTICHIAMO PER L'ENNESIMA VOLTA, QUESTI SETTER SONO NECESSARI!!!!!
    private void setFame(int fame) {
        this.fame = Math.max(STATO_MIN, Math.min(STATO_MAX, fame));
    }
    
    private void setSete(int sete) {
        this.sete = Math.max(STATO_MIN, Math.min(STATO_MAX, sete));
    }
    
    private void setEnergia(int energia) {
        this.energia = Math.max(STATO_MIN, Math.min(STATO_MAX, energia));
    }
    
    private void setIgiene(int igiene) {
        this.igiene = Math.max(STATO_MIN, Math.min(STATO_MAX, igiene));
    } 
    
    // GETTER E SETTER PER LA POSIZIONE
    public Stanza getStanzaCorrente() {
        return stanzaCorrente;
    }
    // DA VEDERE
    public void setStanzaCorrente(Stanza stanzaCorrente) {
        this.stanzaCorrente = stanzaCorrente;
    }
    // DA RIVEDERE E FORSE TOGLIERE
    // METODI PRINCIPALI ----------------------------------------------------------------
    public String stampaStato() {
        StringBuilder stato = new StringBuilder();
        
        stato.append("\n STATO DI ").append(nome.toUpperCase()).append("\n");
        stato.append("Vestiti: ").append(vestiti.getNome()).append("\n");
        stato.append("Capelli: ").append(capelli.getNome()).append("\n");
        stato.append("Fame: ").append(fame).append("/100\n");
        stato.append("Sete: ").append(sete).append("/100\n");
        stato.append("Energia: ").append(energia).append("/100\n");
        stato.append("Igiene: ").append(igiene).append("/100\n");
        stato.append("Posizione: ").append(getPosizione()).append("\n"); // AGGIUNTO
        
        return stato.toString();
    }

   
    // METODI PER LA PERSONALIZZAZIONE -------------------------------------------------------

    /* METODO PER CAMBIARE VESTITI
    public String cambiaVestiti(Vestito nuoviVestiti) {
        this.vestiti = nuoviVestiti;
        return "Hai cambiato i vestiti in: " + nuoviVestiti.getNome();
    }
    // DA TOGLIERE
    // METODO PER CAMBIARE CAPELLI
    public String cambiaCapelli(Capelli nuoviCapelli) {
        this.capelli = nuoviCapelli;
        return "Hai cambiato i capelli in: " + nuoviCapelli.getNome();
    }
    // HA SENSO MA NON SAPPIAMO SE SERVE
    // METODO PER MAPPARE LO STATO COMPLETO
    public Map<String, Integer> getStatoCompleto() {
        Map<String, Integer> stato = new HashMap<>();
        stato.put("fame", fame);
        stato.put("sete", sete);
        stato.put("energia", energia);
        stato.put("igiene", igiene);
        return stato;
    }
 */
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
    

    // Ottiene le missioni attive con un NPC specifico
    public Optional<Missione> getMissioneAttivaConNPC(NPC npc) {
        return missioniAttive.stream()
            .filter(missione -> missione.getNPCAssegnatore().equals(npc))
            .findFirst();
    }
    

    // Verifica automaticamente il completamento di tutte le missioni attive con un NPC
    public Optional<Missione> getMissioneCompletataConNPC(NPC npc) {
	    return missioniAttive.stream()
	        .filter(missione -> missione.getNPCAssegnatore().equals(npc))
	        .filter(missione -> missione.verificaCompletamento(this))
	        .findFirst();
	}

    public void registraUsoOggetto(String nomeOggetto) {
        if (!oggettiUsati.contains(nomeOggetto)) {
            oggettiUsati.add(nomeOggetto);
        }
    }
    
    // Verifica se un oggetto è stato usato
    public boolean haUsatoOggetto(String nomeOggetto) {
        return oggettiUsati.contains(nomeOggetto);
    }
     
    public String applicaRisultatoAzione(RisultatoAzione risultato, String nomeOggetto) {
        // 1. Controlla se l'azione ha senso
        String messaggioControllo = verificaUtilitaAzione(risultato);
        if (messaggioControllo != null) {
            return messaggioControllo;
        }
        
        // 2. Registra uso oggetto
        registraUsoOggetto(nomeOggetto);
        
        // 3. Applica effetti
        this.setFame(this.getFame() + risultato.getDeltaFame());
        this.setSete(this.getSete() + risultato.getDeltaSete());
        this.setEnergia(this.getEnergia() + risultato.getDeltaEnergia());
        this.setIgiene(this.getIgiene() + risultato.getDeltaIgiene());
        
        // 4. Restituisce messaggio
        return risultato.getMessaggio();
    }
    
    
    public String interagisci(OggettoGioco oggetto) {
        RisultatoAzione risultato = oggetto.usa(this);
        return applicaRisultatoAzione(risultato, oggetto.getNome());
    }
    
    
    private String verificaUtilitaAzione(RisultatoAzione risultato) {
    	
        if (risultato.getDeltaEnergia() > 0 && this.energia >= 100) {
            return "Sei già pieno di energia, non ha senso riposare ora!";
        }else if (risultato.getDeltaFame() < 0 && this.fame <= 0) {
            return "Non hai fame, non ha senso mangiare ora!";
        }else if (risultato.getDeltaSete() < 0 && this.sete <= 0) {
            return "Non hai sete, non ha senso bere ora!";
        }else if (risultato.getDeltaIgiene() > 0 && this.igiene >= 100) {
            return "Sei già pulitissimo, non serve lavarti!";
        }else {
        	return null;
        }
    }

    public void decadimentoStato(){
        this.fame = Math.max(0, this.fame-2);
        this.sete = Math.max(0,this.sete-3);
        this.energia = Math.max(0, this.energia-1);
        this.igiene = Math.max(0, this.igiene-1);
    }
}

    