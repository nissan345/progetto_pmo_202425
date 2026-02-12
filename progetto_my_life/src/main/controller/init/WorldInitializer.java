package main.controller.init;

import main.model.world.House;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;

import java.util.HashMap;
import java.util.Map;

import main.model.character.MainCharacter;
import main.model.character.npc.*;
import main.model.quest.QuestSystem;

/**
 * Responsible for initializing the game world elements.
 * This includes building the house structure, creating rooms and connections, 
 * placing the main character, and instantiating NPCs.
 */
public class WorldInitializer {
    
    // ATTRIBUTES ------------------------------------------------------------------------
    private House house;

    // HOUSE SETUP -----------------------------------------------------------------------

    /**
     * Initializes the game world by creating all rooms and establishing their connections.
     * @return The fully constructed House object containing all rooms.
     */
    public House initHouse() {
        house = new House();
        
        // Creating rooms 
        Room bedroom = ItemFactory.createBedroom();
        Room kitchen = ItemFactory.createKitchen();
        Room bathroom = ItemFactory.createBathroom();
        Room livingRoom = ItemFactory.createLivingRoom();
        Room storageRoom = ItemFactory.createStorageRoom();
        Room garden = ItemFactory.createGarden();

        // Setting up room connections
        bedroom.addExit("Cucina", kitchen);
        bedroom.addExit("Salotto", bathroom);
        
        kitchen.addExit("Camera da Letto", bedroom);
        kitchen.addExit("Salotto", livingRoom);
        kitchen.addExit("Bagno", bathroom);
        kitchen.addExit("Giardino", garden);
        
        livingRoom.addExit("Cucina", kitchen);
        livingRoom.addExit("Camera da Letto", bedroom);
        livingRoom.addExit("Sgabuzzino", storageRoom);
        
        storageRoom.addExit("Salotto", livingRoom);
        
        bathroom.addExit("Cucina", kitchen);
        
        garden.addExit("Cucina", kitchen);

        // Adding the rooms to the house
        house.addRoom(bedroom);
        house.addRoom(kitchen);
        house.addRoom(bathroom);
        house.addRoom(livingRoom);
        house.addRoom(storageRoom);
        house.addRoom(garden);
        
        return house;
    }
    
    // CHARACTER SETUP -------------------------------------------------------------------

    /**
     * Sets the initial position of the main character in the game world.
     * Checks if the starting room exists before placing the character.
     * @param mainCharacter The player character to be placed.
     */
    public void startingRoom(MainCharacter mainCharacter) {
  
        String startRoomName = "Camera da Letto";
        Room startRoom = house.getRoom(startRoomName);

        if(startRoom != null) {
            house.enterRoom(startRoomName);
            mainCharacter.pickCurrentRoom(startRoom);
        } else {
            System.err.println("Errore! Stanza " + startRoomName + "non trovata! Controlla la sintassi nella Item factory.");
        }
    }

    // NPC SETUP -------------------------------------------------------------------------

    /**
     * Initializes NPCs, places them in their specific rooms, registers them 
     * in the quest system, and returns a collection of them for easy access.
     * @param house The house where NPCs will be placed.
     * @param questSystem The system to register NPC quests.
     * @return A Map containing the created NPCs, keyed by their role/name (e.g., "Mum").
     */
    public Map<String, NPC> initNPCs(House house, QuestSystem questSystem) {
        Room livingRoom = house.getRoom("Salotto");
        Room garden = house.getRoom("Giardino");
        Room kitchen = house.getRoom("Cucina");

        Mum mum = new Mum(livingRoom, house);
        Dad dad = new Dad(garden, house);
        Brother brother = new Brother(kitchen, house);

        livingRoom.setNpc(mum);
        garden.setNpc(dad);
        kitchen.setNpc(brother);

        questSystem.registerNPC(mum);
        questSystem.registerNPC(dad);
        questSystem.registerNPC(brother);

        Map<String, NPC> family = new HashMap<>();
        family.put("Mum", mum);
        family.put("Dad", dad);
        family.put("Brother", brother);

        return family;
    }
}