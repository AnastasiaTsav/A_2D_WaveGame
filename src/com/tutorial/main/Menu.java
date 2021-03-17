package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter{
	
	private final Game game;
	private final Handler handler;
	private final Random r = new Random();
	private final HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.hud = hud;
		this.handler = handler;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Menu) {
			//play button
			if(mouseOver(mx, my, 150)) {
				//game.gameState = STATE.Game;
				//handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32, ID.Player, handler));
				//handler.clearEnemies();
				//handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
				Game.gameState = STATE.Select;

				return;
				
			}
			
			//help button
			if(mouseOver(mx, my, 250)) {
				Game.gameState = STATE.Help;
				
			}

			
					
			//quit button
			if(mouseOver(mx, my, 350)) {
				System.exit(1);
			}
		}
		if(Game.gameState == STATE.Select) {
			//normal button
			if(mouseOver(mx, my, 150)) {
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));

				game.diff = 0;

			}

			//hard button
			if(mouseOver(mx, my, 250)) {
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.HardEnemy, handler));

				game.diff = 1;

			}

			//back button
			if(mouseOver(mx, my, 350)) {
				Game.gameState = STATE.Menu;
				return;
			}
		}




		//back button
		if(Game.gameState == STATE.Help) {
			if(mouseOver(mx, my, 350)) {
				Game.gameState = STATE.Menu;
				return;
			}
		}
		
		//back button
		if(Game.gameState == STATE.End) {
			if(mouseOver(mx, my, 350)) {
				Game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
			}
		}
		
	}
	
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int y) {
		if(mx > 210 && mx < 210 + 200) {
			return my > y && my < y + 64;
		}else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		if(Game.gameState == STATE.Menu) {
			Font fnt = new Font("arial", Font.BOLD, 50);
			Font fnt2 = new Font("arial", Font.BOLD, 30);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Wave", 240, 70);
			
			
			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 270, 190);
			
			g.drawRect(210, 250, 200, 64);
			g.drawString("Help", 270, 290);
			
			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 270, 390);
		}else if(Game.gameState == STATE.Help) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", Font.BOLD, 30);
			Font fnt3 = new Font("arial", Font.BOLD, 20);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Help", 240, 70);
			
			g.setFont(fnt3);
			g.drawString("Use WASD keys to move player and dodge enemies", 50, 200);
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		}else if(Game.gameState == STATE.End) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Game Over", 180, 70);
			
			g.setFont(fnt3);
			g.drawString("You lost with a score of: " + hud.getScore(), 175, 200);
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Try again ", 245, 390);
		}else if(Game.gameState == STATE.Select) {
			Font fnt = new Font("arial", Font.BOLD, 50);
			Font fnt2 = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("DIFFICULTY", 180, 70);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Normal", 270, 190);

			g.drawRect(210, 250, 200, 64);
			g.drawString("Hard", 270, 290);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		}






	}
	
	
}
