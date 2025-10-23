package main.view;

import java.util.List;
import main.aboufaris.interfaces.*;
import main.giuseppetti.classes.*;
import main.neri.classes.*;

public class View {
    public int mostraAzioni(List<String> a){return 1;}
    public void mostraStanza(String nome,Stanza stanza){}
    public void mostraMissioniAttive(String Nome, String Descrizione){}
    public void mostraAvviso(String a){}
    public void mostraBisogni(){}
    public void mostraErrore(String errore){}
    public void mostraRisultato(String getMessaggio){}
    public void aggiornaStatoPersonaggio(String stato){}  
    public void mostraMessaggio(String s){}
    public void mostraOpzioni(List<OpzioniInterazione> opzinioni){}
    public void mostraCasa(){}
    public void mostraStanza(){}
    public void mostraVittoria(){}
    public void mostraSconfitta(){}
    public void mostraMenu(){}
    public void mostraOggettiInStanza(List<OggettoGioco> oggettiPresenti){};
    public void mostraNpc(NPC npcInStanza){}
    public void aggiornaStanza(Stanza stanzaCorrente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aggiornaStanza'");
    };

    public String chiediNomePersonaggio() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int mostraVestitiDisponibili(String[] values) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int mostraDieteDisponibili(String[] values) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int mostraCapelli(String[] values) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
