package main.model.character;

/**
 * The Stats class manages the statistics of the character and see if an action is possible or not
 */
public class Stats {
	
	// CONSTANTS --------------------------------------------------------------------------
	private static final int SLEEP_MAX_ENERGY = 70;
    private static final int SLEEP_MIN_HYGIENE = 30;

    private static final int EAT_MIN_ENERGY = 40;
    private static final int EAT_MIN_HYGIENE = 50;
    private static final int EAT_MIN_SATIETY = 85; 

    private static final int DRINK_MIN_HYDRATION = 70; 
    
    private static final int PLAY_MIN_ENERGY = 20;

    private static final int SHOWER_MIN_ENERGY = 20;
    private static final int SHOWER_MAX_HYGIENE = 70;

	private static final int MAX_STAT = 100;
	private static final int MIN_STAT = 0;
	private static final int CONTROL_STAT = 20;	
	private static final int STAT_DECAY = -2;
	
	// ATTRIBUTES ------------------------------------------------------------------------
	private int satiety;
	private int hygiene;
	private int energy;
	private int hydration;
	
	// CONSTRUCTOR ------------------------------------------------------------------------
	public Stats() {
		this.satiety = 100;
		this.hygiene = 100;
		this.energy = 100;
		this.hydration = 100;
	}
	
	// METHODS ---------------------------------------------------------------------------

	// ACTION CHECKERS --------------------------------------------------------
	/**
	 * Checks if the character can sleep based on current stats.
	 * @return
	 */
	public boolean canSleep() {
		boolean isCleanEnough = hygiene > SLEEP_MIN_HYGIENE;
		boolean isTiredEnough = energy < SLEEP_MAX_ENERGY;
		return isCleanEnough && isTiredEnough;
	}
	
	/**
	 * Checks if the character can eat based on current stats.
	 * @return
	 */
	public boolean canEat() {
		boolean isHungryEnough = satiety < EAT_MIN_SATIETY;
		boolean isCleanEnough = hygiene > EAT_MIN_HYGIENE;
		boolean isEnergeticEnough = energy > EAT_MIN_ENERGY;
		return isHungryEnough && isCleanEnough && isEnergeticEnough;
	}
	
	/**
	 * Checks if the character can drink based on current stats.
	 * @return
	 */
	public boolean canDrink() {
		return this.hydration < DRINK_MIN_HYDRATION;
	}
	 
	/**
	 * Checks if the character can shower based on current stats.
	 * @return
	 */
	public boolean canShower() {
		boolean hasEnoughEnergy =  energy > SHOWER_MIN_ENERGY;
		boolean isDirtyEnough =  hygiene < SHOWER_MAX_HYGIENE;
		return hasEnoughEnergy && isDirtyEnough;
	}
	
	/**
	 * Checks if the character can play based on current stats.
	 * @return
	 */
	public boolean canPlay() {
		return energy > PLAY_MIN_ENERGY;
	}
	
	// STAT CHANGERS --------------------------------------------------------
	/**
	 * Changes energy by delta amount.
	 * @param delta
	 */
	public void changeEnergy(int delta) {
        energy = clamp(energy + delta);
    }

	/**
	 * Changes satiety by delta amount.
	 * @param delta
	 */
    public void changeSatiety(int delta) {
        satiety = clamp(satiety + delta);
    }

	/**
	 * Changes hydration by delta amount.
	 * @param delta
	 */
    public void changeHydration(int delta) {
    	hydration = clamp(hydration + delta);
    }

	/**
	 * Changes hygiene by delta amount.
	 * @param delta
	 */
    public void changeHygiene(int delta) {
        hygiene = clamp(hygiene + delta);
    }
    
	// STATUS CHECKERS --------------------------------------------------------
    public boolean isExhausted() { return energy < CONTROL_STAT; }
    public boolean isStarving()  { return satiety < CONTROL_STAT; }
    public boolean isDehydrated(){ return hydration < CONTROL_STAT; }
    public boolean isDirty()     { return hygiene < CONTROL_STAT; }

	/**
	 * Checks if any stat has reached zero.
	 * @return
	 */
    public boolean isAnyStatZero() {
        return energy == 0 || satiety == 0 || hydration == 0 || hygiene == 0;
    }

	// GETTERS -----------------------------------------------------------------
    public int getEnergy() { return energy; }
    public int getSatiety() { return satiety; }
    public int getHydration() { return hydration; }
    public int getHygiene() { return hygiene; }
	public String getDeathCause() {
		if(energy == 0) return "You died of exhaustion.";
		if(satiety == 0) return "You died of starvation.";
		if(hydration == 0) return "You died of dehydration.";
		if(hygiene == 0) return "You died of infection.";
		return "Unknown cause.";
	}
    
	// HELPER METHODS -------------------------------------------------------

    /**
	 * Decreases all stats by a fixed decay amount.
	 */
    public void decay(){
    	changeEnergy(STAT_DECAY);
    	changeSatiety(STAT_DECAY);
    	changeHydration(STAT_DECAY);
    	changeHygiene(STAT_DECAY);
    }
    
	/**
	 * Clamps a stat value between MIN_STAT and MAX_STAT.
	 */
    private int clamp(int value) {
        return Math.max(MIN_STAT, Math.min(MAX_STAT, value));
    }
	
}
