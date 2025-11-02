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
    public String getMissioneAssegnataDialogo(Missione missione) {
        return "Ottimo che tu voglia aiutare! " + missione.getDescrizione() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
    public String getDialogoMissioneInCorso(Missione missione) {
    	switch(missione.getNome()) {
    		case "Annaffia le piante":
    			return "Hai già Annaffiato le piante? Ricorda che l'Innaffiatoio si trova in giardino";
    		case "Festa a sorpresa": 
    			return "Com'è andata con i preparativi per la festa a sorpresa? Ricordati di: pulire con l'aspirapolvere, cucinare ai fornelli e mettere la musica con lo stereo!";
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
       
        Missione missionePiante = new Missione("Innaffia le piante", 
                                   "Dovresti innaffiare le piante", 
                                   this, 
                                   15, 
                                   Arrays.asList(new CondizioneAzioneOggetto("Innaffatoio"))
                            );
        this.aggiungiMissione(missionePiante);
        
        Missione missioneFestaSorpresa = new Missione("Festa a sorpresa", 
        											  "Dovresti preparare una festa a sorpresa per la mamma, fai delle pulizie in casa e metti della musica in Salotto", 
        											  this, 
        											  30, 
        											  Arrays.asList(new CondizioneAzioneOggetto("Aspirapolvere"), 
        													        new CondizioneAzioneOggetto("Fornelli"), 
        													        new CondizioneAzioneOggetto("Stereo e vinili"))
        		);
        this.aggiungiMissione(missioneFestaSorpresa);
    }

}