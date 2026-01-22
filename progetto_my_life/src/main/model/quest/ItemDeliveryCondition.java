package main.model.quest;

import main.model.character.MainCharacter;
import main.model.world.gameItem.GameItem;

public class ItemDeliveryCondition implements CompletionCondition {
    
    private String itemName;
    
    public ItemDeliveryCondition(GameItem item) {
        this.itemName = item.getName();
    }
    
    public ItemDeliveryCondition(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean checkCompletion(MainCharacter character) {
        return character.getInventory().hasItem(itemName);
    }

    @Override
    public void onQuestCompleted(MainCharacter character) {
        character.getInventory().removeItem(itemName);
    }
}
