package main.neri.classes;

import java.util.*;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.Stanza;
     

public class FabbricaOggetti { 
    
   
	 // Camera da Letto
        public static Stanza creaCameraDaLetto() {
        	List<OggettoGioco> oggetti = new ArrayList<>(List.of(
        	        new OggettoGioco.Builder("Letto", "Camera da Letto", 80)
        	            .messaggio("Ti sdrai sul letto e riposi")
        	            .energia(40)
        	            .igiene(-10)
        	            .build(), 
        	            
        	        new OggettoGioco.Builder("Computer", "Camera da Letto", 20)
        	            .messaggio("Giochi al computer.")
        	            .fame(-10)
        	            .sete(-5)
        	            .energia(-20)
        	            .igiene(-5)
        	            .build(),
        	            
        	        new OggettoGioco.Builder("Armadio", "Camera da Letto",90)
        	            .messaggio("Provi dei nuovi outfit!")
        	            .energia(-10)
        	            .igiene(20)
        	 //         .isInterazioneSpeciale(true)
        	            .build()
        	    ));
            return new StanzaImpl("Camera da Letto" , oggetti);
        }
        
     // Cucina
        public static Stanza creaCucina() {
        	List<OggettoGioco> oggetti = new ArrayList<>(List.of(
        	        new OggettoGioco.Builder("Fornelli", "Cucina", 80)
        	        		.messaggio("Cucini un pasto caldo")
        	        		.fame(20)
        	        		.sete(0)
        	        		.igiene(-5)
        	        		.energia(-10)
        	     //   		.isInterazioneSpeciale(true)
        	        		.build(),
        	        		
        	        new Frigorifero(),
        	        
        	        new OggettoGioco.Builder("Lavandino", "Cucina", 80)
        	        		.messaggio("Lava i piatti")
        	        		.fame(0)
        	        		.sete(0)
        	        		.igiene(0)
        	        		.energia(-10)
        	        		.build()
        			));	
        	return new StanzaImpl("Cucina", oggetti);
        }
       
     // Bagno
        public static Stanza creaBagno() {
        	List<OggettoGioco> oggetti = new ArrayList<>(List.of(
        			 new OggettoGioco.Builder("Doccia","Bagno", 80)
        		        		.messaggio("Fai una doccia rigenerante.")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(40)
        		        		.energia(10)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("WC","Bagno", 60)
        		        		.messaggio("Ti senti sollevata dopo essere andata al bagno.")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(5)
        		        		.energia(5)
        		        		.build(),
        		        
        		        new OggettoGioco.Builder("Lavatrice","Bagno", 80)
        		        		.messaggio("Metti i vestiti in lavatrice. La casa è più ordinata!")
        		        		.fame(0)
        		        		.sete(0)
        		        		.igiene(0)
        		        		.energia(-10)
        		        		.build()
        			));
        			return new StanzaImpl("Bagno", oggetti);
        }
        
     // Salotto
        public static Stanza creaSalotto() {
        	List<OggettoGioco> oggetti = new ArrayList<>(List.of(
        			new OggettoGioco.Builder("Televisione","Salotto", 40)
	            		.messaggio("Guardi la TV e ti rilassi.")
	            		.fame(-5)
	            		.sete(-5)
	            		.igiene(-5)
	            		.energia(10)
	            		.build(),
            
            new OggettoGioco.Builder("Stereo","Salotto", 30)
	            		.messaggio("Ascolti Billie Eilish.")
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(10)
	            		.build(),
            
            new OggettoGioco.Builder("Divano","Salotto", 80)
	            		.messaggio("Ti siedi sul divano e ti riposi un po'.")
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-15)
	            		.build(),
            
           new OggettoGioco.Builder("Libreria","Salotto", 80)
	            		.messaggio("Hai appena letto Harry Potter e la pietra filosofale!")
	            		.fame(-5)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-10)
	            		.build(),
            
            new OggettoGioco.Builder("Album","Salotto", 20)
	            		.messaggio("Hai preso il vecchio album di fotografie")
	            		.fame(0)
	            		.sete(0)
	            		.igiene(0)
	            		.energia(-5)
	            		.build()
	        			));
        	return new StanzaImpl("Salotto", oggetti);
        			
        }
        
        
     // Sgabuzzino
       public static Stanza creaSgabuzzino() {
    	   List<OggettoGioco> oggetti = new ArrayList<>(List.of(
    			 new OggettoGioco.Builder("Aspirapolvere","Sgabuzzino", 30)
	           		.messaggio("Usi l'aspirapolvere e pulisci la stanza.")
	           		.fame(0)
	           		.sete(0)
	           		.igiene(0)
	           		.energia(-20)
	           		.build()
					));
    	   return new StanzaImpl("Sgabuzzino", oggetti);
       }
        
                
       // Giardino
       public static Stanza creaGiardino() {
    	   List<OggettoGioco> oggetti = new ArrayList<>(List.of(
    			   new OggettoGioco.Builder("Innaffiatoio","Giardino", 10)
		           		.messaggio("Innaffi le piante: ora sono più verdi")
		           		.fame(0)
		           		.sete(0)
		           		.igiene(0)
		           		.energia(-10)
		           		.build(),
           
	           		new OggettoGioco.Builder("Palla","Giardino",10)
		           		.messaggio("Giochi con la palla e fai un po' di esercizio.")
		           		.fame(-10)
		           		.sete(-5)
		           		.igiene(-15)
		           		.energia(-5)
		           		.build(),
           
	           		new OggettoGioco.Builder("Altalena","Giardino", 80)
		           		.messaggio("Ti dondoli sull'altalena, ti diverti e ti rilassi.")
		           		.fame(0)
		           		.sete(0)
		           		.igiene(-15)
		           		.energia(0)
		           		.build(),
           
	           		new OggettoGioco.Builder("Macchina","Giardino",90)
		           		.messaggio("Prendi la macchina e fai un giro.")
		           		.fame(-15)
		           		.sete(-15)
		           		.igiene(-20)
		           		.energia(-5)
		           		.build()
		   ));
          return new StanzaImpl("Giardino", oggetti);
       }
}