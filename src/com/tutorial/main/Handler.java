package com.tutorial.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<>();
	
	public void tick() {

		for(int i =0; i<object.size(); i++){
			object.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for (GameObject tempObject : object) {
			tempObject.render(g);
		}
	}
	
	public void clearEnemies() {
		for(int i =0; i< object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				object.clear();
				if(Game.gameState != Game.STATE.End)
				addObject(new Player(tempObject.getX(), tempObject.getY(), ID.Player, this));
			}
		}
		
	}
	
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	
}
