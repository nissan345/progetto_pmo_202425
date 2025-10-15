package main.giuseppetti.classes;

import java.util.ArrayList;
import java.util.List;

import main.giuseppetti.interfaces.CriterioCompletamento;

public class CriterioUsoOggetti implements CriterioCompletamento{

    private List<String> oggettiRichiesti;
    private List<String> oggettiUsati;

    public CriterioUsoOggetti(List<String> oggettiRichiesti) {
        this.oggettiRichiesti = new ArrayList<>(oggettiRichiesti);
        this.oggettiUsati = new ArrayList<>();
    }

    // Metodo chiamato quando il giocatore usa un oggetto
    public void oggettoUsato(String oggetto) {
        if (oggettiRichiesti.contains(oggetto) && !oggettiUsati.contains(oggetto)) {
            oggettiUsati.add(oggetto);
        }
    }

    @Override
    public boolean verificaCompletamento() {
        return oggettiUsati.containsAll(oggettiRichiesti);
    }
}
