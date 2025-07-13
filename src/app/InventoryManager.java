package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory of SalableProducts in the store.
 */
public class InventoryManager {
	private List<SalableProduct> storeInventory;

	/**
	 * Initialize the storeInventory with sample products
	 */
	public InventoryManager() {
		this.storeInventory = new ArrayList<>();
	}

	/**
	 * Removes a specified quantity of a product from the inventory. If the product
	 * exists in the inventory, its quantity will be updated to the new quantity.
	 * 
	 * @param product     The {@code SalableProduct} to remove from the inventory.
	 * @param newQuantity The new quantity of the product to be set.
	 */
	public void removeSalableProduct(SalableProduct product, int newQuantity) {
		if (product == null) {
			System.out.println("Product not found");
		} else {
			product.setQuantity(newQuantity);
		}
	}

	/**
	 * Adds a new product to the inventory.
	 * 
	 * @param product The SalableProduct to add.
	 */
	public void addSalableProduct(SalableProduct product) {
		if (product != null) {
			this.storeInventory.add(product);
		} else {
			System.out.println("Cannot add null product to inventory.");
		}
	}

	/**
	 * Gets the list of all products in the inventory.
	 * 
	 * @return A list of SalableProduct instances.
	 */
	public List<SalableProduct> getAllProduct() {
		return this.storeInventory;
	}

	/**
	 * Searches for a product in the inventory by its name (case-insensitive).
	 * 
	 * @param name The name of the product to search for.
	 * @return The matching SalableProduct, or null if not found.
	 */
	public SalableProduct getProductByName(String name) {
		List<SalableProduct> storeInventory = this.storeInventory;
		for (int i = 0; i < storeInventory.size(); i++) {
			if (storeInventory.get(i).getName().equalsIgnoreCase(name)) {
				return storeInventory.get(i);
			}
		}
		return null;
	}

}
