package questTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import main.model.quest.Quest;
import main.model.world.gameItem.GameItem;

public class MumQuestsTest extends SetUpWorldTest {

    /**
     * Tests the full lifecycle of Mum's quests:
     */
    @Test
    public void testQuests() {
        // --- QUEST 1: L'album perduto ---
        // Move to Living Room to trigger the first quest
    	c.addXp(100);
        c.pickCurrentRoom(livingRoom);
        List<Quest> firstQuest = questSystem.onPlayerEnteredRoom(c, livingRoom);

        Quest albumQuest = firstQuest.stream()
                .filter(q -> q.getName().equals("L'album perduto"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Quest 'L'album perduto' not found"));

        assertEquals("L'album perduto", albumQuest.getName());
        assertTrue(questSystem.isOffered(albumQuest));

        // Find the Album in the Living Room
        GameItem album = livingRoom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Album"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item 'Album' not found in Living Room"));

        // This quest requires ItemDeliveryCondition, so the player must pick it up
        c.pickUpItemAction(album);

        // Verify completion status
        assertTrue(c.getInventory().hasItem("Album"));
        assertTrue(albumQuest.checkCompletion(c));

        // Turn in the quest to Mum
        boolean turnIn1 = questSystem.tryTurnIn(c, mum);
        assertTrue(turnIn1);
        
        // Verify system state after turn-in
        assertTrue(questSystem.isTurnedIn(albumQuest));
        assertFalse(c.hasActiveQuestWithNPC(mum));
        assertFalse(c.getInventory().hasItem("Album"));


        // --- QUEST 2: Visita ospiti ---

        // Re-enter Living Room to trigger the second quest (requires Quest 1 completed)
        List<Quest> secondQuest = questSystem.onPlayerEnteredRoom(c, livingRoom);
        
        Quest guestQuest = secondQuest.stream()
                .filter(q -> q.getName().equals("Visita ospiti"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Quest 'Visita ospiti' not found"));

        assertTrue(questSystem.isOffered(guestQuest));

        // Move to Bathroom and use the Shower
        c.pickCurrentRoom(bathroom);
        GameItem shower = bathroom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Doccia"))
                .findFirst()
                .orElseThrow();
        c.getStats().changeHygiene(-50);
        shower.use(c);

        // Move to Bedroom and use the Wardrobe
        c.pickCurrentRoom(bedroom);
        GameItem wardrobe = bedroom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Armadio"))
                .findFirst()
                .orElseThrow();
        wardrobe.use(c);

        // Return to Mum in Living Room
        c.pickCurrentRoom(livingRoom);
        
        // Check completion before turning in
        assertTrue(guestQuest.checkCompletion(c));

        // Turn in Quest 2
        boolean turnIn2 = questSystem.tryTurnIn(c, mum);
        assertTrue(turnIn2);
        assertTrue(questSystem.isTurnedIn(guestQuest));
        assertFalse(c.hasActiveQuestWithNPC(mum));


        // --- QUEST 3: La Citazione Mancante ---

        // Re-enter Living Room to trigger the third quest (requires Quest 2 completed)
        List<Quest> thirdQuest = questSystem.onPlayerEnteredRoom(c, livingRoom);

        Quest bookQuest = thirdQuest.stream()
                .filter(q -> q.getName().equals("La Citazione Mancante"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Quest 'La Citazione Mancante' not found"));

        // Find Bookshelf in Living Room and use it
        GameItem bookshelf = livingRoom.getItemsInRoom().stream()
                .filter(i -> i.getName().equals("Libreria"))
                .findFirst()
                .orElseThrow();
        bookshelf.use(c);

        // Verify completion and turn in
        assertTrue(bookQuest.checkCompletion(c));
        
        boolean turnIn3 = questSystem.tryTurnIn(c, mum);
        assertTrue(turnIn3);
        assertTrue(questSystem.isTurnedIn(bookQuest));


        // --- GIFT INTERACTION ---

        // Manually increase affinity to trigger the gift condition (Threshold is 70)
        mum.increaseAffinity(100);
        
        // Trigger the interaction method
        String giftDialogue = mum.checkGiftInteraction(c);

        // Verify the gift was received
        assertFalse(giftDialogue.isEmpty());
        assertTrue(c.getInventory().hasItem("Videogioco retro"));
    }
}