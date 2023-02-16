package domain;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Pronounication {

	private Map<String, String> audioUrls = null;

	private BufferedImage pitchAccentImage = null;

	public void setAudioUrls(final Map<String, String> audioUrls) {
		this.audioUrls = audioUrls;
	}

	public void setPitchAccentImage(final BufferedImage pitchAccentImage) {
		this.pitchAccentImage = pitchAccentImage;
	}

}