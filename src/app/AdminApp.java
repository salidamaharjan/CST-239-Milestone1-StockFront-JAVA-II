package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * AdminApp is a client application that connects to the AdminService server.
 * 
 * It allows an adminUser to: - Send "U" command to update inventory with JSON
 * input - Send "R" command to retrieve the current inventory - Send "Q" to quit
 * the application
 */
public class AdminApp {

	/**
	 * Entry point for the AdminApp.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		final String SERVER = "localhost";
		final int PORT = 9999;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Admin Application");

		try {
			while (true) {
				try (Socket socket = new Socket(SERVER, PORT);
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

					System.out.println("\nEnter Command (U to update, R to retrieve, Q to quit):");
					String command = scanner.nextLine().trim().toUpperCase();

					if (command.equals("Q")) {
						System.out.println("Exiting Admin Application...");
						break;
					}

					out.write(command + "\n");
					out.flush();

					if (command.equals("U")) {
						System.out.println("Enter JSON of products to add:");
						String jsonPayload = scanner.nextLine();
						out.write(jsonPayload + "\n");
						out.flush();

						String response = in.readLine();
						System.out.println("Server: " + response);

					} else if (command.equals("R")) {
						String response = in.readLine();
						System.out.println("Inventory Received:\n" + response);

					} else {
						System.out.println("Unknown command. Please enter U, R, or Q.");
					}

				} catch (IOException e) {
					System.out.println("Connection failed: " + e.getMessage());
				}
			}
		} finally {
			scanner.close();
		}
	}
}
