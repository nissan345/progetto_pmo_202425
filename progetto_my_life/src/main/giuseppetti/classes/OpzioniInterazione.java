package main.giuseppetti.classes;

public enum OpzioniInterazione{

<<<<<<< HEAD
    CHIEDI_MISSIONE("Chiedi missione"),
    CONSEGNA_MISSIONE("Consegna missione"),
    MISSIONE_IN_CORSO("Missione in corso"),
=======
    CHIEDI_MISSIONE("Chiedi quest"),
    CONSEGNA_MISSIONE("Consegna quest"),
    MISSIONE_IN_CORSO("Quest in corso"),
>>>>>>> nicxole
    ESCI("Esci");

    private final String messaggio;

    OpzioniInterazione(String messaggio){
        this.messaggio = messaggio;
    }
}