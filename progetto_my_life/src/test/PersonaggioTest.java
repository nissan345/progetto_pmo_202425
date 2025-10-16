package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.fabbri.classes.*;
import main.fabbri.classes.PreferenzeGusto.Reazione;    // Questo va specificato perché è un ulteriore enum dentro PreferenzeGusto

import java.util.Map;

public class PersonaggioTest {

    private Personaggio personaggio;
    private Personaggio personaggioVegano;

    @Before
    public void setUp() {
        personaggio = new Personaggio("Giocatore", Vestito.INFORMALE, Dieta.ONNIVORO, Capelli.CORTI_MOSSI);
        personaggioVegano = new Personaggio("Vegano", Vestito.SPORTIVO, Dieta.VEGANO, Capelli.LUNGHI_LISCI);
        
        personaggio.setPreferenza(Gusto.DOLCE, Reazione.PIACE);
        personaggio.setPreferenza(Gusto.SALATO, Reazione.NON_PIACE);
        personaggioVegano.setPreferenza(Gusto.SALATO, Reazione.PIACE);
    }

    @Test
    public void testCreazionePersonaggio() {
        assertEquals("Giocatore", personaggio.getNome());
        assertEquals(1, personaggio.getLivello());
        assertEquals(Dieta.ONNIVORO, personaggio.getDieta());
        assertEquals(100, personaggio.getFame());
        assertEquals(100, personaggio.getSete());
        assertEquals(100, personaggio.getEnergia());
        assertEquals(100, personaggio.getIgiene());
    }

    @Test
    public void testMangiare() {
        personaggio.setFame(50);
        int fameIniziale = personaggio.getFame();
        String risultato = personaggio.mangia(TipoCibo.BISTECCA);
        assertTrue(risultato.contains("Hai mangiato Bistecca"));
        assertTrue(risultato.contains("Fame: +"));
        assertTrue(personaggio.getFame() > fameIniziale);
    }

    @Test
    public void testMangiareCiboIncompatibile() {
        String risultato = personaggioVegano.mangia(TipoCibo.BISTECCA);
        assertTrue(risultato.contains("Non puoi mangiare Bistecca con la tua dieta"));
        assertEquals(100, personaggioVegano.getFame());
    }

    @Test
    public void testBere() {
        personaggio.setSete(50);
        String risultato = personaggio.bevi(Bevanda.ACQUA);
        assertTrue(risultato.contains("Hai bevuto Acqua"));
        assertTrue(risultato.contains("Sete: +"));
        assertEquals(90, personaggio.getSete()); // 50 + 40 = 90
    }

    @Test
    public void testDormireLetto() {
        personaggio.setEnergia(20);
        String risultato = personaggio.dormi();
        assertTrue(risultato.contains("Hai dormito e recuperato "));
        assertTrue(risultato.contains("70"));
        assertEquals(90, personaggio.getEnergia()); // 20 + 70 = 90
    }

    @Test
    public void testFarePisolino() {
        personaggio.setEnergia(50);
        String risultato = personaggio.faiPisolino();
        assertTrue(risultato.contains("Hai fatto un pisolino e recuperato "));
        assertTrue(risultato.contains("40"));
        assertEquals(90, personaggio.getEnergia()); // 50 + 40 = 90
    }

    @Test
    public void testFareDoccia() {
        personaggio.setIgiene(30);
        String risultato = personaggio.faiDoccia();
        assertTrue(risultato.contains("Hai fatto la doccia e recuperato "));
        assertTrue(risultato.contains("40"));
        assertEquals(70, personaggio.getIgiene()); // 30 + 40 = 70
    }

    @Test
    public void testCambiareVestiti() {
        String risultato = personaggio.cambiaVestiti(Vestito.FORMALE);
        assertTrue(risultato.contains("Hai cambiato i vestiti in: "));
        assertTrue(risultato.contains(Vestito.FORMALE.getNome()));
        assertEquals(Vestito.FORMALE, personaggio.getVestiti());
    }

    @Test
    public void testCambiareCapelli() {
        String risultato = personaggio.cambiaCapelli(Capelli.LUNGHI_LISCI);
        assertTrue(risultato.contains("Hai cambiato i capelli in:"));
        assertTrue(risultato.contains(Capelli.LUNGHI_LISCI.getNome()));
        assertEquals(Capelli.LUNGHI_LISCI, personaggio.getCapelli());
    }

    @Test
    public void testStampaStato() {
        String stato = personaggio.stampaStato();
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
        Map<String, Integer> stato = personaggio.getStatoCompleto();
        assertEquals(4, stato.size());
        assertEquals(Integer.valueOf(100), stato.get("fame"));
        assertEquals(Integer.valueOf(100), stato.get("sete"));
        assertEquals(Integer.valueOf(100), stato.get("energia"));
        assertEquals(Integer.valueOf(100), stato.get("igiene"));
    }
}