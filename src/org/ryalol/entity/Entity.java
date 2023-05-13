package org.ryalol.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.ryalol.main.SpriteSheetLoader;

public class Entity
{
	public int worldX, worldY;
	public int speed;
	public String direction = "left";
	public String spritePath;
	public int FRAME_SIZE = 16;
	
	private int delay = 10;
	private int current = 0;
	
	SpriteSheetLoader ssl;
	BufferedImage[][] frames;
	SpriteSheetLoader loader;
	BufferedImage[] rowFrames;
	
	protected int currentFrame;
	
	public void setSpritePath(String path)
	{
		spritePath = path;
	}
	
	public void loadSprite()
	{
		try
		{
			
			loader = new SpriteSheetLoader(spritePath, FRAME_SIZE);
			frames = loader.getFrames();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void drawSprite(Graphics2D g2, int x, int y, int size)
	{
		//BufferedImage resizedImage = new BufferedImage(size, size, rowFrames[currentFrame].getType());
		//System.out.println(direction);
		if (rowFrames == null) setFrame(0);
		if (currentFrame >= rowFrames.length) currentFrame = 0;
		

		g2.drawImage(rowFrames[currentFrame], x, y, size, size, null);
		/*if (current == delay)
		{
			current = 0;			
			currentFrame += 1;
		}
		current++;*/
	}
	
	public void setDirection(String dir_)
	{
		direction = dir_;
	}
	
	protected void setFrame(int frame)
	{
		rowFrames = loader.playRow(frame); 
	}
	
}
