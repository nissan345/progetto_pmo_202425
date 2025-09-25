package main.aboufaris.interfaces;

import java.util.Map;
import java.util.Optional;

public interface Casa{

    public Map<String, Stanza> getStanze();
    public Optional<Stanza> entraInStanza(Stanza s);
    public Map<String, Stanza> esciDaStanza();

}