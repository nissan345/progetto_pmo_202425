package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import main.aboufaris.interfaces.Room;

import main.fabbri.classes.*;
import main.fabbri.classes.MainCharacter;

public abstract class NPC {
    private final String relazione;
    private final Room posizione; 
    private int affinita;

    private List<Quest> questiDisponibili;

    private List<OpzioniInterazione> opzioni;

    public NPC(final String relazione, final Room s) {
        this.relazione = relazione;
        this.posizione = s; 
        this.affinita = 0;
        this.questiDisponibili = new ArrayList<>();
        this.opzioni = new ArrayList<>();
        inizializzaQuesti();
    }

    // Metodi Astratti
    
    // Metodo per il dialogo iniziale con un NPC
    public abstract String getDialogoIniziale();
    
    // Metodo per il dialogo quando un NPC assegna una quest
    public abstract String getQuestAssegnataDialogo(Quest quest);
    
    // Metodo per il dialogo quando una quest non è stata ancora completata
    public abstract String getDialogoQuestInCorso(Quest quest);
    
    // Metodo per il dialogo quando una quest è stata completata 
    public abstract String getDialogoCompletamentoQuest(Quest quest);
    
    // Metodo per l'inizializzazione della quest
    protected abstract void inizializzaQuesti();

    
    // Metodi concreti 
    
    // Gestisce l'interazione tra personaggio e NPC

    public List<OpzioniInterazione> getOpzioniDisponibili(MainCharacter personaggio) {
        this.opzioni.clear();

        Optional<Quest> questCompletata = personaggio.getCompletedQuestWithNPC(this);
        Optional<Quest> questAttiva = personaggio.getOngoingQuestWithNPC(this);

        if (questCompletata.isPresent()) {
            this.opzioni.add(OpzioniInterazione.CONSEGNA_MISSIONE);

        } else if (questAttiva.isPresent()) {
            this.opzioni.add(OpzioniInterazione.MISSIONE_IN_CORSO);

        } else if (!questiDisponibili.isEmpty()) {
        	
        }else {
            this.opzioni.add(OpzioniInterazione.CHIEDI_MISSIONE);
        }

        this.opzioni.add(OpzioniInterazione.ESCI);
        return this.opzioni;
    }
    

    public Quest assegnaQuest(MainCharacter personaggio) {
    	if(personaggio.hasOngoingQuestWithNPC(this) || questiDisponibili.isEmpty()) {
    		return null;
    	}
    	Quest quest = questiDisponibili.remove(0);
    	personaggio.addQuest(quest);
    	return quest;
    }

    public List<String> consegnaQuest(MainCharacter personaggio) {
        List<String> messaggi = new ArrayList<>();
        Optional<Quest> questCompletata = personaggio.getCompletedQuestWithNPC(this);

        if (questCompletata.isEmpty()) {
            messaggi.add("Non ci sono questi completate con " + this.relazione);
            return messaggi;
        }
        
        Quest quest = questCompletata.get();
        incrementaAffinita(quest.getPuntiAffinita());
        personaggio.removeQuest(quest);
        
        messaggi.add("Quest '" + quest.getName() + "' completata!");

        messaggi.add("Affinità con " + this.relazione + ": " + this.affinita + "/100");
        return messaggi;
    }
    

    // Si aggiunge una quest a quelle disponibili 
    protected void addQuest(Quest quest) {
        questiDisponibili.add(quest);

    }
    
    // Incrementa l'affinita tra un personaggio e un NPC, l'affinita va da 0 a 100
    protected void incrementaAffinita(int puntiAffinita) {
        this.affinita = Math.min(100, this.affinita + puntiAffinita);
    }

    // Getter
    public String getRelazione() { 
    	return this.relazione; 
    }
    
    public int getAffinita() { 
    	return this.affinita; 
    }
    

    public Room getCurrentRoom() { 
    	return this.posizione; 
    }
    
    public List<Quest> getQuestiDisponibili() { 
        return new ArrayList<>(this.questiDisponibili); 

    }
}