package main.fabbri.classes;

public enum Dieta {
    // ALTERNATIVE DI DIETA
    ONNIVORO(2, "Onnivoro", "Mangia di tutto"),
    VEGETARIANO(1, "Vegetariano", "Non mangia carne e pesce"),
    VEGANO(0, "Vegano", "Non mangia prodotti di origine animale");

    // ATTRIBUTI
    private final int livello;
    private final String nome;
    private final String descrizione;

    // COSTRUTTORE
    Dieta(int livello, String nome, String descrizione) {
        this.livello = livello;
        this.nome = nome;
        this.descrizione = descrizione;
    }
 
    // GETTER
    public int getLivello() { return livello; }
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }

    // METODO PER VERIFICARE SE UN CIBO PUO' ESSERE MANGIATO
    public boolean puoMangiare(Dieta dietaMinimaCibo) {
        return this.livello >= dietaMinimaCibo.getLivello();
    }
}
