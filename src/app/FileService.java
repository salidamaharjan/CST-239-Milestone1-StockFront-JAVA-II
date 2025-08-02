package app;

import java.util.List;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * FileService handles saving a list of products to a JSON file.
 */
public class FileService {

	/**
	 * Saves the given list of SalableProduct objects to a JSON file.
	 *
	 * @param filename the name of the file to save to
	 * @param products the list of products to be saved
	 */
	public static void saveToFile(String filename, List<SalableProduct> products) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), products);
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}
}
