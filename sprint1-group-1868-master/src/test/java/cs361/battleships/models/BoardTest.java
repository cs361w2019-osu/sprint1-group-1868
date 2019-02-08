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
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 15, 'C', true));     //To check if the place ship will throw error if in invalid position
        assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 5, 'Z', true));
        assertFalse(board_a.placeShip(new Ship("BATTLESHIP"), 8, 'C', true));
        //assertFalse(board_a.placeShip(new Ship("MINESWEEPER"), 9, 'C', true));

        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'C', true));       //To check if the place ship will correct insert the ship
        assertTrue(board.placeShip(new Ship("DESTROYER"), 2, 'E', false));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 5, 'C', false));

        assertTrue(board.attack(5,'C').getResult() == AtackStatus.HIT);      //To check if the shot result could record the hit status
        assertTrue(board.attack(5,'D').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(5,'E').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(5,'F').getResult() == AtackStatus.SUNK);

        assertTrue(board.attack(2,'E').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(2,'F').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(2,'G').getResult() == AtackStatus.SUNK);

        assertTrue(board.attack(1,'C').getResult() == AtackStatus.HIT);
        assertTrue(board.attack(2,'C').getResult() == AtackStatus.SURRENDER);      //To check if the last ship sunk the player lose
    }
}
