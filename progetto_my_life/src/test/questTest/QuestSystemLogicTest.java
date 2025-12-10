package questTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import main.model.quest.Quest;
import main.model.world.gameItem.GameItem;

public class QuestSystemLogicTest extends SetUpWorldTest{
    
    /**
     * Tests that a completed quest can only be turned in to the correct NPC assigner.
     */
	@Test
	public void testTurnInToWrongNPC() {
        c.addXp(200); 
        c.pickCurrentRoom(livingRoom);
        
        List<Quest> quests = questSystem.onPlayerEnteredRoom(c, livingRoom);
        assertFalse(quests.isEmpty());

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
        
        assertNotNull(album);
        c.pickUpItemAction(album);

        assertTrue(mumQuest.checkCompletion(c));
        
        // Attempt turn-in to WRONG NPC (Dad)
        boolean wrongNpcTurnIn = questSystem.tryTurnIn(c, dad);
        assertFalse(wrongNpcTurnIn);
        
        // Attempt turn-in to CORRECT NPC (Mum)
        boolean rightNpcTurnIn = questSystem.tryTurnIn(c, mum);
        assertTrue(rightNpcTurnIn);
    }
    
    /**
     * Tests that a player cannot turn in a quest that hasn't been completed yet.
     */
	@Test
	public void testCannotTurnInIncompleteQuest() {
        c.pickCurrentRoom(kitchen);
        questSystem.onPlayerEnteredRoom(c, kitchen);
        
        assertTrue(c.hasActiveQuestWithNPC(brother));
        
        // Attempt to turn in without using the item
        boolean turnInSuccess = questSystem.tryTurnIn(c, brother);
        
        assertFalse(turnInSuccess);
        assertTrue(c.hasActiveQuestWithNPC(brother));
        
        // Verify system state
        Optional<Quest> completed = questSystem.findCompletedQuestForNPC(c, brother);
        assertFalse(completed.isPresent());
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
        
        assertFalse(gardenOffers.isEmpty());
        assertTrue(c.hasActiveQuestWithNPC(dad));

        // 2. Activate Mum's Quest (Living Room)
        c.pickCurrentRoom(livingRoom);
        List<Quest> livingRoomOffers = questSystem.onPlayerEnteredRoom(c, livingRoom);
        
        assertFalse(livingRoomOffers.isEmpty());
        assertTrue(c.hasActiveQuestWithNPC(mum));

        // 3. Verify Coexistence
        boolean hasDadQuest = c.hasActiveQuestWithNPC(dad);
        boolean hasMumQuest = c.hasActiveQuestWithNPC(mum); 
        
        assertTrue(hasDadQuest);
        assertTrue(hasMumQuest);
    }
    
    /**
     * Tests that the items are removed when they are delivered
     */
	@Test
	public void testDeliveryRemovesItemFromInventory() {
        c.addXp(200); 
        c.pickCurrentRoom(livingRoom);
        questSystem.onPlayerEnteredRoom(c, livingRoom);

        GameItem album = livingRoom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Album")).findFirst().orElseThrow();
        
        c.pickUpItemAction(album);
        assertTrue(c.getInventory().hasItem("Album"));

        assertTrue(questSystem.tryTurnIn(c, mum));

        assertFalse( c.getInventory().hasItem("Album"));
    }
    
    /**
     * Tests that rewards are given to the character
     */
    @Test
    public void testQuestRewardsApplication() {
        c.pickCurrentRoom(kitchen);
        Quest quest = questSystem.onPlayerEnteredRoom(c, kitchen).get(0); 
        
        int initialXp = c.getXp();
        int initialAffinity = brother.getAffinity();
        int expectedXpReward = 50; 
        int expectedAffinityReward = 20; 

        GameItem stove = kitchen.getItemsInRoom().get(0);
        c.getStats().changeSatiety(-50);
        stove.use(c);
        assertTrue(questSystem.tryTurnIn(c, brother));

        assertEquals(5, c.getXp());
        assertEquals(2, c.getLvl());
        assertEquals(initialAffinity + expectedAffinityReward, brother.getAffinity());
    }
    
    /**
     * Test Scenario: Verifies that questSystem.reset() correctly clears all data.
     */
    @Test
    public void testQuestSystemReset() {
        // Start a quest
        c.pickCurrentRoom(kitchen);
        Quest quest = questSystem.onPlayerEnteredRoom(c, kitchen).get(0);
        
        assertTrue(questSystem.isOffered(quest));
        
        // Reset the system
        questSystem.reset(); 
        
        // Verify state is cleared
        assertFalse(questSystem.isOffered(quest));
        assertFalse(questSystem.isTurnedIn(quest));
    }
}