package com.ninjaengine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.Util.Constants;
import com.Util.Time;

public class Window extends JFrame implements Runnable {
	
	public ML mouseListener;
	public KL keyListener;

	private static Window window = null;
	private boolean isRunning = true;
	private Scene currentScene = null;
	private Image doubleBufferImage = null;
	private Graphics doubleBufferGraphics = null;
	
	public Window() {
		this.mouseListener = new ML();
		this.keyListener = new KL();
		
		
		this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		this.setTitle(Constants.SCREEN_TITLE);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addMouseListener(mouseListener);
		this.addKeyListener(keyListener);
		this.addMouseMotionListener(mouseListener);
		this.setLocationRelativeTo(null);
	}
	
	public void init() {
		changeScene(0);
		
	}
	
	public void changeScene(int scene) {
		switch (scene) {
		case 0:
			currentScene = new LevelEditor("Level Editor");
			break;
			default:
				System.out.println("Do not know which Scene");
				currentScene = null;
				break;
		}
	}
	
	public static Window getWindow() {
		if (Window.window == null) {
			Window.window = new Window();
		}
		
		return Window.window;
		
	}
	
	
	public void update(double dt) {
		currentScene.update(dt);
		draw(getGraphics());

	}
	
	public void draw(Graphics g) {
		if (doubleBufferImage == null) {
			doubleBufferImage = createImage(getWidth(), getHeight());
			doubleBufferGraphics = doubleBufferImage.getGraphics();
		}
		
		renderOffscreen(doubleBufferGraphics);
		
		g.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
	}
	
	public void renderOffscreen(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		currentScene.draw(g2);
		
	}
	
	@Override
	public void run() {
		double lastFrameTime = 0.0;
		try {
			while(isRunning) {
				
			double time = Time.getTime();
			double deltaTime = time - lastFrameTime;
			lastFrameTime = time;
			
			update(time);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
