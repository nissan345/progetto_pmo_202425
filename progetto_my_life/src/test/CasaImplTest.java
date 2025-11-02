

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.neri.classes.OggettoGioco;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.Stanza;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CasaImplTest {

    private CasaImpl casa;
    private StanzaImpl salotto;
    private StanzaImpl cucina;
    private StanzaImpl camera;

    // Inizializzazione dei soggetti di test ---------------------------------------------------------------------------------
    @Before
    public void setUp() {
        casa = new CasaImpl();
        
        List<OggettoGioco> oggettiSalotto = new ArrayList<>();
        //oggettiSalotto.add(new OggettoGenerico(TipoOggetto.DIVANO));
        //oggettiSalotto.add(new OggettoGenerico(TipoOggetto.TELEVISIONE));
        salotto = new StanzaImpl("Salotto", oggettiSalotto);
        
        List<OggettoGioco> oggettiCucina = new ArrayList<>();
        //oggettiCucina.add(new OggettoGenerico(TipoOggetto.FRIGORIFERO));
        //oggettiCucina.add(new OggettoGenerico(TipoOggetto.FORNELLI));
        cucina = new StanzaImpl("Cucina", oggettiCucina);
        
        List<OggettoGioco> oggettiCamera = new ArrayList<>();
        //oggettiCamera.add(new OggettoGenerico(TipoOggetto.LETTO));
        //oggettiCamera.add(new OggettoGenerico(TipoOggetto.ARMADIO));
        camera = new StanzaImpl("Camera da letto", oggettiCamera);
    }

    // TEST PER CREARE LE STANZE ----------------------------------------------------------------------------------------------
    @Test
    public void testAggiungiStanza() {
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        
        Map<String, Stanza> stanze = casa.getStanze();
        assertEquals(2, stanze.size());
        assertTrue(stanze.containsKey("Salotto"));
        assertTrue(stanze.containsKey("Cucina"));
        assertEquals(salotto, stanze.get("Salotto"));
    }

    // TEST PER LE TRANSIZIONI TRA STANZE ------------------------------------------------------------------------------------
    @Test
    public void testEntraInStanzaEsistente() {
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        
        Optional<Stanza> stanzaEntrata = casa.entraInStanza("Cucina");
        assertTrue(stanzaEntrata.isPresent());
        assertEquals(cucina, stanzaEntrata.get());
        
        Optional<Stanza> stanzaCorrente = casa.getStanzaCorrente();
        assertTrue(stanzaCorrente.isPresent());
        assertEquals(cucina, stanzaCorrente.get());
    }

    @Test
    public void testTransizioneTraStanze() {
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(camera);
        
        // Entra nella prima stanza
        casa.entraInStanza("Salotto");
        Optional<Stanza> corrente = casa.getStanzaCorrente();
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
        
        Map<String, Stanza> stanze = casa.esciDaStanza();
        assertNotNull(stanze);
        assertEquals(2, stanze.size());
        // Verifica che la mappa restituita sia la stessa delle stanze della casa
        assertSame(casa.getStanze(), stanze);
    }

    // TEST PER LA GESTIONE DELLA CASA ------------------------------------------------------------------------------------------
    @Test
    public void testCasaVuota() {
        assertTrue(casa.getStanze().isEmpty());
        assertFalse(casa.getStanzaCorrente().isPresent());
    }

    @Test
    public void testGestioneStanzeMultiple() {
        // Aggiungi tutte e tre le stanze
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(camera);
        
        Map<String, Stanza> stanze = casa.getStanze();
        assertEquals(3, stanze.size());
        assertTrue(stanze.containsKey("Salotto"));
        assertTrue(stanze.containsKey("Cucina"));
        assertTrue(stanze.containsKey("Camera da letto"));
        
        // Verifica che ogni stanza abbia i suoi oggetti
        Stanza s = casa.getStanza("Salotto");
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
    }
}