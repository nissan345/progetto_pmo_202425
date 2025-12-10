package worldTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import main.model.character.enums.*;
import main.model.character.MainCharacter;
import main.model.character.npc.Brother;
import main.model.character.npc.Dad;
import main.model.character.npc.Mum;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.GameItem;

/**
 * Test class for House and Room functionality
 * Contains unit tests for room management, NPC placement, and house operations
 */
public class HouseTest {
	
	private MainCharacter c; 
	private House h;  
	private Room bedroom; 
	private Room livingRoom; 
	private Room kitchen; 
	private Room bathroom; 
	private Room storageRoom; 
	private Room garden; 
	private Mum mum; 
	private Dad dad;
	private Brother brother; 

	/**
	 * Sets up test fixtures before each test method
	 * Initializes main character, house, rooms, and NPCs
	 */
	@Before 
	public void setUp() {
		c = new MainCharacter("Ari", Outfit.CASUAL, Hair.CURLY_LONG);
		h = new House(); 
	    
        bedroom     = ItemFactory.createBedroom();
        livingRoom  = ItemFactory.createLivingRoom();
        kitchen     = ItemFactory.createKitchen();
        bathroom    = ItemFactory.createBathroom();
        storageRoom = ItemFactory.createStorageRoom();
        garden      = ItemFactory.createGarden();
        
        h.addRoom(bedroom);
        h.addRoom(livingRoom);
        h.addRoom(kitchen);
        h.addRoom(bathroom);
        h.addRoom(storageRoom);
        h.addRoom(garden);
        
        mum = new Mum(livingRoom, h);
        dad = new Dad(garden, h);
        brother = new Brother(kitchen, h);

        livingRoom.setNpc(mum);
        garden.setNpc(dad);
        kitchen.setNpc(brother);
	}
	
	// TEST HOUSE METHODS
	
	/**
	 * Tests house creation and room management
	 * Verifies that house contains correct number of rooms and specific room names
	 */
	@Test 
	public void testHouseCreation() {
	    assertEquals(6, h.getRooms().size());
	    assertTrue(h.getRooms().containsKey("Camera Da Letto"));
	    assertTrue(h.getRooms().containsKey("Salotto"));
	    assertTrue(h.getRooms().containsKey("Cucina"));
	    assertTrue(h.getRooms().containsKey("Bagno"));
	    assertTrue(h.getRooms().containsKey("Sgabuzzino"));
	    assertTrue(h.getRooms().containsKey("Giardino"));
	}
	
	/**
	 * Tests room entry and exit functionality
	 * Verifies that entering a room sets it as current room and exit returns room map
	 */
	@Test
	public void testEnterExitRoom() {
	    assertThrows(UnsupportedOperationException.class, () -> {
	        h.getCurrentRoom();
	    });
	    
	    Optional<Room> enteredRoom = h.enterRoom("Camera Da Letto");
	    assertTrue(enteredRoom.isPresent());
	    assertEquals(bedroom, h.getCurrentRoom().get());
	    
	    Map<String, Room> rooms = h.exitRoom();
	    assertNotNull(rooms);
	}
	
	/**
	 * Tests behavior when attempting to enter non-existent room
	 * Verifies that non-existent room returns empty optional and doesn't set current room
	 */
	@Test
	public void testEnterNonExistentRoom() {
	    Optional<Room> result = h.enterRoom("Non-existent Room");
	    assertFalse(result.isPresent());
	    
	    assertThrows(UnsupportedOperationException.class, () -> {
	        h.getCurrentRoom();
	    });
	}
	
	/**
	 * Tests room retrieval by name functionality
	 * Verifies that existing rooms are found and non-existent rooms return null
	 */
	@Test
	public void testGetRoom() {
		Room foundRoom = h.getRoom("Camera Da Letto");
	    assertEquals(bedroom, foundRoom);
	        
        Room notFound = h.getRoom("Non-existent Room");
        assertNull(notFound);
	}
	    
	/**
	 * Tests exit room behavior when no current room is set
	 * Verifies that exiting without current room throws appropriate exception
	 */
	@Test
	public void testExitRoomWithoutCurrentRoom() {
		House emptyHouse = new House();
	    assertThrows(UnsupportedOperationException.class, () -> {
	    	emptyHouse.exitRoom();
	    });
	}
	
	// TEST ROOM METHODS
	
	/**
	 * Tests basic room creation and initialization
	 * Verifies room name, item list presence, and initial NPC absence
	 */
	@Test 
	public void testRoomCreation() {
		assertNotNull(bedroom);
	    assertEquals("Camera Da Letto", bedroom.getRoomName());
        assertNotNull(bedroom.getItemsInRoom());
        assertFalse(bedroom.getItemsInRoom().isEmpty());
        assertFalse(bedroom.getNpcInRoom().isPresent());
    }

	/**
	 * Tests NPC assignment to rooms and prevention of NPC duplication
	 * Verifies correct NPC placement and exception when attempting duplicate assignment
	 */
	@Test
	public void testRoomWithNPC() {
		assertTrue(livingRoom.hasNpc(mum));
	    assertTrue(garden.hasNpc(dad));
	    assertTrue(kitchen.hasNpc(brother));
		
	    Exception exception = assertThrows(IllegalStateException.class, () -> {
	        bedroom.setNpc(dad);
	    });
	    
	    assertFalse(bedroom.hasNpc(dad));
	}
	
	/**
	 * Tests that rooms without NPCs remain empty
	 * Verifies that bedroom, bathroom, and storage room have no NPCs assigned
	 */
	@Test
	public void testRoomsWithoutNPCStayEmpty() {
	    assertFalse(bedroom.getNpcInRoom().isPresent());
	    assertFalse(bathroom.getNpcInRoom().isPresent());
	    assertFalse(storageRoom.getNpcInRoom().isPresent());
	}
	
	/**
	 * Tests room entry requirements based on character level
	 * Verifies that character can enter some rooms but not others based on level requirements
	 */
	@Test
	public void testRoomRequirements() {
	    c.addXp(200);
	    assertEquals(2, c.getLvl());
	    
	    assertTrue(bedroom.canEnter(c));
	    assertTrue(kitchen.canEnter(c));
	    
	    assertFalse(storageRoom.canEnter(c));
	    assertFalse(garden.canEnter(c));
	    
	    List<String> reasons = garden.getEntryFailureReasons(c);
        assertNotNull(reasons);
        assertFalse(reasons.isEmpty());
	}

	/**
	 * Tests room item initialization
	 * Verifies that each room contains the expected number of game items
	 */
	@Test
	public void testRoomItems() {
		assertEquals(3, bedroom.getItemsInRoom().size());
		assertEquals(4, kitchen.getItemsInRoom().size());
		assertEquals(3, bathroom.getItemsInRoom().size());
	}

	/**
	 * Tests item management operations in rooms
	 * Verifies adding and removing items from room inventory
	 */
	@Test
	public void testAddRemoveItems() {
	    GameItem testItem = new GameItem.Builder("Test Item", "Camera Da Letto", 10).build();
	    
	    int initialSize = bedroom.getItemsInRoom().size();
	    bedroom.addItemRoom(testItem);
	    assertEquals(initialSize + 1, bedroom.getItemsInRoom().size());
	    assertTrue(bedroom.hasItemRoom(testItem));
	    
	    bedroom.removeItemRoom(testItem);
	    assertEquals(initialSize, bedroom.getItemsInRoom().size());
	}
	
	/**
	 * Tests item presence checking for non-existent items
	 * Verifies that hasItemRoom returns false for items not in the room
	 */
	@Test
    public void testHasItemRoomWhenNotPresent() {
        GameItem nonExistentItem = new GameItem.Builder("NonExistent", "Test", 10).build();
        assertFalse(bedroom.hasItemRoom(nonExistentItem));
    }
}