

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.neri.classes.OggettoGioco;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CasaImplTest {

<<<<<<< HEAD
    private House casa;
    private IRoom salotto;
    private IRoom cucina;
    private IRoom camera;
=======
    private CasaImpl casa;
    private IRoom salotto;
    private IRoom cucina;
    private IRoom camera;
>>>>>>> nicxole

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
<<<<<<< HEAD
        casa = new House();
=======
        casa = new CasaImpl();
>>>>>>> nicxole
        
        List<OggettoGioco> oggettiSalotto = new ArrayList<>();
        //oggettiSalotto.add(new OggettoGenerico(TipoOggetto.DIVANO));
        //oggettiSalotto.add(new OggettoGenerico(TipoOggetto.TELEVISIONE));
<<<<<<< HEAD
        salotto = new IRoom("Salotto", oggettiSalotto);
=======
        salotto = new IRoom("Salotto", oggettiSalotto);
>>>>>>> nicxole
        
        List<OggettoGioco> oggettiCucina = new ArrayList<>();
        //oggettiCucina.add(new OggettoGenerico(TipoOggetto.FRIGORIFERO));
        //oggettiCucina.add(new OggettoGenerico(TipoOggetto.FORNELLI));
<<<<<<< HEAD
        cucina = new IRoom("Cucina", oggettiCucina);
=======
        cucina = new IRoom("Cucina", oggettiCucina);
>>>>>>> nicxole
        
        List<OggettoGioco> oggettiCamera = new ArrayList<>();
        //oggettiCamera.add(new OggettoGenerico(TipoOggetto.LETTO));
        //oggettiCamera.add(new OggettoGenerico(TipoOggetto.ARMADIO));
<<<<<<< HEAD
        camera = new IRoom("Camera da letto", oggettiCamera);
=======
        camera = new IRoom("Camera da letto", oggettiCamera);
>>>>>>> nicxole
    }

    // TEST PER CREARE LE STANZE ----------------------------------------------------------------------------------------------
    @Test
<<<<<<< HEAD
    public void testAggiungiStanza() {
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
=======
    public void testAggiungiRoom() {
        casa.aggiungiRoom(salotto);
        casa.aggiungiRoom(cucina);
>>>>>>> nicxole
        
        Map<String, Room> stanze = casa.getStanze();
        assertEquals(2, stanze.size());
        assertTrue(stanze.containsKey("Salotto"));
        assertTrue(stanze.containsKey("Cucina"));
        assertEquals(salotto, stanze.get("Salotto"));
    }

    // TEST PER LE TRANSIZIONI TRA STANZE ------------------------------------------------------------------------------------
    @Test
<<<<<<< HEAD
    public void testEntraInStanzaEsistente() {
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        
        Optional<Room> stanzaEntrata = casa.entraInStanza("Cucina");
        assertTrue(stanzaEntrata.isPresent());
        assertEquals(cucina, stanzaEntrata.get());
        
        Optional<Room> stanzaCorrente = casa.getStanzaCorrente();
        assertTrue(stanzaCorrente.isPresent());
        assertEquals(cucina, stanzaCorrente.get());
=======
    public void testEntraInRoomEsistente() {
        casa.aggiungiRoom(salotto);
        casa.aggiungiRoom(cucina);
        
        Optional<Room> roomEntrata = casa.entraInRoom("Cucina");
        assertTrue(roomEntrata.isPresent());
        assertEquals(cucina, roomEntrata.get());
        
        Optional<Room> currentRoom = casa.getCurrentRoom();
        assertTrue(currentRoom.isPresent());
        assertEquals(cucina, currentRoom.get());
>>>>>>> nicxole
    }

    @Test
    public void testTransizioneTraStanze() {
<<<<<<< HEAD
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(camera);
        
        // Entra nella prima stanza
        casa.entraInStanza("Salotto");
        Optional<Room> corrente = casa.getStanzaCorrente();
        assertTrue(corrente.isPresent());
        assertEquals("Salotto", corrente.get().getNomeStanza());
        
        // Passa a un'altra stanza
        casa.entraInStanza("Cucina");
        corrente = casa.getStanzaCorrente();
        assertTrue(corrente.isPresent());
        assertEquals("Cucina", corrente.get().getNomeStanza());
        
        // Passa alla terza stanza
        casa.entraInStanza("Camera da letto");
        corrente = casa.getStanzaCorrente();
        assertTrue(corrente.isPresent());
        assertEquals("Camera da letto", corrente.get().getNomeStanza());
    }

    @Test
    public void testEsciDaStanzaConStanzaCorrente() {
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        casa.entraInStanza("Salotto");
        
        Map<String, Room> stanze = casa.esciDaStanza();
=======
        casa.aggiungiRoom(salotto);
        casa.aggiungiRoom(cucina);
        casa.aggiungiRoom(camera);
        
        // Entra nella prima room
        casa.entraInRoom("Salotto");
        Optional<Room> corrente = casa.getCurrentRoom();
        assertTrue(corrente.isPresent());
        assertEquals("Salotto", corrente.get().getNameRoom());
        
        // Passa a un'altra room
        casa.entraInRoom("Cucina");
        corrente = casa.getCurrentRoom();
        assertTrue(corrente.isPresent());
        assertEquals("Cucina", corrente.get().getNameRoom());
        
        // Passa alla terza room
        casa.entraInRoom("Camera da letto");
        corrente = casa.getCurrentRoom();
        assertTrue(corrente.isPresent());
        assertEquals("Camera da letto", corrente.get().getNameRoom());
    }

    @Test
    public void testEsciDaRoomConCurrentRoom() {
        casa.aggiungiRoom(salotto);
        casa.aggiungiRoom(cucina);
        casa.entraInRoom("Salotto");
        
        Map<String, Room> stanze = casa.esciDaRoom();
>>>>>>> nicxole
        assertNotNull(stanze);
        assertEquals(2, stanze.size());
        // Verifica che la mappa restituita sia la stessa delle stanze della casa
        assertSame(casa.getStanze(), stanze);
    }

    // TEST PER LA GESTIONE DELLA CASA ------------------------------------------------------------------------------------------
    @Test
    public void testCasaVuota() {
        assertTrue(casa.getStanze().isEmpty());
<<<<<<< HEAD
        assertFalse(casa.getStanzaCorrente().isPresent());
=======
        assertFalse(casa.getCurrentRoom().isPresent());
>>>>>>> nicxole
    }

    @Test
    public void testGestioneStanzeMultiple() {
        // Aggiungi tutte e tre le stanze
<<<<<<< HEAD
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(camera);
=======
        casa.aggiungiRoom(salotto);
        casa.aggiungiRoom(cucina);
        casa.aggiungiRoom(camera);
>>>>>>> nicxole
        
        Map<String, Room> stanze = casa.getStanze();
        assertEquals(3, stanze.size());
        assertTrue(stanze.containsKey("Salotto"));
        assertTrue(stanze.containsKey("Cucina"));
        assertTrue(stanze.containsKey("Camera da letto"));
        
<<<<<<< HEAD
        // Verifica che ogni stanza abbia i suoi oggetti
        Room s = casa.getStanza("Salotto");
        //assertTrue(s.hasOggettoStanza(new OggettoGenerico(TipoOggetto.DIVANO)));
    }

    @Test
    public void testStanzaCorrenteDopoAggiunta() {
        // Aggiungi stanza ma non entrare
        casa.aggiungiStanza(salotto);
        assertFalse(casa.getStanzaCorrente().isPresent());
        
        // Dopo essere entrati, deve essere impostata
        casa.entraInStanza("Salotto");
        assertTrue(casa.getStanzaCorrente().isPresent());
=======
        // Verifica che ogni room abbia i suoi oggetti
        Room s = casa.getRoom("Salotto");
        //assertTrue(s.hasOggettoRoom(new OggettoGenerico(TipoOggetto.DIVANO)));
    }

    @Test
    public void testCurrentRoomDopoAggiunta() {
        // Aggiungi room ma non entrare
        casa.aggiungiRoom(salotto);
        assertFalse(casa.getCurrentRoom().isPresent());
        
        // Dopo essere entrati, deve essere impostata
        casa.entraInRoom("Salotto");
        assertTrue(casa.getCurrentRoom().isPresent());
>>>>>>> nicxole
    }
}