package main.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DialogService {

<<<<<<< HEAD
	String chiediNome(String titolo, String prompt);
=======
	String chiediName(String titolo, String prompt);
>>>>>>> nicxole

	<T> Optional<T> scegli(String titolo,
			List<T> opzioni,
			Function<T, String> toLabel);

	default <E extends Enum<E>> Optional<E> scegliEnum(String titolo, Class<E> type) {
		
		E[] vals = type.getEnumConstants();
		return scegli(titolo, Arrays.asList(vals), e -> e.toString());
		
	}
	
	default Optional<Integer> scegliIndice(String titolo, List<String> labels) {
		
		throw new UnsupportedOperationException("da implementare");
		
	}
	void info(String msg);
	void errore(String msg);
	
}
