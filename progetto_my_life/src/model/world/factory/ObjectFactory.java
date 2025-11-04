package main.neri.classes;

import java.util.*;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.Room;
     

public class ObjectFactory { 
   
    // Bedroom
    public static Room createBedroom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Bed", "Bedroom", 80)
                    .message("You lie down on the bed and rest.")
                    .energy(40)
                    .hygiene(-10)
                    .build(), 
                    
                new GameObject.Builder("Computer", "Bedroom", 20)
                    .message("You play on the computer.")
                    .hunger(-10)
                    .thirst(-5)
                    .energy(-20)
                    .hygiene(-5)
                    .build(),
                    
                new GameObject.Builder("Wardrobe", "Bedroom", 90)
                    .message("You try on some new outfits!")
                    .energy(-10)
                    .hygiene(20)
             //       .isSpecialInteraction(true)
                    .build()
        ));
        return new RoomImpl("Bedroom", objects);
    }
    
    // Kitchen
    public static Room createKitchen() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Stove", "Kitchen", 80)
                        .message("You cook a hot meal.")
                        .hunger(20)
                        .thirst(0)
                        .hygiene(-5)
                        .energy(-10)
             //           .isSpecialInteraction(true)
                        .build(),
                        
                new Refrigerator(),
                
                new GameObject.Builder("Sink", "Kitchen", 80)
                        .message("You wash the dishes.")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(0)
                        .energy(-10)
                        .build()
        ));	
        return new RoomImpl("Kitchen", objects);
    }
   
    // Bathroom
    public static Room createBathroom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Shower", "Bathroom", 80)
                        .message("You take a refreshing shower.")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(40)
                        .energy(10)
                        .build(),
                
                new GameObject.Builder("Toilet", "Bathroom", 60)
                        .message("You feel relieved after using the bathroom.")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(5)
                        .energy(5)
                        .build(),
                
                new GameObject.Builder("Washing Machine", "Bathroom", 80)
                        .message("You put your clothes in the washing machine. The house is tidier!")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(0)
                        .energy(-10)
                        .build()
        ));
        return new RoomImpl("Bathroom", objects);
    }
    
    // Living Room
    public static Room createLivingRoom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Television", "Living Room", 40)
                        .message("You watch TV and relax.")
                        .hunger(-5)
                        .thirst(-5)
                        .hygiene(-5)
                        .energy(10)
                        .build(),
        
                new GameObject.Builder("Stereo", "Living Room", 30)
                        .message("You listen to Billie Eilish.")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(0)
                        .energy(10)
                        .build(),
        
                new GameObject.Builder("Sofa", "Living Room", 80)
                        .message("You sit on the sofa and rest a bit.")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(0)
                        .energy(-15)
                        .build(),
        
                new GameObject.Builder("Bookshelf", "Living Room", 80)
                        .message("You just finished reading Harry Potter and the Philosopher’s Stone!")
                        .hunger(-5)
                        .thirst(0)
                        .hygiene(0)
                        .energy(-10)
                        .build(),
        
                new GameObject.Builder("Photo Album", "Living Room", 20)
                        .message("You picked up the old photo album.")
                        .hunger(0)
                        .thirst(0)
                        .hygiene(0)
                        .energy(-5)
                        .build()
        ));
        return new RoomImpl("Living Room", objects);
    }
    
    
    // Storage Room
    public static Room createStorageRoom() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Vacuum Cleaner", "Storage Room", 30)
                    .message("You use the vacuum cleaner and clean the room.")
                    .hunger(0)
                    .thirst(0)
                    .hygiene(0)
                    .energy(-20)
                    .build()
        ));
        return new RoomImpl("Storage Room", objects);
    }
            
    // Garden
    public static Room createGarden() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Watering Can", "Garden", 10)
                    .message("You water the plants: they look greener now.")
                    .hunger(0)
                    .thirst(0)
                    .hygiene(0)
                    .energy(-10)
                    .build(),
       
                new GameObject.Builder("Ball", "Garden", 10)
                    .message("You play with the ball and get some exercise.")
                    .hunger(-10)
                    .thirst(-5)
                    .hygiene(-15)
                    .energy(-5)
                    .build(),
       
                new GameObject.Builder("Swing", "Garden", 80)
                    .message("You swing back and forth, having fun and relaxing.")
                    .hunger(0)
                    .thirst(0)
                    .hygiene(-15)
                    .energy(0)
                    .build(),
       
                new GameObject.Builder("Car", "Garden", 90)
                    .message("You take the car and go for a drive.")
                    .hunger(-15)
                    .thirst(-15)
                    .hygiene(-20)
                    .energy(-5)
                    .build()
        ));
        return new RoomImpl("Garden", objects);
    }
}
