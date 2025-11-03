package main.neri.classes;

import java.util.*;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.Room;
     

public class FabbricaOggetti { 
    
   
	 // Camera da Letto
        public static Room creaCameraDaLetto() {
        	List<OggettoGioco> oggetti = List.of(
        	        new OggettoGioco.Builder("Letto", "Camera da Letto")
        	            .message("Ti sdrai sul letto e riposi")
        	            .energy(40)
        	            .hygiene(-10)
        	            .build(), 
        	            
        	        new OggettoGioco.Builder("Computer", "Camera da Letto")
        	            .message("Giochi al computer.")
        	            .hunger(-10)
        	            .thirst(-5)
        	            .energy(-20)
        	            .hygiene(-5)
        	            .build(),
        	            
        	        new OggettoGioco.Builder("Armadio", "Camera da Letto")
        	            .message("Provi dei nuovi outfit!")
        	            .energy(-10)
        	            .hygiene(20)
        	 //         .isInterazioneSpeciale(true)
        	            .build()
        	    );
            return new IRoom("Camera da Letto" , oggetti);

        }
        
     // Cucina
        public static Room creaCucina() {
        	List<OggettoGioco> oggetti = List.of(
        	        new OggettoGioco.Builder("Fornelli", "Cucina")
        	        		.message("Cucini un pasto caldo")
        	        		.hunger(20)
        	        		.thirst(0)
        	        		.hygiene(-5)
        	        		.energy(-10)
        	     //   		.isInterazioneSpeciale(true)
        	        		.build(),
        	        		
        	        new Frigorifero(),
        	        
        	        new OggettoGioco.Builder("Lavandino", "Cucina")
        	        		.message("Lava i piatti")
        	        		.hunger(0)
        	        		.thirst(0)
        	        		.hygiene(0)
        	        		.energy(-10)
        	        		.build()
        			);	
        	return new IRoom("Cucina", oggetti);
        }
       
     // Bagno
        public static Room creaBagno() {
        	List<OggettoGioco> oggetti = List.of(
        			 new OggettoGioco.Builder("Doccia","Bagno")
        		        		.message("Fai una doccia rigenerante.")
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(40)
        		        		.energy(10)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("WC","Bagno")
        		        		.message("Ti senti sollevata dopo essere andata al bagno.")
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(5)
        		        		.energy(5)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("Lavatrice","Bagno")
        		        		.message("Metti i outfit in lavatrice. La casa è più ordinata!")
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(0)
        		        		.energy(-10)
        		        		.build()
        			);
        			return new IRoom("Bagno", oggetti);

        }
        
     // Salotto
        public static Room creaSalotto() {
        	List<OggettoGioco> oggetti = List.of(
        			new OggettoGioco.Builder("Televisione","Salotto")
	            		.message("Guardi la TV e ti rilassi.")
	            		.hunger(-5)
	            		.thirst(-5)
	            		.hygiene(-5)
	            		.energy(10)
	            		.build(),
            
            new OggettoGioco.Builder("Stereo","Salotto")
	            		.message("Ascolti Billie Eilish.")
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(10)
	            		.build(),
            
            new OggettoGioco.Builder("Divano","Salotto")
	            		.message("Ti siedi sul divano e ti riposi un po'.")
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-15)
	            		.build(),
            
           new OggettoGioco.Builder("Libreria","Salotto")
	            		.message("Hai appena letto Harry Potter e la pietra filosofale!")
	            		.hunger(-5)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-10)
	            		.build(),
            
            new OggettoGioco.Builder("Album","Salotto")
	            		.message("Hai preso il vecchio album di fotografie")
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-5)
	            		.build()
	        			);
        	return new IRoom("Salotto", oggetti);
        			
        }
        
        
     // Sgabuzzino
       public static Room creaSgabuzzino() {
    	   List<OggettoGioco> oggetti = List.of(
    			 new OggettoGioco.Builder("Aspirapolvere","Sgabuzzino")
	           		.message("Usi l'aspirapolvere e pulisci la room.")
	           		.hunger(0)
	           		.thirst(0)
	           		.hygiene(0)
	           		.energy(-20)
	           		.build());
    	   return new IRoom("Sgabuzzino", oggetti);

    	   }
        
                
       // Giardino
       public static Room creaGiardino() {
    	   List<OggettoGioco> oggetti = List.of(
    			   new OggettoGioco.Builder("Innaffiatoio","Giardino")
		           		.message("Innaffi le piante: ora sono più verdi")
		           		.hunger(0)
		           		.thirst(0)
		           		.hygiene(0)
		           		.energy(-10)
		           		.build(),
           
	           		new OggettoGioco.Builder("Palla","Giardino")
		           		.message("Giochi con la palla e fai un po' di esercizio.")
		           		.hunger(-10)
		           		.thirst(-5)
		           		.hygiene(-15)
		           		.energy(-5)
		           		.build(),
           
	           		new OggettoGioco.Builder("Altalena","Giardino")
		           		.message("Ti dondoli sull'altalena, ti diverti e ti rilassi.")
		           		.hunger(0)
		           		.thirst(0)
		           		.hygiene(-15)
		           		.energy(0)
		           		.build(),
           
	           		new OggettoGioco.Builder("Macchina","Giardino")
		           		.message("Prendi la macchina e fai un giro.")
		           		.hunger(-15)
		           		.thirst(-15)
		           		.hygiene(-20)
		           		.energy(-5)
		           		.build());
          return new IRoom("Giardino", oggetti);

       }
}