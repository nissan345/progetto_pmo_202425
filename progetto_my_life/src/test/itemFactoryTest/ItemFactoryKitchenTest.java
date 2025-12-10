package itemFactoryTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.action.ActionResult;
import main.model.character.enums.*;
import main.model.character.MainCharacter;
import main.model.requirement.requirementImpl.*;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.FoodType;
import main.model.world.gameItem.GameItem;
import main.model.world.gameItem.Refrigerator;

class ItemFactoryKitchenTest {
	
	private Room kitchen;
	
	@BeforeEach
	void setUp() throws Exception{
		kitchen = ItemFactory.createKitchen();
	}

	@Test
    void testCreateKitchen() {
        assertEquals("Cucina", kitchen.getRoomName());
        assertTrue(kitchen.getEntryRequirement() instanceof LevelRequirement);
        // You can access the kitchen if you're level 1
        assertEquals(1, ((LevelRequirement) kitchen.getEntryRequirement()).getRequiredLvl());  
        // Test items count
        List<GameItem> items = kitchen.getItemsInRoom();
        assertEquals(4, items.size());
    }

    @Test
    void testKitchenItems() {
        List<GameItem> items = kitchen.getItemsInRoom();
        
        // Test for each item in the kitchen, verifies their functionalities are present
        // Test Fornelli 
        GameItem fornelli = items.get(0);
        assertEquals("Fornelli", fornelli.getName());
        assertEquals("Cucina", fornelli.getRoom());
        assertEquals(80, fornelli.getSize());
        assertTrue(fornelli.getRequirement() instanceof CanEatRequirement);
        
        // Test Refrigerator
        GameItem refrigerator = items.get(1);
        assertTrue(refrigerator instanceof Refrigerator);
        assertEquals("Frigorifero", refrigerator.getName());
        assertEquals("Cucina", refrigerator.getRoom());
        assertEquals(90, refrigerator.getSize());
        assertTrue(refrigerator.getRequirement() instanceof CanEatRequirement);
        assertTrue(refrigerator.requiresChoice());
        
        // Test Lavandino
        GameItem lavandino = items.get(2);
        assertEquals("Lavandino", lavandino.getName());
        assertEquals("Cucina", lavandino.getRoom());
        assertEquals(80, lavandino.getSize());
        assertFalse(lavandino.requiresChoice());
    }

    @Test
    void testFornelliDynamicBehavior() {
        GameItem fornelli = kitchen.getItemsInRoom().get(0);
        MainCharacter character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
        
        // Test with different energy levels to verify dynamic behaviour
        character.getStats().changeEnergy(-80); // Low energy (<40), MainCharacter shouldn't be allowed to use fornelli
        ActionResult result1 = fornelli.use(character);
        character.applyActionResult(result1, fornelli);
        assertNotNull(result1);
        assertTrue(!result1.getMessages().isEmpty());
        assertEquals(20, character.getStats().getEnergy());
        
        character.getStats().changeSatiety(-30);
        character.getStats().changeEnergy(50); // Mid energy -> 70 < 80, cost should be -10 and duration 5
        ActionResult result2 = fornelli.use(character);
        assertNotNull(result2);
        assertEquals(60, character.getStats().getEnergy());
        assertEquals(5, result2.getActionDuration());
        
    }

    @Test
    void testRefrigeratorInKitchen() {
        Refrigerator refrigerator = (Refrigerator) kitchen.getItemsInRoom().get(1);
        
        // Test refrigerator specific functionality
        assertTrue(refrigerator.requiresChoice());
        assertEquals(FoodType.values().length, refrigerator.availableOptions().size());
        
        MainCharacter character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
        character.getStats().changeSatiety(-50); // Make sure requirement is satisfied
        int e0 = character.getStats().getEnergy();
        int h0 = character.getStats().getHygiene();
        int s0 = character.getStats().getSatiety();
        int w0 = character.getStats().getHydration();
        // Test basic use
        ActionResult result = refrigerator.use(character);
        assertEquals("Adesso che hai aperto il frigorifero, cosa ti va di mangiare?", result.getMessage());
        
        // Test with food choice
        ActionResult foodResult = refrigerator.useWithChoice(character, FoodType.SALAD);
        assertEquals("Mangi " + FoodType.SALAD.getName(), foodResult.getMessage());
        assertEquals(FoodType.SALAD.getSatiety(),   foodResult.getDeltaSatiety());
        assertEquals(FoodType.SALAD.getHydration(), foodResult.getDeltaHydration());
        assertEquals(FoodType.SALAD.getEnergy(),    foodResult.getDeltaEnergy());
        character.applyActionResult(foodResult, refrigerator);
        
        FoodType pick = FoodType.SALAD;
        
        assertEquals(Math.max(0, Math.min(100, s0 + pick.getSatiety())),   character.getStats().getSatiety());
        assertEquals(Math.max(0, Math.min(100, w0 + pick.getHydration())), character.getStats().getHydration());
        assertEquals(Math.max(0, Math.min(100, e0 + pick.getEnergy())),    character.getStats().getEnergy());
        assertEquals(Math.max(0, Math.min(100, h0 + 0)),                   character.getStats().getHygiene());
    }

    @Test
    void testLavandinoDynamicBehavior() {
        GameItem lavandino = kitchen.getItemsInRoom().get(2);
        MainCharacter character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
        
        // Test lavandino with different energy levels and see how the cost changes based on the eneregy
        
        character.getStats().changeEnergy(-70); // Low energy, cost should be -20
        ActionResult result1 = lavandino.use(character);
        assertNotNull(result1);
        assertEquals(10, character.getStats().getEnergy());
        assertEquals(10, result1.getActionDuration());
        
        character.getStats().changeEnergy(55); // Mid energy 65
        ActionResult result2 = lavandino.use(character);
        assertNotNull(result2);
        assertEquals(55, character.getStats().getEnergy());
        assertEquals(5, result2.getActionDuration());
        
        character.getStats().changeEnergy(40); // High energy
        ActionResult result3 = lavandino.use(character);
        assertNotNull(result3);
        assertEquals(90, character.getStats().getEnergy());
        assertEquals(2, result3.getActionDuration());
        
    }

    @Test
    void testKitchenRequirement() {
        LevelRequirement requirement = (LevelRequirement) kitchen.getEntryRequirement();
        
        MainCharacter character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
        
        // Test that level 1 requirement is satisfied for a new character
        assertTrue(requirement.isSatisfiedBy(character));
        
  
    }

}
