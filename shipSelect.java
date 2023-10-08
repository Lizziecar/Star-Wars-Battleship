import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class shipSelect extends JFrame {
	// background
	ImageIcon backgroundIcon = new ImageIcon();

	// border
	Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

	private JPanel contentPane;

	// global variables
	int shipSelectCounter; //counter for radio button initialization
	int positionCounterX, positionCounterY;
	int boardState = 0; // 0 = choosing point a, 1 = choosing point b
	int choosenShip = 0; //which radio button is selected 
	int savedPointAx, savedPointAy; //tracks pointA when pointB is different
	int shipsOnBoard = 0; //tracks the number of ships

	//starship locations
	starshipLocations[] battleships = new starshipLocations[5];
	int battleshipsIndex = 0;
	
	/**
	 * Create the frame.
	 */

	public shipSelect(int team) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*
		 * JButton[][] position = new JButton[11][11]; position[i][j] = new JBUtton();
		 */
		
		//panel for the ship select board
		JPanel theBoard = new JPanel();
		theBoard.setBackground(Color.WHITE);
		theBoard.setBounds(0, 10, 440, 440);
		contentPane.add(theBoard);
		theBoard.setLayout(null);
		
		//header labels for the coordinates
		JLabel headers[] = new JLabel[21];
		char[] headerLabels = { ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'X' };
		// header column
		for (int i = 0; i < 11; i++) {
			headers[i] = new JLabel(String.valueOf(headerLabels[i]), SwingConstants.CENTER);
			headers[i].setBounds(i * 40, 0, 40, 40);
			headers[i].setBackground(Color.LIGHT_GRAY);
			headers[i].setOpaque(true);
			headers[i].setBorder(border);
			theBoard.add(headers[i]);
		}
		// header row
		for (int i = 11; i < 21; i++) {
			headers[i] = new JLabel(String.valueOf(headerLabels[i]), SwingConstants.CENTER);
			headers[i].setBounds(0, (i - 10) * 40, 40, 40);
			headers[i].setBackground(Color.LIGHT_GRAY);
			headers[i].setOpaque(true);
			headers[i].setBorder(border);
			theBoard.add(headers[i]);
		}

		// button grid
		JButton[][] position = new JButton[10][10];
		int[][] positionValues = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				position[i][j] = new JButton();
				position[i][j].setBounds(i * 40 + 40, j * 40 + 40, 40, 40);
				position[i][j].setBackground(Color.LIGHT_GRAY);
				position[i][j].setBorder(border);
				theBoard.add(position[i][j]);
			}
		}
		// default state of buttons
		// 0 = blank
		// 1 = selected by player
		// 2 = highlighted by board
		// 3 = where a ship is
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				positionValues[i][j] = 0;
			}
		}

		// Ship Select Panel
		JPanel tableSide = new JPanel();
		tableSide.setBounds(520, 220, 460, 220);
		tableSide.setOpaque(false);
		contentPane.add(tableSide);
		tableSide.setLayout(null);

		// radio buttons
		JRadioButton[] shipSelect = new JRadioButton[5];
		for (int i = 0; i < 5; i++) {
			shipSelect[i] = new JRadioButton();
			shipSelect[i].setBounds(i * 60 + 10, 0, 20, 20);
			shipSelect[i].setOpaque(false);
			tableSide.add(shipSelect[i]);
			if (i == 0) { // default case
				shipSelect[i].setSelected(true);
			}
		}
		int[] shipsPlaced = new int[5]; //tracks which ships have are already on the board
		for (int i = 0; i < 5; i++) {
			shipsPlaced[i] = 0;
		}

		// ship diagrams
		JLabel[][] ships = new JLabel[5][5];
		for (int i = 0; i < 5; i++) { //loops for each ship
			if (i == 0) {//five square ship
				for (int j = 0; j < 5; j++) {
					ships[i][j] = new JLabel();
					ships[i][j].setBounds(i * 60, j * 40 + 20, 40, 40);
					ships[i][j].setBackground(Color.LIGHT_GRAY);
					ships[i][j].setOpaque(true);
					ships[i][j].setBorder(border);
					tableSide.add(ships[i][j]);
				}
			}
			if (i == 1) {//4 square ship
				for (int j = 0; j < 4; j++) {
					ships[i][j] = new JLabel();
					ships[i][j].setBounds(i * 60, j * 40 + 20, 40, 40);
					ships[i][j].setBackground(Color.LIGHT_GRAY);
					ships[i][j].setOpaque(true);
					ships[i][j].setBorder(border);
					tableSide.add(ships[i][j]);
				}
			}
			if (i == 2 || i == 3) { //both 3 square ships
				for (int j = 0; j < 3; j++) {
					ships[i][j] = new JLabel();
					ships[i][j].setBounds(i * 60, j * 40 + 20, 40, 40);
					ships[i][j].setBackground(Color.LIGHT_GRAY);
					ships[i][j].setOpaque(true);
					ships[i][j].setBorder(border);
					tableSide.add(ships[i][j]);
				}
			}
			if (i == 4) { //the 2 square ship
				for (int j = 0; j < 2; j++) {
					ships[i][j] = new JLabel();
					ships[i][j].setBounds(i * 60, j * 40 + 20, 40, 40);
					ships[i][j].setBackground(Color.LIGHT_GRAY);
					ships[i][j].setOpaque(true);
					ships[i][j].setBorder(border);
					tableSide.add(ships[i][j]);
				}
			}
		}
		
		//cpu difficulty menu
		JPanel cpuDifficultyMenu = new JPanel();
		cpuDifficultyMenu.setBounds(600, 420, 300, 70);
		cpuDifficultyMenu.setOpaque(true);
		cpuDifficultyMenu.setBackground(Color.LIGHT_GRAY);
		contentPane.add(cpuDifficultyMenu);
		cpuDifficultyMenu.setLayout(null);
		//title
		JLabel cpuTitle = new JLabel("CPU Difficulty:");
		cpuDifficultyMenu.add(cpuTitle);
		cpuTitle.setBounds(0,0,120,15);
		//slider
		JSlider cpuSlider = new JSlider();
		cpuSlider.setValue(1);
		cpuSlider.setMinimum(1);
		cpuSlider.setMaximum(3);
		cpuSlider.setBounds(0, 15, 300, 35);
		cpuSlider.setValue(1);
		cpuDifficultyMenu.add(cpuSlider);
		//labels
		JLabel easy = new JLabel("Easy");
		cpuDifficultyMenu.add(easy);
		easy.setBounds(0,50,30,15);
		JLabel hard = new JLabel("Hard");
		cpuDifficultyMenu.add(hard);
		hard.setBounds(270,50,30,15);

		// background image
		JLabel Screen = new JLabel("");
		Screen.setBounds(0, 0, 944, 501);
		getContentPane().add(Screen);
		if (team == 0) { //empire
			backgroundIcon = new ImageIcon("Empire1.png");
		}
		if (team == 1) { //rebel
			backgroundIcon = new ImageIcon("Rebels1.png");
		}
		Screen.setIcon(backgroundIcon);
		
		//battleships initilization
		for (int i = 0; i < 5; i++) {
			battleships[i] = new starshipLocations();
		}
		
		// ship selecting
		for (shipSelectCounter = 0; shipSelectCounter < 5; shipSelectCounter++) {
			shipSelect[shipSelectCounter].addActionListener(new ActionListener() {
				int j = shipSelectCounter;

				public void actionPerformed(ActionEvent e) {
					if (boardState == 0) {
						choosenShip = j;
					}
					selectShip(choosenShip, shipSelect, Screen, team);
				}
			});
		}
		
		// position selecting
		for (positionCounterX = 0; positionCounterX < 10; positionCounterX++) {
			for (positionCounterY = 0; positionCounterY < 10; positionCounterY++) {
				position[positionCounterX][positionCounterY].addActionListener(new ActionListener() {
					int i = positionCounterX, j = positionCounterY; //initialize the coordinate of the buttons

					public void actionPerformed(ActionEvent e) {
						if (boardState == 0) {//board state 0 is before the first point of the ship gets selected
							savedPointAx = i;
							savedPointAy = j;
							if (choosePointA(positionValues, choosenShip, i, j) == true) { //function to choose point a and update the squares
								boardState = 1; //condition checks whether the player selected a square that was not already selected
							}
						}
						if (boardState == 1) {//board state 1 is after the first point is selected and before the second point gets chosen
							if (choosePointB(positionValues, choosenShip, savedPointAx, savedPointAy, i, j)) { //condition checks whether the player selects a point that is allowed
								boardState = 0;
								shipPlaced(choosenShip, shipSelect, ships, Screen);
								//prevent duplicate ships
								shipsOnBoard++;
								
								//mark the ship as already chosen
								shipsPlaced[choosenShip] = 1; //1 means its chosen
								
								//change the choosenShip to the next one to the right with the radial buttons
								boolean wasChanged = false; //keeps track if default selection was done
								for (int i = choosenShip + 1; i < 5; i++) {
									if (shipsPlaced[i] == 0) {
										choosenShip = i;
										wasChanged = true;
										break;
									}
								}
								if (wasChanged == true) {
									selectShip(choosenShip, shipSelect, Screen, team);
								} else {
									choosenShip = 6;
								}

							}
						}
						if (shipsOnBoard == 5) { //condition for when all five ships are placed
							// go to the game screen
							setVisible(false);
							try {
								gameBoard frame = new gameBoard(positionValues,cpuSlider.getValue(),battleships);
								frame.setVisible(true);
							} catch (Exception f) {
								f.printStackTrace();
							}
						}
						highlightBoard(position, positionValues);
					}
				});
			}
		}

	}

	// switch background image
	public void selectShip(int selectedShip, JRadioButton[] shipSelect, JLabel Screen, int team) {
		for (int i = 0; i < 5; i++) {
			if (i == selectedShip) {
				shipSelect[i].setSelected(true);
				if (team == 0) { //for empire
					backgroundIcon = new ImageIcon("Empire" + (i + 1) + ".png");
				}
				if (team == 1) {//for rebels
					backgroundIcon = new ImageIcon("Rebels" + (i + 1) + ".png");
				}
				Screen.setIcon(backgroundIcon);
			} else {
				shipSelect[i].setSelected(false);
			}
		}
	}

	// highlight the button grid
	public void highlightBoard(JButton[][] position, int[][] positionValues) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (positionValues[i][j] == 0) {
					position[i][j].setBackground(Color.LIGHT_GRAY);
				}
				if (positionValues[i][j] == 1) {
					position[i][j].setBackground(Color.RED);
				}
				if (positionValues[i][j] == 2) {
					position[i][j].setBackground(Color.YELLOW);
				}
				if (positionValues[i][j] == 3) {
					position[i][j].setBackground(Color.DARK_GRAY);
				}
			}
		}
	}

	// choose first point of the ship
	public boolean choosePointA(int[][] positionValues, int selectedShip, int pointAx, int pointAy) {
		// check if the point clicked is blank
		if (positionValues[pointAx][pointAy] != 0) {
			return false;
		}
		// highlight selected position
		positionValues[pointAx][pointAy] = 1;

		// math to calculate where point b could be
		int sizeOfShip = 0;
		switch (selectedShip) {
		case 0:
			sizeOfShip = 5;
			break;
		case 1:
			sizeOfShip = 4;
			break;
		case 2:
			sizeOfShip = 3;
			break;
		case 3:
			sizeOfShip = 3;
			break;
		case 4:
			sizeOfShip = 2;
			break;
		}

		// variable to check for collision
		boolean isAreaClear;
		// north check
		isAreaClear = true;
		if (pointAy >= (sizeOfShip - 1)) {
			// collision check
			for (int i = 1; i < sizeOfShip; i++) {
				if (positionValues[pointAx][pointAy - i] != 0) {
					isAreaClear = false;
				}
			}
			// highlight spaces with yellow
			if (isAreaClear) {
				for (int i = 1; i < sizeOfShip; i++) {
					positionValues[pointAx][pointAy - i] = 2;
				}
			}

		}
		// east check
		isAreaClear = true;
		if ((9 - pointAx) >= (sizeOfShip - 1)) {
			// collision check
			for (int i = 1; i < sizeOfShip; i++) {
				if (positionValues[pointAx + i][pointAy] != 0) {
					isAreaClear = false;
				}
			}
			// highlight spaces with yellow
			if (isAreaClear) {
				for (int i = 1; i < sizeOfShip; i++) {
					positionValues[pointAx + i][pointAy] = 2;
				}
			}
		}
		// south check
		isAreaClear = true;
		if ((9 - pointAy) >= (sizeOfShip - 1)) {
			for (int i = 1; i < sizeOfShip; i++) {
				if (positionValues[pointAx][pointAy + i] != 0) {
					isAreaClear = false;
				}
			}
			// highlight spaces with yellow
			if (isAreaClear) {
				for (int i = 1; i < sizeOfShip; i++) {
					positionValues[pointAx][pointAy + i] = 2;
				}
			}
		}
		// west check
		isAreaClear = true;
		if (pointAx >= (sizeOfShip - 1)) {
			for (int i = 1; i < sizeOfShip; i++) {
				if (positionValues[pointAx - i][pointAy] != 0) {
					isAreaClear = false;
				}
			}
			// highlight spaces with yellow
			if (isAreaClear) {
				for (int i = 1; i < sizeOfShip; i++) {
					positionValues[pointAx - i][pointAy] = 2;
				}
			}
		}

		// if there were no available spots
		boolean availableSpace = false;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (positionValues[i][j] == 2) {
					availableSpace = true;
				}
			}
		}
		// remove the red space if there was nothing available
		if (availableSpace == false) {
			positionValues[pointAx][pointAy] = 0;
		}
		// return a success or a failure depending on if there was available spots
		return availableSpace;
	}

	// choose second point of the ship
	public boolean choosePointB(int[][] positionValues, int selectedShip, int pointAx, int pointAy, int pointBx,
			int pointBy) {
		// has to be a yellow tile
		if (positionValues[pointBx][pointBy] == 2) {
			// calculate size of ship based on selected ship
			int sizeOfShip = 0;
			switch (selectedShip) {
			case 0:
				sizeOfShip = 5;
				break;
			case 1:
				sizeOfShip = 4;
				break;
			case 2:
				sizeOfShip = 3;
				break;
			case 3:
				sizeOfShip = 3;
				break;
			case 4:
				sizeOfShip = 2;
				break;
			}

			// math to pick which direction
			// Don't click the same point again
			if (pointAx == pointBx && pointAy == pointBy) {
				return false;
			}
			int[] yLocations = new int[sizeOfShip];
			int yLocationsCounter = 0;
			// North and South:
			if (pointAx == pointBx) {
				if (pointBy > pointAy) { // south
					for (int i = pointAy; i < (pointAy + sizeOfShip); i++) {
						positionValues[pointAx][i] = 3;
						yLocations[yLocationsCounter] = i; 
						yLocationsCounter++;
					}
				} else { // north
					for (int i = pointAy; i > (pointAy - sizeOfShip); i--) {
						positionValues[pointAx][i] = 3;
						yLocations[yLocationsCounter] = i;
						yLocationsCounter++;
					}
				}
				battleships[battleshipsIndex].setShipSize(1,sizeOfShip);
				battleships[battleshipsIndex].setLocations(yLocations, pointAx);
				battleshipsIndex++;
			}
			int[] xLocations = new int[sizeOfShip];
			int xLocationsCounter = 0;
			// West and East:
			if (pointAy == pointBy) {
				if (pointAx > pointBx) { // west
					for (int i = pointAx; i > (pointAx - sizeOfShip); i--) {
						positionValues[i][pointAy] = 3;
						xLocations[xLocationsCounter] = i; 
						xLocationsCounter++;
					}
				} else { // east
					for (int i = pointAx; i < (pointAx + sizeOfShip); i++) {
						positionValues[i][pointAy] = 3;
						xLocations[xLocationsCounter] = i; 
						xLocationsCounter++;
					}
				}
				battleships[battleshipsIndex].setShipSize(0,sizeOfShip);
				battleships[battleshipsIndex].setLocations(xLocations, pointAy);
				battleshipsIndex++;
			}

			// reset all the yellow tiles
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (positionValues[i][j] == 2)
						positionValues[i][j] = 0;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public void shipPlaced(int selectedShip, JRadioButton[] shipSelect, JLabel[][] ships, JLabel Screen) {
		// hide everything associated with the selectedShip
		shipSelect[selectedShip].setSelected(false);
		shipSelect[selectedShip].setVisible(false);
		int sizeOfShip = 0;
		switch (selectedShip) {
		case 0:
			sizeOfShip = 5;
			break;
		case 1:
			sizeOfShip = 4;
			break;
		case 2:
			sizeOfShip = 3;
			break;
		case 3:
			sizeOfShip = 3;
			break;
		case 4:
			sizeOfShip = 2;
			break;
		}
		for (int i = 0; i < sizeOfShip; i++) {
			ships[selectedShip][i].setVisible(false);
		}
		backgroundIcon = new ImageIcon("ShipSelectDefault.png");
		Screen.setIcon(backgroundIcon);
	}
}
