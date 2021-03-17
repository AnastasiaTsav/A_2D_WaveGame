package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1550691097823471818L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	
	public static boolean paused = false;
	public int diff = 0;
	//0=normal, 1=hard//
	private final Random r;
	private final Handler handler;
	private final HUD hud;
	private final Spawn spawner;
	private final Menu menu;
	
	public enum STATE{
		Menu,
		Select,
		Help,
		Game,
		End
	}

	public static STATE gameState = STATE.Menu;

	public Game() {
		handler = new Handler();	
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler,this));
		this.addMouseListener(menu);
	
		
		new Window(WIDTH, HEIGHT, "Lets Build a Game!", this);


		spawner = new Spawn(handler, hud,this);
		r = new Random();
		
		if(gameState == STATE.Game) {
			handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
			handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		}else {
			for(int i = 0; i < 20; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
				
			}
		}
		
		
	}
	

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop();
		
	}
	
	
	private void tick() {

		if(gameState == STATE.Game) {
			
			if(!paused) {
				handler.tick();
				hud.tick();
				spawner.tick();

				if(HUD.HEALTH <= 0) {
					HUD.HEALTH = 100;
					gameState = STATE.End;
					handler.clearEnemies();
					for(int i = 0; i < 20; i++) {
						handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
					}
				}
			}
			
		}else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select) {
			handler.tick();
			menu.tick();
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);

		if(paused){
			g.setColor(Color.WHITE);
			g.drawString("PAUSED", 100,100);
		}
		
		if(gameState == STATE.Game) {
			hud.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
			menu.render(g);
		}
		

		g.dispose();
		bs.show();
	}
	
	public static int clamp(int y, int min, int max) {
		if(y >= max)
			return max;
		else return Math.max(y, min);
	}
	
	
	public static void main(String[] args) {
		new Game();
	}


}
