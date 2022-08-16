import java.awt.Image;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class BadGuy {

	private Rectangle hitbox;
	private int[] wayX, wayY;
	private int speed, leg, lives;
	private Color color;
	private static Image image;

	public BadGuy(int x, int y, int[] locX, int[] locY) {
		hitbox = new Rectangle(x, y, 25, 25);
		if (image == null) {
			image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("badguy.jpg"));
		}
		speed = 200;
		wayX = locX;
		wayY = locY;
		leg = 0;
		lives = 1;
		float r = 0, g = 0, b = 0;
		while (r + g + b < 1) {
			r = (float) Math.random();
			g = (float) Math.random();
			b = (float) Math.random();

		}
		color = new Color(r, g, b);
	}

	public static Image getImage() {
		return image;
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
	}

	public int getWidth() {
		return hitbox.width;
	}

	public int getHeight() {
		return hitbox.height;
	}

	public void update(double deltaTime) {
		if (leg < wayX.length) {
			if (wayX[leg] > hitbox.x) {
				hitbox.x += deltaTime * speed;
				if (hitbox.x > wayX[leg]) {
					hitbox.x = wayX[leg];
				}
			}

			if (wayX[leg] < hitbox.x) {
				hitbox.x -= deltaTime * speed;
				if (hitbox.x < wayX[leg]) {
					hitbox.x = wayX[leg];
				}
			}

			// Y movement
			if (wayY[leg] > hitbox.y) {
				hitbox.y += deltaTime * speed;
				if (wayY[leg] > hitbox.y) {
					hitbox.y = wayY[leg];
				}
			}

			if (wayY[leg] < hitbox.x) {
				hitbox.y -= deltaTime * speed;
				if (wayY[leg] < hitbox.y) {
					hitbox.y = wayY[leg];
				}
			}

			if (wayY[leg] == hitbox.y && wayX[leg] == hitbox.x) {
				leg++;
			}
		}
	}

	public boolean isDead() {
		if (lives <= 0)
			return true;
		if (leg == wayX.length)
			return true;
		return false;
	}

	public boolean didHit(int x, int y, int w, int h) {
		Rectangle temp = new Rectangle(x, y, w, h);
		if (temp.intersects(hitbox)) {
			lives--;
			return true;
		}
		return false;
	}
}
