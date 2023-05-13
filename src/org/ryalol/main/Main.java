package org.ryalol.main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main
{

	public static void main(String[] args)
	{
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("GAME");
		//window.setSize(new Dimension(720, 320));
		
		GamePanel panel = new GamePanel(60);
		panel.startGameThread();
		window.add(panel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);


	}

}
