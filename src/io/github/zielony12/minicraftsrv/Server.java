package io.github.zielony12.minicraftsrv;

import java.io.IOException;

import io.github.zielony12.minicraftsrv.level.Level;

public class Server {
	protected static Level level;
	public static void main(String[] args) throws IOException {
		level = new Level(128, 128);
		level.createTopMap();
		new ServerThread(1025, "ServerThread").start();
	}
}
