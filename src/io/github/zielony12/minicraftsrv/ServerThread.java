package io.github.zielony12.minicraftsrv;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {
	protected DatagramSocket socket;
	private boolean running;
	private int port;

	public ServerThread(int port, String name) throws IOException {
		super(name);
		socket = new DatagramSocket(port);
		this.port = port;
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
						System.out.println("Client asks for tile array");
						buffer = Server.level.tiles;
						break;
					case 1:
						System.out.println("Client asks for data array");
						buffer = Server.level.data;
						break;
					case 2:
						int x = buffer[1];
						int y = buffer[2];
						byte id = buffer[3];
						byte dataVal = buffer[4];
						System.out.println("Client updates a tile on " + x + ", " + y + " from id " + (int) Server.level.tiles[(int) x + (int) y * Server.level.w] + " to id " + (int) id + " and data " + (int) dataVal);
						Server.level.setTile(x, y, id, dataVal);
						break;
				}

				packet = new DatagramPacket(buffer, buffer.length, address, port);
				socket.send(packet);
			} catch(IOException e) {
				e.printStackTrace();
				running = false;
			}
		}
		socket.close();
	}
}
