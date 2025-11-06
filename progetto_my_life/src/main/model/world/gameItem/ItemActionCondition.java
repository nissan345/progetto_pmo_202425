package main.model.world.gameItem;

import main.model.character.MainCharacter;
import main.model.quest.CompletionCondition;

public class ItemActionCondition implements CompletionCondition {
	
	private String itemName;
	    
	public ItemActionCondition(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public boolean checkCompletion(MainCharacter character) {
		return character.hasUsedItem(itemName);
	} 
	   
}
