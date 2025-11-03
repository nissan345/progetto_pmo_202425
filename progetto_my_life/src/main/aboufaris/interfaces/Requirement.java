package main.aboufaris.interfaces;

import main.fabbri.classes.MainCharacter;

/**
 * The following interface represents the requirements to make an action
 */
public interface Requirement {

    public boolean isSatisfiedBy(MainCharacter c);
    public String failureReason();
    
}
