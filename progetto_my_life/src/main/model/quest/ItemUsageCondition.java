package main.model.quest;

import main.model.character.MainCharacter;
import main.model.world.gameItem.GameItem;

public class ItemUsageCondition implements CompletionCondition {
    
    private GameItem item;
    
    public ItemUsageCondition(GameItem item) {
        this.item = item;
    }

    @Override
    public boolean checkCompletion(MainCharacter character) {
        return character.hasUsedItemForQuest(item);
    }

    @Override
    public void onQuestCompleted(MainCharacter character) {
    }
}
