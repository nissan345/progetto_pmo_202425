import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.action.ActionResult;
import main.model.character.Hair;
import main.model.character.MainCharacter;
import main.model.character.Outfit;
import main.model.requirement.CanPlayRequirement;
import main.model.requirement.CanSleepRequirement;
import main.model.requirement.LevelRequirement;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.GameItem;

class ItemFactoryBedRoomTest {

	private MainCharacter character;
    private Room bedroom;

    @BeforeEach
    void setUp() {
        character = new MainCharacter("Test", Outfit.CASUAL, Hair.CURLY_LONG);
        bedroom = ItemFactory.createBedroom();
    }

    @Test
    void testBedroomCreation() {
        // Test basic room properties
        assertEquals("Camera Da Letto", bedroom.getRoomName());
        assertTrue(bedroom.getEntryRequirement() instanceof LevelRequirement);
        assertEquals(1, ((LevelRequirement) bedroom.getEntryRequirement()).getRequiredLvl());
        
        // Test items count
        assertEquals(3, bedroom.getItemsInRoom().size());
    }

    @Test
    void testBedroomItems() {
        var items = bedroom.getItemsInRoom();
        
        // Test Letto
        GameItem letto = items.get(0);
        assertEquals("Letto", letto.getName());
        assertEquals("Camera da Letto", letto.getRoom());
        assertEquals(80, letto.getSize());
        assertTrue(letto.getRequirement() instanceof CanSleepRequirement);
        
        // Test Computer
        GameItem computer = items.get(1);
        assertEquals("Computer", computer.getName());
        assertEquals("Camera da Letto", computer.getRoom());
        assertEquals(20, computer.getSize());
        assertTrue(computer.getRequirement() instanceof CanPlayRequirement);
        
        // Test Armadio
        GameItem armadio = items.get(2);
        assertEquals("Armadio", armadio.getName());
        assertEquals("Camera da Letto", armadio.getRoom());
        assertEquals(80, armadio.getSize());
    }

    @Test
    void testBedDynamicBehavior() {
        GameItem letto = bedroom.getItemsInRoom().get(0);

        // LOW: en=20 (<40) → +30 energy, -10 hygiene, duration 10
        character.getStats().changeEnergy(-80); // 100 -> 20
        ActionResult low = letto.use(character);
        assertNotNull(low);
        assertEquals(30,  low.getDeltaEnergy());
        assertEquals(-10, low.getDeltaHygiene());
        assertEquals(10,  low.getActionDuration());

        // MID: en=60 (40..79) → +20 energy, -10 hygiene, duration 5
        int now = character.getStats().getEnergy();
        character.getStats().changeEnergy(60 - now); // -> 60
        ActionResult mid = letto.use(character);
        assertNotNull(mid);
        assertEquals(20,  mid.getDeltaEnergy());
        assertEquals(-10, mid.getDeltaHygiene());
        assertEquals(5,   mid.getActionDuration());

    
        character.getStats().changeEnergy(90 - character.getStats().getEnergy()); // -> 90
        ActionResult high = letto.use(character);
        assertNull(high.getMessage());
        assertFalse(high.getMessages().isEmpty());
    }

    @Test
    void testComputerDynamicBehavior() {
        GameItem computer = bedroom.getItemsInRoom().get(1);

        // LOW: en=30 (<40, >20) → -20, duration 10
        character.getStats().changeEnergy(30 - character.getStats().getEnergy()); // 100 -> 30
        ActionResult low = computer.use(character);
        assertNotNull(low);
        assertEquals(-20, low.getDeltaEnergy());
        assertEquals(-5,  low.getDeltaSatiety());
        assertEquals(-5,  low.getDeltaHydration());
        assertEquals(-5,  low.getDeltaHygiene());
        assertEquals(10,  low.getActionDuration());

        // MID: en=70 → -10, duration 5
        character.getStats().changeEnergy(70 - character.getStats().getEnergy());
        ActionResult mid = computer.use(character);
        assertNotNull(mid);
        assertEquals(-10, mid.getDeltaEnergy());
        assertEquals(-5,  mid.getDeltaSatiety());
        assertEquals(-5,  mid.getDeltaHydration());
        assertEquals(-5,  mid.getDeltaHygiene());
        assertEquals(5,   mid.getActionDuration());

        // HIGH: en=90 → -5, duration 2
        character.getStats().changeEnergy(90 - character.getStats().getEnergy());
        ActionResult high = computer.use(character);
        assertNotNull(high);
        assertEquals(-5, high.getDeltaEnergy());
        assertEquals(-5, high.getDeltaSatiety());
        assertEquals(-5, high.getDeltaHydration());
        assertEquals(-5, high.getDeltaHygiene());
        assertEquals(2,  high.getActionDuration());
    }

    @Test
    void testClosetStaticBehavior() {
        GameItem armadio = bedroom.getItemsInRoom().get(2);
        
        // The closet has static behaviour so there's not dynamic function
        ActionResult result = armadio.use(character);
        assertNotNull(result);
        assertEquals("Provi dei nuovi outfit!", result.getMessage());
        assertEquals(-5, result.getDeltaEnergy()); 
        assertEquals(30, result.getDeltaHygiene()); 
        assertEquals(0, result.getDeltaSatiety()); 
        assertEquals(0, result.getDeltaHydration());
    }

    @Test
    void testLettoRequirement() {
        GameItem letto = bedroom.getItemsInRoom().get(0);
        CanSleepRequirement requirement = (CanSleepRequirement) letto.getRequirement();
        
        // Test that requirement works correctly
        assertTrue(!requirement.isSatisfiedBy(character)); // Should be satisfied initially
        

    }

    @Test
    void testComputerRequirement() {
        GameItem computer = bedroom.getItemsInRoom().get(1);
        CanPlayRequirement requirement = (CanPlayRequirement) computer.getRequirement();
        
        // Test that requirement works correctly
        assertTrue(requirement.isSatisfiedBy(character)); // Should be satisfied initially
        
       
    }

    @Test
    void testBedroomRequirement() {
        LevelRequirement requirement = (LevelRequirement) bedroom.getEntryRequirement();
        
        // Test that level 1 requirement is satisfied for a new character
        assertTrue(requirement.isSatisfiedBy(character));
        
 
     
    }

    @Test
    void testItemMessages() {
        var items = bedroom.getItemsInRoom();
        
        assertEquals("Ti sdrai e ti riposi", items.get(0).getMessage());
        assertEquals("Giochi al pc.", items.get(1).getMessage());
        assertEquals("Provi dei nuovi outfit!", items.get(2).getMessage());
    }

    @Test
    void testLettoWhenRequirementNotSatisfied() {
        GameItem letto = bedroom.getItemsInRoom().get(0);
        
        // Simulate condition where sleep requirement is not satisfied
        character.getStats().changeEnergy(100); // Max energy
        
        ActionResult result = letto.use(character);
        // Should return failure reasons if requirement not satisfied
        assertNotNull(result.getMessages());
        assertNull(result.getMessage()); 
    }

}
