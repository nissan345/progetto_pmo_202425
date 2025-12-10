package questTest;
import org.junit.Before;

import main.model.character.MainCharacter;
import main.model.character.enums.Hair;
import main.model.character.enums.Outfit;
import main.model.character.npc.Brother;
import main.model.character.npc.Dad;
import main.model.character.npc.Mum;
import main.model.quest.QuestSystem;
import main.model.world.House;
import main.model.world.Room;
import main.model.world.factory.ItemFactory;

public class SetUpWorldTest {
	
	protected QuestSystem questSystem;
	protected MainCharacter c;
	protected House h;  

	protected Room bedroom;
	protected Room livingRoom;
	protected Room kitchen;
	protected Room bathroom;
	protected Room storageRoom;
	protected Room garden;
 
	protected Mum mum;
	protected Dad dad;
	protected Brother brother;

	/**
	 * Sets up test fixtures before each test method
	 * Initializes main character, house, rooms, NPCs and quest system
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
        
        questSystem = new QuestSystem();
        questSystem.registerNPC(mum);
        questSystem.registerNPC(dad);
        questSystem.registerNPC(brother);
	}
}