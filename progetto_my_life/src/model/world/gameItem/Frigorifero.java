package model.world.gameItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.action.RisultatoAzione;
import model.character.MainCharacter;
import model.world.gameItem.OggettoGioco.Builder;



public class Frigorifero extends OggettoGioco{
	
private final Map<FoodType, RisultatoAzione> effettiCibi;

    
    public Frigorifero() {
        super(new Builder("Frigorifero", "Cucina")
                .message("Apri il frigorifero... Scegli cosa mangiare!"));
        
        this.effettiCibi = new HashMap<>();
        // Inizializza tutti i cibi con i loro effetti

        for (FoodType cibo : FoodType.values()) {
            effettiCibi.put(cibo, new RisultatoAzione(
                cibo.getDescription(), 
                cibo.getHunger(), 
                cibo.getSatiety(), 
                cibo.getEnergy(),
                0
            ));
        }
    }
    
    @Override
    public boolean richiedeScelta() { return true; }

    @Override
    public java.util.List<FoodType> opzioniDisponibili(MainCharacter p) { return new ArrayList<>(effettiCibi.keySet()); }

    @Override
    public RisultatoAzione usa(MainCharacter p) { return new RisultatoAzione("Apri il frigorifero... Scegli cosa mangiare!"); }

    @Override
    public RisultatoAzione usa(MainCharacter p, Object opzione) {
        FoodType cibo = (FoodType) opzione;
        return effettiCibi.getOrDefault(cibo, new RisultatoAzione(cibo.getDescription(), cibo.getHunger(), cibo.getSatiety(), cibo.getEnergy(), 0));

    }
}
