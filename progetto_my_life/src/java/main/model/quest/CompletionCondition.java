package main.model.quest;

import main.model.character.MainCharacter;

public class CompletionCondition {
	
	private String nameItem;
	    
	public CompletionCondition(String nameItem) {
		this.nameItem = nameItem;
	}
	public boolean checkCompletion(MainCharacter character) {
		return character.hasUsedItem(nameItem);
	} 
}