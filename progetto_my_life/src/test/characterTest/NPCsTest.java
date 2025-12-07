package characterTest;

import main.model.world.Room;
import main.model.world.House;
import main.model.character.npc.*;
import main.model.quest.Quest;
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

    private Mum mum;
    private Dad dad;
    private Brother brother;

    @BeforeEach
    void setup() {
        house = new House();
        // Note: Here we are not adding items to the rooms, so initializeQuests() 
        // will not create real quests. We will use dummy quests for dialogue testing.
        livingRoom = new Room("Salotto", Collections.emptyList(), new AlwaysTrueRequirement());
        kitchen = new Room("Cucina", Collections.emptyList(), new AlwaysTrueRequirement());
        garden = new Room("Giardino", Collections.emptyList(), new AlwaysTrueRequirement());
        
        house.addRoom(livingRoom);
        house.addRoom(kitchen);
        house.addRoom(garden);

        mum = new Mum(livingRoom, house);
        dad = new Dad(garden, house);
        brother = new Brother(kitchen, house);
    }

    // TESTS ------------------------------------------------------------------------

    // Test for the initial dialogues of each NPC
    @Test
    void testInitialDialogue() {
        assertEquals("Ciao tesoro! Come sta andando la tua giornata?", mum.getInitialDialogue());
        assertEquals("Ciao tesoro! Come sta andando la tua avventura?", dad.getInitialDialogue());
        assertEquals("Non mi dare fastidio", brother.getInitialDialogue());
    }

    // Test for the quest assigned dialogues: Ownership Check
    @Test
    void testQuestAssignedDialogue() {
        // Create a quest assigned by MUM
        Quest mumQuest  = new Quest.Builder("UnknownQuest", "Descrizione", mum)
                .affinityPoints(5)
                .xpReward(20)
                .build();

        // 1. Mum must recognize HER quest
        String mumDialogue = mum.getQuestAssignedDialogue(mumQuest);
        assertTrue(mumDialogue.contains("Ottimo che tu voglia aiutare! " + mumQuest.getDescription() + 
                "\nSo che posso contare su di te. Torna da me quando avrai finito!"));

        // 2. Dad must NOT recognize Mum's quest
        String dadDialogue = dad.getQuestAssignedDialogue(mumQuest);
        // Verify that it returns the specific refusal string defined in Dad.java
        assertEquals("Non so nulla di questa faccenda.", dadDialogue);

        // 3. Brother must NOT recognize Mum's quest
        String brotherDialogue = brother.getQuestAssignedDialogue(mumQuest);
        assertEquals("Non so nulla di questa faccenda.", brotherDialogue);
    }

    // Test for the quest in-progress dialogues: Default logic vs Specific Switch
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

        // Case B: Specific DAD quest ("Water the plants")
        Quest dadSpecificQuest = new Quest.Builder("Annaffia le piante", "Desc", dad).build();
        
        // Dad responds with the specific phrase defined in the case "Annaffia le piante"
        String specificDialogue = dad.getQuestInProgressDialogue(dadSpecificQuest);
        assertTrue(specificDialogue.contains("Hai già annaffiato le piante? Ricorda che l'annaffiatoio si trova in giardino"));
    }

    // Test for the quest completion dialogues
    @Test
    void testQuestCompletionDialogue() {
        // Quest assigned by MUM
        Quest mumQuest  = new Quest.Builder("UnknownQuest", "Descrizione", mum)
                .affinityPoints(5)
                .xpReward(20)
                .build();

        // Mum congratulates
        assertTrue(mum.getQuestCompletionDialogue(mumQuest).contains("Hai fatto un ottimo lavoro"));
        
        // Dad refuses (doesn't own the quest)
        assertEquals("Non so nulla di questa faccenda.", dad.getQuestCompletionDialogue(mumQuest));
        
        // Brother refuses (doesn't own the quest)
        assertEquals("Non so nulla di questa faccenda.", brother.getQuestCompletionDialogue(mumQuest));
    }
}