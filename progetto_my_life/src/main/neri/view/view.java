package main.neri.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import java.util.stream.Collectors;

import main.aboufaris.interfaces.*;
import main.giuseppetti.classes.*;
import main.neri.classe.*;

/**
 * Implementazione grafica minimale (Swing) per il progetto "My Life".
 * Aggiornata con metodi placeholder e un menu iniziale grafico.
 */
public class View {

    private JFrame frame;
    private JLabel stanzaLabel;
    private JTextArea descrizioneArea;
    private JTextArea logArea;
    private JPanel azioniPanel;
    private DefaultListModel<String> oggettiModel;
    private JList<String> oggettiList;
    private JTextArea statoArea;

    // Menu iniziale
    private JDialog menuDialog;

    public View(){
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private void createAndShowGUI(){
        frame = new JFrame("My Life - Interfaccia");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 600));

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

        oggettiModel = new DefaultListModel<>();
        oggettiList = new JList<>(oggettiModel);
        JScrollPane objScroll = new JScrollPane(oggettiList);
        objScroll.setBorder(BorderFactory.createTitledBorder("Oggetti in stanza"));
        center.add(objScroll);

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
            menuDialog.setSize(400, 300);
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

    public void mostraStanza(String nome, Stanza stanza){
        SwingUtilities.invokeLater(() -> {
            stanzaLabel.setText("Stanza: " + (nome==null?"--":nome));
            if(stanza!=null){
                try{
                    StringBuilder sb = new StringBuilder();
                    sb.append("Descrizione stanza: \n");
                    if(stanza.getDescrizione()!=null) sb.append(stanza.getDescrizione()).append('\n');
                    descrizioneArea.setText(sb.toString());
                }catch(Exception ex){
                    descrizioneArea.setText("(descrizione non disponibile)");
                }
            } else {
                descrizioneArea.setText("");
            }
        });
    }

    public void mostraMissioniAttive(String Nome, String Descrizione){
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

    public void mostraBisogni(){
        SwingUtilities.invokeLater(() -> {
            appendLog("Aggiornamento bisogni...");
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

    public void mostraOpzioni(List<OpzioniInterazione> opzinioni){
        SwingUtilities.invokeLater(() -> {
            if(opzinioni==null) return;
            String txt = opzinioni.stream().map(o->o.toString()).collect(Collectors.joining("\n"));
            appendLog("Opzioni: \n" + txt);
            JOptionPane.showMessageDialog(frame, txt, "Opzioni Interazione", JOptionPane.PLAIN_MESSAGE);
        });
    }

    public void mostraCasa(){
        SwingUtilities.invokeLater(() -> appendLog("Sei a casa."));
    }

    public void mostraStanza(){
        SwingUtilities.invokeLater(() -> appendLog("Cambio stanza."));
    }

    public void mostraVittoria(){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Hai vinto!", "Vittoria", JOptionPane.INFORMATION_MESSAGE));
    }

    public void mostraSconfitta(){
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Hai perso...", "Sconfitta", JOptionPane.INFORMATION_MESSAGE));
    }

    public void mostraOggettiInStanza(List<OggettoGioco> oggettiPresenti){
        SwingUtilities.invokeLater(() -> {
            oggettiModel.clear();
            if(oggettiPresenti!=null){
                for(OggettoGioco o : oggettiPresenti){
                    try{
                        oggettiModel.addElement(o.getNome());
                    }catch(Exception ex){
                        oggettiModel.addElement(o.toString());
                    }
                }
            }
        });
    }

    public void mostraNpc(NPC npcInStanza){
        SwingUtilities.invokeLater(() -> {
            if(npcInStanza!=null)
                appendLog("NPC presente: " + npcInStanza.getNome());
        });
    }

    public void aggiornaStanza(Stanza stanzaCorrente) {
        SwingUtilities.invokeLater(() -> {
            if(stanzaCorrente!=null){
                try{
                    mostraStanza(stanzaCorrente.getNome(), stanzaCorrente);
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
}
