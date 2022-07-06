CREATE TABLE IF NOT EXISTS voice(
	 text                  text
	,romaji                text
	,file_path             text
	,file_length           integer
	,file_digest_algorithm text
	,file_digest           text
	,create_ts			   TIMESTAMP WITH TIME ZONE
);

ALTER TABLE voice ADD COLUMN IF NOT EXISTS update_ts TIMESTAMP WITH TIME ZONE;

ALTER TABLE voice ADD COLUMN IF NOT EXISTS file_extension text;