package main.view;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.controller.Controller;
import main.view.panels.GamePanel;
import main.view.panels.MainCharacterPanel;
import main.view.panels.MenuPanel;

/**
 * The main graphical user interface for the application "My Life Simulator".
 * <p>
 * This class extends {@link JFrame} and manages the different screens of the game
 * (Menu, Character Creation, Main Game) using a {@link CardLayout}. It acts as the
 * primary View component in the MVC pattern, delegating specific UI updates to
 * sub-panels like {@link GamePanel}, {@link MenuPanel}, and {@link MainCharacterPanel}.
 * </p>
 */
public class View extends JFrame {
    
    // --- LAYOUT & CONTAINERS ---------------------------------------------------
    
    private CardLayout cardLayout;
    private JPanel mainContainer;
    
    private static final String MENU_CARD = "MENU";
    private static final String MC_CARD = "MAINCHARACTER"; 
    private static final String GAME_CARD = "GAME";
    
    // --- SUB-PANELS ------------------------------------------------------------
    
    private MenuPanel menu;
    private MainCharacterPanel characterPanel; 
    private GamePanel gamePanel; 
    
    // --- CONTROLLER ------------------------------------------------------------
    
    private Controller controller; 
    
    // --- CONSTRUCTOR & INITIALIZATION ------------------------------------------
    
    /**
     * Constructs the main application window.
     * Initializes the window properties and the user interface components.
     */
    public View() {
        super("My life Simulator"); 
        this.initWindow();
        this.initUI(); 
        this.setVisible(true);
    }
    
    /**
     * Sets up the main window properties (size, close operation, location).
     * Initializes the CardLayout container.
     */
    private void initWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800); // Wider resolution to accommodate the 3-column layout
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        
        add(mainContainer);
    }
    
    /**
     * Initializes the default UI components, specifically the GamePanel.
     */
    private void initUI() {
        this.gamePanel = new GamePanel();
        mainContainer.add(this.gamePanel, GAME_CARD); 
    }
    
    /**
     * Sets the controller for this view and propagates it to the sub-panels.
     * @param controller The main application controller.
     */
    public void setController(Controller controller) {
        this.controller = controller; 
        gamePanel.setController(controller);
    }
    
    // --- NAVIGATION & SCREENS --------------------------------------------------
    
    /**
     * Displays the main menu screen.
     * Initializes the MenuPanel if it hasn't been created yet.
     */
    public void showMenu() {
        if (this.menu == null) {
            this.menu = new MenuPanel(
                e -> switchToMainCharacterCreation(),
                e -> System.exit(0)); 
            this.mainContainer.add(this.menu, MENU_CARD); 
        }
        this.cardLayout.show(mainContainer, MENU_CARD);
    }
    
    /**
     * Switches the view to the Main Character Creation screen.
     * Initializes the MainCharacterPanel if it hasn't been created yet.
     */
    private void switchToMainCharacterCreation() {
        if (this.characterPanel == null) {
            this.characterPanel = new MainCharacterPanel((name, outfit, hair) -> {
                this.controller.mainCharacterCreation(name, outfit, hair);
            });
            mainContainer.add(characterPanel, MC_CARD);
        }
        cardLayout.show(mainContainer, MC_CARD);
    }
    
    /**
     * Switches the view to the main game screen.
     */
    public void switchToGame() {
        this.cardLayout.show(this.mainContainer, GAME_CARD);
    }
    
    // --- USER INTERACTION DIALOGS ----------------------------------------------
    
    /**
     * Shows a dialog with a list of options for the user to choose from.
     * @param options A list of strings representing the available options.
     * @return The index of the selected option, or a closed-dialog code.
     */
    public int showOptionItem(List<String> options) {
        return JOptionPane.showOptionDialog(this, "Cosa vuoi fare?", "Azione",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
                options.toArray(), options.get(0));
    }

    /**
     * Shows a dialog asking the user to make a choice from a list.
     * @param message The message to display in the dialog.
     * @param availableOptions A list of objects to choose from.
     * @return The selected object, or null if cancelled.
     */
    public Object askUserChoice(String message, List<?> availableOptions) {
         Object[] choices = availableOptions.toArray();
         return JOptionPane.showInputDialog(this, message, "Scelta", 
                 JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
    }

    // --- GAME STATE UPDATES (DELEGATED TO PANELS) ------------------------------

    /**
     * Updates the status panel with the character's vital statistics.
     * @param en Energy level.
     * @param sat Satiety level.
     * @param hyd Hydration level.
     * @param hyg Hygiene level.
     */
    public void updateStatsDisplay(int en, int sat, int hyd, int hyg) {
        gamePanel.getStatusPanel().updateStats(en, sat, hyd, hyg);
    }

    /**
     * Updates the status panel with the character's level and experience.
     * @param lvl Current level.
     * @param xp Current experience points.
     * @param xpToNext Experience points required for the next level.
     */
    public void updateLevelDisplay(int lvl, int xp, int xpToNext) {
        gamePanel.getStatusPanel().updateLevel(lvl, xp, xpToNext);
    }

    /**
     * Updates the status panel with the character's relationship affinities.
     * @param mum Affinity with Mum.
     * @param dad Affinity with Dad.
     * @param bro Affinity with Brother.
     */
    public void updateAffinitiesDisplay(int mum, int dad, int bro) {
        gamePanel.getStatusPanel().updateAffinities(mum, dad, bro);
    }

    /**
     * Updates the dashboard panel with the current inventory items.
     * @param inventory A list of item names in the inventory.
     */
    public void updateInventoryList(List<String> inventory) {
        gamePanel.getDashboardPanel().updateInventory(inventory);
    }
    
    /**
     * Updates the dashboard panel with the list of active quests.
     * @param quests A list of active quest descriptions or names.
     */
    public void updateQuests(List<String> quests) {
        gamePanel.getDashboardPanel().updateQuests(quests);
    }

    /**
     * Updates the room panel with the details of the current location.
     * @param roomName The name of the room.
     * @param npcName The name of the NPC in the room (can be empty/null).
     * @param items A list of item names present in the room.
     * @param sizes A list of sizes corresponding to the items (optional).
     * @param exits A list of available exit names.
     */
    public void updateCurrentRoom(String roomName, String npcName, List<String> items, List<Integer> sizes, List<String> exits) {
        gamePanel.getRoomPanel().updateRoom(roomName, npcName, items, sizes, exits);
    }

    // --- LOGGING & MESSAGING ---------------------------------------------------

    /**
     * Appends a message to the in-game log area.
     * @param msg The message string to append.
     */
    public void appendLog(String msg) {
        gamePanel.appendLog(msg);
    }
    
    /**
     * Logs an access message (e.g., entering/exiting rooms) if not empty.
     * @param msg The message to log.
     */
    public void showAccess(String msg) { 
        if(!msg.isEmpty()) appendLog(">>> " + msg); 
    }
    
    /**
     * Displays a Game Over dialog with the specified reason.
     * @param msg The game over message.
     */
    public void showGameOverDialog(String msg) { 
        JOptionPane.showMessageDialog(this, msg, "GAME OVER", JOptionPane.ERROR_MESSAGE); 
    }
    
    /**
     * Disables interactive controls in the game panel (e.g., upon death).
     */
    public void disableControls() { 
        gamePanel.disableInteraction(); 
    }
    
    /**
     * Shows a message from an NPC in a dialog and logs it.
     * @param npcName The name of the NPC speaking.
     * @param msg The message content.
     */
    public void showNpcMessage(String npcName, String msg) { 
        JOptionPane.showMessageDialog(this, msg, npcName, JOptionPane.INFORMATION_MESSAGE); 
        appendLog("NPC: " + msg); 
    }
    
    /**
     * Shows a dialogue related to a specific Quest and logs it.
     * @param name The name of the speaker (usually NPC).
     * @param dialog The dialogue content.
     */
    public void showQuestDialogue(String name, String dialog) { 
        JOptionPane.showMessageDialog(this, dialog, name, JOptionPane.PLAIN_MESSAGE); 
        appendLog(name + ": " + dialog); 
    }
    
    /**
     * Shows a general system message regarding Quests (e.g., New Quest, Completed).
     * @param msg The quest notification message.
     */
    public void showQuestMessage(String msg) { 
        JOptionPane.showMessageDialog(this, msg, "QUEST", JOptionPane.WARNING_MESSAGE); 
        appendLog(msg); 
    }
}