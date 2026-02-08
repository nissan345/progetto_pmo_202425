package main.controller; 

import javax.swing.SwingUtilities;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import main.model.character.MainCharacter;
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
     */
    public House getHouse() {
        return house;
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

    /**
     * Handles the movement of the main character to a new room.
     * @param nextRoom
     */
    public void handleMove(Room nextRoom) {
        // If the game is over, do nothing
    	if(isGameOver) return;

    	Optional<Room> entered = house.enterRoom(nextRoom.getRoomName());
        
    	if (entered.isPresent()) {
    		mainCharacter.pickCurrentRoom(entered.get());
    		view.appendLog("Ti sei spostato in: " + entered.get().getRoomName());
    		updateView();
    	}else {
            view.appendLog("Errore: Non puoi andare lì!");
        }
    	
    	
        String msg = mainCharacter.pickCurrentRoom(nextRoom);
        view.appendLog(msg);
        
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

        view.updateInventoryList(mainCharacter.getInventory());

        if (mainCharacter.getCurrentRoom() != null) {
            view.updateCurrentRoom(mainCharacter.getCurrentRoom());
        }
    }
}
