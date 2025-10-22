/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

import main.giuseppetti.interfaces.CriterioCompletamento;

/**
 *
 * @author OS
 */
// più sottocriteri che devono essere veri per il completamento di un'azione
public class Sottocriteri implements CriterioCompletamento {
    private List<CriterioCompletamento> sottocriteri;

    public Sottocriteri(List<CriterioCompletamento> sottocriteri) {
        this.sottocriteri = new ArrayList<>(sottocriteri);
    }

    @Override
    public boolean verificaCompletamento() {
        for (CriterioCompletamento criterio : sottocriteri) {
            if (!criterio.verificaCompletamento()) {
                return false;
            }
        }
        return true;
    }

    public List<CriterioCompletamento> getSottocriteri() {
        return sottocriteri;
    }
}
