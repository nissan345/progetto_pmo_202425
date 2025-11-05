package main.model.character;

public enum Hair {
    // Hair types
    STRAIGHT_SHORT("Corti e lisci"),
    STRAIGHT_LONG("Lunghi e lisci"),
    WAVY_SHORT("Corti e mossi"),
    WAVY_LONG("Lunghi e mossi"),
    CURLY_SHORT("Corti e ricci"),
    CURLY_LONG("Lunghi e ricci");

    private final String name;

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

