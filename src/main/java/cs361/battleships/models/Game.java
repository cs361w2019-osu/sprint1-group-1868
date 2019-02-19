package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard;
    @JsonProperty private Board opponentsBoard;

    public Game() {
        this.playersBoard = new Board();
        this.opponentsBoard = new Board();
    }
    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;

        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        //Check if the fire coordinates inside the board
        //if(x<1 || x>10 || (int)(y)-65<0 || (int)(y)-65>9)

        System.out.println("==== Player attack!");
        System.out.println("** Player's fire coordinate: " + x + " " + y);
        Result playerAttack = opponentsBoard.attack(x-1, y);
        if (playerAttack.getResult() == INVALID) {
            System.out.println("** Fire invalid! Return false to client!");
            return false;
        }

        System.out.println("==== Opponent attack!");
        Result opponentAttackResult;
        int randX;
        char randY;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            randX = randRow();
            randY = randCol();
            opponentAttackResult = playersBoard.attack(randX, randY);
        } while(opponentAttackResult.getResult() == INVALID);
        System.out.println("** Attack coordinates are: " + randX + " " + randY);

        System.out.println("==== Attack Finish!\n");
        return true;
    }

    private char randCol()
    {
        Random rand = new Random();
        return (char)(rand.nextInt(10) + 65);
    }

    private int randRow()
    {
        Random rand = new Random();
        return rand.nextInt(10);
    }

    private boolean randVertical()
    {
        return Math.random()<0.5;
    }
}
