package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import main.aboufaris.interfaces.Room;
import main.fabbri.classes.Character;

public abstract class NPC {
    private final String relazione;
    private final Room posizione; 
    private int affinita;
    private List<Mission> missioniDisponibili;
    private List<OpzioniInterazione> opzioni;

    public NPC(final String relazione, final Room s) {
        this.relazione = relazione;
        this.posizione = s; 
        this.affinita = 0;
        this.missioniDisponibili = new ArrayList<>();
        this.opzioni = new ArrayList<>();
        inizializzaMissioni();
    }

    // Metodi Astratti
    
    // Metodo per il dialogo iniziale con un NPC
    public abstract String getDialogoIniziale();
    
    // Metodo per il dialogo quando un NPC assegna una mission
    public abstract String getMissionAssegnataDialogo(Mission mission);
    
    // Metodo per il dialogo quando una mission non è stata ancora completata
    public abstract String getDialogoMissionInCorso(Mission mission);
    
    // Metodo per il dialogo quando una mission è stata completata 
    public abstract String getDialogoCompletamentoMission(Mission mission);
    
    // Metodo per l'inizializzazione della mission
    protected abstract void inizializzaMissioni();
    
    // Metodi concreti 
    
    // Gestisce l'interazione tra personaggio e NPC
    public List<OpzioniInterazione> getOpzioniDisponibili(Character personaggio) {
        this.opzioni.clear();

        Optional<Mission> missionCompletata = personaggio.getCompletedMissionWithNPC(this);
        Optional<Mission> missionAttiva = personaggio.getOngoingMissionWithNPC(this);

        if (missionCompletata.isPresent()) {
            this.opzioni.add(OpzioniInterazione.CONSEGNA_MISSIONE);

        } else if (missionAttiva.isPresent()) {
            this.opzioni.add(OpzioniInterazione.MISSIONE_IN_CORSO);

        } else if (!missioniDisponibili.isEmpty()) {
            this.opzioni.add(OpzioniInterazione.CHIEDI_MISSIONE);
        }

        this.opzioni.add(OpzioniInterazione.ESCI);
        return this.opzioni;
    }
    
    public Mission assegnaMission(Character personaggio) {
    	if(personaggio.hasOngoingMissionWithNPC(this) || missioniDisponibili.isEmpty()) {
    		return null;
    	}
    	Mission mission = missioniDisponibili.remove(0);
    	personaggio.addMission(mission);
    	return mission;
    }

    public List<String> consegnaMission(Character personaggio) {
        List<String> messaggi = new ArrayList<>();
        Optional<Mission> missionCompletata = personaggio.getCompletedMissionWithNPC(this);

        if (missionCompletata.isEmpty()) {
            messaggi.add("Non ci sono missioni completate con " + this.relazione);
            return messaggi;
        }
        
        Mission mission = missionCompletata.get();
        incrementaAffinita(mission.getPuntiAffinita());
        personaggio.removeMission(mission);
        
        messaggi.add("Mission '" + mission.getName() + "' completata!");
        messaggi.add("Affinità con " + this.relazione + ": " + this.affinita + "/100");
        return messaggi;
    }
    
    // Si aggiunge una mission a quelle disponibili 
    protected void addMission(Mission mission) {
        missioniDisponibili.add(mission);
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
    
    public List<Mission> getMissioniDisponibili() { 
        return new ArrayList<>(this.missioniDisponibili); 
    }
}