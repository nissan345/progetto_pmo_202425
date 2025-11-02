package main.neri.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.fabbri.classes.*;

public class Frigorifero extends OggettoGioco{
	
private final Map<TipoCibo, RisultatoAzione> effettiCibi;
    
    public Frigorifero() {
        super(new Builder("Frigorifero", "Cucina")
                .message("Apri il frigorifero... Scegli cosa mangiare!"));
        
        this.effettiCibi = new HashMap<>();
        // Inizializza tutti i cibi con i loro effetti
        for (TipoCibo cibo : TipoCibo.values()) {
            effettiCibi.put(cibo, new RisultatoAzione(
                cibo.getDescrizione(), 
                cibo.getFame(), 
                cibo.getSete(), 
                cibo.getEnergia(),
                0
            ));
        }
    }
    
    @Override
    public boolean richiedeScelta() { return true; }

    @Override
    public java.util.List<TipoCibo> opzioniDisponibili(Character p) { return new ArrayList<>(effettiCibi.keySet()); }

    @Override
    public RisultatoAzione usa(Character p) { return new RisultatoAzione("Apri il frigorifero... Scegli cosa mangiare!"); }

    @Override
    public RisultatoAzione usa(Character p, Object opzione) {
        TipoCibo cibo = (TipoCibo) opzione;
        return effettiCibi.getOrDefault(cibo, new RisultatoAzione(cibo.getDescrizione(), cibo.getFame(), cibo.getSete(), cibo.getEnergia(), 0));
    }
}
