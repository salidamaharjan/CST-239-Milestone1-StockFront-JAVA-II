package app;

import java.util.Map;

public interface Cart {

	void addToCart(SalableProduct product, int qty);
	void removeFromCart(SalableProduct product, int qty);
	Map<SalableProduct, Integer> getproductsInCart();
	void clearCart();
	
}
