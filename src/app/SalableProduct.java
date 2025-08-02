package app;

import java.util.Objects;

/**
 * Represents a product that can be sold in the store. Each SalableProduct has a
 * name, description, price, and quantity in stock.
 */
public class SalableProduct implements Comparable<SalableProduct> {
	private String name;
	private String description;
	private double price;
	private int quantity;

	public SalableProduct() {
		name = "";
		description = "";
		price = 0;
		quantity = 0;
	}

	/**
	 * Constructs a new SalableProduct with the given properties.
	 *
	 * @param name        The name of the product.
	 * @param description A brief description of the product.
	 * @param price       The price of the product.
	 * @param quantity    The available quantity of the product.
	 */
	public SalableProduct(String name, String description, double price, int quantity) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalableProduct that = (SalableProduct) obj;
        return Double.compare(that.price, price) == 0 && quantity == that.quantity &&
               Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, quantity);
    }
    
	/**
	 * Gets the name of the product.
	 *
	 * @return The product's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the product.
	 *
	 * @param name The new name of the product.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the product.
	 *
	 * @return The product's description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of the product.
	 *
	 * @param description The new description of the product.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the price of the product.
	 *
	 * @return The product's price.
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Sets the price of the product.
	 *
	 * @param price The new price of the product.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets the available quantity of the product.
	 *
	 * @return The quantity in stock.
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Sets the available quantity of the product.
	 *
	 * @param quantity The new quantity in stock.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(SalableProduct o) {
		int nameComparedValue = this.getName().compareToIgnoreCase(o.getName());
		if(nameComparedValue != 0) {
			return nameComparedValue;
		} 
		return Double.compare(this.getPrice(), o.getPrice());
	}

}
