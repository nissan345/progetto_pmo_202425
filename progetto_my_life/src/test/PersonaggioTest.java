

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.aboufaris.interfaces.Room;
import main.aboufaris.classes.*;
import main.fabbri.classes.*;
import main.neri.classes.FabbricaOggetti;

import java.util.ArrayList;
import java.util.Map;

public class PersonaggioTest {

    private Character character;
    private Character personaggioVegano;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
        //Room stanzaIniziale = new StanzaImpl("Salotto", new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Salotto").values()));
    
        //character = new Character("Giocatore", Vestito.INFORMALE, Dieta.ONNIVORO, Capelli.CORTI_MOSSI);
        //personaggioVegano = new Character("Vegano", Vestito.SPORTIVO, Dieta.VEGANO, Capelli.LUNGHI_LISCI);
        
       // character.scegliStanza(stanzaIniziale);
        //character.setPreferenza(Gusto.DOLCE, Reazione.PIACE);
        //character.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        //personaggioVegano.setPreferenza(Gusto.SALATO, Reazione.PIACE);
    }

    // TEST PER CREAZIONE PERSONAGGIO -------------------------------------------------------------------------------------
    @Test
    public void testCreazionePersonaggio() {
        assertEquals("Giocatore", character.getNome());
        //assertEquals(Dieta.ONNIVORO, character.getDieta());
        assertEquals(100, character.getFame());
        assertEquals(100, character.getSete());
        assertEquals(100, character.getEnergia());
        assertEquals(100, character.getIgiene());
    }

    // TEST PER MANGIARE ---------------------------------------------------------------------------------------------
    @Test
    public void testMangiare() {
        //character.setFame(50);
        int fameIniziale = character.getFame();
        //String risultato = character.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Hai mangiato Bistecca"));
        //assertTrue(risultato.contains("Fame: +"));
        assertTrue(character.getFame() > fameIniziale);
    }

    @Test
    public void testMangiareCiboIncompatibile() {
       // String risultato = personaggioVegano.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Non puoi mangiare Bistecca con la tua dieta"));
        assertEquals(100, personaggioVegano.getFame());
    }

    @Test
    public void testMangiareConPreferenzaGusto() {
        //character.setFame(50);
        //character.setPreferenza(Gusto.SALATO, Reazione.PIACE);
        int fameIniziale = character.getFame();
       // String risultato = character.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Ti è piaciuto!"));
        // Verifica che la fame sia aumentata più del valore base grazie al bonus
        assertTrue(character.getFame() > fameIniziale + 40);
    }

    @Test
    public void testMangiareConGustoNonGradito() {
        //character.setFame(50);
        //character.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        int fameIniziale = character.getFame();
        //String risultato = character.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Non ti è piaciuto"));
        // Verifica che la fame sia aumentata meno del valore base a causa del malus
        assertTrue(character.getFame() < fameIniziale + 40);
    }

    // TEST PER BERE -------------------------------------------------------------------------------------------------
    @Test
    public void testBere() {
        //character.setSete(50);
       // String risultato = character.bevi(Bevanda.ACQUA);
        //assertTrue(risultato.contains("Hai bevuto Acqua"));
        //assertTrue(risultato.contains("Sete: +"));
        assertEquals(90, character.getSete()); // 50 + 40 = 90
    }

    @Test
    public void testBereBevandaEnergizzante() {
        //character.setEnergia(50);
        //String risultato = character.bevi(Bevanda.CAFFE);
        //assertTrue(risultato.contains("Energia: +"));
        //assertEquals(80, character.getEnergia()); // 50 + 30 = 80
    }

    // TEST PER DORMIRE ----------------------------------------------------------------------------------------------
    @Test
    public void testDormireLetto() {
        //character.setEnergia(20);
        //String risultato = character.dormi();
        //assertTrue(risultato.contains("Hai dormito e recuperato "));
        //assertTrue(risultato.contains("70"));
        assertEquals(90, character.getEnergia()); // 20 + 70 = 90
    }

    @Test
    public void testFarePisolino() {
        //character.setEnergia(50);
        //String risultato = character.faiPisolino();
        //assertTrue(risultato.contains("Hai fatto un pisolino e recuperato "));
        //assertTrue(risultato.contains("40"));
        assertEquals(90, character.getEnergia()); // 50 + 40 = 90
    }

    // TEST PER IGIENE ------------------------------------------------------------------------------------------------
    @Test
    public void testFareDoccia() {
        //character.setIgiene(30);
        //String risultato = character.faiDoccia();
        //assertTrue(risultato.contains("Hai fatto la doccia e recuperato "));
        //assertTrue(risultato.contains("40"));
        assertEquals(70, character.getIgiene()); // 30 + 40 = 70
    }

    // TEST PER CAMBIARE ASPETTO -------------------------------------------------------------------------------------
    @Test
    public void testCambiareVestiti() {
        //String risultato = character.cambiaVestiti(Vestito.FORMALE);
        //assertTrue(risultato.contains("Hai cambiato i vestiti in: "));
        //assertTrue(risultato.contains(Vestito.FORMALE.getNome()));
        //assertEquals(Vestito.FORMALE, character.getVestiti());
    }
/*
    @Test
    public void testCambiareCapelli() {
        String risultato = character.cambiaCapelli(Capelli.LUNGHI_LISCI);
        assertTrue(risultato.contains("Hai cambiato i capelli in:"));
        assertTrue(risultato.contains(Capelli.LUNGHI_LISCI.getNome()));
        assertEquals(Capelli.LUNGHI_LISCI, character.getCapelli());
    }

    // TEST PER STAMPA STATO -----------------------------------------------------------------------------------------------
    @Test
    public void testStampaStato() {
        String stato = character.stampaStato();
        assertTrue(stato.contains("STATO DI GIOCATORE"));
        assertTrue(stato.contains("Livello: 1"));
        assertTrue(stato.contains("Dieta: Onnivoro"));
        assertTrue(stato.contains("Fame: 100/100"));
        assertTrue(stato.contains("Sete: 100/100"));
        assertTrue(stato.contains("Energia: 100/100"));
        assertTrue(stato.contains("Igiene: 100/100"));
        assertTrue(stato.contains("Preferenze di gusto:"));
    }

    @Test
    public void testGetStatoCompleto() {
        Map<String, Integer> stato = character.getStatoCompleto();
        assertEquals(4, stato.size());
        assertEquals(Integer.valueOf(100), stato.get("fame"));
        assertEquals(Integer.valueOf(100), stato.get("sete"));
        assertEquals(Integer.valueOf(100), stato.get("energia"));
        assertEquals(Integer.valueOf(100), stato.get("igiene"));
    }*/
}