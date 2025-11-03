package main.fabbri.classes;

public enum Outfit {
    // ALTERNATIVE DI VESTITI
    CASUAL("Informale", "Abbigliamento casual per tutti i giorni"),
    FORMAL("Formale", "Abbigliamento elegante per occasioni speciali"),
    SPORTY("Sportivo", "Abbigliamento comodo per attività fisica"),
    PAJAMA("Pigiama", "Abbigliamento per dormire"),
    SUMMERLY("Estivo", "Abbigliamento leggero per il caldo"),
    WINTERLY("Invernale", "Abbigliamento caldo per il freddo"); 

    // ATTRIBUTI
    private final String name;
    private final String description;

    // COSTRUTTORE
    Outfit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // GETTER
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}