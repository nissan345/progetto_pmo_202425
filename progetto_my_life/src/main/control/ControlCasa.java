package main.control;

import main.aboufaris.classes.*;
import main.aboufaris.interfaces.*;
import main.fabbri.classes.*;
import main.neri.classe.*;

public class ControlCasa{

    private Casa casa;
    private GestoreAzioni gestoreAzioni;
    private Personaggio personaggio;

    public ControlCasa(GestoreAzioni g, Personaggio p){
        this.gestoreAzioni = g;
        this.personaggio = p;
        this.casa = new CasaImpl();
    }

    public void creaCasa(){
        
    } 

    public Stanza getStanza(String s){
        return casa.getStanza(s);
    }

   
   
}