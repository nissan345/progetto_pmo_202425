package questTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import main.model.quest.Quest;
import main.model.world.gameItem.GameItem;

public class DadQuestsTest extends SetUpWorldTest {

    /**
     * Tests the full lifecycle of Dad's quests
     */
    @Test
    public void testQuests() {
        // Prepare character stats if necessary (Affinity/XP)
        c.addXp(500);

        // --- QUEST 1: Annaffia le piante (Water the plants) ---
        
        // Move to Garden to trigger the first quest (Dad is in the Garden)
        c.pickCurrentRoom(garden);
        Quest firstQuest = questSystem.onPlayerEnteredRoom(c, garden).get(0);

        assertEquals("Annaffia le piante", firstQuest.getName());
        assertTrue(questSystem.isOffered(firstQuest));
        
        // Verify that the second quest ("Festa a sorpresa") is NOT yet active/completed
        boolean hassecondQuest = questSystem.findCompletedQuestForNPC(c, dad).stream()
                                             .anyMatch(q -> q.getName().equals("Festa a sorpresa"));
        assertFalse(hassecondQuest);        


        // Find the Watering Can (Annaffiatoio) in the Garden
        GameItem wateringCan = garden.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Annaffiatoio"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item 'Annaffiatoio' not found in Garden"));

        // This quest requires ItemUsageCondition, so the player must use the item
        wateringCan.use(c);

        // Verify completion status
        assertTrue(firstQuest.checkCompletion(c));

        // Turn in the quest to Dad
        boolean turnIn1 = questSystem.tryTurnIn(c, dad);
        assertTrue(turnIn1);
        
        // Verify system state after turn-in
        assertTrue(questSystem.isTurnedIn(firstQuest));
        assertFalse(c.hasActiveQuestWithNPC(dad));


        // --- QUEST 2: Festa a sorpresa (Surprise Party) ---

        // Re-enter Garden to trigger the second quest (requires Quest 1 completed)
        Quest secondQuest = questSystem.onPlayerEnteredRoom(c, garden).get(0);

        assertTrue(questSystem.isOffered(secondQuest));

        // 1. Move to Storage Room (Sgabuzzino) and use Vacuum (Aspirapolvere)
        c.pickCurrentRoom(storageRoom);
        GameItem vacuum = storageRoom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Aspirapolvere"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item 'Aspirapolvere' not found"));
        vacuum.use(c);

        // 2. Move to Kitchen and use Stove (Fornelli)
        c.pickCurrentRoom(kitchen);
        GameItem stove = kitchen.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Fornelli"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item 'Fornelli' not found"));
        c.getStats().changeSatiety(-50);
        stove.use(c);

        // 3. Move to Living Room and use Stereo
        c.pickCurrentRoom(livingRoom);
        GameItem stereo = livingRoom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Stereo"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item 'Stereo' not found"));
        c.getStats().changeEnergy(-50);
        stereo.use(c);

        // Return to Dad in Garden
        c.pickCurrentRoom(garden);
        
        // Check completion before turning in
        assertTrue(secondQuest.checkCompletion(c));

        // Turn in Quest 2
        boolean turnIn2 = questSystem.tryTurnIn(c, dad);
        assertTrue(turnIn2);
        assertTrue(questSystem.isTurnedIn(secondQuest));
        assertFalse(c.hasActiveQuestWithNPC(dad));


        // --- QUEST 3: Riporta le chiavi (Return the keys) ---

        // Re-enter Garden to trigger the third quest (requires Quest 2 completed)
        Quest thirdQuest = questSystem.onPlayerEnteredRoom(c, garden).get(0);

        assertTrue(questSystem.isOffered(thirdQuest));

        // Find Keys in Kitchen and use them
        c.pickCurrentRoom(kitchen);
        GameItem keys = kitchen.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Chiavi macchina"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item 'Chiavi macchina' not found"));
        
        c.pickUpItemAction(keys);
        assertTrue(c.getInventory().hasItem("Chiavi macchina"));
        
        // Return to Dad in Garden
        c.pickCurrentRoom(garden);

        // Verify completion and turn in
        // Note: If Dad.java has a copy-paste error (checking for watering can instead of keys), this assertion will fail.
        assertTrue(thirdQuest.checkCompletion(c));
        
        boolean turnIn3 = questSystem.tryTurnIn(c, dad);
        assertTrue(turnIn3);
        assertTrue(questSystem.isTurnedIn(thirdQuest)); 
    }
}