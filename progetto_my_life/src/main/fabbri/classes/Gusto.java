package main.fabbri.classes;

public enum Gusto {
    // ALTERNATIVE DI GUSTI
    DOLCE("Dolce", "Ama i sapori zuccherini"),
    SALATO("Salato", "Ama i sapori salati");

    // ATTRIBUTI
    private final String nome;
    private final String descrizione;

    // COSTRUTTORE
    Gusto(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // GETTER
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
}