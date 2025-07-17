package app;

/**
 * The {@code Weapon} class represents a weapon item that extends the
 * {@code SalableProduct} class. It implements the {@code Comparable} interface
 * to allow comparing weapons based on their names. A {@code Weapon} object
 * contains details like name, description, price, and quantity. It can also be
 * compared with other {@code Weapon} objects to determine their order based on
 * name.
 * 
 * @author Salida Maharjan
 * @version 11.0
 * @since 2025-07-05
 */
public class Weapon extends SalableProduct implements Comparable<SalableProduct> {

	/**
	 * Constructs a new {@code Weapon} object with the specified name, description,
	 * price, and quantity.
	 * 
	 * @param name        The name of the weapon.
	 * @param description A brief description of the weapon.
	 * @param price       The price of the weapon.
	 * @param quantity    The quantity of the weapon available for sale.
	 */
	public Weapon(String name, String description, double price, int quantity) {
		super(name, description, price, quantity);
	}

	/**
	 * Compares this {@code Weapon} object to another {@code Weapon} based on their
	 * names. The comparison is case-insensitive.
	 * 
	 * @param compareWeapon The weapon to be compared.
	 * @return A negative integer, zero, or a positive integer as this weapon's name
	 *         is less than, equal to, or greater than the specified weapon's name,
	 *         respectively.
	 */
	@Override
	public int compareTo(SalableProduct compareWeapon) {
		return this.getName().compareToIgnoreCase(compareWeapon.getName());
	}
}
