package main.neri.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.fabbri.classes.*;

public class Frigorifero extends OggettoGioco{
	
<<<<<<< HEAD
private final Map<TipoCibo, RisultatoAzione> effettiCibi;
=======
private final Map<FoodType, RisultatoAzione> effettiCibi;
>>>>>>> nicxole
    
    public Frigorifero() {
        super(new Builder("Frigorifero", "Cucina")
                .messaggio("Apri il frigorifero... Scegli cosa mangiare!"));
        
        this.effettiCibi = new HashMap<>();
        // Inizializza tutti i cibi con i loro effetti
<<<<<<< HEAD
        for (TipoCibo cibo : TipoCibo.values()) {
            effettiCibi.put(cibo, new RisultatoAzione(
                cibo.getDescrizione(), 
                cibo.getFame(), 
                cibo.getSete(), 
                cibo.getEnergia(),
=======
        for (FoodType cibo : FoodType.values()) {
            effettiCibi.put(cibo, new RisultatoAzione(
                cibo.getDescription(), 
                cibo.getHunger(), 
                cibo.getThirst(), 
                cibo.getEnergy(),
>>>>>>> nicxole
                0
            ));
        }
    }
    
    @Override
    public boolean richiedeScelta() { return true; }

    @Override
<<<<<<< HEAD
    public java.util.List<TipoCibo> opzioniDisponibili(Personaggio p) { return new ArrayList<>(effettiCibi.keySet()); }

    @Override
    public RisultatoAzione usa(Personaggio p) { return new RisultatoAzione("Apri il frigorifero... Scegli cosa mangiare!"); }

    @Override
    public RisultatoAzione usa(Personaggio p, Object opzione) {
        TipoCibo cibo = (TipoCibo) opzione;
        return effettiCibi.getOrDefault(cibo, new RisultatoAzione(cibo.getDescrizione(), cibo.getFame(), cibo.getSete(), cibo.getEnergia(), 0));
=======
    public java.util.List<FoodType> opzioniDisponibili(Character p) { return new ArrayList<>(effettiCibi.keySet()); }

    @Override
    public RisultatoAzione usa(Character p) { return new RisultatoAzione("Apri il frigorifero... Scegli cosa mangiare!"); }

    @Override
    public RisultatoAzione usa(Character p, Object opzione) {
        FoodType cibo = (FoodType) opzione;
        return effettiCibi.getOrDefault(cibo, new RisultatoAzione(cibo.getDescription(), cibo.getHunger(), cibo.getThirst(), cibo.getEnergy(), 0));
>>>>>>> nicxole
    }
}
