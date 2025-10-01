package main.neri.interfacce;

import main.neri.classe.OggettoGioco;
import main.neri.classe.RisultatoAzione;

public interface Azione {
    RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio);
    boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio);
    String getNome();
    String getDescrizione();
}
