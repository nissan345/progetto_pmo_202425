package main.fabbri.classes;

public enum Vestito {
    // ALTERNATIVE DI VESTITI
    INFORMALE("Informale", "Abbigliamento casual per tutti i giorni"),
    FORMALE("Formale", "Abbigliamento elegante per occasioni speciali"),
    SPORTIVO("Sportivo", "Abbigliamento comodo per attività fisica"),
    PIGIAMA("Pigiama", "Abbigliamento per dormire"),
    ESTIVO("Estivo", "Abbigliamento leggero per il caldo"),
    INVERNALE("Invernale", "Abbigliamento caldo per il freddo"); 

    // ATTRIBUTI
    private final String nome;
    private final String descrizione;

    // COSTRUTTORE
    Vestito(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // GETTER
    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }

    @Override
    public String toString() {
        return nome + ": " + descrizione;
    }
}
