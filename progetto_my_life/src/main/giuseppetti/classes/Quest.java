package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

import main.fabbri.classes.Character;
import main.giuseppetti.interfaces.CondizioneCompletamento;

public class Quest {
    private final String name;
    private final String description;
    private final NPC npcAssegnatore;
    private int puntiAffinita;
    private List<CondizioneCompletamento> condizioni; 
    private boolean completata;

    public Quest(String name, String description, NPC npcAssegnatore, int puntiAffinita, List<CondizioneCompletamento> condizioni) {
        this.name = name;
        this.description = description;
        this.npcAssegnatore = npcAssegnatore;
        this.puntiAffinita = puntiAffinita; 
        this.condizioni = new ArrayList<>(condizioni);
        this.completata = false;
    }
    
    // Verifica il completamento di una quest 
    public boolean verificaCompletamento(Character personaggio) {
        if (completata) {
            return true;
        }
        
        if (personaggio == null) {
            return false;
        }
        
        // Se tutte le condizioni sono soddisfatte, la quest è completata
        for (CondizioneCompletamento condizione : condizioni) {
            if (!condizione.verificaCompletamento(personaggio)) {
                return false;
            }
        }
        
        // Marca come completata solo quando tutte le condizioni sono soddisfatte
        this.completata = true;
        return true;
    }
   
    // GETTER

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

     public NPC getAssigningNPC() {
        return this.npcAssegnatore;
    }
     
    public int getPuntiAffinita() {
    	return this.puntiAffinita;
    }
    
    public List<CondizioneCompletamento> getCondizioni() {
        return this.condizioni;
    }


    public boolean isCompletata() {
        return this.completata;
    }
}