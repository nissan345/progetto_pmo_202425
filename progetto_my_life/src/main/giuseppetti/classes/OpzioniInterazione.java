package main.giuseppetti.classes;

public enum OpzioniInterazione{

    CHIEDI_MISSIONE("Chiedi mission"),
    CONSEGNA_MISSIONE("Consegna mission"),
    MISSIONE_IN_CORSO("Mission in corso"),
    ESCI("Esci");

    private final String messaggio;

    OpzioniInterazione(String messaggio){
        this.messaggio = messaggio;
    }
}