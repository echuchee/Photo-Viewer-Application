import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.*;


public class LightTable extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = -8277663842691541468L;
	int mode;
	ArrayList<PhotoComponent> photoList = new ArrayList<PhotoComponent>();
	ArrayList<ThumbnailComponent> thumbs = new ArrayList<ThumbnailComponent>();
	ArrayList<Image> photos = new ArrayList<Image>();
	ArrayList<JPanel> thumbnails = new ArrayList<JPanel>();
	int index;
	JPanel centerPanel, bottomPanel;
	JScrollPane bottomPanel2, centerPanel2;
	int j, q;
	ThumbnailComponent tc;
	int magMode = 0;
	JLabel mag1, mag2, mag3, mag4;
	boolean tag1 = false,tag2 = false,tag3 = false,tag4 = false;
	ArrayList<Point> origLoc = new ArrayList<Point>();
	ArrayList<Point> newLoc = new ArrayList<Point>();
	//ArrayList<Integer> magnetHistoryList = new ArrayList();
	LinkedList<Integer> histList = new LinkedList();
	Timer timer;
	PhotoAlbum example;
	private int currMag = 0;
	boolean calctags2 = false;
	
	public int getMagMode() {
		return magMode;
	}
	//Calculates the original positions of photos before magnets pull on them.
	public void setOriginal(int x, int y) {
		int tempx = (int)centerPanel.getWidth();
		int tempy = (int)centerPanel.getHeight();
		int tempxx = 100;
		int tempyy = 50;
		origLoc = new ArrayList<Point>();
		for (JPanel pc : thumbnails) {
			Point temp = new Point((int)pc.getX(), (int)pc.getY());
			pc.setLayout(null);
			origLoc.add(new Point(tempxx, tempyy));
			pc.setLocation(tempxx,tempyy);
			pc.setSize(200,200);
			if (tempxx + 200 < tempx - 100) {
				tempxx+= 220;
			}
			else {
				tempxx = 100;
				tempyy+= 220;
			}
		}	
	}
	//Starts/Stops the timer.
	public void animate() {
		if(timer.isRunning()) {
			timer.stop();
		}
		else {
			timer.start();
		}
	}
	//Calculates the final position where tagged photos need to be based on the pull of magnets.
	public void setNew() {
		newLoc = new ArrayList<Point>();
		
		int bcd = 0;
		for (PhotoComponent pc : photoList) {
			ArrayList<Integer> tags = new ArrayList();
			Point p1 = new Point();
			Point p2 = new Point();
			Point p3 = new Point();
			Point p4 = new Point();
			Point pnew = new Point();
			tags = pc.returnTags();
			int temptag1 = tags.indexOf(1);
			
			if (temptag1 >= 0 && tag1 == false) {
				tags.set(0, tags.get(1));
				tags.set(1, tags.get(2));
				tags.set(2, tags.get(3));
				tags.set(3,0);
			}
			int temptag2 = tags.indexOf(2);
			if (temptag2 >= 0 && tag2 == false) {
				if (temptag2 == 0) {
					tags.set(0, tags.get(1));
					tags.set(1, tags.get(2));
					tags.set(2,0);
					
				}
				if (temptag2 == 1) {
					tags.set(1, tags.get(2));
					tags.set(2,tags.get(3));
					tags.set(3, 0);
				}
			}
			int temptag3 = tags.indexOf(3);
			if (temptag3 >= 0 && tag3 == false) {
				if (temptag3 == 0) {
					tags.set(0,tags.get(1));
					tags.set(1,0);
				}
				if (temptag3 ==1) {
					tags.set(1, tags.get(2));
					tags.set(2,0);
				}
				if (temptag3 == 2) {
					tags.set(2,tags.get(3));
					tags.set(3, 0);
				}
			}
			int temptag4 = tags.indexOf(4);
			if (temptag4 >= 0 && tag4 == false) {
				if(temptag4 ==0) {
					tags.set(0, 0);
				}
				if (temptag4 == 1) {
					tags.set(1,0);
				}
				if (temptag4 == 2) {
					tags.set(2, 0);
				}
				if (temptag4 == 3) {
					tags.set(3,0);
				}
			}
			if(tags.get(0) <= 0) {
				if (origLoc.size() > 0) {
					if (photoList.size() <= origLoc.size()) {
						pnew.setLocation(origLoc.get(bcd).getX()+60, origLoc.get(bcd).getY());
					}
				}
			}
			if (tags.get(0) > 0) {
				if(tags.get(0) == 1) {
					//vacation
					p1.setLocation(mag1.getX(), mag1.getY());
				}
				else if (tags.get(0) == 2) {
					//family
					p1.setLocation(mag2.getX(), mag2.getY());
				}
				else if (tags.get(0) == 3) {
					//schoool
					p1.setLocation(mag3.getX(), mag3.getY());
				}
				else if (tags.get(0) == 4) {
					//work
					p1.setLocation(mag4.getX(), mag4.getY());
				}
				pnew.setLocation(p1.getX(), p1.getY());

			}
			if (tags.get(1) > 0) {
				if(tags.get(0) == 1) {
					//vacation
					p1.setLocation(mag1.getX(), mag1.getY());
				}
				else if (tags.get(0) == 2) {
					//family
					p1.setLocation(mag2.getX(), mag2.getY());
				}
				else if (tags.get(0) == 3) {
					//schoool
					p1.setLocation(mag3.getX(), mag3.getY());
				}
				else if (tags.get(0) == 4) {
					//work
					p1.setLocation(mag4.getX(), mag4.getY());
				}
				if(tags.get(1) == 1) {
					//vacation
					p2.setLocation(mag1.getX(), mag1.getY());
				}
				else if (tags.get(1) == 2) {
					//family
					p2.setLocation(mag2.getX(), mag2.getY());
				}
				else if (tags.get(1) == 3) {
					//schoool
					p2.setLocation(mag3.getX(), mag3.getY());
				}
				else if (tags.get(1) == 4) {
					//work
					p2.setLocation(mag4.getX(), mag4.getY());
				}
				double avgX = p1.getX()/2 + p2.getX()/2;
				double avgY = p1.getY()/2 + p2.getY()/2;
				pnew.setLocation(avgX, avgY);
			}
			if (tags.get(2) > 0) {
				if (!tags.contains(1)) {
					p1.setLocation(mag2.getX(), mag2.getY());
					p2.setLocation(mag3.getX(), mag3.getY());
					p3.setLocation(mag4.getX(), mag4.getY());
				}
				if (!tags.contains(2)) {
					p1.setLocation(mag1.getX(), mag1.getY());
					p2.setLocation(mag3.getX(), mag3.getY());
					p3.setLocation(mag4.getX(), mag4.getY());
				}
				if (!tags.contains(3)) {
					p1.setLocation(mag1.getX(), mag1.getY());
					p2.setLocation(mag2.getX(), mag2.getY());
					p3.setLocation(mag4.getX(), mag4.getY());
				}
				if (!tags.contains(4)) {
					p1.setLocation(mag1.getX(), mag1.getY());
					p2.setLocation(mag2.getX(), mag2.getY());
					p3.setLocation(mag3.getX(), mag3.getY());
				}
				double avgX = p1.getX()/3 + p2.getX()/3 + p3.getX()/3;
				double avgY = p1.getY()/3 + p2.getY()/3 + p3.getY()/3;
				pnew.setLocation(avgX, avgY);
			}
			if (tags.get(3) > 0) {
				p1.setLocation(mag1.getX(), mag1.getY());
				p2.setLocation(mag2.getX(), mag2.getY());
				p3.setLocation(mag3.getX(), mag3.getY());
				p4.setLocation(mag4.getX(), mag4.getY());
				double avgX = p1.getX()/4 + p2.getX()/4 + p3.getX()/4 + p4.getX()/4;
				double avgY = p1.getY()/4 + p2.getY()/4 + p3.getY()/4 + p4.getY()/4;
				pnew.setLocation(avgX, avgY);
			}
			pnew.setLocation((int)pnew.getX()-60, (int)pnew.getY()-60);
			newLoc.add(pnew);
			bcd++;
		}
	}
	public void zeroMagnets() {
		mag1.setLocation(0,30);
		mag1.setSize(100,30);
		mag2.setLocation(0,130);
		mag2.setSize(100,30);
		mag3.setLocation(0,230);
		mag3.setSize(100,30);
		mag4.setLocation(0,330);
		mag4.setSize(100,30);
	}
	public LightTable(PhotoAlbum sample) {
		mode = 0; //photo viewer. mode 1 - > browser. 2- > split.
		index = -1;
		setLayout(new BorderLayout());
		fixLayout();
		example = sample;
		mag1 = new JLabel(" Vacation Magnet");
		mag1.setBackground(Color.BLACK);
		mag1.setOpaque(true);
		mag2 = new JLabel("  Family Magnet");
		mag2.setBackground(Color.GREEN);
		mag2.setOpaque(true);
		mag3 = new JLabel("  School Magnet");
		mag3.setBackground(Color.GREEN);
		mag3.setOpaque(true);
		mag4 = new JLabel("    Work Magnet");
		mag4.setBackground(Color.GREEN);
		mag4.setOpaque(true);
		zeroMagnets();
		timer = new Timer(5, this);
		timer.setInitialDelay(1000); //recurrs every 1 second
		animate();
		//Mouse Listeners for dragging magnets around.
		mag1.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				
				mag1.setLocation(mag1.getX()+e.getX()-25, mag1.getY()+e.getY()-25);
				if (currMag != 1) {
					histList.add(0,currMag);
					currMag = 1;
				}
				
			}
		});
		mag1.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (magMode != 1) {
					//magMode = 1;
					updateMagMode(1);
					example.magnet.setSelected(true);
				}
			}
		});
		mag2.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				
				mag2.setLocation(mag2.getX()+e.getX()-25, mag2.getY()+e.getY()-25);
				if (currMag != 2) {
					histList.add(0,currMag);
					currMag = 2;
				}


			}
		});
		mag2.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (magMode != 1) {
					//magMode = 1;
					updateMagMode(1);
					example.magnet.setSelected(true);
				}
			}
		});
		mag3.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				mag3.setLocation(mag3.getX()+e.getX()-25, mag3.getY()+e.getY()-25);
				if (currMag != 3) {
					histList.add(0,currMag);
					currMag = 3;
				}


			}
		});
		mag3.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (magMode != 1) {
					//magMode = 1;
					updateMagMode(1);
					example.magnet.setSelected(true);
				}
			}
		});
		mag4.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				mag4.setLocation(mag4.getX()+e.getX()-25, mag4.getY()+e.getY()-25);
				if (currMag != 4) {
					histList.add(0,currMag);
					currMag = 4;
				}

			}
		});
		mag4.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (magMode != 1) {
					//magMode = 1;
					updateMagMode(1);
					example.magnet.setSelected(true);
				}
			}
		});
		
	}
	public boolean isEmpty() {
		return (photoList.size() == 0);
	}
	public void updateMagMode(int mm) {
			histList.clear();
			magMode = mm;
			fixLayout();
			if (magMode == 0) {
				example.hideDeleteButtons();
				if (isEmpty() == false) {
					example.update(0, "Turned Magnet Mode Off");
				}
				zeroMagnets();
			}
			if (magMode == 1) {
				example.showDeleteButtons();
				if (isEmpty() == false) {
					example.update(0, "Turned Magnet Mode Off");
				}
			}
	}
	public void deleteLastMagnet() {
		if (currMag == 1) {
			mag1.setLocation(0,30);
			tag1 = false;
			
			while (histList.lastIndexOf(1) >= 0) {
				histList.removeLastOccurrence(1);
			}
			if (histList.isEmpty() == false) {
				currMag = histList.get(0);
			}

		}
		else if (currMag == 2) {
			mag2.setLocation(0,130);
			tag2 = false;	
			while (histList.lastIndexOf(2) >= 0) {
				histList.removeLastOccurrence(2);
			}
			if (histList.isEmpty() == false) {
				currMag = histList.get(0);
			}
		}
		else if (currMag == 3) {
			mag3.setLocation(0,230);
			tag3= false;

			while (histList.lastIndexOf(3) >= 0) {
				histList.removeLastOccurrence(3);
			}
			if (histList.isEmpty() == false) {
				currMag = histList.get(0);
			}
		}
		else if (currMag == 4) {
			mag4.setLocation(0,330);
			tag4 = false;
			while (histList.lastIndexOf(4) >= 0) {
				histList.removeLastOccurrence(4);
			}
			if (histList.isEmpty() == false) {
				currMag = histList.get(0);
			}
		}
		setOriginal(0,0);
		setNew();
	}
	public void deleteAllMagnets() {
		mag1.setLocation(0,30);
		mag2.setLocation(0,130);
		mag3.setLocation(0,230);
		mag4.setLocation(0,330);
		tag1 = false;
		tag2 = false;
		tag3 = false;
		tag4 = false;
		setOriginal(0,0);
		setNew();
	}
	
	public void delete() {
		photoList.remove(index);
		
		if (photoList.size() == 0) {
			index = -1;
			fixLayout();
		}
		else {
			if (photoList.size() >= index) {
				if (index != 0) {
					index--;
				}
				fixLayout();
			}
		}
	}
	public void fixLayout() {
		thumbnails = new ArrayList<JPanel>();
		if (this.centerPanel != null) {
			remove(centerPanel);
		}
		if (this.centerPanel2 != null) {
			remove(centerPanel2);
		}
		if (this.bottomPanel2 != null) {
			remove(bottomPanel2);
		}
		if (this.bottomPanel != null) {
			remove(bottomPanel);
		}
		if(mode ==1) {
			add(mag1);
			add(mag2);
			add(mag3);
			add(mag4);
		}
		if (magMode ==1) {
			setLayout(null);
			setBackground(new Color(176,196,222));
			
			if (this.centerPanel != null) {
				remove(centerPanel);
				centerPanel.setBackground(Color.GREEN);
			}
			centerPanel = new JPanel();
			centerPanel.setLayout(null);
			centerPanel.setSize(this.getParent().getWidth(), this.getParent().getHeight());
			setBounds(0,0,this.getParent().getWidth(),this.getParent().getHeight());
			centerPanel.setBackground(new Color(176,196,222));
		

			add(centerPanel);
			
			int temp = 0;
			thumbs = new ArrayList<ThumbnailComponent>();
			thumbnails = new ArrayList<JPanel>();

			for (PhotoComponent pc : photoList) {

				ThumbnailComponent tempThumb = new ThumbnailComponent(pc.getImage(), temp);

				temp++;
				JPanel tempPanel = new JPanel();
				tempPanel.setPreferredSize(new Dimension(220,220));
				
				
				tempPanel.setSize(220,220);
				tempPanel.setBounds(0,0,tempPanel.getX(),tempPanel.getY());
				tempPanel.add(tempThumb);
				tempPanel.setBackground(new Color(176,196,222));
				thumbnails.add(tempPanel);
				
				setOriginal((int)centerPanel.getWidth(), (int)centerPanel.getHeight());
				
				
				centerPanel.add(tempPanel);
				revalidate();
				
			}
			
		}
		else {
			if (mode == 0 || mode == 2) {
				if (this.mag1 != null) {
					mag1.setVisible(false);
				}
				if (this.mag2 != null) {
					mag2.setVisible(false);
				}
				if (this.mag3 != null) {
					mag3.setVisible(false);
				}
				if (this.mag4 != null) {
					mag4.setVisible(false);
				}
			}
			else if (mode == 1) {
				if (this.mag1 != null) {
					mag1.setVisible(true);
				}
				if (this.mag2 != null) {
					mag2.setVisible(true);
				}
				if (this.mag3 != null) {
					mag3.setVisible(true);
				}
				if (this.mag4 != null) {
					mag4.setVisible(true);
				}
			}
			

		}
		if (mode == 0 && magMode == 0) { //viewer
			setLayout(new BorderLayout());
			thumbs = new ArrayList<ThumbnailComponent>();
			thumbnails = new ArrayList<JPanel>();
				
				centerPanel = new JPanel();
				centerPanel2 = new JScrollPane(centerPanel);
				centerPanel.setBackground(new Color(176,196,222));

				if (index != -1) {
					centerPanel.add(photoList.get(index));
					//System.out.println("Index : " + index + " set to focusable");
					//photoList.get(index).setFocusable(true);
				}
				//centerPanel.setBackground(new Color(100,100,0));
				centerPanel2 = new JScrollPane(centerPanel);
				add(centerPanel2, BorderLayout.CENTER);
		}
		else if (mode ==1&& magMode == 0){ //browser  /box layout?
				//bottomPanel = new JScrollPane();
				setLayout(new BorderLayout());
			
				if (this.centerPanel != null) {
					remove(centerPanel);
					centerPanel.setBackground(Color.GREEN);
				}
				if(bottomPanel2 != null) {
					//remove(bottomPanel2);
				}
				centerPanel = new JPanel();
				centerPanel.setBackground(new Color(176,196,222));
				//centerPanel2 = new JScrollPane(centerPanel);
				centerPanel2.setBackground(new Color(176,196,222));
				//bottomPanel = new JPanel();
				//bottomPanel2 = new JScrollPane(bottomPanel);
				
				//centerPanel.setSize(new Dimension(600,600));
				//centerPanel.setPreferredSize(new Dimension(600,600));
				//System.out.println(centerPanel2.getWidth());
				
				//centerPanel.setMaximumSize(new Dimension(600,2000));
				
				//centerPanel2 = new JScrollPane(centerPanel);
				add(centerPanel, BorderLayout.CENTER);
				//add(bottomPanel2, BorderLayout.SOUTH);
				
				int temp = 0;
				thumbs = new ArrayList<ThumbnailComponent>();
				thumbnails = new ArrayList<JPanel>();
				for (PhotoComponent pc : photoList) {

					ThumbnailComponent tempThumb = new ThumbnailComponent(pc.getImage(), temp);

					temp++;
					JPanel tempPanel = new JPanel();
					tempPanel.setPreferredSize(new Dimension(220,220));
					
					
					Border tempBorder = (BorderFactory.createMatteBorder(
                            5, 5, 5, 5, Color.red));
					if (temp == index +1) {
						tempPanel.setBorder(tempBorder);
					}
					tempPanel.add(tempThumb);
					tempPanel.setBackground(new Color(176,196,222));
					
					String tempString = "" + index;
					tempPanel.setName(tempString);
					thumbnails.add(tempPanel);
					thumbs.add(tempThumb);
					centerPanel.add(tempPanel);
					revalidate();
					
				}
				
			

				tc = null;
				for(int i = 0; i < thumbs.size();i++) {
					j++;
					tc = thumbs.get(i);
					tc.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if (index != tc.tempIndex) {
								index = tc.tempIndex;
								fixLayout();
								example.update(0, "");
							}
							if (e.getClickCount() == 2) {
								updateMode(0);
								updateMagMode(0);
								example.magnet.setEnabled(false);
								fixLayout();
							}
							
						}
					});
				}
				
				
		}
		else if (mode == 2&& magMode == 0) { //split
			setLayout(new BorderLayout());
				bottomPanel = new JPanel();
				centerPanel = new JPanel();
				bottomPanel2 = new JScrollPane(bottomPanel);
				centerPanel2 = new JScrollPane(centerPanel);

				centerPanel.setBackground(new Color(176,196,222));

				bottomPanel.setMinimumSize(new Dimension(350,350));
				bottomPanel.setBackground(new Color(176,196,222));
				int temp = 0;
				thumbs = new ArrayList<ThumbnailComponent>();
				for (PhotoComponent pc : photoList) {
					ThumbnailComponent tempThumb = new ThumbnailComponent(pc.getImage(), temp);
					temp++;
					JPanel tempPanel = new JPanel();
					tempPanel.setPreferredSize(new Dimension(220,220));
					
					
					Border tempBorder = (BorderFactory.createMatteBorder(
                            5, 5, 5, 5, Color.red));
					if (temp == index +1) {
						tempPanel.setBorder(tempBorder);
					}
					tempPanel.add(tempThumb);
					tempPanel.setBackground(new Color(176,196,222));
					
					String tempString = "" + index;
					tempPanel.setName(tempString);
					thumbnails.add(tempPanel);
					thumbs.add(tempThumb);
					bottomPanel.add(tempPanel);
					revalidate();
					
				}

				tc = null;
				for(int i = 0; i < thumbs.size();i++) {
					j++;
					tc = thumbs.get(i);
					tc.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e) {
							if (magMode == 0) {
								if (index != tc.tempIndex) {
									index = tc.tempIndex;
									fixLayout();
								}
								if (e.getClickCount() == 2) {
									updateMode(0);
									fixLayout();
									updateMagMode(0);
									example.magnet.setEnabled(false);
									
								}
							}
							
						}
					});
				}

				if (index != -1 && magMode == 0) {
					centerPanel.add(photoList.get(index));
				}
				bottomPanel2 = new JScrollPane(bottomPanel);
				centerPanel2 = new JScrollPane(centerPanel);
				add(centerPanel2, BorderLayout.CENTER);
				add(bottomPanel2, BorderLayout.SOUTH);
		}
		revalidate();
	}
	
	public void updateSelected() {
		bottomPanel.repaint();
	}
	public void addPhoto(PhotoComponent pc) {
		pc.requestFocus();
		photoList.add(pc);
	}
	public void updateMode(int n) {
		if ( mode != n) {
			mode = n;
			fixLayout();
			revalidate();
		}
	}
	public int getMode() {
		return mode;
	}
	
	public PhotoComponent returnIndex(int i) {
		return photoList.get(i);
	}
	public void deleteEntry(int i) {
		photoList.remove(i);
	}
	public void setIndex(int i) {
		index  = i;
	}
	public int getIndex() {
		return index;
	}
	public int getMaxIndex() {
		return photoList.size();
	}
	
	public PhotoComponent getCurrentPhoto() {
		return photoList.get(index);
	}
	
	
	public Dimension getPreferredSize() {
		if (photoList.size() == 0 ) {
			return new Dimension(600,600);
		}
		else{
			int width = photoList.get(index).getImage().getWidth(null);
			int height = photoList.get(index).getImage().getHeight(null);
			
			int q= 0;
			if (mode ==2 ) {
				q = 250;
				
			}
			if (width > 650) {
				width = 650;
				
			}
			if (height > 650) {
				height = 650;
			}
			
			if (mode ==1) {
				height = 650;
				width = 650;
			}
			
			return new Dimension(width,height+ q);
			//return new Dimension(600,600);
			
		}

	}

	public void actionPerformed(ActionEvent e) {
		checkLocations();
	}
	
	public void checkLocations() {
		setNew();
		int abc = 0;
		if (isEmpty()) {
			example.magnet.setSelected(false);
		}
		if (mag1.getX() == 0 && mag1.getY() == 30) {
			
			tag1 = false;
			mag1.setBackground(Color.RED);
		}
		else {
			tag1 = true;
			mag1.setBackground(Color.GREEN);
		}
		if (mag2.getX() == 0 && mag2.getY() == 130) {
			tag2 = false;
			mag2.setBackground(Color.RED);
		}
		else {
			tag2 = true;
			mag2.setBackground(Color.GREEN);
		}
		if (mag3.getX() == 0 && mag3.getY() == 230) {
			tag3 = false;
			mag3.setBackground(Color.RED);
		}
		else {
			tag3 = true;
			mag3.setBackground(Color.GREEN);
		}
		if (mag4.getX() == 0 && mag4.getY() == 330) {
			tag4 = false;
			mag4.setBackground(Color.RED);
		}
		else {
			tag4 = true;
			mag4.setBackground(Color.GREEN);
		}
		for (JPanel pc : thumbnails) {
			if (pc.getX()!= newLoc.get(abc).getX()) {
				if (pc.getX() >= newLoc.get(abc).getX()) {
					pc.setLocation(pc.getX()-1,pc.getY());
				}
				if (pc.getX() <= newLoc.get(abc).getX()) {
					pc.setLocation(pc.getX()+1,pc.getY());
				}
				if (pc.getY() >= newLoc.get(abc).getY()) {
					pc.setLocation(pc.getX(),pc.getY()-1);
				}
				if (pc.getY() <= newLoc.get(abc).getY()) {
					pc.setLocation(pc.getX(),pc.getY()+1);
				}
			}
			abc++;
			repaint();
			revalidate();
		}
		if (example!= null && magMode == 1) {
			example.toggle1.setSelected(false);
			example.toggle2.setSelected(false);
			example.toggle3.setSelected(false);
			example.toggle4.setSelected(false);
		}
		
	}
}
