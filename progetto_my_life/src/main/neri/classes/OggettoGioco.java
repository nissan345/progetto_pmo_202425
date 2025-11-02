package main.neri.classes;

import java.util.List;

import main.fabbri.classes.Personaggio;

/**
 * Classe base per tutti gli oggetti presenti nella casa
 */
public class OggettoGioco {
    protected final String nome;
    protected final String descrizione;
    public String messaggio;
    protected String stanza;
    private boolean interazioneSpeciale;
    private int deltaFame, deltaSete, deltaEnergia, deltaIgiene;
    
    public OggettoGioco(Builder builder) {
        this.nome = builder.nome;
        this.descrizione = builder.descrizione;
        this.stanza = builder.stanza;
        this.deltaEnergia = builder.deltaEnergia;
        this.deltaFame = builder.deltaFame;
        this.deltaIgiene = builder.deltaIgiene;
        this.deltaSete = builder.deltaSete;
        this.messaggio = builder.messaggio;
        this.interazioneSpeciale = builder.interazioneSpeciale;
    }
      
    public RisultatoAzione usa(Personaggio personaggio) {
    	return new RisultatoAzione(messaggio, deltaFame, deltaSete, 
                deltaEnergia, deltaIgiene);
    }
    
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public String getStanza() { return stanza; }
   
    @Override
    public String toString() {
        return nome + " (" + stanza + ")";
    }
    
    public boolean isInterazioneSpeciale() {
		return interazioneSpeciale;
	}
    
    // Metodi aggiuntivi 
    public boolean richiedeScelta() { return false; }

    public List<?> opzioniDisponibili(Personaggio p) { 
        return List.of(); 
    }

    public RisultatoAzione usa(Personaggio p, Object opzione) {
        return usa(p);
    }

	public static class Builder {
    	// Campi obbligatori
        private final String nome;
        private final String stanza;
        
        // Campi opzionali con valori di default
        private String descrizione = "";
        private String messaggio = "Usi l'oggetto.";
        private boolean interazioneSpeciale = false;
        private int deltaFame = 0;
        private int deltaSete = 0;
        private int deltaEnergia = 0;
        private int deltaIgiene = 0;
        
        // Costruttore con campi obbligatori
        public Builder(String nome, String stanza) {
            this.nome = nome;
            this.stanza = stanza;
        }
        
        // Metodi fluent (restituiscono this)
        public Builder descrizione(String val) {
            this.descrizione = val;
            return this;
        }
        
        public Builder messaggio(String val) {
            this.messaggio = val;
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

