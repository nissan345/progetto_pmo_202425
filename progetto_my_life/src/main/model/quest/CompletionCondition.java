package main.model.quest;

import main.model.character.MainCharacter;
<<<<<<< HEAD
import main.model.world.gameItem.GameItem;

public class CompletionCondition {
	
	private GameItem item;
	private Quest quest;
	    
	public CompletionCondition(GameItem item, Quest quest) {
		this.item = item;
		this.quest = quest;
	}
	
	public boolean checkCompletion(MainCharacter character) {
		return character.hasUsedItemForQuest(item,quest);
	}
=======

public class CompletionCondition {
	
	private String nameItem;
	    
	public CompletionCondition(String nameItem) {
		this.nameItem = nameItem;
	}
	public boolean checkCompletion(MainCharacter character) {
		return character.hasUsedItem(nameItem);
	} 
>>>>>>> main
}