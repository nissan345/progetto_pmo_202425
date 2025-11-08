package ActionTest;

import org.junit.jupiter.api.Test;

import main.model.action.ActionResult;

import static org.junit.jupiter.api.Assertions.*;

class ActionResultTest {

    @Test
    void testConstructorAndGetters() {
        ActionResult r = new ActionResult(
                "Ate food", 10, 5, -2, 0, 3
        );

        assertEquals("Ate food", r.getMessage());
        assertEquals(10, r.getDeltaSatiety());
        assertEquals(5, r.getDeltaHydration());
        assertEquals(-2, r.getDeltaEnergy());
        assertEquals(0, r.getDeltaHygiene());
    }

    @Test
    void testApplyEffects() {
        var character = new main.model.character.MainCharacter("Mario");

        int sat = character.getSatiety();
        int hyd = character.getHydration(); 

        ActionResult r = new ActionResult("Drink", 0, 10, 0, 0, 5);
        r.applyEffects(character);

        assertEquals(sat, character.getSatiety());
        assertEquals(hyd + 10, character.getHydration());
    }

    @Test
    void testDurationDefault() {
        ActionResult r = new ActionResult("Test", 0, 0, 0, 0, null);
        assertEquals(5, r.getDurationSeconds()); // valore di default
    }

    @Test
    void testCustomDuration() {
        ActionResult r = new ActionResult("Test", 0, 0, 0, 0, 20);
        assertEquals(20, r.getDurationSeconds());
    }
}
