package model;

import model.character.MainCharacter;
import model.quest.CompletionCondition;

public class ObjectActionCondition implements CompletionCondition {
	
	private String objectName;
	    
	public ObjectActionCondition(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public boolean checkCompletion(MainCharacter character) {
		return character.hasUsedOggetto(objectName);
	} 
	   
}
