

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import main.aboufaris.interfaces.Room;
import main.aboufaris.classes.*;

import main.aboufaris.interfaces.Room;
import main.aboufaris.classes.*;
>>>>>>> nicxole
import main.fabbri.classes.*;
import main.neri.classes.FabbricaOggetti;

import java.util.ArrayList;
import java.util.Map;

public class MainCharacterTest {

    private MainCharacter personaggio;
    private MainCharacter personaggioVegano;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {


        //Room roomIniziale = new IRoom("Salotto", new ArrayList<>(FabbricaOggetti.creaOggettiRoom("Salotto").values()));

    
        //personaggio = new MainCharacter("Giocatore", Vestito.INFORMALE, Dieta.ONNIVORO, Capelli.CORTI_MOSSI);
        //personaggioVegano = new MainCharacter("Vegano", Vestito.SPORTIVO, Dieta.VEGANO, Capelli.LUNGHI_LISCI);
        
        //Room stanzaIniziale = new StanzaImpl("Salotto", new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Salotto").values()));
    
        //character = new Character("Giocatore", Vestito.INFORMALE, Dieta.ONNIVORO, Capelli.CORTI_MOSSI);
        //personaggioVegano = new Character("Vegano", Vestito.SPORTIVO, Dieta.VEGANO, Capelli.LUNGHI_LISCI);

       // personaggio.pickCurrentRoom(roomIniziale);
        //personaggio.setPreferenza(Gusto.DOLCE, Reazione.PIACE);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        //personaggioVegano.setPreferenza(Gusto.SALATO, Reazione.PIACE);

    }

    // TEST PER CREAZIONE PERSONAGGIO -------------------------------------------------------------------------------------
    @Test

    public void testCreazioneMainCharacter() {

        assertEquals("Giocatore", personaggio.getName());
        //assertEquals(Dieta.ONNIVORO, personaggio.getDieta());
        assertEquals(100, personaggio.getHunger());
        assertEquals(100, personaggio.getThirst());
        assertEquals(100, personaggio.getEnergy());
        assertEquals(100, personaggio.getHygiene());

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


        int hungerIniziale = personaggio.getHunger();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Hai mangiato Bistecca"));
        //assertTrue(risultato.contains("Hunger: +"));
        assertTrue(personaggio.getHunger() > hungerIniziale);

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
        assertEquals(100, personaggioVegano.getHunger());
    }

    @Test
    public void testMangiareConPreferenzaGusto() {

        //personaggio.setFame(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.PIACE);
        //personaggio.setHunger(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.PIACE);
        int hungerIniziale = personaggio.getHunger();
       // String risultato = personaggio.mangia(TipoCibo.BISTECCA);
       // assertTrue(risultato.contains("Ti è piaciuto!"));
        // Verifica che la hunger sia aumentata più del valore base grazie al bonus
        assertTrue(personaggio.getHunger() > hungerIniziale + 40);

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

        //personaggio.setFame(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        int fameIniziale = personaggio.getFame();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Non ti è piaciuto"));
        // Verifica che la fame sia aumentata meno del valore base a causa del malus
        assertTrue(personaggio.getFame() < fameIniziale + 40);
        //personaggio.setHunger(50);
        //personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        int hungerIniziale = personaggio.getHunger();
        //String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        //assertTrue(risultato.contains("Non ti è piaciuto"));
        // Verifica che la hunger sia aumentata meno del valore base a causa del malus
        assertTrue(personaggio.getHunger() < hungerIniziale + 40);
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

        //personaggio.setSete(50);
       // String risultato = personaggio.bevi(Bevanda.ACQUA);
        //assertTrue(risultato.contains("Hai bevuto Acqua"));
        //assertTrue(risultato.contains("Sete: +"));
        //personaggio.setThirst(50);
       // String risultato = personaggio.bevi(Bevanda.ACQUA);
        //assertTrue(risultato.contains("Hai bevuto Acqua"));
        //assertTrue(risultato.contains("Thirst: +"));
        assertEquals(90, personaggio.getThirst()); // 50 + 40 = 90
        //character.setSete(50);
       // String risultato = character.bevi(Bevanda.ACQUA);
        //assertTrue(risultato.contains("Hai bevuto Acqua"));
        //assertTrue(risultato.contains("Sete: +"));
        assertEquals(90, character.getSete()); // 50 + 40 = 90

    }

    @Test
    public void testBereBevandaEnergizzante() {

        personaggio.setEnergia(50);
        String risultato = personaggio.bevi(Bevanda.CAFFE);
        assertTrue(risultato.contains("Energia: +"));
        assertEquals(80, personaggio.getEnergia()); // 50 + 30 = 80

        //personaggio.setEnergy(50);
        //String risultato = personaggio.bevi(Bevanda.CAFFE);
        //assertTrue(risultato.contains("Energy: +"));
        //assertEquals(80, personaggio.getEnergy()); // 50 + 30 = 80


        //character.setEnergia(50);
        //String risultato = character.bevi(Bevanda.CAFFE);
        //assertTrue(risultato.contains("Energia: +"));
        //assertEquals(80, character.getEnergia()); // 50 + 30 = 80

    }

    // TEST PER DORMIRE ----------------------------------------------------------------------------------------------
    @Test
    public void testDormireLetto() {

        personaggio.setEnergia(20);
        String risultato = personaggio.dormi();
        assertTrue(risultato.contains("Hai dormito e recuperato "));
        assertTrue(risultato.contains("70"));
        assertEquals(90, personaggio.getEnergia()); // 20 + 70 = 90
        //personaggio.setEnergy(20);
        //String risultato = personaggio.dormi();
        //assertTrue(risultato.contains("Hai dormito e recuperato "));
        //assertTrue(risultato.contains("70"));
        assertEquals(90, personaggio.getEnergy()); // 20 + 70 = 90

    }

    @Test
    public void testFarePisolino() {

        //personaggio.setEnergy(50);
        //String risultato = personaggio.faiPisolino();
        //assertTrue(risultato.contains("Hai fatto un pisolino e recuperato "));
        //assertTrue(risultato.contains("40"));
        assertEquals(90, personaggio.getEnergy()); // 50 + 40 = 90

    }

    // TEST PER IGIENE ------------------------------------------------------------------------------------------------
    @Test
    public void testFareDoccia() {

        //personaggio.setHygiene(30);
        //String risultato = personaggio.faiDoccia();
        //assertTrue(risultato.contains("Hai fatto la doccia e recuperato "));
        //assertTrue(risultato.contains("40"));
        assertEquals(70, personaggio.getHygiene()); // 30 + 40 = 70
    }

    // TEST PER CAMBIARE ASPETTO -------------------------------------------------------------------------------------
    @Test
    public void testCambiareVestiti() {

        String risultato = personaggio.cambiaVestiti(Vestito.FORMALE);
        assertTrue(risultato.contains("Hai cambiato i vestiti in: "));
        assertTrue(risultato.contains(Vestito.FORMALE.getNome()));
        assertEquals(Vestito.FORMALE, personaggio.getVestiti());

        //String risultato = personaggio.cambiaVestiti(Vestito.FORMALE);
        //assertTrue(risultato.contains("Hai cambiato i outfit in: "));
        //assertTrue(risultato.contains(Vestito.FORMALE.getName()));
        //assertEquals(Vestito.FORMALE, personaggio.getVestiti());
    }
/*
    @Test
    public void testCambiareCapelli() {
        String risultato = character.cambiaCapelli(Capelli.LUNGHI_LISCI);
        assertTrue(risultato.contains("Hai cambiato i capelli in:"));
        assertTrue(risultato.contains(Capelli.LUNGHI_LISCI.getNome()));
        assertEquals(Capelli.LUNGHI_LISCI, character.getCapelli());
    }*/

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

    


        Map<String, Integer> state = personaggio.getStatoCompleto();
        assertEquals(4, state.size());
        assertEquals(Integer.valueOf(100), state.get("hunger"));
        assertEquals(Integer.valueOf(100), state.get("thirst"));
        assertEquals(Integer.valueOf(100), state.get("energy"));
        assertEquals(Integer.valueOf(100), state.get("hygiene"));
}
}


