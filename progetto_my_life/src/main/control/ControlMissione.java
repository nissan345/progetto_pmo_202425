package main.control;

import main.aboufaris.interfaces.Stanza;
import main.fabbri.classes.Personaggio;
import main.giuseppetti.classes.Fratello;
import main.giuseppetti.classes.GestoreMissioni;
import main.giuseppetti.classes.Madre;
import main.giuseppetti.classes.Padre;

public class ControlMissione{

    private Madre madre;
    private Padre padre;
    private Fratello fratello;
    private GestoreMissioni gestoreMissioni;
    private Personaggio personaggio;
    private int contatoreMissioni;

    public ControlMissione(Personaggio p){
        this.personaggio = p;
        this.gestoreMissioni = new GestoreMissioni();
        this.contatoreMissioni = 0;
    }

    public void creaNpc(Stanza stanza1, Stanza stanza2, Stanza stanza3){
        

    }

   
    /*
    public void onClickNpc(NPC npc) { /* assegna missione  }
    public void onSecondClickNpc(NPC npc) { /* completa missione  }
    public void uonSceltaOpzioneInterazione(OpzioniInterazioni scelta, NPC npc) { /* aggiorna affinità  }
    public void creaNPC() { /* missioni attive  }
    */
}