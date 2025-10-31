package main.aboufaris.classes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import main.aboufaris.interfaces.Casa;
import main.aboufaris.interfaces.Stanza;

public class CasaImpl implements Casa{
    

    private Map<String, Stanza> stanze;
    private Optional<Stanza> stanzaCorrente;

    public CasaImpl(){
        this.stanze = new HashMap<>();
        this.stanzaCorrente = Optional.empty();
    }

  

    public void aggiungiStanza(Stanza s){
        this.stanze.put(s.getNomeStanza(), s);
    }

    public Optional<Stanza> getStanzaCorrente(){
        return stanzaCorrente;
    }

    public Map<String, Stanza> getStanze(){ 
        return new HashMap<>(stanze); 
    }

    public Optional<Stanza> entraInStanza(String nome){
        Stanza s = this.stanze.get(nome);
        if (s != null) {
            this.stanzaCorrente = Optional.of(s);
            return Optional.of(s);
        }
        return Optional.empty(); // Stanza non trovata
    }

    public Map<String, Stanza> esciDaStanza(){
        if(stanzaCorrente.isEmpty()){
            throw new UnsupportedOperationException();
        }
        return this.stanze;        
    }

    public Stanza getStanza(String s){
        return this.stanze.get(s);
    }
}
