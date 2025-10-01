/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OS
 */
public class GestoreMissioni {
    private List<NPC> npcs;
    private Map<String, List<CriterioAzioneCompletata>> azioniInAttesa;

    public GestoreMissioni() {
        this.npcs = new ArrayList<>();
        this.azioniInAttesa = new HashMap<>();
    }

    public void aggiungiNPC(NPC npc) {
        npcs.add(npc);
        // Registra i criteri di azione
        for (Missione missione : npc.getMissioniAttive()) {
            if (missione.getCriterio() instanceof CriterioAzioneCompletata) {
                CriterioAzioneCompletata criterio = (CriterioAzioneCompletata) missione.getCriterio();
                String azione = criterio.getTipoAzione();
                azioniInAttesa.computeIfAbsent(azione, k -> new ArrayList<>()).add(criterio);
            }
        }
    }

    public void giocatoreCompieAzione(String tipoAzione, String oggettoUsato) {
        // Gestione oggetti
        for (NPC npc : npcs) {
            for (Missione missione : npc.getMissioniAttive()) {
                if (missione.getCriterio() instanceof CriterioUsoOggetti) {
                    CriterioUsoOggetti criterio = (CriterioUsoOggetti) missione.getCriterio();
                    criterio.oggettoUsato(oggettoUsato);
                }
            }
        }
        
        // Gestione azioni semplici
        if (azioniInAttesa.containsKey(tipoAzione)) {
            for (CriterioAzioneCompletata criterio : azioniInAttesa.get(tipoAzione)) {
                criterio.segnalaAzioneCompletata(tipoAzione);
            }
        }
        
        // Verifica completamento
        for (NPC npc : npcs) {
            npc.verificaMissioni();
        }
    }
}