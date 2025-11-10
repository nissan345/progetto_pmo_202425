package main.model.character.enums;

/**
 * The Outfit enum represents different clothing styles for the main character.
 */

public enum Outfit {

    // ALTERNATIVES FOR CLOTHES ----------------------------------------------------------

    CASUAL("Informale", "Abbigliamento casual per tutti i giorni"),
    FORMAL("Formale", "Abbigliamento elegante per occasioni speciali"),
    SPORTY("Sportivo", "Abbigliamento comodo per attività fisica"),
    PAJAMA("Pigiama", "Abbigliamento per dormire"),
    SUMMERLY("Estivo", "Abbigliamento leggero per il caldo"),
    WINTERLY("Invernale", "Abbigliamento caldo per il freddo"); 


    // ATTRIBUTES -----------------------------------------------------------------------
    private final String name;
    private final String description;

    // CONSTRUCTOR ----------------------------------------------------------------------
    Outfit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // GETTER ----------------------------------------------------------------------------
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}