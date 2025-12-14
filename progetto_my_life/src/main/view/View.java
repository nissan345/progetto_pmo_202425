package main.view;

import java.util.List;

import main.model.world.Room;
import main.model.world.gameItem.Inventory;

/**
 * View class responsible for the user interface.
 */
public class View {

    // CONSTRUCTOR ------------------------------------------------------------------------
    public View() {
        // Initialize the view components here
    }

    // METHODS ---------------------------------------------------------------------------
    
    // DATA UPDATES -------------------------------------------------------------
    
    /**
     * Updates the display of the main character's stats.
     * @param energy
     * @param satiety
     * @param hydration
     * @param hygiene
     */
    public void updateStatsDisplay(int energy, int satiety, int hydration, int hygiene) {
        // TODO: Aggiornare le JProgressBar o le JLabel corrispondenti
        // es. energyBar.setValue(energy);
    }

    /**
     * Updates the inventory list display.
     * @param inventory
     */
    public void updateInventoryList(Inventory inventory) {
        // TODO: Pulire la lista attuale (JList o ComboBox) e riempirla con inventory.getItems()
    }

    /**
     * Updates the current room display.
     * @param currentRoom
     */
    public void updateCurrentRoom(Room currentRoom) {
        // TODO: Aggiornare il titolo della stanza e la lista degli oggetti presenti nella stanza
        // es. locationLabel.setText(room.getName());
    }
    
    // LOG AND DIALOGS --------------------------------------------------------

    public void appendLog(String msg) {
        // TODO: Aggiungere il testo alla JTextArea principale
        // es. gameTextArea.append(message + "\n");
    }
    // NOTA: Il controller gestisce le liste di messaggi 
    // chiamando questo metodo più volte, quindi qui basta gestire la singola stringa.

    // USER INTERACTIONS ---------------------------------------------------
    
    /**
     * Asks the user to make a choice from a list of options.
     * @param message
     * @param availableOptions
     * @return
     */
    public Object askUserChoice(String message, List<?> availableOptions) {
        // TODO: Implementare JOptionPane.showInputDialog
        // return JOptionPane.showInputDialog(..., options.toArray(), ...);
        return null; // Placeholder
    }

    /**
     * Shows a game over dialog with the given message.
     * @param string
     */
    public void showGameOverDialog(String string) {
        // TODO: Implementare JOptionPane.showMessageDialog (Errore o Info)
    }

    // UI STATE CONTROL ------------------------------------------------------
    
    /**
     * Disables all user controls in the UI.
     */
    public void disableControls() {
        // TODO: Settare setEnabled(false) su bottoni movimento, inventario, ecc.
    }
}
