package main.neri.classes;

import main.fabbri.classes.Personaggio;

/**
 * Classe base per tutti gli oggetti presenti nella casa
 */
public abstract class OggettoGioco {
    protected String nome;
    protected String descrizione;
    protected boolean utilizzabile;
    protected String stanza;
    
    public OggettoGioco(String nome, String descrizione, String stanza) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.utilizzabile = true;
        this.stanza = stanza;
    }
    
    public abstract RisultatoAzione usa(Personaggio personaggio);
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public boolean isUtilizzabile() { return utilizzabile; }
    public String getStanza() { return stanza; }
    
   // public abstract RisultatoAzione usa(Personaggio personaggio);
    
    @Override
    public String toString() {
        return nome + " (" + stanza + ")";
    }
}

