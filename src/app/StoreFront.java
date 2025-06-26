package app;

import java.util.Map;
import java.util.Scanner;

/**
 * Represents the Store Front, managing Inventory and Shopping Cart.
 */
public class StoreFront {
	private Inventory productInventory;
	private Cart cart;
	Scanner scnr = new Scanner(System.in);

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
//	public Cart viewCart() {
//		return this.cart;
//	}

	public Cart viewCart() {
	    Map<SalableProduct, Integer> products = cart.getProducts();
	    
	    if (products.isEmpty()) {
	        System.out.println("Your cart is empty.");
	    } else {
	        System.out.println("\n--- Cart Contents ---");
	        // Iterate over the map and print product details
	        for (Map.Entry<SalableProduct, Integer> entry : products.entrySet()) {
	            SalableProduct product = entry.getKey();
	            int quantity = entry.getValue();
	            System.out.printf("Name: %s | Description: %s | Price: $%.2f | Quantity: %d\n", 
	                product.getName(), product.getDescription(), product.getPrice(), quantity);
	        }
	        System.out.println("---------------------\n");
	    }

	    return cart;  // Return the cart object itself
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
			cart.addToCart(product, qty);
		} else {
			System.out.println("Product not available in desired quantity.");
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
		System.out.println("Your purchase is cancelled. Cart is saved.");
		System.out.println("Cart Contents:");
		Map<SalableProduct, Integer> products = cart.getProducts();
		if (products.isEmpty()) {
			System.out.println("Cart is empty.");
		} else {
			for (Map.Entry<SalableProduct, Integer> entry : products.entrySet()) {
				System.out.println(entry.getKey().getName() + " x " + entry.getValue());
			}
		}
	}

	/**
	 * Displays the current inventory with product details.
	 */
	public void displayInventory() {
		System.out.println("\n--- Current Inventory ---");
		for (SalableProduct product : productInventory.getAllProduct()) {
			System.out.printf("Name: %s | Description: %s | Price: $%.2f | Quantity: %d\n", product.getName(),
					product.getDescription(), product.getPrice(), product.getQuantity());
		}
		System.out.println("-------------------------\n");
	}

	public static int askUser() {
		Scanner scnr = new Scanner(System.in);
		int choice;

		// Print menu
		System.out.println("What do you want to do?");
		System.out.println("1. View inventory");
		System.out.println("2. Add product to cart");
		System.out.println("3. View Cart");
		System.out.println("4. Purchase item");
		System.out.println("5. Cancel Purchase");
		System.out.println("6. Exit");

		System.out.print("Your Choice: ");
		choice = scnr.nextInt();

		return choice;
	}

	public static void main(String[] args) {
		StoreFront store = new StoreFront();
		Scanner scnr = new Scanner(System.in);
		int choice = 0;

		// Add sample products to inventory
		store.addProductToInventory(new SalableProduct("Laptop", "High-end gaming laptop", 1200.00, 10));
		store.addProductToInventory(new SalableProduct("Phone", "Latest smartphone", 800.00, 15));
		store.addProductToInventory(new SalableProduct("Headphones", "Wireless headphones", 150.00, 30));

		// Main interaction loop
		while (choice != 6) {
			choice = askUser();

			switch (choice) {
			case 1:
				// Show inventory
				store.displayInventory();
				break;
			case 2:
				// Add product to cart
				System.out.println("Which item?");
				System.out.println("1. Laptop");
				System.out.println("2. Phone");
				System.out.println("3. Headphones");
				System.out.print("Your Choice: ");
				int itemChoice = scnr.nextInt();
				if (itemChoice == 1) {
					store.addToCart("Laptop", 1);
				} else if (itemChoice == 2) {
					store.addToCart("Phone", 1);
				} else if (itemChoice == 3) {
					store.addToCart("Headphones", 1);
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
