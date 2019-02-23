package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Square[][] board;			//This is the battle board
	@JsonProperty private List<Ship> ships;			//This is the ship list owned by player
	@JsonProperty private List<Result> attacks;
	@JsonProperty private List<Square> sonar_pulse;         //This is used to store the sonar pulse's coordinate
	private int ship_num = 0;


	/*
	DO NOT change the signature of this method. It is used by the grading scripts.

	***This is the constructor of the Board class, it should initial all the variables when object was created.
	 */
	public Board()
	{
		//Initial the ship list
		this.ships = new ArrayList<>();

		//Initial the board
		this.board = new Square[10][10];

		//Initial the attack
		this.attacks = new ArrayList<>();

		//Initial the sonar pulse
		this.sonar_pulse = new ArrayList<>();

		//Initial the game board, a 10x10 square 2D array.
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				this.board[i][j] = new Square();
				this.board[i][j].setRow(i);
				this.board[i][j].setColumn((char)(j+65));	//Convert j into Uppercase Character
			}
		}
	}

	public void setSonar_pulse(int x, char y)
	{
		Square newSonar = new Square();
		newSonar.setRow(x);
		newSonar.setColumn(y);
		this.sonar_pulse.add(sonar_pulse.size(), newSonar);
		System.out.println("** Sonar coordinate added!");
		System.out.println(sonar_pulse);
	}

	public List<Square> getSonar_pulse()
	{
		return this.sonar_pulse;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		//Check if the new ship type already existed

		for (int i = 0; i < ships.size(); i++) {
			if (ships.get(i).shipName().equals(ship.shipName())) {

				//for(int i = 0; i < ships.size(); i++)
				{
					//		if(ships.get(i).shipName().equals(ship.shipName()))
					//	{

					return false;
				}
			}
		}

		if (ship.shipName().equals("MINESWEEPER")) {
			if (isVertical) {
				if (x < 1 || x > 10 || (int) (y) - 65 < 0 || (int) (y) - 65 > 9 || x + 1 > 10) {
					return false; }
			} else {
				if (x < 1 || x > 10 || (int) (y) - 65 < 0 || (int) (y) - 65 > 9 || (int) (y) - 65 + 1 < 0 || (int) (y) - 65 + 1 > 9) {
					return false; }
			}
		} else if (ship.shipName().equals("DESTROYER")) {
			if (isVertical) {
				if (x < 1 || x > 10 || (int) (y) - 65 < 0 || (int) (y) - 65 > 9 || x + 2 > 10) {
					return false; }
			} else {
				if (x < 1 || x > 10 || (int) (y) - 65 < 0 || (int) (y) - 65 > 9 || (int) (y) - 65 + 2 < 0 || (int) (y) - 65 + 2 > 9) {
					return false; }
			}
		} else if (ship.shipName().equals("BATTLESHIP")) {
			if (isVertical) {
				if (x < 1 || x > 10 || (int) (y) - 65 < 0 || (int) (y) - 65 > 9 || x + 3 > 10) {
					return false; }
			} else {
				if (x < 1 || x > 10 || (int) (y) - 65 < 0 || (int) (y) - 65 > 9 || (int) (y) - 65 + 3 < 0 || (int) (y) - 65 + 3 > 9) {
					return false; }
			}
		}
		Ship nShip = new Ship(ship.shipName());
		nShip.setCoordinates(x, y, isVertical);
		this.ships.add(this.ship_num, nShip);
		this.ship_num++;
		return true;
	}

	//This function used to check if the fire coordinate is valid or not
	public boolean checkFirevalid(int x, char y)
	{
		System.out.println("==== Check if fire valid!");
		//Check if the coordinate out of the board
		if(x < 0 || x > 9 || (int)(y)-65 < 0 || (int)(y)-65 > 9) {
			System.out.println("** Fire coordinate not valid!");
			return false;
		}
		else
		{
			//Check if already hit here
			for(int i = 0; i < this.attacks.size(); i++) {
				if (this.attacks.get(i).getLocation().getRow() == (x+1) && this.attacks.get(i).getLocation().getColumn() == y) {
				    //Check if this is the captain quarter
					for(int j=0; j < this.ships.size(); j++){
						if(x+1 == this.ships.get(j).getrow() && y == this.ships.get(j).getcol() && this.ships.get(j).returnCHp() > 0)
						{
							return true;
						}
					}

					System.out.println("** Fire coordinate not valid!");
					return false;
				}
			}
		}

		System.out.println("** Fire coordinate is valid!");
		return true;
	}
	
	
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.

	***I assume the hit coordinates is valid here since it's not my job to check that.
	***Function Input:			The attack coordinates
	***Function Output:			The attack result
	***Function Description:	This function will do the following thing:
	* 				1. Change the hit square's status
	* 				2. Check if the shot has hit any ship owned by current player
	* 				3. Check if the shot has hit any ship owned by current player to sunk
	* 				4. Check if the shot has made current player surrender
	* 				5. Return the hit status
	 */
	public Result attack(int x, char y)
	{

		//Initial the send back result
		Result currentresult = new Result();

		if (checkFirevalid(x, y)) {
			//Initial the sunk checking flag
			int sunk_num = 0;

			//Record the hit location
			Square fireSquare = new Square();
			fireSquare.setRow(x+1);
			fireSquare.setColumn(y);
			currentresult.setLocation(fireSquare);

			//Initial the current hit result to MISS, this will change if checked hit the ship.
			//AtackStatus result = AtackStatus.MISS;
			AtackStatus result = AtackStatus.MISS;

			System.out.println("==== Checking fire result!");

			//Check all three ships one by one for the shot result
			for(int i = 0; i < this.ships.size(); i++) {
				//Check each square of each ships one by one
				for(int j = 0; j < this.ships.get(i).getOccupiedSquares().size(); j++) {
					//This means if the attack coordinates match the ship's coordinates, the ship been hit
					if(this.ships.get(i).getOccupiedSquares().get(j).getRow()-1 == x && ships.get(i).getOccupiedSquares().get(j).getColumn() == y ) {
						if(this.ships.get(i).getrow() == x+1 && this.ships.get(i).getcol() == y){
							result = AtackStatus.CAPTAIN;
							this.ships.get(i).hitC();

							if(this.ships.get(i).returnCHp() == 0) {
								result = AtackStatus.SUNK;
								this.ships.get(i).shipSunk();
								for(int k = 0; k < this.ships.get(i).getOccupiedSquares().size(); k ++)
								{
										int checkFlag = 0;
										for(int l = 0; l < attacks.size(); l++)
										{
											if(this.ships.get(i).getOccupiedSquares().get(k).getRow() == attacks.get(l).getLocation().getRow() && this.ships.get(i).getOccupiedSquares().get(k).getColumn() == attacks.get(l).getLocation().getColumn())
											{
												checkFlag = 1;
											}
										}
										if(checkFlag == 0)
										{
											Square newSquare = new Square();
											newSquare.setRow(this.ships.get(i).getOccupiedSquares().get(k).getRow());
											newSquare.setColumn(this.ships.get(i).getOccupiedSquares().get(k).getColumn());
											Result newResult = new Result();
											newResult.setLocation(newSquare);
											newResult.setResult(AtackStatus.HIT);
											attacks.add(attacks.size(), newResult);
										}
									}
								}
						}

						else{
							result = AtackStatus.HIT;
							this.ships.get(i).hit();

							//Check if the ship been hit to sunk
							if(this.ships.get(i).returnHp() == 0) {
								result = AtackStatus.SUNK;
								this.ships.get(i).shipSunk();        //Set the ship to sunk
							}
						}
					}
				}
				//Check if the ship sunk
				if(this.ships.get(i).isSunk()) {
					sunk_num++;
				}
			}

			//Check if the player surrender after the shot, if the player surrender, then change the attack result to SURRENDER.
			if(sunk_num == 3) {
				result = AtackStatus.SURRENDER;
			}

			System.out.println("==== Packaging fire result!");

			//Write the hit result into the send back result
			currentresult.setResult(result);

			//Add attack into attack list
			attacks.add(attacks.size(), currentresult);


		}
		else
        {
			currentresult.setResult(AtackStatus.INVALID);
		}

		System.out.println("==== Returning fire result!");
		return currentresult;
	}


	//This function is used to get all the ships belong to the player
	public List<Ship> getShips()
	{
		return this.ships;

	}

	public void setShips(List<Ship> ships)
	{
		this.ships = ships;
	}

	public List<Result> getAttacks(){
		return this.attacks;
	}
	
	public void setAttacks(List<Result> attacks){
		this.attacks = attacks;
	}
}
