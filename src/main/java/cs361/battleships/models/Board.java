package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
	@JsonProperty private List<Square> squares ;
	@JsonProperty private List<Ship> ships;
	//@JsonProperty private List<Result> attact_results;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement

		this.ships = new ArrayList<>();   //initail with 0 ships within it
		/*for(int i = 0; i < 10; i++){
			for(int j = 0 ; j < i ; j++){
				this.squares[i][j] = new Square(x,y);
				System.out.println(this.squares[i].get(j).getRow());
				System.out.print(this.squares[i][j].getColumn());
				y++;
			}
			x++;
		}*/
		//this.attact_results = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement

		if(getShips().size()==0) {
			 if(check_location(getShips().size(), x, y , isVertical)) {
			 	ship.takespot(x,y,isVertical);
				this.getShips().add(ship);
				return true;
			}
		}



		return false;
	}

	private boolean check_location(int size, int x, char y, boolean vertical){
			return false;
	}
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		//TODO implement

		return this.ships;
	}

	public void setShips(List<Ship> ships) {

		//TODO implement
		this.ships = ships;
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}
