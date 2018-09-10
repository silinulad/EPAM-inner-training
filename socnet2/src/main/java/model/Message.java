package model;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Message Bean with Builder pattern
 */
public class Message {

	private long messageId;
	private User sender;
	private User recipient;
	private String body;
	private Collection<AttachFile> files;
	private Timestamp time;

	public Collection<AttachFile> getFiles() {
		return files;
	}

	public void setFiles(Collection<AttachFile> attachedFiles) {
		this.files = attachedFiles;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (int) (messageId ^ (messageId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Message))
			return false;
		Message other = (Message) obj;
		if (messageId != other.messageId)
			return false;
		return true;
	}

	private Message(Builder builder) {
		messageId = builder.messageId;
		sender = builder.sender;
		recipient = builder.recipient;
		body = builder.body;
		files = builder.files;
		time = builder.time;
	}

	public static class Builder {

		private long messageId;
		private User sender;
		private User recipient;
		private String body;
		private Collection<AttachFile> files;
		private Timestamp time;

		public Builder setFiles(Collection<AttachFile> files) {
			this.files = files;
			return this;
		}

		public Builder setMessageId(long messageId) {
			this.messageId = messageId;
			return this;
		}

		public Builder setSender(User sender) {
			this.sender = sender;
			return this;
		}

		public Builder setRecipient(User recipient) {
			this.recipient = recipient;
			return this;
		}

		public Builder setBody(String body) {
			this.body = body;
			return this;
		}

		public Builder setTime(Timestamp time) {
			this.time = time;
			return this;
		}

		public Message build() {
			if (body == null) {
				throw new IllegalArgumentException("Message required parameters are empty");
			}
			return new Message(this);
		}
	}

}
