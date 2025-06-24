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
	public Cart viewCart() {
		return this.cart;
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
	 * Purchases the specified quantity of product. Updates inventory and cart
	 * quantities accordingly.
	 * 
	 * @param productName Name of the product to purchase
	 * @param qty         Quantity to purchase
	 */
	public void purchase(String productName, int qty) {
		SalableProduct product = productInventory.getProductByName(productName);
		if (product != null && product.getQuantity() >= qty) {
			double price = product.getPrice() * qty;
			cart.removeFromCart(product, qty);
			productInventory.updateQuantity(product, product.getQuantity() - qty);
			System.out.println("Your total price is: $" + price);
		} else {
			System.out.println("Not enough quantity in stock.");
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

	public static void main(String[] args) {
		StoreFront store = new StoreFront();

		// Add sample products to inventory
		store.addProductToInventory(new SalableProduct("Laptop", "High-end gaming laptop", 1200.00, 10));
		store.addProductToInventory(new SalableProduct("Phone", "Latest smartphone", 800.00, 15));
		store.addProductToInventory(new SalableProduct("Headphones", "Wireless headphones", 150.00, 30));

		// Show inventory
		store.displayInventory();

		// Add products to cart
		store.addToCart("Laptop", 2);
		store.addToCart("Phone", 1);

		// Show cart total price
		System.out.printf("Cart total price: $%.2f\n", store.viewCart().getTotalPrice());

		// Attempt purchase
		store.purchase("Laptop", 1);

		// Show cart after purchase
		System.out.printf("Cart total price after purchase: $%.2f\n", store.viewCart().getTotalPrice());

		// Show inventory
		store.displayInventory();

		// Cancel purchase
		store.cancelPurchase();

		// Show inventory
		store.displayInventory();
	}
}
