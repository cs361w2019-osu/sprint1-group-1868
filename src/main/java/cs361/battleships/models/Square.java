package cs361.battleships.models;

@SuppressWarnings("unused")
public class Square {

	private int row;
	private char column;
	private boolean is_hit = false;			//This is used to judge if this square been hit or still not hit

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	public char getColumn()
	{
		return column;
	}

	public void setColumn(char column)
	{
		this.column = column;
	}

	public int getRow() {

		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	//Call this when hit the square
	public void hitHere()
	{
		is_hit = true;
	}

	//Call this to check the current square's status
	public boolean checkHere()
	{
		return is_hit;
	}

	//Call this when need the coordinates
	public int[] coordinates()
	{
		int[] coordinates = new int[2];
		coordinates[0] = row;
		coordinates[1] = (int)(column) - 65;
		return coordinates;
	}
}
