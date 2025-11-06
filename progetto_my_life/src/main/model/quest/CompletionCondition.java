package main.model.quest;

import main.model.character.MainCharacter;

public interface CompletionCondition {
	
	public boolean checkCompletion(MainCharacter character);
}