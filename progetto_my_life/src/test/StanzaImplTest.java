

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.aboufaris.classes.StanzaImpl;
import model.character.npc.Brother;
import model.world.gameItem.GameItem;

import java.util.ArrayList;
import java.util.List;

public class StanzaImplTest {
/*
    private StanzaImpl room;
    private OggettoGenerico divano;
    private OggettoGenerico libreria;
    private Brother brother;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
        List<GameItem> oggetti = new ArrayList<>();
        divano = new OggettoGenerico(TipoOggetto.DIVANO);
        libreria = new OggettoGenerico(TipoOggetto.LIBRERIA);
        
        oggetti.add(divano);
        room = new StanzaImpl("Salotto", oggetti);
        brother = new Brother(room);
    }

    // TEST PER GLI NPC DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneNPC() {
        // Verifica iniziale che non ci siano NPC
        assertFalse(room.getNpcInStanza().isPresent());
        assertFalse(room.hasNpc(brother));

        // Aggiungi NPC e verifica presenza
        room.setNpc(brother);
        assertTrue(room.getNpcInStanza().isPresent());
        assertTrue(room.hasNpc(brother));
        assertEquals(brother, room.getNpcInStanza().get());
    }

    @Test
    public void testHasNpcConQualsiasiNPC() {
        Brother altroFratello = new Brother(room);
        room.setNpc(brother);
        
        assertTrue(room.hasNpc(altroFratello));
    }

    // TEST PER GLI OGGETTI DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneOggetti() {
        // Verifica oggetto iniziale
        assertTrue(room.hasOggettoStanza(divano));
        assertFalse(room.hasOggettoStanza(libreria));
        assertEquals(1, room.getOggettiInStanza().size());

        // Aggiungi secondo oggetto
        room.addOggettoStanza(libreria);
        assertTrue(room.hasOggettoStanza(libreria));
        assertEquals(2, room.getOggettiInStanza().size());

        // Rimuovi oggetto
        room.removeOggettoStanza(divano);
        assertFalse(room.hasOggettoStanza(divano));
        assertEquals(1, room.getOggettiInStanza().size());
    }

    @Test
    public void testStanzaConVariOggetti() {
        OggettoGenerico televisione = new OggettoGenerico(TipoOggetto.TELEVISIONE);
        OggettoGenerico stereo = new OggettoGenerico(TipoOggetto.STEREO);
        
        room.addOggettoStanza(libreria);
        room.addOggettoStanza(televisione);
        room.addOggettoStanza(stereo);
        
        List<GameItem> oggetti = room.getOggettiInStanza();
        assertEquals(4, oggetti.size());
        assertTrue(oggetti.contains(divano));
        assertTrue(oggetti.contains(libreria));
        assertTrue(oggetti.contains(televisione));
        assertTrue(oggetti.contains(stereo));
    }

    @Test
    public void testStanzaVuota() {
        List<GameItem> listaVuota = new ArrayList<>();
        StanzaImpl stanzaVuota = new StanzaImpl("Room Vuota", listaVuota);
        
        assertTrue(stanzaVuota.getOggettiInStanza().isEmpty());
        assertFalse(stanzaVuota.getNpcInStanza().isPresent());
    }

    @Test
    public void testInterazioneOggettiReali() {
        // Verifica che gli oggetti reali funzionino correttamente
        room.addOggettoStanza(libreria);
        
        // Test interazione con oggetto reale
        main.neri.classes.ActionResult risultato = divano.usa(null);
        assertNotNull(risultato);
        assertTrue(risultato.getMessaggio().contains("Ti siedi sul divano"));
        
        // Verifica che l'oggetto sia effettivamente nella room
        assertTrue(room.hasOggettoStanza(libreria));
    }*/
}