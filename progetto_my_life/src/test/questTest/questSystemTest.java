package questTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import main.model.character.MainCharacter;
import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;
import main.model.character.npc.Brother;
import main.model.character.npc.Dad;
import main.model.character.npc.Mum;
import main.model.quest.Quest;
import main.model.quest.QuestSystem;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.GameItem;

public class questSystemTest {
	
	private QuestSystem questSystem; 
	private MainCharacter c; 
	private House h;  

	private Room livingRoom; 
	private Room kitchen; 
	private Room garden; 
	private Room storageRoom; 
 
	private Mum mum; 
	private Dad dad;
	private Brother brother; 

	/**
	 * Sets up test fixtures before each test method
	 * Initializes main character, house, rooms, NPCs and quest system
	 */
	@Before 
	public void setUp() {
		c = new MainCharacter("Ari", Outfit.CASUAL, Hair.CURLY_LONG);
		h = new House(); 
	    
        livingRoom  = ItemFactory.createLivingRoom();
        kitchen     = ItemFactory.createKitchen();
        garden      = ItemFactory.createGarden();
        storageRoom = ItemFactory.createStorageRoom(); 
        
        h.addRoom(livingRoom);
        h.addRoom(kitchen);
        h.addRoom(garden);
        h.addRoom(storageRoom);
        
        mum = new Mum(livingRoom, h);
        dad = new Dad(garden, h);
        brother = new Brother(kitchen, h);

        livingRoom.setNpc(mum);
        garden.setNpc(dad);
        kitchen.setNpc(brother);
        
        questSystem = new QuestSystem();
        questSystem.registerNPC(mum);
        questSystem.registerNPC(dad);
        questSystem.registerNPC(brother);
	}
	
	/**
     * Tests the full lifecycle of the Brother's quest:
     * Triggering -> Completion (using Item) -> Identification -> Turn-in -> Cleanup.
     */
    @Test
    public void testBrotherTrigger() {
        // 1. TRIGGER
        c.pickCurrentRoom(kitchen);
        List<Quest> activeQuests = questSystem.onPlayerEnteredRoom(c, kitchen);
        
        // Verify the quest is triggered
        assertFalse("Quest list should not be empty upon entering Kitchen", activeQuests.isEmpty());
        Quest kitchenQuest = activeQuests.get(0);
        assertEquals("Cibo per tutti", kitchenQuest.getName());
        
        // 2. PROGRESSION (Using the Item)
        // Retrieve stove and simulate usage (requires lowering satiety)
        GameItem stove = kitchen.getItemsInRoom().get(0);
        c.getStats().changeSatiety(-50);
        stove.use(c); 
        
        // Verify internal state changes
        assertTrue("Player should have recorded item usage", c.hasUsedItemForQuest(stove));
        assertTrue("Quest condition should be met", kitchenQuest.checkCompletion(c));
        
        // 3. SYSTEM RETRIEVAL
        // Verify the system finds the completed quest for the specific NPC
        Optional<Quest> foundQuest = questSystem.findCompletedQuestForNPC(c, brother);
        assertTrue("System should find the completed quest", foundQuest.isPresent());
        assertEquals(kitchenQuest, foundQuest.get());
        
        // 4. TURN-IN
        boolean turnInSuccess = questSystem.tryTurnIn(c, brother);
        assertTrue("Turn-in should succeed", turnInSuccess);
        
        // 5. CLEANUP & FINAL STATE
        // Verify the quest is archived and no longer active
        Optional<Quest> foundAfterTurnIn = questSystem.findCompletedQuestForNPC(c, brother);
        assertFalse("Quest should no longer be found as 'active-completed'", foundAfterTurnIn.isPresent());
        
        assertTrue("Quest should be in 'turnedIn' set", questSystem.isTurnedIn(kitchenQuest));
        assertFalse("Quest should no longer be in 'offered' set", questSystem.isOffered(kitchenQuest));
        assertFalse("Player should not have the quest active anymore", c.hasActiveQuestWithNPC(brother));
    }
    
    /**
     * Tests a complex multi-quest sequence with a specific NPC (Dad).
     * Verifies sequential triggering (Quest 2 only appears after Quest 1) and uniqueness.
     */
    @Test 
    public void testDadQuests() {
        // Setup: Level up to ensure access to all rooms
        c.addXp(500);

        // --- PHASE 1: FIRST QUEST (PLANTS) ---
        c.pickCurrentRoom(garden); 
        List<Quest> firstVisitQuest = questSystem.onPlayerEnteredRoom(c, garden);
        
        assertEquals("Should trigger exactly 1 quest on first visit", 1, firstVisitQuest.size());
        Quest plantQuest = firstVisitQuest.get(0);
        assertEquals("Annaffia le piante", plantQuest.getName());

        // Verify that the second quest ("Festa a sorpresa") is NOT yet active/completed
        boolean hasPartyQuest = questSystem.findCompletedQuestForNPC(c, dad).stream()
                                      .anyMatch(q -> q.getName().equals("Festa a sorpresa"));
        assertFalse("Second quest should not be active yet", hasPartyQuest);
        
        // Complete Quest 1
        GameItem annaffiatoio = garden.getItemsInRoom().stream()
                                       .filter(i -> i.getName().equals("Annaffiatoio"))
                                       .findFirst()
                                       .orElse(null);
        
        assertNotNull("Watering can item must exist in Garden", annaffiatoio);
        c.recordItemsUsedForQuests(annaffiatoio); 
        
        assertTrue("First quest should be complete", plantQuest.checkCompletion(c));
        assertTrue("Turn-in should succeed", questSystem.tryTurnIn(c, dad));
        
        // Verify Player Memory (Crucial for the trigger of the second quest)
        assertTrue("Player history must contain the completed quest", c.hasCompletedQuest("Annaffia le piante"));
        
        // --- PHASE 2: SECOND QUEST (PARTY) ---
        // Re-enter the room to trigger the next logic
        List<Quest> secondVisitQuest = questSystem.onPlayerEnteredRoom(c, garden); 
        
        // Verify the old quest is NOT re-offered
        boolean hasPlantsQuest = secondVisitQuest.stream()
                .anyMatch(q -> q.getName().equals("Annaffia le piante"));
        assertFalse("Completed quest should not be re-offered", hasPlantsQuest);
        
        // Verify the new quest is offered
        assertEquals("Should trigger exactly 1 new quest", 1, secondVisitQuest.size());
        Quest partyQuest = secondVisitQuest.get(0);
        assertEquals("Festa a sorpresa", partyQuest.getName());
        
        // Complete Quest 2 (Multi-item requirements)
        // Retrieve items using streams
        GameItem aspirapolvere = storageRoom.getItemsInRoom().stream()
                   .filter(i -> i.getName().equals("Aspirapolvere")).findFirst().orElse(null);
        GameItem stereo = livingRoom.getItemsInRoom().stream()
                   .filter(i -> i.getName().equals("Stereo")).findFirst().orElse(null);
        GameItem fornelli = kitchen.getItemsInRoom().stream()
                   .filter(i -> i.getName().equals("Fornelli")).findFirst().orElse(null);
        
        // Assert items exist before using them (Fail Fast strategy)
        assertNotNull("Vacuum cleaner must exist", aspirapolvere);
        assertNotNull("Stereo must exist", stereo);
        assertNotNull("Stove must exist", fornelli);

        c.recordItemsUsedForQuests(aspirapolvere);
        c.recordItemsUsedForQuests(fornelli);
        c.recordItemsUsedForQuests(stereo);
        
        // Final verification
        assertTrue("Second quest should be complete", partyQuest.checkCompletion(c));
        assertTrue("Turn-in for second quest should succeed", questSystem.tryTurnIn(c, dad));
    }
    
    /**
     * Tests that a completed quest can only be turned in to the correct NPC assignee.
     */
    @Test
    public void testTurnInToWrongNPC() {
        c.addXp(200); // Ensure level requirement for Living Room
        c.pickCurrentRoom(livingRoom);
        
        List<Quest> quests = questSystem.onPlayerEnteredRoom(c, livingRoom);
        assertFalse("Quests should be offered in Living Room", quests.isEmpty());

        // Find Mum's quest
        Quest mumQuest = quests.stream()
                               .filter(q -> q.getAssignerNPC().equals(mum))
                               .findFirst()
                               .orElseThrow(() -> new AssertionError("Mum's quest not found"));

        // Complete the quest
        GameItem album = livingRoom.getItemsInRoom().stream()
                                    .filter(i -> i.getName().equals("Album"))
                                    .findFirst()
                                    .orElse(null);
        
        assertNotNull("Album item must exist", album);
        c.recordItemsUsedForQuests(album); 

        assertTrue("Quest should be ready for completion", mumQuest.checkCompletion(c));
        
        // Attempt turn-in to WRONG NPC (Dad)
        boolean wrongNpcTurnIn = questSystem.tryTurnIn(c, dad);
        assertFalse("Should not be able to turn in Mum's quest to Dad", wrongNpcTurnIn);
        
        // Attempt turn-in to CORRECT NPC (Mum)
        boolean rightNpcTurnIn = questSystem.tryTurnIn(c, mum);
        assertTrue("Should successfully turn in to Mum", rightNpcTurnIn);
    }
    
    /**
     * Tests that a player cannot turn in a quest that hasn't been completed yet.
     */
    @Test
    public void testCannotTurnInIncompleteQuest() {
        c.pickCurrentRoom(kitchen);
        questSystem.onPlayerEnteredRoom(c, kitchen);
        
        assertTrue("Player should have an active quest with Brother", c.hasActiveQuestWithNPC(brother));
        
        // Attempt to turn in without using the item
        boolean turnInSuccess = questSystem.tryTurnIn(c, brother);
        
        assertFalse("Turn-in should fail for incomplete quest", turnInSuccess);
        assertTrue("Quest should remain active", c.hasActiveQuestWithNPC(brother));
        
        // Verify system state
        Optional<Quest> completed = questSystem.findCompletedQuestForNPC(c, brother);
        assertFalse("System should not find any completed quest for Brother", completed.isPresent());
    }
    
    /**
     * Tests that multiple quests from different NPCs can be active simultaneously.
     */
    @Test
    public void testMultipleActiveQuestsFromDifferentNPCs() {
        c.addXp(500); // Ensure access to all rooms

        // 1. Activate Dad's Quest (Garden)
        c.pickCurrentRoom(garden);
        List<Quest> gardenOffers = questSystem.onPlayerEnteredRoom(c, garden);
        
        assertFalse("Garden offers should not be empty", gardenOffers.isEmpty());
        assertTrue("Player should have active quest with Dad", c.hasActiveQuestWithNPC(dad));

        // 2. Activate Mum's Quest (Living Room)
        c.pickCurrentRoom(livingRoom);
        List<Quest> livingRoomOffers = questSystem.onPlayerEnteredRoom(c, livingRoom);
        
        assertFalse("Living Room offers should not be empty", livingRoomOffers.isEmpty());
        assertTrue("Player should have active quest with Mum", c.hasActiveQuestWithNPC(mum));

        // 3. Verify Coexistence
        boolean hasDadQuest = c.hasActiveQuestWithNPC(dad);
        boolean hasMumQuest = c.hasActiveQuestWithNPC(mum); // FIXED: Was checking 'dad' twice in original code
        
        assertTrue("Dad's quest should remain active", hasDadQuest);
        assertTrue("Mum's quest should be active simultaneously", hasMumQuest);
    }
}
