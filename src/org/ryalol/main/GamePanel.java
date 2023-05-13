package org.ryalol.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import org.ryalol.entity.Player;

import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable
{
	
	// TILE SETTINGS
	public final int originalTileSize = 16;
	public final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48 tile size

	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	// SCREEN SETTINGS
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768
	public final int screenHeight = tileSize * maxScreenRow; // 576
	
	
	// FPS SETTINGS
    private boolean isRunning = true;
    private int maxFPS;
    private long targetTime;
    private double fps;
    
    KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this, keyH);
	TileManager tileM = new TileManager(this);
	
	// Player default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel(int maxFPS)
	{
        this.maxFPS = maxFPS;
        this.targetTime = 1000 / maxFPS;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
		tileM.loadTileImage();
	}

    @Override
    public void run() {
        long startTime, elapsedTime, waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTimeNano = targetTime * 1_000_000;  // Convert targetTime to nanoseconds
        
        while (isRunning) {
            startTime = System.nanoTime();
            
            update();  // Update game logic
            render();  // Render game graphics
            
            elapsedTime = System.nanoTime() - startTime;
            waitTime = targetTimeNano - elapsedTime;
            
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime / 1_000_000, (int) (waitTime % 1_000_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // Calculate and print current FPS
            frameCount++;
            totalTime += System.nanoTime() - startTime;
            if (frameCount == maxFPS) {
                double averageTime = (double) totalTime / frameCount;
                fps = 1_000_000_000 / averageTime;
                
                //System.out.println("FPS: " + fps);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
    
    public int getFps()
    {
    	return Math.round(maxFPS);
    }
	
	public void update()
	{
		//System.out.println(getFps());
		
		player.update();

		//sSystem.out.println("HI");
	}
	
	public void render()
	{
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    
	    Graphics2D g2 = (Graphics2D) g;

	    // Clear the background
	    g2.setColor(Color.BLACK);
	    g2.fillRect(0, 0, getWidth(), getHeight());

	    // Render game graphics
	    tileM.draw(g2);
	    player.draw(g2);
	    g2.dispose();

	}
}
