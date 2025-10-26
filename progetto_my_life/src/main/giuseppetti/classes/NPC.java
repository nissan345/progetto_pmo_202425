package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.aboufaris.interfaces.Stanza;
import main.fabbri.classes.Personaggio;

public abstract class NPC {
    private final String relazione;
    private final Stanza posizione; 
    private int affinita;
    private List<Missione> missioniDisponibili;
    private List<OpzioniInterazione> opzioni;

    public NPC(final String relazione, final Stanza s) {
        this.relazione = relazione;
        this.posizione = s; 
        this.affinita = 0;
        this.missioniDisponibili = new ArrayList<>();
        this.opzioni = new ArrayList<>();
        inizializzaMissioni();
    }

    // Metodi Astratti
    
    // Metodo per il dialogo iniziale con un NPC
    public abstract String getDialogoIniziale();
    
    // Metodo per il dialogo quando un NPC assegna una missione
    public abstract String getMissioneAssegnataDialogo(Missione missione);
    
    // Metodo per il dialogo quando una missione non è stata ancora completata
    public abstract String getDialogoMissioneInCorso(Missione missione);
    
    // Metodo per il dialogo quando una missione è stata completata 
    public abstract String getDialogoCompletamentoMissione(Missione missione);
    
    // Metodo per l'inizializzazione della missione
    protected abstract void inizializzaMissioni();
    
    // Metodi concreti 
    
    // Gestisce l'interazione tra personaggio e NPC
    public List<OpzioniInterazione> getOpzioniDisponibili(Personaggio personaggio) {
        this.opzioni.clear();
        
        // CHIEDI_MISSIONE: solo se non hai già una missione attiva con questo NPC
        boolean haMissioneAttivaConQuestoNPC = haMissioneAttivaConQuestoNPC(personaggio);
        
        if (!missioniDisponibili.isEmpty() && !haMissioneAttivaConQuestoNPC) {
            this.opzioni.add(OpzioniInterazione.CHIEDI_MISSIONE);
        } 
        
        // CONSEGNA_MISSIONE: solo se hai missioni completate con questo NPC
        if (haMissioniCompletate(personaggio)) {
            this.opzioni.add(OpzioniInterazione.CONSEGNA_MISSIONE);
        }
        
        this.opzioni.add(OpzioniInterazione.ESCI);
        return this.opzioni;
    }

    // Assegna una missione al Personaggio rimuovendola quindi dalle missioni disponibili
    public Missione assegnaMissione(Personaggio personaggio) {
        // Controllo di sicurezza
        if (haMissioneAttivaConQuestoNPC(personaggio)) {
            return null;
        }
        
        if (missioniDisponibili.isEmpty()) {
            return null;
        }
        
        Missione missione = missioniDisponibili.remove(0);
        // La missione viene aggiunta solo al personaggio
        personaggio.aggiungiMissione(missione);
        return missione;
    }

    public List<String> consegnaMissione(Personaggio personaggio) {
        List<String> messaggi = new ArrayList<>();
        
        // Trova missioni completate di questo NPC
        List<Missione> missioniCompletate = personaggio.getMissioniAttive().stream()
            .filter(missione -> missione.getNPCAssegnatore().equals(this))
            .filter(missione -> missione.verificaCompletamento(personaggio))
            .collect(Collectors.toList());

        if (missioniCompletate.isEmpty()) {
            messaggi.add("Non ci sono missioni completate con " + this.relazione);
            return messaggi;
        }

        // Processa la prima missione completata (dovrebbe essercene solo una)
        Missione missione = missioniCompletate.get(0);
        incrementaAffinita(missione.getPuntiAffinita());
        personaggio.rimuoviMissione(missione);
        
        messaggi.add(getDialogoCompletamentoMissione(missione));
        messaggi.add("Missione '" + missione.getNome() + "' completata!");
        messaggi.add("Affinità con " + this.relazione + ": " + this.affinita + "/100");
        
        return messaggi;
    }

    // Metodi privati di supporto
    
    // Controlla se il personaggio ha una missione attiva con un determinato NPC
    private boolean haMissioneAttivaConQuestoNPC(Personaggio personaggio) {
        return personaggio.getMissioniAttive().stream()
            .anyMatch(missione -> missione.getNPCAssegnatore().equals(this));
    }
    
    // Controlla se il personaggio ha una missione completata relativa ad un determinato NPC
    private boolean haMissioniCompletate(Personaggio personaggio) {
        return personaggio.getMissioniAttive().stream()
            .filter(missione -> missione.getNPCAssegnatore().equals(this))
            .anyMatch(missione -> missione.verificaCompletamento(personaggio));
    }
    
    // Si aggiunge una missione a quelle disponibili 
    protected void aggiungiMissione(Missione missione) {
        missioniDisponibili.add(missione);
    }
    
    // Incrementa l'affinita tra un personaggio e un NPC, l'affinita va da 0 a 100
    protected void incrementaAffinita(int puntiAffinita) {
        this.affinita = Math.min(100, this.affinita + puntiAffinita);
    }

    // Getter
    public String getRelazione() { 
    	return this.relazione; 
    }
    
    public int getAffinita() { 
    	return this.affinita; 
    }
    
    public Stanza getPosizione() { 
    	return this.posizione; 
    }
    
    public List<Missione> getMissioniDisponibili() { 
        return new ArrayList<>(this.missioniDisponibili); 
    }
}