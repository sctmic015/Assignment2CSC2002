/**
 * Flow Panal class thats responsible for creating the terrain and water images as well as for creating the threads that start the simulation.
 * @author SCTMIC015
 */

//package FlowSkeleton;

import java.awt.Graphics;
import javax.swing.JPanel;

public class FlowPanel extends JPanel implements Runnable {
	Terrain land;
	Water water;
	int count = 0;
	static boolean running = true;
	int dimx; int dimy;

	/**
	 * constructor
	 * @param land
	 * @param water
	 * @param dimx
	 * @param dimy
	 */
	public FlowPanel(Terrain land, Water water, int dimx, int dimy) {
		this.land = land;
		this.water = water;
		this.dimx = dimx;
		this.dimy = dimy;
	}

	/**
	 * // responsible for painting the terrain and water
	 * 	// as images
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		super.paintComponent(g);

		// draw the landscape in greyscale as an image
		if (land.getImage() != null) {
			g.drawImage(land.getImage(), 0, 0, null);
		}

		if (water.getImage() != null) {
			g.drawImage(water.getImage(), 0, 0, null);
			this.repaint();
		}
	}

	public void setCount(int count){
		this.count = count;
	}

	/**
	 * Creates the threads that run the simulation
	 */
	public void run() {
		// display loop here
		// to do: this should be controlled by the GUI
		// to allow stopping and starting
		WaterEditor.running = true;
		WaterEditor w1 = new WaterEditor(water, land, 1, (dimx/2) +1, 1, (dimy)/2 +1, 1);
		Thread t1 = new Thread(w1);
		t1.start();
		WaterEditor w2 = new WaterEditor(water, land, 1, (dimx/2) + 1, (dimy)/2, dimy -2, 2);
		Thread t2 = new Thread(w2);
		t2.start();
		WaterEditor w3 = new WaterEditor(water, land,(dimx/2) , dimx - 2, 1, (dimy/2)+1, 3);
		Thread t3 = new Thread(w3);
		t3.start();
		WaterEditor w4 = new WaterEditor(water, land, (dimx/2), dimx -2, dimy/2, dimy-2, 4);
		Thread t4 = new Thread(w4);
		t4.start();

	}
	public void running(int x){
		if (x == 1) {
			running = true;
		}
		else running = false;
	}

	public void setWorkingArea(int x, int y){

	}
}

