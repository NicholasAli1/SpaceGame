import java.awt.Rectangle;

public class Bullet {

	private Rectangle hitbox;
	private int speed, radius;
	private double theta;

	public Bullet(int x, int y, int tarX, int tarY, int speed) {
		hitbox = new Rectangle(x, y, 10, 10);
		this.speed = -speed;
		int deltaX = hitbox.x - tarX;
		int deltaY = hitbox.y - tarY;
		theta = Math.atan2(deltaY, deltaX);
		radius = 5;
	}

	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
	}

	public int getCenterX() {
		return hitbox.x + radius;
	}

	public int getCenterY() {
		return hitbox.y + radius;
	}

	public int getRadius() {
		return radius;
	}

	public int getWidth() {
		return radius / 2;
	}

	public int getHeight() {
		return radius / 2;
	}

	public void update(double deltaTime) {
		hitbox.x += speed * Math.cos(theta) * deltaTime;
		hitbox.y += speed * Math.sin(theta) * deltaTime;
	}

}
