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
import main.neri.classe.*;
import main.view.*;

public class Control {
    // Gestire il movimento tra stanze
    private final int MISSIONI_TOTALI = 3;
    private final int SOGLIA_BASSA = 20;
    private final int SOGLIA_CRITICA = 5;
    private final int DECADIMENTO_STATO = 12000;
    private final Casa casa;
    private Personaggio personaggio;
    private GestoreMissioni gestoreMissioni;
    private int contatoreMissioni;
    private GestoreAzioni gestoreAzioni;
    private OggettoGenerico oggettoGioco;
    private View view;
    private Timer gameTimer;
    
    // Inizializzare la mappa, creando le diverse stanze
    // Gestire il movimento tra stanze, idea: 
    // Mi trovo su Casa -> Click su stanza -> Entro nella stanza
    // onClickEsci -> esce dalla stanza -> view della casa
    // Metodo che serve a creare la stanza
    private Control(){
       this.casa = new CasaImpl();
       this.gestoreMissioni = new GestoreMissioni();
       this.contatoreMissioni = 0;
       this.gestoreAzioni = new GestoreAzioni();
       this.view = new View();
       this.gameTimer = new Timer();
       

    }

    public void startGame(){
        // inizializza personalizzazione personaggio
        
        creaMondo();
        // devo vedere altra roba. 
        

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
        
        // Creazione degli NPC
        Madre madre = new Madre(salotto);
        Padre padre = new Padre(giardino);
        Fratello fratello = new Fratello(cucina);
       
         // Registra NPC nel gestore missioni
        gestoreMissioni.registraNPC(madre);
        gestoreMissioni.registraNPC(padre);
        gestoreMissioni.registraNPC(fratello);

        // Ogni NPC assegna una sua missione
        madre.assegnaMissione();
        padre.assegnaMissione();
        fratello.assegnaMissione(); 
    }

    public void onClickEntra(String nomeStanza){
        Optional<Stanza> ris = casa.entraInStanza(nomeStanza);
        if(ris.isPresent()){
            // view.mostraStanza();  metodo inventato per la view visto che non è ancora implementata
            }
        else{
            // view.mostraErrore("Errore, la stanza selezionata non esiste"); 
        }
        }
        

    public void onClickEsci(String nomeStanza){
        casa.esciDaStanza();
        // view.mostraCasa(); metodo inventato per la view per tornare nel menu principale
    }

    public void onClickNpc(NPC n){
        n.interagisci(personaggio);
    }

    public void onSecondClickNpc(NPC n){
        List<OpzioniInterazione> opzioni = n.getOpzioniDisponibili();
        // view.mostraOpzioni(opzioni); menu che dovrebbe mostrare le opzioni che fornisce l'NPC
    }

    public void onSceltaOpzioneInterazione(OpzioniInterazione scelta, NPC npc){
        String messaggio = "";
        switch(scelta) {
            case CHIEDI_MISSIONE:
                Missione missione = npc.assegnaMissione();
                if (missione != null) {
                    messaggio = "Nuova missione: " + missione.getNome();
                    personaggio.aggiungiMissione(missione);
                    // view.mostraMessaggio(messaggio);
                } else {
                    messaggio ="Non ci sono missioni disponibili al momento.";
                    // view.mostraMessaggio(messaggio);
                }
                break;
            case CONSEGNA_MISSIONE:
                List<String> messaggi = npc.consegnaMissione(personaggio);
                contatoreMissioni++;
                for(String m : messaggi)
                // view.mostraMessaggio(m);
                break;
            case ESCI:
                messaggio = "Arrivederci!";
                // view.mostraMessaggio(messaggio);
                break;
            default:
                messaggio = "Opzione non valida.";
                // view.mostraMessaggio(messaggio);
        }
    }

    // Metodo che serve per gli effetti dell'uso dell'oggetto
    public void onClickOggetto(OggettoGioco oggettoGioco){
        String messaggio;
        // Devo controllare in che stanza sono
        Optional<Stanza> stanzaCorrente = casa.getStanzaCorrente();
        if(stanzaCorrente.isEmpty()){
            // Lanciare un'eccezione o altro
        }
        Stanza corrente = stanzaCorrente.get();
        // Quali sono gli oggetti disponibili nella stanza
        if(corrente.hasOggettoStanza(oggettoGioco)){
            List<String> azioniDisponibili = gestoreAzioni.getAzioniDisponibili(oggettoGioco, personaggio);
            /*int scelta = view.mostraAzioni(azioniDisponibili);
            String azioneDaFare = azioniDisponibili.get(scelta);
            RisultatoAzione risultato = gestoreAzioni.eseguiAzione(azioneDaFare, oggettoGioco, personaggio);
            view.mostraRisultato(risultato.getMessaggio());
            view.aggiornaStatoPersonaggio();*/    
        }else{
            //view.mostraErrore("L'oggetto non si trova in stanza!");
        }
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
                        // view.mostraAvviso(a);
                    }
                }
                 // view.mostraBisogni();
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

    // Per la visualizzazione della mappa 
    public void getMappaCompleta(){
        Map<String,Stanza> stanze = casa.getStanze();
        for(Map.Entry<String, Stanza> s: stanze.entrySet()){
            String nome = s.getKey();
            Stanza stanza = s.getValue();
            // view.mostraStanza(nome, stanza);
        }
    }

    // Per la stanza corrente, quindi sapere cosa c'è o meno
    public void getStanzaCorrente(){
        Stanza stanzaCorrente = casa.getStanzaCorrente().get();
        // view.mostraStanza(stanzaCorrente.getName());
    }

    // Metodo che mostra su schermata tutte le missioni attive del personaggio
    public void getMissioniAttive(){
        List<Missione> missioni = personaggio.getMissioniAttive();
        for(Missione m : missioni){
            //view.mostraMissioniAttive(m.getNome, m.getDescrizione);
        }
    }

    public void gestisciVittoria(){
        // Si vince nel caso in cui un personaggio riesce a finire tutte le missioni
        if(contatoreMissioni == MISSIONI_TOTALI){
            
        }
    }

    public void gestisciSconfitta(){
        // Personaggio muore perché TUTTI i suoi bisogni sono sotto la soglia
    }

    public void salvaGioco(){

    }

    public void caricaGioco(){

    }

    /*public void resetGioco(){
         // Si fa un reset degli stati giornalieri
        if(gameTimer != null){
            gameTimer.cancel();
            personaggio.resetStato();
            // view.mostraMessaggio("A domani!")
        }
        
    } */

    public void aggiornaUI(){

    }

    public void mostraStatistiche(){

    }

    public void gestisciNotifiche(){
        //Messaggi del sistema
    }
    
    
    /* DA FARE QUANDO IL PERSONAGGIO SARA COMPLETO

    creaPersonaggioPersonalizzato() - personalizzazione iniziale

    aggiornaStatoPersonaggio() - aggiorna bisogni (fame, energia, igiene)

    getStatoPersonaggio() - restituisce stato corrente */
}
