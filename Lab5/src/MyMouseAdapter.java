import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	
	public void bombspaces(MyPanel myPanel, int x, int y) {
		int count = 0;
		Color cellColor = null;
		//Checks surrounding cells
		for (int i = -1; i<=1; i++) {
			for (int j = -1; j<=1; j++) {
				if (((y+i) < 0) || ((x+j) < 0)){
					//Does Nothing
				}
        		else if (((x+i) > 8) || ((y+j) > 8)){
        			//Does Nothing
        		} 
        		else if ((myPanel.bombs[x+i][y+j])) {
        			count = count + 1; 
        		}
			}
		}
		if (count == 0) {
			cellColor = Color.DARK_GRAY;
			myPanel.colorArray[x][y] = cellColor;
			for (int w = -1; w<=1; w++) {
				for (int z = -1; z<=1; z++) {
					if (((x+w) < 0) || ((y+z) < 0)){
						//Does Nothing
					}
	        		else if (((x+w) > 8) || ((y+z) > 8)){
	        			//Does Nothing
	        		}
	        		else if ((myPanel.bombs[x+w][y+z])) {
	        			cellColor = Color.WHITE;
	        			myPanel.colorArray[x+w][y+z] = cellColor;
	        		}
	        		else if ((!myPanel.bombs[x+w][y+z])) {
	        			cellColor = Color.DARK_GRAY;
	        			myPanel.colorArray[x+w][y+z] = cellColor;
	        			bombspaces(myPanel, x+w, y+z);
	        		}
				}
			}
			myPanel.repaint(); 
		} else {
			cellColor = Color.WHITE;
			myPanel.colorArray[x][y] = cellColor;
		}	
		myPanel.repaint(); 
	}

	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component c2 = e.getComponent();
			while (!(c2 instanceof JFrame)) {
				c2 = c2.getParent();
				if (c2 == null) {
					return;
				}
			}
			JFrame myFrame1 = (JFrame) c2;
			MyPanel myPanel1 = (MyPanel) myFrame1.getContentPane().getComponent(0);
			Insets myInsets1 = myFrame1.getInsets();
			int x2 = myInsets1.left;
			int y2 = myInsets1.top;
			e.translatePoint(-x2, -y2);
			int x3 = e.getX();
			int y3 = e.getY();
			myPanel1.x = x3;
			myPanel1.y = y3;
			myPanel1.mouseDownGridX = myPanel1.getGridX(x3, y3);
			myPanel1.mouseDownGridY = myPanel1.getGridY(x3, y3);
			myPanel1.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
                        //Released the mouse button on a different cell where it was pressed
                        //Do nothing
                    } else {
                        //Released the mouse button on the same cell where it was pressed
                    	boolean trufalse = myPanel.bombs[myPanel.mouseDownGridX][myPanel.mouseDownGridY];
                    	
                    	if (trufalse) {
                    		for (int col = 0; col < 9; col++) {   //Paints the rest of the mines
                    			for (int row = 0; row < 9; row++) {
                    				if (myPanel.bombs[col][row]){
                    					myPanel.colorArray[col][row] = Color.RED;
                    				}
                    			}
                    		}
                    		myPanel.repaint();
                    		JOptionPane.showMessageDialog(null, "Sorry you stepped on a mine", "Game Over!", JOptionPane.PLAIN_MESSAGE);;
                    		System.exit(0);
                    	} 
                    }
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component c2 = e.getComponent();
			while (!(c2 instanceof JFrame)) {
				c2 = c2.getParent();
				if (c2 == null) {
					return;
				}
			}
			JFrame myFrame1 = (JFrame)c2;
			MyPanel myPanel1 = (MyPanel) myFrame1.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets1 = myFrame1.getInsets();
			int x2 = myInsets1.left;
			int y2 = myInsets1.top;
			e.translatePoint(-x2, -y2);
			int x3 = e.getX();
			int y3 = e.getY();
			myPanel1.x = x3;
			myPanel1.y = y3;
			int gridX1 = myPanel1.getGridX(x3, y3);
			int gridY1 = myPanel1.getGridY(x3, y3);
			if ((myPanel1.mouseDownGridX == -1) || (myPanel1.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX1 == -1) || (gridY1 == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel1.mouseDownGridX != gridX1) || (myPanel1.mouseDownGridY != gridY1)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						if ((gridX1>=0) && (gridY1 >=0)) {
							Color newColor = null;
							Color myColor = myPanel1.colorArray[myPanel1.mouseDownGridX][myPanel1.mouseDownGridY];
							do{				
							switch (generator.nextInt(2)) {
							case 0:
								newColor = Color.CYAN;
								break;
							case 1:
								newColor = Color.LIGHT_GRAY;
								break;
							}
								myPanel1.colorArray[myPanel1.mouseDownGridX][myPanel1.mouseDownGridY] = newColor;
								myPanel1.repaint();
							}while(myColor.equals(newColor));	
						}
					}
				}
			}
			myPanel1.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}