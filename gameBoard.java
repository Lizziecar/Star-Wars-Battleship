import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class gameBoard extends JFrame {
	// background
	ImageIcon backgroundIcon = new ImageIcon("ShipSelectDefault.png");

	// border
	Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

	// variables
	int locationX, locationY;
	int previousShotX = 0, previousShotY = 0;
	int isOpponentStuck = 0;
	int boardState = 0;

	starshipLocations[] enemyBattleships = new starshipLocations[5];
	int enemyBattleshipsIndex = 0;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public gameBoard(int[][] playerShips, int cpuLevel, starshipLocations[] playerShipLocations) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Board where you press to attack
		JPanel theAttackBoard = new JPanel();
		theAttackBoard.setBackground(Color.WHITE);
		theAttackBoard.setBounds(0, 10, 440, 440);
		contentPane.add(theAttackBoard);
		theAttackBoard.setLayout(null);

		// label for attack board
		JLabel attackDisplay = new JLabel("Attack Display");
		attackDisplay.setForeground(Color.WHITE);
		attackDisplay.setBounds(179, 450, 82, 15);
		contentPane.add(attackDisplay);

		// board with your ships
		JPanel theDefenseBoard = new JPanel();
		theDefenseBoard.setBackground(Color.WHITE);
		theDefenseBoard.setBounds(510, 10, 440, 440);
		contentPane.add(theDefenseBoard);
		theDefenseBoard.setLayout(null);

		// label for defense board
		JLabel playerShipDisplay = new JLabel("Ship Display");
		playerShipDisplay.setForeground(Color.WHITE);
		playerShipDisplay.setBounds(695, 450, 70, 15);
		contentPane.add(playerShipDisplay);

		// attack board stuff
		JLabel leftHeaders[] = new JLabel[21];
		char[] leftHeaderLabels = { ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '1', '2', '3', '4', '5', '6',
				'7', '8', '9', 'X' };
		// header column
		for (int i = 0; i < 11; i++) {
			leftHeaders[i] = new JLabel(String.valueOf(leftHeaderLabels[i]), SwingConstants.CENTER);
			leftHeaders[i].setBounds(i * 40, 0, 40, 40);
			leftHeaders[i].setBackground(Color.LIGHT_GRAY);
			leftHeaders[i].setOpaque(true);
			leftHeaders[i].setBorder(border);
			theAttackBoard.add(leftHeaders[i]);
		}
		// header row
		for (int i = 11; i < 21; i++) {
			leftHeaders[i] = new JLabel(String.valueOf(leftHeaderLabels[i]), SwingConstants.CENTER);
			leftHeaders[i].setBounds(0, (i - 10) * 40, 40, 40);
			leftHeaders[i].setBackground(Color.LIGHT_GRAY);
			leftHeaders[i].setOpaque(true);
			leftHeaders[i].setBorder(border);
			theAttackBoard.add(leftHeaders[i]);
		}
		// button grid
		JButton[][] attackPosition = new JButton[10][10];
		int[][] attackPositionValues = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				attackPosition[i][j] = new JButton();
				attackPosition[i][j].setBounds(i * 40 + 40, j * 40 + 40, 40, 40);
				attackPosition[i][j].setBackground(Color.LIGHT_GRAY);
				attackPosition[i][j].setBorder(border);
				theAttackBoard.add(attackPosition[i][j]);
			}
		}
		// initialize the values
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				attackPositionValues[i][j] = 0;
			}
		}

		// battleships initilization
		for (int i = 0; i < 5; i++) {
			enemyBattleships[i] = new starshipLocations();
		}

		// defense board stuff
		JLabel rightHeaders[] = new JLabel[21];
		char[] rightHeaderLabels = { ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', 'X' };
		// header column
		for (int i = 0; i < 11; i++) {
			rightHeaders[i] = new JLabel(String.valueOf(rightHeaderLabels[i]), SwingConstants.CENTER);
			rightHeaders[i].setBounds(i * 40, 0, 40, 40);
			rightHeaders[i].setBackground(Color.LIGHT_GRAY);
			rightHeaders[i].setOpaque(true);
			rightHeaders[i].setBorder(border);
			theDefenseBoard.add(rightHeaders[i]);
		}
		// header row
		for (int i = 11; i < 21; i++) {
			rightHeaders[i] = new JLabel(String.valueOf(rightHeaderLabels[i]), SwingConstants.CENTER);
			rightHeaders[i].setBounds(0, (i - 10) * 40, 40, 40);
			rightHeaders[i].setBackground(Color.LIGHT_GRAY);
			rightHeaders[i].setOpaque(true);
			rightHeaders[i].setBorder(border);
			theDefenseBoard.add(rightHeaders[i]);
		}
		// button grid
		JButton[][] defensePosition = new JButton[10][10];
		int[][] defensePositionValues = playerShips;

		// get the ship values from the last menu
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				defensePosition[i][j] = new JButton();
				defensePosition[i][j].setBounds(i * 40 + 40, j * 40 + 40, 40, 40);
				defensePosition[i][j].setBackground(Color.LIGHT_GRAY);
				defensePosition[i][j].setBorder(border);
				theDefenseBoard.add(defensePosition[i][j]);
			}
		}
		highlightBoard(defensePosition, defensePositionValues);

		// randomize ship locations for the opponent
		int randX, randY;
		for (int i = 0; i < 5; i++) {
			// get random coordinates
			do {
				randX = (int) (Math.random() * (10));
				randY = (int) (Math.random() * (10));
			} while (!choosePointA(attackPositionValues, i, randX, randY));
			int pointAx = randX, pointAy = randY;
			do {
				randX = (int) (Math.random() * (10));
				randY = (int) (Math.random() * (10));
			} while (!choosePointB(attackPositionValues, i, pointAx, pointAy, randX, randY));
		}

		// background image
		JLabel Screen = new JLabel("");
		Screen.setBounds(0, 0, 944, 501);
		getContentPane().add(Screen);
		Screen.setIcon(backgroundIcon);

		// clearing board
		int[][] attackSpaces = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				attackSpaces[i][j] = 0;
			}
		}
		highlightBoard(attackPosition, attackSpaces);

		//button logic for attacking
		for (locationX = 0; locationX < 10; locationX++) {
			for (locationY = 0; locationY < 10; locationY++) {
				attackPosition[locationX][locationY].addActionListener(new ActionListener() {
					// initial values
					int x = locationX, y = locationY;

					public void actionPerformed(ActionEvent e) {
						if (boardState == 0) {
							// miss
							if (attackPositionValues[x][y] == 0) {
								attackPositionValues[x][y] = 2;
								attackSpaces[x][y] = 2;
								boardState = 1;
							}
							// hit
							else if (attackPositionValues[x][y] == 3) {
								attackPositionValues[x][y] = 1;
								attackSpaces[x][y] = 1;
								boardState = 1;
							}
							// player clicks something already highlighted
							else {
								boardState = 0;
							}
							// check if the ship was sunk
							checkSunk(attackSpaces, enemyBattleships);
							highlightBoard(attackPosition, attackSpaces);
						}

						// opponent turn
						if (boardState == 1) {
							while (boardState == 1) {
								// attack the players ship
								int opponentX = 0, opponentY = 0;
								// level 1 algorithm:
								if (cpuLevel == 1) {
									// randomly choose a position
									opponentX = (int) (Math.random() * (10));
									opponentY = (int) (Math.random() * (10));
								}
								// level 2 algorithm
								else if (cpuLevel == 2) {
									// less random
									if (defensePositionValues[previousShotX][previousShotY] == 1) {
										int randomDirection = (int) (Math.random() * 4);
										// up
										if (randomDirection == 0) {
											opponentX = previousShotX;
											opponentY = previousShotY + 1;
										}
										// down
										if (randomDirection == 1) {
											opponentX = previousShotX;
											opponentY = previousShotY + -1;
										}
										// left
										if (randomDirection == 3) {
											opponentX = previousShotX - 1;
											opponentY = previousShotY;
										}
										// right
										if (randomDirection == 4) {
											opponentX = previousShotX + 1;
											opponentY = previousShotY;
										}
									} else {
										opponentX = (int) (Math.random() * (10));
										opponentY = (int) (Math.random() * (10));
									}

								} else if (cpuLevel == 3) {
									// essentially cheating, where you flip a coin and if heads the computer auto
									// gets a hit
									//not gonna lie I think i've only won once against this difficulty
									int coinFlip = (int) (Math.random() * (4)); // 1/4 odds
									if (coinFlip == 1) { // opponent hits
										for (int i = 0; i < 10; i++) {
											for (int j = 0; j < 10; j++) {
												if (defensePositionValues[i][j] == 3) {
													opponentX = i;
													opponentY = j;
													break;
												}
											}
										}
									} else {
										opponentX = (int) (Math.random() * (10));
										opponentY = (int) (Math.random() * (10));
									}
								}

								// opponent clicks position
								// check if it was a hit or miss
								// miss
								if (defensePositionValues[opponentX][opponentY] == 0) {
									defensePositionValues[opponentX][opponentY] = 2;
									boardState = 0;
								}
								// hit
								else if (defensePositionValues[opponentX][opponentY] == 3) {
									defensePositionValues[opponentX][opponentY] = 1;
									boardState = 0;
								}
								// anything else
								else {
									boardState = 1;
								}
								// save previous shots
								if (boardState == 0 && defensePositionValues[opponentX][opponentY] != 0) {
									previousShotX = opponentX;
									previousShotY = opponentY;
								} else if (isOpponentStuck == 4) { // its possible for the opponent to get in a loop if
																	// all around its previous hits
									previousShotX = opponentX; // are just positions they previously hit
									previousShotY = opponentY;
								} else { // so this checks if they have hit everything around the one spot, and then
											// moves on
									isOpponentStuck++;
								}
							}

							// check if the ship was sunk
							checkSunk(defensePositionValues, playerShipLocations);
							highlightBoard(defensePosition, defensePositionValues);

							// check if player won
							boolean isThereStillEnemyShips = false;
							for (int i = 0; i < 10; i++) {
								for (int j = 0; j < 10; j++) {
									if (attackPositionValues[i][j] == 3) {
										isThereStillEnemyShips = true;
									}
								}
							}
							if (isThereStillEnemyShips == false) {
								try {
									finalScreen frame = new finalScreen(1); // player wins
									frame.setVisible(true);
								} catch (Exception f) {
									f.printStackTrace();
								}
							} else {

								// check if opponent wins
								boolean isThereStillPlayerShips = false;
								for (int i = 0; i < 10; i++) {
									for (int j = 0; j < 10; j++) {
										if (defensePositionValues[i][j] == 3) {
											isThereStillPlayerShips = true;

										}
									}
								}
								if (isThereStillPlayerShips == false) {
									// player loses!
									// setVisible(false);
									try {
										finalScreen frame = new finalScreen(0); // player loses
										frame.setVisible(true);
									} catch (Exception f) {
										f.printStackTrace();
									}
								}
							}

						}
					}
				});
			}
		}

	}

	// function for colours
	static public void highlightBoard(JButton[][] position, int[][] positionValues) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (positionValues[i][j] == 0) {
					position[i][j].setBackground(Color.LIGHT_GRAY); // blank
				}
				if (positionValues[i][j] == 1) {
					position[i][j].setBackground(Color.RED); // hit
				}
				if (positionValues[i][j] == 2) {
					position[i][j].setBackground(Color.WHITE); // miss
				}
				if (positionValues[i][j] == 3) {
					position[i][j].setBackground(Color.DARK_GRAY); // ship
				}
				if (positionValues[i][j] == 4) {
					position[i][j].setBackground(Color.ORANGE); // sunk
				}
			}
		}
	}

	// choose point A
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
		if (pointAy >= (sizeOfShip)) {
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
		if ((9 - pointAx) >= (sizeOfShip)) {
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
		if ((9 - pointAy) >= (sizeOfShip)) {
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
		if (pointAx >= (sizeOfShip)) {
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
				enemyBattleships[enemyBattleshipsIndex].setShipSize(1, sizeOfShip);
				enemyBattleships[enemyBattleshipsIndex].setLocations(yLocations, pointAx);
				enemyBattleshipsIndex++;
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
				enemyBattleships[enemyBattleshipsIndex].setShipSize(0, sizeOfShip);
				enemyBattleships[enemyBattleshipsIndex].setLocations(xLocations, pointAy);
				enemyBattleshipsIndex++;
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

	public void checkSunk(int[][] positionValues, starshipLocations[] battleships) {
		for (int i = 0; i < 5; i++) {
			boolean isSunk = true;
			for (int j = 0; j < battleships[i].length(); j++) {
				if (positionValues[battleships[i].getLocationX(j)][battleships[i].getLocationY(j)] != 1) {
					isSunk = false;
				}
			}
			if (isSunk == true) {
				for (int j = 0; j < battleships[i].length(); j++) {
					positionValues[battleships[i].getLocationX(j)][battleships[i].getLocationY(j)] = 4;
				}
			}
		}
	}
}
