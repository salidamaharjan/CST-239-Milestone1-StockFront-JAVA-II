package app;

import java.util.List;

public interface Inventory {

	void removeSalableProduct(SalableProduct product, int newQuantity);
	void addSalableProduct(SalableProduct product);
	List<SalableProduct> getAllProduct();
	SalableProduct getProductByName(String name);
	
}
