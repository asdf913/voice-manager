package domain;

import java.util.Date;

public class Voice {

	private String text, romaji, hiragana, filePath, fileDigestAlgorithm, fileDigest, fileExtension = null;

	private Long fileLength = null;

	private Date createTs, updateTs = null;

	public void setText(final String text) {
		this.text = text;
	}

	public void setRomaji(final String romaji) {
		this.romaji = romaji;
	}

	public void setHiragana(final String hiragana) {
		this.hiragana = hiragana;
	}

	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	public void setFileDigestAlgorithm(final String fileDigestAlgorithm) {
		this.fileDigestAlgorithm = fileDigestAlgorithm;
	}

	public void setFileDigest(final String fileDigest) {
		this.fileDigest = fileDigest;
	}

	public void setFileExtension(final String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void setFileLength(final Long fileLength) {
		this.fileLength = fileLength;
	}

	public void setCreateTs(final Date createTs) {
		this.createTs = createTs;
	}

	public void setUpdateTs(final Date updateTs) {
		this.updateTs = updateTs;
	}

}