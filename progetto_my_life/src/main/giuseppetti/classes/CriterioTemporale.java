/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

import main.giuseppetti.interfaces.CriterioCompletamento;

/**
 *
 * @author OS
 */
public class CriterioTemporale implements CriterioCompletamento{
        
    private long tempoInizio;
    private long durataRichiesta; // in millisecondi

    public CriterioTemporale(long durataRichiesta) {
        this.tempoInizio = System.currentTimeMillis();
        this.durataRichiesta = durataRichiesta;
    }

    @Override
    public boolean verificaCompletamento() {
        return (System.currentTimeMillis() - tempoInizio) >= durataRichiesta;
    }
}

