package main.view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import main.controller.Controller;

/**
 * A specialized panel that displays the player's current status regarding Items and Quests.
 * It acts as a passive view component, updating its content only when instructed
 * by the Controller or the main View.
 */
public class DashboardPanel extends JPanel {
    
    // ATTRIBUTES ------------------------------------------------------------
    
    private JPanel invContent;
    private JPanel questContent;
    private Controller controller;

    // CONSTRUCTOR -----------------------------------------------------------

    /**
     * Constructs the DashboardPanel and initializes the layout.
     * Sets up the two main scrollable areas with specific height weights
     */
    public DashboardPanel() {
        // Change main layout to GridBagLayout to manage weights
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(220, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        
        // --- 1. INVENTORY SECTION ---
        invContent = new JPanel();
        invContent.setLayout(new BoxLayout(invContent, BoxLayout.Y_AXIS));
        JScrollPane invScroll = new JScrollPane(invContent);
        invScroll.setBorder(BorderFactory.createTitledBorder("Inventario"));
        
        // Grid configuration
        gbc.gridy = 0;
        gbc.weighty = 0.75;
        add(invScroll, gbc);
        
        // --- 2. QUEST SECTION ---
        questContent = new JPanel();
        questContent.setLayout(new BoxLayout(questContent, BoxLayout.Y_AXIS));
        JScrollPane questScroll = new JScrollPane(questContent);
        questScroll.setBorder(BorderFactory.createTitledBorder("Quest Attive"));
        
        // Grid configuration
        gbc.gridy = 1;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(10, 0, 0, 0); // Spacing above quests
        add(questScroll, gbc);
    }

    // CONTROLLER SETUP ------------------------------------------------------

    /**
     * Sets the reference to the main application controller.
     * * @param c The controller instance.
     */
    public void setController(Controller c) { 
        this.controller = c; 
    }

    // VIEW UPDATE METHODS ---------------------------------------------------

    /**
     * Updates the inventory display with a list of item names.
     * Creates a clickable button for each item that triggers an interaction in the controller.
     * * @param items A list of strings representing the names of items in the inventory.
     */
    public void updateInventory(List<String> items) {
        invContent.removeAll();
        if (items.isEmpty()) {
            JLabel emptyLbl = new JLabel("Vuoto");
            emptyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            invContent.add(emptyLbl);
        }
        
        for (int i = 0; i < items.size(); i++) {
            final int idx = i;
            // Use the item name
            JButton btn = new JButton(items.get(i));
            // Alignment and dimension to fill the inventory panel width
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); 
            
            btn.addActionListener(e -> { 
                if(controller != null) controller.handleInventoryInteraction(idx); 
            });
            
            invContent.add(btn);
            invContent.add(Box.createVerticalStrut(5));
        }
        invContent.revalidate(); 
        invContent.repaint();
    }

    /**
     * Updates the quest display with a list of active quest descriptions.
     * * @param quests A list of strings describing the currently active quests.
     */
    public void updateQuests(List<String> quests) {
        questContent.removeAll();
        if (quests == null || quests.isEmpty()) {
            JLabel noQuestLbl = new JLabel("Nessuna quest");
            noQuestLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            questContent.add(noQuestLbl);
        } else {
            for (String q : quests) {
                JTextArea qText = new JTextArea("• " + q);
                qText.setWrapStyleWord(true);
                qText.setLineWrap(true);
                qText.setEditable(false);
                qText.setOpaque(false);
                qText.setFont(new Font("Arial", Font.ITALIC, 14));
                qText.setAlignmentX(Component.LEFT_ALIGNMENT);
                qText.setMaximumSize(new Dimension(Integer.MAX_VALUE, qText.getPreferredSize().height));
                
                questContent.add(qText);
                questContent.add(Box.createVerticalStrut(5));
  
            }
        }
        questContent.revalidate(); 
        questContent.repaint();
    }

    // UI CONTROL METHODS ----------------------------------------------------

    /**
     * Disables all interactive buttons in the inventory.
     */
    public void disableButtons() {
        for(Component c : invContent.getComponents()) c.setEnabled(false);
    }
}