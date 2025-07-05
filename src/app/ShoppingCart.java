package app;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart that holds SalableProducts and their quantities.
 */
public class ShoppingCart implements Cart {

	private Map<SalableProduct, Integer> productsInCart;

	/**
	 * Constructs an empty shopping cart.
	 */
	public ShoppingCart() {
		this.productsInCart = new HashMap<>();
	}

	/**
	 * Adds a product with the specified quantity to the cart. If the product is
	 * already in the cart, increases the quantity.
	 * 
	 * @param product The product to add.
	 * @param qty     The quantity to add.
	 */
	@Override
	public void addToCart(SalableProduct product, int qty) {
		productsInCart.put(product, productsInCart.getOrDefault(product, 0) + qty);
	}

	/**
	 * Removes the specified quantity of the product from the cart. If the quantity
	 * to remove is greater than or equal to the current quantity, the product is
	 * removed entirely from the cart.
	 * 
	 * @param product The product to remove.
	 * @param qty     The quantity to remove.
	 */
	@Override
	public void removeFromCart(SalableProduct product, int qty) {
		if (productsInCart.containsKey(product)) {
			int currentQty = productsInCart.get(product);
			if (qty >= currentQty) {
				productsInCart.remove(product);
			} else {
				productsInCart.put(product, currentQty - qty);
			}
		}
	}

	/**
	 * Gets the productsInCart and their quantities in the cart.
	 * 
	 * @return A map of SalableProduct to quantity.
	 */
	@Override
	public Map<SalableProduct, Integer> getproductsInCart() {
		return this.productsInCart;
	}

	/**
	 * Clears all productsInCart from the cart.
	 */
	@Override
	public void clearCart() {
		productsInCart.clear();
	}

	/**
	 * Calculates the total price of all productsInCart in the cart, considering
	 * each product's price and quantity.
	 * 
	 * @return The total price of the cart.
	 */
	@Override
	public double getTotalPrice() {
		double totalPrice = 0.0;
		for (Map.Entry<SalableProduct, Integer> entry : productsInCart.entrySet()) {
			totalPrice += entry.getKey().getPrice() * entry.getValue();
		}
		return totalPrice;
	}

}
