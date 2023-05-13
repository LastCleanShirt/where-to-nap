package org.ryalol.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import org.ryalol.main.GamePanel;
import org.ryalol.main.KeyHandler;

public class Player extends Entity
{
	GamePanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;

	
	public Player(GamePanel gp, KeyHandler keyH)
	{
		
		this.gp = gp;
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		setDefaultValues();
		System.out.println(System.getProperty("user.dir"));
		setSpritePath(System.getProperty("user.dir") + "\\res\\sprites\\cat1.png");
		loadSprite();
		
	}
	
	public void setDefaultValues()
	{
		
		worldX = gp.tileSize * 2;
		worldY = gp.tileSize * 9;
		speed = 4;
	}
	
	public void update()
	{
		if (keyH.upPressed)
		{
			worldY -= speed;
			//setDirection("up");
			if (direction == "left") setFrame(2);
			else if (direction == "right") setFrame(3);
		}
		
		if (keyH.downPressed)
		{
			worldY += speed;
			//setDirection("down");
			if (direction == "left") setFrame(2);
			else if (direction == "right") setFrame(3);
		}
		
		if (keyH.leftPressed)
		{
			worldX -= speed;
			setDirection("left");
			setFrame(2);
		}
		
		if (keyH.rightPressed)
		{
			worldX += speed;
			setDirection("right");
			setFrame(3);
		}
		
		if (keyH.areNoKeysPressed())
		{
			if (direction == "left") setFrame(0);
			if (direction == "right") setFrame(1);
		}
		
	}
	
	public void draw(Graphics2D g2)
	{
		//g2.setColor(Color.WHITE);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		drawSprite(g2, screenX, screenY, gp.tileSize);
	}
	
}
