package com.mpatric.mp3agic;

public interface Mp3FileUtil {

	static ID3v1 getId3v1Tag(final Mp3File instance) {
		return instance != null ? instance.getId3v1Tag() : null;
	}

}