package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel{
	
	public MainMenuPanel(ActionListener onStart, ActionListener onExit) {
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

	/**
	 * 
	 * @param text
	 * @param action
	 * @return
	 */
    private JButton createButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setPreferredSize(new Dimension(200, 50));
        btn.addActionListener(action);
        return btn;
    }

}
