package app;

/**
 * The {@code Health} class represents a health-related product that extends the {@code SalableProduct} class.
 * It encapsulates details like name, description, price, and quantity of the health product.
 * 
 * <p>Instances of this class can represent various health-related products such as potions, supplements, 
 * or other items intended to improve health in an e-commerce or inventory system.</p>
 * 
 * @author Salida Maharjan
 * @version 11.0
 * @since 2025-07-05
 */
public class Health extends SalableProduct {

	/**
     * Constructs a new {@code Health} object with the specified name, description, price, and quantity.
     * 
     * @param name The name of the health product.
     * @param description A brief description of the health product.
     * @param price The price of the health product.
     * @param quantity The quantity of the health product available for sale.
     */
	public Health(String name, String description, double price, int quantity) {
		super(name, description, price, quantity);
	}

}
