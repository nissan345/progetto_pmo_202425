
import static org.junit.Assert.*;

import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import main.model.character.Hair;
import main.model.character.MainCharacter;
import main.model.character.Outfit;
import main.model.character.npc.Brother;
import main.model.character.npc.Dad;
import main.model.character.npc.Mum;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;
import main.model.world.gameItem.GameItem;

public class RoomTest {
	
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
	
	
	@Test 
	public void testHouseCreation() {
		
	    assertEquals("House should have 6 rooms", 6, h.getRooms().size());
	    
	    assertTrue("House should contain bedroom", h.getRooms().containsKey("Camera Da Letto"));
	    assertTrue("House should contain living room", h.getRooms().containsKey("Salotto"));
	    
		assertNotNull("Room should be created", bedroom);
        assertEquals("Room name should match", "Camera Da Letto", bedroom.getRoomName());
        assertNotNull("Items list should not be null", bedroom.getItemsInRoom());
        assertFalse("Items list should not be empty initially", bedroom.getItemsInRoom().isEmpty());
        assertFalse("NPC should not be present", bedroom.getNpcInRoom().isPresent());
		
	}
	
	@Test
	public void testEnterExitRoom() {
	    Optional<Room> enteredRoom = h.enterRoom("Camera Da Letto");
	    assertTrue("Should successfully enter room", enteredRoom.isPresent());
	    assertEquals("Current room should be bedroom", bedroom, h.getCurrentRoom().get());
	    
	    Map<String, Room> rooms = h.exitRoom();
	    assertNotNull("Should return rooms map", rooms);
	}
	

	@Test
	public void testRoomWithNPC() {
		// check the setting of NPC
	}
	
	@Test
	public void testRoomsWithoutNPCStayEmpty() {

		// bedroom, bathroom and storage room never have a NPC inside 
	    assertFalse("Bedroom should not have NPC", bedroom.getNpcInRoom().isPresent());
	    assertFalse("Bathroom should not have NPC", bathroom.getNpcInRoom().isPresent());
	    assertFalse("Storage room should not have NPC", storageRoom.getNpcInRoom().isPresent());
	    

	    assertFalse("Bedroom should not have any NPC", bedroom.hasNpc(mum));
	    assertFalse("Storage room should not have any NPC", storageRoom.hasNpc(dad));
	}
	
	@Test
	public void testRoomRequirements() {
	    c.addXp(200);
	    // check that character level is 2 
	    assertEquals("Character level is 2", 2, c.getLvl());
	    
	    assertTrue("Character should be able to enter bedroom", bedroom.canEnter(c));
	    assertTrue("Character should be able to enter kitchen", kitchen.canEnter(c));
	    
	    // Character cannot enter storage room and garden 
	    assertFalse("Character should not be able to enter storage room", storageRoom.canEnter(c));
	    assertFalse("Character should not be able to enter garden", garden.canEnter(c));
	}

	@Test
	public void testRoomItems() {
		assertEquals("Bedroom should have 3 items", 3, bedroom.getItemsInRoom().size());
		assertEquals("Kitchen should have 3 items", 3, kitchen.getItemsInRoom().size());
		assertEquals("Bathroom should have 3 items", 3,bathroom.getItemsInRoom().size());
	}
	
	

	@Test
	public void testAddRemoveItems() {
	    GameItem testItem = new GameItem.Builder("Test Item", "Camera Da Letto", 10).build();
	    
	    int initialSize = bedroom.getItemsInRoom().size();
	    bedroom.addItemRoom(testItem);
	    assertEquals("Should have one more item", initialSize + 1, bedroom.getItemsInRoom().size());
	    assertTrue("Should contain the added item", bedroom.hasItemRoom(testItem));
	    
	    bedroom.removeItemRoom(testItem);
	    assertEquals("Should return to original size", initialSize, bedroom.getItemsInRoom().size());
}

}