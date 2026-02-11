package main.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import main.controller.Controller;

/**
 * View class responsible for the user interface.
 * It handles the visualization of the game state and captures user inputs.
 */
public class View extends JFrame {

    // GUI COMPONENTS ------------------------------------------------------------
    private JProgressBar barEnergy, barSatiety, barHydration, barHygiene;
    private JTextArea gameLog;
    private JPanel roomItemsPanel;      // Central panel for items and NPCs interactions
    private JPanel inventoryPanel;      // Side panel for the inventory
    private JLabel roomTitleLabel;      // Label for the current room name
    
    // NEW COMPONENTS
    private JLabel levelLabel;          // Shows current level
    private JProgressBar xpBar;         // Experience bar
    private JProgressBar affMumBar, affDadBar, affBroBar; // Affinities bars
    
    private JFrame frame; 

    // CONTROLLER REFERENCE ------------------------------------------------------
    private Controller controller;

    // CONSTRUCTOR ---------------------------------------------------------------
    public View() {
        super("My Life Simulator");
        this.frame = this; // Assign current frame
        initUI();
    }

    /**
     * Initializes the graphical user interface components.
     */
    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750); // Increased size slightly to fit new panels
        setLayout(new BorderLayout());

        // 1. NORTH: Stats Panel (Energy, Satiety, etc.)
        JPanel statsPanel = new JPanel(new GridLayout(1, 4));
        barEnergy = createStyledBar("Energia", Color.ORANGE);
        barSatiety = createStyledBar("Sazietà", Color.GREEN);
        barHydration = createStyledBar("Idratazione", Color.BLUE);
        barHygiene = createStyledBar("Igiene", Color.CYAN);

        statsPanel.add(createStatContainer("Energia", barEnergy));
        statsPanel.add(createStatContainer("Sazietà", barSatiety));
        statsPanel.add(createStatContainer("Idratazione", barHydration));
        statsPanel.add(createStatContainer("Igiene", barHygiene));
        add(statsPanel, BorderLayout.NORTH);

        // 2. CENTER: Room Info & Interaction Area
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        roomTitleLabel = new JLabel("Loading...", SwingConstants.CENTER);
        roomTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(roomTitleLabel, BorderLayout.NORTH);

        // Interactive area (NPCs and Items on the floor)
        roomItemsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        roomItemsPanel.setBorder(BorderFactory.createTitledBorder("Interagisci:"));
        centerPanel.add(new JScrollPane(roomItemsPanel), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        // 3. EAST: Side Panel (Level, Inventory, Affinities)
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(280, 0));

        // 3A. Level Panel (Top of Side Panel)
        JPanel levelPanel = new JPanel(new GridLayout(2, 1));
        levelPanel.setBorder(BorderFactory.createTitledBorder("Progresso Giocatore"));
        
        levelLabel = new JLabel("Livello: 1", SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        xpBar = new JProgressBar(0, 100);
        xpBar.setStringPainted(true);
        xpBar.setForeground(new Color(138, 43, 226)); // Purple color for XP
        xpBar.setString("XP");
        
        levelPanel.add(levelLabel);
        levelPanel.add(xpBar);
        sidePanel.add(levelPanel, BorderLayout.NORTH);

        // 3B. Inventory Panel (Center of Side Panel)
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        // The border is now on the scroll pane
        
        JScrollPane invScroll = new JScrollPane(inventoryPanel);
        invScroll.setBorder(BorderFactory.createTitledBorder("Inventario"));
        // Remove preferred size to let it fill available space
        sidePanel.add(invScroll, BorderLayout.CENTER);

        // 3C. Affinity Panel (Bottom of Side Panel)
        JPanel affinityPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        affinityPanel.setBorder(BorderFactory.createTitledBorder("Relazioni"));
        affinityPanel.setPreferredSize(new Dimension(0, 180)); // Fixed height for affinities

        affMumBar = createStyledBar("", Color.PINK);
        affDadBar = createStyledBar("", new Color(100, 149, 237)); // Cornflower Blue
        affBroBar = createStyledBar("", new Color(50, 205, 50));   // Lime Green

        affinityPanel.add(createStatContainer("Mamma", affMumBar));
        affinityPanel.add(createStatContainer("Papà", affDadBar));
        affinityPanel.add(createStatContainer("Fratello", affBroBar));
        
        sidePanel.add(affinityPanel, BorderLayout.SOUTH);

        // Add the complete side panel to the frame
        add(sidePanel, BorderLayout.EAST);

        // 4. SOUTH: Game Log (Console)
        gameLog = new JTextArea(8, 50);
        gameLog.setEditable(false);
        gameLog.setLineWrap(true);
        JScrollPane logScroll = new JScrollPane(gameLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Aggiornamenti"));
        add(logScroll, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center window on screen
        setVisible(true);
    }
    
    // CONTROLLER CONNECTION -----------------------------------------------------

    /**
     * Sets the controller reference to allow callbacks from UI events.
     * @param controller The main game controller.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    // STARTING MENU ----------------------------------------------------------------
    /**
     * Shows the starting menu of the game 
     */
    public void showMenu() {
        
        JDialog menuDialog = new JDialog(frame, "Menu Iniziale", true);
        menuDialog.setSize(10, 10);
        menuDialog.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Benvenuto in My Life!", SwingConstants.CENTER);
        titolo.setFont(new Font("Arial", Font.BOLD, 20));
        menuDialog.add(titolo, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3,1,10,10));
        JButton startBtn = new JButton("Inizia Gioco");
        JButton infoBtn = new JButton("Crediti");
        JButton exitBtn = new JButton("Esci");

        center.add(startBtn);
        center.add(infoBtn);
        center.add(exitBtn); 

        startBtn.addActionListener(e -> menuDialog.dispose());
        infoBtn.addActionListener(e -> JOptionPane.showMessageDialog(menuDialog, "Progetto realizzato per PMO dalle studentesse del gruppo.", "Crediti", JOptionPane.INFORMATION_MESSAGE));
        exitBtn.addActionListener(e -> System.exit(0));

        menuDialog.add(center, BorderLayout.CENTER);
        menuDialog.pack();
        menuDialog.setResizable(false);
        menuDialog.setLocationRelativeTo(frame);
        menuDialog.setVisible(true);
        
    }
    
    // MAINCHARACTER PERSONALIZATION ----------------------------------------------------------------
    
    /**
     * Asks to input the Main Character's name
     * @return
     */
    public String askName() {
        JPanel panel = new JPanel();
        final JLabel label = new JLabel("Inserisci il tuo nome:");
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(textField);
        
        int result = JOptionPane.showConfirmDialog(
                frame, 
                panel, 
                "Name MainCharacter", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE
            );
           return textField.getText().trim();
    }

    /**
     * Displays the different personalization options
     * @param message
     * @param options
     * @return
     */
    public int showPersonalizationOptions(String message, List<String> options) {
        if (options == null || options.isEmpty()) {
            return -1;
        }
        
        String[] arrayOptions = options.toArray(new String[0]);
        
        Object choice = JOptionPane.showInputDialog(
            frame,
            message,
            "Personalizzazione MainCharacter",
            JOptionPane.QUESTION_MESSAGE,
            null,
            arrayOptions,
            arrayOptions[0] 
        );
        
        if (choice == null) {
            return -1;
        }
        
        for (int i = 0; i < arrayOptions.length; i++) {
            if (arrayOptions[i].equals(choice)) {
                return i;
            }
        }
        return -1;
    }
    

    /**
     * Helper method to create a styled progress bar.
     */
    private JProgressBar createStyledBar(String title, Color c) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(0); // Default to 0
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
     * Updates the display of level and XP.
     * @param lvl Current level.
     * @param xp Current XP amount.
     * @param xpToNext XP needed for the next level (sets the max value of the bar).
     */
    public void updateLevelDisplay(int lvl, int xp, int xpToNext) {
        levelLabel.setText("Livello: " + lvl);
        xpBar.setMaximum(xpToNext);
        xpBar.setValue(xp);
        xpBar.setString(xp + " / " + xpToNext + " XP");
    }

    /**
     * Updates the affinity bars for the NPCs.
     * @param affinityMum Affinity value for Mum.
     * @param affinityDad Affinity value for Dad.
     * @param affinityBro Affinity value for Brother.
     */
    public void updateAffinitiesDisplay(int affinityMum, int affinityDad, int affinityBro) {
        affMumBar.setValue(affinityMum);
        affDadBar.setValue(affinityDad);
        affBroBar.setValue(affinityBro);
    }

    /**
     * Updates the display of the inventory list in the side panel.
     * Generates buttons that trigger the controller interaction.
     * @param inventory The character's current inventory.
     */
    public void updateInventoryList(List<String> inventory) {
        System.out.println("DEBUG View: Aggiornamento inventario. Numero oggetti: " + inventory.size());
        
        inventoryPanel.removeAll();

        for (int i = 0; i < inventory.size(); i++) {
            String name = inventory.get(i);
            final int index = i; 
            
            JButton itemBtn = new JButton(name);
            itemBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            itemBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemBtn.setToolTipText("Clicca per interagire"); 

            
            itemBtn.addActionListener(e -> {
                if (controller != null) {
                    controller.handleInventoryInteraction(index);
                }
            });

            inventoryPanel.add(itemBtn);
            inventoryPanel.add(Box.createVerticalStrut(5));
        }

        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }
    
    // ROOM DISPLAY ----------------------------------------------------------------------------------------------------------------------

    /**
     * Updates the current room view.
     * It displays the room name, creates a button for the NPC (if present),
     * and creates buttons for items lying on the floor.
     * @param currentRoom The room the character is currently in.
     */
    public void updateCurrentRoom(String currentRoom, String npcName, List<String> items, List<Integer> itemSizes, List<String> exits) {
        
        // Clear the central panel
        roomItemsPanel.removeAll();
        
        // Update Title
        roomTitleLabel.setText("Posizione attuale: " + currentRoom);
        
        // Adding item buttons
        this.addRoomItemsButtons(items, itemSizes);
        
        if (!items.isEmpty()) roomItemsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        // NPC button
        if (npcName != null && !npcName.isEmpty()) {
            this.addNpcButton(npcName);
            roomItemsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }
        
        // Exits buttons
        this.addExitButtons(exits);
        
        roomItemsPanel.revalidate();
        roomItemsPanel.repaint();
    }
    
    /**
     * Helper method to create item buttons 
     * @param items Lista dei nomi degli oggetti
     * @param itemSizes Lista delle dimensioni degli oggetti (per il tooltip)
     */
    private void addRoomItemsButtons(List<String> items, List<Integer> itemSizes) {
        if (items.isEmpty()) {
            JLabel emptyLabel = new JLabel("Nessun oggetto nella stanza.");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            roomItemsPanel.add(emptyLabel);
            return;
        }

        JLabel itemsLabel = new JLabel("Oggetti a terra:");
        itemsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        itemsLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        roomItemsPanel.add(itemsLabel);
        roomItemsPanel.add(Box.createVerticalStrut(5)); 

        for (int i = 0; i < items.size(); i++) {
            String itemName = items.get(i);

            int size = (itemSizes != null && i < itemSizes.size()) ? itemSizes.get(i) : 0;
            
            final int index = i; 

            JButton itemBtn = new JButton(itemName);
            itemBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); 
            
            itemBtn.setToolTipText("Dimensione oggetto: " + size);
         
            itemBtn.addActionListener(e -> {
                if (controller != null) {
                    controller.handleItemInteraction(index);
                }
            });

            roomItemsPanel.add(itemBtn);
            roomItemsPanel.add(Box.createVerticalStrut(5));
        }
    }
    
    /**
     * Displays a list of options to choose from when interacting with an item
     * @param options
     * @return
     */
    public int showOptionItem(List<String> options) {
        return JOptionPane.showOptionDialog(
                this,                      
                "Cosa desideri fare con questo oggetto?", 
                "Interazione Oggetto",              
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options.toArray(),                  
                options.get(0)                      
        );
    }
    
    /**
     * Helper methods to create NPC button
     * @param name
     */
    private void addNpcButton(String name) {
        JLabel npcLabel = new JLabel("Persone:");
        npcLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        roomItemsPanel.add(npcLabel);

        JButton npcBtn = new JButton("Parla con: " + name);
        npcBtn.setBackground(new Color(255, 220, 220)); // Rosso chiaro
        npcBtn.setFont(new Font("Arial", Font.BOLD, 13));
        
        npcBtn.addActionListener(e -> {
            if (controller != null) {
                controller.handleNpcInteractions();
            }
        });
        roomItemsPanel.add(npcBtn);
    }
    
    /**
     * Helper method to create buttons to change room
     * @param exits
     */
    private void addExitButtons(List<String> exits) {
        JLabel moveLabel = new JLabel("Spostati verso:");
        moveLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        roomItemsPanel.add(moveLabel);

        for (String direction : exits) {
            JButton moveBtn = new JButton(direction);
            moveBtn.setBackground(new Color(220, 255, 220));
            
            moveBtn.addActionListener(e -> {
                if (controller != null) {
                    controller.changeRoom(direction);
                }
            });
            roomItemsPanel.add(moveBtn);
        }
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
            this, message, "Scegli", JOptionPane.QUESTION_MESSAGE,
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
        // Recursively disable components inside panels if necessary
        for (Component c : roomItemsPanel.getComponents()) c.setEnabled(false);
        for (Component c : inventoryPanel.getComponents()) c.setEnabled(false);
    }

    /**
     * * @param message
     */
    public void showNpcMessage(String message) {
        JOptionPane.showMessageDialog(
            this.frame, 
            message, 
            "Interazione", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        appendLog("NPC: " + message);
    }

    /**
     * * @param npcName
     * @param dialogue
     */
    public void showQuestDialogue(String npcName, String dialogue) {
        JOptionPane.showMessageDialog(
            this.frame, 
            dialogue, 
            npcName + ": ", 
            JOptionPane.PLAIN_MESSAGE
        );
       
        appendLog(npcName + ": " + dialogue);
    }

    /**
     * * @param questName
     */
    public void showQuestMessage(String questName) {
        JOptionPane.showMessageDialog(
            this.frame, 
            questName, 
            "Diario Quest", 
            JOptionPane.WARNING_MESSAGE
        );
        
        appendLog(">>> QUEST: " + questName + " <<<");
    }

    /**
     * Displays a message of success/error when entering a room
     * @param message
     */
    public void showAccess(String message) {
        if (message != null && !message.isEmpty()) {
            appendLog(""); 
            appendLog(">>> " + message);
        }
    }
}