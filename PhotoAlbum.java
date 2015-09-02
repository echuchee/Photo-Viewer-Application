import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PhotoAlbum {
	//sets up the variables
	JMenuBar menuBar;
	JMenu menu, menu2;
	JFrame frame;
	JMenuItem menuImport, menuDelete, menuExit;
	JRadioButtonMenuItem menuViewer, menuBrowser, menuSplit;
	ButtonGroup btnGroup = new ButtonGroup();
	ButtonGroup btnGroup2 = new ButtonGroup();
	JButton fwd, bkwd, delMagnets, delLastMagnet;
	JPanel emptyPanel, togglePanel;
	public JToggleButton toggle1, toggle2, toggle3, toggle4, magnet;
	JLabel textLabel = new JLabel("", SwingConstants.CENTER);
	String textInput = "";
	int xLoc, yLoc = 0;
	int hh, ww = 0;
	int strokex1, strokex2, strokey1,strokey2 = 0;
	Point startStroke = new Point(0,0);
	Point endStroke = new Point(0,0);
	Point temp41 = null;
	int mode = 0; 
	int maxIndex = 0;
	int index = -1;
	int magMode = 0;
	LightTable table = new LightTable(this);
	public PhotoAlbum copy;
	
	//Points used for free hand strokes when drawing.
	int currentX, currentY, oldX, oldY;
	// Photo panel that holds that photo component
	JScrollPane photoPanel;
	PhotoComponent photo;

	boolean t1, t2, t3, t4, t5, t6 = false;
	JFileChooser fileChooser;
	// Main method that creates a PhotoAlbum object and then shows the GUI of the object.
	public static void main(String[] args) {
		//System.out.println("test");
		PhotoAlbum test2 = new PhotoAlbum();
		test2.showGUI();

	}
	// Updates the toggles and status text on gui when stroke inputs are made on photo components.
	public void update(int q, String qq) { // 0 - > State. 1 - > Delete 3 - > Fwd 4 - > Bkwd 2 - > unrecognized
		photo =  table.getCurrentPhoto();
		if (q == 0) {
			toggle1.setSelected(photo.getVac());
			toggle2.setSelected(photo.getFam());
			toggle3.setSelected(photo.getSch());
			toggle4.setSelected(photo.getWor());
			textLabel.setText(qq);
		}
		if (q == 1) {
			if (table.isEmpty() == false) {
				table.delete();
				if (table.isEmpty() == false) {
					photo = table.getCurrentPhoto();
					toggle1.setSelected(photo.getVac());
					toggle2.setSelected(photo.getFam());
					toggle3.setSelected(photo.getSch());
					toggle4.setSelected(photo.getWor());

				} 
				else {
					toggle1.setSelected(false);
					toggle2.setSelected(false);
					toggle3.setSelected(false);
					toggle4.setSelected(false);
				}
				table.fixLayout();
				photo.deleteImage();
				photoPanel.revalidate();
				photo.resetValues();
				photo.repaint();
				btnGroup2.clearSelection();
				textLabel.setText(qq);

			}

		}
		if (q == 2) {
			textLabel.setText(qq);
		}
		if (q == 3) {
			if (table.getIndex() != table.getMaxIndex() - 1) {

				table.setIndex(table.getIndex() + 1);
				table.fixLayout();
				table.setPreferredSize(table.getPreferredSize());
				
				photo =  table.getCurrentPhoto();
				Dimension temp51 = new Dimension((photo.getImage()
						.getWidth(null) + 300), photo.getImage().getHeight(
						null) + 300);

				if (table.getMode() == 0 ) {
					frame.setSize(temp51);
				}
				if (table.getMode() == 1) {
					if (table.getMaxIndex() >= 2 && temp51.width < 650) {
						temp51.width = 650;
					}
				}
				if (table.getMode() == 2) {
					
					temp51 = new Dimension((photo.getImage()
							.getWidth(null) + 300), photo.getImage().getHeight(
							null) + 450);
					if (table.getMaxIndex() >= 2 && temp51.width < 650) {
						temp51.width = temp51.width + 300;
					}
					frame.setSize(temp51);
				}
				
				toggle1.setSelected(photo.getVac());
				toggle2.setSelected(photo.getFam());
				toggle3.setSelected(photo.getSch());
				toggle4.setSelected(photo.getWor());

				photoPanel.revalidate();
				photo.repaint();
				table.fixLayout();

			}
			textLabel.setText(qq);
		}
		if (q ==4 ) {
			if (table.getIndex() != 0) {
				table.setIndex(table.getIndex() -1);
				table.fixLayout();
				table.setPreferredSize(table.getPreferredSize());
				//PhotoComponent testPhoto = table.getCurrentPhoto();
				
				//testPhoto.setImage(im);
				photo = table.getCurrentPhoto();
				Dimension temp51 = new Dimension((photo.getImage()
						.getWidth(null) + 300), photo.getImage().getHeight(
						null) + 300);

				if (table.getMode() == 0) {
					frame.setSize(temp51);
				}
				if (table.getMode() == 2) {

					temp51 = new Dimension(
							(photo.getImage().getWidth(null) + 300), photo
									.getImage().getHeight(null) + 450);
					if (table.getMaxIndex() >= 2 && temp51.width < 650) {
						temp51.width = temp51.width + 300;
					}
					frame.setSize(temp51);
				}
				
				toggle1.setSelected(photo.getVac());
				toggle2.setSelected(photo.getFam());
				toggle3.setSelected(photo.getSch());
				toggle4.setSelected(photo.getWor());
				
				photoPanel.revalidate();
				photo.repaint();
				table.fixLayout();
			}
			textLabel.setText(qq);
		}
	}
	// Creates the GUI 
	public void showGUI() {

		// Sets up frame.
		frame = new JFrame("Photo Album Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setMinimumSize(new Dimension(550, 550));
		frame.setFocusable(false);
		
		// KEY LISTENERS ARE BROKEN
		
		// Key listener to detect page up and page down for future controls.
//		frame.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent keyEvent) {
//				int keyCode = keyEvent.getKeyCode();
//				if (keyCode == 33) {
//					textLabel.setText("You Hit Page Forward");
//				}
//				if (keyCode == 34) {
//					textLabel.setText("You Hit Page Backward");
//				}
//				//If the textbox is selected and the button pressed is anything other than shift, enter, backspace, ctrl, alt, and capslock
//				//then it will add the keyChar to the textInput string.
//				if (textBox.isSelected()) {
//					int temp2 = keyEvent.getKeyCode();
//					char temp = keyEvent.getKeyChar();
//					if (temp2 != 10 && temp2 != 8 && temp2 != 16 && temp2 != 20 && temp2 != 17 && temp2 != 18)  {
//						if (xLoc != 0 && (!(xLoc > ww + temp41.x || xLoc < temp41.x || yLoc < temp41.y || yLoc > temp41.y + hh))) {
//							textInput += temp;
//						}
//						
//					}
//					hh = photo.getImage().getHeight(null);
//					ww = photo.getImage().getWidth(null);
//					temp41 = photo.getXY();
//
//					photo.showPoint(xLoc, yLoc);
//					photo.repaint();
//					if (xLoc > ww + temp41.x || xLoc < temp41.x || yLoc < temp41.y || yLoc > temp41.y + hh) {
//						textLabel.setText("Cannot place text out of the bounds of the photo!");
//					}
//					else {
//						textLabel.setText("Insert text:  "  + textInput  + "    at point X?");
//						photo.repaint();
//
//					}
//					if (keyCode ==8 && textInput.length() > 0) {
//						textInput = textInput.substring(0, textInput.length() -1);
//						textLabel.setText("Insert text: " + textInput + " at point X?");
//						photo.repaint();
//
//					}
//				
//					if (keyCode== 10 && (!(xLoc > ww + temp41.x || xLoc < temp41.x || yLoc < temp41.y || yLoc > temp41.y + hh))) {
//						if (textInput.length() > 0) {
//							photo.setTextList(textInput);
//							photo.setTextListLoc(new Point(xLoc-temp41.x, yLoc-temp41.y));
//							textInput = "";
//							photo.showPoint(-50,0);
//							photo.repaint();
//						}
//					}
//				}
//
//			}
//
//			public void keyTyped(KeyEvent e) {
//				//
//			}
//
//			public void keyReleased(KeyEvent e) {
//				//
//			}
//		});

		// Creation of all the labels, buttons, panels, menus, and toggle buttons. 
		// Also adds all of them to the frame.

		//Default status text at the bottom of the screen. Will change after you click something.
		textLabel.setText("Go to file and import an image!");
		textLabel.setPreferredSize(new Dimension(100, 100));
		frame.getContentPane().add(textLabel, BorderLayout.SOUTH);
	
		photo = new PhotoComponent(null,null);
		photoPanel = new JScrollPane(table);
		photoPanel.setBackground(Color.CYAN);
		frame.getContentPane().add(photoPanel);


		//Panel for the toggle buttons to be a part of.
		togglePanel = new JPanel();
		togglePanel.setPreferredSize(new Dimension(120, 500));
		// Toggle buttons on the left side of the screen.
		toggle1 = new JToggleButton("Vacation");
		toggle1.setPreferredSize(new Dimension(100, 30));
		toggle2 = new JToggleButton("Family");
		toggle2.setPreferredSize(new Dimension(100, 30));
		toggle3 = new JToggleButton("School");
		toggle3.setPreferredSize(new Dimension(100, 30));
		toggle4 = new JToggleButton("Work");
		toggle4.setPreferredSize(new Dimension(100, 30));

		fwd = new JButton("-->");
		fwd.setPreferredSize(new Dimension(50, 30));
		bkwd = new JButton("<--");
		bkwd.setPreferredSize(new Dimension(50,30));

		
		
		
		// Keeps the user away from focusing the toggle buttons. This makes the page up and page down commands work 
		// because the key listener is only attached to the frame.
		toggle1.setFocusable(false);
		toggle2.setFocusable(false);
		toggle3.setFocusable(false);
		toggle4.setFocusable(false);
		fwd.setFocusable(false);
		bkwd.setFocusable(false);
		// Adds the toggle buttons to the toggle panel and adds the panel to the frame.
		togglePanel.add(toggle1);
		togglePanel.add(toggle2);
		togglePanel.add(toggle3);
		togglePanel.add(toggle4);
		togglePanel.add(bkwd);
		togglePanel.add(fwd);
		magnet = new JToggleButton("Magnet");
		magnet.setPreferredSize(new Dimension(100,30));
		togglePanel.add(magnet);
		magnet.setEnabled(false);

		delMagnets = new JButton("Delete All Magnets");
		delLastMagnet = new JButton("Delete Last Magnet");
		
		delLastMagnet.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				table.deleteLastMagnet();
			}
		});
		delMagnets.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				table.deleteAllMagnets();
			}
		});
		togglePanel.add(delMagnets);
		togglePanel.add(delLastMagnet);
		delMagnets.setVisible(false);
		delLastMagnet.setVisible(false);
		
		frame.getContentPane().add(togglePanel, BorderLayout.WEST);
		//Creates the menubar and menus.
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menu2 = new JMenu("View");
		//Adds the menus to the menubars.
		menuBar.add(menu);
		menuBar.add(menu2);
		//Creates the menu items
		menuImport = new JMenuItem("Import");
		menuDelete = new JMenuItem("Delete");
		menuExit = new JMenuItem("Exit");
		//Creates the radio buttos which will also be menu items for the second menu.
		menuViewer = new JRadioButtonMenuItem("Photo Viewer");
		menuBrowser = new JRadioButtonMenuItem("Browser");
		menuSplit = new JRadioButtonMenuItem("Split Mode");
		//Puts all the radio buttons into a group so that only 1 can be selected at a time.
		btnGroup.add(menuViewer);
		btnGroup.add(menuBrowser);
		btnGroup.add(menuSplit);

		//Adds the menu items to the menus.
		menu.add(menuImport);
		menu.add(menuDelete);
		menu.add(menuExit);
		menu2.add(menuViewer);
		menu2.add(menuBrowser);
		menu2.add(menuSplit);
		//Sets the default radio button option.
		menuViewer.setSelected(true);
		//Adds the menu bar to the frame.
		frame.setJMenuBar(menuBar);
		photo.setCoords(photoPanel, photo);
		
		//Mouse listeners that change the status text at the bottom of the screen.

		// Disables delete button if there are no photos.
		menu.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.isEmpty()) {
					//menuDelete.setVisible(false);
					menuDelete.setOpaque(true);
					menuDelete.setBackground(Color.GRAY);
				}
				else {
					//menuDelete.setVisible(true);
					menuDelete.setOpaque(false);
					//menuDelete.setBackground(Color.GRAY);
				}
			}
		});
		menu2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(table.getMode() == 0) {
					menuViewer.setSelected(true);
				}
				if(table.getMode() ==1) {
					menuBrowser.setSelected(true);
				}
				if(table.getMode() ==2) {
					menuSplit.setSelected(true);
				}
				
			}
		});


		//Below are all the mouse listeners that are currently used to update the status text bar at the bottom of the screen.


		// Mouse listener for when import is selected from the menu.
		menuImport.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				textLabel.setText("You Selected Import");
				if (table.getMagMode() == 0) {
					fileChooser = new JFileChooser();
	
					int returnVal = fileChooser.showOpenDialog(menuImport);
	
					File f = fileChooser.getSelectedFile();
					BufferedImage im = null;
					try {
						im = ImageIO.read(f);
						if (im != null) {
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					//Resets the photo component.
					
					//Loads the image into the component
					
					PhotoComponent testPhoto = new PhotoComponent(im,copy);
					testPhoto.setImage(im);
					photo = new PhotoComponent(im, copy);
				//	table.addPhoto(testPhoto);
					table.addPhoto(photo);
					table.setOriginal(0, 0);
					table.setIndex(table.getMaxIndex() - 1);
					//table.getMaxIndex()-1;
					
					//Fixes the frame so that resizing the photo is possible.
					Dimension temp51 = new Dimension((photo.getImage().getWidth(null)+300), photo.getImage().getHeight(null) + 300);
				
					if (table.getMode() == 0) {
						frame.setSize(temp51);
					}
					if (table.getMode() == 2) {
						
						temp51 = new Dimension((photo.getImage()
								.getWidth(null) + 300), photo.getImage().getHeight(
								null) + 450);
						frame.setSize(temp51);
					}
					//frame.setMinimumSize(new Dimension(im.getWidth()+400,800));
					//photo.setPreferredSize(photo.getPreferredSize());
					//photo.setSize(photo.getPreferredSize());
					//photo.setCoords(photoPanel, photo);
					// Adds the mouse listener to the photo that lets you flip the photo
					
					toggle1.setSelected(photo.getVac());
					toggle2.setSelected(photo.getFam());
					toggle3.setSelected(photo.getSch());
					toggle4.setSelected(photo.getWor());
					
					photoPanel.revalidate();
					photo.repaint();
					table.fixLayout();
				}

			}

		});
		
		// Mouse listener for when exit is selected from the menu.
		menuExit.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		// Mouse listener for when photo viewer is selected from the menu.
		menuViewer.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mode = 0;
				table.updateMode(mode);
				if (photo.getImage()!= null ) {
					Dimension temp51 = new Dimension((photo.getImage()
							.getWidth(null) + 300), photo.getImage().getHeight(
							null) + 300);
					frame.setSize(temp51);
				}
				textLabel.setText("You Selected Photo Viewer");
				magnet.setEnabled(false);
				magnet.setSelected(false);
				magMode = 0;
				table.updateMagMode(magMode);
			
				table.zeroMagnets();
				table.fixLayout();
			}
		});
		// Mouse listener for when browser is selected from the menu.
		menuBrowser.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mode = 1;
				table.updateMode(mode);
				// table.fixLayout();
				Dimension temp51 = new Dimension(900,900);
				frame.setSize(temp51);

				textLabel.setText("You Selected Browser");
				magnet.setEnabled(true);

				table.fixLayout();
			}
		});
		magnet.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.photoList.size() > 0 && table.getMode() == 1) {
					magMode = table.getMagMode();
					if (magMode ==1 && mode == 1) {
						magMode = 0;
						table.updateMagMode(magMode);
						frame.repaint();
						frame.revalidate();
					}
					else if (magMode ==0 && table.getMode() == 1) {	
						magMode = 1;
						table.updateMagMode(magMode);
						frame.repaint();
						frame.revalidate();
					}
				}
				
			}
		});
		// Mouse listener for when split mode is selected from the menu.
		menuSplit.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mode = 2;
				table.updateMode(mode);

				Dimension temp51 = new Dimension((photo.getImage().getWidth(
						null) + 300), photo.getImage().getHeight(null) + 450);
				if (table.getMaxIndex() >= 2 && temp51.width < 650) {
					temp51.width += 300;
				}
				frame.setSize(temp51);
				textLabel.setText("You Selected Split Mode");
				
				magnet.setEnabled(false);
				magnet.setSelected(false);
				magMode = 0;
				table.updateMagMode(magMode);

				table.zeroMagnets();
				table.fixLayout();
				
			}
		});
		
		if (table.isEmpty()) {
			//menuDelete.setVisible(false);
		}
		else {
			//menuDelete.setVisible(true);
		}
		
		// Mouse listener for when delete is selected from the menu.
		menuDelete.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				textLabel.setText("You Selected Delete");
				if(table.getMagMode() == 0) {
					if (menuDelete.isOpaque() == false) {
						table.delete();
						photo.deleteImage();
						photoPanel.revalidate();
						photo.resetValues();
						photo.repaint();
						btnGroup2.clearSelection();
						if (table.isEmpty()== false ) {
							photo =  table.getCurrentPhoto();
							toggle1.setSelected(photo.getVac());
							toggle2.setSelected(photo.getFam());
							toggle3.setSelected(photo.getSch());
							toggle4.setSelected(photo.getWor());
						}
						else { 
							toggle1.setSelected(false);
							toggle2.setSelected(false);
							toggle3.setSelected(false);
							toggle4.setSelected(false);
						}
	
					}
				}
				
				
			}
		});
		// Mouse listener for when you press the first toggle button on the left side of the screen.
		// Uses boolean to track whether or not the toggle is on/off. 
		//Changes the text based on the boolean.
		toggle1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.getMagMode() == 0) {
					t1 = !t1;
					photo.flipVac();
					if (t1 == true) {
						textLabel.setText("You Toggled Vacation Tag On");
						
					}
					if (t1 == false) {
						textLabel.setText("You Toggled Vacation Tag Off");
					}
				}
			}
		});
		// Mouse listener for when you press the second toggle button on the left side of the screen.
		// Uses boolean to track whether or not the toggle is on/off. 
		//Changes the text based on the boolean.
		toggle2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.getMagMode() == 0) {
					t2 = !t2;
					photo.flipFam();
					if (t2 == true) {
						textLabel.setText("You Toggled Family Tag On");
					}
					if (t2 == false) {
						textLabel.setText("You Toggled Family Tag Off");
					}
				}
			}
		});
		// Mouse listener for when you press the third toggle button on the left side of the screen.
		// Uses boolean to track whether or not the toggle is on/off. 
		//Changes the text based on the boolean.
		toggle3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.getMagMode() == 0) {
					t3 = !t3;
					photo.flipSch();
					if (t3 == true) {
						textLabel.setText("You Toggled School Tag On");
					}
					if (t3 == false) {
						textLabel.setText("You Toggled School Tag Off");
					}
				}
			}
		});
		// Mouse listener for when you press the fourth toggle button on the left side of the screen.
		// Uses boolean to track whether or not the toggle is on/off. 
		//Changes the text based on the boolean.
		toggle4.addMouseListener(new MouseAdapter() {
			int q = 0;
			public void mousePressed(MouseEvent e) {
				if (table.getMagMode() == 0) {
					t4 = !t4;
					photo.flipWor();
					q++;
					if (t4 == true) {
						textLabel.setText("You Toggled Work Tag On");
					}
					if (t4 == false) {
						textLabel.setText("You Toggled Work Tag Off");
					}
				}
			}
		});
		bkwd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.getIndex() != 0 && table.getMagMode() == 0) {
					table.setIndex(table.getIndex() -1);
					table.fixLayout();
					table.setPreferredSize(table.getPreferredSize());
					//PhotoComponent testPhoto = table.getCurrentPhoto();
					
					//testPhoto.setImage(im);
					photo = table.getCurrentPhoto();
					Dimension temp51 = new Dimension((photo.getImage()
							.getWidth(null) + 300), photo.getImage().getHeight(
							null) + 300);

					if (table.getMode() == 0) {
						frame.setSize(temp51);
					}
					if (table.getMode() == 2) {

						temp51 = new Dimension(
								(photo.getImage().getWidth(null) + 300), photo
										.getImage().getHeight(null) + 450);
						if (table.getMaxIndex() >= 2 && temp51.width < 650) {
							temp51.width = temp51.width + 300;
						}
						frame.setSize(temp51);
					}
					
					toggle1.setSelected(photo.getVac());
					toggle2.setSelected(photo.getFam());
					toggle3.setSelected(photo.getSch());
					toggle4.setSelected(photo.getWor());
					
					photoPanel.revalidate();
					photo.repaint();
					table.fixLayout();
				}
				textLabel.setText("Page Backward");
			}
		});
		fwd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (table.getIndex() != table.getMaxIndex() - 1 && table.getMagMode() == 0) {

					table.setIndex(table.getIndex() + 1);
					table.fixLayout();
					table.setPreferredSize(table.getPreferredSize());
					
					//PhotoComponent tempP = table.get(table.getIndex()+1);
					
					photo =  table.getCurrentPhoto();
					Dimension temp51 = new Dimension((photo.getImage()
							.getWidth(null) + 300), photo.getImage().getHeight(
							null) + 300);

					if (table.getMode() == 0 ) {
						frame.setSize(temp51);
					}
					if (table.getMode() == 1) {
						if (table.getMaxIndex() >= 2 && temp51.width < 650) {
							temp51.width = 650;
						}
					}
					if (table.getMode() == 2) {
						
						temp51 = new Dimension((photo.getImage()
								.getWidth(null) + 300), photo.getImage().getHeight(
								null) + 450);
						if (table.getMaxIndex() >= 2 && temp51.width < 650) {
							temp51.width = temp51.width + 300;
						}
						frame.setSize(temp51);
					}
					
					toggle1.setSelected(photo.getVac());
					toggle2.setSelected(photo.getFam());
					toggle3.setSelected(photo.getSch());
					toggle4.setSelected(photo.getWor());

					photoPanel.revalidate();
					photo.repaint();
					table.fixLayout();

				}
				textLabel.setText("Page Foward");
			}
		});
		copy = this;
		
		


		// WHen resizing the application it will adjust the coordinates of the photo to remain in the center of the background.
		frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {

				//photo.setCoords(photoPanel, photo);       
				//if (photo.getImage() !=null) {
				//	hh = photo.getImage().getHeight(null);
				//	ww = photo.getImage().getWidth(null);
				//	temp41 = photo.getXY();
				//}
			}

			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		frame.pack();
		frame.setVisible(true);

	}
	public void showDeleteButtons() {
		delLastMagnet.setVisible(true);
		delMagnets.setVisible(true);
	}
	public void hideDeleteButtons() {
		delLastMagnet.setVisible(false);
		delMagnets.setVisible(false);
	}
}
