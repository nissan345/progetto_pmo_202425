
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.character.MainCharacter;
import main.model.character.NPC;
import main.model.character.Outfit;
import main.model.character.npc.Mum;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.GameItem;
import main.model.character.Hair;
import main.model.quest.*;

class MainCharacterTest {

    private MainCharacter character;
    private Room bedroom;
    private Room kitchen;
    private Room bathroom;
    private Room garden;
    private NPC questGiver;

    @BeforeEach
    void setUp() {
        character = new MainCharacter("Ahri", Outfit.CASUAL, Hair.CURLY_LONG);
        assertEquals(1, character.getLvl());    // Initial level = 1
        assertNull(character.getCurrentRoom()); // nessuna stanza all’inizio
        assertNotNull(character.getInventory());
        assertEquals(0, character.getInventory().getCapacity());

        bedroom    = ItemFactory.createBedroom();
        kitchen    = ItemFactory.createKitchen();
        bathroom   = ItemFactory.createBathroom();
        garden     = ItemFactory.createGarden();
        questGiver = new Mum(kitchen);
    }


    // TESTS -------------------------------------------------------------------------------

    // Test for constructor and getters
    @Test
    void testConstructorAndGetters() {
        assertEquals("Ahri", character.getName());
        assertEquals(Outfit.CASUAL, character.getOutfit());
        assertEquals(Hair.CURLY_LONG, character.getHair());
        assertNotNull(character.getStats());

        // Default progression state
        assertEquals(1, character.getLvl());
        assertEquals(0, character.getXp());
        assertEquals(100, character.getXpToNext()); // computeXpToNext(1) = 100
        assertNull(character.getCurrentRoom());
        //assertNotNull(character.getInventory());    // se l’inventory parte null, cambiare
    }

    // TEST FOR XP AND LEVELING UP -------------------------------------------------------

    // Test for adding XP without leveling up
    @Test
    void testAddXp_NoLevelUp() {
        character.addXp(30);
        assertEquals(1, character.getLvl());
        assertEquals(30, character.getXp());
        assertEquals(100, character.getXpToNext());
    }

    // Test for adding XP that causes exactly one level up
    @Test
    void testAddXp_ExactlyLevelUp() {
        character.addXp(100);
        // Gets to level 2, thus the xp resets to 0 and new threshold is set -> 150
        assertEquals(2, character.getLvl());
        assertEquals(0, character.getXp());
        assertEquals(150, character.getXpToNext());
    }

    // Test for adding XP that causes multiple level ups
    @Test
    void testAddXp_MultipleLevelUps() {
        character.addXp(100 + 150 + 215 + 10); // 475
        assertEquals(4, character.getLvl());    // 1→2→3→4
        assertEquals(10, character.getXp());    // residue XP = 10
        assertEquals(287, character.getXpToNext()); // XP to next level = 287
    }

    // Test for direct level up call
    @Test
    void testLevelUpDirectCall() {
        // Both the level and the threshold should update
        character.levelUp();
        assertEquals(2, character.getLvl());
        assertEquals(150, character.getXpToNext());
    }

    // TEST FOR STATE DECAY -----------------------------------------------------

    @Test
    void testStateDecayDoesNotCrash() {
        int sat0 = character.getStats().getSatiety();
        character.stateDecay();
        int sat1 = character.getStats().getSatiety();
        // checking that the satiety has decreased
        assertTrue(sat1 <= sat0);
    }

    // TEST FOR ROOM ENTRY ----------------------------------------------------------------------
    
    // Testing that the character cannot enter a room when the level requirement fails
    @Test
    void testCannotEnterRoomWhenRequirementFails() {
        String m1 = character.pickCurrentRoom(bathroom);
        assertNotNull(m1);
        assertTrue(m1.startsWith("Non puoi entrare in: Bathroom"), "Messaggio inatteso: " + m1);
        assertTrue(m1.contains("Serve livello 2"), "Motivo mancante. Messaggio: " + m1);
        assertNull(character.getCurrentRoom());

        String m2 = character.pickCurrentRoom(garden);
        assertNotNull(m2);
        assertTrue(m2.startsWith("Non puoi entrare in: Garden"), "Messaggio inatteso: " + m2);
        assertTrue(m2.contains("Serve livello 3"), "Motivo mancante. Messaggio: " + m2);
        assertNull(character.getCurrentRoom());
    }

    // Testing that the character can enter a room when the level requirement passes
    @Test
    void testEnterRoomWhenRequirementPasses() {
        String msg1 = character.pickCurrentRoom(bedroom);
        assertNotNull(msg1);
        assertTrue(msg1.startsWith("Sei entrato in: Bedroom"), "Messaggio inatteso: " + msg1);
        assertNotNull(character.getCurrentRoom());
        assertEquals("Bedroom", character.getCurrentRoom().getRoomName());

        String msg2 = character.pickCurrentRoom(kitchen);
        assertNotNull(msg2);
        assertTrue(msg2.startsWith("Sei entrato in: Kitchen"), "Messaggio inatteso: " + msg2);
        assertNotNull(character.getCurrentRoom());
        assertEquals("Kitchen", character.getCurrentRoom().getRoomName());
    }

    // TEST FOR QUESTS ---------------------------------------------------------------------

    // Test for adding a quest and checking active quest with NPC
    @Test
    void testAddActiveQuest() {
        Quest q = new Quest(
            "FetchStereo",
            "Prendi lo stereo",
            questGiver,
            5,
            20,
            new ArrayList<>()
        );

        character.addQuest(q);

        assertTrue(character.hasActiveQuestWithNPC(questGiver));
        assertTrue(character.getActiveQuestWithNPC(questGiver).isPresent());
        assertEquals("FetchStereo", character.getActiveQuestWithNPC(questGiver).get().getName());
    }

    // Test for recording used items for quests and checking them
    @Test
    void testItemsForQuest() {
        Quest q = new Quest("UsePC", "Usa il PC", questGiver, 0, 0, new ArrayList<>());
        character.addQuest(q);

        GameItem computer = new GameItem.Builder("Computer", "Bedroom", 20)
                .message("Usi il PC.")
                .build();

        assertFalse(character.hasUsedItemForQuest(computer, q));

        character.recordItemsUsedForQuests(computer);

        assertTrue(character.hasUsedItemForQuest(computer, q));

        GameItem pentola = new GameItem.Builder("Pentola", "Kitchen", 10).build();
        assertFalse(character.hasUsedItemForQuest(pentola, q));
    }

    // Test for CompletionCondition with GameItem
    @Test
    void testItemHasBeenUsed() {
        Quest q = new Quest("UsePC", "Usa il PC", questGiver, 0, 0, new ArrayList<>());
        character.addQuest(q);

        GameItem computer = new GameItem.Builder("Computer", "Bedroom", 20).build();

        CompletionCondition cond = new CompletionCondition(computer, q);

        // Before -> false
        assertFalse(cond.checkCompletion(character));

        // Register the use
        character.recordItemsUsedForQuests(computer);

        // After -> true
        assertTrue(cond.checkCompletion(character));
    }

    // TEST FOR ITEMS ---------------------------------------------------------------------
    
    // Testing that items can only get picked up once
    @Test
    void testPickUpOnce() {
        GameItem computer = new GameItem.Builder("Computer", "Bedroom", 20)
                .message("Usi il PC.")
                .build();

        // The room contains only one computer
        bedroom.addItemRoom(computer);
        assertTrue(bedroom.hasItemRoom(computer));

        // First pickup -> Ends up in the inventory
        character.pickUp(computer);
        assertEquals(1, character.getInventory().getUsedSpace());
        assertTrue(character.getInventory().hasItem("Computer"));

        // The item is removed from the room
        bedroom.removeItemRoom(computer);
        assertFalse(bedroom.hasItemRoom(computer));

        // Second pickup -> Should be impossible
        character.pickUp(computer);
        assertEquals(1, character.getInventory().getUsedSpace());
        assertTrue(character.getInventory().hasItem("Computer"));
    }

    // Test for picking up multiple items
    @Test
    void TestPickUpTwoDifferentItems() {
        GameItem phone = new GameItem.Builder("Telefono", "Bedroom", 5)
                .message("Controlli i messaggi.")
                .build();
        GameItem book = new GameItem.Builder("Libro", "Bedroom", 0).build();

        bedroom.addItemRoom(phone);
        bedroom.addItemRoom(book);
        assertTrue(bedroom.hasItemRoom(phone));
        assertTrue(bedroom.hasItemRoom(book));

        character.pickUp(phone);
        bedroom.removeItemRoom(phone);

        character.pickUp(book);
        bedroom.removeItemRoom(book);

        assertEquals(2, character.getInventory().getUsedSpace());
        assertTrue(character.getInventory().hasItem("Phone"));
        assertTrue(character.getInventory().hasItem("Book"));

        assertFalse(bedroom.hasItemRoom(phone));
        assertFalse(bedroom.hasItemRoom(book));
    }

    // Testing dropping an item which gets it removed from the inventory
    @Test
    void testDropItem() {
        GameItem mug = new GameItem.Builder("Tazza", "Bedroom", 0)
                .message("Sorbisci un caffè.")
                .build();

        bedroom.addItemRoom(mug);
        assertTrue(bedroom.hasItemRoom(mug));

        character.pickUp(mug);
        bedroom.removeItemRoom(mug);

        assertEquals(1, character.getInventory().getUsedSpace());

        // Drop -> The item is removed from the inventory
        character.drop(mug);
        assertEquals(0, character.getInventory().getUsedSpace());

        // Adding the item back into the room
        bedroom.addItemRoom(mug);
        assertTrue(bedroom.hasItemRoom(mug));
    }
}
