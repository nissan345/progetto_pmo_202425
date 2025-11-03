package main.giuseppetti.classes;

import java.util.Arrays;

import main.aboufaris.interfaces.Room;

public class Padre extends NPC {

    public Padre(Room s) {
        super("Padre", s);
    }
    
    @Override
    public String getDialogoIniziale() {
        return "Ciao tesoro! Come sta andando la tua avventura?";
    }

    @Override
<<<<<<< HEAD
    public String getMissioneAssegnataDialogo(Missione missione) {
        return "Ottimo che tu voglia aiutare! " + missione.getDescrizione() + 
=======
    public String getQuestAssegnataDialogo(Quest quest) {
        return "Ottimo che tu voglia aiutare! " + quest.getDescription() + 
>>>>>>> nicxole
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
<<<<<<< HEAD
    public String getDialogoMissioneInCorso(Missione missione) {
    	switch(missione.getNome()) {
=======
    public String getDialogoQuestInCorso(Quest quest) {
    	switch(quest.getName()) {
>>>>>>> nicxole
    		case "Annaffia le piante":
    			return "Hai già Annaffiato le piante? Ricorda che l'Innaffiatoio si trova in giardino";
    		case "Festa a sorpresa": 
    			return "Com'è andata con i preparativi per la festa a sorpresa? Ricordati di: pulire con l'aspirapolvere, cucinare ai fornelli e mettere la musica con lo stereo!";
    		default:
<<<<<<< HEAD
    			return "Come sta andando con la missione? Torna da me quando hai finito!";
=======
    			return "Come sta andando con la quest? Torna da me quando hai finito!";
>>>>>>> nicxole
    	} 
    }
    
    @Override
<<<<<<< HEAD
    public String getDialogoCompletamentoMissione(Missione missione) {
=======
    public String getDialogoCompletamentoQuest(Quest quest) {
>>>>>>> nicxole
        return "Grazie mille! Hai fatto un ottimo lavoro." +
               "'!\nSei sempre così affidabile, sono orgoglioso di te!";
    }

<<<<<<< HEAD
    protected void inizializzaMissioni() {
       
        Missione missionePiante = new Missione("Innaffia le piante", 
=======
    protected void inizializzaQuesti() {
       
        Quest questPiante = new Quest("Innaffia le piante", 
>>>>>>> nicxole
                                   "Dovresti innaffiare le piante", 
                                   this, 
                                   15, 
                                   Arrays.asList(new CondizioneAzioneOggetto("Innaffatoio"))
                            );
<<<<<<< HEAD
        this.aggiungiMissione(missionePiante);
        
        Missione missioneFestaSorpresa = new Missione("Festa a sorpresa", 
=======
        this.addQuest(questPiante);
        
        Quest questFestaSorpresa = new Quest("Festa a sorpresa", 
>>>>>>> nicxole
        											  "Dovresti preparare una festa a sorpresa per la mamma, fai delle pulizie in casa e metti della musica in Salotto", 
        											  this, 
        											  30, 
        											  Arrays.asList(new CondizioneAzioneOggetto("Aspirapolvere"), 
        													        new CondizioneAzioneOggetto("Fornelli"), 
        													        new CondizioneAzioneOggetto("Stereo e vinili"))
        		);
<<<<<<< HEAD
        this.aggiungiMissione(missioneFestaSorpresa);
=======
        this.addQuest(questFestaSorpresa);
>>>>>>> nicxole
    }

}