package domain;

public class Voice {

	private String text, romaji, filePath, fileDigestAlgorithm, fileDigest = null;

	private Long fileLength = null;

	public void setText(final String text) {
		this.text = text;
	}

	public void setRomaji(final String romaji) {
		this.romaji = romaji;
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

	public void setFileLength(final Long fileLength) {
		this.fileLength = fileLength;
	}

}