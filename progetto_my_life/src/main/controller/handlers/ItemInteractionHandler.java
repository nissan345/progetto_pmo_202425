package main.controller.handlers;

import main.model.character.MainCharacter;
import main.model.world.gameItem.FoodType;
import main.model.world.gameItem.GameItem;
import main.controller.Controller;
import main.model.action.ActionResult;
import main.view.View;

/**
 * Handles interactions involving game items, such as using, picking up, 
 * or dropping items either from the room or the inventory.
 */
public class ItemInteractionHandler {
    
    // ATTRIBUTES ------------------------------------------------------------------------
    private MainCharacter mainCharacter;
    private View view;
    private Controller controller;

    // CONSTRUCTOR -----------------------------------------------------------------------
    
    /**
     * Constructs a new ItemInteractionHandler.
     * @param controller The main game controller.
     * @param character The main character performing the actions.
     * @param view The view to update logs and UI.
     */
    public ItemInteractionHandler(Controller controller, MainCharacter character, View view) {
        this.controller = controller;
        this.mainCharacter = character;
        this.view = view;
    }
    
    // ITEM USAGE METHODS ----------------------------------------------------------------
    
    /**
     * Handles the logic when the main character attempts to use an item found in the room.
     * If the item requires a specific choice (e.g., selecting a food type), it prompts the user.
     * @param item The game item to be used.
     */
    public void handleUseRoomItem(GameItem item) {
        // If the game is over, do nothing
        if(controller.isGameOver()) return;

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
        this.processActionResult(result);
     }
    
    /**
     * Handles the logic when the main character uses an item directly from the inventory.
     * @param item The game item to be used.
     */
    public void handleUseInventoryItem(GameItem item) {
        // If the game is over, do nothing
        if(controller.isGameOver()) return;
    
        ActionResult result = item.use(mainCharacter);
        this.processActionResult(result);
    }

    /** * Handles the specific logic for using an item that requires a user selection (e.g., cooking).
     * @param item The game item being used.
     * @param choice The specific option selected by the user (e.g., type of food).
     */
    public void handleItemChoice(GameItem item, FoodType choice) {
        // If the game is over, do nothing
        if(controller.isGameOver()) return;

        ActionResult result = item.useWithChoice(mainCharacter, choice);

        mainCharacter.applyActionResult(result, item.getName());
        this.processActionResult(result);
    }

    // INVENTORY MANAGEMENT METHODS ------------------------------------------------------

    /**
     * Handles the action of picking up an item from the room and adding it to the inventory.
     * @param item The game item to pick up.
     */
    public void handlePickUp(GameItem item) {
        // If the game is over, do nothing
        if(controller.isGameOver()) return;

        ActionResult result = mainCharacter.pickUpItemAction(item);
        this.processActionResult(result);
    }

    /**
     * Handles the action of dropping an item from the inventory into the current room.
     * @param item The game item to drop.
     */
    public void handleDrop(GameItem item) {
        // If the game is over, do nothing
        if(controller.isGameOver()) return;

        ActionResult result = mainCharacter.dropItemAction(item);
        this.processActionResult(result);
    }
    
    // HELPER METHODS --------------------------------------------------------------------
    
    /**
     * Processes the result of an item action, updates the game log, 
     * and notifies the controller to refresh the state.
     * @param result The result object containing messages and state changes.
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
        controller.onActionCompleted();
    }
}