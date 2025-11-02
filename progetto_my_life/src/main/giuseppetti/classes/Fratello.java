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
    public String getMissionAssegnataDialogo(Mission mission) {
        return "Ehii " + mission.getDescription() + ", non dirlo a mamma e papà mi raccomando";
    }
    
    @Override 
    public String getDialogoMissionInCorso(Mission mission) {
    	switch(mission.getName()) {
    	case "Cibo per tutti":
    		return "Stai preparando da mangiare? Ricorda di usare i fornelli!"; 
    	default:
    		return "Come sta andando con la mission? Torna da me quando hai finito!";
    } 
    }

    @Override
    public String getDialogoCompletamentoMission(Mission mission) {
        return "Grazie mille per avermi aiutato";
    }

    @Override
    protected void inizializzaMissioni() {
        Mission missionCucina = new Mission("Cibo per tutti", 
                                   "Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera", 
                                   this, 
                                   20, 
                                   Arrays.asList(new CondizioneAzioneOggetto("Fornelli"))
                            );
        this.addMission(missionCucina);
    }


}
