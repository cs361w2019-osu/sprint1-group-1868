package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private String kind;
	@JsonProperty private int len; //size of the ship

	public Ship() {
		occupiedSquares = new ArrayList<>();
	}

	public Ship(String kind) {
		this.kind = kind;
		String M = "Minesweeper";
		String D = "Destroyer";
		//String B = "Battleships";

		if(kind == M){
			this.len = 2;
		}
		else if(kind == D){
			this.len = 3;
		}
		else{
			this.len = 4;
		}

		switch(this.len){
			case 2:
				occupiedSquares = new ArrayList<>(2);
			case 3:
				occupiedSquares = new ArrayList<>(3);
			case 4:
				occupiedSquares = new ArrayList<>(4);
		}
		//TODO implement
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
		//TODO implement
	}
}
