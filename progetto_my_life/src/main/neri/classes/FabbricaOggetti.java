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
<<<<<<< HEAD
        	            .energia(40)
        	            .igiene(-10)
=======
        	            .energy(40)
        	            .hygiene(-10)
>>>>>>> nicxole
        	            .build(), 
        	            
        	        new OggettoGioco.Builder("Computer", "Camera da Letto")
        	            .messaggio("Giochi al computer.")
<<<<<<< HEAD
        	            .fame(-10)
        	            .sete(-5)
        	            .energia(-20)
        	            .igiene(-5)
=======
        	            .hunger(-10)
        	            .thirst(-5)
        	            .energy(-20)
        	            .hygiene(-5)
>>>>>>> nicxole
        	            .build(),
        	            
        	        new OggettoGioco.Builder("Armadio", "Camera da Letto")
        	            .messaggio("Provi dei nuovi outfit!")
<<<<<<< HEAD
        	            .energia(-10)
        	            .igiene(20)
        	 //         .isInterazioneSpeciale(true)
        	            .build()
        	    );
            return new IRoom("Camera da Letto" , oggetti);
=======
        	            .energy(-10)
        	            .hygiene(20)
        	 //         .isInterazioneSpeciale(true)
        	            .build()
        	    );
            return new RoomImpl("Camera da Letto" , oggetti);
>>>>>>> nicxole
        }
        
     // Cucina
        public static Room creaCucina() {
        	List<OggettoGioco> oggetti = List.of(
        	        new OggettoGioco.Builder("Fornelli", "Cucina")
        	        		.messaggio("Cucini un pasto caldo")
<<<<<<< HEAD
        	        		.fame(20)
        	        		.sete(0)
        	        		.igiene(-5)
        	        		.energia(-10)
=======
        	        		.hunger(20)
        	        		.thirst(0)
        	        		.hygiene(-5)
        	        		.energy(-10)
>>>>>>> nicxole
        	     //   		.isInterazioneSpeciale(true)
        	        		.build(),
        	        		
        	        new Frigorifero(),
        	        
        	        new OggettoGioco.Builder("Lavandino", "Cucina")
        	        		.messaggio("Lava i piatti")
<<<<<<< HEAD
        	        		.fame(0)
        	        		.sete(0)
        	        		.igiene(0)
        	        		.energia(-10)
        	        		.build()
        			);	
        	return new IRoom("Cucina", oggetti);
=======
        	        		.hunger(0)
        	        		.thirst(0)
        	        		.hygiene(0)
        	        		.energy(-10)
        	        		.build()
        			);	
        	return new RoomImpl("Cucina", oggetti);
>>>>>>> nicxole
        }
       
     // Bagno
        public static Room creaBagno() {
        	List<OggettoGioco> oggetti = List.of(
        			 new OggettoGioco.Builder("Doccia","Bagno")
        		        		.messaggio("Fai una doccia rigenerante.")
<<<<<<< HEAD
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(40)
        		        		.energia(10)
=======
        		        		.hunger(0)
        		        		.thirst(0)
        		        		.hygiene(40)
        		        		.energy(10)
>>>>>>> nicxole
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("WC","Bagno")
        		        		.messaggio("Ti senti sollevata dopo essere andata al bagno.")
<<<<<<< HEAD
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(5)
        		        		.energia(5)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("Lavatrice","Bagno")
        		        		.messaggio("Metti i vestiti in lavatrice. La casa è più ordinata!")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(0)
        		        		.energia(-10)
        		        		.build()
        			);
        			return new IRoom("Bagno", oggetti);
=======
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
>>>>>>> nicxole
        }
        
     // Salotto
        public static Room creaSalotto() {
        	List<OggettoGioco> oggetti = List.of(
        			new OggettoGioco.Builder("Televisione","Salotto")
	            		.messaggio("Guardi la TV e ti rilassi.")
<<<<<<< HEAD
	            		.fame(-5)
	            		.sete(-5)
	            		.igiene(-5)
	            		.energia(10)
=======
	            		.hunger(-5)
	            		.thirst(-5)
	            		.hygiene(-5)
	            		.energy(10)
>>>>>>> nicxole
	            		.build(),
            
            new OggettoGioco.Builder("Stereo","Salotto")
	            		.messaggio("Ascolti Billie Eilish.")
<<<<<<< HEAD
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(10)
=======
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(10)
>>>>>>> nicxole
	            		.build(),
            
            new OggettoGioco.Builder("Divano","Salotto")
	            		.messaggio("Ti siedi sul divano e ti riposi un po'.")
<<<<<<< HEAD
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-15)
=======
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-15)
>>>>>>> nicxole
	            		.build(),
            
           new OggettoGioco.Builder("Libreria","Salotto")
	            		.messaggio("Hai appena letto Harry Potter e la pietra filosofale!")
<<<<<<< HEAD
	            		.fame(-5)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-10)
=======
	            		.hunger(-5)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-10)
>>>>>>> nicxole
	            		.build(),
            
            new OggettoGioco.Builder("Album","Salotto")
	            		.messaggio("Hai preso il vecchio album di fotografie")
<<<<<<< HEAD
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-5)
	            		.build()
	        			);
        	return new IRoom("Salotto", oggetti);
=======
	            		.hunger(0)
	            		.thirst(0)
	            		.hygiene(0)
	            		.energy(-5)
	            		.build()
	        			);
        	return new RoomImpl("Salotto", oggetti);
>>>>>>> nicxole
        			
        }
        
        
     // Sgabuzzino
       public static Room creaSgabuzzino() {
    	   List<OggettoGioco> oggetti = List.of(
    			 new OggettoGioco.Builder("Aspirapolvere","Sgabuzzino")
<<<<<<< HEAD
	           		.messaggio("Usi l'aspirapolvere e pulisci la stanza.")
	           		.fame(0)
	           		.sete(0)
	           		.igiene(0)
	           		.energia(-20)
	           		.build());
    	   return new IRoom("Sgabuzzino", oggetti);
=======
	           		.messaggio("Usi l'aspirapolvere e pulisci la room.")
	           		.hunger(0)
	           		.thirst(0)
	           		.hygiene(0)
	           		.energy(-20)
	           		.build());
    	   return new RoomImpl("Sgabuzzino", oggetti);
>>>>>>> nicxole
       }
        
                
       // Giardino
       public static Room creaGiardino() {
    	   List<OggettoGioco> oggetti = List.of(
    			   new OggettoGioco.Builder("Innaffiatoio","Giardino")
		           		.messaggio("Innaffi le piante: ora sono più verdi")
<<<<<<< HEAD
		           		.fame(0)
		           		.sete(0)
		           		.igiene(0)
		           		.energia(-10)
=======
		           		.hunger(0)
		           		.thirst(0)
		           		.hygiene(0)
		           		.energy(-10)
>>>>>>> nicxole
		           		.build(),
           
	           		new OggettoGioco.Builder("Palla","Giardino")
		           		.messaggio("Giochi con la palla e fai un po' di esercizio.")
<<<<<<< HEAD
		           		.fame(-10)
		           		.sete(-5)
		           		.igiene(-15)
		           		.energia(-5)
=======
		           		.hunger(-10)
		           		.thirst(-5)
		           		.hygiene(-15)
		           		.energy(-5)
>>>>>>> nicxole
		           		.build(),
           
	           		new OggettoGioco.Builder("Altalena","Giardino")
		           		.messaggio("Ti dondoli sull'altalena, ti diverti e ti rilassi.")
<<<<<<< HEAD
		           		.fame(0)
		           		.sete(0)
		           		.igiene(-15)
		           		.energia(0)
=======
		           		.hunger(0)
		           		.thirst(0)
		           		.hygiene(-15)
		           		.energy(0)
>>>>>>> nicxole
		           		.build(),
           
	           		new OggettoGioco.Builder("Macchina","Giardino")
		           		.messaggio("Prendi la macchina e fai un giro.")
<<<<<<< HEAD
		           		.fame(-15)
		           		.sete(-15)
		           		.igiene(-20)
		           		.energia(-5)
		           		.build());
          return new IRoom("Giardino", oggetti);
=======
		           		.hunger(-15)
		           		.thirst(-15)
		           		.hygiene(-20)
		           		.energy(-5)
		           		.build());
          return new RoomImpl("Giardino", oggetti);
>>>>>>> nicxole
       }
}