package main.neri.classes;

import java.util.*;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.Room;
     

public class FabbricaOggetti { 
    
   
	 // Camera da Letto
        public static Room creaCameraDaLetto() {
        	List<OggettoGioco> oggetti = List.of(
        	        new OggettoGioco.Builder("Letto", "Camera da Letto")
        	            .messaggio("Ti sdrai sul letto e riposi")
        	            .energy(40)
        	            .hygiene(-10)
        	            .build(), 
        	            
        	        new OggettoGioco.Builder("Computer", "Camera da Letto")
        	            .messaggio("Giochi al computer.")
        	            .hunger(-10)
        	            .thirst(-5)
        	            .energy(-20)
        	            .hygiene(-5)
        	            .build(),
        	            
        	        new OggettoGioco.Builder("Armadio", "Camera da Letto")
        	            .messaggio("Provi dei nuovi outfit!")
        	            .energy(-10)
        	            .hygiene(20)
        	 //         .isInterazioneSpeciale(true)
        	            .build()
        	    );
            return new RoomImpl("Camera da Letto" , oggetti);
        }
        
     // Cucina
        public static Room creaCucina() {
        	List<OggettoGioco> oggetti = List.of(
        	        new OggettoGioco.Builder("Fornelli", "Cucina")
        	        		.messaggio("Cucini un pasto caldo")
        	        		.hunger(20)
        	        		.thirst(0)
        	        		.hygiene(-5)
        	        		.energy(-10)
        	     //   		.isInterazioneSpeciale(true)
        	        		.build(),
        	        		
        	        new Frigorifero(),
        	        
        	        new OggettoGioco.Builder("Lavandino", "Cucina")
        	        		.messaggio("Lava i piatti")
        	        		.hunger(0)
        	        		.thirst(0)
        	        		.hygiene(0)
        	        		.energy(-10)
        	        		.build()
        			);	
        	return new RoomImpl("Cucina", oggetti);
        }
       
     // Bagno
        public static Room creaBagno() {
        	List<OggettoGioco> oggetti = List.of(
        			 new OggettoGioco.Builder("Doccia","Bagno")
        		        		.messaggio("Fai una doccia rigenerante.")
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(40)
        		        		.energy(10)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("WC","Bagno")
        		        		.messaggio("Ti senti sollevata dopo essere andata al bagno.")
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(5)
        		        		.energy(5)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("Lavatrice","Bagno")
        		        		.messaggio("Metti i outfit in lavatrice. La casa è più ordinata!")
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(0)
        		        		.energy(-10)
        		        		.build()
        			);
        			return new RoomImpl("Bagno", oggetti);
        }
        
     // Salotto
        public static Room creaSalotto() {
        	List<OggettoGioco> oggetti = List.of(
        			new OggettoGioco.Builder("Televisione","Salotto")
	            		.messaggio("Guardi la TV e ti rilassi.")
	            		.hunger(-5)
	            		.thirst(-5)
	            		.hygiene(-5)
	            		.energy(10)
	            		.build(),
            
            new OggettoGioco.Builder("Stereo","Salotto")
	            		.messaggio("Ascolti Billie Eilish.")
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(10)
	            		.build(),
            
            new OggettoGioco.Builder("Divano","Salotto")
	            		.messaggio("Ti siedi sul divano e ti riposi un po'.")
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-15)
	            		.build(),
            
           new OggettoGioco.Builder("Libreria","Salotto")
	            		.messaggio("Hai appena letto Harry Potter e la pietra filosofale!")
	            		.hunger(-5)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-10)
	            		.build(),
            
            new OggettoGioco.Builder("Album","Salotto")
	            		.messaggio("Hai preso il vecchio album di fotografie")
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-5)
	            		.build()
	        			);
        	return new RoomImpl("Salotto", oggetti);
        			
        }
        
        
     // Sgabuzzino
       public static Room creaSgabuzzino() {
    	   List<OggettoGioco> oggetti = List.of(
    			 new OggettoGioco.Builder("Aspirapolvere","Sgabuzzino")
	           		.messaggio("Usi l'aspirapolvere e pulisci la stanza.")
	           		.hunger(0)
	           		.thirst(0)
	           		.hygiene(0)
	           		.energy(-20)
	           		.build());
    	   return new RoomImpl("Sgabuzzino", oggetti);
       }
        
                
       // Giardino
       public static Room creaGiardino() {
    	   List<OggettoGioco> oggetti = List.of(
    			   new OggettoGioco.Builder("Innaffiatoio","Giardino")
		           		.messaggio("Innaffi le piante: ora sono più verdi")
		           		.hunger(0)
		           		.thirst(0)
		           		.hygiene(0)
		           		.energy(-10)
		           		.build(),
           
	           		new OggettoGioco.Builder("Palla","Giardino")
		           		.messaggio("Giochi con la palla e fai un po' di esercizio.")
		           		.hunger(-10)
		           		.thirst(-5)
		           		.hygiene(-15)
		           		.energy(-5)
		           		.build(),
           
	           		new OggettoGioco.Builder("Altalena","Giardino")
		           		.messaggio("Ti dondoli sull'altalena, ti diverti e ti rilassi.")
		           		.hunger(0)
		           		.thirst(0)
		           		.hygiene(-15)
		           		.energy(0)
		           		.build(),
           
	           		new OggettoGioco.Builder("Macchina","Giardino")
		           		.messaggio("Prendi la macchina e fai un giro.")
		           		.hunger(-15)
		           		.thirst(-15)
		           		.hygiene(-20)
		           		.energy(-5)
		           		.build());
          return new RoomImpl("Giardino", oggetti);
       }
}