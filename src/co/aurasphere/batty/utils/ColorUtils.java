package co.aurasphere.batty.utils;

import java.awt.Color;
import java.util.Random;

public class ColorUtils {
	
	public final static Color BLUE = new Color(27,161,226);
	
	public final static Color BROWN = new Color(160,80,0);
	
	public final static Color GREEN = new Color(51,153,51);
	
	public final static Color LIME = new Color(162,193,57);
	
	public final static Color MAGENTA = new Color(216,0,115);
	
	public final static Color MANGO = new Color(240,150,9);
	
	public final static Color PINK = new Color(230,113,184);
	
	public final static Color PURPLE = new Color(162,0,255);
	
	public final static Color RED = new Color(229,20,0);
	
	public final static Color TEAL = new Color(0,171,169);
	
	private final static Color[] windowsColors = {BLUE, BROWN, GREEN, LIME, MAGENTA, MANGO, PINK, PURPLE, RED, TEAL};

	public static Color getRandomColor(){
		return windowsColors[new Random().nextInt(windowsColors.length)];
	}

}
