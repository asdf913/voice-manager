package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import domain.Voice;

public interface VoiceMapper {

	void insert(@Param("voice") final Voice voice);

	void update(@Param("voice") final Voice voice);

	Voice searchByTextAndRomaji(@Param("text") final String text, @Param("romaji") final String romaji);

	List<Voice> retrieveAllVoices();

}