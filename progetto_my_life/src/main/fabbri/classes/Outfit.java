package main.fabbri.classes;

/**
 * The Outfit enum represents different clothing styles for the main character.
 */

public enum Outfit {
<<<<<<< Updated upstream:progetto_my_life/src/main/fabbri/classes/Outfit.java
    // ALTERNATIVE DI VESTITI
=======
    // ALTERNATIVES FOR CLOTHES ----------------------------------------------------------
>>>>>>> Stashed changes:progetto_my_life/src/model/character/Outfit.java
    CASUAL("Informale", "Abbigliamento casual per tutti i giorni"),
    FORMAL("Formale", "Abbigliamento elegante per occasioni speciali"),
    SPORTY("Sportivo", "Abbigliamento comodo per attività fisica"),
    PAJAMA("Pigiama", "Abbigliamento per dormire"),
    SUMMERLY("Estivo", "Abbigliamento leggero per il caldo"),
    WINTERLY("Invernale", "Abbigliamento caldo per il freddo"); 

<<<<<<< Updated upstream:progetto_my_life/src/main/fabbri/classes/Outfit.java
    // ATTRIBUTI
    private final String name;
    private final String description;

    // COSTRUTTORE
=======
    // ATTRIBUTES -----------------------------------------------------------------------
    private final String name;
    private final String description;

    // CONSTRUCTOR ----------------------------------------------------------------------
>>>>>>> Stashed changes:progetto_my_life/src/model/character/Outfit.java
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
