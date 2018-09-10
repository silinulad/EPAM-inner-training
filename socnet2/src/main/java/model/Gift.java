package model;

import java.sql.Timestamp;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Gift Bean with Builder pattern
 */
public class Gift {

	private long giftId;
	private String name;
	private String description;
	private String image;
	private Timestamp dateCreated;

	public long getGiftId() {
		return giftId;
	}

	public void setGiftId(long giftId) {
		this.giftId = giftId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (int) (giftId ^ (giftId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Gift))
			return false;
		Gift other = (Gift) obj;
		if (giftId != other.giftId)
			return false;
		return true;
	}

	private Gift(Builder builder) {
		giftId = builder.giftId;
		name = builder.name;
		image = builder.image;
		description = builder.description;
		dateCreated = builder.dateCreated;
	}

	public static class Builder {
		private long giftId;
		private String name;
		private String description;
		private String image;
		private Timestamp dateCreated;

		public Builder setGiftId(long giftId) {
			this.giftId = giftId;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setImage(String image) {
			this.image = image;
			return this;
		}

		public Builder setDateCreated(Timestamp dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public Gift build() {
			if (name == null || description == null) {
				throw new IllegalArgumentException("Gift required parameters are empty");
			}
			return new Gift(this);
		}
	}
}
