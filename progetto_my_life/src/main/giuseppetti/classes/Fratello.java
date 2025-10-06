/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

import java.util.Arrays;

import main.aboufaris.interfaces.Stanza;

/**
 *
 * @author OS
 */
public class Fratello extends NPC {
    
    public Fratello(Stanza s) {
        super("Fratello", s);
    }

    @Override
    public String getDialogoIniziale() {
        return "Non mi dare fastidio";
    }

    @Override
    protected String getMissioneAssegnataDialogo(Missione missione) {
        return "Ehii " + missione.getDescrizione() + ", non dirlo a mamma e papà mi raccomando";
    }

    @Override
    public String getReazioneCompletamentoMissione(Missione missione) {
        return "Grazie mille, tieni 5 euro per avermi aiutato";
    }

    @Override
    protected void inizializzaMissioni() {
        Missione missioneCucina = new Missione("Cibo per tutti", 
                                   "Dei miei amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera", 
                                                this
                            );
        Sottocriteri criterioCucina = new Sottocriteri(Arrays.asList(
            new CriterioUsoOggetti((Arrays.asList("Fornelli"))),
            new CriterioAzioneCompletata("cucinare")
        ));

        missioneCucina.aggiungiCriterio(criterioCucina);
        aggiungiMissione(missioneCucina);
    }


}