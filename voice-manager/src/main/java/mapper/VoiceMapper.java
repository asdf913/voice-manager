package mapper;

import org.apache.ibatis.annotations.Param;

import domain.Voice;

public interface VoiceMapper {

	void insert(@Param("voice") final Voice voice);

}