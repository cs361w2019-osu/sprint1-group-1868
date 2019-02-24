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
        //enemy board error setting
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 10, 'J', true));     //To check if the place ship will throw error if in invalid position
        assertFalse(board_a.placeShip(new Ship("BATTLESHIP"), 10, 'J', true));
        assertFalse(board_a.placeShip(new Ship("DESTROYER"), 10, 'J', true));
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 0, 'J', false));
        assertFalse(board_a.placeShip(new Ship("BATTLESHIP"), 0, 'J', false));
        assertFalse(board_a.placeShip(new Ship("DESTROYER"), 0, 'I', false));
        //enemy board correct setting
        assertTrue(board_a.placeShip(new Ship("MINESWEEPER"), 2, 'B', true));       //To check if the place ship will correct insert the ship
        assertTrue(board_a.placeShip(new Ship("BATTLESHIP"), 6, 'D', true));
        assertTrue(board_a.placeShip(new Ship("DESTROYER"), 7, 'H', false));
        //player board error setting
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 10, 'J', true));     //To check if the place ship will throw error if in invalid position
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 10, 'J', true));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 10, 'J', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 0, 'J', false));
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 0, 'J', false));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 0, 'I', false));
        //player board correct setting
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 2, 'B', true));       //To check if the place ship will correct insert the ship
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 6, 'D', true));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 7, 'H', false));

        //Some miss hit result


        assertTrue(board.attack(2,'B').getResult() == AtackStatus.HIT);
        /*assertTrue(board.attack(5,'C').getResult() == AtackStatus.HIT);      //To check if the shot result could record the hit status
        assertTrue(board.attack(5,'D').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(5,'E').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(5,'F').getResult() == AtackStatus.SUNK);

        assertTrue(board.attack(2,'E').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(2,'F').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(2,'G').getResult() == AtackStatus.SUNK);

        assertTrue(board.attack(1,'C').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(2,'C').getResult() == AtackStatus.SURRENDER);    */  //To check if the last ship sunk the player lose
    }
}
