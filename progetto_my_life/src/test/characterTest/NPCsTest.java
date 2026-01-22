package characterTest;

import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.House;
import main.model.character.MainCharacter;
import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;
import main.model.character.npc.*;
import main.model.quest.Quest;
import main.model.quest.QuestSystem;
import main.model.requirement.requirementImpl.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class NPCsTest {

    private House house;
    private Room livingRoom;
    private Room kitchen;
    private Room garden;
    private Room bathroom; 
    private Room bedroom; 
    
    private MainCharacter c; 

    private Mum mum;
    private Dad dad;
    private Brother brother;
    
    private QuestSystem questSystem; 

    @BeforeEach
    void setup() {
        house = new House();
        
        bedroom    = ItemFactory.createBedroom();
        livingRoom = ItemFactory.createLivingRoom();
        kitchen    = ItemFactory.createKitchen();
        garden     = ItemFactory.createGarden();
        bathroom   = ItemFactory.createBathroom();
        
        house.addRoom(livingRoom);
        house.addRoom(kitchen);
        house.addRoom(garden);
        house.addRoom(bathroom);
        house.addRoom(bedroom);
        
        c = new MainCharacter("player", Outfit.CASUAL, Hair.CURLY_LONG);

        mum = new Mum(livingRoom, house);
        dad = new Dad(garden, house);
        brother = new Brother(kitchen, house);
        
        questSystem = new QuestSystem();
        questSystem.registerNPC(dad);
    }

    // TESTS ------------------------------------------------------------------------

    // Test for the initial dialogues of each NPC
    @Test
    void testInitialDialogue() {
    	// Low Affinity (< 15)
        assertTrue(dad.getAffinity() < 15);
        assertEquals("Mmh... tutto bene? Cerca di non combinare guai oggi.", dad.getInitialDialogue());
        
        dad.increaseAffinity(20); // Now 20
        assertEquals("Ciao tesoro! Come sta andando la tua avventura?", dad.getInitialDialogue());
    }

    // Test for the quest assigned dialogues: Ownership Check
    @Test
    void testQuestAssignedDialogue() {
        // Create a quest assigned by Mum
        Quest mumQuest  = new Quest.Builder("UnknownQuest", "Descrizione", mum).build();

        // 1. Mum must recognize her quest
        assertTrue(dad.getAffinity() < 15);
        String mumDialogue = mum.getQuestAssignedDialogue(mumQuest);
        assertTrue(mumDialogue.contains("Grazie tesoro! " + mumQuest.getDescription() + 
                "\nFai con calma, non ti stancare troppo!"));

        // 2. Dad must NOT recognize Mum's quest
        String dadDialogue = dad.getQuestAssignedDialogue(mumQuest);
        // Verify that it returns the specific refusal string defined in Dad.java
        assertEquals("Non so nulla di questa faccenda.", dadDialogue);

        // 3. Brother must NOT recognize Mum's quest
        String brotherDialogue = brother.getQuestAssignedDialogue(mumQuest);
        assertEquals("Non so nulla di questa faccenda.", brotherDialogue);
    }

    /**
     * Test for the quest in-progress dialogues: Default logic vs Specific Switch
     */
    @Test
    void testQuestInProgressDialogue() {
        // Case A: Generic DAD quest (enters the 'default' switch case)
        Quest dadGenericQuest  = new Quest.Builder("UnknownQuest", "Descrizione", dad)
                .build();

        // Dad responds with the default
        assertEquals("Come sta andando con la quest? Torna da me quando hai finito!",
                dad.getQuestInProgressDialogue(dadGenericQuest));
        
        // Mum knows nothing about it
        assertEquals("Non so nulla di questa faccenda.", 
                mum.getQuestInProgressDialogue(dadGenericQuest));

        // Case B: Specific DAD quest ("Annaffia le piante")
        c.addXp(500);
        c.pickCurrentRoom(garden);
        Quest dadSpecificQuest = questSystem.onPlayerEnteredRoom(c, garden).get(0);
        
        // Dad responds with the specific phrase defined in the case "Annaffia le piante"
        String specificDialogue = dad.getQuestInProgressDialogue(dadSpecificQuest);
        assertTrue(specificDialogue.contains("Hai già annaffiato le piante? Ricorda che l'annaffiatoio si trova in giardino"));
    }

    // Test for the quest completion dialogues
    @Test
    void testQuestCompletionDialogue() {
    	// Create a quest assigned by Brother
        Quest brotherQuest  = new Quest.Builder("UnknownQuest", "Descrizione", brother).build();
        
    	assertTrue(brother.getAffinity() < 15); 
    	assertEquals("Era ora. Grazie.", brother.getQuestCompletionDialogue(brotherQuest));
    	
    	brother.increaseAffinity(50);
    	assertEquals("Grazie mille per avermi aiutato, mi hai salvato.", brother.getQuestCompletionDialogue(brotherQuest)); 
    	

        String dadDialogue = dad.getQuestCompletionDialogue(brotherQuest);
        assertEquals("Non so nulla di questa faccenda.", dadDialogue);

        String mumDialogue = mum.getQuestCompletionDialogue(brotherQuest);
        assertEquals("Non so nulla di questa faccenda.", mumDialogue);
    }
}