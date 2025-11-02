package main.giuseppetti.classes;

import java.util.Arrays;

import main.aboufaris.interfaces.Room;

public class Madre extends NPC {

    public Madre(Room s) {
        super("Madre", s);
    }
    
    @Override
    public String getDialogoIniziale() {
        return "Ciao tesoro! Come sta andando la tua giornata?";
    }

    @Override
    public String getMissioneAssegnataDialogo(Missione missione) {
        return "Ottimo che tu voglia aiutare! " + missione.getDescrizione() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
    public String getDialogoMissioneInCorso(Missione missione) {
    	switch(missione.getNome()) {
        	case "L'album perduto":
        		return "Hai trovato l'album? Guarda bene nel salotto, dovrebbe essere da qualche parte!";
        	default:
        		return "Come sta andando con la missione? Torna da me quando hai finito!";
        } 
    }
    
    @Override
    public String getDialogoCompletamentoMissione(Missione missione) {
        return "Grazie mille! Hai fatto un ottimo lavoro." +
               "'!\nSei sempre così affidabile, sono orgoglioso di te!";
    }

    @Override
    protected void inizializzaMissioni() {
        Missione missioneAlbum = new Missione("L'album perduto", 
                                   "Dovresti riportarmi il vecchio album di famiglia che ho perduto da qualche parte in casa e riportamelo", 
                                   this,
                                   15, 
                                   Arrays.asList(new CondizioneAzioneOggetto("Album"))
                            );
        this.aggiungiMissione(missioneAlbum);
    }


}