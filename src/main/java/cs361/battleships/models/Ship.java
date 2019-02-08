package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship
{
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int s_size;
	@JsonProperty private String ship_type;
	@JsonProperty private boolean sunk;

	//This is the function to return the ship's name
	public String shipName()
	{
		return this.ship_type;
	}

	//This is the constructor of the ship
	public Ship()
	{
		this.occupiedSquares = new ArrayList<>();
		this.ship_type = new String();
		this.sunk = false;
		this.s_size = 0;
		if(ship_type.equals("MINESWEEPER")){
			this.sunk = false;
			this.s_size=2;
		}
		else if(ship_type.equals("DESTROYER")){
			this.sunk = false;
			this.s_size=3;
		}
		else if(ship_type.equals("BATTLESHIP")){
			this.sunk = false;
			this.s_size=4;
		}
	}

	//This function is used to set the ship's type
	public Ship(String kind)
	{
		this.occupiedSquares = new ArrayList<>();
		this.ship_type = kind;		//Set the type of ship
	}

	//This function used to set the coordinates of the ship.
	public void setCoordinates(int row, char col, boolean isVerticle)
	{
		Square newsquare;
		if(isVerticle)
		{
			if(this.ship_type.equals("MINESWEEPER"))
			{
				this.s_size = 2;
				for(int i = 0; i < 2; i++)
				{
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("DESTROYER") )
			{
				this.s_size = 3;
				for(int i = 0; i < 3; i++)
				{
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("BATTLESHIP"))
			{
				this.s_size = 4;
				for(int i = 0; i < 4; i++)
				{
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
			}
		}
		else
		{
			if(this.ship_type.equals("MINESWEEPER"))
			{
				this.s_size = 2;
				for(int i = 0; i < 2; i++)
				{
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(this.ship_type.equals("DESTROYER"))
			{
				this.s_size = 3;
				for(int i = 0; i < 3; i++)
				{
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(this.ship_type.equals("BATTLESHIP"))
			{
				this.s_size = 4;
				for(int i = 0; i < 4; i++)
				{
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
			}
		}
	}

	//This function is used to set the ship status to sunk
	public void shipSunk()
	{
		this.sunk = true;
	}

	//This function is used to check if the ship sunk
	public boolean isSunk()
	{
		return this.sunk;
	}

	public List<Square> getOccupiedSquares()
	{
		return this.occupiedSquares;
	}

	/*
	public void takespot(int x, char y , boolean isVertical){
		//WASD
	}
	*/

}
