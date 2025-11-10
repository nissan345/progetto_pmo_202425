package main.model.requirement;

import java.util.List;

import main.model.character.MainCharacter;

public class AlwaysTrueRequirement implements Requirement{

	@Override
	public boolean isSatisfiedBy(MainCharacter c) {
		return true;
	}

	@Override
	public List<String> getFailureReasons(MainCharacter c) {
		return List.of();
	}

}
