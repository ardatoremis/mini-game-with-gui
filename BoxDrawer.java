import java.awt.Graphics;
import java.awt.Color;

public class BoxDrawer {

	public static void drawBox(Graphics g, int startX, int startY, Color color) {
		
		g.setColor(color);
		g.fillRect(startX, startY, 20, 20);
		
	}
	
}
