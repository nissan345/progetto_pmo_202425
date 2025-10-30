package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

import main.fabbri.classes.Personaggio;
import main.giuseppetti.interfaces.CondizioneCompletamento;

public class Missione {
    private final String nome;
    private final String descrizione;
    private final NPC npcAssegnatore;
    private int puntiAffinita;
    private List<CondizioneCompletamento> condizioni; 
    private boolean completata;

    public Missione(String nome, String descrizione, NPC npcAssegnatore, int puntiAffinita, List<CondizioneCompletamento> condizioni) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.npcAssegnatore = npcAssegnatore;
        this.puntiAffinita = puntiAffinita; 
        this.condizioni = new ArrayList<>(condizioni);
        this.completata = false;
    }
    
    // Verifica il completamento di una missione 
    public boolean verificaCompletamento(Personaggio personaggio) {
        if (completata) {
            return true;
        }
        
        if (personaggio == null) {
            return false;
        }
        
        // Se tutte le condizioni sono soddisfatte, la missione è completata
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

    public String getNome() {
        return this.nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

     public NPC getNPCAssegnatore() {
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