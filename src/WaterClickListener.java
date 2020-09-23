/** WaterCLickListener class responsible for adding clickability to the GUI
 * @author SCTMIC015
 */

//package FlowSkeleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class WaterClickListener extends MouseAdapter {
    private JPanel panel;
    Water water;

    /**
     * Constructor
     * @param panel
     * @param water
     */
    public WaterClickListener(JPanel panel, Water water) {
        this.panel = panel;
        this.water = water;
    }

    /**
     * Adds water to the GUI
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        //panel.addCircle(new Circle(e.getX(), e.getY(), 24, Color.BLUE));
       // super.mouseClicked(e);
        water.addWater(e.getX(), e.getY(), 50);
    }


}
