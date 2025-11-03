package main.giuseppetti.classes;


import java.util.Arrays;

import main.aboufaris.interfaces.Room;

public class Fratello extends NPC {
    
    public Fratello(Room s) {
        super("Fratello", s);
    }
    
    @Override
    public String getDialogoIniziale() {
        return "Non mi dare fastidio";
    }

    @Override
<<<<<<< HEAD
    public String getMissioneAssegnataDialogo(Missione missione) {
        return "Ehii " + missione.getDescrizione() + ", non dirlo a mamma e papà mi raccomando";
    }
    
    @Override 
    public String getDialogoMissioneInCorso(Missione missione) {
    	switch(missione.getNome()) {
    	case "Cibo per tutti":
    		return "Stai preparando da mangiare? Ricorda di usare i fornelli!"; 
    	default:
    		return "Come sta andando con la missione? Torna da me quando hai finito!";
=======
    public String getQuestAssegnataDialogo(Quest quest) {
        return "Ehii " + quest.getDescription() + ", non dirlo a mamma e papà mi raccomando";
    }
    
    @Override 
    public String getDialogoQuestInCorso(Quest quest) {
    	switch(quest.getName()) {
    	case "Cibo per tutti":
    		return "Stai preparando da mangiare? Ricorda di usare i fornelli!"; 
    	default:
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
        return "Grazie mille per avermi aiutato";
    }

    @Override
<<<<<<< HEAD
    protected void inizializzaMissioni() {
        Missione missioneCucina = new Missione("Cibo per tutti", 
=======
    protected void inizializzaQuesti() {
        Quest questCucina = new Quest("Cibo per tutti", 
>>>>>>> nicxole
                                   "Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera", 
                                   this, 
                                   20, 
                                   Arrays.asList(new CondizioneAzioneOggetto("Fornelli"))
                            );
<<<<<<< HEAD
        this.aggiungiMissione(missioneCucina);
=======
        this.addQuest(questCucina);
>>>>>>> nicxole
    }


}
