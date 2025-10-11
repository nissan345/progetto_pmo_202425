package main.control;

import java.util.List;
import main.fabbri.classes.*;
import main.giuseppetti.classes.Missione;


public class ControlPersonaggio {

    Personaggio personaggio;
    

    public ControlPersonaggio(){
       //this.personaggio = new Personaggio(nome, vestiti, preferenze, capelli);
    }

    // Metodo che mostra su schermata tutte le missioni attive del personaggio
    public void getMissioniAttive(){
        List<Missione> missioni = personaggio.getMissioniAttive();
        for(Missione m : missioni){
            //view.mostraMissioniAttive(m.getNome(), m.getDescrizione());
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
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'creaPersonaggioPersonalizzato'");
    }
}
