package main.neri.classes;


/**
 * Risultato di un'azione con effetti sul personaggio
 */
public class RisultatoAzione {
    private String messaggio;
<<<<<<< HEAD
    private int deltaFame;
    private int deltaSete;
    private int deltaEnergia;
    private int deltaIgiene;
=======
    private int deltaHunger;
    private int deltaThirst;
    private int deltaEnergy;
    private int deltaHygiene;
>>>>>>> nicxole
    private static final int TEMPO_AZIONE = 5; // Tutte le azioni durano 5 secondi
    
    public RisultatoAzione(String messaggio) {
        this(messaggio, 0, 0, 0, 0);
    } 
    
<<<<<<< HEAD
    public RisultatoAzione(String messaggio, int deltaFame, int deltaSete, int deltaEnergia, int deltaIgiene) {
        this.messaggio = messaggio;
        this.deltaFame = deltaFame;
        this.deltaSete = deltaSete;
        this.deltaEnergia = deltaEnergia;
        this.deltaIgiene = deltaIgiene;
=======
    public RisultatoAzione(String messaggio, int deltaHunger, int deltaThirst, int deltaEnergy, int deltaHygiene) {
        this.messaggio = messaggio;
        this.deltaHunger = deltaHunger;
        this.deltaThirst = deltaThirst;
        this.deltaEnergy = deltaEnergy;
        this.deltaHygiene = deltaHygiene;
>>>>>>> nicxole
    }
    
    // Getters
    public String getMessaggio() { return messaggio; }
<<<<<<< HEAD
    public int getDeltaFame() { return deltaFame; }
    public int getDeltaSete() { return deltaSete; }
    public int getDeltaEnergia() { return deltaEnergia; }
    public int getDeltaIgiene() { return deltaIgiene; }
=======
    public int getDeltaHunger() { return deltaHunger; }
    public int getDeltaThirst() { return deltaThirst; }
    public int getDeltaEnergy() { return deltaEnergy; }
    public int getDeltaHygiene() { return deltaHygiene; }
>>>>>>> nicxole
    public int getTempoDurata() { return TEMPO_AZIONE; }
}

