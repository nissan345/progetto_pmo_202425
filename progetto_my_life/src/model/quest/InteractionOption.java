package model.quest;

public enum InteractionOption {

    REQUEST_QUEST("Chiedi quest"),
    TURN_IN_QUEST("Consegna quest"),
    QUEST_IN_PROGRESS("Quest in corso"),
    EXIT("Esci");

    private final String message;

    InteractionOption(String message){
        this.message = message;
    }
}