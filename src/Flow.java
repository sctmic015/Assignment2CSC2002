/** Flow. Main class for setting up and displaying the GUI
 * @author SCTMIC015
 */

//package FlowSkeleton;

//import FlowPanel;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.Arrays;

public class Flow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static FlowPanel fp;
	//static boolean run = false;

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}

	/**
	 * Method that accepts terrain and water data as parameters and sets up the GUI
	 * @param frameX
	 * @param frameY
	 * @param landdata
	 * @param water
	 */
	public static void setupGUI(int frameX,int frameY,Terrain landdata, Water water) {

		Dimension fsize = new Dimension(800, 800);
    	JFrame frame = new JFrame("Waterflow");

    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setLayout(new BorderLayout());
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 


		fp = new FlowPanel(landdata, water, 512, 512);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		g.add(fp);


		// to do: add a MouseListener, buttons and ActionListeners on those buttons
		JPanel b = new JPanel();
	    b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		JButton endB = new JButton("End");;
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to stop
				frame.dispose();
				System.exit(0);
			}
		});
		b.add(endB);


		JButton startB = new JButton("Start");

		startB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fp.run();
				//WaterEditor.running = true;
				//System.out.println(WaterEditor.running);
			}
		});
		b.add(startB);

		JButton resetB = new JButton("Reset");

		resetB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WaterEditor.running = false;
				water.clearWater();
				WaterEditor.timestep = 0;
			}
		});
		b.add(resetB);

		JButton pauseB = new JButton("Pause");

		pauseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WaterEditor.running = false;
			}
		});
		b.add(pauseB);

		JLabel timesteps = new JLabel(String.valueOf(WaterEditor.timestep));
		JButton button = new JButton("Update Timesteps");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timesteps.setText(String.valueOf(WaterEditor.timestep / 4));
			}
		});
		b.add(button);
		b.add(timesteps);

		g.add(b);

		g.addMouseListener(new WaterClickListener(b, water));

		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
      	frame.setLocationRelativeTo(null);  // center window on screen
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);


	}

	/**
	 * Main Method That reads in args supplied and sets up simulation
 	 * @param args
	 */
	public static void main(String[] args) {
		Terrain landdata = new Terrain();
		Water water = new Water();
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java -jar flow.java intputfilename");
			System.exit(0);
		}
				
		// landscape information from file supplied as argument
		// 
		landdata.readData(args[0]);
		water.readData(args[0]);
		frameX = landdata.getDimX();
		frameY = landdata.getDimY();
		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata, water));
		//SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata, water));

		// to do: initialise and start simulation
		//System.out.println(Arrays.toString(water.lowest(51, 60)));
	}

}
