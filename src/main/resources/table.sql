CREATE TABLE IF NOT EXISTS voice(
	 text                  text
	,romaji                text
	,hiragana              text
	,katakana              text
	,file_path             text
	,file_extension        text
	,file_length           integer
	,file_digest_algorithm text
	,file_digest           text
	,create_ts			   TIMESTAMP WITH TIME ZONE
	,update_ts			   TIMESTAMP WITH TIME ZONE
);

ALTER TABLE voice ADD COLUMN IF NOT EXISTS yomi TEXT;

ALTER TABLE voice ADD COLUMN IF NOT EXISTS source TEXT;