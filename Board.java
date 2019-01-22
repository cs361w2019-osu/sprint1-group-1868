package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private int miss;
	private int hit;
	private int unhit;
	private int unknown;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		// TODO Implement
		miss = 0;
		hit = 0;
		unhit = 9;
		unknown = 100;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		int area = ship.getOccupiedSquares();
		int yy = y;
		yy -= 64;
		if(isVertical){
			if((x+area)>10 || (x-area)<0){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			if((yy+area)>10 || (yy-area)<0){
				return false;
			}
			else{
				return true;
			}
		}
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


		return null;
	}

	public void setShips(List<Ship> ships) {
		//TODO implement
	}

	public List<Result> getAttacks() {
		//TODO implement
		AtackStatus a = AtackStatus.HIT;
		Result r = new Result();

		return r.setResult(a);
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
		if(attacks.getResult() == "MISS"){
			return false;
		}
		else if(attacks.getResult() == "HIT"){
			return false;
		}
		else if(attacks.getResult() == "SUNK"){
			return false;
		}
		else{
			return true;
		}
	}
}
