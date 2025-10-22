package main.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import main.fabbri.classes.Personaggio;

public class ControlBisogni{

    private final int SOGLIA_BASSA = 20;
    private final int SOGLIA_CRITICA = 5;
    private final int DECADIMENTO_STATO = 12000;
    private Personaggio personaggio;
    private Timer timerBisogni;

    public ControlBisogni(Personaggio personaggio) {
        this.personaggio = personaggio;
        this.timerBisogni = new Timer();
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

    // Metodo che gestisce il tempo per il decadimento dei bisogni col tempo
    public void avviaTimerBisogni(){
        TimerTask task = new TimerTask() {
            @Override
            public void run(){
                personaggio.decadimentoStato();
                List<String> avvisi = controllaStatiCritici();
                if(!avvisi.isEmpty()){
                    for(String a : avvisi){
                        //view.mostraAvviso(a);
                    }
                }
                //view.mostraBisogni();
            }
        };
        timerBisogni.scheduleAtFixedRate(task, 0, DECADIMENTO_STATO);
    }
    public List<String> getAvvisiBisogni() {
        return controllaStatiCritici();
    }

    public void fermaTimer() {
        timerBisogni.cancel();
    }
}