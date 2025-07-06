package app;

import java.util.List;

/**
 * The {@code Inventory} interface defines the operations for managing a collection of salable products.
 * It allows adding, removing, and retrieving products from the inventory.
 * 
 * <p>This interface is intended to be implemented by classes responsible for maintaining an inventory of products in a system.</p>
 * 
 * @author Salida Maharjan
 * @version 11.0
 * @since 2025-07-05
 */
public interface Inventory {

	void removeSalableProduct(SalableProduct product, int newQuantity);
	void addSalableProduct(SalableProduct product);
	List<SalableProduct> getAllProduct();
	SalableProduct getProductByName(String name);
	
}
