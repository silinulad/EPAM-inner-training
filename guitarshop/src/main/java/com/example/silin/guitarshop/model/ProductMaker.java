package com.example.silin.guitarshop.model;

public class ProductMaker {
	private long id;
	private String name;
	private String country;

	public ProductMaker() {
	}

	public ProductMaker(long id, String name, String country) {
		this.id = id;
		this.name = name;
		this.country = country;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductMaker))
			return false;
		ProductMaker other = (ProductMaker) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductMaker [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", country=");
		builder.append(country);
		builder.append("]");
		return builder.toString();
	}

}
