package main.giuseppetti.classes;

import main.fabbri.classes.Character;
import main.giuseppetti.interfaces.CompletionCondition;

public class ItemActionCondition implements CompletionCondition {
	
	private String itemName;
	    
	public ItemActionCondition(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public boolean checkCompletion(Character character, Quest quest) {
		return character.hasUsedItemForQuest(itemName, quest);
	}  
}
