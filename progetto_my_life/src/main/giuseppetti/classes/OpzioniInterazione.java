package main.giuseppetti.classes;

public enum OpzioniInterazione{

    CHIEDI_MISSIONE("Chiedi missione"),
    CONSEGNA_MISSIONE("Consegna missione"),
    ESCI("Esci");

    private final String messaggio;

    OpzioniInterazione(String messaggio){
        this.messaggio = messaggio;
    }
}