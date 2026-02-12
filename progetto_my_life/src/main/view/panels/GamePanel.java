package main.view.panels;

import javax.swing.*;
import java.awt.*;
import main.controller.Controller;

/**
 * The main container panel for the gameplay interface.
 * <p>
 * This class uses a {@link BorderLayout} to organize the game screen into four distinct areas:
 * <ul>
 * <li><b>West:</b> {@link StatusPanel} (Stats, Level, Affinities).</li>
 * <li><b>Center:</b> {@link RoomPanel} (Current location and interactions).</li>
 * <li><b>East:</b> {@link DashboardPanel} (Inventory and Quests).</li>
 * <li><b>South:</b> Game Log (Text area for narrative feedback).</li>
 * </ul>
 * It acts as an aggregate view, holding references to specific sub-panels to allow
 * modular updates from the View/Controller.
 * </p>
 */
public class GamePanel extends JPanel {
    
    // --- SUB-PANELS ------------------------------------------------------------
    
    // Panel displaying character statistics (Energy, Satiety, etc.). 
    private StatusPanel statusPanel;      
    
    // Panel handling room interactions (NPCs, Items, Exits). 
    private RoomPanel roomPanel;          
    
    // Panel managing the player's inventory and active quests. 
    private DashboardPanel dashboardPanel; 
    
    // Scrollable text area for the game narrative log. 
    private JTextArea gameLog;

    // --- CONSTRUCTOR -----------------------------------------------------------

    /**
     * Constructs the GamePanel and initializes the user interface layout.
     * Sets up the border layout, initializes all sub-panels, configures the 
     * log area, and assembles the visual components.
     */
    public GamePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Initialize Sub-Panels
        statusPanel = new StatusPanel();
        roomPanel = new RoomPanel();
        dashboardPanel = new DashboardPanel();

        // 2. Configure Log Area (Bottom)
        gameLog = new JTextArea(6, 50);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(gameLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Diario di Bordo"));

        // 3. Add to Layout
        add(statusPanel, BorderLayout.WEST);
        add(roomPanel, BorderLayout.CENTER);
        add(dashboardPanel, BorderLayout.EAST);
        add(logScroll, BorderLayout.SOUTH);
    }

    // --- CONTROLLER CONFIGURATION ----------------------------------------------

    /**
     * Propagates the controller instance to the interactive sub-panels.
     * This allows the sub-panels (Room and Dashboard) to trigger events in the controller.
     * @param c The main application controller.
     */
    public void setController(Controller c) {
        roomPanel.setController(c);
        dashboardPanel.setController(c);
    }

    // --- UI LOGIC & MANIPULATION -----------------------------------------------

    /**
     * Appends a new message to the game log at the bottom of the screen.
     * Automatically scrolls the text area to the most recent message.
     * @param msg The message string to display.
     */
    public void appendLog(String msg) {
        gameLog.append(msg + "\n");
        // Auto-scroll to bottom
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    /**
     * Disables interactive elements in the sub-panels.
     * Typically called when the game ends (Game Over) to prevent further actions.
     */
    public void disableInteraction() {
        roomPanel.disableButtons();
        dashboardPanel.disableButtons();
    }

    // --- GETTERS (COMPONENT ACCESS) --------------------------------------------

    /**
     * Retrieves the Status Panel.
     * @return The panel instance responsible for stats and affinities.
     */
    public StatusPanel getStatusPanel() { 
        return statusPanel; 
    }

    /**
     * Retrieves the Room Panel.
     * @return The panel instance responsible for the current room view.
     */
    public RoomPanel getRoomPanel() { 
        return roomPanel; 
    }

    /**
     * Retrieves the Dashboard Panel.
     * @return The panel instance responsible for inventory and quests.
     */
    public DashboardPanel getDashboardPanel() { 
        return dashboardPanel; 
    }
}