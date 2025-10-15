package main.giuseppetti.classes;

import main.giuseppetti.interfaces.CriterioCompletamento;

public class CriterioAzioneCompletata implements CriterioCompletamento {
    private boolean azioneCompletata;
    private final String tipoAzione;

    public CriterioAzioneCompletata(String tipoAzione) {
        this.tipoAzione = tipoAzione;
        this.azioneCompletata = false;
    }

    public void segnalaAzioneCompletata(String azioneCompiuta) {
        if (tipoAzione.equals(azioneCompiuta)) {
            this.azioneCompletata = true;
        }
    }

    @Override
    public boolean verificaCompletamento() {
        return azioneCompletata;
    }

    public String getTipoAzione() {
        return tipoAzione;
    }
}
