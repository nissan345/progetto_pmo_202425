import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.action.ActionResult;
import main.model.character.Hair;
import main.model.character.MainCharacter;
import main.model.character.Outfit;
import main.model.character.Stats;
import main.model.requirement.CanPlayRequirement;
import main.model.requirement.CanSleepRequirement;
import main.model.world.gameItem.GameItem;

class GameItemTest {
	
	private MainCharacter character;
	private GameItem item;

	@BeforeEach
	void setUp() throws Exception {
		character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
	}

	@Test
	void testBuilder() {
		// Verifies that the builder creates a GameItem the right way
		item =  new GameItem.Builder("Armadio", "Camera da Letto", 80)
                .message("Provi dei nuovi outfit!")
                .energy(-5)
                .hygiene(15)
                .build();
		assertEquals("Provi dei nuovi outfit!", item.getMessage());
		assertEquals("Camera da Letto", item.getRoom());
		assertFalse(item.requiresChoice()); // Every GameItem by default doesn't require a choice
		assertEquals(0, item.getDeltaSatiety()); // By default, if a value is not specified, it should be zero
		assertEquals(-5, item.getDeltaEnergy()); 
	}
	
	@Test 
	void testRequirementFalse() {
		// Verifies that GameItem cannot be used if the requirement is not true
		// In this case character cannot use the bed
		item = new GameItem.Builder("Divano", "Salotto", 80)
                .message("Fai un pisolino sul divano")
                .requirement(new CanSleepRequirement())
                .build();
		ActionResult result = item.use(character);
		assertEquals(item.getRequirement().getFailureReasons(character), result.getMessages());	
		assertNull(result.getMessage()); 
	    assertNotNull(result.getMessages()); 
	    assertTrue(!result.getMessages().isEmpty());
	}		
	
	@Test
	void testRequirementTrueNoDynamic() {
		//  Verifies that GameItem can be used if the requirement is satisfied
		item = new GameItem.Builder("Divano", "Salotto", 80)
                .message("Fai un pisolino sul divano")
                .requirement(new CanSleepRequirement())
                .build();
		character.getStats().changeEnergy(-50); // The character should have 50 energy now
		ActionResult result = item.use(character);
		assertNotNull(result.getMessage());
		assertEquals(item.getMessage(), result.getMessage());
	}
	
	@Test
	void testRequirementTrueWithDynamic() {
		// This is a dynamic object, which means that the result will vary based on the character's stats
		// We'll first analyse the energy gain when energy > 40 and then en < 40
		// We'll see if the requirement works also in this case
		item = new GameItem.Builder("Divano", "Salotto", 80)
        .message("Fai un pisolino sul divano")
        .dynamic((mc, self) -> {
            int en = mc.getStats().getEnergy();
            int gain = (en < 40) ? 20 : 15;  
            return new ActionResult(self.getMessage(), 0, 0, gain, 0, 5);
        })
        .requirement(new CanSleepRequirement())
        .build();
		
		// The character shouldn't be able to use it, energy too high
		ActionResult result = item.use(character);
	    assertNotNull(result.getMessages()); 
	    assertTrue(!result.getMessages().isEmpty());
	    
	    // Since energy > 40, the character should have +15 gain in energy
	    character.getStats().changeEnergy(-35);
	    assertEquals(65, character.getStats().getEnergy());
	    ActionResult result2 = item.use(character);
	    assertNotNull(result2.getMessage());
	    assertEquals(15, result2.getDeltaEnergy());
	    
	    // Now energy should be 25
	    character.getStats().changeEnergy(-40);
	    ActionResult result3 = item.use(character);
		assertEquals(25 ,character.getStats().getEnergy());
		assertEquals(20, result3.getDeltaEnergy());
	}

}
