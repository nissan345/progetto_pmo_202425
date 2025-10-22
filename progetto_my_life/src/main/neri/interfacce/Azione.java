package main.neri.interfacce;

import main.fabbri.classes.*;
import main.neri.classes.OggettoGioco;
import main.neri.classes.RisultatoAzione;

public interface Azione {
    RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio);
    boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio);
    String getNome();
    String getDescrizione();
}
