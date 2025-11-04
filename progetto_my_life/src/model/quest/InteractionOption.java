package model.quest;

public enum InteractionOption {

    REQUEST_QUEST("Chiedi missione"),
    TURN_IN_QUEST("Consegna missione"),
    QUEST_IN_PROGRESS("Missione in corso"),
    EXIT("Esci");

    private final String message;

    InteractionOption(String message){
        this.message = message;
    }
}