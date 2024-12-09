package javazoom.jl.player;

import javazoom.jl.decoder.JavaLayerException;

public interface PlayerUtil {

	static void play(final Player instance) throws JavaLayerException {
		if (instance != null) {
			instance.play();
		}
	}

}