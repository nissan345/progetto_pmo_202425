package main.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents the initial menu screen of the application "My Life Simulator".
 * <p>
 * This panel serves as the entry point for the user, providing options to 
 * start a new game session or exit the application. It uses a {@link GridBagLayout} 
 * to center the title and buttons visually.
 * </p>
 */
public class MenuPanel extends JPanel {
	
    // --- CONSTRUCTOR -----------------------------------------------------------

    /**
     * Constructs the MenuPanel and initializes the user interface.
     * Sets up the background, title, and navigation buttons.
     * * @param onStart The action listener to execute when the "GIOCA" (Play) button is clicked.
     * @param onExit  The action listener to execute when the "ESCI" (Exit) button is clicked.
     */
	public MenuPanel(ActionListener onStart, ActionListener onExit) {
		// Center everything
        setLayout(new GridBagLayout());
        
        // Background color
        setBackground(new Color(240, 248, 255)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        // Space 
        gbc.insets = new Insets(10, 10, 30, 10); 

        // Title
        JLabel title = new JLabel("MY LIFE SIMULATOR");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        add(title, gbc);

        // Buttons
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(createButton("GIOCA", onStart), gbc);

        gbc.gridy++;
        add(createButton("ESCI", onExit), gbc);
	}
	
    // --- UI HELPERS ------------------------------------------------------------

	/**
	 * Helper method to create a styled button with consistent properties.
     * * @param text   The text label to display on the button.
	 * @param action The specific action to trigger when the button is pressed.
	 * @return A fully configured {@link JButton}.
	 */
    private JButton createButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setPreferredSize(new Dimension(200, 50));
        btn.addActionListener(action);
        return btn;
    }

}