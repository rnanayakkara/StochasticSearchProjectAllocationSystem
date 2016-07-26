/**
 * 
 */
package cs.resourceallocationapp.app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 * @author Rasanjana Nanayakkara - 14209670
 * Includes the main method of the project
 */
public class ResourceAllocation {

	//random number
	public static Random RND = new Random();
	/**
	 * Main method of application
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//set splash screen
		ImageIcon icon = new ImageIcon(ResourceAllocation.class.getResource("splashAbacus.gif"));
        JLabel label = new JLabel(icon);
        JWindow window = new JWindow();
        int width = 800;
        int height = 533;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        		
        window.getContentPane().add(label);
    		window.setBounds(x,y,width,height);
    		window.setVisible(true);
    		try {
    		    Thread.sleep(3200);
    		} catch (InterruptedException e) {
    		    e.printStackTrace();
    		}
    		window.setVisible(false);    		
    		
    	//create main form
		ResourceAllocationForm.main(args);	
	}
}
