package main.neri.actions;

import main.neri.model.OggettoGioco;
import main.neri.model.Personaggio;
import main.neri.model.RisultatoAzione;

public interface Azione {
    RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio);
    boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio);
    String getNome();
    String getDescrizione();
}
