package main.model.character.npc;

import main.model.world.House;
import main.model.world.Room;
import main.model.world.gameItem.GameItem;
import main.model.character.npc.Brother;
import main.model.quest.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class NPCsTest {

    private House house;
    private Room kitchen;
    private Room livingRoom;
    private Room garden;

    @BeforeEach
    void setup() {
        // Creiamo stanze con oggetti richiesti per le quest
        kitchen = new Room("Cucina",
                Collections.singletonList(new GameItem.Builder("Fornelli", "Cucina", 5).build()),
                character -> true);
        livingRoom = new Room("Salotto",
                Collections.singletonList(new GameItem.Builder("Album", "Salotto", 1).build()),
                character -> true);
        garden = new Room("Giardino",
                Collections.singletonList(new GameItem.Builder("Annaffiatoio", "Giardino", 3).build()),
                character -> true);

        // Creiamo House fittizia
        house = new House();
        house.addRoom(kitchen);
        house.addRoom(livingRoom);
        house.addRoom(garden);
    }

    @Test
    void testDadInitialization() {
        Dad dad = new Dad(garden, house);
        assertEquals("Ciao tesoro! Come sta andando la tua avventura?", dad.getInitialDialogue());

        Quest quest = dad.getQuests().stream()
                .filter(q -> q.getName().equals("Annaffia le piante"))
                .findFirst()
                .orElse(null);
        assertNotNull(quest);
        assertEquals("Dovresti innaffiare le piante", quest.getDescription());
    }

    @Test
    void testMumInitialization() {
        Mum mum = new Mum(livingRoom, house);
        assertEquals("Ciao tesoro! Come sta andando la tua giornata?", mum.getInitialDialogue());

        Quest quest = mum.getQuests().stream()
                .filter(q -> q.getName().equals("L'album perduto"))
                .findFirst()
                .orElse(null);
        assertNotNull(quest);
        assertEquals("Dovresti riportarmi il vecchio album di famiglia che ho perduto da qualche parte in casa e riportamelo",
                quest.getDescription());
    }

    @Test
    void testBrotherInitialization() {
        Brother brother = new Brother(kitchen, house);
        assertEquals("Non mi dare fastidio", brother.getInitialDialogue());

        Quest quest = brother.getQuests().stream()
                .filter(q -> q.getName().equals("Cibo per tutti"))
                .findFirst()
                .orElse(null);
        assertNotNull(quest);
        assertEquals("Dei nostri amici vengono a casa, potresti prepare qualcosa per tutti mentre io pulisco la mia camera",
                quest.getDescription());
    }
}
