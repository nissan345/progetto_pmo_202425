package main.neri.model;

import java.util.*;

public class FabbricaOggetti {
    
    public static Map<String, OggettoGenerico> creaCasaCompleta() {
        Map<String, OggettoGenerico> casa = new HashMap<>();
        
        // Aggiungi oggetti stanza per stanza (come hai fatto tu)
        casa.put("letto", new OggettoGenerico(OggettoGenerico.TipoOggetto.LETTO));
        casa.put("computer", new OggettoGenerico(OggettoGenerico.TipoOggetto.COMPUTER));
        casa.put("armadio", new OggettoGenerico(OggettoGenerico.TipoOggetto.ARMADIO));
        // … continua con tutti gli altri oggetti …
        
        return casa;
    }
    
    public static Map<String, OggettoGenerico> creaOggettiStanza(String nomeStanza) {
        Map<String, OggettoGenerico> oggetti = new HashMap<>();
        Map<String, OggettoGenerico> tuttiOggetti = creaCasaCompleta();
        
        for (Map.Entry<String, OggettoGenerico> entry : tuttiOggetti.entrySet()) {
            if (entry.getValue().getStanza().equalsIgnoreCase(nomeStanza)) {
                oggetti.put(entry.getKey(), entry.getValue());
            }
        }
        
        return oggetti;
    }
}
