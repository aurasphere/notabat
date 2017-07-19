package co.aurasphere.notabat.utils;

import java.awt.Color;
import java.util.Random;

/**
 * Utility class for color constants.
 * 
 * @author Donato Rimenti
 * 
 */
public class ColorUtils {

	/**
	 * The BLUE color.
	 */
	public final static Color BLUE = new Color(27, 161, 226);

	/**
	 * The BROWN color.
	 */
	public final static Color BROWN = new Color(160, 80, 0);

	/**
	 * The GREEN color.
	 */
	public final static Color GREEN = new Color(51, 153, 51);

	/**
	 * The LIME color.
	 */
	public final static Color LIME = new Color(162, 193, 57);

	/**
	 * The MAGENTA color.
	 */
	public final static Color MAGENTA = new Color(216, 0, 115);

	/**
	 * The MANGO color.
	 */
	public final static Color MANGO = new Color(240, 150, 9);

	/**
	 * The PINK color.
	 */
	public final static Color PINK = new Color(230, 113, 184);

	/**
	 * The PURPLE color.
	 */
	public final static Color PURPLE = new Color(162, 0, 255);

	/**
	 * The RED color.
	 */
	public final static Color RED = new Color(229, 20, 0);

	/**
	 * The TEAL color.
	 */
	public final static Color TEAL = new Color(0, 171, 169);

	/**
	 * All of the above colors put into an array.
	 */
	private final static Color[] allColors = { BLUE, BROWN, GREEN, LIME, MAGENTA, MANGO, PINK, PURPLE, RED, TEAL };

	/**
	 * Private constructor since this is an utility class.
	 */
	private ColorUtils() {
	}

	/**
	 * Gets a random color.
	 *
	 * @return a random color.
	 */
	public static Color getRandomColor() {
		return allColors[new Random().nextInt(allColors.length)];
	}

}
