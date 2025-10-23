package main.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import main.aboufaris.classes.*;
import main.aboufaris.interfaces.*;
import main.fabbri.classes.*;
import main.giuseppetti.classes.*;
import main.neri.classes.*;
import main.view.*;

public class Control {
    // Gestire il movimento tra stanze
    private final int MISSIONI_TOTALI = 3;
    private final int SOGLIA_BASSA = 20;
    private final int SOGLIA_CRITICA = 5;
    private final int DECADIMENTO_STATO = 12000;
    private final Casa casa;
    private Personaggio personaggio;
    private int contatoreMissioni;
    private GestoreAzioni gestoreAzioni;
    private View view;
    private Timer gameTimer;
    
    // Inizializzare la mappa, creando le diverse stanze
    // Gestire il movimento tra stanze, idea: 
    // Mi trovo su Casa -> Click su stanza -> Entro nella stanza
    // onClickEsci -> esce dalla stanza -> view della casa
    // Metodo che serve a creare la stanza
    private Control(){
       this.casa = new CasaImpl();
       this.contatoreMissioni = 0;
       this.gestoreAzioni = new GestoreAzioni();
       this.view = new View();
       this.gameTimer = new Timer();
    }

    public void startGame(){
        // inizializza personalizzazione personaggio
        view.mostraMenu();
        creaPersonaggioPersonalizzato();
        // creaPersonaggio();
        creaMondo();
        // devo vedere altra roba. 
        avviaTimerBisogni();
        view.mostraCasa();

    }

    private void creaMondo(){
         // Creazione delle stanze
        Stanza bagno = new StanzaImpl("Bagno", new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Bagno").values()));
        Stanza camera = new StanzaImpl("Camera da Letto",  new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Camera da Letto").values())) ;
        Stanza cucina = new StanzaImpl("Cucina",  new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Cucina").values()));
        Stanza salotto = new StanzaImpl("Salotto", new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Salotto").values()));
        Stanza giardino = new StanzaImpl("Giardino",  new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Giardino").values()));  
        Stanza sgabuzzino = new StanzaImpl("Sgabuzzino", new ArrayList<>(FabbricaOggetti.creaOggettiStanza("Sgabuzzino").values()));
        // aggiunge le stanze alla casa
        casa.aggiungiStanza(bagno);
        casa.aggiungiStanza(camera);
        casa.aggiungiStanza(cucina);
        casa.aggiungiStanza(salotto);
        casa.aggiungiStanza(giardino);
        casa.aggiungiStanza(sgabuzzino);
        // Crea Npc
        Madre madre = new Madre(salotto);
        Padre padre = new Padre(giardino);
        Fratello fratello = new Fratello(cucina);
        
    }

    

    // Metodo che gestisce il tempo per il decadimento dei bisogni col tempo
    public void avviaTimerBisogni(){
        TimerTask task = new TimerTask() {
            @Override
            public void run(){
                personaggio.decadimentoStato();
                List<String> avvisi = controllaStatiCritici();
                if(!avvisi.isEmpty()){
                    for(String a : avvisi){
                        view.mostraAvviso(a);
                    }
                }
                view.mostraBisogni();
                gestisciSconfitta();
            }
        };
        gameTimer.scheduleAtFixedRate(task, 0, DECADIMENTO_STATO);
    }


    // Metodo che verifica se i bisogni sono sotto la soglia
    private List<String> controllaStatiCritici(){
        List<String> avvisi = new ArrayList<>();

        if(personaggio.getFame() < SOGLIA_BASSA) 
            avvisi.add("Attenzione! " + personaggio.getNome() + " deve mangiare!");
        if(personaggio.getEnergia() < SOGLIA_BASSA)
            avvisi.add("Attenzione! " + personaggio.getNome() + " deve dormire!");
        if(personaggio.getIgiene() < SOGLIA_BASSA)
            avvisi.add("Attenzione! " + personaggio.getNome() + " deve lavarsi!");
        
        if(personaggio.getFame() < SOGLIA_CRITICA) 
            avvisi.add("ALLARME! " + personaggio.getNome() + " STA PER SVENIRE DALLA FAME!");
        if(personaggio.getEnergia() < SOGLIA_CRITICA)
            avvisi.add("ALLARME! " + personaggio.getNome() + " STA PER PERDERE I SENSI!");
        if(personaggio.getIgiene() < SOGLIA_CRITICA)
            avvisi.add("ALLARME! " + personaggio.getNome() + " NON SI RIESCE A RESPIRARGLI VICINO!");
        
        return avvisi;
    }

    
   
    // Metodo che mostra su schermata tutte le missioni attive del personaggio
    public void getMissioniAttive(){
        List<Missione> missioni = personaggio.getMissioniAttive();
        for(Missione m : missioni){
            view.mostraMissioniAttive(m.getNome(), m.getDescrizione());
        }
    }

    public void gestisciVittoria(){
        // Si vince nel caso in cui un personaggio riesce a finire tutte le missioni
        if(contatoreMissioni == MISSIONI_TOTALI){
            view.mostraVittoria();
        }
    }

    public void gestisciSconfitta(){
        // Personaggio muore perché TUTTI i suoi bisogni sono sotto la soglia
        if(personaggio.getEnergia() < 0 && personaggio.getFame() < 0 && personaggio.getIgiene() < 0 && personaggio.getSete()<0){
            view.mostraSconfitta();
            gameTimer.cancel();
        }
    }

    public void onClickNpc(NPC n){
        n.getDialogoIniziale();
    }

    public void onSecondClickNpc(NPC n){
        List<OpzioniInterazione> opzioni = n.getOpzioniDisponibili(personaggio);
        view.mostraOpzioni(opzioni); // menu che dovrebbe mostrare le opzioni che fornisce l'NPC
    }

    public void onSceltaOpzioneInterazione(OpzioniInterazione scelta, NPC npc){
        String messaggio = "";
        switch(scelta) {
            case CHIEDI_MISSIONE:
                Missione missione = npc.assegnaMissione(personaggio);
                if (missione != null) {
                    messaggio = "Nuova missione: " + missione.getNome();
                    personaggio.aggiungiMissione(missione);
                    view.mostraMessaggio(messaggio);
                } else {
                    messaggio ="Non ci sono missioni disponibili al momento.";
                    view.mostraMessaggio(messaggio);
                }
            break;
            case CONSEGNA_MISSIONE:
                List<String> messaggi = npc.consegnaMissione(personaggio);
                contatoreMissioni++;
                for(String m : messaggi)
                view.mostraMessaggio(m);
            break;
            case ESCI:
                messaggio = "Arrivederci!";
                view.mostraMessaggio(messaggio);
            break;
            default:
                messaggio = "Opzione non valida.";
                view.mostraMessaggio(messaggio);
        }
    }


    public Personaggio getPersonaggio(){
      return personaggio;
    }

    public boolean isSconfitta(){
        // Personaggio muore perché TUTTI i suoi bisogni sono sotto la soglia
        return personaggio.getEnergia() < 0 && personaggio.getFame() < 0 && personaggio.getIgiene() < 0 && personaggio.getSete()<0;

    }

    public void creaPersonaggioPersonalizzato() {
        // Qui andrebbe la logica per creare un personaggio personalizzato
        // Per ora creo un personaggio di default

        String nome = view.chiediNomePersonaggio();
        Vestito vestiti = scegliVestiti();
        Dieta dieta = scegliDieta();
        Capelli capelli = scegliCapelli();
        this.personaggio = new Personaggio(nome, vestiti, dieta, capelli);
    }

    private Vestito scegliVestiti(){
        Vestito[] vestitiDisponibili = Vestito.values();
        String[] vestiti = new String[vestitiDisponibili.length];
        for(int i=0; i<vestitiDisponibili.length; i++){
            vestiti[i] = vestitiDisponibili[i].getNome() + ": " + vestitiDisponibili[i].getDescrizione();
        }
        int scelta = view.mostraVestitiDisponibili(vestiti);
        return vestitiDisponibili[scelta];
    }

    private Dieta scegliDieta(){
        Dieta[] dieteDisponibili = Dieta.values();
        String[] diete = new String[dieteDisponibili.length];
        for(int i=0; i<dieteDisponibili.length; i++){
            diete[i] = dieteDisponibili[i].getNome() + ": " + dieteDisponibili[i].getDescrizione();
        }
        int scelta = view.mostraDieteDisponibili(diete);
        return dieteDisponibili[scelta];
    }

    private Capelli scegliCapelli(){
        Capelli[] capelliDisponibili = Capelli.values();
        String[] capelli = new String[capelliDisponibili.length];
        for(int i=0; i<capelliDisponibili.length; i++){
            capelli[i] = capelliDisponibili[i].getNome() + ": " + capelliDisponibili[i].getDescrizione();
        }
        int scelta = view.mostraCapelli(capelli);
        return capelliDisponibili[scelta];
    }
    
    public void aggiornaUI(){
        /*view.mostraStatistiche();
        view.mostraPersonaggio();
        view.mostraStanza();
        view.mostraCasa(); */
        
    }
     public void onClickEntra(String nomeStanza){
        Optional<Stanza> ris = casa.entraInStanza(nomeStanza);
    }

    public void onClickEsci(String nomeStanza){
        casa.esciDaStanza();
        view.mostraCasa(); // metodo inventato per la view per tornare nel menu principale
    }

    // Metodo che serve per gli effetti dell'uso dell'oggetto
    public void onClickOggetto(OggettoGioco oggettoGioco){
        // Devo controllare in che stanza sono
        Optional<Stanza> stanzaCorrente = casa.getStanzaCorrente();
        if(stanzaCorrente.isEmpty()){
            // Lanciare un'eccezione o altro
        }
        Stanza corrente = stanzaCorrente.get();
        // Quali sono gli oggetti disponibili nella stanza
        if(corrente.hasOggettoStanza(oggettoGioco)){
            String messaggio = personaggio.interagisciOggetto(oggettoGioco);
            view.mostraMessaggio(messaggio);
        }else{
            view.mostraErrore("L'oggetto non si trova in stanza!");
        }
    }

    // Per la visualizzazione della mappa 
    public void getMappaCompleta(){
        Map<String,Stanza> stanze = casa.getStanze();
        for(Map.Entry<String, Stanza> s: stanze.entrySet()){
            String nome = s.getKey();
            Stanza stanza = s.getValue();
            view.mostraStanza(nome, stanza);
        }
    }

    private Stanza getStanzaCorrente(){
        return casa.getStanzaCorrente()
            .orElseThrow(() -> new IllegalStateException("Nessuna stanza corrente"));
    }
    public void mostraOggettiStanzaCorrente(){
        Stanza stanzaCorrente = getStanzaCorrente();
        List<OggettoGioco> oggettiPresenti = stanzaCorrente.getOggettiInStanza();
        view.mostraOggettiInStanza(oggettiPresenti);
    }

    public void mostraNpcInStanzaCorrente(){
        Stanza stanzaCorrente = getStanzaCorrente();
        NPC npcInStanza = stanzaCorrente.getNpcInStanza().get();
        view.mostraNpc(npcInStanza);
    }
}