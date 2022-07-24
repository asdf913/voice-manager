package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import domain.Voice;
import domain.VoiceList;

public interface VoiceMapper {

	void insertVoice(@Param("voice") final Voice voice);

	void updateVoice(@Param("voice") final Voice voice);

	Voice searchByTextAndRomaji(@Param("text") final String text, @Param("romaji") final String romaji);

	List<Voice> retrieveAllVoices();

	List<String> searchVoiceListNamesByVoiceId(@Param("voiceId") final Integer voiceId);

	VoiceList searchVoiceListByName(final String name);

	void insertVoiceList(@Param("voiceList") final VoiceList voiceList);

	void insertVoiceListId(@Param("voiceListId") final Integer voiceListId, @Param("voiceId") final Integer voiceId);

	void deleteVoiceListByVoiceId(@Param("voiceId") final Integer voiceId);

	void deleteUnusedVoiceList();

}