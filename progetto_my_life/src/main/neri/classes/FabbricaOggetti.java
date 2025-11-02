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
        	            .energia(40)
        	            .igiene(-10)
        	            .build(), 
        	            
        	        new OggettoGioco.Builder("Computer", "Camera da Letto")
        	            .message("Giochi al computer.")
        	            .fame(-10)
        	            .sete(-5)
        	            .energia(-20)
        	            .igiene(-5)
        	            .build(),
        	            
        	        new OggettoGioco.Builder("Armadio", "Camera da Letto")
        	            .message("Provi dei nuovi outfit!")
        	            .energia(-10)
        	            .igiene(20)
        	 //         .isInterazioneSpeciale(true)
        	            .build()
        	    );
            return new StanzaImpl("Camera da Letto" , oggetti);
        }
        
     // Cucina
        public static Room creaCucina() {
        	List<OggettoGioco> oggetti = List.of(
        	        new OggettoGioco.Builder("Fornelli", "Cucina")
        	        		.message("Cucini un pasto caldo")
        	        		.fame(20)
        	        		.sete(0)
        	        		.igiene(-5)
        	        		.energia(-10)
        	     //   		.isInterazioneSpeciale(true)
        	        		.build(),
        	        		
        	        new Frigorifero(),
        	        
        	        new OggettoGioco.Builder("Lavandino", "Cucina")
        	        		.message("Lava i piatti")
        	        		.fame(0)
        	        		.sete(0)
        	        		.igiene(0)
        	        		.energia(-10)
        	        		.build()
        			);	
        	return new StanzaImpl("Cucina", oggetti);
        }
       
     // Bagno
        public static Room creaBagno() {
        	List<OggettoGioco> oggetti = List.of(
        			 new OggettoGioco.Builder("Doccia","Bagno")
        		        		.message("Fai una doccia rigenerante.")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(40)
        		        		.energia(10)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("WC","Bagno")
        		        		.message("Ti senti sollevata dopo essere andata al bagno.")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(5)
        		        		.energia(5)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("Lavatrice","Bagno")
        		        		.message("Metti i vestiti in lavatrice. La casa è più ordinata!")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(0)
        		        		.energia(-10)
        		        		.build()
        			);
        			return new StanzaImpl("Bagno", oggetti);
        }
        
     // Salotto
        public static Room creaSalotto() {
        	List<OggettoGioco> oggetti = List.of(
        			new OggettoGioco.Builder("Televisione","Salotto")
	            		.message("Guardi la TV e ti rilassi.")
	            		.fame(-5)
	            		.sete(-5)
	            		.igiene(-5)
	            		.energia(10)
	            		.build(),
            
            new OggettoGioco.Builder("Stereo","Salotto")
	            		.message("Ascolti Billie Eilish.")
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(10)
	            		.build(),
            
            new OggettoGioco.Builder("Divano","Salotto")
	            		.message("Ti siedi sul divano e ti riposi un po'.")
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-15)
	            		.build(),
            
           new OggettoGioco.Builder("Libreria","Salotto")
	            		.message("Hai appena letto Harry Potter e la pietra filosofale!")
	            		.fame(-5)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-10)
	            		.build(),
            
            new OggettoGioco.Builder("Album","Salotto")
	            		.message("Hai preso il vecchio album di fotografie")
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-5)
	            		.build()
	        			);
        	return new StanzaImpl("Salotto", oggetti);
        			
        }
        
        
     // Sgabuzzino
       public static Room creaSgabuzzino() {
    	   List<OggettoGioco> oggetti = List.of(
    			 new OggettoGioco.Builder("Aspirapolvere","Sgabuzzino")
	           		.message("Usi l'aspirapolvere e pulisci la room.")
	           		.fame(0)
	           		.sete(0)
	           		.igiene(0)
	           		.energia(-20)
	           		.build());
    	   return new StanzaImpl("Sgabuzzino", oggetti);
       }
        
                
       // Giardino
       public static Room creaGiardino() {
    	   List<OggettoGioco> oggetti = List.of(
    			   new OggettoGioco.Builder("Innaffiatoio","Giardino")
		           		.message("Innaffi le piante: ora sono più verdi")
		           		.fame(0)
		           		.sete(0)
		           		.igiene(0)
		           		.energia(-10)
		           		.build(),
           
	           		new OggettoGioco.Builder("Palla","Giardino")
		           		.message("Giochi con la palla e fai un po' di esercizio.")
		           		.fame(-10)
		           		.sete(-5)
		           		.igiene(-15)
		           		.energia(-5)
		           		.build(),
           
	           		new OggettoGioco.Builder("Altalena","Giardino")
		           		.message("Ti dondoli sull'altalena, ti diverti e ti rilassi.")
		           		.fame(0)
		           		.sete(0)
		           		.igiene(-15)
		           		.energia(0)
		           		.build(),
           
	           		new OggettoGioco.Builder("Macchina","Giardino")
		           		.message("Prendi la macchina e fai un giro.")
		           		.fame(-15)
		           		.sete(-15)
		           		.igiene(-20)
		           		.energia(-5)
		           		.build());
          return new StanzaImpl("Giardino", oggetti);
       }
}