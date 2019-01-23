package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {
	@JsonProperty private String kind;
	@JsonProperty private ArrayList<Square> occupiedSquares;
	@JsonProperty private int s_size;


	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String ship_type;
	@JsonProperty private boolean is_sunk = false;

	//This is the constructor of the ship
	public Ship()
	{
		occupiedSquares = new ArrayList<>();
	}

	//This function is used to set the ship's type
	public Ship(String kind)
	{
		ship_type = kind;		//Set the type of ship
	}

	//This function used to set the coordinates of the ship.
	public void setCoordinates(int row, char col, boolean isVerticle)
	{
		Square newsquare;
		if(isVerticle)
		{
			if(ship_type.equals("MINESWEEPER"))
			{
				for(int i = 0; i < 2; i++)
				{
					newsquare = new Square((row + i), col);
					occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("DESTROYER") )
			{
				for(int i = 0; i < 3; i++)
				{
					newsquare = new Square((row + i), col);
					occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("BATTLESHIP"))
			{
				for(int i = 0; i < 4; i++)
				{
					newsquare = new Square((row + i), col);
					occupiedSquares.add(i, newsquare);
				}
			}
		}
		else
		{
			if(ship_type.equals("MINESWEEPER"))
			{
				for(int i = 0; i < 2; i++)
				{
					newsquare = new Square(row, (char)((int)(col) + i));
					occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("DESTROYER"))
			{
				for(int i = 0; i < 3; i++)
				{
					newsquare = new Square(row, (char)((int)(col) + i));
					occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("BATTLESHIP"))
			{
				for(int i = 0; i < 4; i++)
				{
					newsquare = new Square(row, (char)((int)(col) + i));
					occupiedSquares.add(i, newsquare);
				}
			}
		}
	}

	//This function is used to set the ship status to sunk
	public void shipSunk()
	{
		is_sunk = true;
	}

	//This function is used to check if the ship sunk
	public boolean isSunk()
	{
		return is_sunk;
	}

	public List<Square> getOccupiedSquares()
	{

		return occupiedSquares;
	}
	public void takespot(int x, char y , boolean isVertical){

	}

}
