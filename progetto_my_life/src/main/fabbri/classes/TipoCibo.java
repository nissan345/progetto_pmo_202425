/* Questo metodo poi lo dovremo spostare tra gli oggetti i think */

package main.fabbri.classes;

import main.neri.classes.RisultatoAzione;

public enum TipoCibo {
    // CIBI VEGANI 
    INSALATA("Insalata", "Insalata fresca", 5, 0, 5),
    HUMMUS("Hummus", "Hummus di ceci", 10, -5, 5),
    TOFU("Tofu", "Tofu alla griglia", 10, -5, 5),
    TORTA_VEGANA("Torta vegana", "Torta al cioccolato vegana", 15, 0, -5),

    // CIBI VEGETARIANI 
    YOGURT("Yogurt", "Yogurt bianco", 5, 0, 5),
    UOVA("Uova", "Uova strapazzate", 5, 0, 5),
    TORTA("Torta", "Torta alla frutta", 15, 0, -5),
    FORMAGGIO("Formaggio", "Formaggio stagionato", 5, -5, 5),

    // CIBI ONNIVORI 
    BISTECCA("Bistecca", "Bistecca alla griglia", 25, -10, 10),
    HAMBURGER("Hamburger", "Hamburger con patatine", 25, -10, 10 ),
    SALMONE("Salmone", "Salmone affumicato", 15, -10, 5),
    POLLO("Pollo", "Pollo arrosto", 25, -10, 10),
	
    // BEVANDE
	ACQUA("Acqua", "Dissetante e rinfrescante", 0, 30, 0),
    SUCCO("Succo di frutta", "Dolce e gustoso", 0, 20, 5),
    FANTA("Fanta", "Gustosa e frizzante", 0, 10, 10),
    CAFFE("Caffè", "Energizzante e amaro", 0, 5, 20);

    // ATTRIBUTI
    private final String nome;
    private final String descrizione;
    private final int fame;
	private final int sete;
    private final int energia;


    // COSTRUTTORE
    TipoCibo(String nome, String descrizione, int fame, int sete, int energia) {
        this.nome = nome;
        this.descrizione = descrizione; 
        this.fame = fame;
        this.sete = sete;
        this.energia = energia;
        
    }

    // GETTER
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public int getFame() { return fame; }
	public int getSete() { return sete; }
	public int getEnergia() { return energia; }

	
	public RisultatoAzione getRisultatoAzione() {
        return new RisultatoAzione(descrizione, fame, sete, energia, 0);
    }
}
