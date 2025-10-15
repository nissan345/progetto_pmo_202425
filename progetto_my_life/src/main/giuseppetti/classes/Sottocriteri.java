package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

import main.giuseppetti.interfaces.CriterioCompletamento;

// più criteri che devono essere veri per il completamento di un'azione
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
