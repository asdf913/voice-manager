package domain;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Pronunciation {

	private String pageUrl = null;

	private Map<String, String> audioUrls = null;

	private BufferedImage pitchAccentImage = null;

	public void setPageUrl(final String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setAudioUrls(final Map<String, String> audioUrls) {
		this.audioUrls = audioUrls;
	}

	public Map<String, String> getAudioUrls() {
		return audioUrls;
	}

	public void setPitchAccentImage(final BufferedImage pitchAccentImage) {
		this.pitchAccentImage = pitchAccentImage;
	}

	public BufferedImage getPitchAccentImage() {
		return pitchAccentImage;
	}

}