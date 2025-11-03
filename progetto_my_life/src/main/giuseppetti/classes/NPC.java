package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import main.aboufaris.interfaces.Room;
<<<<<<< HEAD
import main.fabbri.classes.Personaggio;
=======
import main.fabbri.classes.Character;
>>>>>>> nicxole

public abstract class NPC {
    private final String relazione;
    private final Room posizione; 
    private int affinita;
<<<<<<< HEAD
    private List<Missione> missioniDisponibili;
=======
    private List<Quest> questiDisponibili;
>>>>>>> nicxole
    private List<OpzioniInterazione> opzioni;

    public NPC(final String relazione, final Room s) {
        this.relazione = relazione;
        this.posizione = s; 
        this.affinita = 0;
<<<<<<< HEAD
        this.missioniDisponibili = new ArrayList<>();
        this.opzioni = new ArrayList<>();
        inizializzaMissioni();
=======
        this.questiDisponibili = new ArrayList<>();
        this.opzioni = new ArrayList<>();
        inizializzaQuesti();
>>>>>>> nicxole
    }

    // Metodi Astratti
    
    // Metodo per il dialogo iniziale con un NPC
    public abstract String getDialogoIniziale();
    
<<<<<<< HEAD
    // Metodo per il dialogo quando un NPC assegna una missione
    public abstract String getMissioneAssegnataDialogo(Missione missione);
    
    // Metodo per il dialogo quando una missione non è stata ancora completata
    public abstract String getDialogoMissioneInCorso(Missione missione);
    
    // Metodo per il dialogo quando una missione è stata completata 
    public abstract String getDialogoCompletamentoMissione(Missione missione);
    
    // Metodo per l'inizializzazione della missione
    protected abstract void inizializzaMissioni();
=======
    // Metodo per il dialogo quando un NPC assegna una quest
    public abstract String getQuestAssegnataDialogo(Quest quest);
    
    // Metodo per il dialogo quando una quest non è stata ancora completata
    public abstract String getDialogoQuestInCorso(Quest quest);
    
    // Metodo per il dialogo quando una quest è stata completata 
    public abstract String getDialogoCompletamentoQuest(Quest quest);
    
    // Metodo per l'inizializzazione della quest
    protected abstract void inizializzaQuesti();
>>>>>>> nicxole
    
    // Metodi concreti 
    
    // Gestisce l'interazione tra personaggio e NPC
<<<<<<< HEAD
    public List<OpzioniInterazione> getOpzioniDisponibili(Personaggio personaggio) {
        this.opzioni.clear();

        Optional<Missione> missioneCompletata = personaggio.getMissioneCompletataConNPC(this);
        Optional<Missione> missioneAttiva = personaggio.getMissioneAttivaConNPC(this);

        if (missioneCompletata.isPresent()) {
            this.opzioni.add(OpzioniInterazione.CONSEGNA_MISSIONE);

        } else if (missioneAttiva.isPresent()) {
            this.opzioni.add(OpzioniInterazione.MISSIONE_IN_CORSO);

        } else if (!missioniDisponibili.isEmpty()) {
=======
    public List<OpzioniInterazione> getOpzioniDisponibili(Character personaggio) {
        this.opzioni.clear();

        Optional<Quest> questCompletata = personaggio.getCompletedQuestWithNPC(this);
        Optional<Quest> questAttiva = personaggio.getOngoingQuestWithNPC(this);

        if (questCompletata.isPresent()) {
            this.opzioni.add(OpzioniInterazione.CONSEGNA_MISSIONE);

        } else if (questAttiva.isPresent()) {
            this.opzioni.add(OpzioniInterazione.MISSIONE_IN_CORSO);

        } else if (!questiDisponibili.isEmpty()) {
>>>>>>> nicxole
            this.opzioni.add(OpzioniInterazione.CHIEDI_MISSIONE);
        }

        this.opzioni.add(OpzioniInterazione.ESCI);
        return this.opzioni;
    }
    
<<<<<<< HEAD
    public Missione assegnaMissione(Personaggio personaggio) {
    	if(personaggio.haMissioneAttivaConNPC(this) || missioniDisponibili.isEmpty()) {
    		return null;
    	}
    	Missione missione = missioniDisponibili.remove(0);
    	personaggio.aggiungiMissione(missione);
    	return missione;
    }

    public List<String> consegnaMissione(Personaggio personaggio) {
        List<String> messaggi = new ArrayList<>();
        Optional<Missione> missioneCompletata = personaggio.getMissioneCompletataConNPC(this);

        if (missioneCompletata.isEmpty()) {
            messaggi.add("Non ci sono missioni completate con " + this.relazione);
            return messaggi;
        }
        
        Missione missione = missioneCompletata.get();
        incrementaAffinita(missione.getPuntiAffinita());
        personaggio.rimuoviMissione(missione);
        
        messaggi.add("Missione '" + missione.getNome() + "' completata!");
=======
    public Quest assegnaQuest(Character personaggio) {
    	if(personaggio.hasOngoingQuestWithNPC(this) || questiDisponibili.isEmpty()) {
    		return null;
    	}
    	Quest quest = questiDisponibili.remove(0);
    	personaggio.addQuest(quest);
    	return quest;
    }

    public List<String> consegnaQuest(Character personaggio) {
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
>>>>>>> nicxole
        messaggi.add("Affinità con " + this.relazione + ": " + this.affinita + "/100");
        return messaggi;
    }
    
<<<<<<< HEAD
    // Si aggiunge una missione a quelle disponibili 
    protected void aggiungiMissione(Missione missione) {
        missioniDisponibili.add(missione);
=======
    // Si aggiunge una quest a quelle disponibili 
    protected void addQuest(Quest quest) {
        questiDisponibili.add(quest);
>>>>>>> nicxole
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
    
<<<<<<< HEAD
    public Room getPosizione() { 
    	return this.posizione; 
    }
    
    public List<Missione> getMissioniDisponibili() { 
        return new ArrayList<>(this.missioniDisponibili); 
=======
    public Room getCurrentRoom() { 
    	return this.posizione; 
    }
    
    public List<Quest> getQuestiDisponibili() { 
        return new ArrayList<>(this.questiDisponibili); 
>>>>>>> nicxole
    }
}