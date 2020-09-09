package ro.dascaliucadi.springapp.properties;

import org.springframework.beans.factory.annotation.Value;

public class Properties {
	
	String fileExtension;
	String directoryName;

	public String getFileExtension() {
		return fileExtension;
	}

	@Value("${file.extension}")
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	@Value("${file.directory}")
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

}
