package model.world.factory;

import java.util.*;

import model.action.ActionResult;
import model.requirement.*;
import model.world.IRoom;
import model.world.Room;
import model.world.gameItem.GameObject;
import model.world.gameItem.Refrigerator;

     

public class ObjectFactory { 
   
    // Bedroom
    public static Room createBedroom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Letto", "Camera da Letto", 80)
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
                    
                new GameObject.Builder("Computer", "Camera da Letto", 20)
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
                    
                new GameObject.Builder("Armadio", "Camera da Letto", 90)
                    .message("Provi dei nuovi outfit!")
                    .energy(-10)
                    .hygiene(20)
                    .build()
        ));
        return new IRoom("Camera Da Letto", objects);
    }
    
    // Kitchen
    public static Room createKitchen() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Fornelli", "Cucina", 80)
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
                
                new GameObject.Builder("Lavandino", "Cucina", 80)
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
        return new IRoom("Cucina", objects);
    }
   
    // Bagno
    public static Room createBagno() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Doccia", "Bagno", 80)
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
                
                new GameObject.Builder("Toilet", "Bagno", 60)
                        .message("You feel relieved after using the Bagno.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(5)
                        .energy(5)
                        .build(),
                
                new GameObject.Builder("Washing Machine", "Bagno", 80)
                        .message("You put your clothes in the washing machine. The house is tidier!")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-10)
                        .build()
        ));
        return new IRoom("Bagno", objects);
    }
    
    // Salotto
    public static Room createLivingRoom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Televisione", "Salotto", 40)
                        .message("You watch TV and relax.")
                        .satiety(-5)
                        .hydration(-5)
                        .hygiene(-5)
                        .energy(10)
                        .build(),
        
                new GameObject.Builder("Stereo", "Salotto", 30)
                        .message("You listen to Billie Eilish.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(10)
                        .build(),
        
                new GameObject.Builder("Sofa", "Salotto", 80)
                        .message("You sit on the sofa and rest a bit.")
                        .dynamic((mc, self) -> {
                            int en = mc.getStats().getEnergy();
                            int gain = (en < 50) ? 15 : 8;  
                            return new ActionResult(self.message, 0, 0, gain, 0, 5);
                        })
                        .requirement(new CanSleepRequirement())
                        .build(),
        
                new GameObject.Builder("Bookshelf", "Salotto", 80)
                        .message("You just finished reading Harry Potter and the Philosopher’s Stone!")
                        .satiety(-5)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-10)
                        .build(),
        
                new GameObject.Builder("Photo Album", "Salotto", 20)
                        .message("You picked up the old photo album.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-5)
                        .build()
        ));
        return new IRoom("Salotto", objects);
    }
    
    
    // Sgabuzzino
    public static Room createStorageRoom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Vacuum Cleaner", "Sgabuzzino", 30)
                    .message("You use the vacuum cleaner and clean the room.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(0)
                    .energy(-20)
                    .build()
        ));
        return new IRoom("Sgabuzzino", objects);
    }
            
    // Giardino
    public static Room createGiardino() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Watering Can", "Giardino", 10)
                    .message("You water the plants: they look greener now.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(0)
                    .energy(-10)
                    .build(),
       
                new GameObject.Builder("Ball", "Giardino", 10)
                    .message("You play with the ball and get some exercise.")
                    .satiety(-10)
                    .hydration(-5)
                    .hygiene(-15)
                    .energy(-5)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameObject.Builder("Swing", "Giardino", 80)
                    .message("You swing back and forth, having fun and relaxing.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(-15)
                    .energy(0)
                    .requirement(new CanPlayRequirement())
                    .build(),
       
                new GameObject.Builder("Car", "Giardino", 90)
                    .message("You take the car and go for a drive.")
                    .satiety(-15)
                    .hydration(-15)
                    .hygiene(-20)
                    .energy(-5)
                    .requirement(new CanPlayRequirement())
                    .build()
        ));
        return new IRoom("Giardino", objects);
    }
}
