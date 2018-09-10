package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Silin on 08.2018.
 */

/**
 * User Bean with Builder pattern
 */
public class User {

	private long userId;
	private String email;
	private String hash;
	private String firstName;
	private String lastName;
	private char gender;
	private Date birthDate;
	private String country;
	private String city;
	private String phone;
	private String avatar;
	private final Timestamp regDate;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getHash() {
		return hash;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public char getGender() {
		return gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (userId != other.userId)
			return false;
		return true;
	}

	private User(Builder builder) {
		userId = builder.userId;
		email = builder.email;
		hash = builder.hash;
		firstName = builder.firstName;
		lastName = builder.lastName;
		gender = builder.gender;
		birthDate = builder.birthDate;
		country = builder.country;
		city = builder.city;
		phone = builder.phone;
		avatar = builder.avatar;
		regDate = builder.regDate;
	}

	public static class Builder {
		private long userId;

		private String email;
		private String hash;
		private String firstName;
		private String lastName;
		private char gender;
		private Date birthDate;
		private String country;
		private String city;
		private String phone;
		private String avatar;
		private Timestamp regDate;

		public Builder setCountry(String country) {
			this.country = country;
			return this;
		}

		public Builder setCity(String city) {
			this.city = city;
			return this;
		}

		public Builder setAvatar(String avatar) {
			this.avatar = avatar;
			return this;
		}

		public Builder setUserId(long userId) {
			this.userId = userId;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setHash(String hash) {
			this.hash = hash;
			return this;
		}

		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder setGender(char gender) {
			this.gender = gender;
			return this;
		}

		public Builder setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public Builder setRegDate(Timestamp regDate) {
			this.regDate = regDate;
			return this;
		}

		public User build() {
			if (email == null || hash == null || lastName == null || firstName == null) {
				throw new IllegalArgumentException("User required parameters are empty");
			}
			return new User(this);
		}
	}
}
