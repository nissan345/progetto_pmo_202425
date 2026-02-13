package main.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;

/**
 * Handles the creation of the new character.
 */
public class MainCharacterPanel extends JPanel {

    // CONSTANTS -----------------------------------------------------------------
    private static final String CARD_NAME = "NAME";
    private static final String CARD_HAIR = "HAIR";
    private static final String CARD_OUTFIT = "OUTFIT";

    // UI COMPONENTS -------------------------------------------------------------
    private CardLayout cardLayout;
    
    // TEMPORARY STATE -----------------------------------------------------------
    private String tempName;
    private Hair tempHair;
    private Outfit tempOutfit;

    // EVENTS & LISTENER ------------------------------------------------------------
    
    /**
     * Interface definition for the callback invoked when character creation is finalized.
     */
    public interface CharacterCreatorListener {
        /**
         * Called when the user has finished all selection steps.
         * @param name The chosen name.
         * @param outfit The chosen outfit.
         * @param hair The chosen hairstyle.
         */
        void onCreate(String name, Outfit outfit, Hair hair);
    }

    private CharacterCreatorListener listener;

    // CONSTRUCTOR ----------------------------------------------------------------------

    /**
     * Constructs the character creation panel.
     * @param listener The listener to notify when the creation process is complete.
     */
    public MainCharacterPanel(CharacterCreatorListener listener) {
        this.listener = listener;
        
        // Setup main layout
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        // Create sub-panels
        JPanel namePanel = createNamePanel();
        JPanel hairPanel = createSelectionPanel("SCEGLI I CAPELLI", Hair.values(), this::handleHairSelection);
        JPanel outfitPanel = createSelectionPanel("SCEGLI L'OUTFIT", Outfit.values(), this::handleOutfitSelection);

        // Add cards to layout
        this.add(namePanel, CARD_NAME);
        this.add(hairPanel, CARD_HAIR);
        this.add(outfitPanel, CARD_OUTFIT);

        // Show initial card
        cardLayout.show(this, CARD_NAME);
    }

    // UI CONSTRUCTION METHODS ------------------------------------------------------

    /**
     * Creates the first screen for entering the character's name.
     * @return The constructed JPanel for name entry.
     */
    private JPanel createNamePanel() {
        JPanel namePanel = new JPanel(new GridBagLayout());
        
        JLabel label = new JLabel("INSERISCI IL TUO NOME");
        label.setFont(new Font("Monospaced", Font.BOLD, 28));
        
        JTextField nameField = new JTextField(15);
        nameField.setFont(new Font("Monospaced", Font.PLAIN, 24));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        
        JButton confirmBtn = createStyledButton("CONFERMA");

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Spacing below label
        namePanel.add(label, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        namePanel.add(nameField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0); // Spacing above button
        namePanel.add(confirmBtn, gbc);

        // Actions
        ActionListener submitAction = e -> {
            String text = nameField.getText().trim();
            if(!text.isEmpty()) {
                this.tempName = text;
                cardLayout.show(this, CARD_HAIR);
            } else {
                JOptionPane.showMessageDialog(this, "Il nome non può essere vuoto", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        };
        
        confirmBtn.addActionListener(submitAction);
        nameField.addActionListener(submitAction);

        return namePanel;
    }

    /**
     * Generically creates a selection grid based on Enum values.
     * * @param <T> The Enum type (e.g., Hair or Outfit).
     * @param title The title to display at the top of the panel.
     * @param options The array of Enum values to generate buttons for.
     * @param onSelection The consumer function to execute when an option is clicked.
     * @return The constructed JPanel containing the selection grid.
     */
    private <T> JPanel createSelectionPanel(String title, T[] options, Consumer<T> onSelection) {
        JPanel selectionPanel = new JPanel(new BorderLayout());

        JLabel titleLbl = new JLabel(title, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Monospaced", Font.BOLD, 32));
        titleLbl.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        selectionPanel.add(titleLbl, BorderLayout.NORTH);

        // Button Grid
        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 100, 100, 100));

        for (T option : options) {
            JButton btn = new JButton(option.toString());
            btn.setFont(new Font("Arial", Font.PLAIN, 20));
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(0, 60));
            
            btn.addActionListener(e -> onSelection.accept(option));
            grid.add(btn);
        }
        
        selectionPanel.add(grid, BorderLayout.CENTER);
        return selectionPanel;
    }

    /**
     * Helper method to create buttons.
     * @param text The text to display on the button.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(10, 20, 10, 20));
        return btn;
    }

    // EVENT HANDLERS ---------------------------------------------------------------

    /**
     * Handles the selection of a hairstyle.
ù     * @param hair The selected hair enum.
     */
    private void handleHairSelection(Hair hair) {
        this.tempHair = hair;
        // Proceed to next screen (Outfit)
        cardLayout.show(this, CARD_OUTFIT);
    }

    /**
     * Handles the selection of an outfit and the finalization of the character.
     * @param outfit The selected outfit enum.
     */
    private void handleOutfitSelection(Outfit outfit) {
        this.tempOutfit = outfit;
        
        if (listener != null) {
            listener.onCreate(tempName, tempOutfit, tempHair);
        }
    }
}