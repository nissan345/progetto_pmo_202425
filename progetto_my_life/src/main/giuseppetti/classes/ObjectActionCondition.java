package main.giuseppetti.classes;

import main.fabbri.classes.MainCharacter;
import main.giuseppetti.interfaces.CompletionCondition;

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
