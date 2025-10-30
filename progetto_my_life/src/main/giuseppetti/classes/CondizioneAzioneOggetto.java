package main.giuseppetti.classes;

import main.fabbri.classes.Personaggio;
import main.giuseppetti.interfaces.CondizioneCompletamento;

public class CondizioneAzioneOggetto implements CondizioneCompletamento{
	
	private String nomeOggetto;
	    
	public CondizioneAzioneOggetto(String nomeOggetto) {
		this.nomeOggetto = nomeOggetto;
	}

	@Override
	public boolean verificaCompletamento(Personaggio personaggio) {
		return personaggio.haUsatoOggetto(nomeOggetto);
	} 
	   
}
