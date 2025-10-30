package main.view;
/*
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import main.aboufaris.interfaces.*;
import main.control.Control;
import main.fabbri.classes.*;
import main.giuseppetti.classes.*;
import main.neri.classes.*;

/**
 * Implementazione grafica minimale (Swing) per il progetto "My Life".
 * Aggiornata con metodi placeholder e un menu iniziale grafico.
 */
public class View1 {

  /*  private JFrame frame;
    private JLabel stanzaLabel;
    private JTextArea descrizioneArea;
    private JTextArea logArea;
    private JPanel azioniPanel;
    private JPanel oggettiPanel;
    private DefaultListModel<String> oggettiModel;
    private JList<String> oggettiList;
    private JTextArea statoArea;
    private Control controller;

    public void setController(Control controller) {
        this.controller = controller;
    }

    // Menu iniziale
    private JDialog menuDialog;

    public View1(){
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private void createAndShowGUI(){
        frame = new JFrame("My Life - Interfaccia");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));

        // Top: stanza
        JPanel top = new JPanel(new BorderLayout());
        stanzaLabel = new JLabel("Stanza: --");
        stanzaLabel.setFont(stanzaLabel.getFont().deriveFont(Font.BOLD, 18f));
        top.add(stanzaLabel, BorderLayout.NORTH);

        descrizioneArea = new JTextArea(4, 40);
        descrizioneArea.setLineWrap(true);
        descrizioneArea.setWrapStyleWord(true);
        descrizioneArea.setEditable(false);
        JScrollPane descScroll = new JScrollPane(descrizioneArea);
        top.add(descScroll, BorderLayout.CENTER);

        // Center: log + oggetti
        JPanel center = new JPanel(new GridLayout(1,2));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Log / Messaggi"));
        center.add(logScroll);

        
        frame.getContentPane().add(center, BorderLayout.CENTER);
        oggettiPanel = new JPanel();
        oggettiPanel.setLayout(new BoxLayout(oggettiPanel, BoxLayout.Y_AXIS));
        JScrollPane oggettiScroll = new JScrollPane(oggettiPanel);
        oggettiScroll.setBorder(BorderFactory.createTitledBorder("Oggetti in stanza"));
        center.add(oggettiScroll);
        frame.getContentPane().add(center, BorderLayout.CENTER);
        // Right: stato personaggio e missioni
        JPanel right = new JPanel(new BorderLayout());
        statoArea = new JTextArea(10,20);
        statoArea.setEditable(false);
        statoArea.setLineWrap(true);
        statoArea.setWrapStyleWord(true);
        right.add(new JScrollPane(statoArea), BorderLayout.CENTER);
        right.setBorder(BorderFactory.createTitledBorder("Stato Personaggio / Missioni"));

        // Bottom: azioni
        azioniPanel = new JPanel(new GridLayout(0,3,6,6));
        JScrollPane azioniScroll = new JScrollPane(azioniPanel);
        azioniScroll.setBorder(BorderFactory.createTitledBorder("Azioni Rapide"));
        azioniScroll.setPreferredSize(new Dimension(100,120));
        
        JButton mappaBtn = new JButton("Apri Mappa");
        mappaBtn.addActionListener(e -> mostraCasa());
        azioniPanel.add(mappaBtn);

        frame.getContentPane().add(top, BorderLayout.NORTH);
        frame.getContentPane().add(center, BorderLayout.CENTER);
        frame.getContentPane().add(right, BorderLayout.EAST);
        frame.getContentPane().add(azioniScroll, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MENU GRAFICO INIZIALE
    public void mostraMenu(){
        SwingUtilities.invokeLater(() -> {
            menuDialog = new JDialog(frame, "Menu Iniziale", true);
            menuDialog.setSize(1000, 800);
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
            menuDialog.setLocationRelativeTo(frame);
            menuDialog.setVisible(true);
        });
    }

    // Mostra azioni possibili
    public int mostraAzioni(List<String> a){
        if(a==null || a.isEmpty()) return -1;
        String[] options = a.toArray(new String[0]);
        int scelta = JOptionPane.showOptionDialog(frame,
                "Scegli un'azione:",
                "Azioni",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        return scelta;
    }

  

    public void mostraMissioneAttiva(String Nome, String Descrizione){
        SwingUtilities.invokeLater(() -> {
            appendLog("Missione attiva: " + Nome + " - " + Descrizione);
            statoArea.append("Missione: " + Nome + " - " + Descrizione + "\n");
        });
    }

    public void mostraAvviso(String a){
        SwingUtilities.invokeLater(() -> {
            appendLog("AVVISO: " + a);
            JOptionPane.showMessageDialog(frame, a, "Avviso", JOptionPane.WARNING_MESSAGE);
        });
    }

    public void mostraErrore(String errore){
        SwingUtilities.invokeLater(() -> {
            appendLog("ERRORE: " + errore);
            JOptionPane.showMessageDialog(frame, errore, "Errore", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void mostraRisultato(String getMessaggio){
        SwingUtilities.invokeLater(() -> {
            appendLog("Risultato: " + getMessaggio);
            JOptionPane.showMessageDialog(frame, getMessaggio, "Risultato", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void aggiornaStatoPersonaggio(String stato){
        SwingUtilities.invokeLater(() -> {
            statoArea.setText(stato);
        });
    }

    public void mostraMessaggio(String s){
        SwingUtilities.invokeLater(() -> appendLog(s));
    }

    public void mostraOpzioni(List<OpzioniInterazione> opzioni){
    	SwingUtilities.invokeLater(() -> {
            if(opzioni == null || opzioni.isEmpty()) {
                mostraMessaggio("Nessuna opzione disponibile");
                return;
            }
            
            // Converti le opzioni in array di stringhe
            String[] opzioniArray = opzioni.stream()
                .map(Enum::toString)
                .toArray(String[]::new);
            
            int scelta = JOptionPane.showOptionDialog(
                frame,
                "Scegli un'opzione di interazione:",
                "Opzioni Interazione con NPC",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opzioniArray,
                opzioniArray[0]
            );
            
            // Se l'utente ha selezionato un'opzione valida
            if (scelta >= 0 && scelta < opzioni.size()) {
                Control controller = Control.getControlInstance();
                // 👇 Passa solo l'opzione selezionata, non l'NPC
                controller.onSceltaOpzioneInterazione(opzioni.get(scelta));
            } else {
                mostraMessaggio("Interazione annullata");
            }
            
            appendLog("Opzioni presentate: " + String.join(", ", opzioniArray));
        });
    }

    



    public void mostraVittoria(){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Hai vinto!", "Vittoria", JOptionPane.INFORMATION_MESSAGE));
    }

    public void mostraSconfitta(){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Hai perso...", "Sconfitta", JOptionPane.INFORMATION_MESSAGE));
    }

    public void mostraOggettiInStanza(List<OggettoGioco> oggettiPresenti){
    	SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: mostraOggettiInStanza chiamato con " + 
                (oggettiPresenti != null ? oggettiPresenti.size() : "null") + " oggetti");
            
            // CONTROLLO DI SICUREZZA
            if (oggettiPanel == null) {
                System.err.println("ERRORE: oggettiPanel è ancora null!");
                return;
            }
            
            oggettiPanel.removeAll();
            
            if(oggettiPresenti != null && !oggettiPresenti.isEmpty()){
                System.out.println("DEBUG: Creazione pulsanti per oggetti:");
                for(OggettoGioco oggetto : oggettiPresenti){
                    System.out.println("  - " + oggetto.getNome());
                    
                    JButton oggettoButton = new JButton(oggetto.getNome());
                    oggettoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                    oggettoButton.setMaximumSize(new Dimension(200, 30));
                    
                    // Aggiungi tooltip
                    oggettoButton.setToolTipText("Clicca per usare " + oggetto.getNome());
                    
                    // ActionListener
                    oggettoButton.addActionListener(e -> {
                        if (controller != null) {
                            controller.onClickOggetto(oggetto);
                        } else {
                            mostraMessaggio("Hai cliccato su: " + oggetto.getNome());
                        }
                    });
                    
                    oggettiPanel.add(oggettoButton);
                    oggettiPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                }
            } else {
                JLabel nessunOggetto = new JLabel("Nessun oggetto in questa stanza");
                nessunOggetto.setAlignmentX(Component.LEFT_ALIGNMENT);
                oggettiPanel.add(nessunOggetto);
            }
            
            oggettiPanel.revalidate();
            oggettiPanel.repaint();
            System.out.println("DEBUG: Pulsanti creati e panel aggiornato");
        });
    }
    
    

    
    public void mostraNpcInterattivi(Stanza stanza) {

        // Pulisci i vecchi pulsanti NPC
        Component[] components = azioniPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (btn.getText().contains("NPC")) {
                    azioniPanel.remove(btn);
                }
            }
        }
        
        // Se c'è un NPC nella stanza, aggiungi pulsanti
        if (stanza.getNpcInStanza().isPresent()) {
            NPC npc = stanza.getNpcInStanza().get();
            
            // Primo click - Dialogo iniziale
            JButton dialogoBtn = new JButton("Parla con " + npc.getRelazione());
            dialogoBtn.addActionListener(e -> {
                Control controller = Control.getControlInstance();
                controller.onClickNpc(npc);
            });
            
            // Secondo click - Opzioni di interazione
            JButton opzioniBtn = new JButton("Opzioni con " + npc.getRelazione());
            opzioniBtn.addActionListener(e -> {
                Control controller = Control.getControlInstance();
                controller.onSecondClickNpc(npc);
            });
            
            azioniPanel.add(dialogoBtn);
            azioniPanel.add(opzioniBtn);
            
            azioniPanel.revalidate();
            azioniPanel.repaint();
        }
    }

    public void aggiornaStanza(Stanza stanzaCorrente) {
        SwingUtilities.invokeLater(() -> {
            if(stanzaCorrente!=null){
                try{
                    mostraStanza(stanzaCorrente.getNomeStanza(), stanzaCorrente);
                }catch(Exception ex){
                    stanzaLabel.setText("Stanza: (sconosciuta)");
                }
            }
        });
    }

    // Placeholder per aggiornamenti futuri del controller
    public void mostraStatistiche(Personaggio p){
        SwingUtilities.invokeLater(() -> {
            if(p==null) return;
            String stats = String.format("Energia: %d | Fame: %d | Igiene: %d | Sete: %d", p.getEnergia(), p.getFame(), p.getIgiene(), p.getSete());
            statoArea.append("\n" + stats + "\n");
            appendLog("Statistiche aggiornate per " + p.getNome());
        });
    }

    public void mostraPersonaggio(Personaggio p){
        SwingUtilities.invokeLater(() -> {
            if(p==null) return;
            appendLog("Personaggio: " + p.getNome() + " creato!");
            statoArea.setText("Nome: " + p.getNome() + "\n" + "Energia: " + p.getEnergia());
        });
    }

    // Utility
    private void appendLog(String s){
        logArea.append(s + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public String chiediNomePersonaggio() {
        JPanel panel = new JPanel();
        final JLabel label = new JLabel("Insert your Name:");
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(textField);
        
        int result = JOptionPane.showConfirmDialog(
                frame, 
                panel, 
                "Nome Personaggio", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.QUESTION_MESSAGE
            );
           return textField.getText().trim();
    }

    public int mostraOpzioniPersonalizzazione(String messaggio, List<String> opzioni) {
    	if (opzioni == null || opzioni.isEmpty()) {
            return -1;
        }
    	// Converti la lista in array per JOptionPane
        String[] opzioniArray = opzioni.toArray(new String[0]);
        
        // Mostra il dialog di selezione
        Object selezione = JOptionPane.showInputDialog(
            frame,
            messaggio,
            "Personalizzazione Personaggio",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opzioniArray,
            opzioniArray[0]  // Valore predefinito
        );
        
        // Se l'utente ha cancellato o chiuso il dialog
        if (selezione == null) {
            return -1;
        }
        
        // Trova l'indice dell'opzione selezionata
        for (int i = 0; i < opzioniArray.length; i++) {
            if (opzioniArray[i].equals(selezione)) {
                return i;
            }
        }
        return -1; // Non dovrebbe mai arrivare qui
    }


	public Object mostraDialogSceltaGenerica(String titolo, List<?> opzioni) {
		if (opzioni == null || opzioni.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Nessuna opzione disponibile.");
	        return null;
	    }

	    Object scelta = JOptionPane.showInputDialog(
	        null,
	        titolo,
	        "Scelta",
	        JOptionPane.PLAIN_MESSAGE,
	        null,
	        opzioni.toArray(),
	        opzioni.get(0)
	    );

	    return scelta;
	}

	
*/
	
}
