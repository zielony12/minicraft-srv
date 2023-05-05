package io.github.zielony12.minicraftsrv;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {
	protected DatagramSocket socket;
	private boolean running;
	private int port;
	private static final String[] responses = { "Hello!", "I'm an UDP server!", "Wassup?" };
	private int iterator;

	public ServerThread(int port, String name) throws IOException {
		super(name);
		socket = new DatagramSocket(port);
		this.port = port;

		iterator = 0;
	}

	public void run() {
		running = true;
		while(running) {
			try {
				byte[] buffer = new byte[Server.level.w * Server.level.h];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				switch(buffer[0]) {
					case 0:
						System.out.println("Client asks for map");
						buffer = Server.level.tiles;
						//TODO: load map to buffer
						break;
					case 1:
						System.out.println("Client asks for entities");
						//TODO: load entities to buffer
						break;
					case 2:
						int x = buffer[1];
						int y = buffer[2];
						int id = buffer[3];
						switch(id) {
							case 3: // flower (breaks instantly)
								System.out.println("Client breaks a tile on " + x + ", " + y + " of id " + id);
								
								//TODO: move tiles operations to Level class
								Server.level.tiles[x + y * Server.level.w] = 0; // to grass
								break;
						}
						//TODO: receive tile info
						break;
				}

				packet = new DatagramPacket(buffer, buffer.length, address, port);
				socket.send(packet);
				setNext();
			} catch(IOException e) {
				e.printStackTrace();
				running = false;
			}
		}
		socket.close();
	}
	
	public void setNext() {
		if(iterator >= responses.length-1)
			iterator = 0;
		else
			iterator ++;
	}
}
