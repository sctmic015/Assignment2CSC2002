/**WaterEditor class that is responsible for managing the water over the terrain
 * @author SCTMIC015
 */

//package FlowSkeleton;

public class WaterEditor implements Runnable{
    Water water;
    Terrain land;
    int x1; int x2; int y1; int y2;
    //FlowPanel fp;
    public static boolean running = true;
    static int timestep = 0;
    public int thrNumb =0;

    /**
     * constructor
     * @param water
     * @param land
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @param fp
     * @param thrNum
     */
    public WaterEditor(Water water, Terrain land, int x1, int x2, int y1, int y2, int thrNum) {
        this.water = water;
        this.land = land;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.thrNumb = thrNum;
    }

    /**
     * Run Method that continually transfers water over the terrain
     */
    @Override
    public void run() {
        synchronized (this) {
            while (running == true) {
                water.waterTransfer(x1, x2, y1, y2);
                water.deriveImage();
                timestep++;
                water.clearBoundary();
            }
        }
    }


}
