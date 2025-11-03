package main.giuseppetti.classes;

<<<<<<< HEAD
import main.fabbri.classes.Personaggio;
=======
import main.fabbri.classes.Character;
>>>>>>> nicxole
import main.giuseppetti.interfaces.CondizioneCompletamento;

public class CondizioneAzioneOggetto implements CondizioneCompletamento{
	
<<<<<<< HEAD
	private String nomeOggetto;
	    
	public CondizioneAzioneOggetto(String nomeOggetto) {
		this.nomeOggetto = nomeOggetto;
	}

	@Override
	public boolean verificaCompletamento(Personaggio personaggio) {
		return personaggio.haUsatoOggetto(nomeOggetto);
=======
	private String nameOggetto;
	    
	public CondizioneAzioneOggetto(String nameOggetto) {
		this.nameOggetto = nameOggetto;
	}

	@Override
	public boolean verificaCompletamento(Character personaggio) {
		return personaggio.haUsatoOggetto(nameOggetto);
>>>>>>> nicxole
	} 
	   
}
