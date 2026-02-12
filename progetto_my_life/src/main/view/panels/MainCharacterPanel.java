package main.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;

/**
 * Handles the "Wizard" style flow for creating a new character.
 * <p>
 * This panel uses a {@link CardLayout} to guide the user through three sequential steps:
 * <ol>
 * <li>Name Entry</li>
 * <li>Hair Selection</li>
 * <li>Outfit Selection</li>
 * </ol>
 * Data is accumulated locally and sent to the Controller via the {@link CharacterCreatorListener}
 * only when the final step is completed.
 * </p>
 */
public class MainCharacterPanel extends JPanel {

    // --- CONSTANTS (Card Identifiers) ------------------------------------------
    private static final String CARD_NAME = "NAME";
    private static final String CARD_HAIR = "HAIR";
    private static final String CARD_OUTFIT = "OUTFIT";

    // --- UI COMPONENTS ---------------------------------------------------------
    private CardLayout cardLayout;
    
    // --- TEMPORARY STATE (Data Accumulation) -----------------------------------
    private String tempName;
    private Hair tempHair;
    private Outfit tempOutfit;

    // --- EVENTS & LISTENER -----------------------------------------------------
    
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

    // --- CONSTRUCTOR -----------------------------------------------------------

    /**
     * Constructs the character creation panel.
     * Initializes the CardLayout and builds the three sub-panels (Name, Hair, Outfit).
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

    // --- UI CONSTRUCTION METHODS -----------------------------------------------

    /**
     * Creates the first screen for entering the character's name.
     * Uses a custom {@link GridBagLayout} for centering elements.
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

        // Layout Logic
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

        // Action Logic (Click and Enter Key)
        ActionListener submitAction = e -> {
            String text = nameField.getText().trim();
            if(!text.isEmpty()) {
                this.tempName = text; // Save name
                cardLayout.show(this, CARD_HAIR); // Proceed to next screen
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
     * Used for both Hair and Outfit selection to avoid code duplication.
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
        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20)); // 2 columns, 20px gap
        grid.setBorder(BorderFactory.createEmptyBorder(20, 100, 100, 100)); // Wide margins

        for (T option : options) {
            JButton btn = new JButton(option.toString());
            btn.setFont(new Font("Arial", Font.PLAIN, 20));
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(0, 60)); // Taller buttons
            
            btn.addActionListener(e -> onSelection.accept(option));
            grid.add(btn);
        }
        
        selectionPanel.add(grid, BorderLayout.CENTER);
        return selectionPanel;
    }

    /**
     * Helper method to create buttons with consistent styling.
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

    // --- EVENT HANDLERS --------------------------------------------------------

    /**
     * Handles the selection of a hairstyle.
     * Saves the choice locally and advances to the Outfit screen.
     * @param hair The selected hair enum.
     */
    private void handleHairSelection(Hair hair) {
        this.tempHair = hair;
        // Proceed to next screen (Outfit)
        cardLayout.show(this, CARD_OUTFIT);
    }

    /**
     * Handles the selection of an outfit.
     * This is the final step: saves the choice and notifies the main Listener.
     * @param outfit The selected outfit enum.
     */
    private void handleOutfitSelection(Outfit outfit) {
        this.tempOutfit = outfit;
        
        // ALL DATA COLLECTED -> Notify Controller via Listener
        if (listener != null) {
            listener.onCreate(tempName, tempOutfit, tempHair);
        }
    }
}