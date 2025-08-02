package test;

import app.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.List;

public class StoreFrontTest {

	private StoreFront store;

	@Before
	public void setUp() {
		// Initialize the StoreFront object before each test
		store = new StoreFront();
	}

	@Test
	public void testInitializeSampleProducts() {
		// Ensure that the default products are added when no file exists
		InventoryManager inventory = store.getProductInventory();
		assertNotNull("Inventory should not be null", inventory);

		List<SalableProduct> products = inventory.getAllProduct();
		assertTrue("There should be at least 6 products", products.size() >= 6);
	}

	@Test
	public void testAddProductToInventory() {
		// Create a new product
		SalableProduct newProduct = new Weapon("Bow", "Long range weapon", 500.0, 10);
		store.addProductToInventory(newProduct);

		// Verify the product was added to the inventory
		InventoryManager inventory = store.getProductInventory();
		SalableProduct fetchedProduct = inventory.getProductByName("Bow");
		assertNotNull("New product should be in inventory", fetchedProduct);
		assertEquals("Product name should be Bow", "Bow", fetchedProduct.getName());
	}

	@Test
	public void testAddToCart() {
		store.addToCart("Sword", 2);

		InventoryManager inventory = store.getProductInventory();
		SalableProduct swordInInventory = inventory.getProductByName("Sword");

		ShoppingCart cart = store.viewCart();
		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();

		// Ensure Sword has been added with the correct quantity
		assertTrue("Cart should contain the Sword", productsInCart.containsKey(swordInInventory));
		assertEquals("Quantity in cart should be 2", Integer.valueOf(2), productsInCart.get(swordInInventory));
	}

	@Test
	public void testRemoveFromCart() {
		store.addToCart("Axe", 3);

		InventoryManager inventory = store.getProductInventory();
		SalableProduct axeInInventory = inventory.getProductByName("Axe");

		store.removeFromCart("Axe", 2);

		ShoppingCart cart = store.viewCart();
		Map<SalableProduct, Integer> productsInCart = cart.getProductsInCart();

		// Verify that the remaining quantity is correct (1 in this case)
		assertTrue("Cart should still contain Axe", productsInCart.containsKey(axeInInventory));
		assertEquals("Quantity in cart should be 1", Integer.valueOf(1), productsInCart.get(axeInInventory));
	}

	@Test
	public void testPurchaseFromCart() {
		// Add products to the cart
		store.addToCart("Sword", 2);
		store.addToCart("Axe", 1);

		// Capture the current total price before purchase
		ShoppingCart cart = store.viewCart();
		double prePurchaseTotal = cart.getTotalPrice();

		// Purchase the items from the cart
		store.purchaseFromCart();

		// After purchase, the cart should be empty, and inventory should be updated
		assertEquals("Cart should be empty after purchase", 0, cart.getProductsInCart().size());
		assertTrue("Total price should have been deducted from inventory", prePurchaseTotal > 0);
	}

	@Test
	public void testCancelPurchase() {
		// Add products to the cart
		store.addToCart("Sword", 2);
		store.addToCart("Axe", 1);

		// Cancel the purchase
		store.cancelPurchase();

		// Verify that cart is not empty but the purchase has been canceled
		ShoppingCart cart = store.viewCart();
		assertTrue("Cart should contain products after canceling", cart.getProductsInCart().size() > 0);
	}

	@Test
	public void testDisplayInventory() {
		// Display inventory (this is typically more of a UI function, but we can check
		// the output in some way)
		store.displayInventory();

		// Test that the inventory has at least the default products
		InventoryManager inventory = store.getProductInventory();
		List<SalableProduct> products = inventory.getAllProduct();
		assertTrue("Inventory should contain products", products.size() >= 6);
	}

}
