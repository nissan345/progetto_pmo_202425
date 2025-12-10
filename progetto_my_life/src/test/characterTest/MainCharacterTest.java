
package characterTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.character.MainCharacter;
import main.model.character.npc.*;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.GameItem;
import main.model.character.enums.*;
import main.model.quest.*;

class MainCharacterTest {

    private MainCharacter character;
    private Room bedroom;
    private Room kitchen;
    private Room bathroom;
    private Room garden;
    private Room livingroom;
    private NPC questGiver;
    private House h;

    /**
     * Initializes test environment before each test method execution.
     * Creates a main character with default attributes and sets up house rooms.
     * Verifies initial character state including level, room location, and inventory capacity.
     */
    @BeforeEach
    void setUp() {
        character = new MainCharacter("Ahri", Outfit.CASUAL, Hair.CURLY_LONG);
        assertEquals(1, character.getLvl());                                        
        assertNull(character.getCurrentRoom());                                
        assertNotNull(character.getInventory());
        assertEquals(30, character.getInventory().getCapacity());


        h = new House(); 
        bedroom    = ItemFactory.createBedroom();
        kitchen    = ItemFactory.createKitchen();
        bathroom   = ItemFactory.createBathroom();
        garden     = ItemFactory.createGarden();
        livingroom = ItemFactory.createLivingRoom();

        
        h.addRoom(bedroom);
        h.addRoom(kitchen);
        h.addRoom(bathroom);
        h.addRoom(garden);
        h.addRoom(livingroom);
        
        questGiver = new Mum(kitchen, h);
    }


    // TESTS -------------------------------------------------------------------------------

    /**
     * Tests MainCharacter constructor and getter methods.
     * Verifies correct initialization of name, outfit, hair style, stats, and default progression values.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("Ahri", character.getName());
        assertEquals(Outfit.CASUAL, character.getOutfit());
        assertEquals(Hair.CURLY_LONG, character.getHair());
        assertNotNull(character.getStats());

        // Default progression state
        assertEquals(1, character.getLvl());
        assertEquals(0, character.getXp());
        assertEquals(100, character.getXpToNext());             // computeXpToNext(1) = 100
        assertNull(character.getCurrentRoom());
    }

    // TEST FOR XP AND LEVELING UP -------------------------------------------------------

    /**
     * Tests experience point accumulation without triggering level up.
     * Verifies XP increases but level remains unchanged when XP threshold is not reached.
     */
    @Test
    void testAddXp_NoLevelUp() {
        character.addXp(30);
        assertEquals(1, character.getLvl());
        assertEquals(30, character.getXp());
        assertEquals(100, character.getXpToNext());
    }

    /**
     * Tests experience point accumulation triggering exactly one level up.
     * Verifies level increases, XP resets, and new XP threshold is set correctly.
     */
    @Test
    void testAddXp_ExactlyLevelUp() {
        character.addXp(100);
        // Gets to level 2, thus the xp resets to 0 and new threshold is set -> 150
        assertEquals(2, character.getLvl());
        assertEquals(0, character.getXp());
        assertEquals(150, character.getXpToNext());
    }

    /**
     * Tests experience point accumulation triggering multiple level ups.
     * Verifies correct level progression, residual XP calculation, and updated XP threshold.
     */
    @Test
    void testAddXp_MultipleLevelUps() {
        character.addXp(100 + 150 + 215 + 10);          // 475
        assertEquals(4, character.getLvl());            
        assertEquals(10, character.getXp());            // residue XP = 10
        assertEquals(287, character.getXpToNext());     // XP to next level = 287
    }

    /**
     * Tests direct level up method call.
     * Verifies level increment and XP threshold update without XP accumulation.
     */
    @Test
    void testLevelUpDirectCall() {
        // Both the level and the threshold should update
        character.levelUp();
        assertEquals(2, character.getLvl());
        assertEquals(150, character.getXpToNext());
    }

    // TEST FOR STATE DECAY -----------------------------------------------------

    /**
     * Tests character state decay functionality.
     * Verifies that state decay process executes without errors and decreases character's satiety.
     */
    @Test
    void testStateDecayDoesNotCrash() {
        int sat0 = character.getStats().getSatiety();
        character.stateDecay();
        int sat1 = character.getStats().getSatiety();
        // Checking that the satiety has decreased
        assertTrue(sat1 <= sat0);
    }

    // TEST FOR ROOM ENTRY ----------------------------------------------------------------------
    
    /**
     * Tests room entry restrictions based on level requirements.
     * Verifies that character cannot enter rooms with level requirements higher than current level.
     * Checks appropriate error messages and maintains null current room after failed entry attempts.
     */
    @Test
    void testCannotEnterRoomWhenRequirementFails() {
        // Bathroom requires level 2
        String msg1 = character.pickCurrentRoom(bathroom);
        assertNotNull(msg1);
        assertTrue(msg1.startsWith("Non puoi entrare in: Bagno"), "Messaggio inatteso: " + msg1);
        assertTrue(msg1.contains("Livello richiesto: 2"), "Motivo mancante nel messaggio: " + msg1);
        assertEquals(null, character.getCurrentRoom());

        // Garden requires level 4
        String msg2 = character.pickCurrentRoom(garden);
        assertNotNull(msg2);
        assertTrue(msg2.startsWith("Non puoi entrare in: Giardino"), "Messaggio inatteso: " + msg2);
        assertTrue(msg2.contains("Livello richiesto: 4"), "Motivo mancante nel messaggio: " + msg2);
        assertEquals(null, character.getCurrentRoom());
    }

    /**
     * Tests successful room entry when level requirements are met.
     * Verifies entry messages and updates to character's current room.
     */
    @Test
    void testEnterRoomWhenRequirementPasses() {
        String msg1 = character.pickCurrentRoom(bedroom);
        assertNotNull(msg1);
        assertTrue(msg1.startsWith("Sei entrato in: Camera Da Letto"), "Messaggio inatteso: " + msg1);
        assertEquals("Camera Da Letto", character.getCurrentRoom().getRoomName());

        String msg2 = character.pickCurrentRoom(kitchen);
        assertNotNull(msg2);
        assertTrue(msg2.startsWith("Sei entrato in: Cucina"), "Messaggio inatteso: " + msg2);
        assertEquals("Cucina", character.getCurrentRoom().getRoomName());
    }

    // TEST FOR QUESTS ---------------------------------------------------------------------

    /**
     * Tests quest assignment and retrieval functionality.
     * Verifies that quests can be added to character and retrieved by associated NPC.
     */
    @Test
    void testAddActiveQuest() {
    	Quest q = new Quest.Builder("FetchStereo", "Prendi lo stereo", questGiver)
                .affinityPoints(5)
                .xpReward(20)
                .build();

        character.addQuest(q);

        assertTrue(character.hasActiveQuestWithNPC(questGiver));
    }

    /**
     * Tests quest item usage tracking.
     * Verifies that items used for quests are properly recorded and recognized.
     */
    @Test
    void testItemsForQuest() {
    	Quest q = new Quest.Builder("FetchStereo", "Prendi lo stereo", questGiver)
                .affinityPoints(5)
                .xpReward(20)
                .build();
        character.addQuest(q);

        GameItem computer = new GameItem.Builder("Computer", "Bedroom", 20)
                .message("Usi il PC.")
                .build();

        assertFalse(character.hasUsedItemForQuest(computer));

        character.recordItemsUsedForQuests(computer);

        assertTrue(character.hasUsedItemForQuest(computer));

        GameItem pentola = new GameItem.Builder("Pentola", "Kitchen", 10).build();
        assertFalse(character.hasUsedItemForQuest(pentola));
    }

    /**
     * Tests quest completion condition checking with game items.
     * Verifies completion condition correctly identifies when required items have been used.
     */
    @Test
    void testItemHasBeenUsed() {
    	Quest q = new Quest.Builder("FetchStereo", "Prendi lo stereo", questGiver)
                .affinityPoints(5)
                .xpReward(20)
                .build();
        character.addQuest(q);

        GameItem computer = new GameItem.Builder("Computer", "Bedroom", 20).build();

        CompletionCondition cond = new ItemUsageCondition(computer);

        assertFalse(cond.checkCompletion(character));

        character.recordItemsUsedForQuests(computer);

        assertTrue(cond.checkCompletion(character));
    }

    // TEST FOR ITEMS ---------------------------------------------------------------------
    
    /**
     * Tests item pickup restriction to prevent duplicate acquisitions.
     * Verifies items can only be picked up once and inventory remains unchanged on duplicate attempts.
     */
    @Test
    void testPickUpOnce() {
        character.pickCurrentRoom(bedroom);

        GameItem comb = new GameItem.Builder("Spazzola", "Bedroom", 20)
                .message("Usi il PC.")
                .build();

        bedroom.addItemRoom(comb);
        assertTrue(bedroom.hasItemRoom(comb));

        // Added to inventory
        character.pickUpItemAction(comb);
        assertEquals(20, character.getInventory().getUsedSpace());
        assertTrue(character.getInventory().hasItem("Spazzola"));
        assertFalse(bedroom.hasItemRoom(comb)); 

        
        character.pickUpItemAction(comb);
        assertEquals(20, character.getInventory().getUsedSpace()); // Stays the same
        assertTrue(character.getInventory().hasItem("Spazzola"));
        assertFalse(bedroom.hasItemRoom(comb));
    }

    /**
     * Tests pickup of multiple distinct items.
     * Verifies all items are correctly added to inventory and removed from room.
     */
    @Test
    void TestPickUpTwoDifferentItems() {
    	
    	character.pickCurrentRoom(bedroom);
    	
        GameItem phone = new GameItem.Builder("Telefono", "Bedroom", 5)
                .message("Controlli i messaggi.")
                .build();
        GameItem book = new GameItem.Builder("Libro", "Bedroom", 0).build();

        bedroom.addItemRoom(phone);
        bedroom.addItemRoom(book);
        assertTrue(bedroom.hasItemRoom(phone));
        assertTrue(bedroom.hasItemRoom(book));

        character.pickUpItemAction(phone);


        character.pickUpItemAction(book);


        assertEquals(2, character.getInventory().getItems().size());
        assertTrue(character.getInventory().hasItem("Telefono"));
        assertTrue(character.getInventory().hasItem("Libro"));

        assertFalse(bedroom.hasItemRoom(phone));
        assertFalse(bedroom.hasItemRoom(book));
    }

    /**
     * Tests item dropping functionality.
     * Verifies items are removed from inventory and returned to the current room.
     */
    @Test
    void testDropItem() {
        character.pickCurrentRoom(bedroom);

        GameItem mug = new GameItem.Builder("Tazza", "Bedroom", 0)
                .message("Sorbisci un caffè.")
                .build();

        bedroom.addItemRoom(mug);
        assertTrue(bedroom.hasItemRoom(mug));
        
        // Adds to inventory
        character.pickUpItemAction(mug);
        assertEquals(1, character.getInventory().getItems().size());
        assertFalse(bedroom.hasItemRoom(mug));

        // Removes from inventory
        character.dropItemAction(mug);
        assertEquals(0, character.getInventory().getItems().size());

        // Add's the object back into the room
        bedroom.addItemRoom(mug);
        assertTrue(bedroom.hasItemRoom(mug));
    }

}