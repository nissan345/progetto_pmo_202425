package main.neri.actions;

import model.OggettoGioco;
import model.Personaggio;
import model.RisultatoAzione;

public interface Azione {
    RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio);
    boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio);
    String getNome();
    String getDescrizione();
}
