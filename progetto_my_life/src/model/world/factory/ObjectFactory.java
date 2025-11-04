package model.world.factory;

import java.util.*;
import model.requirement.LevelRequirement;
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
                    .energy(40)
                    .hygiene(-10)
                    .build(), 
                    
                new GameObject.Builder("Computer", "Camera da Letto", 20)
                    .message("Giochi al pc.")
                    .satiety(-10)
                    .hydration(-5)
                    .energy(-20)
                    .hygiene(-5)
                    .build(),
                    
                new GameObject.Builder("Armadio", "Camera da Letto", 90)
                    .message("Provi dei nuovi outfit!")
                    .energy(-10)
                    .hygiene(20)
             //     .isSpecialInteraction(true)
                    .build()
        ));
        return new IRoom("Camera Da Letto", objects, new LevelRequirement(1));
    }
    
    // Kitchen
    public static Room createKitchen() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Fornelli", "Cucina", 80)
                        .message("Cuoci un buon pasto.")
                        .satiety(20)
                        .hydration(0)
                        .hygiene(-5)
                        .energy(-10)
             //           .isSpecialInteraction(true)
                        .build(),
                        
                new Refrigerator(),
                
                new GameObject.Builder("Lavandino", "Cucina", 80)
                        .message("Lavi i piatti")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-10)
                        .build()
        ));	
        return new IRoom("Cucina", objects, new LevelRequirement(1));
    }
   
    // Bagno
    public static Room createBagno() {
        List<GameObject> objects = new ArrayList<>(List.of(
                new GameObject.Builder("Doccia", "Bagno", 80)
                        .message("You take a refreshing shower.")
                        .satiety(0)
                        .hydration(0)
                        .hygiene(40)
                        .energy(10)
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
        return new IRoom("Bagno", objects, new LevelRequirement(3));
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
                        .satiety(0)
                        .hydration(0)
                        .hygiene(0)
                        .energy(-15)
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
        return new IRoom("Salotto", objects, new LevelRequirement(2));
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
        return new IRoom("Sgabuzzino", objects, new LevelRequirement(4));
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
                    .build(),
       
                new GameObject.Builder("Swing", "Giardino", 80)
                    .message("You swing back and forth, having fun and relaxing.")
                    .satiety(0)
                    .hydration(0)
                    .hygiene(-15)
                    .energy(0)
                    .build(),
       
                new GameObject.Builder("Car", "Giardino", 90)
                    .message("You take the car and go for a drive.")
                    .satiety(-15)
                    .hydration(-15)
                    .hygiene(-20)
                    .energy(-5)
                    .build()
        ));
        return new IRoom("Giardino", objects, new LevelRequirement(5));
    }
}
