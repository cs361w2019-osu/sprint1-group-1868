package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testInvalidPlacement()
    {
        Board board = new Board();
        Board board_a = new Board();
        //enemy board error setting --------------------------------------------------------------------
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 10, 'J', true));     //To check if the place ship will throw error if in invalid position
        assertFalse(board_a.placeShip(new Ship("BATTLESHIP"), 10, 'J', true));
        assertFalse(board_a.placeShip(new Ship("DESTROYER"), 10, 'J', true));
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 0, 'J', false));
        assertFalse(board_a.placeShip(new Ship("BATTLESHIP"), 0, 'J', false));
        assertFalse(board_a.placeShip(new Ship("DESTROYER"), 0, 'I', false));
        //---------------------------------------------------------------------------------------------

        //enemy board correct setting------------------------------------------------------------------
        assertTrue(board_a.placeShip(new Ship("MINESWEEPER"), 2, 'B', true));       //To check if the place ship will correct insert the ship
        //error becuase square overlap
        assertFalse(board_a.placeShip(new Ship("BATTLESHIP"), 2, 'B', false));
        assertTrue(board_a.placeShip(new Ship("BATTLESHIP"), 6, 'D', true));
        //error becasue place same ship again
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 7, 'H', false));
        assertTrue(board_a.placeShip(new Ship("DESTROYER"), 7, 'H', true));
        //--------------------------------------------------------------------------------------------


        //player board error setting----------------------------------------------------------------------
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', true));     //To check if the place ship will throw error if in invalid position
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 10, 'J', true));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 10, 'J', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 0, 'J', false));
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 0, 'J', false));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 0, 'I', false));
        //------------------------------------------------------------------------------------------------

        //player board correct setting -------------------------------------------------------------------
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 3, 'B', true));       //To check if the place ship will correct insert the ship
        //error becuase square overlap
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 3, 'B', false));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 5, 'D', true));
        //error becasue place same ship again
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 7, 'H', false));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 4, 'H', false));
        //----------------------------------------------------------------------------------------------
        //Some miss hit result on both board
        assertTrue(board.attack(1,'B').getResult() == AtackStatus.MISS);
        assertTrue(board_a.attack(7,'B').getResult() == AtackStatus.MISS);
        assertTrue(board.attack(7,'I').getResult() == AtackStatus.MISS);
        assertTrue(board_a.attack(5,'I').getResult() == AtackStatus.MISS);
        assertTrue(board.attack(8,'B').getResult() == AtackStatus.MISS);

        //player hit ship of enemy
        assertTrue(board_a.attack(2,'B').getResult() == AtackStatus.HIT);
        //enemy hit ship of player normally
        assertTrue(board.attack(3,'B').getResult() == AtackStatus.HIT);
        //player sunk ship of enemy
        assertTrue(board_a.attack(1,'B').getResult() == AtackStatus.SUNK);
        //enemy sunk ship of player
        assertTrue(board.attack(2,'B').getResult() == AtackStatus.SUNK);

        //player hit one captain quarter of enemy's ship
        assertTrue(board_a.attack(7,'D').getResult() == AtackStatus.CAPTAIN);
        assertTrue(board.attack(6,'D').getResult() == AtackStatus.CAPTAIN);
        //player sunk one enemy'ship by only hitting twice captain quarter
        assertTrue(board_a.attack(7,'D').getResult() == AtackStatus.SUNK);
        //enemy sunk one player'ship by only hitting twice captain quarter
        assertTrue(board.attack(6,'D').getResult() == AtackStatus.SUNK);

        //enemy' another captain quarter got hit again
        assertTrue(board_a.attack(7,'H').getResult() == AtackStatus.CAPTAIN);
        assertTrue(board.attack(4,'H').getResult() == AtackStatus.MISS);


        //This is last ship of enemy being hit by player. Game over
        assertTrue(board_a.attack(7,'H').getResult() == AtackStatus.SURRENDER);

        //assertTrue(board_a.attack(7,'H').getResult() == AtackStatus.SUNK);


    }
}
