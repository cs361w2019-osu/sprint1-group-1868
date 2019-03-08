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
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical, boolean isSubmerged) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical, isSubmerged);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;

        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical(), randSubmerged());
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

    private boolean checkSonar(int x, char y)
    {
        //First check if the game already start
        if(opponentsBoard.getShips().size() != 3)
        {
            System.out.println("** Sonar Attack check, not gaming phase!");
            return false;
        }
        //Second check if the sonar attack could deploy on the board
        int sunk_num = 0;
        for(int i = 0; i < 3; i++)
        {
            if(opponentsBoard.getShips().get(i).isSunk())
            {
                sunk_num++;
            }
        }
        if(sunk_num == 0)
        {
            System.out.println("** Sonar Attack check, not sinking yet!");
            return false;
        }
        //Third check if the coordinate in the board, is this really useful?
        if(x<1 || x>10 || (int)(y) < 65 || (int)(y) > 74)
            return false;
        //Fourth check if the sonar already used twice
        if(this.playersBoard.getSonar_pulse().size() == 2)
            return false;
        //If all pass, return true
        return true;
    }

    public boolean sonarAttack(int x, char y)
    {
        System.out.println("==== Sonar Attack!");
        if(checkSonar(x, y))        //If the sonar valid
        {
            this.playersBoard.setSonar_pulse(x, y);
            System.out.println("** Sonar Attack Succeed!");
            return true;
        }
        System.out.println("** Sonar Attack Fail!");
        return false;
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

    private boolean randSubmerged()
    {
        return Math.random()<0.5;
    }
}
