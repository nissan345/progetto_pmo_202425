package questTest;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import main.model.quest.Quest;
import main.model.world.gameItem.GameItem;

public class BrotherQuestsTest extends SetUpWorldTest {
	
	/**
     * Tests the full lifecycle of the Brother's quests.
     * Includes interaction with Mum to obtain a key item.
     */
    @Test
    public void testQuests() {
    	c.addXp(300);
    	
        // --- QUEST 1: Cibo per tutti ---
        // Trigger: Player enters the kitchen
        c.pickCurrentRoom(kitchen);
        List<Quest> firstQuest = questSystem.onPlayerEnteredRoom(c, kitchen);
        
        // Verify the quest is triggered
        assertFalse("Quest list should not be empty", firstQuest.isEmpty());
        Quest kitchenQuest = firstQuest.get(0);
        assertEquals("Cibo per tutti", kitchenQuest.getName());
        
        // Retrieve stove and simulate usage (requires lowering satiety)
        GameItem stove = kitchen.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Fornelli"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Stove not found"));
        
        c.getStats().changeSatiety(-50); // Lower satiety to allow eating/cooking
        stove.use(c); 
        
        // Verify internal state changes
        assertTrue("Character should have marked the item as used", c.hasUsedItemForQuest(stove));
        assertTrue("Quest conditions should be met", kitchenQuest.checkCompletion(c));
        
        // Verify the system finds the completed quest for the specific NPC
        Optional<Quest> foundQuest = questSystem.findCompletedQuestForNPC(c, brother);
        assertTrue("System should find the completed quest for Brother", foundQuest.isPresent());
        assertEquals(kitchenQuest, foundQuest.get());
        
        // Turn-in
        assertTrue("Turn-in should succeed", questSystem.tryTurnIn(c, brother));
        
        // Verify the quest is archived and no longer active
        Optional<Quest> foundAfterTurnIn = questSystem.findCompletedQuestForNPC(c, brother);
        assertFalse("Quest should be archived after turn-in", foundAfterTurnIn.isPresent());
        
        assertTrue(questSystem.isTurnedIn(kitchenQuest));
        assertFalse(questSystem.isOffered(kitchenQuest));
        assertFalse(c.hasActiveQuestWithNPC(brother));
        
        
        // --- QUEST 2: Il Bucato (Assumed Name) ---
        
        // Re-enter kitchen to trigger the second quest (requires Quest 1 completed)
        List<Quest> secondQuest = questSystem.onPlayerEnteredRoom(c, kitchen);
        assertFalse("Second quest should be triggered", secondQuest.isEmpty());
        
        // Go to the bathroom and use the washing machine
        c.pickCurrentRoom(bathroom);
        GameItem washingMachine = bathroom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Lavatrice"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Washing machine not found"));
        
        washingMachine.use(c);
        
        // Return to brother and turn in
        c.pickCurrentRoom(kitchen);
        boolean turnIn2 = questSystem.tryTurnIn(c, brother); // Complete Quest 2
        
        assertTrue("Second quest turn-in should succeed", turnIn2);
        assertFalse("Brother should have no active quests now", c.hasActiveQuestWithNPC(brother));


        // --- QUEST 3: Videogioco retro (Requires Item from Mum) ---
        // Mum gives the gift only if affinity is >= 70. Brother needs this item.
        mum.increaseAffinity(80); 
        
        // Trigger the gift dialogue with Mum (assuming she is in Living Room or accessible)
        // Note: In a real game loop, we might need to be in Mum's room, but for unit test method calls it's fine.
        String dialogue = mum.checkGiftInteraction(c);
        assertFalse("Gift dialogue should not be empty", dialogue.isEmpty());
        
        // Verify item reception (Case sensitive check: "Videogioco retro" as defined in Mum.java)
        assertTrue("Player should have the retro game", c.getInventory().hasItem("Videogioco retro"));

        // Trigger Brother's third quest (he asks for the game)
        List<Quest> thirdQuests = questSystem.onPlayerEnteredRoom(c, kitchen);
        
        // Find the specific quest
        Quest retroQuest = thirdQuests.stream()
                .filter(q -> q.getName().equals("Videogioco retro"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("The quest 'Videogioco retro' should be active"));
        
        assertTrue(questSystem.isOffered(retroQuest));

        // Since we already have the item in inventory (from Mum), the quest should be completable immediately.
        // ItemDeliveryCondition checks the inventory automatically.
        assertTrue("Quest should be complete immediately if item is in inventory", retroQuest.checkCompletion(c));
        
        // Turn in to Brother
        boolean turnInSuccess = questSystem.tryTurnIn(c, brother);
        
        assertTrue("Third quest turn-in should succeed", turnInSuccess);
        assertTrue(questSystem.isTurnedIn(retroQuest));
        
        // Verify the item was consumed (removed from inventory) upon quest completion
        // IMPORTANT: Must match the exact string "Videogioco retro" from Mum.java
        assertFalse("Item should be removed from inventory", c.getInventory().hasItem("Videogioco retro")); 
    }
}