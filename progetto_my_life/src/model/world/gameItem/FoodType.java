package model.world.gameItem;

import model.action.ActionResult;

public enum FoodType {
  
    SALAD("Insalata", "Insalata fresca", 5, 0, 5),
    HUMMUS("Hummus", "Hummus di ceci", 10, -5, 5),
    TOFU("Tofu", "Tofu alla griglia", 10, -5, 5),
    VEGAN_CAKE("Torta vegana", "Torta al cioccolato vegana", 15, 0, -5),

    YOGURT("Yogurt", "Yogurt bianco", 5, 0, 5),
    EGGS("Uova", "Uova strapazzate", 5, 0, 5),
    CAKE("Torta", "Torta alla frutta", 15, 0, -5),
    CHEESE("Formaggio", "Formaggio stagionato", 5, -5, 5),

    STEAK("Bistecca", "Bistecca alla griglia", 25, -10, 10),
    HAMBURGER("Hamburger", "Hamburger con patatine", 25, -10, 10 ),
    SALMON("Salmone", "Salmone affumicato", 15, -10, 5),
    CHICKEN("Pollo", "Pollo arrosto", 25, -10, 10),

	WATER("Acqua", "Dissetante e rinfrescante", 0, 30, 0),
    JUICE("Succo di frutta", "Dolce e gustoso", 0, 20, 5),
    FANTA("Fanta", "Gustosa e frizzante", 0, 10, 10),
    COFFEE("Caffè", "Energizzante e amaro", 0, 5, 20);

    // ATTRIBUTES
    private final String name;
    private final String description;
    private final int satiety;
	private final int hydration;
    private final int energy;


    // CONSTRUCTOR
    FoodType(String name, String description, int satiety, int hydration, int energy) {
        this.name = name;
        this.description = description; 
        this.satiety = satiety;
        this.hydration = hydration;
        this.energy = energy;
        
    }

    // GETTER
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSatiety() { return satiety; }
	public int getHydration() { return hydration; }
	public int getEnergy() { return energy; }

	
	public ActionResult getActionResult() {
        return new ActionResult(description, satiety, hydration, energy, 0);
    }
}
