package main.view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import main.controller.Controller;

/**
 * Represents the visual interface for the current room in the game.
 */
public class RoomPanel extends JPanel {

    // UI COMPONENTS -------------------------------------------------------------
    private JLabel roomTitle;
    private JPanel npcContainer;
    private JPanel itemContainer;
    private JPanel exitContainer;

    // CONTROLLER REFERENCE -------------------------------------------------------
    private Controller controller;

    // CONSTRUCTOR -------------------------------------------------------------------

    /**
     * Constructs the RoomPanel and initializes the UI layout.
     */
    public RoomPanel() {
        setLayout(new BorderLayout());
        
        // Room Title
        roomTitle = new JLabel("Caricamento...", SwingConstants.CENTER);
        roomTitle.setFont(new Font("Monospaced", Font.BOLD, 26));
        roomTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(roomTitle, BorderLayout.NORTH);

        // Scrollable Panel for vertical content
        JPanel contentStack = new JPanel();
        contentStack.setLayout(new BoxLayout(contentStack, BoxLayout.Y_AXIS));

        // Create Sections
        npcContainer = createSection("NPC");
        itemContainer = createSection("Oggetti");
        exitContainer = createSection("Stanze");

        contentStack.add(npcContainer);
        contentStack.add(Box.createVerticalStrut(15));
        contentStack.add(itemContainer);
        contentStack.add(Box.createVerticalStrut(15));
        contentStack.add(exitContainer);

        add(new JScrollPane(contentStack), BorderLayout.CENTER);
    }

    // CONFIGURATION ---------------------------------------------------------

    /**
     * Sets the controller responsible for handling interactions from this panel.
     * @param c The main application controller.
     */
    public void setController(Controller c) { 
        this.controller = c; 
    }

    // VIEW UPDATE METHODS ---------------------------------------------------

    /**
     * Updates the panel to reflect the current state of the room.
     * @param name The name of the room to display as the title.
     * @param npcName The name of the NPC in the room.
     * @param items A list of names of items present in the room.
     * @param sizes A list of sizes corresponding to the items.
     * @param exits A list of names of adjacent rooms.
     */
    public void updateRoom(String name, String npcName, List<String> items, List<Integer> sizes, List<String> exits) {
        roomTitle.setText(name);

        // Update NPC
        npcContainer.removeAll();
        if (npcName != null && !npcName.isEmpty()) {
            JButton btn = new JButton(npcName);
            btn.setBackground(new Color(255, 230, 230));
            btn.addActionListener(e -> { if(controller!=null) controller.handleNpcInteractions(); });
            npcContainer.add(btn);
        } else {
            npcContainer.add(new JLabel("Nessuno qui."));
        }

        // Update Items
        itemContainer.removeAll();
        if (items.isEmpty()) {
            itemContainer.add(new JLabel("Nessun oggetto."));
        } else {
            for (int i = 0; i < items.size(); i++) {
                final int idx = i;
                JButton btn = new JButton(items.get(i));
                if(sizes != null) btn.setToolTipText("Dimensione: " + sizes.get(i));
                btn.addActionListener(e -> { if(controller!=null) controller.handleItemInteraction(idx); });
                itemContainer.add(btn);
            }
        }

        // Update Exits
        exitContainer.removeAll();
        for (String exit : exits) {
            JButton btn = new JButton(exit);
            btn.setBackground(new Color(230, 255, 230));
            btn.addActionListener(e -> { if(controller!=null) controller.changeRoom(exit); });
            exitContainer.add(btn);
        }

        revalidate(); repaint();
    }

    // UI CONTROL METHODS ----------------------------------------------------

    /**
     * Recursively disables all buttons within the interaction containers.
     */
    public void disableButtons() {
        setEnabledRecursive(npcContainer, false);
        setEnabledRecursive(itemContainer, false);
        setEnabledRecursive(exitContainer, false);
    }

    // INTERNAL HELPERS ------------------------------------------------------

    /**
     * Helper method to create a titled section panel.
     * @param title The title of the section border.
     * @return A styled JPanel.
     */
    private JPanel createSection(String title) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), title));
        p.setMinimumSize(new Dimension(300, 80));
        return p;
    }

    /**
     * Recursively sets the enabled state of a container and all its children.
     * @param container The parent container.
     * @param enabled The target state (true/false).
     */
    private void setEnabledRecursive(Container container, boolean enabled) {
        for (Component c : container.getComponents()) c.setEnabled(enabled);
    }
}