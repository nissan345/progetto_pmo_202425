package main.neri.classes;


/**
 * Risultato di un'azione con effetti sul personaggio
 */
public class RisultatoAzione {
    private String messaggio;
    private int deltaFame;
    private int deltaSete;
    private int deltaEnergia;
    private int deltaIgiene;
    private static final int TEMPO_AZIONE = 5; // Tutte le azioni durano 5 secondi
    
    public RisultatoAzione(String messaggio) {
        this(messaggio, 0, 0, 0, 0);
    }
    
    public RisultatoAzione(String messaggio, int deltaFame, int deltaSete, int deltaEnergia, int deltaIgiene) {
        this.messaggio = messaggio;
        this.deltaFame = deltaFame;
        this.deltaSete = deltaSete;
        this.deltaEnergia = deltaEnergia;
        this.deltaIgiene = deltaIgiene;
    }
    
    // Getters
    public String getMessaggio() { return messaggio; }
    public int getDeltaFame() { return deltaFame; }
    public int getDeltaSete() { return deltaSete; }
    public int getDeltaEnergia() { return deltaEnergia; }
    public int getDeltaIgiene() { return deltaIgiene; }
    public int getTempoDurata() { return TEMPO_AZIONE; }
}

