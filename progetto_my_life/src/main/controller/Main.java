package main.controller;

import main.view.View;

import main.model.character.MainCharacter;

public class Main {
	
	public static void main(String[] args) {
		MainCharacter character = new MainCharacter("ll", null, null); 
		View view = new View(); 
		Controller controller = new Controller(character, view);
		
	}

}
