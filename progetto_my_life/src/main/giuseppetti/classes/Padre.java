package main.giuseppetti.classes;

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
    public String getMissioneAssegnataDialogo(Missione missione) {
        return "Ottimo che tu voglia aiutare! " + missione.getDescrizione() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
    public String getDialogoMissioneInCorso(Missione missione) {
    	switch(missione.getNome()) {
    		case "Annaffia le piante":
    			return "Hai già Annaffiato le piante? Ricorda che l'Innaffiatoio si trova in giardino";
    		default:
    			return "Come sta andando con la missione? Torna da me quando hai finito!";
    	} 
    }

    @Override
    public String getDialogoCompletamentoMissione(Missione missione) {
        return "Grazie mille! Hai fatto un ottimo lavoro." +
               "'!\nSei sempre così affidabile, sono orgoglioso di te!";
    }

    protected void inizializzaMissioni() {
       
        Missione missionePiante = new Missione("Annaffia le piante", 
                                   "Dovresti annaffiare le piante", 
                                   this, 
                                   15
                            );
     // Condizione
        missionePiante.aggiungiCondizione(new CondizioneAzioneOggetto("Innaffiatoio"));
        this.aggiungiMissione(missionePiante);
    }

}