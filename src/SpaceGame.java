import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SpaceGame extends JComponent implements KeyListener, Runnable {

	public static void main(String[] args) {

		SpaceGame game = new SpaceGame();

		JFrame frame = new JFrame("SpaceGame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(400, 800);

		frame.addKeyListener(game);
		frame.add(game);

		frame.getContentPane().setBackground(Color.black);
	}

	Player player;
	boolean moveLeft = false, moveRight = false, fire = false;
	long lastTime, nextFire = 0;
	ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	ArrayList<BadGuy> badguyList = new ArrayList<BadGuy>();
	double spawnTime = 0;

	public SpaceGame() {
		player = new Player(175, 725);
		Thread t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		g.setColor(Color.blue);

		for (int i = 0; i < bulletList.size(); i++) {
			g.fillOval(bulletList.get(i).getX(), bulletList.get(i).getY(), bulletList.get(i).getWidth(),
					bulletList.get(i).getHeight());
		}

		for (int i = 0; i < badguyList.size(); i++) {
			g.drawImage(BadGuy.getImage(), badguyList.get(i).getX(), badguyList.get(i).getY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 37) {
			moveLeft = true;
		}
		if (e.getKeyCode() == 39) {
			moveRight = true;
		}
		if (e.getKeyCode() == 32) {
			fire = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 37) {
			moveLeft = false;
		}
		if (e.getKeyCode() == 39) {
			moveRight = false;
		}
		if (e.getKeyCode() == 32) {
			fire = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		lastTime = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();
			double deltaTime = (currentTime - lastTime) / 1000.0;
			lastTime = currentTime;
			if (moveLeft) {
				player.setTarget(-10);
			}
			if (moveRight) {
				player.setTarget(10);
			}
			bulletMove: for (int i = 0; i < bulletList.size(); i++) {
				bulletList.get(i).update(deltaTime);
				for (int j = 0; j < badguyList.size(); j++) {
					if (badguyList.get(j).didHit(bulletList.get(i).getX(), bulletList.get(i).getY(),
							bulletList.get(i).getWidth(), bulletList.get(i).getHeight())) {
						bulletList.remove(i);
						i--;
						continue bulletMove;
					}
				}

				if (bulletList.get(i).getY() < -20) {
					bulletList.remove(i);
					i--;
				}
			}

			for (int i = 0; i < badguyList.size(); i++) {
				badguyList.get(i).update(deltaTime);
				if (badguyList.get(i).isDead()) {
					badguyList.remove(i);
					i--;
				}
			}

			if (fire && nextFire < currentTime) {
				bulletList.add(new Bullet(player.getX() + player.getWidth() / 2 - 5, player.getY(), 300, 300, 300));
				nextFire = currentTime + 100;
			}
			player.update(deltaTime);
			spawnTime -= deltaTime;

			if (spawnTime < 0) {
				int route = (int) (Math.random() * 2);
				if (route == 0) {
					int[] x = { 359, 0, 375, 375 };
					int[] y = { 359, 400, 400, 0 };
					badguyList.add(new BadGuy(0, 0, x, y));
				} else if (route == 1) {
					int[] x = { 359, 0, 400, 0 };
					int[] y = { 359, 600, 100, 0 };
					badguyList.add(new BadGuy(359, 0, x, y));
				}
				spawnTime = 0.5;
			}

			repaint();

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}