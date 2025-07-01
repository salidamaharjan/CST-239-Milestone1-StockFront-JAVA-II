package app;

import java.util.Map;
import java.util.Scanner;

/**
 * Represents a store front where users can interact with the inventory, add
 * products to the shopping cart, and make purchases.
 * <p>
 * The StoreFront class handles all operations related to viewing and managing
 * the inventory, managing the shopping cart, and processing purchases. It
 * interacts with other components like Inventory and Cart classes.
 * </p>
 *
 * <p>
 * This class provides a user-interactive interface through the console for a
 * simulated e-commerce experience.
 * </p>
 * 
 * @author Salida Maharjan
 * @version 11.0
 * @see Inventory
 * @see Cart
 * @see SalableProduct
 */
public class StoreFront {
	private Inventory productInventory;
	private Cart cart;

	/**
	 * Constructs a new StoreFront with empty inventory and cart.
	 */
	public StoreFront() {
		this.productInventory = new Inventory();
		this.cart = new Cart();
	}

	/**
	 * Gets the product inventory.
	 * 
	 * @return Inventory instance
	 */
	public Inventory getProductInventory() {
		return this.productInventory;
	}

	/**
	 * Gets the current shopping cart.
	 * 
	 * @return Cart instance
	 */
	public Cart viewCart() {
		Map<SalableProduct, Integer> products = cart.getProducts();

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
		productInventory.addProduct(product);
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
	    if (product != null && cart.getProducts().containsKey(product)) {
	        int currentQtyInCart = cart.getProducts().get(product);

	        // Check if the quantity to remove is valid
	        if (qty <= currentQtyInCart) {
	            System.out.println("Product removed from cart");
	            cart.removeFromCart(product, qty);
	            System.out.println("--------------------------------------------------------------------------------------------");
	        } else {
	            System.out.println("Cannot remove more than the quantity in your cart.");
	            System.out.println("You currently have " + currentQtyInCart + " of this product in your cart.");
	            System.out.println("--------------------------------------------------------------------------------------------");
	        }
	    } else {
	        System.out.println("Product not found in cart.");
	        System.out.println("--------------------------------------------------------------------------------------------");
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
			Map<SalableProduct, Integer> cartProducts = cart.getProducts();
			for (Map.Entry<SalableProduct, Integer> entry : cartProducts.entrySet()) {
				SalableProduct product = entry.getKey();
				int qtyPurchased = entry.getValue();

				// Update the inventory: Reduce stock based on the quantity purchased
				productInventory.updateQuantity(product, product.getQuantity() - qtyPurchased);
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
		Map<SalableProduct, Integer> products = cart.getProducts();
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
     * with the user, including viewing inventory, adding/removing products from the cart,
     * and handling purchases.
     *
     * @param args Command line arguments (not used)
     */
	public static void main(String[] args) {
		StoreFront store = new StoreFront();
		Scanner scnr = new Scanner(System.in);
		int choice = 0;
		int qty = 0;
		String itemName = "";
		// Add sample products to inventory
		store.addProductToInventory(new Weapon("Axe", "Sharp and can swing", 1200.00, 10));
		store.addProductToInventory(new Weapon("Sword", "Sharp and pointy", 800.00, 15));
		store.addProductToInventory(new Armor("Sheild", "Stops things", 1500, 30));
		store.addProductToInventory(new Armor("Helmet", "Save my head", 150.00, 20));
		store.addProductToInventory(new Health("Health Herb", "Tastes bad but helps", 150.00, 25));
		store.addProductToInventory(new Health("Med Kit", "Life saver", 150.00, 35));

		store.welcomeToStoreFront();

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
				System.out.println("1. Axe");
				System.out.println("2. Sword");
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
					store.addToCart("Axe", qty);
				} else if (itemChoice == 2) {
					System.out.println("How many do you want to add?");
					System.out.print("Entered Quantity: ");
					qty = scnr.nextInt();
					store.addToCart("Sword", qty);
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
				Cart cart = store.viewCart();
				if(cart.getProducts().isEmpty()) {
					break;
				}
				Map<SalableProduct, Integer> products = cart.getProducts();
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
