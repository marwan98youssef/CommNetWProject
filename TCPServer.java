package project;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPServer {
	public static Socket socket;
	public static PrintWriter writer;
	public static BufferedReader reader;

	public static void startListening() {
		Thread listeningThread = new Thread(new Runnable() {

			@Override
			public void run() {
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						System.out.println("Client: " + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		listeningThread.start();
	}

	public static void startConnection() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					ServerSocket server = new ServerSocket(6789);
					System.out.println("Wating for client");
					while (true) {
						try {
							socket = server.accept();
							System.out.println("Connection built");
							reader = new BufferedReader(new InputStreamReader(
									socket.getInputStream()));
							writer = new PrintWriter(new OutputStreamWriter(
									socket.getOutputStream()));
							startListening();
						} catch (Exception e) {

						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();

	}

	public static void main(String argv[]) throws Exception {
		startConnection();
		Scanner sc = new Scanner(System.in);
		String line = null;
		while ((line = sc.nextLine()) != null) {
			writer.println(line);
			writer.flush();
		}
	}
}
