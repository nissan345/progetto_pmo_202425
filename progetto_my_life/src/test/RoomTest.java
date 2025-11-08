

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.main.model.world.Room;
import java.main.model.world.gameItem.GameItem;
import java.util.ArrayList;
import java.util.List;

public class RoomTest {
/*
    private IRoom room;
    private OggettoGenerico divano;
    private OggettoGenerico libreria;
    private Fratello fratello;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
        List<GameItem> oggetti = new ArrayList<>();
        divano = new OggettoGenerico(TipoOggetto.DIVANO);
        libreria = new OggettoGenerico(TipoOggetto.LIBRERIA);
        
        oggetti.add(divano);
        room = new IRoom("Salotto", oggetti);
        fratello = new Fratello(room);
    }

    // TEST PER GLI NPC DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneNPC() {
        // Verifica iniziale che non ci siano NPC
        assertFalse(room.getNpcInRoom().isPresent());
        assertFalse(room.hasNpc(fratello));

        // Aggiungi NPC e verifica presenza
        room.setNpc(fratello);
        assertTrue(room.getNpcInRoom().isPresent());
        assertTrue(room.hasNpc(fratello));
        assertEquals(fratello, room.getNpcInRoom().get());
    }

    @Test
    public void testHasNpcConQualsiasiNPC() {
        Fratello altroFratello = new Fratello(room);
        room.setNpc(fratello);
        
        assertTrue(room.hasNpc(altroFratello));
    }

    // TEST PER GLI OGGETTI DELLA STANZA -------------------------------------------------------------------------------------
    @Test
    public void testGestioneOggetti() {
        // Verifica oggetto iniziale
        assertTrue(room.hasOggettoRoom(divano));
        assertFalse(room.hasOggettoRoom(libreria));
        assertEquals(1, room.getOggettiInRoom().size());

        // Aggiungi secondo oggetto
        room.addOggettoRoom(libreria);
        assertTrue(room.hasOggettoRoom(libreria));
        assertEquals(2, room.getOggettiInRoom().size());

        // Rimuovi oggetto
        room.removeOggettoRoom(divano);
        assertFalse(room.hasOggettoRoom(divano));
        assertEquals(1, room.getOggettiInRoom().size());
    }

    @Test
    public void testRoomConVariOggetti() {
        OggettoGenerico televisione = new OggettoGenerico(TipoOggetto.TELEVISIONE);
        OggettoGenerico stereo = new OggettoGenerico(TipoOggetto.STEREO);
        
        room.addOggettoRoom(libreria);
        room.addOggettoRoom(televisione);
        room.addOggettoRoom(stereo);
        
        List<GameItem> oggetti = room.getOggettiInRoom();
        assertEquals(4, oggetti.size());
        assertTrue(oggetti.contains(divano));
        assertTrue(oggetti.contains(libreria));
        assertTrue(oggetti.contains(televisione));
        assertTrue(oggetti.contains(stereo));
    }

    @Test
    public void testRoomVuota() {
        List<GameItem> listaVuota = new ArrayList<>();
        IRoom roomVuota = new IRoom("Room Vuota", listaVuota);
        
        assertTrue(roomVuota.getOggettiInRoom().isEmpty());
        assertFalse(roomVuota.getNpcInRoom().isPresent());
    }

    @Test
    public void testInterazioneOggettiReali() {
        // Verifica che gli oggetti reali funzionino correttamente
        room.addOggettoRoom(libreria);
        
        // Test interazione con oggetto reale
        main.neri.classes.ActionResult risultato = divano.usa(null);
        assertNotNull(risultato);
        assertTrue(risultato.getMessaggio().contains("Ti siedi sul divano"));
        
        // Verifica che l'oggetto sia effettivamente nella room
        assertTrue(room.hasOggettoRoom(libreria));
    }*/
}