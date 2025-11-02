package main.neri.classes;


/**
 * Risultato di un'azione con effetti sul personaggio
 */
public class RisultatoAzione {
    private String messaggio;
    private int deltaHunger;
    private int deltaThirst;
    private int deltaEnergy;
    private int deltaHygiene;
    private static final int TEMPO_AZIONE = 5; // Tutte le azioni durano 5 secondi
    
    public RisultatoAzione(String messaggio) {
        this(messaggio, 0, 0, 0, 0);
    } 
    
    public RisultatoAzione(String messaggio, int deltaHunger, int deltaThirst, int deltaEnergy, int deltaHygiene) {
        this.messaggio = messaggio;
        this.deltaHunger = deltaHunger;
        this.deltaThirst = deltaThirst;
        this.deltaEnergy = deltaEnergy;
        this.deltaHygiene = deltaHygiene;
    }
    
    // Getters
    public String getMessaggio() { return messaggio; }
    public int getDeltaHunger() { return deltaHunger; }
    public int getDeltaThirst() { return deltaThirst; }
    public int getDeltaEnergy() { return deltaEnergy; }
    public int getDeltaHygiene() { return deltaHygiene; }
    public int getTempoDurata() { return TEMPO_AZIONE; }
}

