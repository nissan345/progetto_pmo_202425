package gameItemTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.model.character.enums.*;
import main.model.character.MainCharacter;
import main.model.action.ActionResult;
import main.model.requirement.requirementImpl.*;
import main.model.world.gameItem.FoodType;
import main.model.world.gameItem.Refrigerator;


class RefrigeratorTest {

    private MainCharacter character;
    private Refrigerator refrigerator;

    @BeforeEach
    void setUp() {
        character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
        refrigerator = new Refrigerator();
    }

    @Test
    void testRefrigeratorCreation() {
    	// Verifies that the Refrigerator is correctly created
        assertEquals("Frigorifero", refrigerator.getName());
        assertEquals("Cucina", refrigerator.getRoom());
        assertEquals(90, refrigerator.getSize());
        assertTrue(refrigerator.requiresChoice());
        assertTrue(refrigerator.getRequirement() instanceof CanEatRequirement);
    }

    @Test
    void testAvailableOptions() {
        // Verifies that all foods are present
        assertFalse(refrigerator.availableOptions().isEmpty());
        assertEquals(FoodType.values().length, refrigerator.availableOptions().size());
        assertTrue(refrigerator.availableOptions().containsAll(Arrays.asList(FoodType.values())));
    }

    @Test
    void testUseWithoutChoice() {
        // Verifies the refrigerator's response when you don't specify a choice
    	character.getStats().changeSatiety(-50);
        ActionResult result = refrigerator.use(character);
        assertNotNull(result.getMessage());
        assertEquals("Adesso che hai aperto il frigorifero, cosa ti va di mangiare?", result.getMessage());
    }

    @Test
    void testUseWithChoice() {
        // Tests the use of each type of food that is in the refrigerators
        for (FoodType food : FoodType.values()) {
        	character.getStats().changeSatiety(-50);
            ActionResult result = refrigerator.useWithChoice(character, food);
            assertNotNull(result.getMessage());
            assertEquals("Mangi " + food.getName(), result.getMessage());
            assertEquals(food.getSatiety(), result.getDeltaSatiety());
            assertEquals(food.getHydration(), result.getDeltaHydration());
            assertEquals(food.getEnergy(), result.getDeltaEnergy());
            assertEquals(0, result.getDeltaHygiene()); 
        }
    }

    @Test
    void testUseWithInvalidChoice() {
        // Verifies the presence of an invalid choice if the requirements are met
    	character.getStats().changeSatiety(-50);
        ActionResult result = refrigerator.useWithChoice(character, null);
        assertEquals("Questo cibo non è disponibile nel frigorifero!", result.getMessage());
    }

    @Test
    void testUseWhenRequirementNotSatisfied() {
        // Character can't eat because his satiety is 100

        ActionResult result = refrigerator.use(character);
        assertNotNull(result.getMessages());
        assertNull(result.getMessage());    

        ActionResult resultWithChoice = refrigerator.useWithChoice(character, FoodType.SALAD);
        assertNotNull(resultWithChoice.getMessages()); 
        assertNull(resultWithChoice.getMessage()); 
    }

    @Test
    void testUseWhenRequirementSatisfied() {
        character.getStats().changeSatiety(-50); 

        ActionResult result = refrigerator.use(character);
        assertNotNull(result.getMessage());
        assertEquals("Adesso che hai aperto il frigorifero, cosa ti va di mangiare?", result.getMessage());

        
        ActionResult resultWithChoice = refrigerator.useWithChoice(character, FoodType.SALAD);
        assertNotNull(resultWithChoice.getMessage());
        assertEquals("Mangi " + FoodType.SALAD.getName(), resultWithChoice.getMessage());
        assertEquals(FoodType.SALAD.getSatiety(), resultWithChoice.getDeltaSatiety());
    }

}