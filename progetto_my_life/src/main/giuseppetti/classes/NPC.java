package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import main.aboufaris.interfaces.Stanza;
import main.fabbri.classes.*;


/**
 *
 * @author OS
 */
public abstract class NPC {
    private final String relazione;
    private Stanza posizione; 
    private int affinita;
    private List<Missione> missioniDisponibili;
    private List<Missione> missioniAttive;
    private List<OpzioniInterazione> opzioni;

    public NPC(final String relazione, Stanza s) {
        this.relazione = relazione;
        this.posizione = s; 
        this.affinita = 0;
        this.missioniDisponibili = new ArrayList<>();
        this.missioniAttive = new ArrayList<>();
        this.opzioni = new ArrayList<>();
        inizializzaMissioni();
        
    }

    // Metodo per il dialogo iniziale che ha ogni NPC
    public abstract String getDialogoIniziale(); 

    // Metodo per il dialogo per assegnare una missione 
    protected abstract  String getMissioneAssegnataDialogo(Missione missione);

    // Metodo per la risposta al completamento di una missione 
    public abstract String getReazioneCompletamentoMissione(Missione missione); 

    // Metodo astratto per inizializzare le missioni specifiche di ogni NPC
    protected abstract void inizializzaMissioni();

    // Interazione con il Personaggio - COMPLETA
    public String interagisci(Personaggio p) {
        return this.getRelazione() + ": " + this.getDialogoIniziale();
    }

    public List<OpzioniInterazione> getOpzioniDisponibili(){
        this.opzioni.add(OpzioniInterazione.CHIEDI_MISSIONE);
        this.opzioni.add(OpzioniInterazione.CONSEGNA_MISSIONE);
        this.opzioni.add(OpzioniInterazione.ESCI);
        return opzioni;
    }

    // Ho messo il metodo pubblico che richiama quello privato
    public List<String> consegnaMissione(Personaggio p){
           return elaboraConsegnaMissione(p);
    }
    
    
    private List<String> elaboraConsegnaMissione(Personaggio p) {
        List<String> messaggio = new ArrayList<>();
        List<Missione> missioniPersonaggio = p.getMissioniAttive();
        if (missioniPersonaggio.isEmpty()) {
            messaggio.add("La missione non è stata registrata.");
            return messaggio;    
        }
        
        List<Missione> missioniCompletate = p.getMissioniAttive().stream()
                                            .filter(missione -> missione.getNPCAssegnato().equals(this))
                                            .filter(Missione::verificaCompletamento)
                                            .collect(Collectors.toList());

        for (Missione missione : missioniCompletate) {
            // CORREZIONE: getNpcAssegnato() invece di getNPCAssegnato()
                incrementaAffinita();
                p.rimuoviMissione(missione);
                messaggio.add(getReazioneCompletamentoMissione(missione) + "\nMissione"+ missione.getNome()+" completata");  
            }
        if(missioniCompletate.isEmpty())
            messaggio.add("Non ci sono missioni completate con " + this.relazione);

        messaggio.add("Affinità con " + this.relazione + " E' " +this.affinita);
        return messaggio;
    }

    // Aggiunge una missione disponibile
    protected void aggiungiMissione(Missione missione) {
        missioniDisponibili.add(missione);
    }

    // Assegna una missione al Personaggio
    public Missione assegnaMissione() {
        if (!missioniDisponibili.isEmpty()) {
            Missione missione = missioniDisponibili.remove(0);
            missioniAttive.add(missione);
            return missione;
        }
        return null;
    }

    // Verifica il completamento di tutte le missioni attive
    public void verificaMissioni() {
        List<Missione> completate = new ArrayList<>();
        
        for (Missione missione : missioniAttive) {
            if (missione.verificaCompletamento()) {
                completate.add(missione);
                incrementaAffinita();
            }
        }
        
        missioniAttive.removeAll(completate);
    }

    public void incrementaAffinita() {
        this.affinita += 10;
        String n = "Affinità con " + this.relazione + " aumentata a: " + affinita;
    }

    // GETTER 
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

    public List<Missione> getMissioniAttive() {
        return new ArrayList<>(this.missioniAttive);
    }
    
    // SETTER per la posizione 
    public void setPosizione(Stanza nuovaPosizione) {
        this.posizione = nuovaPosizione;
    }
}