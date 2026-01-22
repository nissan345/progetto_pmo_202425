package main.model.quest;

import main.model.character.MainCharacter;

public interface CompletionCondition {
    boolean checkCompletion(MainCharacter character);
    
    void onQuestCompleted(MainCharacter character);
}