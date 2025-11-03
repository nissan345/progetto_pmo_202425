package main.giuseppetti.classes;


import main.fabbri.classes.MainCharacter;

import main.giuseppetti.interfaces.CondizioneCompletamento;

public class CondizioneAzioneOggetto implements CondizioneCompletamento{

	private String nameOggetto;
	    
	public CondizioneAzioneOggetto(String nameOggetto) {
		this.nameOggetto = nameOggetto;
	}

	@Override
	public boolean verificaCompletamento(MainCharacter personaggio) {
		return personaggio.haUsatoOggetto(nameOggetto);

	} 
	   
}
