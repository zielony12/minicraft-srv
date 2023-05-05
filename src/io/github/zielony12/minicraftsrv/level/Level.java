package io.github.zielony12.minicraftsrv.level;

public class Level {
	public int w, h;
	public byte tiles[];
	public byte data[];

	public Level(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void createTopMap() {
		tiles = new byte[w * h];
		data = new byte[w * h];
	
		for(int i = 0; i < 128 * 128; i ++) {
			if(i % 2 == 0)
				tiles[i] = 3;
			else
				tiles[i] = 0;
		}
	}
}
