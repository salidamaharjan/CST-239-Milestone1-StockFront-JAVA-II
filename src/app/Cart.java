package app;

import java.util.Map;

/**
 * The {@code Cart} interface defines the operations that a shopping cart should support.
 * It provides methods for adding and removing products, retrieving the products in the cart, 
 * clearing the cart, and calculating the total price of the products in the cart.
 * 
 * <p>This interface is intended to be implemented by classes that represent shopping carts in an e-commerce system.</p>
 * 
 * @author Salida Maharjan
 * @version 11.0
 * @since 2025-07-05
 */
public interface Cart {

	void addToCart(SalableProduct product, int qty);
	void removeFromCart(SalableProduct product, int qty);
	Map<SalableProduct, Integer> getProductsInCart();
	void clearCart();
	double getTotalPrice();
	
}
