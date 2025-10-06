package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;
import main.giuseppetti.interfaces.CriterioCompletamento;

public class Missione {
    private final String nome;
    private final String descrizione;
    private final NPC npcAssegnato;
    private List<CriterioCompletamento> criteri; 
    private boolean completata;

    public Missione(String nome, String descrizione, NPC npcAssegnato) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.npcAssegnato = npcAssegnato;
        this.criteri = new ArrayList<>();
        this.completata = false;
    }

    // Aggiungi un criterio alla missione
    public void aggiungiCriterio(CriterioCompletamento criterio) {
        this.criteri.add(criterio);
    }

    // Verifica se TUTTI i criteri sono soddisfatti
    public boolean verificaCompletamento() {
        if (completata) return true;
        
        for (CriterioCompletamento criterio : criteri) {
            if (!criterio.verificaCompletamento()) {
                return false;
            }
        }
        
        completata = true;
        npcAssegnato.incrementaAffinita();
        return true;
    }

    // GETTER

    public String getNome() {
        return this.nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

     public NPC getNPCAssegnato() {
        return this.npcAssegnato;
    }
    
    public List<CriterioCompletamento> getCriteri() {
        return this.criteri;
    }


    public boolean isCompletata() {
        return this.completata;
    }
}