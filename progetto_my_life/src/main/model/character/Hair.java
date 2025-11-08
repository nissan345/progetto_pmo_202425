package main.model.character;


/**
 * The Hair enum represents different hairstyles for the main character.
*/

public enum Hair {
    // HAIR TYPES ----------------------------------------------------------------
    STRAIGHT_SHORT("Corti e lisci"),
    STRAIGHT_LONG("Lunghi e lisci"),
    WAVY_SHORT("Corti e mossi"),
    WAVY_LONG("Lunghi e mossi"),
    CURLY_SHORT("Corti e ricci"),
    CURLY_LONG("Lunghi e ricci");

    // ATTRIBUTE ----------------------------------------------------------------
    private final String name;

    // CONSTRUCTOR -------------------------------------------------------------
    Hair(String name) {
        this.name = name;
    }

    // GETTER ----------------------------------------------------------------
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}