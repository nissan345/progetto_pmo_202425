package ActionTest;


import main.model.action.ActionResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;


class ActionResultTest {

    @Test
    void testCostruttoreConSoloMessaggio() {
        ActionResult result = new ActionResult("Test messaggio");

        assertEquals("Test messaggio", result.getMessage());
        assertEquals(0, result.getDeltaSatiety());
        assertEquals(0, result.getDeltaHydration());
        assertEquals(0, result.getDeltaEnergy());
        assertEquals(0, result.getDeltaHygiene());
        assertEquals(5, result.getActionDuration());            // default duration
    }

    @Test
    void testCostruttoreCompletoSenzaDurataPersonalizzata() {
        ActionResult result = new ActionResult("Ok", 10, -5, 15, 0);

        assertEquals("Ok", result.getMessage());
        assertEquals(10, result.getDeltaSatiety());
        assertEquals(-5, result.getDeltaHydration());
        assertEquals(15, result.getDeltaEnergy());
        assertEquals(0, result.getDeltaHygiene());
        assertEquals(5, result.getActionDuration());            // default duration
    }

    @Test
    void testCostruttoreCompletoConDurataPersonalizzata() {
        ActionResult result = new ActionResult("azione lunga", 1, 2, 3, 4, 12);

        assertEquals("azione lunga", result.getMessage());
        assertEquals(1, result.getDeltaSatiety());
        assertEquals(2, result.getDeltaHydration());
        assertEquals(3, result.getDeltaEnergy());
        assertEquals(4, result.getDeltaHygiene());
        assertEquals(12, result.getActionDuration());           // custom duration
    }

    @Test
    void testCostruttoreConListaDiMessaggi() {
        List<String> messages = Arrays.asList("Step 1", "Step 2", "Step 3");
        ActionResult result = new ActionResult(messages);

        assertEquals(messages, result.getMessages());
        assertNull(result.getMessage(), "Il campo messaggio dovrebbe essere null quando si usa la lista di messaggi");
        assertEquals(5, result.getActionDuration()); 
    }

    @Test
    void testDefaultActionDurationQuandoCustomNonImpostato() {
        ActionResult result = new ActionResult("Default");
        assertEquals(5, result.getActionDuration());
    }

    @Test
    void testCustomActionDurationQuandoImpostata() {
        ActionResult result = new ActionResult("Personalizzata", 0, 0, 0, 0, 8);
        assertEquals(8, result.getActionDuration());
    }
}