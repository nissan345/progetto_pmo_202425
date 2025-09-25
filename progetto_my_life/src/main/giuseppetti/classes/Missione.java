package main.giuseppetti.classes;

import main.giuseppetti.interfaces.CriterioCompletamento;

public class Missione {

    private final String nome;                      // nome missione 
    private final String descrizione;               // descrizione della missione
    private final NPC npcAssegnato;                 // NPC a cui appartiene la missione 
    private CriterioCompletamento criterio;         // criterio di completamento di una determinata missione
    private boolean completata;                     // stato della missione  


    public Missione(String nome, String descrizione, NPC npcAssegnato, CriterioCompletamento c) {
        this.nome = nome;
        this.descrizione = descrizione; 
        this.npcAssegnato = npcAssegnato; 
        this.criterio = c; 
        this.completata = false; 
    }

    // Verifica se la missione può essere completata
    public boolean verificaCompletamento() {
        if (!completata && criterio.verificaCompletamento()) {
            completata = true;
            npcAssegnato.incrementaAffinita();
            return true;
        }
        return false;
    }

    // GETTER e SETTER
    public String getNome() { 
        return this.nome; 
    }

    public String getDescrizione() { 
        return this.descrizione; 
    }

    public NPC getnpcAssegnato() { 
        return this.npcAssegnato; 
    }

    public boolean isCompletata() { 
        return this.completata; 
    }

    public void setCriterio(CriterioCompletamento criterio) { 
        this.criterio = criterio; 
    }

    public CriterioCompletamento getCriterio() {
       return this.criterio;
    }
}
