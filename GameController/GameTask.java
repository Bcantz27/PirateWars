package com.piraterevenge.PirateWars.GameController;

public class GameTask implements Runnable {

	@Override
	public void run() {
		GameManager.getInstance().checkQueues();
		
	}

}
