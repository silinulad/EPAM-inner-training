package model;

import java.sql.Timestamp;

/**
 * Created by Silin on 08.2018.
 */

/**
 * Group Bean with Builder pattern
 */
public class Group {

	private long groupId;
	private String name;
	private User owner;
	private String description;
	private String image;
	private Timestamp dateCreated;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public String getDescription() {
		return description;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public boolean isOwner(User user) {
		return owner.equals(user);
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Group))
			return false;
		Group other = (Group) obj;
		if (groupId != other.groupId)
			return false;
		return true;
	}

	private Group(Builder builder) {
		groupId = builder.groupId;
		name = builder.name;
		owner = builder.owner;
		description = builder.description;
		image = builder.image;
		dateCreated = builder.dateCreated;
	}

	public static class Builder {
		private long groupId;
		private String name;
		private User owner;
		private String description;
		private String image;
		private Timestamp dateCreated;

		public Builder setGroupId(long groupId) {
			this.groupId = groupId;
			return this;
		}

		public Builder setImage(String image) {
			this.image = image;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setOwner(User owner) {
			this.owner = owner;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setDateCreated(Timestamp dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public Group build() {
			if (name == null || description == null) {
				throw new IllegalArgumentException("Group required parameters are empty");
			}
			return new Group(this);
		}
	}
}
