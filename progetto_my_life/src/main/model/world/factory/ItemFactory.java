package main.model.world.factory;

import java.util.*;

import main.model.action.ActionResult;
import main.model.requirement.*;
import main.model.world.Room;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Refrigerator;

     

public class ItemFactory { 
	
	private static final int ENERGY_LOW = 40;
	private static final int ENERGY_MID = 80;
	
	private static final int HYGIENE_LOW = 40;
	private static final int HYGIENE_MID = 80;
	
	private static final int HIGH_HYGIENE_GAIN = 30;
	private static final int MID_HYGIENE_GAIN = 20;
	private static final int LOW_HYGIENE_GAIN = 10;
	
	private static final int LOW_DURATION = 2;
	private static final int MID_DURATION = 5;
	private static final int HIGH_DURATION = 10;
	
	private static final int HIGH_ENERGY_GAIN = 30;
	private static final int MID_ENERGY_GAIN = 20;
	private static final int LOW_ENERGY_GAIN = 10;
	
	private static final int LOW_ENERGY_COST = -5;
	private static final int MID_ENERGY_COST = -10;
	private static final int HIGH_ENERGY_COST = -20;
	
	private static final int BIG_SIZE = 80;
	private static final int MID_SIZE = 40;
	private static final int NORMAL_SIZE = 20;
	private static final int SMALL_SIZE = 10;
	
   
    // Bedroom
    public static Room createBedroom() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Letto", "Camera da Letto", BIG_SIZE)
                    .message("Ti sdrai e ti riposi")
                    .dynamic((mc, self) -> {
                        int en = mc.getStats().getEnergy();
                        int gain = (en < ENERGY_LOW) ? HIGH_ENERGY_GAIN : (en < ENERGY_MID ? MID_ENERGY_GAIN : LOW_ENERGY_GAIN);
                        int duration = (en < ENERGY_LOW) ? HIGH_DURATION : (en < ENERGY_MID ? MID_DURATION : LOW_DURATION);
                        int hyg = -10;
                        return new ActionResult(self.message, 0, 0, gain, hyg, duration);
                    })
                    .requirement(new CanSleepRequirement())
                    .build(), 
                    
                new GameItem.Builder("Computer", "Camera da Letto", NORMAL_SIZE)
                    .message("Giochi al pc.")
                    .dynamic((mc, self) -> {
                        int en = mc.getStats().getEnergy();
                        int energyCost  = (en < ENERGY_LOW) ? HIGH_ENERGY_COST : (en < ENERGY_MID ? MID_ENERGY_COST : LOW_ENERGY_COST);
                        int durationSec = (en < ENERGY_LOW) ? HIGH_DURATION : (en < ENERGY_MID ? MID_DURATION : LOW_DURATION); // stanco = più lento
                        int hygieneCost = -5;
                        int satCost     = -5;
                        int hydCost     = -10;
                        return new ActionResult(self.message, satCost, hydCost, energyCost, hygieneCost, durationSec);
                    })
                    .requirement(new CanPlayRequirement())
                    .build(),
                    
                new GameItem.Builder("Armadio", "Camera da Letto", BIG_SIZE)
                    .message("Provi dei nuovi outfit!")
                    .energy(-10)
                    .hygiene(20)
                    .build()
        ));
        return new Room("Camera Da Letto", items);
    }
    
    // Kitchen
    public static Room createKitchen() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Fornelli", "Cucina", BIG_SIZE)
                        .message("Cuoci un buon pasto.")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int energyCost   = (en < ENERGY_LOW) ? HIGH_ENERGY_COST : (en < ENERGY_MID ? MID_ENERGY_COST : LOW_ENERGY_COST); 
                            int durationSec  = (en < ENERGY_LOW) ?  HIGH_DURATION : (en < ENERGY_MID ?  MID_DURATION :   LOW_DURATION);  
                            int satietyGain  = 20;
                            int hygieneCost  = -15;
                            return new ActionResult(self.message, satietyGain, 0, energyCost, hygieneCost, durationSec);
                        })
                        .requirement(new CanCookRequirement())
                        .build(),
                        
                new Refrigerator(),
                
                new GameItem.Builder("Lavandino", "Cucina", BIG_SIZE)
                        .message("Lavi i piatti")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int energyCost  = (en < ENERGY_LOW) ? HIGH_ENERGY_COST : (en < ENERGY_MID ? MID_ENERGY_COST : LOW_ENERGY_COST);   
                            int durationSec = (en < ENERGY_LOW) ? HIGH_DURATION : (en < ENERGY_MID ? MID_DURATION :  LOW_DURATION);
                            int hygieneCost = -10;
                            return new ActionResult(self.message, 0, 0, energyCost, hygieneCost, durationSec);
                        })
                        .build()
        ));	
        return new Room("Cucina", items);
    }
   
    // Bagno
    public static Room createBathroom() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Doccia", "Bagno", BIG_SIZE)
                        .message("You take a refreshing shower.")
                        .dynamic((mc, self) -> {
                            int currentHyg   = mc.getStats().getHygiene();
                            int hygieneGain  = (currentHyg < HYGIENE_LOW) ? HIGH_HYGIENE_GAIN : (currentHyg < HYGIENE_MID  ? MID_HYGIENE_GAIN : LOW_HYGIENE_GAIN);
                            int durationSec  = (currentHyg < HYGIENE_LOW) ? HIGH_DURATION : (currentHyg <  HYGIENE_MID ? MID_DURATION : LOW_DURATION);
                            int energyCost   = -5; // fare la doccia consuma un filo
                            return new ActionResult(self.message, 0, 0, energyCost, hygieneGain, durationSec);
                        })
                        .requirement(new CanShowerRequirement())
                        .build(),
                
                new GameItem.Builder("Toilet", "Bagno", BIG_SIZE)
                        .message("You feel relieved after using the Bagno.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(5)
                        .energy(5)
                        .build(),
                
                new GameItem.Builder("Washing Machine", "Bagno", BIG_SIZE)
                        .message("You put your clothes in the washing machine. The house is tidier!")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-10)
                        .build()
        ));
        return new Room("Bagno", items);
    }
    
    // Salotto
    public static Room createLivingRoom() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Televisione", "Salotto", MID_SIZE)
                        .message("You watch TV and relax.")
                        .satiety(-5)
                        .hydration(-5)
                        .hygiene(-5)
                        .energy(10)
                        .build(),
        
                new GameItem.Builder("Stereo", "Salotto", NORMAL_SIZE)
                        .message("You listen to Billie Eilish.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(10)
                        .build(),
        
                new GameItem.Builder("Sofa", "Salotto", BIG_SIZE)
                        .message("You sit on the sofa and rest a bit.")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int gain = (en < ENERGY_LOW) ? HIGH_ENERGY_GAIN : LOW_ENERGY_GAIN;  
                            return new ActionResult(self.message, 0, 0, gain, 0, 5);
                        })
                        .requirement(new CanSleepRequirement())
                        .build(),
        
                new GameItem.Builder("Bookshelf", "Salotto", BIG_SIZE)
                        .message("You just finished reading Harry Potter and the Philosopher’s Stone!")
                        .satiety(-5)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-10)
                        .build(),
        
                new GameItem.Builder("Photo Album", "Salotto", SMALL_SIZE)
                        .message("You picked up the old photo album.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-5)
                        .build()
        ));
        return new Room("Salotto", items);
    }
    
    
    // Sgabuzzino
    public static Room createStorageRoom() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Vacuum Cleaner", "Sgabuzzino", MID_SIZE)
                    .message("You use the vacuum cleaner and clean the room.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(0)
                    .energy(-20)
                    .build()
        ));
        return new Room("Sgabuzzino", items);
    }
            
    // Giardino
    public static Room createGarden() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Watering Can", "Giardino", SMALL_SIZE)
                    .message("You water the plants: they look greener now.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(0)
                    .energy(-10)
                    .build(),
       
                new GameItem.Builder("Ball", "Giardino", SMALL_SIZE)
                    .message("You play with the ball and get some exercise.")
                    .satiety(-10)
                    .hydration(-5)
                    .hygiene(-15)
                    .energy(-5)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameItem.Builder("Swing", "Giardino", BIG_SIZE)
                    .message("You swing back and forth, having fun and relaxing.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(-15)
                    .energy(0)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameItem.Builder("Car", "Giardino", BIG_SIZE)
                    .message("You take the car and go for a drive.")
                    .satiety(-15)
                    .hydration(-15)
                    .hygiene(-20)
                    .energy(-5)
                    .requirement(new CanPlayRequirement())
                    .build()
        ));
        return new Room("Giardino", items);
    }
}
