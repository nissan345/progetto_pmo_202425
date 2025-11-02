

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.aboufaris.classes.StanzaImpl;
import main.giuseppetti.classes.Fratello;
import main.neri.classes.OggettoGioco;

import java.util.ArrayList;
import java.util.List;

public class StanzaImplTest {
/*
    private StanzaImpl stanza;
    private OggettoGenerico divano;
    private OggettoGenerico libreria;
    private Fratello fratello;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
        List<OggettoGioco> oggetti = new ArrayList<>();
        divano = new OggettoGenerico(TipoOggetto.DIVANO);
        libreria = new OggettoGenerico(TipoOggetto.LIBRERIA);
        
        oggetti.add(divano);
        stanza = new StanzaImpl("Salotto", oggetti);
        fratello = new Fratello(stanza);
    }

    // TEST PER GLI NPC DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneNPC() {
        // Verifica iniziale che non ci siano NPC
        assertFalse(stanza.getNpcInStanza().isPresent());
        assertFalse(stanza.hasNpc(fratello));

        // Aggiungi NPC e verifica presenza
        stanza.setNpc(fratello);
        assertTrue(stanza.getNpcInStanza().isPresent());
        assertTrue(stanza.hasNpc(fratello));
        assertEquals(fratello, stanza.getNpcInStanza().get());
    }

    @Test
    public void testHasNpcConQualsiasiNPC() {
        Fratello altroFratello = new Fratello(stanza);
        stanza.setNpc(fratello);
        
        assertTrue(stanza.hasNpc(altroFratello));
    }

    // TEST PER GLI OGGETTI DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneOggetti() {
        // Verifica oggetto iniziale
        assertTrue(stanza.hasOggettoStanza(divano));
        assertFalse(stanza.hasOggettoStanza(libreria));
        assertEquals(1, stanza.getOggettiInStanza().size());

        // Aggiungi secondo oggetto
        stanza.addOggettoStanza(libreria);
        assertTrue(stanza.hasOggettoStanza(libreria));
        assertEquals(2, stanza.getOggettiInStanza().size());

        // Rimuovi oggetto
        stanza.removeOggettoStanza(divano);
        assertFalse(stanza.hasOggettoStanza(divano));
        assertEquals(1, stanza.getOggettiInStanza().size());
    }

    @Test
    public void testStanzaConVariOggetti() {
        OggettoGenerico televisione = new OggettoGenerico(TipoOggetto.TELEVISIONE);
        OggettoGenerico stereo = new OggettoGenerico(TipoOggetto.STEREO);
        
        stanza.addOggettoStanza(libreria);
        stanza.addOggettoStanza(televisione);
        stanza.addOggettoStanza(stereo);
        
        List<OggettoGioco> oggetti = stanza.getOggettiInStanza();
        assertEquals(4, oggetti.size());
        assertTrue(oggetti.contains(divano));
        assertTrue(oggetti.contains(libreria));
        assertTrue(oggetti.contains(televisione));
        assertTrue(oggetti.contains(stereo));
    }

    @Test
    public void testStanzaVuota() {
        List<OggettoGioco> listaVuota = new ArrayList<>();
        StanzaImpl stanzaVuota = new StanzaImpl("Stanza Vuota", listaVuota);
        
        assertTrue(stanzaVuota.getOggettiInStanza().isEmpty());
        assertFalse(stanzaVuota.getNpcInStanza().isPresent());
    }

    @Test
    public void testInterazioneOggettiReali() {
        // Verifica che gli oggetti reali funzionino correttamente
        stanza.addOggettoStanza(libreria);
        
        // Test interazione con oggetto reale
        main.neri.classes.RisultatoAzione risultato = divano.usa(null);
        assertNotNull(risultato);
        assertTrue(risultato.getMessaggio().contains("Ti siedi sul divano"));
        
        // Verifica che l'oggetto sia effettivamente nella stanza
        assertTrue(stanza.hasOggettoStanza(libreria));
    }*/
}