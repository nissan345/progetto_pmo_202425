package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.aboufaris.interfaces.Stanza;

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

    public NPC(final String relazione, Stanza s) {
        this.relazione = relazione;
        this.posizione = s; 
        this.affinita = 0;
        this.missioniDisponibili = new ArrayList<>();
        this.missioniAttive = new ArrayList<>();
        inizializzaMissioni();
    }

    // Metodo per il dialogo iniziale che ha ogni NPC
    public abstract String getDialogoIniziale(); 

    // Metodo per la risposta al completamento di una missione 
    public abstract String getReazioneCompletamentoMissione(Missione missione);

    // Metodo astratto per inizializzare le missioni specifiche di ogni NPC
    protected abstract void inizializzaMissioni();

    // Interazione con il Personaggio - COMPLETA
    public void interagisci(Personaggio p) {
        System.out.println(this.getRelazione() + ": " + this.getDialogoIniziale());
        mostraOpzioni(p);
        gestisciScelta(p);
    }

    private void mostraOpzioni(Personaggio p) {
        System.out.println("\n--- Opzioni ---");
        System.out.println("1. Chiedi missione");
        System.out.println("2. Consegna missione");
        System.out.println("3. Esci");
    }

    private void gestisciScelta(Personaggio p) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Scegli un'opzione (1-3): ");
        int scelta = scanner.nextInt();
        
        switch(scelta) {
            case 1:
                Missione missione = assegnaMissione();
                if (missione != null) {
                    System.out.println("Nuova missione: " + missione.getNome());
                    p.aggiungiMissione(missione);
                } else {
                    System.out.println("Non ci sono missioni disponibili al momento.");
                }
                break;
            case 2:
                consegnaMissione(p);
                break;
            case 3:
                System.out.println("Arrivederci!");
                break;
            default:
                System.out.println("Opzione non valida.");
        }
    }

    private void consegnaMissione(Personaggio p) {
        List<Missione> missioniPersonaggio = p.getMissioniAttive();
        if (missioniPersonaggio.isEmpty()) {
            System.out.println("Non hai missioni attive da consegnare.");
            return;
        }
        
        for (Missione missione : missioniPersonaggio) {
            if (missione.getNPCAssegnato().equals(this)) {
                if (missione.verificaCompletamento()) {
                    missione.isCompletata();
                    System.out.println(getReazioneCompletamentoMissione(missione));
                    incrementaAffinita();
                } else {
                    System.out.println("La missione '" + missione.getNome() + "' non è ancora completata!");
                }
            }
        }
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
        System.out.println("Affinità con " + this.relazione + " aumentata a: " + affinita);
    }

    // GETTER - CORRETTI
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