package test;

import org.junit.Before;
import org.junit.Test;

import app.Armor;
import app.InventoryManager;
import app.SalableProduct;
import app.Weapon;

import static org.junit.Assert.*;
import java.util.List;

/**
 * Unit tests for the {@link InventoryManager} class to ensure proper
 * functionality of adding, removing, sorting, and retrieving products in the
 * inventory. The tests cover a variety of scenarios including adding products,
 * removing products, and sorting the inventory by name and price.
 */
public class InventoryManagerTest {

	private InventoryManager inventoryManager;
	private SalableProduct sword;
	private SalableProduct axe;
	private SalableProduct shield;

	/**
	 * Sets up the test environment by initializing the inventory manager and adding
	 * sample products to the inventory. This method is called before each test.
	 */
	@Before
	public void setUp() {
		// Initialize the inventory manager
		inventoryManager = new InventoryManager();

		// Create sample products
		sword = new Weapon("Sword", "Sharp and can swing", 1200.0, 10);
		axe = new Weapon("Axe", "Sharp and pointy", 800.0, 15);
		shield = new Armor("Shield", "Stops things", 1500.0, 30);

		// Add products to the inventory
		inventoryManager.addSalableProduct(sword);
		inventoryManager.addSalableProduct(axe);
		inventoryManager.addSalableProduct(shield);
	}

	/**
	 * Tests adding a valid product to the inventory. Verifies that the product is
	 * added successfully.
	 */
	@Test
	public void testAddSalableProduct() {
		// Add a new product to the inventory
		SalableProduct hammer = new Weapon("Hammer", "Heavy and powerful", 2500.0, 5);
		inventoryManager.addSalableProduct(hammer);

		// Verify the product has been added to the inventory
		List<SalableProduct> products = inventoryManager.getAllProduct();
		assertTrue("Hammer should be added to the inventory", products.contains(hammer));
	}

	/**
	 * Tests adding a null product to the inventory. Verifies that no product is
	 * added and the inventory size remains unchanged.
	 */
	@Test
	public void testAddNullProduct() {
		// Try adding a null product
		inventoryManager.addSalableProduct(null);

		// Verify that no product is added and the inventory size remains the same
		List<SalableProduct> products = inventoryManager.getAllProduct();
		assertEquals("Inventory size should remain the same", 3, products.size());
	}

	/**
	 * Tests removing a quantity of an existing product from the inventory. Verifies
	 * that the product quantity is correctly updated.
	 */
	@Test
	public void testRemoveSalableProduct() {
		// Remove a quantity from a product
		inventoryManager.removeSalableProduct(sword, 5);

		// Verify that the quantity of the sword has been updated
		assertEquals("Sword quantity should be updated to 5", 5, sword.getQuantity());
	}

	/**
	 * Tests removing a product that does not exist in the inventory. Verifies that
	 * no change occurs and the error message is printed.
	 */
	@Test
	public void testRemoveSalableProduct_ProductNotFound() {
		// Try to remove from a product not in inventory (e.g., null)
		inventoryManager.removeSalableProduct(null, 0);

		// Check if the quantity remains unchanged and error is printed (optional)
		assertEquals("Sword quantity should remain unchanged", 10, sword.getQuantity());
	}

	/**
	 * Tests sorting the inventory by product name first and then by price. Verifies
	 * that the inventory is sorted correctly.
	 */
	@Test
	public void testSortByNameThenPrice() {
		// Add unsorted products
		SalableProduct bow = new Weapon("Bow", "Long-range weapon", 900.0, 8);
		SalableProduct helmet = new Armor("Helmet", "Protects head", 300.0, 10);
		inventoryManager.addSalableProduct(bow);
		inventoryManager.addSalableProduct(helmet);

		// Sort the inventory
		inventoryManager.sortByNameThenPrice();

		// Verify that the inventory is sorted by name first, then by price
		List<SalableProduct> sortedProducts = inventoryManager.getAllProduct();
		assertEquals("First product should be Axe", axe, sortedProducts.get(0));
		assertEquals("Second product should be Bow", bow, sortedProducts.get(1));
		assertEquals("Third product should be Helmet", helmet, sortedProducts.get(2));
		assertEquals("Fourth product should be Shield", shield, sortedProducts.get(3));
		assertEquals("Fifth product should be Sword", sword, sortedProducts.get(4));
	}

	/**
	 * Tests retrieving a product from the inventory by its name. Verifies that the
	 * correct product is returned.
	 */

	@Test
	public void testGetProductByName() {
		// Search for a product by name
		SalableProduct foundProduct = inventoryManager.getProductByName("Sword");

		// Verify that the correct product is returned
		assertEquals("The product should be Sword", sword, foundProduct);
	}

	/**
	 * Tests searching for a non-existing product by name. Verifies that no product
	 * is found and returns null.
	 */
	@Test
	public void testGetProductByName_NotFound() {
		// Search for a non-existing product by name
		SalableProduct foundProduct = inventoryManager.getProductByName("Non-existent product");

		// Verify that no product is found
		assertNull("The product should not be found", foundProduct);
	}

	/**
	 * Tests retrieving all products from the inventory. Verifies that all products
	 * are returned correctly.
	 */
	@Test
	public void testGetAllProduct() {
		// Verify that all products are returned
		List<SalableProduct> products = inventoryManager.getAllProduct();
		assertEquals("Inventory should have 3 products", 3, products.size());
		assertTrue("Inventory should contain Sword", products.contains(sword));
		assertTrue("Inventory should contain Axe", products.contains(axe));
		assertTrue("Inventory should contain Shield", products.contains(shield));
	}
}
