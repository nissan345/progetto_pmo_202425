package characterTest;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.character.Stats;

class StatsTest {

	private Stats statistics;
	
	@BeforeEach
	void setUp() throws Exception {
		statistics = new Stats();  // Initial statistics are all 100
	}

	@Test
	public void testChangeState() {
		// Verifies the states are initialised well
		assertTrue(statistics.getEnergy()==100);	
		assertTrue(statistics.getHydration()==100);
		assertFalse(statistics.getHygiene()==80);
		assertTrue(statistics.getSatiety()==100);
		// Verifies the statistics change the right way
		statistics.changeEnergy(-20);
		statistics.changeHygiene(-5);
		assertTrue(statistics.getEnergy()==80);
		assertTrue(statistics.getHygiene()==95);
		// Verifies a statistic cannot get past the MAX_VALUE = 100
		statistics.changeSatiety(20);
		assertEquals(statistics.getSatiety(), 100);
		// Verifies a statistic cannot be negative
		statistics.changeSatiety(-120);
		assertEquals(statistics.getSatiety(),0);
		// verifies the decay method works
		statistics.decay();
		assertTrue(statistics.getHydration()==98);
		
		// Verifies the controls for states work
		assertFalse(statistics.isExhausted());
		assertFalse(statistics.isDehydrated());
		assertFalse(statistics.isDirty());
		statistics.changeEnergy(-90);
		statistics.changeHydration(-90);
		statistics.changeHygiene(-90);
		assertTrue(statistics.isExhausted());
		assertTrue(statistics.isDehydrated());
		assertTrue(statistics.isDirty());
		assertTrue(statistics.isStarving());
		
	}
	
	@Test
	public void testCanEat() {
		// Verifies if an action can be done if the statistics allow it
		// Now all the statistics are 100, so the MainCharacter 
		// should not be allowed to eat
		// The Method should only return true if:
		// energy > 40, hygiene > 50, satiety < 80
		assertFalse(statistics.canEat());
		statistics.changeEnergy(-30);
		assertEquals(statistics.getEnergy(), 70);
		statistics.changeHygiene(-40);
		assertEquals(statistics.getHygiene(), 60);
		statistics.changeSatiety(-20);
		assertEquals(statistics.getSatiety(), 80);
		assertTrue(statistics.canEat());
		// Now we'll get energy = 40 to see if the method works as expected
		statistics.changeEnergy(-10);
		statistics.changeSatiety(-50);
		assertTrue(statistics.canEat()); 
		// If the energy is too low (35), MainCharacter won't be allowed to eat
		statistics.changeEnergy(-25);
		assertFalse(statistics.canEat());
		
	}
	
	@Test
	public void testCanSleep() {
		// Verifies if an action can be done if the statistics allow it
		// Now all the statistics are 100, so the MainCharacter 
		// should not be allowed to sleep
		// Sleep is only allowed if energy < 70 and hygiene > 30
		assertFalse(statistics.canSleep());
		statistics.changeEnergy(-10);
		assertEquals(statistics.getEnergy(), 90);
		assertFalse(statistics.canSleep());
		statistics.changeEnergy(-30);
		assertEquals(statistics.getEnergy(), 60);
		assertTrue(statistics.canSleep());
		// The MainCharacter should not be allowed to sleep if
		// he's too dirty
		statistics.changeHygiene(-75);
		assertEquals(statistics.getHygiene(), 25);
		assertFalse(statistics.canSleep());	
	}
	
	@Test
	public void testCanDrink() {
		// The method canDrink should return true only if the 
		// MainCharacter is thirsty
		assertFalse(statistics.canDrink());
		statistics.changeHydration(-10);
		assertEquals(statistics.getHydration(), 90);
		assertFalse(statistics.canDrink());
		statistics.changeHydration(-30);
		assertEquals(statistics.getHydration(), 60);
		assertTrue(statistics.canDrink());
	}
	
	@Test
	public void testCanShower() {
		// Verifies if an action can be done if the statistics allow it
		// Now all the statistics are 100, so the MainCharacter 
		// should not be allowed to shower
		// Showering is only allowed if energy > 20 and hygiene < 70
		assertFalse(statistics.canShower());
		statistics.changeEnergy(-10);
		statistics.changeHygiene(-30);
		assertEquals(statistics.getEnergy(), 90);
		assertEquals(statistics.getHygiene(), 70);
		assertFalse(statistics.canShower());
		statistics.changeHygiene(-20);
		assertTrue(statistics.canShower());
		// MainCharacter too tired to shower
		statistics.changeEnergy(-75);
		assertFalse(statistics.canShower());
	}
	
	@Test
	public void testCanPlay() {
		// The MainCharacter should only allowed to play if the energy > 20
		assertTrue(statistics.canPlay());
		// Other statistics shouldn't interfere with the outcome
		statistics.changeSatiety(-50);
		statistics.changeHygiene(-40);
		assertTrue(statistics.canPlay());
		//if energy < 20, player shouldn't be allowed to play
		statistics.changeEnergy(-85);
		assertFalse(statistics.canPlay());
	}
	
}
