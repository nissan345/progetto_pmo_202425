package main.model.character.npc;

import java.util.Collections;
import main.model.character.NPC;
import main.model.quest.CompletionCondition;
import main.model.quest.Quest;
import main.model.world.Room;
import main.model.world.House;
import main.model.world.gameItem.GameItem;

public class Dad extends NPC {

    public Dad(Room s, House house) {
        super("Dad", s, house);
    }
    
    @Override
    public String getInitialDialogue() {
        return "Ciao tesoro! Come sta andando la tua avventura?";
    }

    @Override
    public String getQuestAssignedDialogue(Quest quest) {
        return "Ottimo che tu voglia aiutare! " + quest.getDescription() + 
               "\nSo che posso contare su di te. Torna da me quando avrai finito!";
    }
    
    @Override 
    public String getQuestInProgressDialogue(Quest quest) {
        switch(quest.getName()) {
            case "Annaffia le piante":
                return "Hai già Annaffiato le piante? Ricorda che l'Innaffiatoio si trova in giardino";
            case "Festa a sorpresa": 
                return "Com'è andata con i preparativi per la festa a sorpresa? Ricordati di: pulire con l'aspirapolvere, cucinare ai fornelli e mettere la musica con lo stereo!";
            default:
                return "Come sta andando con la quest? Torna da me quando hai finito!";
        } 
    }
    
    @Override
    public String getQuestCompletionDialogue(Quest quest) {
        return "Grazie mille! Hai fatto un ottimo lavoro." +
                "'!\nSei sempre così affidabile, sono orgoglioso di te!";
    }

    @Override
    protected void initializeQuests() {
        // Get the item from the garden room
        Room garden = this.getHouse().getRoom("Giardino");
        GameItem annaffiatoio = garden.getItemsInRoom().stream()
            .filter(item -> item.getName().equals("Annaffiatoio"))
            .findFirst()
            .orElse(null);
        
        // If the item exists, create and add the quest
        if (annaffiatoio != null) {
            Quest plantsQuest = new Quest(
                "Annaffia le piante", 
                "Dovresti innaffiare le piante",
                this, 
                15, 
                20,
                Collections.singletonList(createCondition(annaffiatoio))
            );
            this.addQuest(plantsQuest);
        }

        /* Questa per ora la commentiamo perché richiede più oggetti e la gestiamo bene più avanti
        
        Quest surprisePartyQuest = new Quest("Festa a Sorpresa", 
                "Dovresti preparare una festa a sorpresa per la mamma, fai delle pulizie in casa e metti della musica in Salotto", 
                this, 
                30,
                55, 
                Arrays.asList(new CompletionCondition("Vacuum cleaner"), 
                            new CompletionCondition("Stove"), 
                            new CompletionCondition("Stereo and records"))
        );
        this.addQuest(surprisePartyQuest);
        */
    }
}