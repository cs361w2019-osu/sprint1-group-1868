package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.Game;
import cs361.battleships.models.Ship;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        Ship ship = new Ship(g.getShipType());
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical(), g.isSubmerged());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
            //return Results.status(700);
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
            //return Results.status(700);
        }
    }

    public Result sonar(Context context, AttackGameAction g)
    {
        Game game = g.getGame();
        boolean result = game.sonarAttack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }


    public Result move(Context context, AttackGameAction g)
    {
        Game game = g.getGame();
        int direction = g.getActionRow();
        boolean result = game.moveFleet(direction);
        if(result)
        {
            return Results.json().render(game);
        }
        else
        {
            return Results.badRequest();
        }
    }
}
