package main.view.panels;

import javax.swing.*;
import java.awt.*;

/**
 * A side panel responsible for displaying the main character's real-time status.
 */
public class StatusPanel extends JPanel {

    // UI COMPONENTS ---------------------------------------------------------
    
    // Vital Stats Bars
    private JProgressBar barEnergy, barSatiety, barHydration, barHygiene;
    private JLabel levelLabel;
    private JProgressBar xpBar;
    private JProgressBar affMum, affDad, affBro;

    // CONSTRUCTOR ------------------------------------------------------------------

    /**
     * Constructs the StatusPanel and organizes the layout.
     */
    public StatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(220, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        // Colour definition 
        Color energyCol = new Color(255, 209, 80);
        Color satietyCol = new Color(234, 168, 183);
        Color thirstCol = new Color(126, 196, 203);
        Color hygieneCol = new Color(72, 179, 175);
        
        Color lvlCol = new Color(240, 143, 160);

        Color mumCol = new Color(203, 161, 191);
        Color dadCol = new Color(71, 110, 174);
        Color broCol = new Color(246, 191, 184);

        // Vital Parameters Section
        JPanel statsBox = createBox("Parametri Vitali");
        barEnergy = createBar(energyCol);
        barSatiety = createBar(satietyCol);
        barHydration = createBar(thirstCol);
        barHygiene = createBar(hygieneCol);
        
        statsBox.add(createPair("Energia", barEnergy));
        statsBox.add(createPair("Sazietà", barSatiety));
        statsBox.add(createPair("Idratazione", barHydration));
        statsBox.add(createPair("Igiene", barHygiene));

        // Experience/Level Section
        JPanel lvlBox = createBox("Esperienza");
        levelLabel = new JLabel("Livello: 1");
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        xpBar = createBar(lvlCol);
        xpBar.setString("XP");
        
        lvlBox.add(levelLabel);
        lvlBox.add(Box.createVerticalStrut(5));
        lvlBox.add(xpBar);

        // Affinities Section
        JPanel affBox = createBox("Affinità");
        affMum = createBar(mumCol);
        affDad = createBar(dadCol);
        affBro = createBar(broCol);
        
        affBox.add(createPair("Mamma", affMum));
        affBox.add(createPair("Papà", affDad));
        affBox.add(createPair("Fratello", affBro));

        // Add sections to main layout
        add(statsBox);
        add(Box.createVerticalStrut(15));
        add(lvlBox);
        add(Box.createVerticalStrut(15));
        add(affBox);
    }

    // UPDATE METHODS ----------------------------------------------------------

    /**
     * Updates the visual bars for the character's vital parameters.
     * @param e Energy value (0-100).
     * @param s Satiety value (0-100).
     * @param h Hydration value (0-100).
     * @param hy Hygiene value (0-100).
     */
    public void updateStats(int e, int s, int h, int hy) {
        barEnergy.setValue(e); 
        barSatiety.setValue(s);
        barHydration.setValue(h); 
        barHygiene.setValue(hy);
    }

    /**
     * Updates the character's level and experience bar.
     * @param lvl The current character level.
     * @param xp The current experience points accumulated.
     * @param max The experience points required to reach the next level.
     */
    public void updateLevel(int lvl, int xp, int max) {
        levelLabel.setText("Livello: " + lvl);
        xpBar.setMaximum(max); 
        xpBar.setValue(xp);
        xpBar.setString(xp + " / " + max);
    }

    /**
     * Updates the affinity bars.
     * @param m Affinity value for Mum.
     * @param d Affinity value for Dad.
     * @param b Affinity value for Brother.
     */
    public void updateAffinities(int m, int d, int b) {
        affMum.setValue(m); 
        affDad.setValue(d); 
        affBro.setValue(b);
    }

    // --- UI HELPER METHODS -----------------------------------------------------

    /**
     * Creates a vertical box panel with a titled border.
     * @param title The text to display in the border title.
     * @return A styled JPanel.
     */
    private JPanel createBox(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createTitledBorder(title));
        return p;
    }
    
    /**
     * Creates a standardized progress bar with a specific color.
     * @param c The foreground color of the bar.
     * @return A styled JProgressBar.
     */
    private JProgressBar createBar(Color c) {
        JProgressBar b = new JProgressBar(0, 100);
        b.setStringPainted(true); 
        b.setForeground(c);
        return b;
    }

    /**
     * Creates a labeled pair containing a text label and a progress bar.
     * @param lbl The text label.
     * @param bar The progress bar component.
     * @return A JPanel containing the label and bar.
     */
    private JPanel createPair(String lbl, JProgressBar bar) {
        JPanel p = new JPanel(new BorderLayout());
        JLabel l = new JLabel(lbl);
        l.setFont(new Font("Arial", Font.PLAIN, 11));
        
        p.add(l, BorderLayout.NORTH);
        p.add(bar, BorderLayout.CENTER);
        p.add(Box.createVerticalStrut(5), BorderLayout.SOUTH); // Spacing
        return p;
    }
}