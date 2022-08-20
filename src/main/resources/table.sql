CREATE TABLE IF NOT EXISTS voice(
	id                     IDENTITY
	,text                  TEXT
	,romaji                TEXT
	,hiragana              TEXT
	,katakana              TEXT
	,file_path             TEXT
	,file_extension        TEXT
	,file_length           INTEGER
	,file_digest_algorithm TEXT
	,file_digest           TEXT
	,yomi                  TEXT
	,source                TEXT
	,language              TEXT
	,ordinal_position      INTEGER
	,create_ts			   TIMESTAMP WITH TIME ZONE
	,update_ts			   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS voice_list(
	id    IDENTITY
	,name TEXT
);

CREATE TABLE IF NOT EXISTS voice_list_id(
	voice_list_id INTEGER
	,voice_id     INTEGER
);