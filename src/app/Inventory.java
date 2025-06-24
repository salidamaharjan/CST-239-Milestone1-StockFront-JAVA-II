package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory of SalableProducts in the store.
 */
public class Inventory {
	private List<SalableProduct> productList;

	/**
	 * Constructs an empty inventory.
	 */
	public Inventory() {
		this.productList = new ArrayList<>();
	}

	/**
	 * Gets the list of all products in the inventory.
	 * 
	 * @return A list of SalableProduct instances.
	 */
	public List<SalableProduct> getAllProduct() {
		return this.productList;
	}

	/**
	 * Adds a new product to the inventory.
	 * 
	 * @param product The SalableProduct to add.
	 */
	public void addProduct(SalableProduct product) {
		if (product != null) {
			this.productList.add(product);
		} else {
			System.out.println("Cannot add null product to inventory.");
		}
	}

	/**
	 * Searches for a product in the inventory by its name (case-insensitive).
	 * 
	 * @param name The name of the product to search for.
	 * @return The matching SalableProduct, or null if not found.
	 */
	public SalableProduct getProductByName(String name) {
		List<SalableProduct> productList = this.productList;
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getName().equalsIgnoreCase(name)) {
				return productList.get(i);
			}
		}
		return null;
	}

	/**
	 * Updates the quantity of the specified product in the inventory.
	 * 
	 * @param product     The product to update.
	 * @param newQuantity The new quantity to set.
	 */
	public void updateQuantity(SalableProduct product, int newQuantity) {
		if (product == null) {
			System.out.println("Product not found");
		} else {
			product.setQuantity(newQuantity);
		}
	}
}
