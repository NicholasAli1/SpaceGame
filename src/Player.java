import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;

public class Player {

	

private Rectangle hitbox;
	int targetX, speed = 500;
	private static Image image;

	public Player(int x, int y) {
		hitbox = new Rectangle(x, y, 50, 50);
		targetX = x;
		if (image == null) {
			image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("player.jpg"));
		}
	}

	public Image getImage() {
		return image;
	}

	
	
	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
	}

	public int getWidth() {
		return hitbox.height;
	}

	public int getHeight() {
		return hitbox.height;
	}

	public void setTarget(int value) {
		targetX += value;
		if (targetX < 0)
			targetX = 0;
		if (targetX > 350)
			targetX = 350;
	}

	public void update(double deltaTime) {
		if (targetX > hitbox.x) {
			hitbox.x += deltaTime * speed;
			if (hitbox.x > targetX) {
				hitbox.x = targetX;
			}
		}

		if (targetX < hitbox.x) {
			hitbox.x -= deltaTime * speed;
			if (hitbox.x < targetX) {
				hitbox.x = targetX;
			}
		}

	}

	{
	}
}
