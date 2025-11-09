package main.model.requirement;

import java.util.List;

import main.model.character.MainCharacter;

/**
 * The following interface represents the requirements to perform an action
 */
public interface Requirement {

	/**
	 * Checks if a condition is satisfied by the character
	 * @param c
	 * @return true if the character can perform an action, false otherwise
	 */
    public boolean isSatisfiedBy(MainCharacter c);
    
    /**
     * Shows the user the reasons why performing that action failed
     * @param c
     * @return a list of detailed failure reasons
     */
    public List<String> getFailureReasons(MainCharacter c);
    
}
