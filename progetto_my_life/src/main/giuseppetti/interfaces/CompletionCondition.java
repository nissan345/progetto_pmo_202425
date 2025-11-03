package main.giuseppetti.interfaces;

import main.fabbri.classes.Character;
import main.giuseppetti.classes.Quest; 

public interface CompletionCondition {
	
	public boolean checkCompletion(Character character, Quest quest);
}