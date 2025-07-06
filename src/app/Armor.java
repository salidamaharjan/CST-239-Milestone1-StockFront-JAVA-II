package app;

/**
 * The {@code Armor} class represents an armor item that extends the
 * {@code SalableProduct} class. It encapsulates details such as name,
 * description, price, and quantity of the armor.
 * 
 * @author Salida Maharjan
 * @version 11.0
 * @since 2025-07-05
 */
public class Armor extends SalableProduct {

	/**
	 * Constructs a new {@code Armor} object with the specified name, description,
	 * price, and quantity.
	 * 
	 * @param name        The name of the armor.
	 * @param description A brief description of the armor.
	 * @param price       The price of the armor.
	 * @param quantity    The quantity of the armor available for sale.
	 */
	public Armor(String name, String description, double price, int quantity) {
		super(name, description, price, quantity);
	}

}
