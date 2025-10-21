/* Questo metodo poi lo dovremo spostare tra gli oggetti i think */

package main.fabbri.classes;

public enum TipoCibo {
    // CIBI VEGANI (livello 0)
    INSALATA("Insalata", "Insalata fresca", Dieta.VEGANO, Gusto.SALATO, 25),
    HUMMUS("Hummus", "Hummus di ceci", Dieta.VEGANO, Gusto.SALATO, 30),
    TOFU("Tofu", "Tofu alla griglia", Dieta.VEGANO, Gusto.SALATO, 32),
    TORTA_VEGANA("Torta vegana", "Torta al cioccolato vegana", Dieta.VEGANO, Gusto.DOLCE, 28),

    // CIBI VEGETARIANI (livello 1)
    YOGURT("Yogurt", "Yogurt bianco", Dieta.VEGETARIANO, Gusto.DOLCE, 20),
    UOVA("Uova", "Uova strapazzate", Dieta.VEGETARIANO, Gusto.SALATO, 30),
    TORTA("Torta", "Torta alla frutta", Dieta.VEGETARIANO, Gusto.DOLCE, 25),
    FORMAGGIO("Formaggio", "Formaggio stagionato", Dieta.VEGETARIANO, Gusto.SALATO, 28),

    // CIBI ONNIVORI (livello 2)
    BISTECCA("Bistecca", "Bistecca alla griglia", Dieta.ONNIVORO, Gusto.SALATO, 40),
    HAMBURGER("Hamburger", "Hamburger con patatine", Dieta.ONNIVORO, Gusto.SALATO, 35),
    SALMONE("Salmone", "Salmone affumicato", Dieta.ONNIVORO, Gusto.SALATO, 38),
    POLLO("Pollo", "Pollo arrosto", Dieta.ONNIVORO, Gusto.SALATO, 36);

    // ATTRIBUTI
    private final String nome;
    private final String descrizione;
    private final Dieta dietaMinima;
    private final Gusto gusto;
    private final int valoreNutritivo;

    // COSTRUTTORE
    TipoCibo(String nome, String descrizione, Dieta dietaMinima, Gusto gusto, int valoreNutritivo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dietaMinima = dietaMinima;
        this.gusto = gusto;
        this.valoreNutritivo = valoreNutritivo;
    }

    // GETTER
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public Dieta getDietaMinima() { return dietaMinima; }
    public Gusto getGusto() { return gusto; }
    public int getValoreNutritivo() { return valoreNutritivo; }
}
