package main.giuseppetti.classes;

import main.fabbri.classes.Character;
import main.giuseppetti.interfaces.CompletionCondition;

public class ObjectActionCondition implements CompletionCondition {
	
	private String objectName;
	    
	public ObjectActionCondition(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public boolean checkCompletion(Character character) {
		return character.hasUsedObject(objectName);
	} 
	   
}
