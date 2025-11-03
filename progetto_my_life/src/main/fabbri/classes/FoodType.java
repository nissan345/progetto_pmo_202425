/* Questo metodo poi lo dovremo spostare tra gli oggetti i think */
/* Ma stai zitta */

package main.fabbri.classes;

import main.neri.classes.RisultatoAzione;

public enum FoodType {
    // CIBI VEGANI 
    SALAD("Insalata", "Insalata fresca", 5, 0, 5),
    HUMMUS("Hummus", "Hummus di ceci", 10, -5, 5),
    TOFU("Tofu", "Tofu alla griglia", 10, -5, 5),
    VEGAN_CAKE("Torta vegana", "Torta al cioccolato vegana", 15, 0, -5),

    // CIBI VEGETARIANI 
    YOGURT("Yogurt", "Yogurt bianco", 5, 0, 5),
    EGGS("Uova", "Uova strapazzate", 5, 0, 5),
    CAKE("Torta", "Torta alla frutta", 15, 0, -5),
    CHEESE("Formaggio", "Formaggio stagionato", 5, -5, 5),

    // CIBI ONNIVORI 
    STEAK("Bistecca", "Bistecca alla griglia", 25, -10, 10),
    HAMBURGER("Hamburger", "Hamburger con patatine", 25, -10, 10 ),
    SALMON("Salmone", "Salmone affumicato", 15, -10, 5),
    CHICKEN("Pollo", "Pollo arrosto", 25, -10, 10),
	
    // BEVANDE
	WATER("Acqua", "Dissetante e rinfrescante", 0, 30, 0),
    JUICE("Succo di frutta", "Dolce e gustoso", 0, 20, 5),
    FANTA("Fanta", "Gustosa e frizzante", 0, 10, 10),
    COFFEE("Caffè", "Energizzante e amaro", 0, 5, 20);

    // ATTRIBUTI
    private final String name;
    private final String description;
    private final int hunger;
	private final int thirst;
    private final int energy;


    // COSTRUTTORE
    FoodType(String name, String description, int hunger, int thirst, int energy) {
        this.name = name;
        this.description = description; 
        this.hunger = hunger;
        this.thirst = thirst;
        this.energy = energy;
        
    }

    // GETTER
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHunger() { return hunger; }
	public int getThirst() { return thirst; }
	public int getEnergy() { return energy; }

	
	public RisultatoAzione getRisultatoAzione() {
        return new RisultatoAzione(description, hunger, thirst, energy, 0);
    }
}
