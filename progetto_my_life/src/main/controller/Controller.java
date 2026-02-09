package main.controller; 

import javax.swing.SwingUtilities;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.model.character.MainCharacter;
import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;
import main.model.character.npc.Brother;
import main.model.character.npc.Dad;
import main.model.character.npc.Mum;
import main.model.character.npc.NPC;
import main.model.quest.Quest;
import main.model.quest.QuestSystem;
import main.model.world.gameItem.FoodType;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.factory.ItemFactory;
import main.model.action.ActionResult;
import main.view.View;

/**
 * Controller class that manages the game logic and interactions between the model and the view.
 */
public class Controller {
    // ATTRIBUTES ------------------------------------------------------------------------
    private MainCharacter mainCharacter;
    
    private House house;
    
    private Mum mum;
	private Dad dad;
	private Brother brother;
	
	private QuestSystem questSystem; 
	
    private View view;
    
    private ScheduledExecutorService gameTimer;
    private boolean isGameOver;

    // CONSTRUCTOR ------------------------------------------------------------------------
    public Controller(MainCharacter mainCharacter, View view) {
        this.mainCharacter = mainCharacter;
        this.house = new House();
        this.view = view;
        this.view.setController(this);
        this.isGameOver = false;

        // Initialization of the world
        initializeWorld();

        // Initial view update
        updateView();

        // Start the game loop
        startGameLoop();
    }

    // METHODS ---------------------------------------------------------------------------
    
    // WORLD CREATION -------------------------------------------------------------

    /**
     * Initializes the game world by creating rooms and setting up their connections.
     */
    private void initializeWorld() {
        // Rooms creation
        Room bedroom = ItemFactory.createBedroom();
        Room kitchen = ItemFactory.createKitchen();
        Room bathroom = ItemFactory.createBathroom();
        Room livingRoom = ItemFactory.createLivingRoom();
        Room storageRoom = ItemFactory.createStorageRoom();
        Room garden = ItemFactory.createGarden();

        //COLLEGAMENTI
        bedroom.addExit("Cucina", kitchen);
        bedroom.addExit("Bagno", bathroom);

        
        kitchen.addExit("Camera da Letto", bedroom);
        kitchen.addExit("Salotto", livingRoom);
        kitchen.addExit("Giardino", garden);

        
        livingRoom.addExit("Cucina", kitchen);
        livingRoom.addExit("Ripostiglio", storageRoom);
        
        
        storageRoom.addExit("Salotto", livingRoom);
        bathroom.addExit("Camera da Letto", bedroom);
        garden.addExit("Cucina", kitchen);

        
        
        // Setting up room connections
        house.addRoom(bedroom);
        house.addRoom(kitchen);
        house.addRoom(bathroom);
        house.addRoom(livingRoom);
        house.addRoom(storageRoom);
        house.addRoom(garden);
        
        // Setting NPCs
        mum = new Mum(livingRoom, house);
        dad = new Dad(garden, house);
        brother = new Brother(kitchen, house);

        livingRoom.setNpc(mum);
        garden.setNpc(dad);
        kitchen.setNpc(brother);
        
        // Setting quest system
        questSystem = new QuestSystem();
        questSystem.registerNPC(mum);
        questSystem.registerNPC(dad);
        questSystem.registerNPC(brother);

        // Setting initial position of the main character
        String startRoomName = "Camera da Letto";
        Room startRoom = house.getRoom(startRoomName);

        if(startRoom != null) {
            house.enterRoom(startRoomName);
            mainCharacter.pickCurrentRoom(startRoom);
        } else {
            System.err.println("Errore! Stanza " + startRoomName + "non trovata! Controlla la sintassi nella Item factory.");
        }
    }

    /**
     * Gets the house instance in case the view needs to access it.
     * @return
     
    public House getHouse() {
        return house;
    } */
    
    // ROOM VISUALIZATION --------------------------------------------------------------
    
    /**
     * Switches the current room of the MainCharacter to the chosen one
     * @param roomName
     */
    public void changeRoom(String roomName) {
    	Optional<Room> room = house.enterRoom(roomName); 
    	
    	if (room.isPresent()) {
            Room currentRoom = room.get();
            // Tries to enter the room of choice
            String roomMessage = this.mainCharacter.pickCurrentRoom(currentRoom); 
            
            // If the MC got access to the room, they enter
            if(this.mainCharacter.getCurrentRoom().equals(currentRoom)) {
            	view.updateCurrentRoom(currentRoom.getRoomName());
            	view.showAccess(roomMessage);
                
                this.itemsInRoom(); 
                
                Optional<NPC> npc = currentRoom.getNpcInRoom();
                if (npc != null) {
                    this.handleQuest(currentRoom, npc.get()); 
                    this.handleNpcInteractions(npc.get());
                }
            // Otherwise, the access gets denied
            } else {
            	view.showAccess(roomMessage);
            }
        }
    }
    
    public void itemsInRoom() {
    	 Room room = this.mainCharacter.getCurrentRoom();
         List<GameItem> currentRoomItems = room.getItemsInRoom();

         List<String> labels = currentRoomItems.stream()
             .map(o -> o.getName() + " - " + o.getDescription())
             .toList();

         view.showItemsInRoom(labels, idx -> {
             if (idx >= 0 && idx < currentRoomItems.size()) {
            	 handleItemInteraction(currentRoomItems.get(idx));
             }
         });
    }
    
    
    
    // CHARACTER CREATION --------------------------------------------------------------
    
    /**
     *  MainCharacter starting personalization
     */
    private void mainCharacterCreation() {
        String name = view.askName();
        Outfit outfit = chooseOption(Outfit.values());
        Hair hair = chooseOption(Hair.values());
        this.mainCharacter = new MainCharacter(name, outfit, hair);
    }
    
    /**
     * Helper method to choose a personalization option 
     * @param <T>
     * @param message
     * @param availableOptions
     * @return
     */
    private <T> T chooseOption(T[] availableOptions){
        List<String> options = Arrays.stream(availableOptions)
            .map(Object::toString)
            .toList();
        int choice  = view.showPersonalizationOptions(options);
        return availableOptions[choice];
    }
    

    // TIME MANAGEMENT --------------------------------------------------------------

    /**
     * Starts the game loop that updates the game state at fixed intervals.
     */
    private void startGameLoop() {
        gameTimer = Executors.newSingleThreadScheduledExecutor();
        
        gameTimer.scheduleAtFixedRate(() -> {
            if (!isGameOver) {
                // Game state updates
                mainCharacter.stateDecay();
                boolean dead = checkGameOverConditions();

                // View update
                SwingUtilities.invokeLater(() -> {
                    if(dead) {
                        handleGameOver();
                    }else{
                        updateView();
                    }
                });
            }
        }, 0, 5, TimeUnit.SECONDS); // Update every 5 seconds
    }

    /**
     * Checks if any of the main character's stats have reached zero.
     */
    public void stopGameLoop() {
        if (gameTimer != null && !gameTimer.isShutdown()) {
            gameTimer.shutdown();
        }
    }

    // USER ACTIONS MANAGEMENT -----------------------------------------------------

    
    /**
     * Handles the main character's choice when interacting with an item 
     * @param item 
     */
    private void handleItemInteraction(GameItem item) {
        String[] options = {"Usa", "Raccogli", "Annulla"};
        
        int choice = view.showOptionItem(Arrays.asList(options));

        switch (choice) {
            case 0:
                handleUseItem(item);
                break;
            case 1: 
                handlePickUp(item);
                itemsInRoom(); 
                break;
            case 2: 
            default:
                break;
        }
    }
    
    /**
     * Handles the use of a game item by the main character.
     * @param item
     */
    public void handleUseItem(GameItem item) {
        // If the game is over, do nothing
        if(isGameOver) return;

        // If the item requires a choice, ask the user
        if(item.requiresChoice()) {
            Object choice = view.askUserChoice(item.getMessage(), item.availableOptions());

            if (choice instanceof FoodType) {
                handleItemChoice(item,(FoodType) choice);
            }
            return;     // To exit the method after handling the choice
        }

        // Items that don't require choice
        ActionResult result = item.use(mainCharacter);
        processActionResult(result);
    }

    /** 
     * Handles the use of a game item that requires a choice.
     * @param item
     * @param choice
     */
    public void handleItemChoice(GameItem item, FoodType choice) {
        // If the game is over, do nothing
        if(isGameOver) return;

        ActionResult result = item.useWithChoice(mainCharacter, choice);

        mainCharacter.applyActionResult(result, item.getName());
        processActionResult(result);

    }

    /**
     * Handles the pick up of a game item by the main character.
     * @param item
     */
    public void handlePickUp(GameItem item) {
        // If the game is over, do nothing
    	
    	if(isGameOver) return;

        ActionResult result = mainCharacter.pickUpItemAction(item);
        processActionResult(result);
        updateView();
    }

    /**
     * Handles the dropping of a game item by the main character.
     * @param item
     */
    public void handleDrop(GameItem item) {
        // If the game is over, do nothing
        if(isGameOver) return;

        ActionResult result = mainCharacter.dropItemAction(item);
        processActionResult(result);
    }

    // HELPER METHODS -------------------------------------------------------------

    /**
     * Processes the result of an action and updates the view accordingly.
     * @param result
     */
    private void processActionResult(ActionResult result) {
        // Append a message to the log
        if (result.getMessage() != null && !result.getMessage().isEmpty()) {
            view.appendLog(result.getMessage());
        }

        // Append multiple messages
        if(result.getMessages() != null) {
            for(String msg : result.getMessages()) {
                view.appendLog(msg);
            }
        }

        // Update the view
        updateView();
    }

    /**
     * Checks if the game over conditions are met.
     * @return
     */
    private boolean checkGameOverConditions() {
        return mainCharacter.getStats().isAnyStatZero();
    }

    /**
     * Handles the game over state.
     */
    private void handleGameOver() {
        isGameOver = true;
        String reason = mainCharacter.getStats().getDeathCause();

        view.showGameOverDialog("GAME OVER: " + reason);
        stopGameLoop();
        view.disableControls();
    }

    /**
     * Updates the view with the current stats and inventory of the main character.
     */
    private void updateView() {
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

        view.updateInventoryList(mainCharacter.getInventory());
        
        view.updateAffinitiesDisplay(
                mum.getAffinity(), 
                dad.getAffinity(), 
                brother.getAffinity()
            );

        if (mainCharacter.getCurrentRoom() != null) {
            view.updateCurrentRoom(mainCharacter.getCurrentRoom());
        }
    }
    
    // NPCS INTERACTIONS MANAGEMENT --------------------------------------------------------------
    
    /**
     * Helper method for handling all different kinds of Npcs interactions
     * @param npc
     */
    private void handleNpcInteractions(NPC npc) {
    	
    	// Checking for mum's gift availability
    	if (npc instanceof Mum) {
            String giftMessage = ((Mum) npc).checkGiftInteraction(mainCharacter);
            if (!giftMessage.isEmpty()) {
                view.showNpcMessage(giftMessage);
                updateView(); 
            }
        }
    	
    	String dialogue = ""; 
    	Optional<Quest> ongoing = this.mainCharacter.getOngoingQuests().stream()
    																.filter(q -> q.getAssignerNPC().equals(npc))
    																.findFirst();
    	
        if(!this.mainCharacter.hasActiveQuestWithNPC(npc)) {
        	dialogue = npc.getInitialDialogue(); 
        	view.showNpcMessage(dialogue); 
        	
        } else if(ongoing.isPresent()) {
        	dialogue = npc.getQuestInProgressDialogue(ongoing.get());
        	view.showQuestDialogue(npc.getName(), ongoing.get().getName());
        }
    }
    
    // NPCS QUEST MANAGEMENT --------------------------------------------------------------
    
    /**
     * Helper method to handle the quest according to the quest system 
     * @param room
     * @param npc
     */
    private void handleQuest(Room room, NPC npc) {
    	
    	// Assignment of a new quest
    	List<Quest> newQuests = questSystem.onPlayerEnteredRoom(mainCharacter, room);
    	
    	if (!newQuests.isEmpty()) {
            for (Quest quest : newQuests) {
                view.showQuestMessage(quest.getName());
            
                String npcQuestAssignedDialogue = npc.getQuestAssignedDialogue(quest);
                view.showQuestDialogue(npc.getName(), npcQuestAssignedDialogue);
            }
        }
    	
    	// Handling the completion of the quest
		Optional<Quest> completedQuest = this.mainCharacter.getCompletedQuestWithNPC(npc); 

        if (completedQuest.isPresent()) {
        	view.showQuestMessage(completedQuest.get().getName());
            
        	String npcQuestCompletionDialogue = npc.getQuestCompletionDialogue(completedQuest.get()); 
            view.showQuestDialogue(npc.getName(), npcQuestCompletionDialogue);
            
            questSystem.tryTurnIn(mainCharacter, npc);
            
            updateView();
        }
    }
}
