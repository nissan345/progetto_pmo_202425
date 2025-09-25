/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OS
 */
/* Appena hai la classe Stanza aggiunge l'attributo posizione e mettilo nel costruttore */
 public abstract class NPC {
    private final String relazione;
    private int affinita;
    private List<Missione> missioniDisponibili;
    private List<Missione> missioniAttive;

    public NPC(final String relazione) {
        this.relazione = relazione;
        this.affinita = 0;
        this.missioniDisponibili = new ArrayList<>();
        this.missioniAttive = new ArrayList<>();
        inizializzaMissioni();
    }

    // Metodo astratto per inizializzare le missioni specifiche di ogni NPC
    protected abstract void inizializzaMissioni();

    // Interazione con il giocatore
    public abstract String interazione();

    // Aggiunge una missione disponibile
    protected void aggiungiMissione(Missione missione) {
        missioniDisponibili.add(missione);
    }

    // Assegna una missione al giocatore
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
            }
        }
        
        missioniAttive.removeAll(completate);
    }

    public void incrementaAffinita() {
        this.affinita += 10;
    }

    // GETTER
    public String getRelazione() { 
        return relazione; 
    }

    public int getAffinita() { 
        return affinita; 
    }

    public List<Missione> getMissioniDisponibili() { 
        return new ArrayList<>(missioniDisponibili); 
    }

    public List<Missione> getMissioniAttive() { 
        return new ArrayList<>(missioniAttive); 
    }
}

