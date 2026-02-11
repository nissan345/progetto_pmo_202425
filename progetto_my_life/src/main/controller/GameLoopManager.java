package main.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;
import main.model.character.MainCharacter;

/**
 * Manages the main game loop, handling time-based events such as character state decay 
 * and view updates at fixed intervals.
 */
public class GameLoopManager {
	
    // ATTRIBUTES ------------------------------------------------------------------------
    private ScheduledExecutorService gameTimer;
    private Controller controller; 
    private MainCharacter character;

    // CONSTRUCTOR -----------------------------------------------------------------------
    
    /**
     * Constructs a new GameLoopManager.
     * @param controller The main game controller used to trigger game over or view updates.
     * @param character The main character whose state is updated over time.
     */
    public GameLoopManager(Controller controller, MainCharacter character) {
        this.controller = controller;
        this.character = character;
    }
    
    // MAIN METHODS ----------------------------------------------------------------------
    
    /**
     * Starts the game loop that updates the game state at fixed intervals.
     * It schedules a task to run every 5 seconds which:
     * 1. Applies state decay to the character.
     * 2. Checks if the character has died.
     * 3. Updates the view or triggers game over on the Event Dispatch Thread.
     */
    public void startGameLoop() {
    	gameTimer = Executors.newSingleThreadScheduledExecutor();
    	
        gameTimer.scheduleAtFixedRate(() -> {
            if (!controller.isGameOver()) {
            	
                character.stateDecay();
                boolean dead = character.getStats().isAnyStatZero();

                SwingUtilities.invokeLater(() -> {
                    if (dead) {
                        controller.handleGameOver();
                    } else {
                        controller.updateView();
                    }
                });
            }
        }, 0, 5, TimeUnit.SECONDS);
    }
    

    /**
     * Stops the game loop if it is currently running.
     * This method shuts down the scheduled executor service.
     */
    public void stopGameLoop() {
        if (gameTimer != null && !gameTimer.isShutdown()) {
            gameTimer.shutdown();
        }
    }

}