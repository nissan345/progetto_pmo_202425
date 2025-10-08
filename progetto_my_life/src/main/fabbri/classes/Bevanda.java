package main.fabbri.classes;

public enum Bevanda {
    // ALTERNATIVE DI BEVANDE
    ACQUA("Acqua", "Dissetante e rinfrescante", 40, 0),
    SUCCO("Succo di frutta", "Dolce e gustoso", 30, 10),
    COLA("Coca-Cola", "Gustosa e frizzante", 25, 10),
    CAFFE("Caffè", "Energizzante e amaro", 10, 30);

    // ATTRIBUTI
    private final String nome;
    private final String descrizione;
    private final int dissetamento;
    private final int energiaBevanda;

    // COSTRUTTORE
    Bevanda(String nome, String descrizione, int dissetamento, int energiaBevanda) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dissetamento = dissetamento;
        this.energiaBevanda = energiaBevanda;
    }

    // GETTER
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public int getDissetamento() { return dissetamento; }
    public int getEnergiaBevanda() { return energiaBevanda; }
}
