package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.Armor;
import app.FileService;
import app.SalableProduct;
import app.Weapon;

public class FileServiceTest {

	private static final String TEST_FILE = "test_inventory.json";
	private List<SalableProduct> testProducts;

	@Before
	public void setUp() {
		// Create sample products for testing
		SalableProduct sword = new Weapon("Sword", "Sharp and can swing", 1200.0, 10);
		SalableProduct axe = new Weapon("Axe", "Sharp and pointy", 800.0, 15);
		SalableProduct shield = new Armor("Shield", "Stops things", 1500.0, 30);

		// Create a list of test products
		testProducts = Arrays.asList(sword, axe, shield);
	}

	@Test
	public void testSaveToFile_EmptyList() {
		// Save an empty list
		List<SalableProduct> emptyList = Arrays.asList();
		FileService.saveToFile(TEST_FILE, emptyList);

		// Verify that the file is created
		File file = new File(TEST_FILE);
		assertTrue("File should be created", file.exists());

		// Verify that the file is empty (no products)
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<SalableProduct> productsFromFile = Arrays.asList(mapper.readValue(file, SalableProduct[].class));
			assertEquals("Product list should be empty", 0, productsFromFile.size());
		} catch (Exception e) {
			fail("Exception during file reading: " + e.getMessage());
		}
	}

	@Test
	public void testSaveToFile_FileAlreadyExists() {
		// Save the products initially
		FileService.saveToFile(TEST_FILE, testProducts);

		// Ensure the file exists
		File file = new File(TEST_FILE);
		assertTrue("File should be created", file.exists());

		// Save the products again to ensure it overwrites without issues
		FileService.saveToFile(TEST_FILE, testProducts);

		// Verify that the file is still present and contains the same data
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<SalableProduct> productsFromFile = Arrays.asList(mapper.readValue(file, SalableProduct[].class));
			assertEquals("Product list size should match", testProducts.size(), productsFromFile.size());
		} catch (Exception e) {
			fail("Exception during file reading: " + e.getMessage());
		}
	}

	@Test
	public void testSaveToFile_SpecialCharacters() {
		// Create a product with special characters in its name
		SalableProduct productWithSpecialChars = new Weapon("Sword $100", "Special sword", 1500.0, 10);
		List<SalableProduct> productsWithSpecialChars = Arrays.asList(productWithSpecialChars);

		// Save the product with special characters
		FileService.saveToFile(TEST_FILE, productsWithSpecialChars);

		// Verify the file is created and read back the data
		File file = new File(TEST_FILE);
		assertTrue("File should be created", file.exists());

		try {
			ObjectMapper mapper = new ObjectMapper();
			List<SalableProduct> productsFromFile = Arrays.asList(mapper.readValue(file, SalableProduct[].class));
			assertEquals("Product list size should match", productsWithSpecialChars.size(), productsFromFile.size());
			assertEquals("Product name should match", productWithSpecialChars.getName(),
					productsFromFile.get(0).getName());
		} catch (Exception e) {
			fail("Exception during file reading: " + e.getMessage());
		}
	}

}