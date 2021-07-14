import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.parser.Entity;
import java.util.Scanner;
import java.util.*;
import java.util.Timer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.function.Supplier;
import java.util.Random;

class MenuPanel extends JPanel {

	JButton play = new JButton("Play");
	JButton help = new JButton("Help");
	JButton exit = new JButton("Exit");

	Image menubkg = new ImageIcon("images\\bg.png").getImage(); // menu background
	JPanel center = new JPanel(); // adding another jpanel in a panel for boxLayout

	MenuPanel() {
		center.setLayout(new GridLayout(3, 1, 0, 0));
		center.setOpaque(false);
		center.setBounds(350, 250, 300, 180);
		setFocusable(true);

		/* setting icons on buttons */
		Font f1 = new Font("Times New Roman", Font.PLAIN, 55);
		play.setFont(f1);
		Font f2 = new Font("Times New Roman", Font.PLAIN, 55);
		help.setFont(f1);
		Font f3 = new Font("Times New Roman", Font.PLAIN, 75);
		exit.setFont(f1);
		add(center); // adding the panel to anothe JPanel
		this.setLayout(null);
		play.setForeground(Color.white);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorder(null);
		help.setForeground(Color.white);
		help.setOpaque(false);
		help.setContentAreaFilled(false);
		help.setBorder(null);
		exit.setForeground(Color.white);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setBorder(null);

		/* adding the buttons in the panel */
		center.add(play);
		center.add(help);
		center.add(exit);

		/* adding mouseListeners on buttons */
		play.addMouseListener(new Click());
		help.addMouseListener(new Click());
		exit.addMouseListener(new Click());
	}// end constructor

	class Click extends MouseAdapter { // internal friendly class
		public void mouseClicked(MouseEvent me) {
			if (me.getSource() == play) {
				Game.cl.show(Game.cards, "GamePanel"); // show gamePanel when play is clicked
			}
			if (me.getSource() == help) {
				Game.cl.show(Game.cards, "HelpPanel"); // show helpPanel when help is clicked
			}
			if (me.getSource() == exit) {
				System.exit(0); // exit application when exit is clicked
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		setFocusable(true);
		int x = this.getWidth();
		int y = this.getHeight();
		center.setBounds(x / 2 - 150, y - 200, 300, 180);
		g2d.drawImage(menubkg, 0, 0, x, y, null);
		repaint();
	}// end paintComponent
}// end GamePanel

///////////////////////////////////////////////////// Help Panel
///////////////////////////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
class HelpPanel extends JPanel {
	int amount = 6;
	Image background[] = new Image[amount];

	Image eggA = new ImageIcon("images\\help\\eggA.gif").getImage();
	Image eggB = new ImageIcon("images\\help\\eggB.gif").getImage();
	Image door = new ImageIcon("images\\help\\portal.gif").getImage();

	JButton back = new JButton("Back"); // back button
	int page = 0;

	HelpPanel() {
		addKeyListener(new Press());

		for (int i = 0; i < amount; i++) {
			background[i] = Toolkit.getDefaultToolkit().getImage("images\\help\\game" + String.valueOf(i) + ".jpg");
		}
	}

	class Press extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				page--;
				if (page < 0) {
					page = 0;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				page++;
				if (page == amount) {
					page = amount - 1;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_B) {
				Game.cl.show(Game.cards, "MenuPanel");
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		setFocusable(true);
		grabFocus();

		int x = this.getWidth();
		int y = this.getHeight();

		g2d.drawImage(background[page], 0, 0, x, y, null);
		if (page == 0) {
			g2d.drawImage(eggA, 650, 400, 200, 200, null);
			g2d.drawImage(door, 1080, 300, 200, 200, null);
			g2d.drawImage(eggB, 1530, 400, 200, 200, null);
		}

		repaint();
	}
}// end class

////////////////////////////////////////////////////////////////// GAME PANEL
////////////////////////////////////////////////////////////////// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
class GamePanel extends JPanel {
	Image gamebkg = new ImageIcon("images\\in2.png").getImage();
	Image player1 = new ImageIcon("images\\eggB.png").getImage();
	Image player1_right = new ImageIcon("images\\eggBright.gif").getImage();
	Image player1_left = new ImageIcon("images\\eggBleft.gif").getImage();
	Image player2 = new ImageIcon("images\\eggA.png").getImage();
	Image player2_right = new ImageIcon("images\\eggAright.gif").getImage();
	Image player2_left = new ImageIcon("images\\eggAleft.gif").getImage();
	Image move1 = new ImageIcon("images\\move.png").getImage();
	Image move2 = new ImageIcon("images\\move.png").getImage();
	Image move3 = new ImageIcon("images\\move.png").getImage();
	Image door = new ImageIcon("images\\portal.gif").getImage();
	Image jump_table = new ImageIcon("images\\jump_table.png").getImage();
	Image unlock = new ImageIcon("images\\unlock.png").getImage();
	Image unlock2 = new ImageIcon("images\\unlock.png").getImage();
	Image lock = new ImageIcon("images\\lock.png").getImage();
	Image tempbkg; // temporary background

	public int x1, y1;
	public int x2, y2;

	public boolean jumping = false;
	public boolean falling = true;
	public boolean right = false;
	public boolean left = false;
	public double gravity = 0.0;// player1
	public boolean jumping2 = false;
	public boolean falling2 = true;
	public boolean right2 = false;
	public boolean left2 = false;
	public double gravity2 = 0.0;// player2
	public boolean ground = false;
	public boolean ground2 = false;
	public boolean ground3 = false;// player1
	public boolean ground4 = false;
	public boolean ground5 = false;
	public boolean ground6 = false;// player2
	public boolean disappear = false;
	public boolean disappear2 = false;
	public boolean jump = false;
	public boolean jump2 = false;

	JLabel points;
	Thread jumpThread;// jump sound
	Music Data;
	int pos = 900, pos2 = 900, move_x1 = 0, move_x2 = 0;
	int move_dir = 0, move_key = 0, move_dir2 = 0, move_key2 = 0, move_dir3 = 0, move_key3 = 0;
	int people = 0;
	static int level_door = 1;
	int level_key = 0;// use at unlock the key
	int key = 0, key2 = 0;

	GamePanel() {

		setLayout(null);
		setFocusable(true);
		tempbkg = gamebkg;

		x1 = 5;
		y1 = 0;
		x2 = 5;
		y2 = 0;

		points = new JLabel("Points: 0");
		points.setBounds(5, 5, 100, 20);

		/* adding both components in jpanel */
		add(points);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				///////////////////////////////////////////////////////////// jump sound
				Data = new Music();
				jumpThread = new Thread(() -> {
					System.out.println(jumping2);
					while (jump) {
						Data.playMusic("music\\jump.wav");
						jump = false;
					}
				});
				jumpThread.start();
				////////////////////////////////////////////////////////////////////////////////////// player1
				////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_LEFT) {
					if (!left2) {
						left2 = true;
					}
				}
				if (ke.getKeyCode() == ke.VK_RIGHT) {
					if (!right2) {
						right2 = true;
					}
				}
				if (ke.getKeyCode() == ke.VK_UP) {
					if (!jumping2) {
						jumping2 = true;
						gravity2 = 7.5;
					}
				}
				////////////////////////////////////////////////////////////////////////////////////// player2
				////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_A) {
					if (!left) {
						left = true;
					}
				} // left
				if (ke.getKeyCode() == ke.VK_D) {
					if (!right) {
						right = true;
					}
				} // right
				if (ke.getKeyCode() == ke.VK_W) {
					if (!jumping) {
						jumping = true;
						gravity = 7.5;
					}
				} // up
				if (ke.getKeyCode() == ke.VK_B) // debug
					people = 2;
			}

			public void keyReleased(KeyEvent ke) {
				/////////////////////////////////////////////////////////////////////////////////////// player1
				/////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_LEFT) {
					if (left2) {
						left2 = false;
					}
				}
				if (ke.getKeyCode() == ke.VK_RIGHT) {
					if (right2) {
						right2 = false;
					}
				}
				/////////////////////////////////////////////////////////////////////////////////////// player2
				/////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_A) {
					if (left) {
						left = false;
					}
				}
				if (ke.getKeyCode() == ke.VK_D) {
					if (right) {
						right = false;
					}
				}

			}
		});
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// paint
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int x = this.getWidth();
		int y = this.getHeight();
		g2d.drawImage(tempbkg, 0, 0, x, y, null); // game background
		setFocusable(true);
		grabFocus();
		points.setText("Points: " + MONEY.total);

		////////////////////////////////////////////////////////////////////// player1
		// jump paint
		if (jumping) {
			gravity -= 0.1;
			y1 -= gravity;
			// jump=true;
			falling = false;
			if (gravity <= 0.0) {
				jumping = false;
				falling = true;
				key = 0;
			}

		}

		if (falling && key == 0) {// falling==true
			gravity += 0.1;
			y1 += gravity;
			if (y1 > 0) {
				falling = false;
			}
		}
		if (left) {
			if (x1 > 0) {
				x1 -= 2;
			}
		}
		if (right) {
			if (x1 < 1810) {
				x1 += 2;
			}
		}
		////////////////////////////////////////////////////////////////////// player2
		////////////////////////////////////////////////////////////////////// jump
		////////////////////////////////////////////////////////////////////// paint
		if (jumping2) {
			gravity2 -= 0.1;
			y2 -= gravity2;
			falling2 = false;
			if (gravity2 <= 0.0) {
				jumping2 = false;
				falling2 = true;
				key2 = 0;
			}
		}
		if (falling2 && key2 == 0) {
			gravity2 += 0.1;
			y2 += gravity2;
			if (y2 > 0) {
				falling2 = false;
			}
		}
		if (left2) {
			if (x2 > 0) {
				x2 -= 2;
			}
		}
		if (right2) {
			if (x2 < 1810) {
				x2 += 2;
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////// ground(1~3)_move
		if (move_key == 0) {
			move_dir++;
			if (move_dir == 600) {
				move_key = 1;
			}
		} else {
			move_dir--;
			if (move_dir == 0) {
				move_key = 0;
			}
		}

		if (move_key2 == 0) {
			move_dir2 += 2;
			if (move_dir2 == 600) {
				move_key2 = 1;
			}
		} else {
			move_dir2 -= 2;
			if (move_dir2 == 0) {
				move_key2 = 0;
			}
		}

		if (move_key3 == 0) {
			move_dir3 += 3;
			if (move_dir3 == 600) {
				move_key3 = 1;
			}
		} else {
			move_dir3 -= 3;
			if (move_dir3 == 0) {
				move_key3 = 0;
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////// player1

		if (falling && x1 > 1700 && x1 < 1850 && y1 > -167) {
			key = 1;
		} // 1
		else if (falling && x1 > 1500 && x1 < 1640 && y1 > -290 && y1 < -260) {
			key = 1;
		} // 2
		else if (falling && x1 > 1700 && x1 < 1850 && y1 > -430 && y1 < -410) {
			key = 1;
		} // 3
		else if (falling && x1 > 1500 && x1 < 1640 && y1 > -580 && y1 < -550) {
			key = 1;
		} // 4
		else if (falling && x1 > 1700 && x1 < 1850 && y1 > -720 && y1 < -690) {
			key = 1;
		} // 5
		else if (falling && x1 > -10 && x1 < 470 && y1 > -820 && y1 < -790) {// yellow brick
			key = 1;
		} else if (falling && x1 > -10 && x1 < 280 && y1 > -460 && y1 < -430) {// gery brick
			key = 1;
		} else if (falling && x1 > 220 && x1 < 350 && y1 > -530 && y1 < -500) {// grey brick
			key = 1;
		} else {
			key = 0;
			ground3 = false;
			ground2 = false;
			ground = false;
		}

		//////////////////////////////////////////////////
		if (jumping) {
			ground = false;
			ground2 = false;
			ground3 = false;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////// player2

		if (falling2 && x2 > 1700 && x2 < 1850 && y2 > -167) {
			key2 = 1;
		} // 1
		else if (falling2 && x2 > 1500 && x2 < 1640 && y2 > -290 && y2 < -260) {
			key2 = 1;
		} // 2
		else if (falling2 && x2 > 1700 && x2 < 1850 && y2 > -430 && y2 < -410) {
			key2 = 1;
		} // 3
		else if (falling2 && x2 > 1500 && x2 < 1640 && y2 > -580 && y2 < -550) {
			key2 = 1;
		} // 4
		else if (falling2 && x2 > 1700 && x2 < 1850 && y2 > -720 && y2 < -690) {
			key2 = 1;
		} // 5
		else if (falling2 && x2 > -10 && x2 < 470 && y2 > -820 && y2 < -790) {// player2's yellow brick
			key2 = 1;
		} // 2
		else if (falling2 && x2 > -10 && x2 < 280 && y2 > -460 && y2 < -430) {// player2's grey brick
			key2 = 1;
		} else if (falling2 && x2 > 220 && x2 < 350 && y2 > -530 && y2 < -500) {// player2's grey brick
			key2 = 1;
		} else {
			key2 = 0;
			ground4 = false;
			ground5 = false;
			ground6 = false;
		}

		////////////////////////////////////////////////// if player2 jump, moving floor
		////////////////////////////////////////////////// will unlock
		if (jumping2) {
			ground4 = false;
			ground5 = false;
			ground6 = false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		if (level_door == 1) {/////////////////////////////////////////////////////////// level 1 use moving
								/////////////////////////////////////////////////////////// floor
			g2d.drawImage(move1, 500 + move_dir, 350, null);
			g2d.drawImage(move2, 500 + move_dir2, 550, null);
			g2d.drawImage(move3, 500 + move_dir3, 750, null);
			///////////////////////////////////////////////////////////////////////////////////////////////////// player1
			if (falling && x1 > 430 + move_dir3 && x1 < 700 + move_dir3 && y1 > -190 && y1 < -160) {///// moving first
																									///// moving floor
				key = 1;
				ground3 = true;
			} else if (falling && x1 > 430 + move_dir2 && x1 < 700 + move_dir2 && y1 > -390 && y1 < -370) {///// moving
																											///// second
																											///// moving
																											///// floor
				key = 1;
				ground2 = true;
			} else if (falling && x1 > 430 + move_dir && x1 < 700 + move_dir && y1 > -590 && y1 < -570) {///// moving
																											///// third
																											///// moving
																											///// floor
				key = 1;
				ground = true;
			} // 1

			if (ground3 && move_key3 == 0) {// when player1 using trampoline, moving first floor together
				move_x1 = 3;
				x1 += move_x1;
			} else if (ground3 && move_key3 == 1) {
				move_x1 = -3;
				x1 += move_x1;
			}
			///////////////////////////////////////////////
			if (ground2 && move_key2 == 0) {// when player1 using trampoline, moving second floor together
				move_x1 = 2;
				x1 += move_x1;
			} else if (ground2 && move_key2 == 1) {
				move_x1 = -2;
				x1 += move_x1;
			}
			//////////////////////////////////////////////////
			if (ground && move_key == 0) {// when player1 using trampoline, moving third floor together
				move_x1 = 1;
				x1 += move_x1;
			} else if (ground && move_key == 1) {
				move_x1 = -1;
				x1 += move_x1;
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////// player2

			if (falling2 && x2 > 430 + move_dir3 && x2 < 700 + move_dir3 && y2 > -190 && y2 < -160) {///// player2 jump
																										///// on first
																										///// moving
																										///// floor
				key2 = 1;
				ground4 = true;
			} else if (falling2 && x2 > 430 + move_dir2 && x2 < 700 + move_dir2 && y2 > -390 && y2 < -370) {///// player2
																											///// jump
																											///// on
																											///// second
																											///// moving
																											///// floor
				key2 = 1;
				ground5 = true;
			} else if (falling2 && x2 > 430 + move_dir && x2 < 700 + move_dir && y2 > -590 && y2 < -570) {///// player2
																											///// jump
																											///// on
																											///// third
																											///// moving
																											///// floor
				key2 = 1;
				ground6 = true;
			} // 1

			///////////////////////////////////////////////// moving first floor
			if (ground4 && move_key3 == 0) {// when player2 using trampoline, moving first floor together
				move_x2 = 3;
				x2 += move_x2;
			} else if (ground4 && move_key3 == 1) {
				move_x2 = -3;
				x2 += move_x2;
			}
			////////////////////////////////////////////////// moving second floor
			if (ground5 && move_key2 == 0) {// when player2 using trampoline, moving second floor together
				move_x2 = 2;
				x2 += move_x2;
			} else if (ground5 && move_key2 == 1) {
				move_x2 = -2;
				x2 += move_x2;
			}
			////////////////////////////////////////////////// moving third floor
			if (ground6 && move_key == 0) {// when player2 using trampoline, moving third floor together
				move_x2 = 1;
				x2 += move_x2;
			} else if (ground6 && move_key == 1) {
				move_x2 = -1;
				x2 += move_x2;
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////

			if (!disappear && x1 > 30 && x1 < 90 && y1 > -880 && y1 < -850) {// player1 go in level_door
				disappear = true;
				people++;
			}
			if (!disappear2 && x2 > 30 && x2 < 90 && y2 > -880 && y2 < -850) {// player2 go in level_door
				disappear2 = true;
				people++;
			}

			if (people != 2) {
				g2d.drawImage(door, 10, 10, null); // level 1
			} else if (people == 2) {
				people = 0;
				level_door++;// go to next level
				Game.loadGame1();
				Game.cl.show(Game.cards, "one_game"); // show gamePanel when play is clicked
				disappear = false;
				disappear2 = false;
			}
		} else if (level_door == 2) {////////////////////////////////////////////////////// level 2

			if (!disappear && x1 > 1720 && x1 < 1780 && y1 > -900 && y1 < -880) {
				disappear = true;
				people++;
			}
			if (!disappear2 && x2 > 1720 && x2 < 1780 && y2 > -900 && y2 < -880) {
				disappear2 = true;
				people++;
			}
			if (people != 2) {
				g2d.drawImage(door, 1700, 0, null); // level 2
			} else if (people == 2) {
				people = 0;
				level_door++;
				Game.loadGame2();
				Game.cl.show(Game.cards, "two_game"); // show gamePanel when play is clicked
				disappear = false;
				disappear2 = false;
			} // go to next level
		} else if (level_door == 3) {////////////////////////////////////////////////////////////////////////////// level
										////////////////////////////////////////////////////////////////////////////// 3
										////////////////////////////////////////////////////////////////////////////// pick
										////////////////////////////////////////////////////////////////////////////// up
										////////////////////////////////////////////////////////////////////////////// key
										////////////////////////////////////////////////////////////////////////////// to
										////////////////////////////////////////////////////////////////////////////// unlock
										////////////////////////////////////////////////////////////////////////////// lock
			g2d.drawImage(jump_table, 500 + move_dir2, 760, 400, 350, null);// trampoline

			if (falling && x1 > 430 + move_dir2 && x1 < 730 + move_dir2 && y1 > -70 && y1 < -40) {/////// player1
																									/////// trampoline
				falling = false;
				gravity = 14;
				jumping = true;
			}
			if (falling2 && x2 > 430 + move_dir2 && x2 < 730 + move_dir2 && y2 > -70 && y2 < -40) {///// player2
																									///// trampoline
				key2 = 1;
				gravity2 = 14;
				jumping2 = true;
			} // 2

			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// level4
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// find
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// key
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// to
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// open
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////// lock
			if (level_key == 0) {
				g2d.drawImage(lock, 165, 170, 310, 440, null);
				g2d.drawImage(unlock, 300, 130, 50, 50, null);
			} else if (level_key == 1) {
				g2d.drawImage(lock, 165, 170, 310, 440, null);
				g2d.drawImage(unlock2, 1600, 655, 50, 50, null);
			}

			if (level_key == 0 && x1 > 270 && x1 < 340 && y1 > -820 && y1 < -790) {
				level_key++;
			} // get first key
			else if (level_key == 1 && x1 > 1530 && x1 < 1640 && y1 > -290 && y1 < -260) {
				level_key++;
			} // get second key
			if (level_key == 0 && x2 > 270 && x2 < 340 && y2 > -820 && y2 < -790) {
				level_key++;
			} // get first key
			else if (level_key == 1 && x2 > 1530 && x2 < 1640 && y2 > -290 && y2 < -260) {
				level_key++;
			} // get second key

			if (!disappear && x1 > 30 && x1 < 90 && y1 > -600 && y1 < -500) {
				disappear = true;
				people++;
			}
			if (!disappear2 && x2 > 30 && x2 < 90 && y2 > -600 && y2 < -500) {
				disappear2 = true;
				people++;
			}
			if (people != 2) {
				g2d.drawImage(door, 10, 350, null); // level 3
			} else if (people == 2) {
				people = 0;
				level_door++;
				Game.loadGame3();
				Game.cl.show(Game.cards, "three_game"); // show gamePanel when play is clicked
				disappear = false;
				disappear2 = false;
				level_key = 0;// initialize
			} // go to next level
		} else if (level_door == 4) {////////////////////////////////////////////////////// level 4 use trampoline to
										////////////////////////////////////////////////////// go in level_door
			g2d.drawImage(jump_table, 500 + move_dir2, 760, 400, 350, null);// trampoline

			if (falling && x1 > 430 + move_dir2 && x1 < 730 + move_dir2 && y1 > -70 && y1 < -40) {/////// player1
																									/////// trampoline
				falling = false;
				gravity = 14;
				jumping = true;
			}
			if (falling2 && x2 > 430 + move_dir2 && x2 < 730 + move_dir2 && y2 > -70 && y2 < -40) {///// player2
																									///// trampoline
				key2 = 1;
				gravity2 = 14;
				jumping2 = true;
			} // 2

			//////////////////////////////////////////////////////////////////////////////////////////////////////////
			if (!disappear && x1 > 880 && x1 < 970 && y1 > -900 && y1 < -880) {
				disappear = true;
				people++;
			}
			if (!disappear2 && x2 > 880 && x2 < 970 && y2 > -900 && y2 < -880) {
				disappear2 = true;
				people++;
			}
			if (people != 2) {
				g2d.drawImage(door, 870, -10, null);// level 4
			} else if (people == 2) {
				people = 0;
				level_door++;
				Game.loadGame4();
				Game.cl.show(Game.cards, "four_game"); // show gamePanel when play is clicked
				disappear = false;
				disappear2 = false;
                y1=760-pos;
				y2=760-pos;
			} // go to next level
		} else if (level_door == 5) {////////////////////////////////////////////////////// level 5
			g2d.drawImage(jump_table, 500 + move_dir2, 760, 400, 350, null);// trampoline
			
			if (falling && x1 > 430 + move_dir2 && x1 < 730 + move_dir2 && y1 > -70 && y1 < -40) {/////// player1
																									/////// trampoline
				falling = false;
				gravity = 14;
				jumping = true;
			}
			if (falling2 && x2 > 430 + move_dir2 && x2 < 730 + move_dir2 && y2 > -70 && y2 < -40) {///// player2
																									///// trampoline
				key2 = 1;
				gravity2 = 14;
				jumping2 = true;
			} // 2
				///////////////////////////////////////////////////////////////////////////////////////////////// trampoline
				///////////////////////////////////////////////////////////////////////////////////////////////// function
			if (!disappear && x1 > 580 + move_dir3 && x1 < 740 + move_dir3 && y1 > -900 && y1 < -880) {
				disappear = true;
				people++;
			}
			if (!disappear2 && x2 > 580 + move_dir3 && x2 < 740 + move_dir3 && y2 > -900 && y2 < -880) {
				disappear2 = true;
				people++;
			}
			if (people != 2) {
				g2d.drawImage(door, 600 + move_dir3, -10, null);
			} // level 5
			else if (people == 2) {
				people = 0;
				level_door++;
				Game.loadGame5();
				Game.cl.show(Game.cards, "five_game"); // show gamePanel when play is clicked
				disappear = false;
				disappear2 = false;
				//////////////////////////////////////////////////////////////////////////////// restart
				x1 = 5;
				y1 = 0;
				x2 = 5;
				y2 = 0;
				level_door = 1;
				ground = false;
				ground2 = false;
				ground3 = false;
				ground4 = false;
				ground5 = false;
				ground6 = false;
			} // go to next level
		}

		if (!disappear) {//////// player1_action
			if (right) {
				g2d.drawImage(player1_right, x1, y1 + pos, 100, 100, null);
			} else if (left) {
				g2d.drawImage(player1_left, x1, y1 + pos, 100, 100, null);
			} else {
				g2d.drawImage(player1, x1, y1 + pos, 100, 100, null);
			}
		}
		if (!disappear2) {//////// player2_action
			if (right2) {
				g2d.drawImage(player2_right, x2, y2 + pos2, 100, 100, null);
			} else if (left2) {
				g2d.drawImage(player2_left, x2, y2 + pos2, 100, 100, null);
			} else {
				g2d.drawImage(player2, x2, y2 + pos2, 100, 100, null);
			}
		}

		repaint();
	}// end paintComponent
}// end class

/////////////////////////// Catch the Eggs Game Panel \\\\\\\\\\\\\\\\\\\\\\\\\
public class Game extends JFrame {
	static MenuPanel mp = new MenuPanel();
	static HelpPanel hp = new HelpPanel();
	static GamePanel gp = new GamePanel();

	static one_game one;
	static two_game two;
	static three_game three;
	static four_game four;
	static five_game five;
	static victory win;

	static CardLayout cl = new CardLayout();
	static JPanel cards = new JPanel(); // to contain the panels as cards

	Game() {
		cards.setLayout(cl);// setting the layout to cardlayout
		cards.add(mp, "MenuPanel");
		cards.add(hp, "HelpPanel");
		cards.add(gp, "GamePanel");

		cl.show(cards, "MenuPanel");
		add(cards); // adding the panel with cardlayout in JFrame

		setTitle("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setLocationRelativeTo(null);
		setVisible(true); // frame visibility
	}

	static void loadGame1() {
		one_game one = new one_game();
		cards.add(one, "one_game");
	}

	static void loadGame2() {
		two_game two = new two_game();
		cards.add(two, "two_game");
	}

	static void loadGame3() {
		three_game three = new three_game();
		cards.add(three, "three_game");
	}

	static void loadGame4() {
		four_game four = new four_game();
		cards.add(four, "four_game");
	}

	static void loadGame5() {
		five_game four = new five_game();
		cards.add(four, "five_game");
	}

	static void end() {
		victory win = new victory();
		cards.add(win, "victory");
	}

	public static void main(String args[]) {
		new Game();// making an object
	}
}

// global money
class MONEY {
	public static String winner = "";
	public static int total = 0;

	static void restart() {
		total = 0;
	}
}

class one_game extends JPanel {
	Image endBackground = new ImageIcon("images\\endBackground.jpg").getImage();

	Image gamebkg = new ImageIcon("images\\game1.PNG").getImage();
	Image player1 = new ImageIcon("images\\eggB.png").getImage();
	Image player1_right = new ImageIcon("images\\eggBright.gif").getImage();
	Image player1_left = new ImageIcon("images\\eggBleft.gif").getImage();
	Image player2 = new ImageIcon("images\\eggA.png").getImage();
	Image player2_right = new ImageIcon("images\\eggAright.gif").getImage();
	Image player2_left = new ImageIcon("images\\eggAleft.gif").getImage();
	Image treasure = new ImageIcon("images\\treasure.png").getImage();
	////////////////////////////////////////////////////////////////////////////////////// wood
	Image up_wood = new ImageIcon("images\\up_wood.png").getImage();
	Image under_wood = new ImageIcon("images\\under_wood.png").getImage();
	Image up_wood2 = new ImageIcon("images\\up_wood.png").getImage();
	Image under_wood2 = new ImageIcon("images\\under_wood.png").getImage();
	Image up_wood3 = new ImageIcon("images\\up_wood.png").getImage();
	Image under_wood3 = new ImageIcon("images\\under_wood.png").getImage();
	//////////////////////////////////////////////////////////////////////////////////////
	public int x1, y1;
	public int x2, y2;

	public boolean jumping = false;
	public boolean falling = true;
	public boolean right = false;
	public boolean left = false;
	public double gravity = 0.0;// player1
	public boolean jumping2 = false;
	public boolean falling2 = true;
	public boolean right2 = false;
	public boolean left2 = false;
	public boolean ground = false;
	public double gravity2 = 0.0;// player2
	public boolean disappear = false;
	public boolean disappear2 = false;
	public boolean arrival = true;
	public boolean arrival2 = true;
	boolean isLeave = false;
	int draw = 1;
	int returnSec = 0;
	Thread musicThread;
	Music Data;
	boolean musicStop = false;

	int pos = 900, pos2 = 900, move_x1 = 0, move_x2 = 0;
	int move_dir = 0, move_key = 0, move_dir2 = 0, move_key2 = 0, move_dir3 = 0;
	int key = 0, key2 = 0;

	one_game() {
		setLayout(null);
		setFocusable(true);

		x1 = 100;
		y1 = -300;
		x2 = 5;
		y2 = -300;

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == ke.VK_B) {// debug
					arrival = true;
					arrival2 = true;
					endGame();
				}
				////////////////////////////////////////////////////////////////////////////////////// player1
				////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_LEFT) {
					if (!left2) {
						left2 = true;
					}
				}
				if (ke.getKeyCode() == ke.VK_RIGHT) {
					if (!right2) {
						right2 = true;
					}
				}
				if (ke.getKeyCode() == ke.VK_UP) {
					if (!jumping2) {
						jumping2 = true;
						gravity2 = 8;
					}
				}
				////////////////////////////////////////////////////////////////////////////////////// player2
				////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_A) {
					if (!left) {
						left = true;
					}
				} // left
				if (ke.getKeyCode() == ke.VK_D) {
					if (!right) {
						right = true;
					}
				} // right
				if (ke.getKeyCode() == ke.VK_W) {
					if (!jumping) {
						jumping = true;
						gravity = 8;
					}
				} // up
			}// end keyReleased

			public void keyReleased(KeyEvent ke) {
				/////////////////////////////////////////////////////////////////////////////////////// player1
				/////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_LEFT) {
					if (left2) {
						left2 = false;
					}
				}
				if (ke.getKeyCode() == ke.VK_RIGHT) {
					if (right2) {
						right2 = false;
					}
				}
				/////////////////////////////////////////////////////////////////////////////////////// player2
				/////////////////////////////////////////////////////////////////////////////////////// move
				if (ke.getKeyCode() == ke.VK_A) {
					if (left) {
						left = false;
					}
				}
				if (ke.getKeyCode() == ke.VK_D) {
					if (right) {
						right = false;
					}
				}
			}// end keypressed
		});

		musicStop = false;
		Data = new Music();
		musicThread = new Thread(() -> {
			while (!musicStop && !isLeave) {
				Data.playMusic("music\\1 .wav");
			}
		});
		musicThread.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int x = this.getWidth();
		int y = this.getHeight();
		g2d.drawImage(gamebkg, 0, 0, x, y, null); // game background
		setFocusable(true);
		grabFocus();

		if (draw == 1) {
			////////////////////////////////////////////////////////////////////// player1
			// jump paint
			if (jumping) {
				gravity -= 0.1;
				y1 -= gravity;
				falling = false;
				if (gravity <= 0.0) {
					jumping = false;
					falling = true;
					key = 0;
				}
			}
			if (falling && key == 0) {// falling==true
				gravity += 0.1;
				y1 += gravity;
				if (y1 > 5) {
					falling = false;
				}
			}
			if (left) {
				if (x1 > 0) {
					x1 -= 3;
				}
			}
			if (right) {
				if (x1 < 1810) {
					x1 += 3;
				}
			}
			////////////////////////////////////////////////////////////////////// player2
			////////////////////////////////////////////////////////////////////// jump
			////////////////////////////////////////////////////////////////////// paint
			if (jumping2) {
				gravity2 -= 0.1;
				y2 -= gravity2;
				falling2 = false;
				if (gravity2 <= 0.0) {
					jumping2 = false;
					falling2 = true;
					key2 = 0;
				}
			}
			if (falling2 && key2 == 0) {
				gravity2 += 0.1;
				y2 += gravity2;
				if (y2 > 5) {
					falling2 = false;
				}
			}
			if (left2) {
				if (x2 > 0) {
					x2 -= 3;
				}
			}
			if (right2) {
				if (x2 < 1810) {
					x2 += 3;
				}
			}

			g2d.drawImage(treasure, 1830, 630, 100, 100, null);// first wood
			//////////////////////////////////////////////////////////////////////////////////////////////////////////// wood
			//////////////////////////////////////////////////////////////////////////////////////////////////////////// move
			if (move_key == 0) {
				move_dir++;
				if (move_dir == 500) {
					move_key = 1;
				}
			} // first and second wood
			else {
				move_dir--;
				if (move_dir == 0) {
					move_key = 0;
				}
			}

			if (move_key2 == 0) {
				move_dir2 += 1;
				if (move_dir2 == 250) {
					move_key2 = 1;
				}
			} // third wood
			else {
				move_dir2 -= 1;
				if (move_dir2 == -250) {
					move_key2 = 0;
				}
			}
			// System.out.println(move_dir);
			//////////////////////////////////////////////////////////////////////////////////////////////////////////// set
			// game
			////////////////////////////////////////////////////////////////////////////////////////// player1
			g2d.drawImage(up_wood, 250, -640 + move_dir, 400, 1000, null);// above first wood
			g2d.drawImage(under_wood, 230, 140 + move_dir, 400, 1000, null);// below first wood

			g2d.drawImage(up_wood2, 770, -140 - move_dir, 400, 1000, null);// above second wood
			g2d.drawImage(under_wood2, 750, 640 - move_dir, 400, 1000, null);// below second wood

			g2d.drawImage(up_wood3, 1290, -390 + move_dir2, 400, 1000, null);// above third wood
			g2d.drawImage(under_wood3, 1270, 390 + move_dir2, 400, 1000, null);// below third wood

			if (falling && x1 > -10 && x1 < 230 && y1 > -190 && y1 < -160) {
				key = 1;
			} // player1 start standing place
			else if (x1 > -10 && x1 < 1900 && y1 + pos > 895) {
				x1 = 100;
				y1 = -300;
			} /////////// jump to floor
			else if (x1 > -10 && x1 < 1900 && y1 + pos < 10) {
				x1 = 100;
				y1 = -300;
			} /////////// jump to sky
			else if (x1 > 250 && x1 < 550 && y1 > -540 + move_dir) {
				x1 = 100;
				y1 = -300;
			} /////////////// above first wood
			else if (x1 > 250 && x1 < 550 && y1 < -820 + move_dir) {
				x1 = 100;
				y1 = -300;
			} //////////// below first wood
			else if (x1 > 770 && x1 < 1050 && y1 < -330 - move_dir) {
				x1 = 100;
				y1 = -300;
			} //////////// above second wood
			else if (x1 > 770 && x1 < 1050 && y1 > -70 - move_dir) {
				x1 = 100;
				y1 = -300;
			} ///////////// below second wood
			else if (x1 > 1290 && x1 < 1570 && y1 < -580 + move_dir2) {
				x1 = 100;
				y1 = -300;
			} ///////////// above third wood
			else if (x1 > 1290 && x1 < 1570 && y1 > -320 + move_dir2) {
				x1 = 100;
				y1 = -300;
			} ///////////// below third wood
			else if (falling && x1 > 1650 && x1 < 1900 && y1 > -290 && y1 < -260) {
				key = 1;
				arrival = false;
			} // end place
			else {
				key = 0;
			}

			/////////////////////////////////////////////////////////////////////////////////////////// player2

			if (falling2 && x2 > -10 && x2 < 230 && y2 > -190 && y2 < -160) {
				key2 = 1;
			} // player2 start standing place
			else if (x2 > -10 && x2 < 1900 && y2 + pos2 > 895) {
				x2 = 5;
				y2 = -300;
			} ////////// jump to floor
			else if (x2 > -10 && x2 < 1900 && y2 + pos2 < 10) {
				x2 = 5;
				y2 = -300;
			} /////////// jump to sky
			else if (x2 > 250 && x2 < 550 && y2 < -820 + move_dir) {
				x2 = 5;
				y2 = -300;
			} ///////// above first wood
			else if (x2 > 250 && x2 < 550 && y2 > -540 + move_dir) {
				x2 = 5;
				y2 = -300;
			} ////////// below first wood
			else if (x2 > 770 && x2 < 1050 && y2 < -330 - move_dir) {
				x2 = 5;
				y2 = -300;
			} ///////// above second wood
			else if (x2 > 770 && x2 < 1050 && y2 > -70 - move_dir) {
				x2 = 5;
				y2 = -300;
			} ///////// below second wood
			else if (x2 > 1290 && x2 < 1570 && y2 < -580 + move_dir2) {
				x2 = 5;
				y2 = -300;
			} ///////// above third wood
			else if (x2 > 1290 && x2 < 1570 && y2 > -320 + move_dir2) {
				x2 = 5;
				y2 = -300;
			} ///////// below third wood
			else if (falling2 && x2 > 1650 && x2 < 1900 && y2 > -290 && y2 < -260) {
				key2 = 1;
				arrival2 = false;
			} // end place
			else {
				key2 = 0;
			}

			////////////////////////////////////////////////////////////////////////////////////////////////////////////

			if (!disappear) {//////// player1_action
				if (right) {
					g2d.drawImage(player1_right, x1, y1 + pos, 100, 100, null);
				} else if (left) {
					g2d.drawImage(player1_left, x1, y1 + pos, 100, 100, null);
				} else {
					g2d.drawImage(player1, x1, y1 + pos, 100, 100, null);
				}
			}
			if (!disappear2) {//////// player2_action
				if (right2) {
					g2d.drawImage(player2_right, x2, y2 + pos2, 100, 100, null);
				} else if (left2) {
					g2d.drawImage(player2_left, x2, y2 + pos2, 100, 100, null);
				} else {
					g2d.drawImage(player2, x2, y2 + pos2, 100, 100, null);
				}
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if (arrival2 == false && arrival == false) {// if two player go to the end place, go back to main game
				x1 = 100;
				y1 = -300;
				x2 = 5;
				y2 = -300;
				arrival = true;
				arrival2 = true;
				musicStop = true;
				MONEY.total += 500;
				endGame();
			}
		} else if (draw == 2) // two player go to the end place
		{
			g2d.drawImage(endBackground, 0, 0, x, y, null);
		}

		repaint();
	}

	void endGame() {
		draw = 2;

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				if (returnSec < 3) {
					returnSec++;
				} else {
					if (!isLeave) {
						isLeave = true;
						Data.stopMusic();
						musicThread.interrupt();
						Game.cl.show(Game.cards, "GamePanel");
					}
				}
			}
		};
		timer.schedule(task, 10, 1000);
	}
}

////////////////////////////////////////////////////////////////////// game_one//////
class two_game extends JPanel {
	Image passImg = null;
	Image dancing = null;
	Image dancing2 = null;
	Image dancing3 = null;
	Image dancing4 = null;
	Image hitImg = null;

	Image l_W = null;
	Image l_A = null;
	Image l_S = null;
	Image l_D = null;
	Image l_up = null;
	Image l_left = null;
	Image l_down = null;
	Image l_right = null;

	JLabel label1;

	Image colorImg[] = new Image[8];
	Image noColorImg[] = new Image[8];
	int isLight[] = new int[8];
	int imgPos[] = new int[8];

	int tmp = 10;
	int second = 0;
	int musicLength = 37;
	int score = 0;
	int passScore = 10;

	int imgX = 250, imgY = 700, imgW = 150, imgH = 150, space = 20;

	int draw = 1;
	int repaintNum;

	Thread musicThread;
	Music Data;
	boolean isLeave = false;
	int returnSec = 0;

	two_game() {
		label1 = new JLabel("Hit: 0");
		label1.setFont(label1.getFont().deriveFont(64f));
		label1.setVisible(true);

		setLayout(new FlowLayout());
		Color c = new Color(216, 191, 216);
		setBackground(c);
		add(label1);
		setFocusable(true);
		addKeyListener(new Press());

		setVisible(true);

		MediaTracker t = new MediaTracker(this);
		l_W = Toolkit.getDefaultToolkit().getImage("images\\dance\\W_150.png");
		l_A = Toolkit.getDefaultToolkit().getImage("images\\dance\\A_150.png");
		l_S = Toolkit.getDefaultToolkit().getImage("images\\dance\\S_150.png");
		l_D = Toolkit.getDefaultToolkit().getImage("images\\dance\\D_150.png");
		l_up = Toolkit.getDefaultToolkit().getImage("images\\dance\\up_150.png");
		l_left = Toolkit.getDefaultToolkit().getImage("images\\dance\\left_150.png");
		l_down = Toolkit.getDefaultToolkit().getImage("images\\dance\\down_150.png");
		l_right = Toolkit.getDefaultToolkit().getImage("images\\dance\\right_150.png");

		t.addImage(l_W, 0);
		t.addImage(l_A, 0);
		t.addImage(l_S, 0);
		t.addImage(l_D, 0);
		t.addImage(l_up, 0);
		t.addImage(l_left, 0);
		t.addImage(l_down, 0);
		t.addImage(l_right, 0);

		draw = 1;

		dancing = Toolkit.getDefaultToolkit().getImage("images\\dance\\dance.gif");
		dancing2 = Toolkit.getDefaultToolkit().getImage("images\\dance\\dance2.gif");
		dancing3 = Toolkit.getDefaultToolkit().getImage("images\\dance\\dance3.gif");
		dancing4 = Toolkit.getDefaultToolkit().getImage("images\\dance\\dance4.gif");
		passImg = Toolkit.getDefaultToolkit().getImage("images\\dance\\pass2.png");
		hitImg = Toolkit.getDefaultToolkit().getImage("images\\dance\\hit.png");

		colorImg[0] = Toolkit.getDefaultToolkit().getImage("images\\dance\\W_COLOR.png");
		colorImg[1] = Toolkit.getDefaultToolkit().getImage("images\\dance\\A_COLOR.png");
		colorImg[2] = Toolkit.getDefaultToolkit().getImage("images\\dance\\S_COLOR.png");
		colorImg[3] = Toolkit.getDefaultToolkit().getImage("images\\dance\\D_COLOR.png");
		colorImg[4] = Toolkit.getDefaultToolkit().getImage("images\\dance\\UP_COLOR.png");
		colorImg[5] = Toolkit.getDefaultToolkit().getImage("images\\dance\\LEFT_COLOR.png");
		colorImg[6] = Toolkit.getDefaultToolkit().getImage("images\\dance\\DOWN_COLOR.png");
		colorImg[7] = Toolkit.getDefaultToolkit().getImage("images\\dance\\RIGHT_COLOR.png");

		noColorImg[0] = Toolkit.getDefaultToolkit().getImage("images\\dance\\W_150.png");
		noColorImg[1] = Toolkit.getDefaultToolkit().getImage("images\\dance\\A_150.png");
		noColorImg[2] = Toolkit.getDefaultToolkit().getImage("images\\dance\\S_150.png");
		noColorImg[3] = Toolkit.getDefaultToolkit().getImage("images\\dance\\D_150.png");
		noColorImg[4] = Toolkit.getDefaultToolkit().getImage("images\\dance\\up_150.png");
		noColorImg[5] = Toolkit.getDefaultToolkit().getImage("images\\dance\\left_150.png");
		noColorImg[6] = Toolkit.getDefaultToolkit().getImage("images\\dance\\down_150.png");
		noColorImg[7] = Toolkit.getDefaultToolkit().getImage("images\\dance\\right_150.png");

		for (int i = 0; i < 8; i++) {
			isLight[i] = 0;
			if (i != 0)
				imgPos[i] = imgX + imgW * i + space * i;
			else
				imgPos[i] = imgX + imgW * i;
		}

		Data = new Music();
		musicThread = new Thread(() -> {
			while (second < musicLength && !isLeave) {
				Data.playMusic("music\\baby-shark.wav");
			}
		});
		musicThread.start();

		Timer timer = new Timer();
		Timer timer1 = new Timer();

		TimerTask task = new TimerTask() {
			public void run() {
				if (second <= musicLength) {
					dowork();
				} else {
					endGame();
				}
			}
		};

		TimerTask task1 = new TimerTask() {
			public void run() {
				if (second <= musicLength) {
					runGif();
				}
			}
		};

		timer.schedule(task, 10, 1000);
		timer1.schedule(task1, 10, 100);
	}

	class Press extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == e.VK_B) // debug
				endGame();
			if (second <= musicLength) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_W && isLight[0] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(0);
				} else if (key == KeyEvent.VK_A && isLight[1] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(1);
				} else if (key == KeyEvent.VK_S && isLight[2] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(2);
				} else if (key == KeyEvent.VK_D && isLight[3] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(3);
				} else if (key == KeyEvent.VK_UP && isLight[4] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(4);
				} else if (key == KeyEvent.VK_LEFT && isLight[5] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(5);
				} else if (key == KeyEvent.VK_DOWN && isLight[6] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(6);
				} else if (key == KeyEvent.VK_RIGHT && isLight[7] == 1) {
					score++;
					label1.setText(String.valueOf("Hit: " + score));
					clearImg(7);
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		setFocusable(true);
		grabFocus();

		int x = this.getWidth();
		int y = this.getHeight();

		if (draw == 1) {
			g2d.drawImage(l_W, imgPos[0], imgY, imgW, imgH, null);
			g2d.drawImage(l_A, imgPos[1], imgY, imgW, imgH, null);
			g2d.drawImage(l_S, imgPos[2], imgY, imgW, imgH, null);
			g2d.drawImage(l_D, imgPos[3], imgY, imgW, imgH, null);
			g2d.drawImage(l_up, imgPos[4], imgY, imgW, imgH, null);
			g2d.drawImage(l_left, imgPos[5], imgY, imgW, imgH, null);
			g2d.drawImage(l_down, imgPos[6], imgY, imgW, imgH, null);
			g2d.drawImage(l_right, imgPos[7], imgY, imgW, imgH, null);

			repaint();
		} else if (draw == 2) {
			for (int i = 0; i < 8; i++) {
				if (isLight[i] == 1)
					g2d.drawImage(colorImg[i], imgPos[i], imgY, imgW, imgH, null);
				else
					g2d.drawImage(noColorImg[i], imgPos[i], imgY, imgW, imgH, null);
			}
			g2d.drawImage(dancing, 150, 240, 400, 400, null);
			g2d.drawImage(dancing2, 540, 240, 400, 400, null);
			g2d.drawImage(dancing3, 950, 260, 300, 400, null);
			g2d.drawImage(dancing4, 1250, 275, 400, 400, null);

			repaint();
		} else if (draw == 3) {
			g2d.drawImage(passImg, x / 2 - 600, y / 2 - 400, 1200, 800, null);
			repaint();
		} else if (draw == 4) {
			for (int i = 0; i < 8; i++) {
				if (isLight[i] == 1)
					g2d.drawImage(colorImg[i], imgPos[i], imgY, imgW, imgH, null);
				else
					g2d.drawImage(noColorImg[i], imgPos[i], imgY, imgW, imgH, null);
			}

			g2d.drawImage(hitImg, imgPos[repaintNum], imgY, 50, 50, null);
			g2d.drawImage(dancing, 150, 240, 400, 400, null);
			g2d.drawImage(dancing2, 540, 240, 400, 400, null);
			g2d.drawImage(dancing3, 950, 260, 300, 400, null);
			g2d.drawImage(dancing4, 1250, 275, 400, 400, null);

			repaint();
		}
	}

	void endGame() {
		label1.setVisible(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		for (int i = 0; i < 8; i++)
			clearImg(i);

		if (score >= passScore) {
			draw = 3;
		} else {
			draw = 3;
		}

		Timer timer4 = new Timer();
		TimerTask task4 = new TimerTask() {
			public void run() {
				if (returnSec < 3) {
					returnSec++;
				} else {
					if (!isLeave) {
						isLeave = true;
						Data.stopMusic();
						musicThread.interrupt();
						MONEY.total += score;
						Game.cl.show(Game.cards, "GamePanel");
					}
				}
			}
		};
		timer4.schedule(task4, 10, 1000);
	}

	void runGif() {
		draw = 2;
	}

	void dowork() {
		for (int i = 0; i < 8; i++) {
			isLight[i] = 0;
		}

		int count = (int) (Math.random() * 7) + 1;
		for (int i = 0; i < count; i++) {
			int num = (int) (Math.random() * 8);
			isLight[num] = 1;
		}
		draw = 2;
		second++;
	}

	void clearImg(int num) {
		isLight[num] = 0;
		repaintNum = num;
		draw = 4;
	}
}

////////////////////////////////////////////////////////////////////// game_two//////
class three_game extends JPanel {
	// region Variables
	JPanel center = new JPanel();
	JLabel board, countDown, score;
	Font font = new Font("Verdana", Font.BOLD, 30);
	Font font2 = new Font("Verdana", Font.BOLD, 40);
	Font font3 = new Font("Verdana", Font.BOLD, 60);

	Image pAL = new ImageIcon("images\\catchNslap\\catchAL.png").getImage();
	Image pAR = new ImageIcon("images\\catchNslap\\catchAR.png").getImage();
	Image pBL = new ImageIcon("images\\catchNslap\\catchBL.png").getImage();
	Image pBR = new ImageIcon("images\\catchNslap\\catchBR.png").getImage();

	Image coin = new ImageIcon("images\\catchNslap\\coin.png").getImage();
	Image shit = new ImageIcon("images\\catchNslap\\shit.png").getImage();
	Image cash = new ImageIcon("images\\catchNslap\\cash.png").getImage();
	Image gameover = new ImageIcon("images\\catchNslap\\teamwork.png").getImage();
	Image playerA = pAR, playerB = pBL;

	static int[] dropx = new int[20];// drop location x
	static int[] dropy = new int[20]; // drop location y
	static int[] dropx2 = new int[20];// drop location x
	static int[] dropy2 = new int[20]; // drop location y
	static int[] dropx3 = new int[20];// drop location x
	static int[] dropy3 = new int[20]; // drop location y

	static int[] speed = new int[20];
	static int[] speedfast = new int[20];
	static int[] speedshit = new int[20];

	int[] posCoin = new int[20];
	int[] posCash = new int[20];
	int[] posShit = new int[20];

	int x1, x2;
	int spawnCoin;
	int spawnCash;
	int spawnShit;

	int count = 10000, count2 = 10000, count3 = 10000;
	int size = 50;
	int timer = 4500;
	boolean end = false;
	boolean aF = false, aB = false, bF = false, bB = false;

	Thread musicThread;
	Music Data;
	boolean musicStop = false;
	int returnSec = 0;
	boolean isLeave = false;

	three_game() {
		setFocusable(true);
		setBackground(Color.white);
		center.setLayout(new GridLayout(2, 1));
		center.setOpaque(false);
		board = new JLabel("Total: $" + MONEY.total, SwingConstants.CENTER);
		countDown = new JLabel("CountDown: " + timer / 5, SwingConstants.CENTER);
		score = new JLabel();
		board.setFont(font);
		countDown.setFont(font2);
		center.add(board);
		center.add(countDown);
		add(center);

		x1 = 0;
		x2 = 0;
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == ke.VK_B) // debug
					endGame();
				if (ke.getKeyCode() == ke.VK_A)
					aF = true; // x1 -= 10;
				if (ke.getKeyCode() == ke.VK_D)
					aB = true; // x1 += 10;
				if (ke.getKeyCode() == ke.VK_LEFT)
					bF = true; // x2 -= 10;
				if (ke.getKeyCode() == ke.VK_RIGHT)
					bB = true; // x2 += 10;
				repaint();

			}

			public void keyReleased(KeyEvent ke) {

				if (ke.getKeyCode() == ke.VK_A)
					aF = false; // x1 -= 10;
				if (ke.getKeyCode() == ke.VK_D)
					aB = false; // x1 += 10;
				if (ke.getKeyCode() == ke.VK_LEFT)
					bF = false; // x2 -= 10;
				if (ke.getKeyCode() == ke.VK_RIGHT)
					bB = false; // x2 += 10;
				repaint();

			}
		});

		// region Object position initialize
		for (int i = 0; i < 20; i++) {
			posCoin[i] = 0;
			posCash[i] = 0;
			posShit[i] = 0;
		}

		musicStop = false;
		Data = new Music();
		musicThread = new Thread(() -> {
			while (!musicStop && !isLeave) {
				Data.playMusic("music\\3 .wav");
			}
		});
		musicThread.start();
	}

	public void paintComponent(Graphics g) {
		setFocusable(true);
		grabFocus();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int x = this.getWidth();
		int y = this.getHeight();

		if (end == false) {
			if (aF == true) {
				x1 -= 2;
				playerA = pAL;

			}
			if (aB == true) {
				x1 += 2;
				playerA = pAR;

			}
			if (bF == true) {
				x2 -= 2;
				playerB = pBL;
			}
			if (bB == true) {
				x2 += 2;
				playerB = pBR;
			}
			g2d.drawImage(playerA, x1, y - 200, null);
			g2d.drawImage(playerB, x + x2 - 100, y - 200, null);

			// region initialize speed, spawm, position
			count += 1;
			if (count >= y + 300) {
				for (int i = 0; i < 20; i++) {
					dropx[i] = (int) (Math.random() * (x - 10)) + 15;
					dropy[i] = -10 - (int) (Math.random() * 300);
					speed[i] = (int) (Math.random() * 3) + 1;
					posCoin[i] = 0;
				}
				count = 0;
				spawnCoin = (int) ((Math.random() * 15) + 5);
			}
			count2 += 2;
			if (count2 >= y + 300) {
				for (int i = 0; i < 20; i++) {
					dropx2[i] = (int) (Math.random() * (x - 10)) + 15;
					dropy2[i] = -10 - (int) (Math.random() * 300);
					speedfast[i] = (int) (Math.random() * 4) + 2;
					posCash[i] = 0;
				}
				count2 = 0;
				spawnCash = (int) ((Math.random() * 4) + 2);
			}
			count3 += 2;
			if (count3 >= y + 400) {
				for (int i = 0; i < 20; i++) {
					dropx3[i] = (int) (Math.random() * (x - 10)) + 15;
					dropy3[i] = -10 - (int) (Math.random() * 300);
					speedshit[i] = (int) (Math.random() * 5) + 2;
					posShit[i] = 0;
				}
				count3 = 0;
				spawnShit = (int) ((Math.random() * 5) + 3);
			}

			// region Coin(50) spawn and catched-control
			for (int i = 0; i <= spawnCoin; i++) {

				if (dropx[i] + 30 >= x1 && dropx[i] + size <= x1 + 130 && dropy[i] + posCoin[i] >= y - 105
						&& dropy[i] + posCoin[i] <= y - 95) {
					g2d.drawImage(coin, dropx[i], dropy[i] + posCoin[i], 0, 0, this);
					posCoin[i] += 100;
					MONEY.total += 50;
					board.setText("Total: $" + MONEY.total);
					continue;
				} else if (dropx[i] + 30 >= x + x2 - 100 && dropx[i] + size <= x + x2 - 100 + 130
						&& dropy[i] + posCoin[i] == y - 100) {
					g2d.drawImage(coin, dropx[i], dropy[i] + posCoin[i], 0, 0, this);
					posCoin[i] += 100;
					MONEY.total += 50;
					board.setText("Total: $" + MONEY.total);

					continue;
				} else {
					g2d.drawImage(coin, dropx[i], dropy[i] + posCoin[i], this);
					posCoin[i] += speed[i];
				}
			}

			// region Cash(200) spawn and catched-control
			for (int i = 0; i <= spawnCash; i++) {
				if (dropx2[i] + 30 >= x1 && dropx2[i] + size <= x1 + 130 && dropy2[i] + posCash[i] >= y - 105
						&& dropy2[i] + posCash[i] <= y - 95) {
					g2d.drawImage(cash, dropx2[i], dropy2[i] + posCash[i], 0, 0, this);
					posCash[i] += 100;
					MONEY.total += 200;
					board.setText("Total: $" + MONEY.total);

					continue;
				} else if (dropx2[i] + 30 >= x + x2 - 100 && dropx2[i] + size <= x + x2 - 100 + 130
						&& dropy2[i] + posCash[i] >= y - 105 && dropy2[i] + posCash[i] <= y - 95) {
					g2d.drawImage(cash, dropx2[i], dropy2[i] + posCash[i], 0, 0, this);
					posCash[i] += 100;
					MONEY.total += 200;
					board.setText("Total: $" + MONEY.total);

					continue;
				} else {
					g2d.drawImage(cash, dropx2[i], dropy2[i] + posCash[i], this);
					posCash[i] += speedfast[i];
				}
			}

			// region Shit(-100) spawn and catched-control
			for (int i = 0; i <= spawnShit; i++) {
				if (dropx3[i] + 30 >= x1 && dropx3[i] + size <= x1 + 130 && dropy3[i] + posShit[i] >= y - 105
						&& dropy3[i] + posShit[i] <= y - 95) {
					g2d.drawImage(shit, dropx3[i], dropy3[i] + posShit[i], 0, 0, this);
					posShit[i] += 100;
					MONEY.total -= 100;
					if (MONEY.total < 0)
						MONEY.total = 0;
					board.setText("Total: $" + MONEY.total);

					continue;
				} else if (dropx3[i] + 30 >= x + x2 - 100 && dropx3[i] + size <= x + x2 - 100 + 130
						&& dropy3[i] + posShit[i] >= y - 105 && dropy3[i] + posShit[i] <= y - 95) {
					g2d.drawImage(shit, dropx3[i], dropy3[i] + posShit[i], 0, 0, this);
					posShit[i] += 100;
					MONEY.total -= 200;
					if (MONEY.total < 0)
						MONEY.total = 0;
					board.setText("Total: $" + MONEY.total);

					continue;
				} else {
					g2d.drawImage(shit, dropx3[i], dropy3[i] + posShit[i], 60, 60, this);
					posShit[i] += speedshit[i];
				}
			}
		} else {
			g2d.drawImage(gameover, 0, 0, x, y, null);
		}

		timer -= 1;

		if (timer == -700)
			Game.cl.show(Game.cards, "GamePanel");

		if (timer % 5 == 0 && timer >= 0) {
			countDown.setText("CountDown: " + (timer / 100 % 1000));
			if (timer == 0) {
				countDown.setText("");
				board.setText("");
				add(score);
				score.setLocation(x / 2, 200);
				score.setText("Total: $" + MONEY.total);
				score.setFont(font3);

				end = true;
				musicStop = true;
				endGame();
			}
		}
		repaint();
	}

	void endGame() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				if (returnSec < 3) {
					returnSec++;
				} else {
					if (!isLeave) {
						isLeave = true;
						Data.stopMusic();
						musicThread.interrupt();
						Game.cl.show(Game.cards, "GamePanel");
					}
				}
			}
		};
		timer.schedule(task, 10, 1000);
	}
}

////////////////////////////////////////////////////////////////////// game_three////
class four_game extends JPanel {
	Image background = new ImageIcon("images\\hit\\background.png").getImage();
	Image gameoverImg = new ImageIcon("images\\hit\\gameover.png").getImage();
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	int gameoverImgX = 450;
	int gameoverImgY = 150;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Image runningManImg = null;
	Image runningEgg = null;
	Image runningEggHit = null;
	Image hammer_png = null;
	Image hammer_gif = null;

	int draw = 1;
	int runningManX = 0;
	int runningEggX = -950;
	int runningEggHitX = -650;

	Image thief[] = new Image[20];
	Image police[] = new Image[20];
	Image thief_hit[] = new Image[20];
	Image police_hit[] = new Image[20];

	int hole_who[] = new int[9];
	int hole_pic[] = new int[9];
	boolean hole_show[] = new boolean[9];
	boolean hole_isHit[] = new boolean[9];
	int hole_pos[][] = new int[9][2];

	boolean gameStart = false; // false == runnungMan

	Image bloods[] = new Image[21];
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	int bloodsX = 630;
	int bloodsY = 25;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	JLabel label = new JLabel("20");
	int blood = 20;

	int amount = 1;
	boolean choose = false;

	int mouseX = 0;
	int mouseY = 0;

	boolean hit = false;

	Image bar[] = new Image[10];
	int bar_num[] = new int[10];

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	int barX = 1655;
	int barY = 340;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	boolean isLeave = false;

	int returnSec = 0;

	Thread musicThread;
	Music Data;
	boolean musicStop = false;

	four_game() {
		setLayout(new FlowLayout());
		Color c = new Color(126, 205, 194);
		setBackground(c);
		setFocusable(true);

		runningManImg = Toolkit.getDefaultToolkit().getImage("images\\hit\\run.gif");
		runningEgg = Toolkit.getDefaultToolkit().getImage("images\\hit\\egg.gif");
		runningEggHit = Toolkit.getDefaultToolkit().getImage("images\\hit\\hit_egg.gif");
		hammer_png = Toolkit.getDefaultToolkit().getImage("images\\hit\\hammer.png");
		hammer_gif = Toolkit.getDefaultToolkit().getImage("images\\hit\\hammer.gif");

		for (int i = 0; i < 10; i++) {
			thief[i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\thief" + String.valueOf(i) + ".png");
			thief[19 - i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\thief" + String.valueOf(i) + ".png");
			police[i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\police" + String.valueOf(i) + ".png");
			police[19 - i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\police" + String.valueOf(i) + ".png");
			thief_hit[i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\thief_hit" + String.valueOf(i) + ".png");
			thief_hit[19 - i] = Toolkit.getDefaultToolkit()
					.getImage("images\\hit\\thief_hit" + String.valueOf(i) + ".png");
			police_hit[i] = Toolkit.getDefaultToolkit()
					.getImage("images\\hit\\police_hit" + String.valueOf(i) + ".png");
			police_hit[19 - i] = Toolkit.getDefaultToolkit()
					.getImage("images\\hit\\police_hit" + String.valueOf(i) + ".png");
			bar[i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\bar" + String.valueOf(i) + ".png");
		}

		for (int i = 0; i < 21; i++) {
			bloods[i] = Toolkit.getDefaultToolkit().getImage("images\\hit\\blood" + String.valueOf(i) + ".png");
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		hole_pos[0][0] = 310;
		hole_pos[0][1] = 200;// 190
		hole_pos[1][0] = 835;
		hole_pos[1][1] = 200;
		hole_pos[2][0] = 1310;
		hole_pos[2][1] = 200;
		hole_pos[3][0] = 310;
		hole_pos[3][1] = 420;
		hole_pos[4][0] = 835;
		hole_pos[4][1] = 430;
		hole_pos[5][0] = 1310;
		hole_pos[5][1] = 430;
		hole_pos[6][0] = 310;
		hole_pos[6][1] = 670;
		hole_pos[7][0] = 835;
		hole_pos[7][1] = 670;
		hole_pos[8][0] = 1310;
		hole_pos[8][1] = 680;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		bar_num[0] = 1;
		bar_num[1] = 2;
		bar_num[2] = 3;
		bar_num[3] = 4;
		bar_num[4] = 5;
		bar_num[5] = 6;
		bar_num[6] = 7;
		bar_num[7] = 8;
		bar_num[8] = 9;
		bar_num[9] = 0;

		addKeyListener(new Press());
		addMouseMotionListener(new myMouse());
		addMouseListener(new myMouse2());

		for (int i = 0; i < 9; i++) {
			hole_who[i] = 0;
			hole_pic[i] = 0;
			hole_show[i] = false;
			hole_isHit[i] = false;
		}

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				if (gameStart) {
					round();
					System.out.println("round start");
				}
			}
		};
		timer.schedule(task, 10, 15000);

		Timer timer1 = new Timer();
		TimerTask task1 = new TimerTask() {
			public void run() {
				play();
			}
		};
		timer1.schedule(task1, 10, 500);

		draw = 1;

		musicStop = false;
		Data = new Music();
		musicThread = new Thread(() -> {
			while (!musicStop && !isLeave) {
				Data.playMusic("music\\4.wav");
			}
		});
		musicThread.start();
	}

	class Press extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_B)
				endGame();
			if (!choose && e.getKeyCode() == KeyEvent.VK_SPACE) {
				for (int i = 0; i < bar_num[amount]; i++) // choose thief position
				{
					while (true) {
						int num = (int) (Math.random() * 9);
						if (hole_show[num] == false) {
							hole_show[num] = true;
							hole_who[num] = 1;
							break;
						}
					}
				}

				int remainHole = 9 - bar_num[amount]; // choose police position
				int policeAmount = (int) (Math.random() * (remainHole + 1));
				for (int i = 0; i < policeAmount; i++) {
					while (true) {
						int num = (int) (Math.random() * 9);
						if (hole_show[num] == false) {
							hole_show[num] = true;
							hole_who[num] = 0;
							break;
						}
					}
				}

				choose = true;
			}
		}
	}

	class myMouse extends MouseAdapter {
		public void mouseMoved(MouseEvent m) {
			mouseX = m.getX();
			mouseY = m.getY();
			hit = false;
		}
	}

	class myMouse2 extends MouseAdapter {
		public void mouseClicked(MouseEvent m) {
			if (choose) {
				hit = true;
				for (int i = 0; i < 9; i++) {
					if (hole_show[i]) // if is show people (police / thief)
					{
						if (hole_pos[i][0] - 20 <= mouseX - 250 && mouseX - 250 <= hole_pos[i][0] + 200
								&& mouseY >= hole_pos[i][1] && mouseY <= hole_pos[i][1] + 200) {

							if (!hole_isHit[i]) {
								// add point or minus point
								if (hole_who[i] == 0) // police
								{
									blood++;
									if (blood > 20)
										blood = 20;
								} else if (hole_who[i] == 1) // thief
								{
									blood--;
									if (blood == 0) {
										endGame();
										musicStop = true;
									}
								}
								label.setText(String.valueOf(blood));
							}

							hole_isHit[i] = true;
							if (hole_pic[i] < 10)
								hole_pic[i] = 19 - i;
						}
					}
				}
			}
		}
	}

	public void round() {
		if (gameStart) {
			choose = false;

			for (int i = 0; i < 9; i++) {
				hole_who[i] = 0;
				hole_pic[i] = 0;
				hole_show[i] = false;
				hole_isHit[i] = false;
			}

			amount = 9;
			draw = 2;
		}
	}

	public void play() {
		if (gameStart) {
			if (!choose) // thief amount
			{
				if (amount == 9) {
					amount = 0;
				} else {
					amount++;
				}
			} else // run game
			{
				draw = 3;
				for (int i = 0; i < 9; i++) {
					if (hole_pic[i] == 19)
						hole_show[i] = false;
					else
						hole_pic[i]++;
				}
			}
		}
	}

	public void endGame() {
		returnSec = 0;
		draw = 4;
		System.out.println("endGame");
		gameStart = false;

		Timer timer3 = new Timer();
		TimerTask task3 = new TimerTask() {
			public void run() {
				if (returnSec < 3) {
					returnSec++;
				} else {
					if (!isLeave) {
						Data.stopMusic();
						musicThread.interrupt();
						isLeave = true;
						Game.cl.show(Game.cards, "GamePanel");
					}
				}
			}
		};
		timer3.schedule(task3, 10, 1000);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		setFocusable(true);
		grabFocus();

		int x = this.getWidth();
		int y = this.getHeight();

		if (draw == 1) {
			g2d.drawImage(runningManImg, runningManX, 200, 600, 450, null);
			g2d.drawImage(runningEgg, runningEggX, 300, 300, 300, null);
			g2d.drawImage(runningEggHit, runningEggHitX, 300, 300, 300, null);
			runningManX += 5;
			runningEggHitX += 6;
			runningEggX += 6;

			if (runningEggX > 1300) {
				draw = 2;
				gameStart = true;
			}
			repaint();
		} else if (draw == 2) // draw move bar
		{
			g2d.drawImage(background, 0, 0, x, y, null);
			g2d.drawImage(bar[bar_num[amount]], barX, barY, 200, 400, null);
			g2d.drawImage(bloods[blood], bloodsX, bloodsY, 600, 200, null);
			repaint();
		} else if (draw == 3) // draw people & no move bar
		{
			g2d.drawImage(background, 0, 0, x, y, null);
			g2d.drawImage(bar[bar_num[amount]], barX, barY, 200, 400, null);
			g2d.drawImage(bloods[blood], bloodsX, bloodsY, 600, 200, null);

			for (int i = 0; i < 9; i++) {
				if (hole_show[i] && hole_who[i] == 1) // draw thief
				{
					if (hole_isHit[i])
						g2d.drawImage(thief_hit[hole_pic[i]], hole_pos[i][0], hole_pos[i][1], 200, 200, null);
					else
						g2d.drawImage(thief[hole_pic[i]], hole_pos[i][0], hole_pos[i][1], 200, 200, null);
				} else if (hole_show[i] && hole_who[i] == 0) // draw police
				{
					if (hole_isHit[i])
						g2d.drawImage(police_hit[hole_pic[i]], hole_pos[i][0], hole_pos[i][1], 200, 200, null);
					else
						g2d.drawImage(police[hole_pic[i]], hole_pos[i][0], hole_pos[i][1], 200, 200, null);
				}
			}

			if (!hit)
				g2d.drawImage(hammer_png, mouseX - 270, mouseY - 200, 400, 300, null);
			else
				g2d.drawImage(hammer_gif, mouseX - 270, mouseY - 200, 400, 300, null);
			repaint();
		} else if (draw == 4) {
			g2d.drawImage(bloods[blood], bloodsX, bloodsY, 600, 200, null);
			g2d.drawImage(gameoverImg, gameoverImgX, gameoverImgY, 1000, 700, null);
			repaint();
		}
	}
}

////////////////////////////////////////////////////////////////////// game_four////
class five_game extends JPanel {
	// region Variables
	int xm, ym;
	JLabel board, whosturn;
	Boolean hit = false;
	Boolean stop = false;
	Boolean hide = false;
	Boolean miss = false;

	int scoreA, scoreB = 0;
	int count = 0;
	String turn;

	JPanel center = new JPanel();
	Image pA = new ImageIcon("images\\catchNslap\\A.png").getImage(); // playerA
	Image pA_hit = new ImageIcon("images\\catchNslap\\Ahit.png").getImage();
	Image pA_hide = new ImageIcon("images\\catchNslap\\Ahide.png").getImage();
	Image pB = new ImageIcon("images\\catchNslap\\B.png").getImage(); // playerB
	Image pB_hit = new ImageIcon("images\\catchNslap\\Bhit.png").getImage();
	Image pB_hide = new ImageIcon("images\\catchNslap\\Bhide.png").getImage();

	Image slap = new ImageIcon("images\\catchNslap\\slap.png").getImage();
	Image slapA = new ImageIcon("images\\catchNslap\\slapA.png").getImage();
	Image slapB = new ImageIcon("images\\catchNslap\\slapB.png").getImage();

	Image missed = new ImageIcon("images\\catchNslap\\miss.png").getImage();

	Font font = new Font("Verdana", Font.BOLD, 30);
	Font font2 = new Font("Verdana", Font.BOLD, 50);
	int w, h;

	boolean isLeave = false;
	int returnSec = 0;
	Thread musicThread;
	Music Data;
	boolean musicStop = false;

	five_game() {
		setFocusable(true);
		setBackground(Color.white);
		// Add Condition: Assume A had Higher score so far.
		turn = "A";
		board = new JLabel("A's score: " + scoreA + "   B's score: " + scoreB, SwingConstants.CENTER);
		board.setFont(font);
		whosturn = new JLabel(turn + "\'s Turn To Slap !", SwingConstants.CENTER);
		whosturn.setFont(font2);
		center.setLayout(new GridLayout(2, 1));
		center.setBackground(Color.white);
		center.add(board);
		center.add(whosturn);
		add(center);

		// region Mouse and Keyboard
		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				xm = e.getX();
				ym = e.getY();
				if (turn == "A" && 800 + w > xm && xm > 650 + w && 550 + h > ym && ym > 400 + h && stop == false) {
					count = 0;
					hit = true;
					System.out.println("hit");
					repaint();
				} else if (turn == "B" && 450 + w > xm && xm > 300 + w && 500 + h > ym && ym > 350 + h
						&& stop == false) {
					count = 0;
					hit = true;
					System.out.println("hit");
					repaint();
				} else {
					miss = true;
					count = 0;
					System.out.println("miss");
					if (turn == "A")
						turn = "B";
					else
						turn = "A";

				}
			}

			public void mouseDragged(MouseEvent e) {
				xm = e.getX();
				ym = e.getY();
				repaint();
			}
		});

		addMouseMotionListener(new MouseAdapter() { // empty
			public void mouseMoved(MouseEvent e) {
				xm = e.getX();
				ym = e.getY();
				repaint();
			}

			public void mousePressed(MouseEvent e) {
				xm = e.getX();
				ym = e.getY();
				repaint();
			}

		});
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();

				if (turn != "A" && key == KeyEvent.VK_W && hit == true) {
					count = 0;
					hide = true;
					System.out.println("A hide");
					repaint();
					hit = false;
				} else if (turn != "B" && key == KeyEvent.VK_UP && hit == true) {
					count = 0;
					hide = true;
					System.out.println("B hide");
					repaint();
					hit = false;
				} else
					System.out.println("To hide ,pressed (W)playerA  (Up)playerB");
			}
		});

		musicStop = false;
		Data = new Music();
		musicThread = new Thread(() -> {
			while (!musicStop && !isLeave) {
				Data.playMusic("music\\5.wav");
			}
		});
		musicThread.start();
	}

	int timer = 0;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setFocusable(true);
		grabFocus();
		whosturn.setText(turn + "\'s Turn To Slap !");
		Graphics2D g2d = (Graphics2D) g;
		count++;
		int x = getWidth() / 5;
		int y = getHeight() / 5;
		w = x;
		h = y;

		// region A's turn to slap
		if (turn == "A" && hit == true && hide == false && count > 50) {
			stop = true;
			g2d.drawImage(pA, 200 + x, 200 + y, null);
			g2d.drawImage(pB_hit, 650 + x, 210 + y, null);
			g2d.drawImage(slap, 430 + x, 300 + y, null);

			if (count == 200) {
				scoreA += 10;
				board.setText("A's score: " + scoreA + "   B's score: " + scoreB);
				hit = false;
				stop = false;
				System.out.println("New Round");
				turn = "B";
			}
		} else if (turn == "A" && hide == true) {
			stop = true;
			g2d.drawImage(pA, 200 + x, 200 + y, null);
			g2d.drawImage(pB_hide, 650 + x, 200 + y, null);

			if (count == 200) {
				scoreB += 5;
				board.setText("A's score: " + scoreA + "   B's score: " + scoreB);
				hide = false;
				stop = false;
				System.out.println("New Round");
				turn = "B";
			}
		}
		// endregion
		// region B's turn to slap
		else if (turn == "B" && hit == true && hide == false && count > 50) {
			stop = true;
			g2d.drawImage(pA_hit, 200 + x, 200 + y, null);
			g2d.drawImage(pB, 600 + x, 200 + y, null);
			g2d.drawImage(slap, 430 + x, 300 + y, null);

			if (count == 200) {
				scoreB += 10;
				board.setText("A's score: " + scoreA + "   B's score: " + scoreB);
				hit = false;
				stop = false;
				System.out.println("New Round");
				turn = "A";

			}
		} else if (turn == "B" && hide == true) {
			stop = true;
			g2d.drawImage(pA_hide, 150 + x, 200 + y, null);
			g2d.drawImage(pB, 600 + x, 200 + y, null);

			if (count == 200) {
				scoreA += 5;
				board.setText("A's score: " + scoreA + "   B's score: " + scoreB);
				hide = false;
				stop = false;
				System.out.println("New Round");
				turn = "A";
			}
		}

		// region Miss situation
		else if (miss == true) {
			whosturn.setText(turn + " Missed!");
			g2d.drawImage(pA, 200 + x, 200 + y, null);
			g2d.drawImage(pB, 600 + x, 200 + y, null);
			g2d.drawImage(missed, 430 + x, 300 + y, null);
			if (count == 200)
				miss = false;
		}

		// region normal situation
		else {
			g2d.drawImage(pA, 200 + x, 200 + y, null);
			g2d.drawImage(pB, 600 + x, 200 + y, null);
			stop = false;
		}
		if (turn == "A")
			g2d.drawImage(slapA, xm - 50, ym - 50, 100, 100, this);
		if (turn == "B")
			g2d.drawImage(slapB, xm - 50, ym - 50, 100, 100, this);
		if (scoreA >= 50) {
			musicStop = true;
			MONEY.winner = "A";
			whosturn.setText("A wins!");
			timer -= 1;
		} else if (scoreB >= 50) {
			musicStop = true;
			MONEY.winner = "B";
			whosturn.setText("B wins!");
			timer -= 1;
		}
		if (timer == -500) {
			endGame();
			Game.end();
			Game.cl.show(Game.cards, "victory");
		}

		repaint();
	}

	void endGame() {
		musicStop = true;
		isLeave = true;
		Data.stopMusic();
		musicThread.interrupt();
	}
}

class victory extends JPanel {
	Image A = new ImageIcon("images\\catchNslap\\Awin.png").getImage();
	Image B = new ImageIcon("images\\catchNslap\\Bwin.png").getImage();

	JPanel center = new JPanel();
	JLabel name;
	Font font = new Font("Verdana", Font.BOLD, 40);
	int timer = 500;
	int returnSec = 0;
	boolean isLeave = false;
	Thread musicThread;
	Music Data;
	boolean musicStop = false;

	victory() {
		setFocusable(true);
		setBackground(Color.white);
		System.out.println("victory" + MONEY.winner);
		name = new JLabel();
		name.setLocation(0, 200);
		center.setOpaque(false);
		center.add(name);
		add(center);
		name.setFont(font);

		musicStop = false;
		Data = new Music();
		musicThread = new Thread(() -> {
			while (!musicStop && !isLeave) {
				Data.playMusic("music\\6.wav");
			}
		});
		musicThread.start();

		Timer timer3 = new Timer();
		TimerTask task3 = new TimerTask() {
			public void run() {
				if (returnSec < 5) {
					returnSec++;
				} else {
					if (!isLeave) {
						isLeave = true;
						musicStop = true;
						Data.stopMusic();
						musicThread.interrupt();
						MONEY.total = 0;
						MONEY.winner = "";
						MONEY.restart();
						Game.cl.show(Game.cards, "MenuPanel");
					}
				}
			}
		};
		timer3.schedule(task3, 10, 1000);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		setFocusable(true);
		grabFocus();
		int x = this.getWidth();
		int y = this.getHeight();
		if (MONEY.winner == "A") {
			name.setText("A got: $" + MONEY.total);
			g2d.drawImage(A, 50, 50, x - 100, y - 100, this);

		} else if (MONEY.winner == "B") {
			name.setText("B got: $" + MONEY.total);
			g2d.drawImage(B, 50, 50, x - 100, y - 100, this);
		}
		repaint();
	}
}