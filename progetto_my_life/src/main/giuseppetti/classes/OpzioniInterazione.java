package main.giuseppetti.classes;

public enum OpzioniInterazione{


    CHIEDI_MISSIONE("Chiedi quest"),
    CONSEGNA_MISSIONE("Consegna quest"),
    MISSIONE_IN_CORSO("Quest in corso"),
    ESCI("Esci");

    private final String messaggio;

    OpzioniInterazione(String messaggio){
        this.messaggio = messaggio;
    }
}