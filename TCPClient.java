package project;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter writer;

	public static void startListening() {
		Thread listeningThread = new Thread(new Runnable() {

			@Override
			public void run() {
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						System.out.println("Server: " + line);
					}
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		});
		listeningThread.start();
	}

	public static void startConnection() {
		try {

			socket = new Socket("192.168.43.165", 6789);
			System.out.println("Connection started");
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			startListening();
			Scanner sc = new Scanner(System.in);
			String line = null;
			while ((line = sc.nextLine()) != null) {
				writer.println(line);
				writer.flush();
			}

		} catch (Exception e) {
			System.out.println("Connection closed");
		}
	}

	public static void main(String argv[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		while (true) {
			if (command.equals("CONNECT")) {
				startConnection();
				break;
			}
			command = sc.nextLine();
		}
	}

}