/** Water class that is responsible for managing the water over the terrain
 * @author SCTMIC015
 */

//package FlowSkeleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Water {
    float[][] height;
    int[][] waterValues;
    int dimx; int dimy;
    BufferedImage img;
    ArrayList<Integer> permute;

    /**
     * @return the number of items in water array
     */
    int dim() {
        return dimx*dimy;
    }

    /**
     * @return x dimensions
     */
    public int getDimx() {
        return dimx;
    }

    /**
     * @return y dimension
     */
    public int getDimy() {
        return dimy;
    }

    /**
     * Creates image of the water
     * @return buffered image
     */
    public BufferedImage getImage() {
        return img;
    }

    /**
     * // convert linear position into 2D location in grid
     * @param pos
     * @param ind
     */
    void locate(int pos, int [] ind)
    {
        ind[0] = (int) pos / dimy; // x
        ind[1] = pos % dimy; // y
    }

    /**
     * Derives the water image
     */
    void deriveImage()
    {
        img = new BufferedImage(dimx, dimy, BufferedImage.TYPE_INT_ARGB);
        for(int x=0; x < dimx; x++)
            for(int y=0; y < dimy; y++) {
                if (waterValues[x][y] != 0){
                    Color col = new Color(0, 0, 250);
                    img.setRGB(x, y, col.getRGB());
                }
            }
    }

    /**
     * // generate a permuted list of linear index positions to allow a random
     * // traversal over the terrain
     */
    void genPermute() {
        permute = new ArrayList<Integer>();
        for(int idx = 0; idx < dim(); idx++)
            permute.add(idx);
        java.util.Collections.shuffle(permute);
    }

    /**
     *  // find permuted 2D location from a linear index in the
     *  // range [0, dimx*dimy)
     * @param i
     * @param loc
     */
    void getPermute(int i, int [] loc) {
        locate(permute.get(i), loc);
    }

    /**
     * Reads in the matchng terrain data and sets empty water array
     * @param fileName
     */
    void readData(String fileName){
        try{
            Scanner sc = new Scanner(new File(fileName));

            // read grid dimensions
            // x and y correpond to columns and rows, respectively.
            // Using image coordinate system where top left is (0, 0).
            dimy = sc.nextInt();
            dimx = sc.nextInt();

            // populate height grid
            height = new float[dimx][dimy];
            waterValues = new int[dimx][dimy];
            for(int y = 0; y < dimy; y++){
                for(int x = 0; x < dimx; x++) {
                    height[x][y] = Float.parseFloat(sc.next());
                    waterValues[x][y] = 0;
                }
            }

            sc.close();

            // create randomly permuted list of indices for traversal
            genPermute();

            // generate greyscale heightfield image
            deriveImage();
        }
        catch (IOException e){
            System.out.println("Unable to open input file "+fileName);
            e.printStackTrace();
        }
        catch (java.util.InputMismatchException e){
            System.out.println("Malformed input file "+fileName);
            e.printStackTrace();
        }
    }

    /**
     * Adds water to the array
     * @param x
     * @param y
     * @param val
     */
    void addWater(int x, int y, int val) {
        synchronized (this) {
            for (int i = x - 3; i <= x + 3; i++) {
                for (int j = y - 3; j <= y + 3; j++) {
                    waterValues[i][j] = val;
                }
            }
            deriveImage();
        }
    }

    /**
     * Calculates the water surface
     * @param x
     * @param y
     * @return
     */
    double waterSurface(int x, int y) {
        synchronized (this) {
            double height1 = height[x][y];
            double waterDepth = waterValues[x][y] / 100;
            return height1 + waterDepth;
        }
    }

    /**
     * Transfers water over all the points in a range of values
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     */
    void waterTransfer(int x1, int x2, int y1, int y2) {
        synchronized (this) {
            for (int i = x1; i < x2; i++) {
                for (int j = y1; j < y2; j++) {
                    transferWater(i, j);
                }
            }
            deriveImage();
        }
    }

    /**
     * FInd the lowest neighbour of a point
     * @param x
     * @param y
     * @return
     */
    int[] lowest(int x, int y) {
        synchronized (this) {
            double low = waterSurface(x, y);
            int[] valPos = new int[]{x, y};
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (waterSurface(i, j) < low && i !=x && j != y) {
                        low = waterSurface(i, j);
                        valPos[0] = i;
                        valPos[1] = j;
                    }
                }
            }
            return valPos;
        }
    }

    /**
     * Transfers water from a point to its lowest neighbour
     * @param x
     * @param y
     */
    void transferWater(int x, int y) {
        synchronized (this) {
            int[] lowestPos = lowest(x, y);
            int lowX = lowestPos[0];
            int lowY = lowestPos[1];
            double lowWaterValSur = waterSurface(lowX, lowY);
            if (waterValues[x][y] > 0 && waterSurface(x, y) > lowWaterValSur) {
                waterValues[x][y]--;
                waterValues[lowX][lowY]++;
            }
        }
    }

    /**
     * Clears the water from the grid
     */
    void clearWater() {
        synchronized (this) {
            for (int i = 0; i < dimx; i++) {
                for (int j = 0; j < dimy; j++) {
                    waterValues[i][j] = 0;
                }
            }
            deriveImage();
        }
    }

    /**
     * Responsible for clearing water off the boundaries
     */
    void clearBoundary(){
        synchronized (this) {
            for (int i = 0; i < getDimx(); i++) {
                for (int j = 0; j < getDimy(); j++) {
                    if (i == 0 || i == getDimx() - 1 || j == 0 || j == getDimy() - 1) {
                        waterValues[i][j] = 0;
                    }
                }
            }
        }
    }
}
