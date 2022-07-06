create table if not exists voice(
	 text                  text
	,romaji                text
	,file_path             text
	,file_length           integer
	,file_digest_algorithm text
	,file_digest           text
	,create_ts			   timestamp WITH TIME ZONE
)