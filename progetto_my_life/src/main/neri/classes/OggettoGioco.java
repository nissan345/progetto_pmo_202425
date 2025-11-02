package main.neri.classes;

import java.util.List;

import main.fabbri.classes.Character;

/**
 * Classe base per tutti gli oggetti presenti nella casa
 */
public class OggettoGioco {
    protected final String nome;
    protected final String descrizione;
    public String message;
    protected String room;
    private boolean interazioneSpeciale;
    private int deltaFame, deltaSete, deltaEnergia, deltaIgiene;
    
    public OggettoGioco(Builder builder) {
        this.nome = builder.nome;
        this.descrizione = builder.descrizione;
        this.room = builder.room;
        this.deltaEnergia = builder.deltaEnergia;
        this.deltaFame = builder.deltaFame;
        this.deltaIgiene = builder.deltaIgiene;
        this.deltaSete = builder.deltaSete;
        this.message = builder.message;
        this.interazioneSpeciale = builder.interazioneSpeciale;
    }
      
    public RisultatoAzione usa(Character character) {
    	return new RisultatoAzione(message, deltaFame, deltaSete, 
                deltaEnergia, deltaIgiene);
    }
    
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public String getStanza() { return room; }
   
    @Override
    public String toString() {
        return nome + " (" + room + ")";
    }
    
    public boolean isInterazioneSpeciale() {
		return interazioneSpeciale;
	}
    
    // Metodi aggiuntivi 
    public boolean richiedeScelta() { return false; }

    public List<?> opzioniDisponibili(Character p) { 
        return List.of(); 
    }

    public RisultatoAzione usa(Character p, Object opzione) {
        return usa(p);
    }

	public static class Builder {
    	// Campi obbligatori
        private final String nome;
        private final String room;
        
        // Campi opzionali con valori di default
        private String descrizione = "";
        private String message = "Usi l'oggetto.";
        private boolean interazioneSpeciale = false;
        private int deltaFame = 0;
        private int deltaSete = 0;
        private int deltaEnergia = 0;
        private int deltaIgiene = 0;
        
        // Costruttore con campi obbligatori
        public Builder(String nome, String room) {
            this.nome = nome;
            this.room = room;
        }
        
        // Metodi fluent (restituiscono this)
        public Builder descrizione(String val) {
            this.descrizione = val;
            return this;
        }
        
        public Builder message(String val) {
            this.message = val;
            return this;
        }
        
        public Builder fame(int val) {
            this.deltaFame = val;
            return this;
        }
        
        public Builder sete(int val) {
            this.deltaSete = val;
            return this;
        }
        
        public Builder energia(int val) {
            this.deltaEnergia = val;
            return this;
        }
        
        public Builder igiene(int val) {
            this.deltaIgiene = val;
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

