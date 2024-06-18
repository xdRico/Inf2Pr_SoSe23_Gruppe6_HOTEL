package de.gruppe6.hotel.backend.base.gamerule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.event.NextPlayerOnMove;
import de.gruppe6.hotel.backend.base.gamerule.classic.GameRuleBasicMovement;
import de.gruppe6.hotel.backend.base.gamerule.classic.GameRuleMapFieldAction;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.util.Log;

/**
 * The RuleBook Class, that contains the Rules and Actions used for a Game
 * 
 * @author ricow
 *
 */
public class RuleBook {

	private String name;
	private GameMain gameMain;
	private Queue<IGameRule> queue;

	private boolean isLocked = false;
	private List<IGameRule> gameRules = new ArrayList<IGameRule>();

	/**
	 * RuleBook Class for creating and editing the rules, that the game uses as
	 * references
	 * 
	 * @param gameMain - the GameMain that the RuleBook is used of
	 */
	public RuleBook(GameMain gameMain) {
		this.gameMain = gameMain;
	}

	/**
	 * RuleBook Class for creating and editing the rules, that the game uses as
	 * references
	 * 
	 * @param gameMain - the GameMain that the RuleBook is used of
	 * @param name     - the Name that the RuleBook is called
	 */
	public RuleBook(GameMain gameMain, String name) {
		this.gameMain = gameMain;
		this.name = name;
	}

	/**
	 * Method to add a specified GameRule to the current RuleBook
	 * 
	 * @param rule
	 */
	public final void addRule(IGameRule rule) {

		if (!isLocked) {
			gameRules.add(rule);
			Log.sendMessage("Added Rule " + rule.getClass().getName() + " to Rulebook!");
		}
	}

	/**
	 * Method to create a Game with classic GameRules
	 */
	public final void createClassic() {

		if (!isLocked) {

			gameRules = new ArrayList<IGameRule>();
			gameRules.add(new GameRuleBasicMovement());
			gameRules.add(new GameRuleMapFieldAction());
			Log.sendMessage("Finished creating classic Rulebook!");
		}

	}

	/**
	 * Method to move a Rule in the Rulebook from one to another place in queue
	 * 
	 * @param indexFrom - the place of the Rule that wants to be moved
	 * @param indexTo   - the place where the moved rule is wanted to be after moved
	 */
	public final void moveRule(int indexFrom, int indexTo) {

		if (!isLocked) {
			IGameRule tempRule = gameRules.set(indexTo, gameRules.get(indexFrom));
			while (tempRule != null) {
				indexTo++;
				tempRule = gameRules.set(indexTo, tempRule);
			}
		}
	}

	/**
	 * Method to
	 * 
	 * @param main
	 * @param curPlayer
	 * @throws NextPlayerOnMove
	 */
	public void playRule(GameMain main, Player player) throws NextPlayerOnMove {

		if (!gameMain.isRunning())
			return;
		IGameRule rule = queue.poll();
		if (rule == null) {
			resetRules();
			throw new NextPlayerOnMove();
		}
		rule.ruleBody(gameMain, player);
	}

	/**
	 * Method to create a game with the specified GameRules or create a Game with
	 * classic GameRules
	 */
	private final void createGame() {
		if (gameRules.size() == 0)
			createClassic();

		resetRules();
		Log.sendMessage("Finished creating Game with RuleBook " + name + "!");
	}

	/**
	 * Method to lock the currents RuleBook's gameRules
	 */
	public final void start() {

		createGame();

		isLocked = true;
	}

	/**
	 * Method to reset the rules to the Queue
	 */
	private final void resetRules() {

		queue = new LinkedList<IGameRule>();

		for (IGameRule rule : gameRules)
			queue.add(rule);
	}
}
