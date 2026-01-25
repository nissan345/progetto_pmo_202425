package main.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

import main.controller.Controller;
import main.model.character.npc.NPC;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Inventory;

/**
 * View class responsible for the user interface.
 * It handles the visualization of the game state and captures user inputs.
 */
public class View extends JFrame {

    // GUI COMPONENTS ------------------------------------------------------------
    private JProgressBar barEnergy, barSatiety, barHydration, barHygiene;
    private JTextArea gameLog;
    private JPanel roomItemsPanel;      // Central panel for items and NPCs
    private JPanel inventoryPanel;      // Side panel for the inventory
    private JLabel roomTitleLabel;      // Label for the current room name

    // CONTROLLER REFERENCE ------------------------------------------------------
    private Controller controller;

    // CONSTRUCTOR ---------------------------------------------------------------
    public View() {
        super("My Life Simulator");
        initUI();
    }

    /**
     * Initializes the graphical user interface components.
     */
    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // 1. NORTH: Stats Panel (Energy, Satiety, etc.)
        JPanel statsPanel = new JPanel(new GridLayout(1, 4));
        barEnergy = createStyledBar("Energy", Color.ORANGE);
        barSatiety = createStyledBar("Satiety", Color.GREEN);
        barHydration = createStyledBar("Hydration", Color.BLUE);
        barHygiene = createStyledBar("Hygiene", Color.CYAN);

        statsPanel.add(createStatContainer("Energy", barEnergy));
        statsPanel.add(createStatContainer("Satiety", barSatiety));
        statsPanel.add(createStatContainer("Hydration", barHydration));
        statsPanel.add(createStatContainer("Hygiene", barHygiene));
        add(statsPanel, BorderLayout.NORTH);

        // 2. CENTER: Room Info & Interaction Area
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        roomTitleLabel = new JLabel("Loading...", SwingConstants.CENTER);
        roomTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(roomTitleLabel, BorderLayout.NORTH);

        // Interactive area (NPCs and Items on the floor)
        roomItemsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        roomItemsPanel.setBorder(BorderFactory.createTitledBorder("In the Room (Interact)"));
        centerPanel.add(new JScrollPane(roomItemsPanel), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        // 3. EAST: Inventory Panel
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Backpack (L: Use / R: Drop)"));
        
        JScrollPane invScroll = new JScrollPane(inventoryPanel);
        invScroll.setPreferredSize(new Dimension(250, 0));
        add(invScroll, BorderLayout.EAST);

        // 4. SOUTH: Game Log (Console)
        gameLog = new JTextArea(8, 50);
        gameLog.setEditable(false);
        gameLog.setLineWrap(true);
        JScrollPane logScroll = new JScrollPane(gameLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Game Log"));
        add(logScroll, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center window on screen
        setVisible(true);
    }

    /**
     * Helper method to create a styled progress bar.
     */
    private JProgressBar createStyledBar(String title, Color c) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setStringPainted(true);
        bar.setForeground(c);
        return bar;
    }

    /**
     * Helper method to wrap a progress bar within a labelled panel.
     */
    private JPanel createStatContainer(String label, JProgressBar bar) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label, SwingConstants.CENTER), BorderLayout.NORTH);
        p.add(bar, BorderLayout.CENTER);
        return p;
    }

    // CONTROLLER CONNECTION -----------------------------------------------------

    /**
     * Sets the controller reference to allow callbacks from UI events.
     * @param controller The main game controller.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    // DATA UPDATES --------------------------------------------------------------

    /**
     * Updates the display of the character's vital statistics.
     */
    public void updateStatsDisplay(int energy, int satiety, int hydration, int hygiene) {
        barEnergy.setValue(energy);
        barSatiety.setValue(satiety);
        barHydration.setValue(hydration);
        barHygiene.setValue(hygiene);
    }

    /**
     * Re-renders the inventory list in the side panel.
     * Generates buttons with MouseListeners for Left Click (Use) and Right Click (Drop).
     * @param inventory The character's current inventory.
     */
    public void updateInventoryList(Inventory inventory) {
        inventoryPanel.removeAll();

        for (GameItem item : inventory.getItems()) {
            JButton itemBtn = new JButton(item.getName());
            itemBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            itemBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemBtn.setToolTipText("Left Click: Use | Right Click: Drop");

            // Mouse Listener to distinguish between Use (Left) and Drop (Right)
            itemBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (controller == null) return;
                    
                    if (SwingUtilities.isRightMouseButton(evt)) {
                        controller.handleDrop(item); // Action: Drop item
                    } else {
                        controller.handleUseItem(item); // Action: Use item
                    }
                }
            });

            inventoryPanel.add(itemBtn);
            inventoryPanel.add(Box.createVerticalStrut(5));
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    /**
     * Updates the current room view.
     * It displays the room name, creates a button for the NPC (if present),
     * and creates buttons for items lying on the floor.
     * @param currentRoom The room the character is currently in.
     */
    public void updateCurrentRoom(Room currentRoom) {
        // 1. Update Title
        roomTitleLabel.setText("Current Location: " + currentRoom.getRoomName());

        // 2. Clear the central panel
        roomItemsPanel.removeAll();

        // 3. NPC MANAGEMENT (Check if an NPC is present)
        Optional<NPC> npcOpt = currentRoom.getNpcInRoom();
        if (npcOpt.isPresent()) {
            NPC npc = npcOpt.get();
            // Using getRelationship() as display text
            String npcName = npc.getRelationship();
            
            JButton npcBtn = new JButton("TALK TO: " + npcName);
            npcBtn.setBackground(new Color(255, 200, 200)); // Light red to highlight NPC
            npcBtn.setFont(new Font("Arial", Font.BOLD, 14));
            
            // Link to Controller's NPC interaction handler
            npcBtn.addActionListener(e -> {
                if (controller != null) controller.handleNpcInteraction(npc);
            });
            
            roomItemsPanel.add(npcBtn);
        }

        // 4. ITEMS ON FLOOR MANAGEMENT
        List<GameItem> itemsInRoom = currentRoom.getItemsInRoom();
        
        if (itemsInRoom.isEmpty() && npcOpt.isEmpty()) {
            roomItemsPanel.add(new JLabel("The room is empty."));
        } else {
            for (GameItem item : itemsInRoom) {
                JButton pickupBtn = new JButton("Pick Up: " + item.getName());
                // Link to Controller's PickUp handler
                pickupBtn.addActionListener(e -> {
                    if (controller != null) controller.handlePickUp(item);
                });
                roomItemsPanel.add(pickupBtn);
            }
        }
        
        // 5. Refresh UI
        roomItemsPanel.revalidate();
        roomItemsPanel.repaint();
    }
    
    // LOG AND DIALOGS -----------------------------------------------------------

    /**
     * Appends a message to the main game log text area.
     * @param msg The message to display.
     */
    public void appendLog(String msg) {
        gameLog.append(msg + "\n");
        // Auto-scroll to the bottom
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    /**
     * Opens a dialog to ask the user for a specific choice (e.g., FoodType).
     * @param message The prompt message.
     * @param availableOptions The list of available options.
     * @return The selected object.
     */
    public Object askUserChoice(String message, List<?> availableOptions) {
        if (availableOptions == null || availableOptions.isEmpty()) return null;

        Object[] choices = availableOptions.toArray();
        return JOptionPane.showInputDialog(
            this, message, "Make a Choice", JOptionPane.QUESTION_MESSAGE,
            null, choices, choices[0]
        );
    }

    /**
     * Shows a modal Game Over dialog.
     * @param message The reason for the game over.
     */
    public void showGameOverDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "GAME OVER", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Disables all interactive controls in the UI (used when Game Over).
     */
    public void disableControls() {
        roomItemsPanel.setEnabled(false);
        inventoryPanel.setEnabled(false);
        // Recursively disable components inside panels
        for (Component c : roomItemsPanel.getComponents()) c.setEnabled(false);
        for (Component c : inventoryPanel.getComponents()) c.setEnabled(false);
    }
}