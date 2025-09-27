package main.neri.demo;

import java.util.*;
import main.neri.actions.*;
import main.neri.model.*;

public class TestMyPrincessLife {
    public static void main(String[] args) {
        System.out.println("=== MY PRINCESS LIFE - SISTEMA AZIONI E OGGETTI ===");
        System.out.println();
        
        Personaggio principessa = new Personaggio("Isabella");
        GestoreAzioni gestore = new GestoreAzioni();
        Map<String, OggettoGenerico> casa = FabbricaOggetti.creaCasaCompleta();
        
        System.out.println("🏠 Casa creata con " + casa.size() + " oggetti!");
        System.out.println("👸 " + principessa.getStato());
        System.out.println();
        
        simulaGiornata(principessa, gestore, casa);
        
        System.out.println("\n=== STATISTICHE FINALI ===");
        System.out.println("👸 " + principessa.getStato());
        
        System.out.println("\n=== AZIONI DISPONIBILI ===");
        OggettoGenerico letto = casa.get("letto");
        List<String> azioniDisponibili = gestore.getAzioniDisponibili(letto, principessa);
        System.out.println("Per " + letto.getNome() + ": " + azioniDisponibili);
    }
    
    private static void simulaGiornata(Personaggio principessa, GestoreAzioni gestore, Map<String, OggettoGenerico> casa) {
        System.out.println("🌅 INIZIO GIORNATA");
        eseguiEMostra("🚿 DOCCIA MATTUTINA", gestore, "usa", casa.get("doccia"), principessa);
        eseguiEMostra("🚽 BAGNO", gestore, "usa", casa.get("wc"), principessa);
        eseguiEMostra("🍳 COLAZIONE", gestore, "usa", casa.get("frigorifero"), principessa);
        eseguiEMostra("💧 BEVO ACQUA", gestore, "usa", casa.get("lavandino"), principessa);
        eseguiEMostra("👗 MI CAMBIO", gestore, "usa", casa.get("armadio"), principessa);
        eseguiEMostra("💻 COMPUTER", gestore, "usa", casa.get("computer"), principessa);
        eseguiEMostra("📺 GUARDO TV", gestore, "usa", casa.get("televisione"), principessa);
        eseguiEMostra("🎵 ASCOLTO MUSICA", gestore, "usa", casa.get("stereo"), principessa);
        eseguiEMostra("🌱 CURO LE PIANTE", gestore, "usa", casa.get("piante"), principessa);
        eseguiEMostra("🧹 PULIZIE", gestore, "pulisci", casa.get("aspirapolvere"), principessa);
        eseguiEMostra("🍽️ CUCINO CENA", gestore, "usa", casa.get("fornelli"), principessa);
        eseguiEMostra("😴 VADO A LETTO", gestore, "usa", casa.get("letto"), principessa);
        System.out.println("🌙 FINE GIORNATA");
    }
    
    private static void eseguiEMostra(String fase, GestoreAzioni gestore, String azione, OggettoGenerico oggetto, Personaggio personaggio) {
        System.out.println("\n" + fase);
        RisultatoAzione risultato = gestore.eseguiAzione(azione, oggetto, personaggio);
        System.out.println("➤ " + risultato.getMessaggio());
        personaggio.applicaEffetti(risultato);
              if (Math.abs(risultato.getDeltaFame()) > 0 || Math.abs(risultato.getDeltaSete()) > 0 || 
            Math.abs(risultato.getDeltaEnergia()) > 10 || Math.abs(risultato.getDeltaIgiene()) > 10) {
            System.out.println("📊 " + personaggio.getStato());
     }
    }
}

   

