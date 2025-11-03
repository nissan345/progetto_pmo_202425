package main.fabbri.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import main.aboufaris.interfaces.Room;
import main.giuseppetti.classes.Quest;
import main.giuseppetti.classes.NPC;
import main.neri.classes.OggettoGioco;
import main.neri.classes.RisultatoAzione;

public class MainCharacter {
	private static final int MAX_STATE = 100;
    private static final int MIN_STATE = 0;
    private String name;
    private Outfit outfit;
    private Hair hair;
    private int hunger;
    private int thirst;
    private int energy;
    private int hygiene;
    private Room currentRoom;
    private Map<Quest, Set<String>> itemUsedForQuests;


    // Modifiche per questi 
    private List<Quest> ongoingQuests;
    private List<String> oggettiUsati; // Traccia gli oggetti usati
 
    // COSTRUTTORE ------------------------------------------------------------------------
    public MainCharacter(String name, Outfit outfit, Hair hair) {
        this.name = name;
        this.outfit = outfit;
        this.hair = hair;  
        // Valori iniziali
        this.hunger = 100;
        this.thirst = 100;
        this.energy = 100;
        this.hygiene = 100;
        this.itemUsedForQuests = new HashMap<>();

        this.currentRoom = null; // Inizialmente nessuna room
        
        this.ongoingQuests = new ArrayList<>();
        this.oggettiUsati = new ArrayList<>();
    }

    // Rimuovi setters per stati, livello, name, preferenze, cibo, hair ecc... sono tutti gestiti
    // in altri metodi 

    // GETTER E SETTER -------------------------------------------------------------------
    public String getName() { 
        return name; 
    }
    
    public Outfit getVestiti() { 
        return outfit; 
    }

    public Hair getCapelli() { 
        return hair; 
    }
    
    public int getHunger() { 
        return hunger; 
    }
    
    public int getThirst() { 
        return thirst; 
    }
    
    public int getEnergy() { 
        return energy; 
    }
    
    public int getHygiene() { 
        return hygiene; 
    }

    public String getCurrentRoom() {
        if (currentRoom != null) {
            return currentRoom.getRoomName();
        } else {
            return "Nessuna room";
        }
    }
    
    // IN CASO CI DIMENTICHIAMO PER L'ENNESIMA VOLTA, QUESTI SETTER SONO NECESSARI!!!!!
    private void setHunger(int hunger) {
        this.hunger = Math.max(MIN_STATE, Math.min(MAX_STATE, hunger));
    }
    
    private void setThirst(int thirst) {
        this.thirst = Math.max(MIN_STATE, Math.min(MAX_STATE, thirst));
    }
    
    private void setEnergy(int energy) {
        this.energy = Math.max(MIN_STATE, Math.min(MAX_STATE, energy));
    }
    
    private void setHygiene(int hygiene) {
        this.hygiene = Math.max(MIN_STATE, Math.min(MAX_STATE, hygiene));
    } 
        
    public String pickCurrentRoom(Room room) {
        this.currentRoom = room;
        return "Sei entrato in: " + room.getRoomName();
    }

    
    // DA RIVEDERE E FORSE TOGLIERE
    // METODI PRINCIPALI ----------------------------------------------------------------
    public String printState() {
        StringBuilder state = new StringBuilder();
        
        state.append("\n STATO DI ").append(name.toUpperCase()).append("\n");
        state.append("Vestiti: ").append(outfit.getName()).append("\n");
        state.append("Capelli: ").append(hair.getName()).append("\n");
        state.append("Fame: ").append(hunger).append("/100\n");
        state.append("Sete: ").append(thirst).append("/100\n");
        state.append("Energia: ").append(energy).append("/100\n");
        state.append("Igiene: ").append(hygiene).append("/100\n");
        state.append("Posizione: ").append(getCurrentRoom()).append("\n");
        
        return state.toString();
    }

   
    // METODI PER LA PERSONALIZZAZIONE -------------------------------------------------------

    /* METODO PER CAMBIARE VESTITI
    public String cambiaVestiti(Vestito nuoviVestiti) {
        this.outfit = nuoviVestiti;
        return "Hai cambiato i outfit in: " + nuoviVestiti.getName();
    }
    // DA TOGLIERE
    // METODO PER CAMBIARE CAPELLI
    public String cambiaCapelli(Capelli nuoviCapelli) {
        this.hair = nuoviCapelli;
        return "Hai cambiato i hair in: " + nuoviCapelli.getName();
    }
    // HA SENSO MA NON SAPPIAMO SE SERVE
    // METODO PER MAPPARE LO STATO COMPLETO
    public Map<String, Integer> getStatoCompleto() {
        Map<String, Integer> state = new HashMap<>();
        state.put("hunger", hunger);
        state.put("thirst", thirst);
        state.put("energy", energy);
        state.put("hygiene", hygiene);
        return state;
    }
 */
  
 // METODI PER LE MISSIONI -----------------------------------------------------------------------

    //Aggiunge una quest alla lista delle questi attive
    public void addQuest(Quest quest) {
        if (quest != null && !ongoingQuests.contains(quest)) {
            ongoingQuests.add(quest);
            itemUsedForQuests.put(quest, new HashSet<>());
        }
    }
    
    // Rimuove una quest completata dalla lista delle questi attive
    public void removeQuest(Quest quest) {
        ongoingQuests.remove(quest);
        itemUsedForQuests.remove(quest);
    }

    // Verifica se il personaggio ha questi attive con un NPC specifico
    public boolean hasActiveQuestWithNPC(NPC npc) {
        return ongoingQuests.stream()
            .anyMatch(quest -> quest.getAssignerNPC().equals(npc));
    }

    // Ottiene le questi attive con un NPC specifico
    public Optional<Quest> getActiveQuestWithNPC(NPC npc) {
        return ongoingQuests.stream()
            .filter(quest -> quest.getAssignerNPC().equals(npc))
            .findFirst();
    }
    
    // Verifica automaticamente il completamento di tutte le questi attive con un NPC
    public Optional<Quest> getCompletedQuestWithNPC(NPC npc) {
	    return ongoingQuests.stream()
        .filter(q -> q.getAssignerNPC().equals(npc))
        .filter(q -> q.checkCompletion(this))
        .findFirst();
	}

    // METODI PER INTERAGIRE CON GLI OGGETTI -------------------------------------------------------
    
    // Registra l'uso di un oggetto
    public void recordItemsUsedForQuests(String itemName) {
    for (Quest q : ongoingQuests) {
        itemUsedForQuests.computeIfAbsent(q, k -> new HashSet<>()).add(itemName);
    }
}
    
    // Verifica se un oggetto è state usato
    public boolean hasUsedItemForQuest(String nameOggetto, Quest quest) {
        return itemUsedForQuests.getOrDefault(quest, Set.of()).contains(nameOggetto);
    }
     
    public String applicaRisultatoAzione(RisultatoAzione risultato, String nameOggetto) {
        // 1. Controlla se l'azione ha senso
        String messaggioControllo = verificaUtilitaAzione(risultato);
        if (messaggioControllo != null) {
            return messaggioControllo;
        }
        
        // 2. Registra uso oggetto
        recordItemsUsedForQuests(nameOggetto);
        
        // 3. Applica effetti
        this.setHunger(this.getHunger() + risultato.getDeltaHunger());
        this.setThirst(this.getThirst() + risultato.getDeltaThirst());
        this.setEnergy(this.getEnergy() + risultato.getDeltaEnergy());
        this.setHygiene(this.getHygiene() + risultato.getDeltaHygiene());
        
        // 4. Restituisce messaggio
        return risultato.getMessaggio();
    }


    // METODI PER INTERAGIRE CON GLI OGGETTI -------------------------------------------------------
    
    // Registra l'uso di un oggetto
    public void registraUsoOggetto(String nameOggetto) {
        if (!oggettiUsati.contains(nameOggetto)) {
            oggettiUsati.add(nameOggetto);
        }
    }
    
    // Verifica se un oggetto è state usato
    public boolean hasUsedOggetto(String nameOggetto) {
        return oggettiUsati.contains(nameOggetto);
    }
    
    
    public String interagisci(OggettoGioco oggetto) {
        RisultatoAzione risultato = oggetto.usa(this);
        return applicaRisultatoAzione(risultato, oggetto.getName());
    }
    
    
    private String verificaUtilitaAzione(RisultatoAzione risultato) {
    	
        if (risultato.getDeltaEnergy() > 0 && this.energy >= 100) {
            return "Sei già pieno di energy, non ha senso riposare ora!";
        }else if (risultato.getDeltaHunger() < 0 && this.hunger <= 0) {
            return "Non hai hunger, non ha senso mangiare ora!";
        }else if (risultato.getDeltaThirst() < 0 && this.thirst <= 0) {
            return "Non hai thirst, non ha senso bere ora!";
        }else if (risultato.getDeltaHygiene() > 0 && this.hygiene >= 100) {
            return "Sei già pulitissimo, non serve lavarti!";
        }else {
        	return null;
        }
    }

    public void stateDecay(){
        this.hunger = Math.max(0, this.hunger-2);
        this.thirst = Math.max(0,this.thirst-3);
        this.energy = Math.max(0, this.energy-1);
        this.hygiene = Math.max(0, this.hygiene-1);
    }
}

    