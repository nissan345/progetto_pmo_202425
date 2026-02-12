package main.controller; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import main.controller.engine.GameLoopManager;
import main.controller.handlers.ItemInteractionHandler;
import main.controller.handlers.NpcInteractionHandler;
import main.controller.init.WorldInitializer;
import main.model.character.MainCharacter;
import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;
import main.model.character.npc.NPC;
import main.model.quest.Quest;
import main.model.quest.QuestSystem;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.view.View;

/**
 * Controller class that manages the game logic and mediates interactions 
 * between the Model (data) and the View (interface).
 */
public class Controller {
    
    // ATTRIBUTES ------------------------------------------------------------------------
    private MainCharacter mainCharacter;
    private House house;
    private Map<String, NPC> family;
    
    private GameLoopManager gameLoopManager;
    
    private ItemInteractionHandler itemHandler;
    private NpcInteractionHandler NpcHandler; 
    
    private QuestSystem questSystem; 
    
    private View view;
    
    private boolean isGameOver;

    // CONSTRUCTOR -----------------------------------------------------------------------

    /**
     * Constructs a new Controller with the specified View.
     * Initializes the game over state to false.
     * * @param view The graphical user interface of the game.
     */
    public Controller(View view) {
        this.view = view;
        this.view.setController(this);
        this.isGameOver = false;
    }

    // GAME LIFECYCLE METHODS ------------------------------------------------------------
    
    /**
     * Starts menu
     */
    public void StartMenu() {
    	 view.showMenu();
    }
    
    /**
     * Starts the game session.
     * Displays the menu, handles character creation, initializes the world, 
     * NPCs, handlers, systems, and starts the main game loop.
     */
    public void startSession() {
        
        // Initialization Quest System
        this.questSystem = new QuestSystem();
        
        // Initialization of the world
        WorldInitializer initializer = new WorldInitializer(); 
        this.house = initializer.initHouse(); 
        initializer.startingRoom(mainCharacter);
        this.family = initializer.initNPCs(house, questSystem);
        
        // Intialization item handler
        this.itemHandler = new ItemInteractionHandler(this, mainCharacter, view);
        
        // Intialization NPC interactions handler
        this.NpcHandler = new NpcInteractionHandler(this, mainCharacter, view, questSystem);
        
        view.switchToGame();
        
        // Initial view update
        this.updateView();
        
        // Start the game loop
        this.gameLoopManager = new GameLoopManager(this, mainCharacter);
        this.gameLoopManager.startGameLoop(); 
    }
    
    /**
     * Checks if the game is currently over.
     * * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() { return this.isGameOver; }

    /**
     * Handles the game over state.
     * Displays the reason for the game over, stops the game loop, and disables UI controls.
     */
    public void handleGameOver() {
        isGameOver = true;
        String reason = mainCharacter.getStats().getDeathCause();

        view.showGameOverDialog("GAME OVER: " + reason);
        this.gameLoopManager.stopGameLoop();;
        view.disableControls();
    } 

    // CHARACTER CREATION --------------------------------------------------------------
    
    public void mainCharacterCreation(String name, Outfit outfit, Hair hair) {
        this.mainCharacter = new MainCharacter(name, outfit, hair);
        
        this.startSession();
    }
    
    // NAVIGATION & EXPLORATION --------------------------------------------------------
    
    /**
     * Attempts to move the MainCharacter to the specified room.
     * Updates the view and triggers room-entry interactions if successful.
     * * @param roomName The name of the room to enter.
     */
    public void changeRoom(String roomName) {
        Optional<Room> room = house.enterRoom(roomName); 
        
        if (room.isPresent()) {
            Room currentRoom = room.get();
            // Tries to enter the room of choice
            String roomMessage = this.mainCharacter.pickCurrentRoom(currentRoom); 
            
            // If the MC got access to the room, they enter
            if(this.mainCharacter.getCurrentRoom().equals(currentRoom)) {
                this.updateView();
                
                Optional<NPC> npc = currentRoom.getNpcInRoom();
                if (npc != null) {
                    this.NpcHandler.handleRoomEntryInteraction();;
                }
            // Otherwise, the access gets denied
            } else {
                view.showAccess(roomMessage);
            }
        }
    }
    
    // INTERACTION MANAGEMENT ----------------------------------------------------------

    /**
     * Handles the player's interaction with an item present in the current room.
     * Presents options like "Use" or "Pick Up" based on the item's properties.
     * * @param itemIndex The index of the item in the room's item list.
     */
    public void handleItemInteraction(int itemIndex) {
        List<GameItem> items = mainCharacter.getCurrentRoom().getItemsInRoom();
        int midSize = 40; 
        
        if (itemIndex >= 0 && itemIndex < items.size()) {
            GameItem item = items.get(itemIndex);

            List<String> options = new ArrayList<>();
            options.add("Usa"); 

            if (item.getSize() <= midSize) { 
                options.add("Raccogli");
            }

            options.add("Annulla");

            int choiceIndex = view.showOptionItem(options);

            if (choiceIndex == -1) return;

            String action = options.get(choiceIndex);

            switch (action) {
                case "Usa":
                    this.itemHandler.handleUseRoomItem(item);
                    break;
                case "Raccogli":
                    this.itemHandler.handlePickUp(item);
                    break;
                case "Annulla":
                default:
                    break;
            }
        }
    }
    
    /**
     * Handles the player's interaction with an item currently in their inventory.
     * Presents options like "Use" or "Drop".
     * * @param itemIndex The index of the item in the inventory list.
     */
    public void handleInventoryInteraction(int itemIndex) {
        List<GameItem> items = mainCharacter.getInventory().getItems();
        
        if (itemIndex >= 0 && itemIndex < items.size()) {
            GameItem item = items.get(itemIndex);
            
            List<String> options = Arrays.asList("Usa", "Lascia", "Annulla");
            
            int choiceIndex = view.showOptionItem(options);
            
            if (choiceIndex == -1) return; 

            String action = options.get(choiceIndex);

            switch (action) {
                case "Usa": 
                    this.itemHandler.handleUseInventoryItem(item);
                    break;
                case "Lascia":
                    this.itemHandler.handleDrop(item);
                    break;
                case "Annulla":
                default:
                    break;
            }
        }
    }
    
    /**
     * Delegates manual NPC interactions (user click) to the NpcInteractionHandler.
     */
    public void handleNpcInteractions() {
        if (this.NpcHandler != null) {
            this.NpcHandler.handleUserClickInteraction();
        }
    }
    
    /**
     * Callback method invoked after a player action is completed.
     * Checks for potential quest updates related to room entry conditions and refreshes the view.
     */
    public void onActionCompleted() {
        if (this.NpcHandler != null) {
            this.NpcHandler.handleRoomEntryInteraction();
        }
        this.updateView();
    }
    
    // VIEW UPDATE ---------------------------------------------------------------------
    
    /**
     * Updates the View with the latest data from the Model.
     * Refreshes stats, level, inventory, affinities, and room information.
     */
    public void updateView() {
        Room room = mainCharacter.getCurrentRoom();
        
        int mumAff = this.family.get("Mum").getAffinity();
        int dadAff = this.family.get("Dad").getAffinity();
        int broAff = this.family.get("Brother").getAffinity();
        
        String npcName = ""; 
        
        if (room.getNpcInRoom().isPresent()) {
            npcName = room.getNpcInRoom().get().getName();
        }
        
        List<String> itemNames = room.getItemsInRoom().stream()
                                                      .map(GameItem::getName)
                                                      .toList();
        
        List<Integer> itemSizes = room.getItemsInRoom().stream()
                                                       .map(GameItem::getSize)
                                                       .toList();
        
        List<String> exitDirections = room.getExits().keySet().stream().toList();
        
        List<String> activeQuests = mainCharacter.getOngoingQuests().stream()
                												    .map(Quest::getName) 
                												    .toList();
        
        view.updateStatsDisplay(
            mainCharacter.getStats().getEnergy(),
            mainCharacter.getStats().getSatiety(),
            mainCharacter.getStats().getHydration(),
            mainCharacter.getStats().getHygiene()
        );
        
        view.updateLevelDisplay(
            mainCharacter.getLvl(),
            mainCharacter.getXp(), 
            mainCharacter.getXpToNext()
        );

        view.updateInventoryList(mainCharacter.getInventory().getItems().stream()
                                                                        .map(GameItem::getName)
                                                                        .toList());
        
        view.updateAffinitiesDisplay(mumAff, dadAff, broAff);
            
        view.updateCurrentRoom(room.getRoomName(), npcName, itemNames, itemSizes, exitDirections);
        
        view.updateQuests(activeQuests);
    }
}