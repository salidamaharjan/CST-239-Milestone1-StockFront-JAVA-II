package app;

public class Weapon extends SalableProduct implements Comparable<Weapon> {

	public Weapon(String name, String description, double price, int quantity) {
		super(name, description, price, quantity);
	}

	@Override
    public int compareTo(Weapon compareWeapon) {
        return this.getName().compareToIgnoreCase(compareWeapon.getName());
    }
}
