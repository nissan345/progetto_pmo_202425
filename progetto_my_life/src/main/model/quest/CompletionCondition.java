package main.model.quest;

import main.model.character.MainCharacter;
import main.model.world.gameItem.GameItem;

public class CompletionCondition {
	
	private GameItem item;
	    
	public CompletionCondition(GameItem item) {
		this.item = item;
	}
	
	public boolean checkCompletion(MainCharacter character) {
		return character.hasUsedItemForQuest(item);
	}
}