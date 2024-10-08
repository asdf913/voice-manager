package domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldOrder {
	String[] value();
}

@FieldOrder({ "language", "text", "romaji", "hiragana", "katakana", "filePath", "fileExtension", "fileLength",
		"jlptLevel" })
public class Voice {

	private Integer id = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Note {
		String value();
	}

	@Note("Ordinal Position")
	private Integer ordinalPosition = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface ImportField {
	}

	@ImportField
	private String language = null;

	@ImportField
	private String text = null;

	@ImportField
	private String source = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Name {
		String value();
	}

	public static enum Yomi {

		@Name("訓読み")
		KUN_YOMI

		, @Name("音読み")
		ON_YOMI

	}

	@ImportField
	private Yomi yomi = null;

	@ImportField
	private String romaji = null;

	@ImportField
	private String hiragana = null;

	@ImportField
	private String katakana = null;

	@ImportField
	private String filePath = null;

	@ImportField
	private String ipaSymbol = null;

	@ImportField
	private Boolean isKanji = null;

	@ImportField
	private Boolean jouYouKanji = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface SpreadsheetColumn {
		String value();
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface GaKuNenBeTsuKanJi {
	}

	@SpreadsheetColumn("学年別漢字")
	@ImportField
	@GaKuNenBeTsuKanJi
	private String gaKuNenBeTsuKanJi = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface JLPT {
	}

	@ImportField
	@JLPT
	private String jlptLevel = null;

	@Note("File Digest Algorithm")
	private String fileDigestAlgorithm = null;

	@Note("File Digest")
	private String fileDigest = null;

	private String fileExtension = null;

	private Iterable<String> listNames = null;

	/**
	 * @see org.apache.poi.ss.usermodel.BuiltinFormats
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface DataFormat {
		String value();
	}

	@DataFormat("#,##0")
	private Long fileLength = null;

	@Note("Pronunication Page URL")
	private String pronunciationPageUrl = null;

	private Boolean tts = null;

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface DateFormat {
		String value();
	}

	@DateFormat("yyyy-MM-dd HH:mm:ss Z")
	private Date createTs = null;

	@DateFormat("yyyy-MM-dd HH:mm:ss Z")
	private Date updateTs = null;

	public static class ByteArray {

		private byte[] content = null;

		private String mimeType = null;

		public byte[] getContent() {
			return content;
		}

		public void setContent(final byte[] content) {
			this.content = content;
		}

		public String getMimeType() {
			return mimeType;
		}

		public void setMimeType(final String mimeType) {
			this.mimeType = mimeType;
		}

	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface Visibility {
		boolean value();
	}

	@Visibility(false)
	private ByteArray pitchAccentImage = null;

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setYomi(final Yomi yomi) {
		this.yomi = yomi;
	}

	public void setRomaji(final String romaji) {
		this.romaji = romaji;
	}

	public String getRomaji() {
		return romaji;
	}

	public void setHiragana(final String hiragana) {
		this.hiragana = hiragana;
	}

	public String getHiragana() {
		return hiragana;
	}

	public void setKatakana(final String katakana) {
		this.katakana = katakana;
	}

	public String getKatakana() {
		return katakana;
	}

	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFileDigestAlgorithm(final String fileDigestAlgorithm) {
		this.fileDigestAlgorithm = fileDigestAlgorithm;
	}

	public String getFileDigestAlgorithm() {
		return fileDigestAlgorithm;
	}

	public void setFileDigest(final String fileDigest) {
		this.fileDigest = fileDigest;
	}

	public String getFileDigest() {
		return fileDigest;
	}

	public void setFileExtension(final String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileLength(final Long fileLength) {
		this.fileLength = fileLength;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setCreateTs(final Date createTs) {
		this.createTs = createTs;
	}

	public void setUpdateTs(final Date updateTs) {
		this.updateTs = updateTs;
	}

	public void setListNames(final Iterable<String> listNames) {
		this.listNames = listNames;
	}

	public Iterable<String> getListNames() {
		return listNames;
	}

	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setJlptLevel(final String jlptLevel) {
		this.jlptLevel = jlptLevel;
	}

	public String getJlptLevel() {
		return jlptLevel;
	}

	public void setIpaSymbol(final String ipaSymbol) {
		this.ipaSymbol = ipaSymbol;
	}

	public void setIsKanji(final Boolean isKanji) {
		this.isKanji = isKanji;
	}

	public void setJouYouKanji(Boolean jouYouKanji) {
		this.jouYouKanji = jouYouKanji;
	}

	public void setGaKuNenBeTsuKanJi(final String gaKuNenBeTsuKanJi) {
		this.gaKuNenBeTsuKanJi = gaKuNenBeTsuKanJi;
	}

	public void setPronunciationPageUrl(final String pronunciationPageUrl) {
		this.pronunciationPageUrl = pronunciationPageUrl;
	}

	public Boolean getTts() {
		return tts;
	}

	public void setTts(final Boolean tts) {
		this.tts = tts;
	}

	public ByteArray getPitchAccentImage() {
		return pitchAccentImage;
	}

	public void setPitchAccentImage(final ByteArray pitchAccentImage) {
		this.pitchAccentImage = pitchAccentImage;
	}

}