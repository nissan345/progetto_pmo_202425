package main.giuseppetti.classes;

import main.fabbri.classes.Character;
import main.giuseppetti.interfaces.CondizioneCompletamento;

public class CondizioneAzioneOggetto implements CondizioneCompletamento{
	
	private String nameOggetto;
	    
	public CondizioneAzioneOggetto(String nameOggetto) {
		this.nameOggetto = nameOggetto;
	}

	@Override
	public boolean verificaCompletamento(Character personaggio) {
		return personaggio.haUsatoOggetto(nameOggetto);
	} 
	   
}
