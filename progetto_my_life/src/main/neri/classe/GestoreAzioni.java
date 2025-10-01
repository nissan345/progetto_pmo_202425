package main.neri.classe;

import java.util.*;
import main.neri.interfacce.*;

public class GestoreAzioni {
     Map<String, Azione> azioni;
    
    public GestoreAzioni() {
        this.azioni = new HashMap<>();
        inizializzaAzioni();
    }
    
    private void inizializzaAzioni() {
        azioni.put("usa", new AzioniImplementate.AzioneUsa());
        azioni.put("esamina", new AzioniImplementate.AzioneEsamina());
        azioni.put("pulisci", new AzioniImplementate.AzionePulire());
    }
    
    public RisultatoAzione eseguiAzione(String nomeAzione, OggettoGioco oggetto, Personaggio personaggio) {
        Azione azione = azioni.get(nomeAzione.toLowerCase());
        if (azione == null) {
            return new RisultatoAzione("Non so come fare: " + nomeAzione);
        }
        return azione.esegui(oggetto, personaggio);
    }
    
    public List<String> getAzioniDisponibili(OggettoGioco oggetto, Personaggio personaggio) {
        List<String> disponibili = new ArrayList<>();
        for (Map.Entry<String, Azione> entry : azioni.entrySet()) {
            if (entry.getValue().puoEseguire(oggetto, personaggio)) {
                disponibili.add(entry.getKey());
            }
        }
        return disponibili;
    }
    
    public Set<String> getTutteLeAzioni() {
        return azioni.keySet();
    }
}

