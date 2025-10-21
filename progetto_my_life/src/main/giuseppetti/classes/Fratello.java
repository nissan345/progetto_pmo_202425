package main.giuseppetti.classes;


import main.aboufaris.interfaces.Stanza;

public class Fratello extends NPC {
    
    public Fratello(Stanza s) {
        super("Fratello", s);
    }

    @Override
    public String getDialogoIniziale() {
        return "Non mi dare fastidio";
    }

    @Override
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
    } 
    }

    @Override
    public String getDialogoCompletamentoMissione(Missione missione) {
        return "Grazie mille per avermi aiutato";
    }

    @Override
    protected void inizializzaMissioni() {
        Missione missioneCucina = new Missione("Cibo per tutti", 
                                   "Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera", 
                                   this, 
                                   20
                            );
        // Condizione
        missioneCucina.aggiungiCondizione(new CondizioneAzioneOggetto("Fornelli"));
        this.aggiungiMissione(missioneCucina);
    }


}
