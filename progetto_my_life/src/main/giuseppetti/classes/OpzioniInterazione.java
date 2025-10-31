package main.giuseppetti.classes;

public enum OpzioniInterazione{

    CHIEDI_MISSIONE("Chiedi missione"),
    CONSEGNA_MISSIONE("Consegna missione"),
    MISSIONE_IN_CORSO("Missione in corso"),
    ESCI("Esci");

    private final String messaggio;

    OpzioniInterazione(String messaggio){
        this.messaggio = messaggio;
    }
}