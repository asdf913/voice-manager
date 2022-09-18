CREATE TABLE IF NOT EXISTS voice(
	id                       IDENTITY
	,text                    TEXT
	,romaji                  TEXT
	,hiragana                TEXT
	,katakana                TEXT
	,file_path               TEXT
	,file_extension          TEXT
	,file_length             INTEGER
	,file_digest_algorithm   TEXT
	,file_digest             TEXT
	,yomi                    TEXT
	,source                  TEXT
	,language                TEXT
	,ordinal_position        INTEGER
	,jlpt_level              TEXT
	,ipa_symbol              TEXT
	,is_kanji                BOOLEAN
	,jo_yo_kanji             BOOLEAN
	,ga_ku_nen_be_tsu_kan_ji TEXT
	,create_ts			     TIMESTAMP WITH TIME ZONE
	,update_ts			     TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS voice_list(
	id    IDENTITY
	,name TEXT
);

CREATE TABLE IF NOT EXISTS voice_list_id(
	voice_list_id INTEGER
	,voice_id     INTEGER
);