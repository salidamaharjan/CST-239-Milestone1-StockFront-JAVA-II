package app;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * The StoreFront class represents a storefront in a retail system, where customers can view and purchase products.
 * It manages the inventory of salable products and a shopping cart for users to add items to their cart.
 * The class initializes the product inventory, either from a JSON file or with default products if the file is missing.
 * @author Salida Maharjan
 * @version 11.0
 * @see InventoryManager
 * @see ShoppingCart
 * @see SalableProduct
 */
public class StoreFront {
	private InventoryManager productInventory;
	private ShoppingCart cart;

	/**
	 * Constructs a new StoreFront instance. Initializes the product inventory with
	 * products from a JSON file or default products if the file cannot be read.
	 */
	public StoreFront() {
		this.productInventory = new InventoryManager();
		this.cart = new ShoppingCart();
		initializeSampleProducts();
	}

	/**
	 * Initializes sample products in the inventory. This method attempts to read a
	 * JSON file to populate the inventory. If reading the file fails (e.g., the
	 * file doesn't exist or is malformed), it adds default products to the
	 * inventory.
	 */
	private void initializeSampleProducts() {
		try {
			File file = new File("Inventory.json");
			ObjectMapper objectMapper = new ObjectMapper();
			SalableProduct[] product = objectMapper.readValue(file, SalableProduct[].class);
			for (int i = 0; i < product.length; i++) {
				productInventory.addSalableProduct(product[i]);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Unable to read saved inventory adding default inventory to the store");
			productInventory.addSalableProduct(new Weapon("Sword", "Sharp and can swing", 1200.0, 10));
			productInventory.addSalableProduct(new Weapon("Axe", "Sharp and pointy", 800.0, 15));
			productInventory.addSalableProduct(new Armor("Sheild", "Stops things", 1500.0, 30));
			productInventory.addSalableProduct(new Armor("Helmet", "Save my head", 150.0, 20));
			productInventory.addSalableProduct(new Health("Health Herb", "Tastes bad but helps", 150.0, 25));
			productInventory.addSalableProduct(new Health("Med Kit", "Life saver", 150.0, 35));
		}
	}

	/**
	 * Gets the product inventory.
	 * 
	 * @return Inventory instance
	 */
	public InventoryManager getProductInventory() {
		return this.productInventory;
	}

	/**
	 * Gets the current shopping cart.
	 * 
	 * @return Cart instance
	 */
	public ShoppingCart viewCart() {
		Map<SalableProduct, Integer> products = cart.getProductsInCart();

		if (products.isEmpty()) {
			System.out.println("Your cart is empty.");
		} else {
			System.out.println("\n------------------------------- Cart Contents -------------------------------");
			// Iterate over the map and print product details
			for (Map.Entry<SalableProduct, Integer> entry : products.entrySet()) {
				SalableProduct product = entry.getKey();
				int quantity = entry.getValue();
				System.out.printf("Name: %s | Description: %s | Price: $%.2f | Quantity: %d\n", product.getName(),
						product.getDescription(), product.getPrice(), quantity);
			}
			System.out.println("-----------------------------------------------------------------------------");
		}

		return cart;
	}

	/**
	 * Adds a new product to the inventory.
	 * 
	 * @param product The SalableProduct to add
	 */
	public void addProductToInventory(SalableProduct product) {
		productInventory.addSalableProduct(product);
	}

	/**
	 * Adds a product to the cart by product name and quantity.
	 * 
	 * @param productName Name of the product
	 * @param qty         Quantity to add
	 */
	public void addToCart(String productName, int qty) {
		SalableProduct product = productInventory.getProductByName(productName);

		if (product != null && product.getQuantity() >= qty) {
			System.out.println("Product added to cart");
			cart.addToCart(product, qty);
			System.out.println(
					"--------------------------------------------------------------------------------------------");
			System.out.println(
					"--------------------------------------------------------------------------------------------");
		} else {
			System.out.println("Product not available in desired quantity.");
			System.out.println(
					"--------------------------------------------------------------------------------------------");
			System.out.println(
					"--------------------------------------------------------------------------------------------");
		}
	}

	/**
	 * Represents the main interface and logic for the store front application.
	 */
	public void removeFromCart(String productName, int qty) {
		SalableProduct product = productInventory.getProductByName(productName);
		// Check if the product exists in the cart
		if (product != null && cart.getProductsInCart().containsKey(product)) {
			int currentQtyInCart = cart.getProductsInCart().get(product);

			// Check if the quantity to remove is valid
			if (qty <= currentQtyInCart) {
				System.out.println("Product removed from cart");
				cart.removeFromCart(product, qty);
				System.out.println(
						"--------------------------------------------------------------------------------------------");
			} else {
				System.out.println("Cannot remove more than the quantity in your cart.");
				System.out.println("You currently have " + currentQtyInCart + " of this product in your cart.");
				System.out.println(
						"--------------------------------------------------------------------------------------------");
			}
		} else {
			System.out.println("Product not found in cart.");
			System.out.println(
					"--------------------------------------------------------------------------------------------");
		}
	}

	/**
	 * Purchases all items in the cart and updates inventory accordingly.
	 */
	public void purchaseFromCart() {
		double totalPrice = cart.getTotalPrice();

		if (totalPrice > 0) {
			System.out.printf("Your total is $%.2f. Proceeding with purchase...\n", totalPrice);

			// Process purchase and update inventory
			Map<SalableProduct, Integer> cartProducts = cart.getProductsInCart();
			for (Map.Entry<SalableProduct, Integer> entry : cartProducts.entrySet()) {
				SalableProduct product = entry.getKey();
				int qtyPurchased = entry.getValue();

				productInventory.removeSalableProduct(product, product.getQuantity() - qtyPurchased);
			}

			// Clear the cart after purchase
			cart.clearCart();
			System.out.println("Purchase completed!");
		} else {
			System.out.println("Your cart is empty, cannot proceed with purchase.");
		}
	}

	/**
	 * Cancels the current purchase and displays the saved cart contents.
	 */
	public void cancelPurchase() {
		Map<SalableProduct, Integer> products = cart.getProductsInCart();
		if (products.isEmpty()) {
			System.out.println("Cart is empty.");
			return;
		} else {
			for (Map.Entry<SalableProduct, Integer> entry : products.entrySet()) {
				System.out.println(entry.getKey().getName() + " x " + entry.getValue());
				System.out.println("Your purchase is cancelled. Cart is saved.");
			}
		}
	}

	/**
	 * Displays the current inventory with product details.
	 */
	public void displayInventory() {
		productInventory.sortByNameThenPrice();

		System.out.println("\n----------------------------- Current Inventory -------------------------------");
		for (SalableProduct product : productInventory.getAllProduct()) {
			System.out.printf("Name: %s | Description: %s | Price: $%.2f | Quantity: %d\n", product.getName(),
					product.getDescription(), product.getPrice(), product.getQuantity());
		}
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
	}

	/**
	 * Displays a welcome message to the user when they enter the store front.
	 */
	public void welcomeToStoreFront() {
		System.out.println(
				"-----------------------------------🛒STORE FRONT🛍️-----------------------------------------");
		System.out.println("Welcome to the Store Front");
		System.out.println(
				"--------------------------------------------------------------------------------------------\n");
	}

	/**
	 * Asks the user to select an action from the main menu and returns the choice.
	 *
	 * @param scnr Scanner object used for input
	 * @return the user's menu choice
	 */
	public static int askUser(Scanner scnr) {
		int choice;

		// Print menu
		System.out.println("What do you want to do?");
		System.out.println("1. View inventory");
		System.out.println("2. Add product to cart");
		System.out.println("3. View Cart");
		System.out.println("4. Purchase item");
		System.out.println("5. Cancel Purchase");
		System.out.println("6. Remove product from cart");
		System.out.println("7. Exit");

		System.out.print("Your Choice: ");
		choice = scnr.nextInt();
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		return choice;
	}

	/**
	 * Main method to run the store front program. Handles the main interaction loop
	 * with the user, including viewing inventory, adding/removing products from the
	 * cart, and handling purchases.
	 *
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) throws IOException {

		StoreFront store = new StoreFront();
		Scanner scnr = new Scanner(System.in);
		int choice = 0;
		int qty = 0;
		String itemName = "";

		// Start the main StoreFront application interface for customers
		store.welcomeToStoreFront();

		// Start the AdminService on a new thread to listen for admin commands on port
		// 9999
		// This allows admin tasks (like updating inventory) to run in the background
		new Thread(new AdminService(store.getProductInventory(), 9999)).start();

		// Main interaction loop
		while (choice != 7) {
			choice = askUser(scnr);

			switch (choice) {
			case 1:
				// Show inventory
				store.displayInventory();
				break;
			case 2:
				// Add product to cart
				System.out.println("Which item do you want to add?");
				System.out.println("1. Sword");
				System.out.println("2. Axe");
				System.out.println("3. Sheild");
				System.out.println("4. Helmet");
				System.out.println("5. Health Herb");
				System.out.println("6. Med Kit");
				System.out.print("Item you chose: ");
				int itemChoice = scnr.nextInt();
				if (itemChoice == 1) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Sword", qty);
				} else if (itemChoice == 2) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Axe", qty);
				} else if (itemChoice == 3) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Sheild", qty);
				} else if (itemChoice == 4) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Helmet", qty);
				} else if (itemChoice == 5) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Health Herb", qty);
				} else if (itemChoice == 6) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Med Kit", qty);
				}
				break;
			case 3:
				// Show cart total price
				System.out.printf("Cart total price: $%.2f\n", store.viewCart().getTotalPrice());
				break;
			case 4:
				store.purchaseFromCart();
				break;
			case 5:
				// Cancel purchase
				store.cancelPurchase();
				break;
			case 6:
				// remove product from cart
				ShoppingCart cart = store.viewCart();
				if (cart.getProductsInCart().isEmpty()) {
					break;
				}
				Map<SalableProduct, Integer> products = cart.getProductsInCart();
				System.out.println("Which product do you want to remove?");
				for (Map.Entry<SalableProduct, Integer> entry : products.entrySet()) {
					SalableProduct product = entry.getKey();
					System.out.println(product.getName());
				}
				System.out.print("Item you chose: ");
				itemName = scnr.next();
				if (itemName.toLowerCase().equals("axe")) {
					System.out.println("How many do you want to remove?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.removeFromCart("Axe", qty);
				} else if (itemName.toLowerCase().equals("sword")) {
					System.out.println("How many do you want to remove?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.removeFromCart("Sword", qty);
				} else if (itemName.toLowerCase().equals("sheild")) {
					System.out.println("How many do you want to remove?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.removeFromCart("Sheild", qty);
				} else if (itemName.toLowerCase().equals("helmet")) {
					System.out.println("How many do you want to remove?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.removeFromCart("Helmet", qty);
				} else if (itemName.toLowerCase().equals("health herb")) {
					System.out.println("How many do you want to remove?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.removeFromCart("Health Herb", qty);
				} else if (itemName.toLowerCase().equals("med kit")) {
					System.out.println("How many do you want to remove?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.removeFromCart("Med Kit", qty);
				}
				break;
			case 7:
				// Exit program
				System.out.println("Thank you for shopping with us!");
				break;
			default:
				// Handle invalid input
				System.out.println("Invalid choice, please try again.");
				break;
			}
		}

		scnr.close(); // Close scanner when done
	}

}
