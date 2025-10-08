package main.fabbri.classes;

import java.util.EnumMap;
import java.util.Map;

public class PreferenzeGusto {

    // REAZIONI POSSIBILI
    public enum Reazione {
        PIACE("Gli piace", +15),
        NON_PIACE("Non gli piace", -10),
        INDIFFERENTE("Indifferente", 0);

        // ATTRIBUTI
        private final String descrizione;
        private final int modificaFame;

        // COSTRUTTORE
        Reazione(String descrizione, int modificaFame) {
            this.descrizione = descrizione;
            this.modificaFame = modificaFame;
        }

        // GETTER
        public String getDescrizione() { return descrizione; }
        public int getModificaFame() { return modificaFame; }
    }

    // MAPPA DELLE PREFERENZE
    private final Map<Gusto, Reazione> preferenze;

    // COSTRUTTORE
    public PreferenzeGusto() {
        this.preferenze = new EnumMap<>(Gusto.class);
        for (Gusto gusto : Gusto.values()) {
            preferenze.put(gusto, Reazione.INDIFFERENTE);
        }
    }

    // METODO PER IMPOSTARE LA PREFERENZA
    public void setPreferenza(Gusto gusto, Reazione reazione) {
        preferenze.put(gusto, reazione);
    }

    // METODO PER OTTENERE LA PREFERENZA
    public Reazione getPreferenza(Gusto gusto) {
        return preferenze.get(gusto);
    }

    // METODO PER OTTENERE LA MODIFICA DELLA FAME IN BASE AL GUSTO
    public int getModificaFame(Gusto gusto) {
        return preferenze.get(gusto).getModificaFame();
    }

    // METODO TO STRING PER VISUALIZZARE LE PREFERENZE
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Gusto, Reazione> entry : preferenze.entrySet()) {
            sb.append(entry.getKey().getNome())
              .append(": ")
              .append(entry.getValue().getDescrizione())
              .append("\n");
        }
        return sb.toString();
    }
}
