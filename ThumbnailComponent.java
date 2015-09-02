import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class ThumbnailComponent extends JComponent implements MouseListener{
	
	
	private static final long serialVersionUID = -3805444074524992984L;
	//private PhotoComponent myPhotoComponent = null;
	private Image im;
	private int id;
	//Keeps the value of the index of a thumbnail component that gets clicked on 
	// so that lightTable knows to change the index if something is clicked.
	public static int tempIndex = 0;
	
	public ThumbnailComponent(Image im, int num) {
		this.im = im;
		id = num -1;
        setSize(new Dimension(200,200));
        setPreferredSize(new Dimension(200,200));
        setVisible(true);
		setFocusable(true);
        requestFocusInWindow();
        addMouseListener(this);
		repaint();
	}
	// Returns index of thumbnail component.
	public int getID() {
		return id;
	}
	// Sets the ID to be equal to the index of the thumbnail component.
	public void setID(int num) {
		id = num;
		
	}
	public Dimension getPreferredSize() {
		return new Dimension(200,200);
	}
	public Dimension getMinimumSize() {
		return new Dimension(200,200);
	}
	public Dimension getMaximumSize() {
		return new Dimension(200,200);
	}
	
	//Scales the thumbnails to 200x200.
    public void paintComponent(Graphics g){
    	Graphics2D g2 = (Graphics2D)g;        
    	g2.setBackground(Color.CYAN);
        double temp1 =  (200.0/im.getWidth(null));
        double temp2 =  (200.0/im.getHeight(null));
        g2.scale(temp1, temp2);
	 	g2.drawImage(im, 0, 0, null);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	//Sets tempIndex to the ID to the thumbnail that was clicked.
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		tempIndex = getID() + 1;		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
