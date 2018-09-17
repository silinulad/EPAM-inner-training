package com.example.silin.guitarshop.model;

public class ProductModel {
	private long id;
	private ProductCategory category;
	private ProductMaker maker;
	private String name;
	private String description;
	private String image;

	public ProductModel() {
	}

	public ProductModel(long id, ProductCategory category, ProductMaker maker, String name, String description,
			String image) {
		this.id = id;
		this.category = category;
		this.maker = maker;
		this.name = name;
		this.description = description;
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public ProductMaker getMaker() {
		return maker;
	}

	public void setMaker(ProductMaker maker) {
		this.maker = maker;
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
		if (!(obj instanceof ProductModel))
			return false;
		ProductModel other = (ProductModel) obj;
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
		builder.append("ProductModel [id=");
		builder.append(id);
		builder.append(", category=");
		builder.append(category);
		builder.append(", maker=");
		builder.append(maker);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", image=");
		builder.append(image);
		builder.append("]");
		return builder.toString();
	}

}
