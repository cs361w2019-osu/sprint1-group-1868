package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship
{
	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private List<Square> captainSquares;
	@JsonProperty private int s_size;
	@JsonProperty private String ship_type;
	@JsonProperty private boolean sunk;
	@JsonProperty private boolean submerged;
	@JsonProperty private int hp;
	@JsonProperty private int c_hp;
	@JsonProperty private int row;
	@JsonProperty private char col;
	//This is the function to return the ship's name
	public String shipName()
	{
		return this.ship_type;
	}

	//This is the constructor of the ship
	public Ship()
	{
		this.occupiedSquares = new ArrayList<>();
		this.captainSquares = new ArrayList<>();
		this.ship_type = new String();
		this.sunk = false;
		this.s_size = 0;
		this.hp = 0;
		this.c_hp = 0;
		this.row = 0;
		this.col = 'A';
	}

	//This function is used to set the ship's type
	public Ship(String kind)
	{
		this.occupiedSquares = new ArrayList<>();
		this.captainSquares = new ArrayList<>();
		this.ship_type = kind;		//Set the type of ship
		if(this.ship_type.equals("MINESWEEPER")){
			this.sunk = false;
			this.s_size=2;
			this.hp = 2;
			this.c_hp = 1;
			this.submerged = false;
		}
		else if(this.ship_type.equals("DESTROYER")){
			this.sunk = false;
			this.s_size=3;
			this.hp = 3;
			this.c_hp = 2;
			this.submerged = false;
		}
		else if(this.ship_type.equals("BATTLESHIP")){
			this.sunk = false;
			this.s_size=4;
			this.hp = 4;
			this.c_hp = 2;
			this.submerged = false;
		}
		else if(this.ship_type.equals("SUBMARINE")){
			this.sunk = false;
			this.s_size=5;
			this.hp = 5;
			this.c_hp = 2;
		}
	}

	//This function used to set the coordinates of the ship.
	public void setCoordinates(int row, char col, boolean isVerticle, boolean isSubmerged)
	{
		Square newsquare;
		Square caps;
		if(this.ship_type.equals("SUBMARINE")){
			submerged = isSubmerged;
		}
		if(isVerticle)
		{
			if(this.ship_type.equals("MINESWEEPER"))
			{
				this.s_size = 2;
				this.row = row;
				this.col = col;

				for(int i = 0; i < 2; i++)
				{
					if(i == 0){
						caps = new Square((row + i), col);
						this.captainSquares.add(caps);
					}
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("DESTROYER") )
			{
				this.s_size = 3;
				this.row = row+1;
				this.col = col;

				for(int i = 0; i < 3; i++)
				{
					if(i == 1){
						caps = new Square((row + i), col);
						this.captainSquares.add(caps);
					}
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("BATTLESHIP"))
			{
				this.s_size = 4;
				this.row = row+2;
				this.col = col;

				for(int i = 0; i < 4; i++)
				{
					if(i == 2){
						caps = new Square((row + i), col);
						this.captainSquares.add(caps);
					}
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(ship_type.equals("SUBMARINE"))
			{
				this.s_size = 5;
				this.row = row+3;
				this.col = col;

				for(int i = 0; i < 4; i++)
				{
					if(i == 3){
						caps = new Square((row + i), col);
						this.captainSquares.add(caps);
					}
					newsquare = new Square((row + i), col);
					this.occupiedSquares.add(i, newsquare);
				}
				newsquare = new Square((row + 2), (char)((int)(col) + 1));
				this.occupiedSquares.add(4, newsquare);
			}
		}
		else
		{
			if(this.ship_type.equals("MINESWEEPER"))
			{
				this.s_size = 2;
				this.row = row;
				this.col = col;

				for(int i = 0; i < 2; i++)
				{
					if(i == 0){
						caps = new Square(row, (char)((int)(col)+i));
						this.captainSquares.add(caps);
					}
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(this.ship_type.equals("DESTROYER"))
			{
				this.s_size = 3;
				this.row = row;
				this.col = (char)((int)(col)+1);

				for(int i = 0; i < 3; i++)
				{
					if(i == 1){
						caps = new Square(row, (char)((int) (col)+ i));
						this.captainSquares.add(caps);
					}
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(this.ship_type.equals("BATTLESHIP"))
			{
				this.s_size = 4;
				this.row = row;
				this.col = (char)((int)(col)+2);

				for(int i = 0; i < 4; i++)
				{
					if(i == 2){
						caps = new Square(row, (char)((int) (col)+ i));
						this.captainSquares.add(caps);
					}
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
			}
			else if(this.ship_type.equals("SUBMARINE"))
			{
				this.s_size = 5;
				this.row = row;
				this.col = (char)((int)(col)+3);

				for(int i = 0; i < 4; i++)
				{
					if(i == 3){
						caps = new Square(row, (char)((int) (col)+ i));
						this.captainSquares.add(caps);
					}
					newsquare = new Square(row, (char)((int)(col) + i));
					this.occupiedSquares.add(i, newsquare);
				}
				newsquare = new Square(row-1, (char)((int)(col) + 2));
				this.occupiedSquares.add(4, newsquare);
			}
		}
	}

	public boolean move(int direction)
	{

		int check_fg = 0;		//This flag was used to check if the ship moved to the edge

		for(int i = 0; i < this.getOccupiedSquares().size(); i++)
		{
			if(direction == 1)
			{
				this.getOccupiedSquares().get(i).setRow(this.getOccupiedSquares().get(i).getRow()-1);
				if(this.getOccupiedSquares().get(i).getRow() < 1)
				{
					check_fg = 1;
				}
			}
			else if(direction == 2)
			{
				this.getOccupiedSquares().get(i).setColumn((char)(((int)this.getOccupiedSquares().get(i).getColumn())+1));
				if((int)this.getOccupiedSquares().get(i).getColumn()-65 > 9)
				{
					check_fg = 1;
				}
			}
			else if(direction == 3)
			{
				this.getOccupiedSquares().get(i).setRow(this.getOccupiedSquares().get(i).getRow()+1);
				if(this.getOccupiedSquares().get(i).getRow() > 10)
				{
					check_fg = 1;
				}
			}
			else if(direction == 4)
			{
				this.getOccupiedSquares().get(i).setColumn((char)(((int)this.getOccupiedSquares().get(i).getColumn())-1));
				if((int)this.getOccupiedSquares().get(i).getColumn()-65 < 0)
				{
					check_fg = 1;
				}
			}
		}

		if(check_fg == 1)		//If the ship can't move
		{
			for(int i = 0; i < this.occupiedSquares.size(); i++)		//Reset the ship
			{
				if(direction == 1)
				{
					this.getOccupiedSquares().get(i).setRow(this.getOccupiedSquares().get(i).getRow()+1);
				}
				else if(direction == 2)
				{
					this.getOccupiedSquares().get(i).setColumn((char)(((int)this.getOccupiedSquares().get(i).getColumn())-1));
				}
				else if(direction == 3)
				{
					this.getOccupiedSquares().get(i).setRow(this.getOccupiedSquares().get(i).getRow()-1);
				}
				else if(direction == 4)
				{
					this.getOccupiedSquares().get(i).setColumn((char)(((int)this.getOccupiedSquares().get(i).getColumn())+1));
				}
			}
			return false;
		}
		else		//If the ship can move, move the captain square
		{
			if(direction == 1)
			{
				this.captainSquares.get(0).setRow(this.captainSquares.get(0).getRow() - 1);
			}
			else if(direction == 2)
			{
				this.captainSquares.get(0).setColumn((char)((int)this.captainSquares.get(0).getColumn() + 1));
			}
			else if(direction == 3)
			{
				this.captainSquares.get(0).setRow(this.captainSquares.get(0).getRow() + 1);
			}
			else if(direction == 4)
			{
				this.captainSquares.get(0).setColumn((char)((int)this.captainSquares.get(0).getColumn() - 1));
			}
			return true;
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

	public int returnHp()
	{
		return this.hp;
	}

	public void hit()
	{
		this.hp--;
	}

	public int returnCHp() {
		return this.c_hp;
	}

	public void hitC() {
		this.c_hp--;
	}

	public int getrow() {
		return this.row;
	}

	public char getcol() {
		return this.col;
	}

	public boolean isSubmerged(){
		return this.submerged;
	}

}
