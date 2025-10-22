/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import main.giuseppetti.interfaces.CriterioCompletamento;

/**
 *
 * @author OS
 */
public class GestoreMissioni {
    private List<NPC> npcs = new ArrayList<>(); // Inizializza la lista

    // Metodo per registrare NPC
    public void registraNPC(NPC npc) {
        npcs.add(npc);
    }

    public void giocatoreCompieAzione(String tipoAzione, String oggettoUsato) {
        for (NPC npc : npcs) {
            for (Missione missione : npc.getMissioniAttive()) {
                for (CriterioCompletamento criterio : missione.getCriteri()) {
                    notificaCriterio(criterio, tipoAzione, oggettoUsato);
                }
                // Da rimuovere System.out.println()
                if (missione.verificaCompletamento()) {
                    System.out.println("Missione completata: " + missione.getNome());
                } 
            }
        }
    }
    
    private void notificaCriterio(CriterioCompletamento criterio, String azione, String oggetto) {
        if (criterio instanceof CriterioUsoOggetti && oggetto != null) {
            ((CriterioUsoOggetti) criterio).oggettoUsato(oggetto);
        } else if (criterio instanceof CriterioAzioneCompletata && azione != null) {
            ((CriterioAzioneCompletata) criterio).segnalaAzioneCompletata(azione);
        } else if (criterio instanceof Sottocriteri) {
            for (CriterioCompletamento sub : ((Sottocriteri) criterio).getSottocriteri()) {
                notificaCriterio(sub, azione, oggetto);
            }
        }
    }
}