package test;

import org.junit.Before;
import org.junit.Test;

import app.SalableProduct;
import app.ShoppingCart;

import static org.junit.Assert.*;
import java.util.Map;

public class ShoppingCartTest {

	private ShoppingCart cart;
	private SalableProduct product1;
	private SalableProduct product2;

	@Before
	public void setUp() {
		// Create test products
		product1 = new SalableProduct("Laptop", "A powerful laptop", 999.99, 10);
		product2 = new SalableProduct("Smartphone", "Latest smartphone", 699.99, 20);

		// Initialize the shopping cart
		cart = new ShoppingCart();
	}

	@Test
	public void testAddToCart() {
		// Test adding products to the cart
		cart.addToCart(product1, 2);
		cart.addToCart(product2, 3);

		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();

		// Check if products are correctly added with the right quantity
		assertEquals("Cart should contain 2 units of Laptop", Integer.valueOf(2), productsInCart.get(product1));
		assertEquals("Cart should contain 3 units of Smartphone", Integer.valueOf(3), productsInCart.get(product2));
	}

	@Test
	public void testAddToCartWithExistingProduct() {
		// Test adding an existing product to the cart (should update quantity)
		cart.addToCart(product1, 2);
		cart.addToCart(product1, 3); // Adding 3 more units

		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();

		// Check that quantity is updated correctly
		assertEquals("Cart should contain 5 units of Laptop", Integer.valueOf(5), productsInCart.get(product1));
	}

	@Test
	public void testRemoveFromCart() {
		// Test removing products from the cart
		cart.addToCart(product1, 5);
		cart.removeFromCart(product1, 3);

		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();

		// Check that the quantity has been reduced
		assertEquals("Cart should contain 2 units of Laptop after removal", Integer.valueOf(2),
				productsInCart.get(product1));
	}

	@Test
	public void testRemoveProductCompletely() {
		// Test removing a product completely from the cart
		cart.addToCart(product1, 5);
		cart.removeFromCart(product1, 5); // Remove all 5 units

		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();

		// Check that the product is completely removed
		assertFalse("Cart should not contain Laptop after complete removal", productsInCart.containsKey(product1));
	}

	@Test
	public void testClearCart() {
		// Test clearing the cart
		cart.addToCart(product1, 5);
		cart.addToCart(product2, 3);

		cart.clearCart();

		// Check that the cart is empty
		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();
		assertTrue("Cart should be empty after clear", productsInCart.isEmpty());
	}

	@Test
	public void testGetTotalPrice() {
		// Test calculating the total price of the cart
		cart.addToCart(product1, 2);
		cart.addToCart(product2, 3);

		double expectedTotal = (2 * 999.99) + (3 * 699.99);
		assertEquals("Total price should be correct", expectedTotal, cart.getTotalPrice(), 0.001);
	}

	@Test
	public void testGetTotalPriceWithEmptyCart() {
		// Test getting total price for an empty cart
		double expectedTotal = 0.0;
		assertEquals("Total price should be 0.0 for empty cart", expectedTotal, cart.getTotalPrice(), 0.001);
	}
}