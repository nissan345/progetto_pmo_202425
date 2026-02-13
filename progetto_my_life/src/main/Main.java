package main;

import main.view.View;
import main.controller.Controller;

public class Main {
	
	public static void main(String[] args) {

            // View creation
            View view = new View();

            // Controller creation
            Controller controller = new Controller(view);
            
            // Starting game session
            controller.StartMenu();
	}

}