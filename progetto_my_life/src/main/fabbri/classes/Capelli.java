package main.fabbri.classes;

public enum Capelli {
    // ALTERNATIVE DI CAPELLI
    CORTI_LISCI("Capelli corti e lisci", "Prattico e facile da gestire"),
    CORTI_MOSSI("Capelli corti e mossi", "Giovane e dinamico"),
    LUNGHI_LISCI("Capelli lunghi e lisci", "Elegante e fluente"),
    LUNGHI_MOSSI("Capelli lunghi e mossi", "Selvaggio e naturale"),
    RICCI_CORTI("Ricci corti", "Riccioli definiti e pratici"),
    RICCI_LUNGHI("Ricci lunghi", "Riccioli lunghi e voluminosi");

    // ATTRIBUTI
    private final String nome;
    private final String descrizione;

    // COSTRUTTORE
    Capelli(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // GETTER
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
}