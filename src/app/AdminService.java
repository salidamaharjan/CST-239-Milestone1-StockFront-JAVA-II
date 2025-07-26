package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AdminService listens on a network port and handles admin commands.
 * 
 * It supports: - "U": Update inventory with a JSON list of products - "R":
 * Retrieve current inventory as JSON
 */
public class AdminService implements Runnable {

	private InventoryManager inventoryManager;
	private int port;

	/**
	 * Creates an AdminService with a given inventory and port number.
	 *
	 * @param inventoryManager the inventory manager to use
	 * @param port             the port to listen on
	 */
	public AdminService(InventoryManager inventoryManager, int port) {
		this.inventoryManager = inventoryManager;
		this.port = port;
	}

	/**
	 * Starts the service and waits for client connections.
	 */
	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("AdminService is running on port " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();

				new Thread(() -> handleClient(clientSocket)).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles one client request.
	 *
	 * @param socket the client socket
	 */
	private void handleClient(Socket socket) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

			String command = in.readLine();
			ObjectMapper mapper = new ObjectMapper();

			switch (command) {
			case "U":
				// Read next line: JSON payload
				String jsonPayload = in.readLine();
				SalableProduct[] products = mapper.readValue(jsonPayload, SalableProduct[].class);

				for (SalableProduct p : products) {
					inventoryManager.addSalableProduct(p);
				}

				FileService.saveToFile("Inventory.json", inventoryManager.getAllProduct());
				out.write("Inventory updated successfully\n");
				out.flush();
				break;

			case "R":
				List<SalableProduct> inventory = inventoryManager.getAllProduct();
				String json = mapper.writeValueAsString(inventory);
				out.write(json + "\n");
				out.flush();
				break;

			default:
				out.write("Unknown command\n");
				out.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
