

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD
import main.aboufaris.interfaces.Room;
import main.aboufaris.classes.*;
=======
<<<<<<< Updated upstream
import main.aboufaris.interfaces.Stanza;
=======
import main.aboufaris.interfaces.Room;
import main.aboufaris.classes.*;
>>>>>>> Stashed changes
>>>>>>> nicxole
import main.fabbri.classes.*;
import main.neri.classes.FabbricaOggetti;

import java.util.ArrayList;
import java.util.Map;

public class PersonaggioTest {

    private Character personaggio;
    private Character personaggioVegano;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
<<<<<<< HEAD
        //Stanza stanzaIniziale = new StanzaImpl("Salotto", new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Salotto").values()));
=======
<<<<<<< Updated upstream
        Stanza roomIniziale = new Stanza("Salotto");
=======
        //Room roomIniziale = new RoomImpl("Salotto", new ArrayList<>(FabbricaOggetti.creaOggettiRoom("Salotto").values()));
>>>>>>> Stashed changes
>>>>>>> nicxole
    
        //personaggio = new Personaggio("Giocatore", Vestito.INFORMALE, Dieta.ONNIVORO, Capelli.CORTI_MOSSI);
        //personaggioVegano = new Personaggio("Vegano", Vestito.SPORTIVO, Dieta.VEGANO, Capelli.LUNGHI_LISCI);
        
<<<<<<< HEAD
       // personaggio.scegliStanza(stanzaIniziale);
        //personaggio.setPreferenza(Gusto.DOLCE, Reazione.PIACE);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        //personaggioVegano.setPreferenza(Gusto.SALATO, Reazione.PIACE);
=======
<<<<<<< Updated upstream
        personaggio.scegliStanza(roomIniziale);
        personaggio.setPreferenza(Gusto.DOLCE, Reazione.PIACE);
        personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        personaggioVegano.setPreferenza(Gusto.SALATO, Reazione.PIACE);
=======
       // personaggio.pickCurrentRoom(roomIniziale);
        //personaggio.setPreferenza(Gusto.DOLCE, Reazione.PIACE);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        //personaggioVegano.setPreferenza(Gusto.SALATO, Reazione.PIACE);
>>>>>>> Stashed changes
>>>>>>> nicxole
    }

    // TEST PER CREAZIONE PERSONAGGIO -------------------------------------------------------------------------------------
    @Test
    public void testCreazionePersonaggio() {
<<<<<<< Updated upstream
        assertEquals("Giocatore", personaggio.getNome());
        //assertEquals(Dieta.ONNIVORO, personaggio.getDieta());
        assertEquals(100, personaggio.getFame());
        assertEquals(100, personaggio.getSete());
        assertEquals(100, personaggio.getEnergia());
        assertEquals(100, personaggio.getIgiene());
=======
        assertEquals("Giocatore", personaggio.getName());
        //assertEquals(Dieta.ONNIVORO, personaggio.getDieta());
        assertEquals(100, personaggio.getHunger());
        assertEquals(100, personaggio.getThirst());
        assertEquals(100, personaggio.getEnergy());
        assertEquals(100, personaggio.getHygiene());
>>>>>>> Stashed changes
    }

    // TEST PER MANGIARE ---------------------------------------------------------------------------------------------
    @Test
    public void testMangiare() {
<<<<<<< HEAD
        //personaggio.setFame(50);
=======
<<<<<<< Updated upstream
        personaggio.setFame(50);
>>>>>>> nicxole
        int fameIniziale = personaggio.getFame();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Hai mangiato Bistecca"));
        //assertTrue(risultato.contains("Fame: +"));
        assertTrue(personaggio.getFame() > fameIniziale);
=======
        //personaggio.setHunger(50);
        int hungerIniziale = personaggio.getHunger();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Hai mangiato Bistecca"));
        //assertTrue(risultato.contains("Hunger: +"));
        assertTrue(personaggio.getHunger() > hungerIniziale);
>>>>>>> Stashed changes
    }

    @Test
    public void testMangiareCiboIncompatibile() {
<<<<<<< HEAD
       // String risultato = personaggioVegano.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Non puoi mangiare Bistecca con la tua dieta"));
=======
<<<<<<< Updated upstream
        String risultato = personaggioVegano.mangia(TipoCibo.BISTECCA);
        assertTrue(risultato.contains("Non puoi mangiare Bistecca con la tua dieta"));
>>>>>>> nicxole
        assertEquals(100, personaggioVegano.getFame());
=======
       // String risultato = personaggioVegano.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Non puoi mangiare Bistecca con la tua dieta"));
        assertEquals(100, personaggioVegano.getHunger());
>>>>>>> Stashed changes
    }

    @Test
    public void testMangiareConPreferenzaGusto() {
<<<<<<< HEAD
        //personaggio.setFame(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.PIACE);
=======
<<<<<<< Updated upstream
        personaggio.setFame(50);
        personaggio.setPreferenza(Gusto.SALATO, Reazione.PIACE);
>>>>>>> nicxole
        int fameIniziale = personaggio.getFame();
       // String risultato = personaggio.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Ti è piaciuto!"));
        // Verifica che la fame sia aumentata più del valore base grazie al bonus
        assertTrue(personaggio.getFame() > fameIniziale + 40);
=======
        //personaggio.setHunger(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.PIACE);
        int hungerIniziale = personaggio.getHunger();
       // String risultato = personaggio.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Ti è piaciuto!"));
        // Verifica che la hunger sia aumentata più del valore base grazie al bonus
        assertTrue(personaggio.getHunger() > hungerIniziale + 40);
>>>>>>> Stashed changes
    }

    @Test
    public void testMangiareConGustoNonGradito() {
<<<<<<< HEAD
        //personaggio.setFame(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
=======
<<<<<<< Updated upstream
        personaggio.setFame(50);
        personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
>>>>>>> nicxole
        int fameIniziale = personaggio.getFame();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Non ti è piaciuto"));
        // Verifica che la fame sia aumentata meno del valore base a causa del malus
        assertTrue(personaggio.getFame() < fameIniziale + 40);
=======
        //personaggio.setHunger(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        int hungerIniziale = personaggio.getHunger();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Non ti è piaciuto"));
        // Verifica che la hunger sia aumentata meno del valore base a causa del malus
        assertTrue(personaggio.getHunger() < hungerIniziale + 40);
>>>>>>> Stashed changes
    }

    // TEST PER BERE -------------------------------------------------------------------------------------------------
    @Test
    public void testBere() {
<<<<<<< HEAD
        //personaggio.setSete(50);
       // String risultato = personaggio.bevi(Bevanda.ACQUA);
        //assertTrue(risultato.contains("Hai bevuto Acqua"));
        //assertTrue(risultato.contains("Sete: +"));
=======
<<<<<<< Updated upstream
        personaggio.setSete(50);
        String risultato = personaggio.bevi(Bevanda.ACQUA);
        assertTrue(risultato.contains("Hai bevuto Acqua"));
        assertTrue(risultato.contains("Sete: +"));
>>>>>>> nicxole
        assertEquals(90, personaggio.getSete()); // 50 + 40 = 90
=======
        //personaggio.setThirst(50);
       // String risultato = personaggio.bevi(Bevanda.ACQUA);
        //assertTrue(risultato.contains("Hai bevuto Acqua"));
        //assertTrue(risultato.contains("Thirst: +"));
        assertEquals(90, personaggio.getThirst()); // 50 + 40 = 90
>>>>>>> Stashed changes
    }

    @Test
    public void testBereBevandaEnergizzante() {
<<<<<<< HEAD
        //personaggio.setEnergia(50);
        //String risultato = personaggio.bevi(Bevanda.CAFFE);
        //assertTrue(risultato.contains("Energia: +"));
        //assertEquals(80, personaggio.getEnergia()); // 50 + 30 = 80
=======
<<<<<<< Updated upstream
        personaggio.setEnergia(50);
        String risultato = personaggio.bevi(Bevanda.CAFFE);
        assertTrue(risultato.contains("Energia: +"));
        assertEquals(80, personaggio.getEnergia()); // 50 + 30 = 80
=======
        //personaggio.setEnergy(50);
        //String risultato = personaggio.bevi(Bevanda.CAFFE);
        //assertTrue(risultato.contains("Energy: +"));
        //assertEquals(80, personaggio.getEnergy()); // 50 + 30 = 80
>>>>>>> Stashed changes
>>>>>>> nicxole
    }

    // TEST PER DORMIRE ----------------------------------------------------------------------------------------------
    @Test
    public void testDormireLetto() {
<<<<<<< HEAD
        //personaggio.setEnergia(20);
        //String risultato = personaggio.dormi();
        //assertTrue(risultato.contains("Hai dormito e recuperato "));
        //assertTrue(risultato.contains("70"));
=======
<<<<<<< Updated upstream
        personaggio.setEnergia(20);
        String risultato = personaggio.dormi();
        assertTrue(risultato.contains("Hai dormito e recuperato "));
        assertTrue(risultato.contains("70"));
>>>>>>> nicxole
        assertEquals(90, personaggio.getEnergia()); // 20 + 70 = 90
=======
        //personaggio.setEnergy(20);
        //String risultato = personaggio.dormi();
        //assertTrue(risultato.contains("Hai dormito e recuperato "));
        //assertTrue(risultato.contains("70"));
        assertEquals(90, personaggio.getEnergy()); // 20 + 70 = 90
>>>>>>> Stashed changes
    }

    @Test
    public void testFarePisolino() {
<<<<<<< HEAD
        //personaggio.setEnergia(50);
        //String risultato = personaggio.faiPisolino();
        //assertTrue(risultato.contains("Hai fatto un pisolino e recuperato "));
        //assertTrue(risultato.contains("40"));
=======
<<<<<<< Updated upstream
        personaggio.setEnergia(50);
        String risultato = personaggio.faiPisolino();
        assertTrue(risultato.contains("Hai fatto un pisolino e recuperato "));
        assertTrue(risultato.contains("40"));
>>>>>>> nicxole
        assertEquals(90, personaggio.getEnergia()); // 50 + 40 = 90
=======
        //personaggio.setEnergy(50);
        //String risultato = personaggio.faiPisolino();
        //assertTrue(risultato.contains("Hai fatto un pisolino e recuperato "));
        //assertTrue(risultato.contains("40"));
        assertEquals(90, personaggio.getEnergy()); // 50 + 40 = 90
>>>>>>> Stashed changes
    }

    // TEST PER IGIENE ------------------------------------------------------------------------------------------------
    @Test
    public void testFareDoccia() {
<<<<<<< HEAD
        //personaggio.setIgiene(30);
        //String risultato = personaggio.faiDoccia();
        //assertTrue(risultato.contains("Hai fatto la doccia e recuperato "));
        //assertTrue(risultato.contains("40"));
=======
<<<<<<< Updated upstream
        personaggio.setIgiene(30);
        String risultato = personaggio.faiDoccia();
        assertTrue(risultato.contains("Hai fatto la doccia e recuperato "));
        assertTrue(risultato.contains("40"));
>>>>>>> nicxole
        assertEquals(70, personaggio.getIgiene()); // 30 + 40 = 70
=======
        //personaggio.setHygiene(30);
        //String risultato = personaggio.faiDoccia();
        //assertTrue(risultato.contains("Hai fatto la doccia e recuperato "));
        //assertTrue(risultato.contains("40"));
        assertEquals(70, personaggio.getHygiene()); // 30 + 40 = 70
>>>>>>> Stashed changes
    }

    // TEST PER CAMBIARE ASPETTO -------------------------------------------------------------------------------------
    @Test
    public void testCambiareVestiti() {
<<<<<<< HEAD
        //String risultato = personaggio.cambiaVestiti(Vestito.FORMALE);
        //assertTrue(risultato.contains("Hai cambiato i vestiti in: "));
        //assertTrue(risultato.contains(Vestito.FORMALE.getNome()));
        //assertEquals(Vestito.FORMALE, personaggio.getVestiti());
=======
<<<<<<< Updated upstream
        String risultato = personaggio.cambiaVestiti(Vestito.FORMALE);
        assertTrue(risultato.contains("Hai cambiato i vestiti in: "));
        assertTrue(risultato.contains(Vestito.FORMALE.getNome()));
        assertEquals(Vestito.FORMALE, personaggio.getVestiti());
=======
        //String risultato = personaggio.cambiaVestiti(Vestito.FORMALE);
        //assertTrue(risultato.contains("Hai cambiato i outfit in: "));
        //assertTrue(risultato.contains(Vestito.FORMALE.getName()));
        //assertEquals(Vestito.FORMALE, personaggio.getVestiti());
>>>>>>> Stashed changes
>>>>>>> nicxole
    }
/*
    @Test
    public void testCambiareCapelli() {
        String risultato = personaggio.cambiaCapelli(Capelli.LUNGHI_LISCI);
        assertTrue(risultato.contains("Hai cambiato i hair in:"));
        assertTrue(risultato.contains(Capelli.LUNGHI_LISCI.getName()));
        assertEquals(Capelli.LUNGHI_LISCI, personaggio.getCapelli());
    }

    // TEST PER STAMPA STATO -----------------------------------------------------------------------------------------------
    @Test
    public void testStampaStato() {
        String state = personaggio.printState();
        assertTrue(state.contains("STATO DI GIOCATORE"));
        assertTrue(state.contains("Livello: 1"));
        assertTrue(state.contains("Dieta: Onnivoro"));
        assertTrue(state.contains("Hunger: 100/100"));
        assertTrue(state.contains("Thirst: 100/100"));
        assertTrue(state.contains("Energy: 100/100"));
        assertTrue(state.contains("Hygiene: 100/100"));
        assertTrue(state.contains("Preferenze di gusto:"));
    }

    @Test
    public void testGetStatoCompleto() {
<<<<<<< Updated upstream
        Map<String, Integer> stato = personaggio.getStatoCompleto();
        assertEquals(4, stato.size());
        assertEquals(Integer.valueOf(100), stato.get("fame"));
        assertEquals(Integer.valueOf(100), stato.get("sete"));
        assertEquals(Integer.valueOf(100), stato.get("energia"));
        assertEquals(Integer.valueOf(100), stato.get("igiene"));
<<<<<<< HEAD
    }*/
=======
    }
=======
        Map<String, Integer> state = personaggio.getStatoCompleto();
        assertEquals(4, state.size());
        assertEquals(Integer.valueOf(100), state.get("hunger"));
        assertEquals(Integer.valueOf(100), state.get("thirst"));
        assertEquals(Integer.valueOf(100), state.get("energy"));
        assertEquals(Integer.valueOf(100), state.get("hygiene"));
    }*/
>>>>>>> Stashed changes
>>>>>>> nicxole
}