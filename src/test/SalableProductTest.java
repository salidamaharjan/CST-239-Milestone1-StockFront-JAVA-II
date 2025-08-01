package test;

import org.junit.Before;
import org.junit.Test;

import app.SalableProduct;

import static org.junit.Assert.*;

public class SalableProductTest {

	private SalableProduct product1;
	private SalableProduct product2;

	@Before
	public void setUp() {
		// Initialize products before each test
		product1 = new SalableProduct("Laptop", "A powerful laptop", 999.99, 10);
		product2 = new SalableProduct("Smartphone", "Latest smartphone", 699.99, 20);
	}

	@Test
	public void testDefaultConstructor() {
		// Test the default constructor
		SalableProduct defaultProduct = new SalableProduct();
		assertEquals("Default product name should be empty.", "", defaultProduct.getName());
		assertEquals("Default product description should be empty.", "", defaultProduct.getDescription());
		assertEquals("Default product price should be 0.", 0, defaultProduct.getPrice(), 0.001);
		assertEquals("Default product quantity should be 0.", 0, defaultProduct.getQuantity());
	}

	@Test
	public void testParameterizedConstructor() {
		// Test the parameterized constructor
		assertEquals("Product name should match constructor value.", "Laptop", product1.getName());
		assertEquals("Product description should match constructor value.", "A powerful laptop",
				product1.getDescription());
		assertEquals("Product price should match constructor value.", 999.99, product1.getPrice(), 0.001);
		assertEquals("Product quantity should match constructor value.", 10, product1.getQuantity());
	}

	@Test
	public void testGettersAndSetters() {
		// Test setters and getters
		product1.setName("Gaming Laptop");
		product1.setDescription("High-performance gaming laptop");
		product1.setPrice(1299.99);
		product1.setQuantity(5);

		assertEquals("Product name should be updated.", "Gaming Laptop", product1.getName());
		assertEquals("Product description should be updated.", "High-performance gaming laptop",
				product1.getDescription());
		assertEquals("Product price should be updated.", 1299.99, product1.getPrice(), 0.001);
		assertEquals("Product quantity should be updated.", 5, product1.getQuantity());
	}

	@Test
	public void testCompareToWithDifferentProducts() {
		// Test compareTo method for different products
		SalableProduct product3 = new SalableProduct("Laptop", "A fast laptop", 899.99, 15);
		SalableProduct product4 = new SalableProduct("Laptop", "A fast laptop", 999.99, 15);
		SalableProduct product5 = new SalableProduct("Smartphone", "Latest smartphone", 699.99, 20);

		// Test compare by name
		assertTrue("Product1 should come after Product3 by name.", product1.compareTo(product3) > 0);
		assertTrue("Product1 should come before Product5 by name.", product1.compareTo(product5) < 0);

		// Test compare by price when names are the same
		assertTrue("Product4 should come after Product3 by price.", product4.compareTo(product3) > 0);
	}

	@Test
	public void testCompareToWithEqualProducts() {
		// Test compareTo when products are identical
		SalableProduct identicalProduct = new SalableProduct("Laptop", "A powerful laptop", 999.99, 10);
		assertEquals("Products should be considered equal.", 0, product1.compareTo(identicalProduct));
	}

	@Test
	public void testCompareToWithSameNameDifferentPrice() {
		// Test comparison when names are the same, but prices differ
		SalableProduct product6 = new SalableProduct("Laptop", "A powerful laptop", 799.99, 10);
		assertTrue("Product1 should come after Product6 by price.", product1.compareTo(product6) > 0);
	}

}
