package main.aboufaris.interfaces;

import java.util.Map;
import java.util.Optional;

public interface Casa{

    public Map<String, Stanza> getStanze();
    public void aggiungiStanza(Stanza s);
    public Optional<Stanza> getStanzaCorrente();
    public Optional<Stanza> entraInStanza(String s);
    public Map<String, Stanza> esciDaStanza();

}