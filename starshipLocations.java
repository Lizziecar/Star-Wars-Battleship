
public class starshipLocations {//this program tracks where the player and opponents place their ships so that they can be marked as sunk
	private int direction;
	// horizontal {A horizontal ship has the same y coordinate for each square}
	private int[] locationsX;
	private int locationY;
	// vertical {A vertical ship has the same x coordinate for each square}
	private int[] locationsY;
	private int locationX;

	//set the locations of the squares of the ship
	public void setLocations(int[] variableLocation, int constantLocation) {// 0 is horizontal, 1 is
																							// vertical
		if (direction == 0) { // horizontal -> x changes, y doesn't
			this.locationsX = variableLocation;
			this.locationY = constantLocation;
		}
		if (direction == 1) { // vertical -> y changes, x doesn't
			this.locationX = constantLocation;
			this.locationsY = variableLocation;
		}
	}
	
	//set the size of the ship
	public void setShipSize(int direction, int shipSize) {
		this.direction = direction;
		if (this.direction == 0) {
			locationsX = new int[shipSize];
		} else {
			locationsY = new int[shipSize];
		}
	}
	
	//get the x location which is either a constant int for all the squares (vertical) or a list of incrementing ints (Horizontal)
	public int getLocationX(int index) {
		if (this.direction == 0) {
			return this.locationsX[index];
		} else {
			return this.locationX;
		}
	}
	
	//get the y location which is either a constant int for all the squares (horizontal) or a list of incrementing ints (vertical)
	public int getLocationY(int index) {
		if (this.direction == 1) {
			return this.locationsY[index];
		} else {
			return this.locationY;
		}
	}
	
	//returns the length of the ship
	public int length() {
		if (this.direction == 0) {
			return this.locationsX.length;
		} else {
			return this.locationsY.length;
		}
	}
}
