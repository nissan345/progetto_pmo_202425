package main.model.character;

/**
 * The Stats class manages the statistics of the character and see if an action is possible or not
 */
public class Stats {
	
	private static final int SLEEP_MAX_ENERGY = 70;
    private static final int SLEEP_MIN_HYGIENE = 30;

    private static final int COOK_MIN_ENERGY = 40;
    private static final int COOK_MIN_HYGIENE = 50;
    private static final int COOK_MIN_SATIETY = 40; 

    private static final int DRINK_MIN_HYDRATION = 70; 
    
    private static final int PLAY_MIN_ENERGY = 20;

    private static final int SHOWER_MIN_ENERGY = 20;
    private static final int SHOWER_MAX_HYGIENE = 70;

	private static final int MAX_STAT = 100;
	private static final int MIN_STAT = 0;
	private static final int CONTROL_STAT = 20;	
	private static final int STAT_DECAY = -2;
	
	private int satiety;
	private int hygiene;
	private int energy;
	private int hydration;
	
	
	public Stats() {
		this.satiety = 100;
		this.hygiene = 100;
		this.energy = 100;
		this.hydration = 100;
	}
	
	public boolean canSleep() {
		boolean isCleanEnough = hygiene > SLEEP_MIN_HYGIENE;
		boolean isTiredEnough = energy < SLEEP_MAX_ENERGY;
		return isCleanEnough && isTiredEnough;
	}
	
	public boolean canCook() {
		boolean isHungryEnough = satiety < COOK_MIN_SATIETY;
		boolean isCleanEnough = hygiene > COOK_MIN_HYGIENE;
		boolean isEnergeticEnough = energy > COOK_MIN_ENERGY;
		return isHungryEnough && isCleanEnough && isEnergeticEnough;
	}
	
	public boolean canDrink() {
		return this.hydration < DRINK_MIN_HYDRATION;
	}
	
	public boolean canShower() {
		boolean hasEnoughEnergy =  energy > SHOWER_MIN_ENERGY;
		boolean isDirtyEnough =  hygiene < SHOWER_MAX_HYGIENE;
		return hasEnoughEnergy && isDirtyEnough;
	}
	
	public boolean canPlay() {
		return energy > PLAY_MIN_ENERGY;
	}
	
	public void changeEnergy(int delta) {
        energy = clamp(energy + delta);
    }

    public void changeSatiety(int delta) {
        satiety = clamp(satiety + delta);
    }

    public void changeHydration(int delta) {
    	hydration = clamp(hydration + delta);
    }

    public void changeHygiene(int delta) {
        hygiene = clamp(hygiene + delta);
    }
    
    public boolean isExhausted() { return energy < CONTROL_STAT; }
    public boolean isStarving()  { return satiety < CONTROL_STAT; }
    public boolean isDehydrated(){ return hydration < CONTROL_STAT; }
    public boolean isDirty()     { return hygiene < CONTROL_STAT; }

    private int clamp(int value) {
        return Math.max(MIN_STAT, Math.min(MAX_STAT, value));
    }

    public int getEnergy() { return energy; }
    public int getSatiety() { return satiety; }
    public int getHydration() { return hydration; }
    public int getHygiene() { return hygiene; }
    
    
    public void decay(){
    	changeEnergy(STAT_DECAY);
    	changeSatiety(STAT_DECAY);
    	changeHydration(STAT_DECAY);
    	changeHygiene(STAT_DECAY);
    }
    
}
