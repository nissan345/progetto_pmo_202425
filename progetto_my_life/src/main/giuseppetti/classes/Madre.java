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
public class Madre extends NPC {

    public Madre(Stanza s) {
        super("Madre", s);
    }

    @Override
    public String getDialogoIniziale() {
        return "Ciao tesoro! Come sta andando la tua giornata?";
    }

    @Override
    protected String getMissioneAssegnataDialogo(Missione missione) {
        return "Ottimo che tu voglia aiutare! " + missione.getDescrizione() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }

    @Override
    public String getReazioneCompletamentoMissione(Missione missione) {
        return "Grazie mille figliola! Hai fatto un ottimo lavoro." +
               "'!\nSei sempre così affidabile, sono orgogliosa di te!";
    }

    @Override
    protected void inizializzaMissioni() {
        Missione missioneAlbum = new Missione("Alla ricerca dell'album perduto", 
                                   "Dovresti riportarmi il vecchio album di famiglia che ho perduto da qualche parte in casa e riportamelo", 
                                                this
                            );
        Sottocriteri criterioAlbum = new Sottocriteri(Arrays.asList(
            new CriterioUsoOggetti((Arrays.asList("album")))
        ));
        missioneAlbum.aggiungiCriterio(criterioAlbum);
        aggiungiMissione(missioneAlbum);
    }


}