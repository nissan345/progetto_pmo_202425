package main.neri.classe;
import main.fabbri.classes.*;

public class OggettoGenerico extends OggettoGioco {
    public TipoOggetto tipo;
    private boolean statoSpeciale; // per oggetti con stato (es. piante annaffiate)
    
    public enum TipoOggetto {
        // Camera da Letto
        LETTO("Letto", "Un comodo letto per riposare", "Camera da Letto"),
        COMPUTER("Computer", "Il tuo computer per divertirti", "Camera da Letto"),
        ARMADIO("Armadio", "Il tuo guardaroba pieno di vestiti", "Camera da Letto"),
        
        // Cucina
        FRIGORIFERO("Frigorifero", "Contiene cibo fresco e bevande", "Cucina"),
        FORNELLI("Fornelli", "Per cucinare deliziosi pasti", "Cucina"),
        LAVANDINO("Lavandino", "Per lavare i piatti e bere acqua", "Cucina"),
        
        // Bagno
        DOCCIA("Doccia", "Per lavarti e sentirti fresca", "Bagno"),
        WC("WC", "Per i bisogni fisiologici", "Bagno"),
        LAVATRICE("Lavatrice", "Per lavare i vestiti", "Bagno"),
        
        // Salotto
        TELEVISIONE("Televisione", "Per guardare i tuoi programmi preferiti", "Salotto"),
        STEREO("Stereo e vinili", "Per ascoltare la tua musica preferita", "Salotto"),
        DIVANO("Divano", "Per rilassarti comodamente", "Salotto"),
        LIBRERIA("Libreria", "Piena di libri interessanti da leggere", "Salotto"),
        ALBUM("Album", "Album di fotografie da riportare alla mamma per completare la missione", "Salotto"),
        
        // Sgabuzzino
        ASPIRAPOLVERE("Aspirapolvere", "Per pulire la casa", "Sgabuzzino"),
        INNAFFIATOIO("Innaffiatoio", "Per annaffiare le piante", "Sgabuzzino"),
        
        // Giardino
        PIANTE("Piante", "Belle piante che hanno bisogno di cure", "Giardino"),
        PALLA("Palla", "Per giocare e fare esercizio", "Giardino"),
        ALTALENA("Altalena", "Per dondolarti e rilassarti", "Giardino"),
        MACCHINA("Macchina", "Per spostarti fuori casa", "Giardino");
        
        private final String nomeDefault;
        private final String descrizioneDefault;
        private final String stanzaDefault;
        
        TipoOggetto(String nome, String descrizione, String stanza) {
            this.nomeDefault = nome;
            this.descrizioneDefault = descrizione;
            this.stanzaDefault = stanza;
        }
        
        public String getNomeDefault() { return nomeDefault; }
        public String getDescrizioneDefault() { return descrizioneDefault; }
        public String getStanzaDefault() { return stanzaDefault; }
    }
    
    public OggettoGenerico(TipoOggetto tipo) {
        super(tipo.getNomeDefault(), tipo.getDescrizioneDefault(), tipo.getStanzaDefault());
        this.tipo = tipo;
        this.statoSpeciale = false;
    }
    
    public OggettoGenerico(TipoOggetto tipo, String nomeCustom) {
        super(nomeCustom, tipo.getDescrizioneDefault(), tipo.getStanzaDefault());
        this.tipo = tipo;
        this.statoSpeciale = false;
    }
    
    public TipoOggetto getTipo() { return tipo; }
    public boolean getStatoSpeciale() { return statoSpeciale; }
    public void setStatoSpeciale(boolean stato) { this.statoSpeciale = stato; }
    
    
    @Override
    public RisultatoAzione usa(Personaggio personaggio) {
        switch(tipo) {
        // Camera da letto
        case LETTO -> {
            return new RisultatoAzione("Ti sdrai sul letto e riposi.", 0, 0, +40, -10);
            }
        case COMPUTER -> {
            return new RisultatoAzione("Passi un po' di tempo al computer.", +10, +5, -20, -5);
            }
        case ARMADIO -> {
            return new RisultatoAzione("Ti cambi con un abito nuovo. Ti senti fresca!", 0, 0, -5, +20);
            }
            // Cucina
        case FRIGORIFERO -> {
            return new RisultatoAzione("Mangi qualcosa dal frigorifero.", -30, 0, +10, -5);
            }
        case FORNELLI -> {
            return new RisultatoAzione("Cucini un pasto caldo.", -20, 0, -10, -5);
            }
            // Bagno
        case LAVANDINO -> {
            return new RisultatoAzione("Bevi un bicchiere d'acqua fresca.", 0, -30, +5, 0);
            }
        case DOCCIA -> {
            return new RisultatoAzione("Fai una doccia rigenerante.", 0, 0, +10, +40);
            }
        case WC -> {
            return new RisultatoAzione("Ti senti sollevata dopo essere andata al bagno.", 0, 0, +5, +5);
            }
        case LAVATRICE -> {
            return new RisultatoAzione("Metti i vestiti in lavatrice. La casa è più ordinata!", 0, 0, -10, +10);
            }
             // Salotto
        case TELEVISIONE -> {
            return new RisultatoAzione("Guardi la TV e ti rilassi.", +5, +5, +10, -5);
            }
        case STEREO -> {
            return new RisultatoAzione("Ascolti la tua musica preferita.", 0, 0, +10, 0);
            }
        case DIVANO -> {
            return new RisultatoAzione("Ti siedi sul divano e ti riposi un po'.", 0, 0, +15, 0);
            }
        case LIBRERIA -> {
            return new RisultatoAzione("Leggi un buon libro dalla libreria.", +5, 0, +10, 0);
            }
        case ALBUM -> {
            return new RisultatoAzione("Hai preso il vecchio album di fotografie.", 0, 0, 0, 0);
            }
            // Sgabuzzino
        case ASPIRAPOLVERE -> {
            return new RisultatoAzione("Usi l'aspirapolvere e pulisci la stanza.", 0, 0, -20, +10);
            }
            // Giardino
        case INNAFFIATOIO -> {
            if (!statoSpeciale) {
                statoSpeciale = true;
                return new RisultatoAzione("Innaffi le piante: ora sono felici!", 0, 0, -5, 0);
            } else {
                return new RisultatoAzione("Le piante sono già state annaffiate di recente.");
            }
            }
        case PIANTE -> {
            if (!statoSpeciale) {
                return new RisultatoAzione("Le piante sembrano assetate. Forse dovresti usare l'innaffiatoio.");
            } else {
                return new RisultatoAzione("Le piante sono rigogliose e verdi!", 0, 0, +5, +5);
            }
            }
        case PALLA -> {
            return new RisultatoAzione("Giochi con la palla e fai un po' di esercizio.", +10, +5, -15, -5);
            }
        case ALTALENA -> {
            return new RisultatoAzione("Ti dondoli sull'altalena, ti diverti e ti rilassi.", 0, 0, +15, 0);
            }
        case MACCHINA -> {
            return new RisultatoAzione("Prendi la macchina e fai un giro.", +15, +15, -20, -1);
            }
            default -> {
                return new RisultatoAzione("Non sai come usare questo oggetto.");
            }
        }

    }
    
    // Metodo per resettare stati speciali (es. a fine giornata)
    public void resetStato() {
        this.statoSpeciale = false;
    }
}

