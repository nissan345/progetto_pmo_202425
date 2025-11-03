package model.character;

public enum Hair {
    // ALTERNATIVE DI CAPELLI
    STRAIGHT_SHORT("Corti e lisci"),
    STRAIGHT_LONG("Lunghi e lisci"),
    WAVY_SHORT("Corti e mossi"),
    WAVY_LONG("Lunghi e mossi"),
    CURLY_SHORT("Corti e ricci"),
    CURLY_LONG("Lunghi e ricci");

    // ATTRIBUTI
    private final String name;

    // COSTRUTTORE
    Hair(String name) {
        this.name = name;
    }

    // GETTER
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
