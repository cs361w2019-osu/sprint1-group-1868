package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {
	@JsonProperty private String kind;
	@JsonProperty private ArrayList<Square> occupiedSquares;
	@JsonProperty private int s_size;


	public Ship() {
		//occupiedSquares = new ArrayList<>();
		this.kind = new String();

	}

	public int s_size() {
		return this.s_size;
	}


	public String kind(){
		return this.kind;
	}

	public Ship(String kind) {
		this.kind = kind;
		if(kind.equals("MINESWEEPER")){
				this.s_size = 2;
				occupiedSquares = new ArrayList<>();
		}
		else if (kind.equals("DESTROYER")){
				this.s_size = 3;
				occupiedSquares = new ArrayList<>(3);
		}
		else{
				this.s_size = 4;
				occupiedSquares = new ArrayList<>(4);

		}
		//occupiedSquares = new ArrayList<>();
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}
	public void takespot(int x, char y , boolean isVertical){

	}

}
