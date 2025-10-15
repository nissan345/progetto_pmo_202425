package main.giuseppetti.classes;

import java.util.Arrays;

import main.aboufaris.interfaces.Stanza;

public class Padre extends NPC {

    public Padre(Stanza s) {
        super("Padre", s);
    }

    @Override
    public String getDialogoIniziale() {
        return "Ciao tesoro! Come sta andando la tua avventura?";
    }

    @Override
    protected String getMissioneAssegnataDialogo(Missione missione) {
        return "Ottimo che tu voglia aiutare! " + missione.getDescrizione() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }

    @Override
    public String getReazioneCompletamentoMissione(Missione missione) {
        return "Grazie mille! Hai fatto un ottimo lavoro." +
               "'!\nSei sempre così affidabile, sono orgoglioso di te!";
    }

    protected void inizializzaMissioni() {
       
        Missione missionePiante = new Missione("Innaffia le piante", 
                                   "Dovresti annaffiare le piante", 
                                                this
                            );
        Sottocriteri criterioPiante = new Sottocriteri(Arrays.asList(
            new CriterioUsoOggetti((Arrays.asList("Innaffiatoio"))),
            new CriterioAzioneCompletata("usa")
        ));
        missionePiante.aggiungiCriterio(criterioPiante);
        aggiungiMissione(missionePiante);
    }

}