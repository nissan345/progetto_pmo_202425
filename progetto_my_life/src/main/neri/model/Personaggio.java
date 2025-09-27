package main.neri.model;


/**
 * Classe del Personaggio
 */
public class Personaggio {
    private String nome;
    private int livello;
    private int fame;      // 0-100
    private int sete;      // 0-100
    private int energia;   // 0-100
    private int igiene;    // 0-100
    
    public Personaggio(String nome) {
        this.nome = nome;
        this.livello = 1;
        this.fame = 50;
        this.sete = 50;
        this.energia = 80;
        this.igiene = 70;
    }
    
    // Getters
    public String getNome() { return nome; }
    public int getLivello() { return livello; }
    public int getFame() { return fame; }
    public int getSete() { return sete; }
    public int getEnergia() { return energia; }
    public int getIgiene() { return igiene; }
    
    // Metodo per applicare gli effetti delle azioni
    public void applicaEffetti(RisultatoAzione risultato) {
        this.fame = Math.max(0, Math.min(100, this.fame + risultato.getDeltaFame()));
        this.sete = Math.max(0, Math.min(100, this.sete + risultato.getDeltaSete()));
        this.energia = Math.max(0, Math.min(100, this.energia + risultato.getDeltaEnergia()));
        this.igiene = Math.max(0, Math.min(100, this.igiene + risultato.getDeltaIgiene()));
    }
    
    public String getStato() {
        return String.format(
            "%s (Liv.%d) - Fame: %d/100, Sete: %d/100, Energia: %d/100, Igiene: %d/100",
            nome, livello, fame, sete, energia, igiene
        );
    }
}

