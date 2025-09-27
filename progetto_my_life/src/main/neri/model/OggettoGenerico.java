package main.neri.model;

public class OggettoGenerico extends OggettoGioco {
    private TipoOggetto tipo;
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
            // … (mantieni tutta la logica che hai già scritto per ogni oggetto)
            default:
                return new RisultatoAzione("Non sai come usare questo oggetto.");
        }
    }
    
    // Metodo per resettare stati speciali (es. a fine giornata)
    public void resetStato() {
        this.statoSpeciale = false;
    }
}

