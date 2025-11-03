package main.neri.classes;

import java.util.List;
import main.fabbri.classes.MainCharacter;

/**
 * Classe base per tutti gli oggetti presenti nella casa
 */
public class OggettoGioco {
    protected final String name;
    protected final String description;
    public String messaggio;
    protected String room;
    private boolean interazioneSpeciale;
    private int deltaHunger, deltaThirst, deltaEnergy, deltaHygiene;
    
    public OggettoGioco(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.room = builder.room;
        this.deltaEnergy = builder.deltaEnergy;
        this.deltaHunger = builder.deltaHunger;
        this.deltaHygiene = builder.deltaHygiene;
        this.deltaThirst = builder.deltaThirst;
        this.messaggio = builder.messaggio;
        this.interazioneSpeciale = builder.interazioneSpeciale;
    }
      
    public RisultatoAzione usa(MainCharacter personaggio) {
    	return new RisultatoAzione(messaggio, deltaHunger, deltaThirst, 
                deltaEnergy, deltaHygiene);
    }
    
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getRoom() { return room; }
   
    @Override
    public String toString() {
        return name + " (" + room + ")";
    }
    
    public boolean isInterazioneSpeciale() {
		return interazioneSpeciale;
	}
    
    // Metodi aggiuntivi 
    public boolean richiedeScelta() { return false; }

    public List<?> opzioniDisponibili(MainCharacter p) { 
        return List.of(); 
    }

    public RisultatoAzione usa(MainCharacter p, Object opzione) {
        return usa(p);
    }

	public static class Builder {
    	// Campi obbligatori
        private final String name;
        private final String room;
        
        // Campi opzionali con valori di default
        private String description = "";
        private String messaggio = "Usi l'oggetto.";
        private boolean interazioneSpeciale = false;
        private int deltaHunger = 0;
        private int deltaThirst = 0;
        private int deltaEnergy = 0;
        private int deltaHygiene = 0;
        
        // Costruttore con campi obbligatori
        public Builder(String name, String room) {
            this.name = name;
            this.room = room;
        }
        
        // Metodi fluent (restituiscono this)
        public Builder description(String val) {
            this.description = val;
            return this;
        }
        
        public Builder message(String val) {
            this.message = val;
            return this;
        }
        
        public Builder hunger(int val) {
            this.deltaHunger = val;
            return this;
        }
        
        public Builder thirst(int val) {
            this.deltaThirst = val;
            return this;
        }
        
        public Builder energy(int val) {
            this.deltaEnergy = val;
            return this;
        }
        
        public Builder hygiene(int val) {
            this.deltaHygiene = val;
            return this;
        }
        
        public Builder isInterazioneSpeciale(boolean interazione) {
        	this.interazioneSpeciale = interazione;
        	return this;
        }
        
        // Metodo finale che costruisce l'oggetto
        public OggettoGioco build() {
            return new OggettoGioco(this);
        }
    }
}

