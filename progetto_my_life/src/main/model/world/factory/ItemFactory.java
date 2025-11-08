package main.model.world.factory;

import java.util.*;
import main.model.action.ActionResult;
import main.model.requirement.*;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Refrigerator;


public class ItemFactory { 
	
    private static final int NO_EFFECT = 0;

	private static final int ENERGY_LOW = 40;
	private static final int ENERGY_MID = 80;
	
	private static final int HYGIENE_LOW = 40;
	private static final int HYGIENE_MID = 80;
	
	private static final int HIGH_GAIN = 30;
	private static final int MID_GAIN = 20;
	private static final int LOW_GAIN = 10;

    private static final int LOW_COST = -5;
	private static final int MID_COST = -10;
	private static final int HIGH_COST = -20;
	
	private static final int LOW_DURATION = 2;
	private static final int MID_DURATION = 5;
	private static final int HIGH_DURATION = 10;

	private static final int BIG_SIZE = 80;
	private static final int MID_SIZE = 40;
	private static final int NORMAL_SIZE = 20;
	private static final int SMALL_SIZE = 10;
	
   
    // Bedroom
    public static Room createBedroom() {
        List<GameItem> items = new ArrayList<>(List.of(new GameItem.Builder("Letto", "Camera da Letto", BIG_SIZE)
                    .message("Ti sdrai e ti riposi")
                    .dynamic((mc, self) -> {
                        int en = mc.getStats().getEnergy();
                        int gain = (en < ENERGY_LOW) ? HIGH_GAIN : (en < ENERGY_MID ? MID_GAIN : LOW_GAIN);
                        int duration = (en < ENERGY_LOW) ? HIGH_DURATION : (en < ENERGY_MID ? MID_DURATION : LOW_DURATION);
                        int hyg = -LOW_GAIN;
                        return new ActionResult(self.getMessage(), 0, 0, gain, hyg, duration);
                    }) 
                    .requirement(new CanSleepRequirement())
                    .build(), 
                    
                new GameItem.Builder("Computer", "Camera da Letto", NORMAL_SIZE)
                    .message("Giochi al pc.")
                    .dynamic((mc, self) -> {
                        int en = mc.getStats().getEnergy();
                        int energyCost  = (en < ENERGY_LOW) ? HIGH_COST : (en < ENERGY_MID ? MID_COST : LOW_COST);
                        int durationSec = (en < ENERGY_LOW) ? HIGH_DURATION : (en < ENERGY_MID ? MID_DURATION : LOW_DURATION); // stanco = più lento
                        int hyg         = LOW_COST;
                        int sat         = LOW_COST;
                        int hyd         = LOW_COST;
                        return new ActionResult(self.getMessage(), sat, hyd, energyCost, hyg, durationSec);
                    })
                    .requirement(new CanPlayRequirement())
                    .build(),
                    
                new GameItem.Builder("Armadio", "Camera da Letto", BIG_SIZE)
                    .message("Provi dei nuovi outfit!")
                    .energy(LOW_COST)
                    .hygiene(HIGH_GAIN)
                    .build()
        ));
        return new Room("Camera Da Letto", items, new LevelRequirement(1));
    }
    
    // Kitchen
    public static Room createKitchen() {
        List<GameItem> items = new ArrayList<>(List.of(new GameItem.Builder("Fornelli", "Cucina", BIG_SIZE)
                        .message("Cuoci un buon pasto.")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int energyCost   = (en < ENERGY_MID ? MID_COST : LOW_COST); 
                            int durationSec  = (en < ENERGY_MID ?  MID_DURATION :   LOW_DURATION);  
                            int satietyGain  = -HIGH_COST;
                            int hygieneCost  = -LOW_GAIN;
                            return new ActionResult(self.getMessage(), satietyGain, NO_EFFECT, energyCost, hygieneCost, durationSec);
                        })
                        .requirement(new CanEatRequirement())
                        .build(),
                        
                new Refrigerator(),
                
                new GameItem.Builder("Lavandino", "Cucina", BIG_SIZE)
                        .message("Lavi i piatti")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int energyCost  = (en < ENERGY_LOW) ? HIGH_COST : (en < ENERGY_MID ? MID_COST : LOW_COST);   
                            int durationSec = (en < ENERGY_LOW) ? HIGH_DURATION : (en < ENERGY_MID ? MID_DURATION :  LOW_DURATION);
                            int hygieneCost = LOW_COST;
                            return new ActionResult(self.getMessage(), NO_EFFECT, NO_EFFECT, energyCost, hygieneCost, durationSec);
                        })
                        .build()
        ));	
        return new Room("Cucina", items, new LevelRequirement(1));
    }
   
    // Bagno
    public static Room createBathroom() {
        List<GameItem> items = new ArrayList<>(List.of(new GameItem.Builder("Doccia", "Bagno", BIG_SIZE)
                        .message("Fai una doccia rinfrescante.")
                        .dynamic((mc, self) -> {
                            int currentHyg   = mc.getStats().getHygiene();
                            int hygieneGain  = (currentHyg < HYGIENE_LOW) ? HIGH_GAIN : (currentHyg < HYGIENE_MID  ? MID_GAIN : LOW_GAIN);
                            int durationSec  = (currentHyg < HYGIENE_LOW) ? HIGH_DURATION : (currentHyg <  HYGIENE_MID ? MID_DURATION : LOW_DURATION);
                            int energyCost   = LOW_COST; 
                            return new ActionResult(self.getMessage(), NO_EFFECT, NO_EFFECT, energyCost, hygieneGain, durationSec);
                        })
                        .requirement(new CanShowerRequirement())
                        .build(),
                
                new GameItem.Builder("WC", "Bagno", BIG_SIZE)
                        .message("Ti senti sollevato, dopo aver usato il bagno.")
                        .hygiene(LOW_GAIN)
                        .energy(LOW_GAIN)
                        .build(),
                
                new GameItem.Builder("Lavatrice", "Bagno", BIG_SIZE)
                        .message("Metti i vestiti in lavatrice!")
                        .energy(LOW_COST)
                        .build()
        ));
        return new Room("Bagno", items, new LevelRequirement(2));
    }
    
    // Salotto
    public static Room createLivingRoom() {
        List<GameItem> items = new ArrayList<>(List.of(new GameItem.Builder("Televisione", "Salotto", MID_SIZE)
                        .message("Guardi la tv, rilassandoti")
                        .satiety(LOW_COST)
                        .hydration(LOW_COST)
                        .hygiene(LOW_COST)
                        .energy(MID_GAIN)
                        .build(),
        
                new GameItem.Builder("Stereo", "Salotto", NORMAL_SIZE)
                        .message("Ascolti Billie Eilish.")
                        .energy(MID_GAIN)
                        .build(),
        
                new GameItem.Builder("Divano", "Salotto", BIG_SIZE)
                        .message("Fai un pisolino sul divano")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int gain = (en < ENERGY_LOW) ? HIGH_GAIN : LOW_GAIN;  
                            return new ActionResult(self.getMessage(), NO_EFFECT, NO_EFFECT, gain, NO_EFFECT, 5);
                        })
                        .requirement(new CanSleepRequirement())
                        .build(),
        
                new GameItem.Builder("Libreria", "Salotto", BIG_SIZE)
                        .message("Hai appena finito di leggere Harry Potter e la Pietra Filosofale")
                        .satiety(LOW_COST)
                        .energy(LOW_COST)
                        .build(),
        
                new GameItem.Builder("Album", "Salotto", SMALL_SIZE)
                        .message("Hai preso un vecchio album.")
                        .energy(LOW_COST)
                        .build()
        ));
        return new Room("Salotto", items, new LevelRequirement(2));
    }
    
    
    // Sgabuzzino
    public static Room createStorageRoom() {
        List<GameItem> items = new ArrayList<>(List.of(new GameItem.Builder("Aspirapolvere", "Sgabuzzino", MID_SIZE)
                    .message("Pulisci la stanza con un aspirapolvere.")
                    .energy(MID_COST)
                    .build()
        ));
        return new Room("Sgabuzzino", items, new LevelRequirement(3));
    }
            
    // Giardino
    public static Room createGarden() {
        List<GameItem> items = new ArrayList<>(List.of(new GameItem.Builder("Annaffiatoio", "Giardino", SMALL_SIZE)
                    .message("Annaffi le piante.")
                    .energy(LOW_COST)
                    .build(),
       
                new GameItem.Builder("Palla", "Giardino", SMALL_SIZE)
                    .message("Giochi con la palla.")
                    .satiety(HIGH_COST)
                    .hydration(HIGH_COST)
                    .hygiene(HIGH_COST)
                    .energy(LOW_COST)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameItem.Builder("Altalena", "Giardino", BIG_SIZE)
                    .message("Ti diverti sull'altalena")
                    .hygiene(MID_COST )
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameItem.Builder("Macchina", "Giardino", BIG_SIZE)
                    .message("Vai in città in macchina.")
                    .satiety(HIGH_COST)
                    .hydration(HIGH_COST)
                    .hygiene(HIGH_GAIN)
                    .energy(LOW_COST)
                    .requirement(new CanPlayRequirement())
                    .build()
        ));
        return new Room("Giardino", items, new LevelRequirement(4));
    }
}
