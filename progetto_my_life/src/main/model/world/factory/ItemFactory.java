package main.model.world.factory;

import java.util.*;

import main.model.action.ActionResult;
import main.model.requirement.*;
import main.model.world.Room;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Refrigerator;

     

public class ItemFactory { 
   
    // Bedroom
    public static Room createBedroom() {
        List<GameItem> items = new ArrayList<>(List.of(
                new GameItem.Builder("Letto", "Camera da Letto", 80)
                    .message("Ti sdrai e ti riposi")
                    .dynamic((mc, self) -> {
                        int en = mc.getStats().getEnergy();
                        int gain = (en < 40) ? 30 : (en < 80 ? 20 : 10);
                        int duration = (en < 40) ? 20 : (en < 80 ? 10 : 5);
                        int hyg = -10;
                        return new ActionResult(self.message, 0, 0, gain, hyg, duration);
                    })
                    .requirement(new CanSleepRequirement())
                    .build(), 
                    
                new GameItem.Builder("Computer", "Camera da Letto", 20)
                    .message("Giochi al pc.")
                    .dynamic((mc, self) -> {
                        int en = mc.getStats().getEnergy();
                        int energyCost  = (en < 40) ? -20 : (en < 80 ? -10 : -5);
                        int durationSec = (en < 40) ? 10 : (en < 80 ? 5 : 2); // stanco = più lento
                        int hygieneCost = -5;
                        int satCost     = -5;
                        int hydCost     = -10;
                        return new ActionResult(self.message, satCost, hydCost, energyCost, hygieneCost, durationSec);
                    })
                    .requirement(new CanPlayRequirement())
                    .build(),
                    
                new GameItem.Builder("Armadio", "Camera da Letto", 90)
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
                new GameItem.Builder("Fornelli", "Cucina", 80)
                        .message("Cuoci un buon pasto.")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int energyCost   = (en < 40) ? -20 : (en < 80 ? -15 : -10); 
                            int durationSec  = (en < 40) ?  20 : (en < 80 ?  10 :   5);  
                            int satietyGain  = 20;
                            int hygieneCost  = -15;
                            return new ActionResult(self.message, satietyGain, 0, energyCost, hygieneCost, durationSec);
                        })
                        .requirement(new CanCookRequirement())
                        .build(),
                        
                new Refrigerator(),
                
                new GameItem.Builder("Lavandino", "Cucina", 80)
                        .message("Lavi i piatti")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int energyCost  = (en < 40) ? -5 : (en < 80 ? -10 : -15);   // più stanco = consumi meno (o inverti se vuoi)
                            int durationSec = (en < 40) ? 15 : (en < 80 ? 10 :  5);
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
                new GameItem.Builder("Doccia", "Bagno", 80)
                        .message("You take a refreshing shower.")
                        .dynamic((mc, self) -> {
                            int currentHyg   = mc.getStats().getHygiene();
                            int hygieneGain  = (currentHyg < 40) ? 30 : (currentHyg < 80 ? 20 : 10);
                            int durationSec  = (currentHyg < 40) ? 20 : (currentHyg < 80 ? 10 : 5);
                            int energyCost   = -5; // fare la doccia consuma un filo
                            return new ActionResult(self.message, 0, 0, energyCost, hygieneGain, durationSec);
                        })
                        .requirement(new CanShowerRequirement())
                        .build(),
                
                new GameItem.Builder("Toilet", "Bagno", 60)
                        .message("You feel relieved after using the Bagno.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(5)
                        .energy(5)
                        .build(),
                
                new GameItem.Builder("Washing Machine", "Bagno", 80)
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
                new GameItem.Builder("Televisione", "Salotto", 40)
                        .message("You watch TV and relax.")
                        .satiety(-5)
                        .hydration(-5)
                        .hygiene(-5)
                        .energy(10)
                        .build(),
        
                new GameItem.Builder("Stereo", "Salotto", 30)
                        .message("You listen to Billie Eilish.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(10)
                        .build(),
        
                new GameItem.Builder("Sofa", "Salotto", 80)
                        .message("You sit on the sofa and rest a bit.")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int gain = (en < 50) ? 15 : 8;  
                            return new ActionResult(self.message, 0, 0, gain, 0, 5);
                        })
                        .requirement(new CanSleepRequirement())
                        .build(),
        
                new GameItem.Builder("Bookshelf", "Salotto", 80)
                        .message("You just finished reading Harry Potter and the Philosopher’s Stone!")
                        .satiety(-5)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-10)
                        .build(),
        
                new GameItem.Builder("Photo Album", "Salotto", 20)
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
                new GameItem.Builder("Vacuum Cleaner", "Sgabuzzino", 30)
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
                new GameItem.Builder("Watering Can", "Giardino", 10)
                    .message("You water the plants: they look greener now.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(0)
                    .energy(-10)
                    .build(),
       
                new GameItem.Builder("Ball", "Giardino", 10)
                    .message("You play with the ball and get some exercise.")
                    .satiety(-10)
                    .hydration(-5)
                    .hygiene(-15)
                    .energy(-5)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameItem.Builder("Swing", "Giardino", 80)
                    .message("You swing back and forth, having fun and relaxing.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(-15)
                    .energy(0)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameItem.Builder("Car", "Giardino", 90)
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
