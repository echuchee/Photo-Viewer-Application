import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.regex.*;
//import javafx.geometry.*;
//import BoundingBox;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class PhotoComponent extends JComponent implements MouseMotionListener,
		MouseListener, KeyListener {

	private boolean flip;
	private Image image;
	private int x, y = 0;
	boolean showPoint = false;
	boolean preview = false;
	private int xLoc, yLoc, temp31 = 0;
	int oldX, oldY, currentX, currentY = 0;
	String textInput = "";
	boolean vacation = false, family = false, school = false, work = false;
	private int clickmode = 0; // 0 -> Left Click. 1 -> Right Click
	private String tempGesture = "";
	private String returnString = "";
	PhotoAlbum main; //Instance of photoalbum 
	int held = 0; int drag = 0;
	//BoundingBox box, deleteBox;
	Rectangle box, deleteBox;
	double tempXx = 0;
	double tempYy = 0;

	int textSize, textSize2, textSize3 = 0;
	// Arraylists that hold the strings of text that are inputted.
	ArrayList<String> textList = new ArrayList<String>();
	ArrayList<Point> textListLoc = new ArrayList<Point>();
	// Arraylists that hold the points for the strokes
	ArrayList<Point> strokeList = new ArrayList<Point>();
	ArrayList<Point> strokeList2 = new ArrayList<Point>();
	//Holds the points for the strokes of the gesture currently being drawn
	ArrayList<Point> gestureList = new ArrayList<Point>();
	ArrayList<Point> gestureList2 = new ArrayList<Point>();
	//Holds the points for the strokes of the last gesture drawn.
	ArrayList<Point> tempGestureList = new ArrayList<Point>();
	ArrayList<Point> tempGestureList2 = new ArrayList<Point>();
	//Holds the list of indexes for points that are in the bounding box.
	ArrayList<Integer> tempList = new ArrayList<Integer>();

	private static final long serialVersionUID = 7526472295622776147L;

	// Starts the photoComponent in a flipped state.
	public PhotoComponent(Image pic, PhotoAlbum pa) {
		flip = false;
		image = pic;
		x = 0;
		y = 0;
		showPoint = false;
		xLoc = 0;
		yLoc = 0;
		textSize = 0;
		textSize2 = 0;
		textList = new ArrayList<String>();
		textListLoc = new ArrayList<Point>();
		strokeList = new ArrayList<Point>();
		strokeList2 = new ArrayList<Point>();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		requestFocusInWindow();
		setFocusable(true);
		main = pa;
	}

	// Flips the photo.
	public void flip() {
		if (image != null) {
			flip = !flip;
			repaint();
		}
	}
	
	//Returns the value of booleans that are responsible for the toggle buttons on the left.
	public boolean getVac() {
		return vacation;
	}
	public boolean getFam() {
		return family;
	}
	public boolean getSch() {
		return school;
	}
	public boolean getWor() {
		return work;
	}
	public int calcTags() {
		int tags = 0;
		if (vacation == true) tags++;
		if (family == true) tags++;
		if (school == true) tags++;
		if (work == true) tags++;
		return tags;
	}
	public ArrayList<Integer> returnTags() {
		ArrayList<Integer> tags = new ArrayList();
		tags.add(0);
		tags.add(0);
		tags.add(0);
		tags.add(0);
		int qqq= 0;
		int qqq2 = 0;
			if(vacation) {
				tags.set(qqq2, 1);
				qqq2++;
			}
			if(family) {
				tags.set(qqq2, 2);
				qqq2++;
			}
			if(school) {
				tags.set(qqq2, 3);
				qqq2++;
				
			}
			if (work) {
				tags.set(qqq2, 4);
				qqq2 = 0;
			}
			return tags;
		}

	
	// Flips the boolean values that are responsible for the toggle buttons. 
	// These are called when the buttons are pressed or when gestures are drawn.
	public void flipVac() {
		vacation = !vacation;
	}
	public void flipFam() {
		family = !family;
	}
	public void flipSch() {
		school = !school;
	}
	public void flipWor() {
		work = !work;
	}

	// Inputs points into the strokelists that will be used in painting the
	// strokes later.
	public void give(int oldX, int oldY, int currentX, int currentY) {
		strokeList.add(new Point(oldX, oldY));
		strokeList2.add(new Point(currentX, currentY));
	}
	public void give2(int oldX, int oldY, int currentX, int currentY) {
		gestureList.add(new Point(oldX, oldY));
		gestureList2.add(new Point(currentX, currentY));
	}

	// Resets all the variables.
	public void resetValues() {
		flip = false;
		// image = null;
		x = 0;
		y = 0;
		showPoint = false;
		xLoc = 0;
		yLoc = 0;
		textSize = 0;
		textSize2 = 0;
		textList = new ArrayList<String>();
		textListLoc = new ArrayList<Point>();
		strokeList = new ArrayList<Point>();
		strokeList2 = new ArrayList<Point>();
		gestureList = new ArrayList<Point>();
		gestureList2 = new ArrayList<Point>();
	}

	// Adds a string to the arraylist of strings.
	public void setTextList(String newString) {
		textList.add(newString);
	}
	public String getReturnString() {
		return returnString;
	}

	// Adds the point at which each string starts at to an arraylist.
	public void setTextListLoc(Point newPoint) {
		textListLoc.add(newPoint);
	}

	// Returns the flipped state.
	public boolean getFlip() {
		return flip;
	}

	// Sets the image of the photo component.
	public void setImage(Image image2) {
		image = image2;
		repaint();
	}

	// Deletes the image of the photo component.
	public void deleteImage() {
		flip = false;
		// image = null;
		repaint();

	}
	
	public void resetGestureList() {
		if (flip == false) {
			printDirectionList(gestureList, gestureList2);
		}
		else {
			printDirectionList(tempGestureList, tempGestureList2);
		}
		tempGesture = "";
	}
	//Creates the string that contains the direction vector of the list of strokes(from 2 list of points). 
	public void printDirectionList(ArrayList<Point> gestures, ArrayList<Point> gestures2) {
		int r = 0;
		
		for (Point p : gestures) {
			Point p2 = gestures2.get(r);
			double slope = (p2.getY()-p.getY())/(p2.getX()-p.getX());
			if (p2.x >= p.x) { // RIGHT HALF
				if (slope > -.5 && slope < .5) {
					//System.out.println("E");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'E') {
						tempGesture+="E";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="E";
					}
				}
				if (slope >= .5 && slope <= 2) {
					//System.out.println("SE");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'B') {
						tempGesture+="B";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="B";
					}
				}
				if (slope <= -.5 && slope >= -2) {
					//System.out.println("NE");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'A') {
						tempGesture+="A";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="A";
					}
				}
				if (slope < -2) {
					//System.out.println("N");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'N') {
						tempGesture+="N";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="N";
					}
				}
				if (slope > 2) {
					//System.out.println("S");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'S') {
						tempGesture+="S";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="S";
					}
				}
				
			}
			else { //left half
				if (slope < .5 && slope > -.5) {
					//System.out.println("W");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'W') {
						tempGesture+="W";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="W";
					}
				}
				if (slope <= -.5 && slope >= -2) {
					//System.out.println("SW");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'C') {
						tempGesture+="C";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="C";
					}
				}
				if (slope >= .5 && slope <= 2) {
					//System.out.println("NW");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'D') {
						tempGesture+="D";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="D";
					}
				}
				if (slope < -2) {
					//System.out.println("S");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'S') {
						tempGesture+="S";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="S";
					}
				}
				if (slope > 2) { // UNDEEFINED, INFINITY
					//System.out.println("N");
					if(tempGesture.length() > 0 && tempGesture.charAt(tempGesture.length()-1) != 'N') {
						tempGesture+="N";
					}
					else if (tempGesture.length() == 0 ) {
						tempGesture+="N";
					}
				}
			}
			
			r++;
		}
		//System.out.println(tempGesture);
		parseGesture(tempGesture);
	}
	// Parses the gesture to determine what patterns it matches to perform various actions.
	public void parseGesture(String gesture) {
		//System.out.println("testing2");
		//System.out.println(gesture);
		if (flip == false && gesture.length() >= 2) {
			gesture = gesture.substring(1,gesture.length()-2);
			String rightArrow = "[AEB]+[NEWSCD]+[DWC]+"; //right arrow
			String leftArrow = "[DWC]+[NSABEW]+[AEB]+"; //left arrow
			String home = "[AEB]+[NS]*[CSB]+[NS]*[DWC]+[NS]*[DNA]+"; //square for home tag
			String delete = "[AEB]+[DNA]+E?[DNA]*+E?[DWC]+N?[DWC]*N?[CSB]+"; // pigtail delete pattern
			String vacation = "[CSB]+[EW]?[DNA]+"; // V for vacation tag
			String work = "[DWC]+[DNA]+[AEB]+[DNA]+[AEB]+[CSB]+[AEB]+[CSB]"; //  work tag
			String school = "[CSB]+[AEB]+[CSB]+"; //   school tag
			Pattern qm = Pattern.compile(rightArrow);
			Pattern qm2 = Pattern.compile(leftArrow);
			Pattern qm3 = Pattern.compile(home);
			Pattern qm4 = Pattern.compile(delete);
			Pattern qm5 = Pattern.compile(vacation);
			Pattern qm6 = Pattern.compile(work);
			Pattern qm7 = Pattern.compile(school);
			 if(qm6.matcher(gesture).find()) {
				 flipWor();
				main.update(0, "You toggled the work tag via gesture!");
			}
			 else if(qm3.matcher(gesture).matches()) {
				 flipFam();
				 main.update(0, "You toggled the family tag via gesture!");
				}
			else if(qm7.matcher(gesture).matches()) {
				flipSch();
				main.update(0, "You toggled the school tag via gesture!");
			}
			else if(qm4.matcher(gesture).matches()) {
				main.update(1, "Deleted photo via gesture!");
				
			}
			else if(qm5.matcher(gesture).matches()) {
				flipVac();
				main.update(0, "You toggled the vacation tag via gesture!");
			}
			else if(qm.matcher(gesture).matches()) {
				main.update(3, "Page forward via gesture!");
			}
			else if(qm2.matcher(gesture).matches()) {
				main.update(4, "Page backward via gesture!");
			}
			else {
				main.update(2, "Unrecognized gesture!");
			}
			 gestureList = new ArrayList<Point>();
			 gestureList2 = new ArrayList<Point>();

		}
		if (flip == true && held ==1 ) {
			if (gesture.length() > 3) {
				gesture = gesture.substring(1,gesture.length()-2);
				String delete = "[AEB]+[DNA]+E?[DNA]*+E?[DWC]+N?[DWC]*N?[CSB]+"; // pigtail delete pattern
				Pattern qm4 = Pattern.compile(delete);
				if(qm4.matcher(gesture).matches()) {
					deletePoints();
					main.update(2, "Deleted selected region!");
					
				}
				else { 
					main.update(2, "Unrecognized gesture!");
				}
			}
		}

	}

	// Switches on the showPoint variable which determines where the X is which
	// is where the text is placed.
	public void showPoint(int x, int y) {
		showPoint = true;
		xLoc = x;
		yLoc = y;
		if (x == -50) {
			showPoint = false;
			xLoc = 0;
			yLoc = 0;
		}
	}

	// Moves the image to the center. Called when the frame is resized in
	// PhotoALbum.
	public void setCoords(JScrollPane photoPanel, PhotoComponent photo) {
		if (image != null) {
			if (photoPanel.getWidth() > image.getWidth(null)) {
				float temp11 = photoPanel.getWidth() / 2 - image.getWidth(null)
						/ 2;
				x = (int) temp11;
			} else {
				x = 0;
			}
			if (photoPanel.getHeight() > image.getHeight(null)) {
				float temp12 = photoPanel.getHeight() / 2
						- image.getHeight(null) / 2;
				y = (int) temp12;
			} else {
				y = 0;
			}
		}
	}

	// Returns the image.
	public Image getImage() {
		return image;
	}

	// Returns the x and y coordinates of the image.
	public Point getXY() {
		return new Point(x, y);
	}

	// Returns the preferred size of the width and height of the image.
	@Override
	public Dimension getPreferredSize() {
		if (image == null) {
			return super.getPreferredSize();
		}
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new Dimension(width, height);
	}

	// Paint component responsible for the background, text, strokes, and image
	@Override
	public void paintComponent(Graphics g) { // paintComponents
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (flip) {
			// Blue background gradient.
			g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 102), 600, 600,
					new Color(155, 155, 255), true));
			g2.fillRect(0, 0, 2048, 2048);
			g2.setPaint(new Color(255, 255, 255));
			g2.fillRect(x, y, image.getWidth(null), image.getHeight(null));
			// Goes through the array of strings that holds the text and word
			// wraps it and displays it properly.
			if (textList.size() > 0) {
				for (int i = 0; i < textList.size(); i++) {
					String t = textList.get(i);

					g2.setPaint(new Color(0, 0, 0));
					Font currFont = super.getFont();
					FontMetrics metrics = g2.getFontMetrics(currFont);

					textSize = metrics.stringWidth(t);

					String temp = "";
					int tempSize = 0;
					if (textListLoc.get(i).x + textSize > image.getWidth(null)) { // wordwrap

						textSize2 = metrics.stringWidth(t);
						String newString1 = "";
						String newString2 = "";
						textSize3 = metrics.stringWidth(newString1);
						int qq = 0;

						temp += t.charAt(qq);

						tempSize = metrics.stringWidth(temp);

						while (textListLoc.get(i).x + textSize3 + tempSize < image
								.getWidth(null)) {
							tempSize = metrics.stringWidth(temp);
							newString1 += t.charAt(qq);
							textSize3 = metrics.stringWidth(newString1);
							qq++;
						}
						newString2 = t.substring(qq, t.length());
						textList.set(i, newString1);
						if (textListLoc.get(i).y < image.getHeight(null) - 12) {
							textList.add(newString2);
							textListLoc.add(new Point(textListLoc.get(i).x,
									textListLoc.get(i).y + 12));
						}

					}

					temp31++;
				}
				temp31 = 0;
				for (String t2 : textList) {

					g2.setPaint(new Color(0, 0, 0));
					g2.drawString(t2, textListLoc.get(temp31).x + x,
							textListLoc.get(temp31).y + y);
					temp31++;
				}

			}
			// Goes through the strokelist so that it can draw all the lines
			// that the strokes are composed of.
			int r = 0;
			RenderingHints AA = g2.getRenderingHints();
			AA.put(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHints(AA);
			for (Point p : strokeList) {
				g2.setPaint(new Color(0, 0, 0));
				if (held == 1 && box.contains(p.getX(),p.getY()) && box.contains(strokeList2.get(r).getX(),strokeList2.get(r).getY() )) {
					g2.setPaint(new Color(0,255,0));
				}
				g2.setStroke(new BasicStroke(2.0f));
				g2.drawLine(p.x + x, p.y + y, strokeList2.get(r).x + x,
						strokeList2.get(r).y + y);
				r++;
				repaint();
			}
			
			// Shows the X point where text is placed at.
			if (showPoint) {
				g2.setPaint(new Color(0, 0, 0));
				g2.drawString("X", xLoc, yLoc);
				repaint();
			}

		} else {
			// Blue background gradient
			//g2.setPaint(new GradientPaint(0, 0, new Color(0, 0, 102), 600, 600,
			//		new Color(155, 155, 255), true));
			//g2.fillRect(0, 0, 2048, 2048);
			// Image
			if (image == null) {
			}

			g2.drawImage((BufferedImage) image, null, x, y);
		}
		int rr = 0;
		for (Point p : gestureList) {
			g2.setPaint(new Color(255,0,255));
			g2.setStroke(new BasicStroke(2.0f));
			g2.drawLine(p.x + x, p.y + y, gestureList2.get(rr).x + x,
					gestureList2.get(rr).y + y);
			rr++;
			repaint();
		}
		rr = 0;
		for (Point p : tempGestureList) {
			g2.setPaint(new Color(255,0,255));
			g2.setStroke(new BasicStroke(2.0f));
			g2.drawLine(p.x + x, p.y + y, tempGestureList2.get(rr).x + x,
					tempGestureList2.get(rr).y + y);
			rr++;
			repaint();
		}
	}
	// Key listener responsible for putting the X where the mouse was clicked(for typing) and for drawing.
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1){
			clickmode = 0;
			if (held == 1) {
				if (box.contains(e.getX(), e.getY())) {
					//System.out.println("clicked in BB");
					drag = 1;
					tempXx = e.getX();
					tempYy = e.getY();
					int r = 0;
					for (Point p : strokeList) {
						if (box.contains(p.getX(),p.getY()) && box.contains(strokeList2.get(r).getX(),strokeList2.get(r).getY() )) {
							tempList.add(r);
						}
						r++;
					}
				}
				else {
					held = 0;
					tempGestureList = new ArrayList<Point>();
					tempGestureList2 = new ArrayList<Point>();
					
				}
				
				
			}
		}
		else if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
			clickmode = 1;
		}
		if(clickmode == 1) {

				xLoc = e.getX();
				yLoc = e.getY();
				showPoint(xLoc, yLoc);
				// textLabel.setText("Insert text: " + textInput +
				// " at point X?");
				repaint();

				oldX = currentX;
				oldY = currentY;

			//}
			oldX = e.getX() - getXY().x;
			oldY = e.getY()-getXY().y;
		}
		if (e.getClickCount() == 2 && clickmode == 0) {
			flip();
		}
		if (clickmode == 0) {

			if (e.getClickCount() == 1 && flip == true) {

				xLoc = e.getX();
				yLoc = e.getY();
				showPoint(xLoc, yLoc);
				// textLabel.setText("Insert text: " + textInput +
				// " at point X?");
				repaint();
				oldX = currentX;
				oldY = currentY;

			}
			oldX = e.getX() - getXY().x;
			oldY = e.getY()-getXY().y;
		}

	}
	//Key listener for typing on the back of photos. Not working right now.
	public void keyPressed(KeyEvent keyEvent) {
		System.out.println("keypressed");
		int temp2 = keyEvent.getKeyCode();
		char temp = keyEvent.getKeyChar();
		int hh = 0;
		int ww = 0;
		Point temp41= null;
		int keyCode = keyEvent.getKeyCode();
		if (temp2 != 10 && temp2 != 8 && temp2 != 16 && temp2 != 20 && temp2 != 17 && temp2 != 18)  {
			if (xLoc != 0 && (!(xLoc > ww + temp41.x || xLoc < temp41.x || yLoc < temp41.y || yLoc > temp41.y + hh))) {
				textInput += temp;
			}
			
		}
		
		hh = getImage().getHeight(null);
		ww = getImage().getWidth(null);
		temp41 = getXY();
		showPoint(xLoc, yLoc);
		repaint();

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

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

	public void mouseReleased(MouseEvent arg0) {
		//Clickmode = 1 -> Right clicking
		//Clickmode = 0 - > Left clicking
		if (clickmode ==1 && flip == false ) {
			resetGestureList();
		}
		else if (clickmode == 0 && flip == false) {
			resetGestureList();
		}
		else if ( clickmode == 0 && drag == 1) {
			tempList = new ArrayList<Integer>();
			drag = 0;

		}
		else if (clickmode == 1 && flip == true) {
			held = 1;
			
			int rr = 0;
			double minx = 1000;
			double miny = 1000;
			double maxx = 0;
			double maxy = 0;
			for (Point p : tempGestureList) {
				if (p.getX() <= minx ) {
					minx = p.getX();
				}
				if (tempGestureList2.get(rr).getX() <= minx) {
					minx = tempGestureList2.get(rr).getX();
				}
				if (p.getY() <= miny ) {
					miny = p.getY();
				}
				if (tempGestureList2.get(rr).getY() <= miny) {
					miny= tempGestureList2.get(rr).getY();
				}
				//set Max to help determine width/height
				if (p.getX() >= maxx ) {
					maxx = p.getX();
				}
				if (tempGestureList2.get(rr).getX() >= maxx) {
					maxx = tempGestureList2.get(rr).getX();
				}
				if (p.getY() >= maxy ) {
					maxy = p.getY();
				}
				if (tempGestureList2.get(rr).getY() >= maxy) {
					maxy= tempGestureList2.get(rr).getY();
				}
				rr++;
			}
			double width = maxx -minx;
			double height = maxy - miny;
			deleteBox = new Rectangle((int)minx,(int)miny,(int)width,(int)height);
			
			tempGestureList = gestureList;
			tempGestureList2 = gestureList2;
			gestureList = new ArrayList<Point>();
			gestureList2 = new ArrayList<Point>();
			tempGesture = "";
			rr = 0;
			minx = 1000;
			miny = 1000;
			maxx = 0;
			maxy = 0;
			for (Point p : tempGestureList) {
				if (p.getX() <= minx ) {
					minx = p.getX();
				}
				if (tempGestureList2.get(rr).getX() <= minx) {
					minx = tempGestureList2.get(rr).getX();
				}
				if (p.getY() <= miny ) {
					miny = p.getY();
				}
				if (tempGestureList2.get(rr).getY() <= miny) {
					miny= tempGestureList2.get(rr).getY();
				}
				//set Max to help determine width/height
				if (p.getX() >= maxx ) {
					maxx = p.getX();
				}
				if (tempGestureList2.get(rr).getX() >= maxx) {
					maxx = tempGestureList2.get(rr).getX();
				}
				if (p.getY() >= maxy ) {
					maxy = p.getY();
				}
				if (tempGestureList2.get(rr).getY() >= maxy) {
					maxy= tempGestureList2.get(rr).getY();
				}
				rr++;
			}
			width = maxx -minx;
			height = maxy - miny;
			box = new Rectangle((int)minx,(int)miny,(int)width,(int)height);
			resetGestureList();
		}

	}
	//Drags the points in the bounding box.
	public void movePoints(double x, double y) {

		for (int q : tempList) {
			strokeList.get(q).setLocation(strokeList.get(q).getX()+x, strokeList.get(q).getY()+y);
			strokeList2.get(q).setLocation(strokeList2.get(q).getX()+x,strokeList2.get(q).getY()+y);
		}
		
	}
	//Deletes the points in the bounding box if the delete gesture is drawn. 
	public void deletePoints() {
		int r = 0;
		tempList = new ArrayList<Integer>();
		for (Point p : strokeList) {
			if (deleteBox.contains(p.getX(),p.getY()) && deleteBox.contains(strokeList2.get(r).getX(),strokeList2.get(r).getY() )) {
				tempList.add(r);
			}
			
			r++;
		}
		int tempSize = tempList.size();
		ArrayList<Integer> tempList2 = new ArrayList<Integer>();
		for (int i = tempSize-1; i > -1; i--) {
			tempList2.add(tempList.get(i));
		}
		for (int q: tempList2) {
			strokeList.remove(q);
			strokeList2.remove(q);
		}
		held = 0;
		drag = 0;
		tempGestureList = new ArrayList<Point>();
		tempGestureList2 = new ArrayList<Point>();
		box = new Rectangle(0,0,0,0);
		deleteBox = new Rectangle(0,0,0,0);
		tempList = new ArrayList<Integer>();
	}
	public void mouseDragged(MouseEvent e2) {
		//Sends out coordinates for 
		//photo component to make a large arraylist of lines from.
		if (clickmode == 0 && drag == 1) {
			double tempX = e2.getX();
			double tempY = e2.getY();
			double xDist = (tempXx - tempX) * -1;
			double yDist = (tempYy - tempY) * -1;
			movePoints(xDist,yDist);
			tempXx = e2.getX();
			tempYy = e2.getY();

		}
		else if (clickmode == 0 && flip == true ) {
			currentX = e2.getX() - getXY().x;
			currentY = e2.getY() - getXY().y;
			give(oldX, oldY, currentX, currentY);
			repaint();
			oldX = currentX;
			oldY = currentY;
		}
		if (clickmode ==1) { // && flip == false
			currentX = e2.getX() - getXY().x;
			currentY = e2.getY() - getXY().y;
			give2(oldX, oldY, currentX, currentY);
			repaint();
			oldX = currentX;
			oldY = currentY;
		}

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
