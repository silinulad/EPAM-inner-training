package model;

import java.sql.Timestamp;

/**
 * Created by Silin on 08.2018.
 */

/**
 * File Bean with Builder pattern
 */
public class AttachFile {
	private long fileId;
	private String originName;
	private String uploadedName;
	private String path;
	private final Timestamp dateUploaded;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getUploadedName() {
		return uploadedName;
	}

	public void setUploadedName(String uploadedName) {
		this.uploadedName = uploadedName;
	}

	public Timestamp getdDateUploaded() {
		return dateUploaded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (int) (fileId ^ (fileId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AttachFile))
			return false;
		AttachFile other = (AttachFile) obj;
		if (fileId != other.fileId)
			return false;
		return true;
	}

	private AttachFile(Builder builder) {
		fileId = builder.fileId;
		originName = builder.originName;
		uploadedName = builder.uploadedName;
		path = builder.path;
		dateUploaded = builder.dateUploaded;
	}

	public static class Builder {
		private long fileId;
		private String originName;
		private String uploadedName;
		private String path;
		private Timestamp dateUploaded;

		public Builder setFileId(long fileId) {
			this.fileId = fileId;
			return this;
		}

		public Builder setOriginName(String originName) {
			this.originName = originName;
			return this;
		}

		public Builder setUploadedName(String uploadedName) {
			this.uploadedName = uploadedName;
			return this;
		}

		public Builder setPath(String path) {
			this.path = path;
			return this;
		}

		public Builder setDateUploaded(Timestamp dateUploaded) {
			this.dateUploaded = dateUploaded;
			return this;
		}

		public AttachFile build() {
			if (originName == null || uploadedName == null) {
				throw new IllegalArgumentException("File required parameters are empty");
			}
			return new AttachFile(this);
		}
	}
}
