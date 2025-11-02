

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.aboufaris.classes.RoomImpl;
import main.giuseppetti.classes.Fratello;
import main.neri.classes.OggettoGioco;

import java.util.ArrayList;
import java.util.List;

public class RoomImplTest {
/*
    private RoomImpl stanza;
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
        stanza = new RoomImpl("Salotto", oggetti);
        fratello = new Fratello(stanza);
    }

    // TEST PER GLI NPC DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneNPC() {
        // Verifica iniziale che non ci siano NPC
        assertFalse(stanza.getNpcInRoom().isPresent());
        assertFalse(stanza.hasNpc(fratello));

        // Aggiungi NPC e verifica presenza
        stanza.setNpc(fratello);
        assertTrue(stanza.getNpcInRoom().isPresent());
        assertTrue(stanza.hasNpc(fratello));
        assertEquals(fratello, stanza.getNpcInRoom().get());
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
        assertTrue(stanza.hasOggettoRoom(divano));
        assertFalse(stanza.hasOggettoRoom(libreria));
        assertEquals(1, stanza.getOggettiInRoom().size());

        // Aggiungi secondo oggetto
        stanza.addOggettoRoom(libreria);
        assertTrue(stanza.hasOggettoRoom(libreria));
        assertEquals(2, stanza.getOggettiInRoom().size());

        // Rimuovi oggetto
        stanza.removeOggettoRoom(divano);
        assertFalse(stanza.hasOggettoRoom(divano));
        assertEquals(1, stanza.getOggettiInRoom().size());
    }

    @Test
    public void testRoomConVariOggetti() {
        OggettoGenerico televisione = new OggettoGenerico(TipoOggetto.TELEVISIONE);
        OggettoGenerico stereo = new OggettoGenerico(TipoOggetto.STEREO);
        
        stanza.addOggettoRoom(libreria);
        stanza.addOggettoRoom(televisione);
        stanza.addOggettoRoom(stereo);
        
        List<OggettoGioco> oggetti = stanza.getOggettiInRoom();
        assertEquals(4, oggetti.size());
        assertTrue(oggetti.contains(divano));
        assertTrue(oggetti.contains(libreria));
        assertTrue(oggetti.contains(televisione));
        assertTrue(oggetti.contains(stereo));
    }

    @Test
    public void testRoomVuota() {
        List<OggettoGioco> listaVuota = new ArrayList<>();
        RoomImpl stanzaVuota = new RoomImpl("Room Vuota", listaVuota);
        
        assertTrue(stanzaVuota.getOggettiInRoom().isEmpty());
        assertFalse(stanzaVuota.getNpcInRoom().isPresent());
    }

    @Test
    public void testInterazioneOggettiReali() {
        // Verifica che gli oggetti reali funzionino correttamente
        stanza.addOggettoRoom(libreria);
        
        // Test interazione con oggetto reale
        main.neri.classes.RisultatoAzione risultato = divano.usa(null);
        assertNotNull(risultato);
        assertTrue(risultato.getMessaggio().contains("Ti siedi sul divano"));
        
        // Verifica che l'oggetto sia effettivamente nella stanza
        assertTrue(stanza.hasOggettoRoom(libreria));
    }*/
}