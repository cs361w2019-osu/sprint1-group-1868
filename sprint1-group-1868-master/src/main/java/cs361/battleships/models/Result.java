package cs361.battleships.models;

public class Result {
	private Ship hitship;
	private Square hitsquare;
	private AtackStatus hitresult;

	//This function was called when need to know the result of the hit
	public AtackStatus getResult()
	{
		return this.hitresult;
	}

	//This function was called when the hit was checked by enemy's board
	public void setResult(AtackStatus result)
	{
		this.hitresult = result;
	}

	public Ship getShip()
	{
		return this.hitship;
	}

	//This function will call only when player's ship sunk by enemy's shot, in order to tell enemy which ship they hit.
	public void setShip(Ship ship)
	{
		this.hitship = ship;
	}

	public Square getLocation()
	{
		return this.hitsquare;
	}

	//Just like setShip(), this function is used to tell enemy where they hit and the status of the shot?
	public void setLocation(Square square)
	{
		this.hitsquare = square;
	}
}
