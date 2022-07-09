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

@FieldOrder({ "text", "romaji", "hiragana", "katakana", "filePath", "fileExtension", "fileLength" })
public class Voice {

	private String text, romaji, hiragana, katakana, filePath, fileDigestAlgorithm, fileDigest, fileExtension = null;

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

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface DateFormat {
		String value();
	}

	@DateFormat("yyyy-MM-dd HH:mm:ss Z")
	private Date createTs = null;

	@DateFormat("yyyy-MM-dd HH:mm:ss Z")
	private Date updateTs = null;

	public void setText(final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
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

}