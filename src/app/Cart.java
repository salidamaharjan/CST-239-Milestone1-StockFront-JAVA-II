package app;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart that holds SalableProducts and their quantities.
 */
public class Cart {

	private Map<SalableProduct, Integer> products;

	/**
	 * Constructs an empty shopping cart.
	 */
	public Cart() {
		this.products = new HashMap<>();
	}

	/**
	 * Gets the products and their quantities in the cart.
	 * 
	 * @return A map of SalableProduct to quantity.
	 */
	public Map<SalableProduct, Integer> getProducts() {
		return this.products;
	}

	/**
	 * Adds a product with the specified quantity to the cart. If the product is
	 * already in the cart, increases the quantity.
	 * 
	 * @param product The product to add.
	 * @param qty     The quantity to add.
	 */
	public void addToCart(SalableProduct product, int qty) {
		products.put(product, products.getOrDefault(product, 0) + qty);
	}

	/**
	 * Removes the specified quantity of the product from the cart. If the quantity
	 * to remove is greater than or equal to the current quantity, the product is
	 * removed entirely from the cart.
	 * 
	 * @param product The product to remove.
	 * @param qty     The quantity to remove.
	 */
	public void removeFromCart(SalableProduct product, int qty) {
		if (products.containsKey(product)) {
			int currentQty = products.get(product);
			if (qty >= currentQty) {
				products.remove(product);
			} else {
				products.put(product, currentQty - qty);
			}
		}
	}

	/**
	 * Calculates the total price of all products in the cart, considering each
	 * product's price and quantity.
	 * 
	 * @return The total price of the cart.
	 */
	public double getTotalPrice() {
		double totalPrice = 0.0;
		for (Map.Entry<SalableProduct, Integer> entry : products.entrySet()) {
			totalPrice += entry.getKey().getPrice() * entry.getValue();
		}
		return totalPrice;
	}

	/**
	 * Clears all products from the cart.
	 */
	public void clearCart() {
		products.clear();
	}

}
