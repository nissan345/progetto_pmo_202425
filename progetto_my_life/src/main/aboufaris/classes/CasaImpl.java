package main.aboufaris.classes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import main.aboufaris.interfaces.Casa;
import main.aboufaris.interfaces.Stanza;

public class CasaImpl implements Casa{
    
   
    private Map<String, Stanza> stanze;
    private Optional<Stanza> stanzaCorrente;

    public CasaImpl(Stanza nuovaStanza){
        this.stanze = new HashMap<>();
        this.stanzaCorrente = Optional.empty();
        this.stanze.put(nuovaStanza.getNomeStanza(), nuovaStanza);
    }

    public Optional<Stanza> getStanzaCorrente(){
        if(stanzaCorrente.isEmpty()){
            return Optional.empty();
        }
        return stanzaCorrente;
    }

    public Map<String, Stanza> getStanze(){
        return stanze;
    }

    public Optional<Stanza> entraInStanza(Stanza s){
        this.stanzaCorrente = Optional.of(s);
        return stanzaCorrente;
    }

    public Map<String, Stanza> esciDaStanza(){
        if(stanzaCorrente.isEmpty()){
            throw new UnsupportedOperationException();
        }
        return this.stanze;
        
    }
}
