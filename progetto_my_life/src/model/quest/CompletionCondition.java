package model.quest;

import model.character.MainCharacter;

public interface CompletionCondition {
	
	public boolean checkCompletion(MainCharacter character);
}