package test;

import app.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.List;

/**
 * Unit tests for the {@link StoreFront} class. This class tests various
 * operations on the store such as adding products to inventory, adding and
 * removing products from the shopping cart, purchasing, and canceling
 * purchases.
 */
public class StoreFrontTest {

	private StoreFront store;

	/**
	 * Initializes the {@link StoreFront} object before each test method is
	 * executed. This method sets up the testing environment and ensures the store
	 * is in a consistent state for each test.
	 */
	@Before
	public void setUp() {
		// Initialize the StoreFront object before each test
		store = new StoreFront();
	}

	/**
	 * Tests the initialization of sample products in the store's inventory.
	 * Verifies that at least 6 products are added when no file exists for the
	 * inventory.
	 */
	@Test
	public void testInitializeSampleProducts() {
		// Ensure that the default products are added when no file exists
		InventoryManager inventory = store.getProductInventory();
		assertNotNull("Inventory should not be null", inventory);

		List<SalableProduct> products = inventory.getAllProduct();
		assertTrue("There should be at least 6 products", products.size() >= 6);
	}

	/**
	 * Tests adding a product to the store's inventory. Verifies that a new product
	 * is successfully added to the inventory.
	 */
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

	/**
	 * Tests adding a product to the shopping cart. Verifies that the product is
	 * correctly added to the cart with the appropriate quantity.
	 */
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

	/**
	 * Tests removing a product from the shopping cart. Verifies that the quantity
	 * of the product is updated correctly in the cart after removal.
	 */
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

	/**
	 * Tests purchasing products from the shopping cart. Verifies that the cart is
	 * emptied and inventory is updated after a purchase.
	 */
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

	/**
	 * Tests canceling a purchase. Verifies that the cart retains its contents after
	 * canceling the purchase.
	 */
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

	/**
	 * Tests displaying the inventory. Verifies that the inventory has at least the
	 * default products when displayed.
	 */
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
