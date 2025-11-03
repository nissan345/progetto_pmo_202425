package model.requirement;

import java.util.List;

import model.character.MainCharacter;

/**
 * The following interface represents the requirements to make an action
 */
public interface Requirement {

    public boolean isSatisfiedBy(MainCharacter c);
    public List<String> getFailureReasons(MainCharacter c);
    
}
