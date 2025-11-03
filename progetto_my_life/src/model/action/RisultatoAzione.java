package model.action;


/**
 * Risultato di un'azione con effetti sul character
 */
public class RisultatoAzione {
    private String messaggio;
    private int deltaHunger;
    private int deltaHydration;
    private int deltaEnergy;
    private int deltaHygiene;

    private static final int TEMPO_AZIONE = 5; // Tutte le azioni durano 5 secondi
    
    public RisultatoAzione(String message) {
        this(message, 0, 0, 0, 0);
    } 
    

    public RisultatoAzione(String messaggio, int deltaHunger, int deltaHydration, int deltaEnergy, int deltaHygiene) {
        this.messaggio = messaggio;
        this.deltaHunger = deltaHunger;
        this.deltaHydration = deltaHydration;
        this.deltaEnergy = deltaEnergy;
        this.deltaHygiene = deltaHygiene;

    }
    
    // Getters
    public String getMessaggio() { return messaggio; }
    public int getDeltaSatiety() { return deltaHunger; }
    public int getDeltaHydration() { return deltaHydration; }
    public int getDeltaEnergy() { return deltaEnergy; }
    public int getDeltaHygiene() { return deltaHygiene; }
    public int getTempoDurata() { return TEMPO_AZIONE; }
}

