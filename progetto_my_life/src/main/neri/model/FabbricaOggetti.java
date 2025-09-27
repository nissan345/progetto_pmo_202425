package main.neri.model;

import java.util.*;
     

public class FabbricaOggetti {
    
    public static Map<String, OggettoGenerico> creaCasaCompleta() {
        Map<String, OggettoGenerico> casa = new HashMap<>();
        
        // Camera da Letto
        casa.put("letto", new OggettoGenerico(OggettoGenerico.TipoOggetto.LETTO));
        casa.put("computer", new OggettoGenerico(OggettoGenerico.TipoOggetto.COMPUTER));
        casa.put("armadio", new OggettoGenerico(OggettoGenerico.TipoOggetto.ARMADIO));
        
        // Cucina
        casa.put("frigorifero", new OggettoGenerico(OggettoGenerico.TipoOggetto.FRIGORIFERO));
        casa.put("fornelli", new OggettoGenerico(OggettoGenerico.TipoOggetto.FORNELLI));
        casa.put("lavandino", new OggettoGenerico(OggettoGenerico.TipoOggetto.LAVANDINO));
        
        // Bagno
        casa.put("doccia", new OggettoGenerico(OggettoGenerico.TipoOggetto.DOCCIA));
        casa.put("wc", new OggettoGenerico(OggettoGenerico.TipoOggetto.WC));
        casa.put("lavatrice", new OggettoGenerico(OggettoGenerico.TipoOggetto.LAVATRICE));
        
        // Salotto
        casa.put("televisione", new OggettoGenerico(OggettoGenerico.TipoOggetto.TELEVISIONE));
        casa.put("stereo", new OggettoGenerico(OggettoGenerico.TipoOggetto.STEREO));
        casa.put("divano", new OggettoGenerico(OggettoGenerico.TipoOggetto.DIVANO));
        casa.put("libreria", new OggettoGenerico(OggettoGenerico.TipoOggetto.LIBRERIA));
        
        // Sgabuzzino
        casa.put("aspirapolvere", new OggettoGenerico(OggettoGenerico.TipoOggetto.ASPIRAPOLVERE));
        casa.put("innaffiatoio", new OggettoGenerico(OggettoGenerico.TipoOggetto.INNAFFIATOIO));
        
        // Giardino
        casa.put("piante", new OggettoGenerico(OggettoGenerico.TipoOggetto.PIANTE));
        casa.put("palla", new OggettoGenerico(OggettoGenerico.TipoOggetto.PALLA));
        casa.put("altalena", new OggettoGenerico(OggettoGenerico.TipoOggetto.ALTALENA));
        casa.put("macchina", new OggettoGenerico(OggettoGenerico.TipoOggetto.MACCHINA));
        
        
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
