package app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory of SalableProducts in the store.
 */
public class InventoryManager implements Inventory {
	private List<SalableProduct> storeInventory;

	/**
	 * Initialize the storeInventory with sample products
	 */
	public InventoryManager() {
		this.storeInventory = new ArrayList<>();
	}

	@Override
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
	@Override
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
	@Override
	public List<SalableProduct> getAllProduct() {
		return this.storeInventory;
	}

	/**
	 * Searches for a product in the inventory by its name (case-insensitive).
	 * 
	 * @param name The name of the product to search for.
	 * @return The matching SalableProduct, or null if not found.
	 */
	@Override
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
