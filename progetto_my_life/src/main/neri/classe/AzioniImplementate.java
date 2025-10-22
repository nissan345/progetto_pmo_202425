package main.neri.classe;


import main.fabbri.classes.*;
import main.neri.interfacce.*;



public class AzioniImplementate {
    
    public static class AzioneUsa implements Azione {
        @Override
        public String getNome() { return "usa"; }
        @Override
        public String getDescrizione() { return "Utilizza l'oggetto per i suoi effetti"; }
        @Override
         public boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio) {
            return oggetto.isUtilizzabile();
        } 
        @Override
        public RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio) {
            if (!puoEseguire(oggetto, personaggio)) {
                return new RisultatoAzione("Non puoi usare " + oggetto.getNome() + " ora.");
            }
            return oggetto.usa(personaggio);
        }
    }
    
    public static class AzioneEsamina implements Azione {
        @Override
        public String getNome() { return "esamina"; }
        @Override
        public String getDescrizione() { return "Osserva attentamente l'oggetto"; }
        @Override
        public boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio) {
            return true;
        }
        @Override
        public RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio) {
            return new RisultatoAzione("Esamini " + oggetto.getNome() + ": " + oggetto.getDescrizione());
        }
    }
    
    public static class AzionePulire implements Azione {
        @Override
        public String getNome() { return "pulisci"; }
        @Override
        public String getDescrizione() { return "Pulisci la stanza o l'oggetto"; }
        @Override
        public boolean puoEseguire(OggettoGioco oggetto, Personaggio personaggio) {
            return personaggio.getEnergia() > 20;
        }
        @Override
        public RisultatoAzione esegui(OggettoGioco oggetto, Personaggio personaggio) {
            if (!puoEseguire(oggetto, personaggio)) {
                return new RisultatoAzione("Sei troppo stanca per pulire ora.");
            }
            return new RisultatoAzione(
                "Pulisci " + oggetto.getNome() + " accuratamente. Che soddisfazione!",
                0, -5, -25, -10
            );
        }
    }
}

