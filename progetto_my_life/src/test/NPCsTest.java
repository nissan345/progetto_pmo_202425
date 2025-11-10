import main.model.world.Room;
import main.model.world.House;
import main.model.character.npc.Brother;
import main.model.character.npc.Dad;
import main.model.character.npc.Mum;
import main.model.quest.Quest;
import main.model.requirement.AlwaysTrueRequirement;

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

    // Test for the quest assigned dialogues of each NPC
    @Test
    void testQuestAssignedDialogue() {
        Quest dummyQuest = new Quest("Dummy", "Descrizione", mum, 10, 5, Collections.emptyList());

        String mumDialogue = mum.getQuestAssignedDialogue(dummyQuest);
        assertTrue(mumDialogue.contains("Descrizione"));
        assertTrue(mumDialogue.contains("Torna da me quando avrai finito!"));

        String dadDialogue = dad.getQuestAssignedDialogue(dummyQuest);
        assertTrue(dadDialogue.contains("Descrizione"));
        assertTrue(dadDialogue.contains("Torna da me quando avrai finito!"));

        String brotherDialogue = brother.getQuestAssignedDialogue(dummyQuest);
        assertTrue(brotherDialogue.contains("Descrizione"));
    }

    // Test for the quest in-progress dialogues of each NPC
    @Test
    void testQuestInProgressDialogueDefault() {
        Quest dummyQuest = new Quest("UnknownQuest", "Descrizione", mum, 10, 5, Collections.emptyList());

        assertEquals("Come sta andando con la quest? Torna da me quando hai finito!",
                mum.getQuestInProgressDialogue(dummyQuest));
        assertEquals("Come sta andando con la quest? Torna da me quando hai finito!",
                dad.getQuestInProgressDialogue(dummyQuest));
        assertEquals("Come sta andando con la quest? Torna da me quando hai finito!",
                brother.getQuestInProgressDialogue(dummyQuest));
    }

    // Test for the quest completion dialogues of each NPC
    @Test
    void testQuestCompletionDialogue() {
        Quest dummyQuest = new Quest("Dummy", "Descrizione", mum, 10, 5, Collections.emptyList());

        assertTrue(mum.getQuestCompletionDialogue(dummyQuest).contains("Hai fatto un ottimo lavoro"));
        assertTrue(dad.getQuestCompletionDialogue(dummyQuest).contains("Hai fatto un ottimo lavoro"));
        assertEquals("Grazie mille per avermi aiutato", brother.getQuestCompletionDialogue(dummyQuest));
    }
}

