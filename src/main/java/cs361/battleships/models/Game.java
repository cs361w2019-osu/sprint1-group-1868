package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard;
    @JsonProperty private Board opponentsBoard;
    @JsonProperty private int move;

    public Game() {
        this.playersBoard = new Board();
        this.opponentsBoard = new Board();
        this.move = 2;
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

        laserAttack(x-1, y);

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

    /*
    This function used to move the ship after player sunk two enemy's ship, and could only do twice
    1 -- N
    2 -- E
    3 -- S
    4 -- W
    */
    public boolean moveFleet(int direction)
    {
        if(move == 0)       //If already used two move
        {
            return false;
        }

        //Check if sunk two enemy's ship
        int sink_num = 0;
        for(int i = 0; i < 4; i++)      //Check each ship
        {
            if(opponentsBoard.getShips().get(i).isSunk() == true)
            {
                sink_num++;
            }
        }

        if(sink_num < 2)       //If less than two ship has sunk
        {
            return false;
        }

        //Init the check flag
        int[] fg = {0, 0, 0, 0};

        //Start move the fleet
        boolean[] mfg = playersBoard.move(direction);


        for(int m = 0; m < 3; m++)      //Check three times, in order to correct all ships
        {
            for (int i = 0; i < 4; i++)      //Check if collide
            {
                for (int j = 0; j < playersBoard.getShips().get(i).getOccupiedSquares().size(); j++) {
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < playersBoard.getShips().get(k).getOccupiedSquares().size(); l++) {
                            if (i == k)      //If the same ship
                            {
                                continue;       //Pass to next
                            }

                            //If find the same square
                            if (playersBoard.getShips().get(i).getOccupiedSquares().get(j).getColumn() == playersBoard.getShips().get(k).getOccupiedSquares().get(l).getColumn() && playersBoard.getShips().get(i).getOccupiedSquares().get(j).getRow() == playersBoard.getShips().get(k).getOccupiedSquares().get(l).getRow()) {
                                System.out.println("==== Detected! " + playersBoard.getShips().get(i).shipName() + " and " + playersBoard.getShips().get(k).shipName() + "\n");
                                //Check if there is submarine
                                if (playersBoard.getShips().get(i).isSubmerged() == true || playersBoard.getShips().get(k).isSubmerged() == true) {
                                    continue;       //If there is submerged submarine, then pass to next
                                }

                                //If still not pass, reset two ships
                                fg[i] = 1;
                                fg[k] = 1;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 4; i++)         //Operation for each ships
            {
                if (fg[i] == 1 && mfg[i])        //If the ship need to be reset and the ship has moved
                {
                    fg[i] = 0;              //Reset the flag
                    mfg[i] = false;        //Set the ship to not moved
                    if (direction == 1) {
                        playersBoard.getShips().get(i).move(3);
                    } else if (direction == 2) {
                        playersBoard.getShips().get(i).move(4);
                    } else if (direction == 3) {
                        playersBoard.getShips().get(i).move(1);
                    } else if (direction == 4) {
                        playersBoard.getShips().get(i).move(2);
                    }
                }
            }
        }

        move--;     //If passed all check, reduce one move
        return true;
    }

    private boolean checkSonar(int x, char y)
    {
        //First check if the game already start
        if(opponentsBoard.getShips().size() != 4)
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

    public void laserAttack(int x, char y){

        if(checkLaser(x, y))
        {
            System.out.println("==== Laser Attack!");
            this.opponentsBoard.setSwitch(true);     
            System.out.println("** Laser Attack Succeed!");

        }
        System.out.println("** Laser Attack Fail!");

    }

    private boolean checkLaser(int x, char y)
    {
        //First check if the game already start
        if(opponentsBoard.getShips().size() != 4)
        {
            System.out.println("** laser Attack check, not gaming phase!");
            return false;
        }
        //Second check if the sonar attack could deploy on the board
        int sunk = 0;
        for(int i = 0; i < 4; i++)
        {
            if(opponentsBoard.getShips().get(i).isSunk())
            {
                sunk++;
            }
        }
        if(sunk == 0)
        {
            System.out.println("** laser Attack check, not sinking yet!");
            return false;
        }
        //Third check if the coordinate in the board, is this really useful?
        if(x<1 || x>10 || (int)(y) < 65 || (int)(y) > 74)
            return false;


        //If all pass, return true
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

    private boolean randSubmerged()
    {
        return Math.random()<0.5;
    }
}
