package main.view;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.function.IntConsumer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.aboufaris.interfaces.*;
import main.control.Control;
import main.fabbri.classes.Personaggio;
import main.giuseppetti.classes.*;
import main.neri.classes.*;

public class View {
	
	private JFrame frame;
    private JLabel stanzaLabel;
    private JTextArea descrizioneArea;
    private JTextArea logArea;
    private JTextArea oggettiArea;
    private JTextArea statoArea;
    private JPanel azioniPanel;
    private JPanel azioniFissePanel;
    private JPanel azioniContextPanel;
    private JPanel oggettiPanel;
    private DefaultListModel oggettiModel;
    private JList oggettiList;
    
    private Control controller;
	
	public void setController(Control control) {
		this.controller = control;
	}
	
	public View() {
		createAndShowGUI();
	}
	

	private void createAndShowGUI(){
	    frame = new JFrame("My Life");
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout(20, 20));

	    frame.add(buildTop(),    BorderLayout.NORTH);
	    frame.add(buildCenter(), BorderLayout.CENTER);  // aggiungi CENTER UNA sola volta
	    frame.add(buildRight(),  BorderLayout.EAST);
	    frame.add(buildBottom(), BorderLayout.SOUTH);

	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
	


	private JPanel buildTop() {
	    JPanel top = new JPanel(new BorderLayout(6,6));
	    top.setBorder(new EmptyBorder(0, 0, 6, 0));
	    
        stanzaLabel = new JLabel("Stanza: --");
        stanzaLabel.setFont(stanzaLabel.getFont().deriveFont(Font.BOLD, 18f));
        top.add(stanzaLabel, BorderLayout.NORTH);

        descrizioneArea = new JTextArea(4, 40);
        descrizioneArea.setLineWrap(true);
        descrizioneArea.setWrapStyleWord(true);
        descrizioneArea.setEditable(false);
        
        JScrollPane descScroll = new JScrollPane(descrizioneArea);
        descScroll.setBorder(BorderFactory.createTitledBorder("Descrizione"));
        top.add(descScroll, BorderLayout.CENTER);

	    return top;
	}

	private JPanel buildCenter() {
	    JPanel center = new JPanel(new GridLayout(2, 2, 8, 8));
	    // Log / Messaggi
        logArea = new JTextArea(12, 30);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(Color.WHITE);

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Log / Messaggi"));
        center.add(logScroll);

        // Oggetti in stanza 
        oggettiPanel = new JPanel();
        oggettiPanel.setLayout(new BoxLayout(oggettiPanel, BoxLayout.Y_AXIS));
        JScrollPane oggettiScroll = new JScrollPane(oggettiPanel);
        oggettiScroll.setBorder(BorderFactory.createTitledBorder("Oggetti in stanza"));
        center.add(oggettiScroll);
        
        return center;
	}

	private JComponent buildBottom() {
		azioniFissePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
	    azioniContextPanel = new JPanel(new GridLayout(0, 3, 6, 6));

	    // Bottone mappa che NON verrà mai toccato
	    JButton mappaBtn = new JButton("Mappa");
	    mappaBtn.addActionListener(e -> mostraCasa());
	    azioniFissePanel.add(mappaBtn);

	    JPanel container = new JPanel(new BorderLayout(6, 6));
	    container.add(azioniFissePanel, BorderLayout.NORTH);
	    container.add(azioniContextPanel, BorderLayout.CENTER);

	    JScrollPane scroll = new JScrollPane(container);
	    scroll.setBorder(BorderFactory.createTitledBorder("Azioni"));
	    scroll.setPreferredSize(new Dimension(100, 120));
	    return scroll;
	}

	private JPanel buildRight() {
		JPanel right = new JPanel(new BorderLayout(6, 6));
        right.setBorder(BorderFactory.createTitledBorder("Stato Personaggio / Missioni"));

        statoArea = new JTextArea(10, 20);
        statoArea.setEditable(false);
        statoArea.setLineWrap(true);
        statoArea.setWrapStyleWord(true);
        statoArea.setBackground(Color.WHITE);

        right.add(new JScrollPane(statoArea), BorderLayout.CENTER);
        return right;
	}	
	
	public void mostraMenu(){
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
    	// Converti la lista in array per JOptionPanel
        String[] opzioniArray = opzioni.toArray(new String[0]);
        
        // Mostra il dialog di selezione
        Object selezione = JOptionPane.showInputDialog(
            frame,
            messaggio,
            "Personalizzazione Personaggio",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opzioniArray,
            opzioniArray[0] 
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
        return -1;
	}
	
	public void mostraCasa(){
		JDialog mappaDialog = new JDialog(frame, "Mappa della Casa", true);
        mappaDialog.setSize(600, 400);
        mappaDialog.setLayout(new BorderLayout());
        
        JLabel titolo = new JLabel("Seleziona una Stanza", SwingConstants.CENTER);
        titolo.setFont(new Font("Arial", Font.BOLD, 18));
        mappaDialog.add(titolo, BorderLayout.NORTH);
        
        // Pannello centrale con la mappa (layout a griglia per le stanze)
        JPanel mappaPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // 2 righe, 3 colonne
        mappaPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Lista delle stanze disponibili (puoi personalizzare questa lista)
        String[] stanze = {"Bagno", "Camera da Letto", "Cucina", "Salotto", "Giardino", "Sgabuzzino"};
        
        for (String stanza : stanze) {
            JButton stanzaBtn = new JButton(stanza);
            stanzaBtn.setFont(new Font("Arial", Font.PLAIN, 14));
            stanzaBtn.setPreferredSize(new Dimension(120, 60));
            
            // Aggiungi l'action listener per entrare nella stanza
            stanzaBtn.addActionListener(e -> {
                Control controller = Control.getControlInstance();
                controller.onClickEntra(stanza);
                mappaDialog.dispose(); // Chiudi la mappa dopo la selezione
            });
            
            mappaPanel.add(stanzaBtn);
        }
        
        mappaDialog.add(mappaPanel, BorderLayout.CENTER);
        
        // Pulsante per chiudere
        JButton chiudiBtn = new JButton("Chiudi Mappa");
        chiudiBtn.addActionListener(e -> mappaDialog.dispose());
        mappaDialog.add(chiudiBtn, BorderLayout.SOUTH);
        
        mappaDialog.setLocationRelativeTo(frame);
        mappaDialog.setVisible(true);
        
	}

	public void mostraStanza(String nome, String descrizione){
		stanzaLabel.setText("Stanza: " + (nome == null ? "--" : nome));
	    descrizioneArea.setText(descrizione == null ? "" : descrizione);
	    appendLog("Entrato in stanza: " + nome);
	}
	public void mostraOggettiInStanza(List<String> labels, IntConsumer onClickIndex) {
	    SwingUtilities.invokeLater(() -> {
	        if (oggettiPanel == null) return;

	        oggettiPanel.removeAll();

	        if (labels == null || labels.isEmpty()) {
	            JLabel none = new JLabel("Nessun oggetto in questa stanza");
	            none.setAlignmentX(Component.LEFT_ALIGNMENT);
	            oggettiPanel.add(none);
	        } else {
	            for (int i = 0; i < labels.size(); i++) {
	                final int idx = i;
	                String testo = labels.get(i);

	                JButton btn = new JButton(testo);
	                btn.setAlignmentX(Component.LEFT_ALIGNMENT);
	                btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
	                btn.setToolTipText("Clicca per usare " + testo);

	                btn.addActionListener(e -> {
	                    if (onClickIndex != null) onClickIndex.accept(idx);
	                });

	                oggettiPanel.add(btn);
	                oggettiPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	            }
	        }

	        oggettiPanel.revalidate();
	        oggettiPanel.repaint();
	    });
	}
	
	public void clearAzioniNpc() {
	    SwingUtilities.invokeLater(() -> {
	        azioniContextPanel.removeAll();
	        azioniContextPanel.revalidate();
	        azioniContextPanel.repaint();
	    });
	}
	
	private void appendLog(String msg) {
	    if (logArea == null) return;

	    SwingUtilities.invokeLater(() -> {
	        logArea.append(msg + "\n");
	        logArea.setCaretPosition(logArea.getDocument().getLength());
	    });
	}
	
	public void mostraNpcInterattivi(String nomeNpc,
            String relazioneNpc,
            Runnable onDialogo,
            Runnable onOpzioni) {
		SwingUtilities.invokeLater(() -> {
		azioniContextPanel.removeAll();
		
		JButton dialogoBtn = new JButton("Parla con " + nomeNpc);
		dialogoBtn.addActionListener(e -> { if (onDialogo != null) onDialogo.run(); });
		
		JButton opzioniBtn = new JButton("Opzioni con " + relazioneNpc);
		opzioniBtn.addActionListener(e -> { if (onOpzioni != null) onOpzioni.run(); });
		
		// AGGIUNGI QUI, NON su azioniPanel
		azioniContextPanel.add(dialogoBtn);
		azioniContextPanel.add(opzioniBtn);
		
		azioniContextPanel.revalidate();
		azioniContextPanel.repaint();
		});
		}

	public int mostraAzioni(List<String> a){return 1;}
	
    public void mostraMissioneAttiva(String nome, String descrizione) {
    	 SwingUtilities.invokeLater(() -> {
             appendLog("Missione attiva: " + nome + " - " + descrizione);
             statoArea.append("Missione: " + nome + " - " + descrizione + "\n");
         });
    }
    public void aggiornaStatoPersonaggio(String stato){
    	SwingUtilities.invokeLater(() -> {
            statoArea.setText(stato);
        });
    }
    
    public void mostraStatistiche(String stato) {
    	SwingUtilities.invokeLater(() -> {
            statoArea.setText(stato);
        });
    }
    
    public void mostraAvviso(String a){}
    public void mostraErrore(String errore){}
    public void mostraMessaggio(String s){
    	SwingUtilities.invokeLater(() -> appendLog(s));
    }
    
    public int mostraOpzioniIndice(String titolo, String messaggio, List<String> labels) {
        if (labels == null || labels.isEmpty()) {
            mostraMessaggio("Nessuna opzione disponibile");
            return -1;
        }

        String[] array = labels.toArray(String[]::new);
        int scelta = JOptionPane.showOptionDialog(
                frame,
                messaggio,
                titolo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                array,
                array[0]
        );

        if (scelta >= 0 && scelta < labels.size()) {
            appendLog("Scelta: " + array[scelta]);
            return scelta;
        } else {
            appendLog("Interazione annullata");
            return -1; 
        }
    }
    
    public <T> Optional<T> mostraDialogSceltaGenerica(String titolo, String messaggio, List<T> opzioni) {
        if (opzioni == null || opzioni.isEmpty()) return Optional.empty();
        String[] labels = opzioni.stream().map(Object::toString).toArray(String[]::new);

        int scelta = JOptionPane.showOptionDialog(
                frame, messaggio, titolo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, labels, labels[0]);

        if (scelta >= 0 && scelta < opzioni.size()) return Optional.of(opzioni.get(scelta));
        return Optional.empty();
    }
    
   
    public void mostraVittoria(){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Hai vinto!", "Vittoria", JOptionPane.INFORMATION_MESSAGE));
    }

    public void mostraSconfitta(){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Hai perso...", "Sconfitta", JOptionPane.INFORMATION_MESSAGE));
    }
    
}
