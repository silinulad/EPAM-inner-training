package model;

import java.sql.Timestamp;

public class Present {
	private long presentId;
	private Gift gift;
	private User sender;
	private Timestamp time;

	public long getPresentId() {
		return presentId;
	}

	public void setPresentId(long presentId) {
		this.presentId = presentId;
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
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
		result = prime * result + (int) (presentId ^ (presentId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Present))
			return false;
		Present other = (Present) obj;
		if (presentId != other.presentId)
			return false;
		return true;
	}

	private Present(Builder builder) {
		presentId = builder.presentId;
		gift = builder.gift;
		sender = builder.sender;
		time = builder.time;
	}

	public static class Builder {
		private long presentId;
		private Gift gift;
		private User sender;
		private Timestamp time;

		public Builder setPresentId(long presentId) {
			this.presentId = presentId;
			return this;
		}

		public Builder setGift(Gift gift) {
			this.gift = gift;
			return this;
		}

		public Builder setSender(User sender) {
			this.sender = sender;
			return this;
		}

		public Builder setTime(Timestamp timestamp) {
			this.time = timestamp;
			return this;
		}

		public Present build() {
			if (gift == null || sender == null) {
				throw new IllegalArgumentException("Present required parameters are empty");
			}
			return new Present(this);

		}

	}

}
