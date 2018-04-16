//Blackjack 12.11
// In this file:
// Ask the number of user 
// Used for loop and array to find players.
// Ask the number of decks
// And hint system
public class Blackjack {

	public static void main(String[] args) {

		System.out.println("************************************");
		System.out.println(" Welcome to play Blackjack ");
		System.out.println("************************************");
		System.out.println("Enter number of player:");
		int Players = IO.readInt();
		Players++;
		boolean dealerpresent = false;

		Player[] players = new Player[Players];
		if (Players > 6 || Players<=0 ) {
			System.out.println("This game max players are 6");
			return;
		}

		for (int i = 1; i < Players + 1; i++) {

			System.out.println("Name of player " + i + " :");
			String name = IO.readString();
			System.out.println("Is player " + i + " the dealer? ");
			boolean dealer = IO.readBoolean();
			System.out.println("What is " + i + " 's starting bank account? ");
			double bank = IO.readDouble();
			if (bank <= 0) {
				System.out.println("Bank value must biger than 0");
				return;
			}
			if (dealer == true && dealerpresent == true) {
				System.out.println("Sorry, only one dealer, exchange " + name + " dealer instead?");
				boolean del = IO.readBoolean();

				if (del == true) {
					for (int j = 0; j < players.length; j++) {
						if (players[j] != null) {
							if (players[j].isDealer() == true) {
								players[j].setDealer(false);
							}
						}
					}
				}
				if (del == false) {
					dealer = false;
				}
			}

			if (dealer == true && dealerpresent == false) {
				dealerpresent = true;
			}

			System.out.println("");

			players[i - 1] = new Player(name, dealer, bank);
		}

		System.out.println("*****************************************************");

		System.out.println("How many decks you want to play?");
		int num_decks = IO.readInt();

		Deck d = new Deck(num_decks);
		d.shuffle();

		boolean dealerlose = false;
		boolean quit = false;
		while (quit == false) {
			playRound(players, d);

			for (int i = 0; i < players.length; i++) {
				if (players[i].isDealer() == true) {
					if (players[i].getBank() <= 0) {
						System.out.println("");
						System.out.println("");
						System.out.println("GAME OVER!  Dealer's bank has emptry, player your win!");
						dealerlose = true;
					}
				}
			}

			if (dealerlose != true) {

				System.out.println("*****************************************************");
				System.out.println("Do you want to quit? or play again? ");
				quit = IO.readBoolean();

				if (dealerlose == true) {
					break;
				}

			}
		}

	}

	// This part will start the game and send card to every player
	public static void playRound(Player[] players, Deck d) {

		for (int i = 0; i < players.length; i++) {
			if (players[i].isDealer() == true) {
				Player temp = players[i];
				players[i] = players[players.length - 1];
				players[players.length - 1] = temp;
				break;
			}
		}

		for (int i = 0; i < players.length; i++) {
			players[i].startRound(d);
			System.out.println("*****************************************************");
			System.out.println("");
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i].isDealer() == false)
				Hint(players[i]);
			players[i].playRound(d);
			System.out.println("*****************************************************");
			System.out.println("");
		}

		int dealerhand = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].isDealer() == true) {
				dealerhand = players[i].getHand().getScore();
			}
		}

		double totalbets = 0;
		// compute the final bet
		for (int i = 0; i < players.length; i++) {
			players[i].finishRound(dealerhand);
			if (players[i].getWinH() == 1)
				totalbets = totalbets - players[i].getBetH();
			if (players[i].getWinH() == 0)
				totalbets = totalbets + players[i].getBetH();

			if (players[i].getWinS() == 1)
				totalbets = totalbets - players[i].getBetS();
			if (players[i].getWinS() == 0)
				totalbets = totalbets + players[i].getBetS();

			if (players[i].isDealer() == true) {
				players[i].lastRoundDealer(totalbets);
			}

		}

	}

	public static void Hint(Player p) {// Hint system

		System.out.println("Do you want to get a hint from system?");
		boolean takehint = IO.readBoolean();

		if (takehint == true) {
			int score = p.getHand().getScore();
			if (score == 21)
				System.out.println("You win and you are blackjack!");
			if (score == 20)
				System.out.println("The 92 % chance that you will bust if you hit.");
			if (score == 19)
				System.out.println("The 85 % chance that you will bust if you hit.");
			if (score == 18)
				System.out.println("The 77 % chance that you will bust if you hit.");
			if (score == 17)
				System.out.println("The 69 % chance that you will bust if you hit.");
			if (score == 16)
				System.out.println("The 62 % chance that you will bust if you hit.");
			if (score == 15)
				System.out.println("The 54 % chance that you will bust if you hit.");
			if (score == 14)
				System.out.println("The 46 % chance that you will bust if you hit.");
			if (score == 13)
				System.out.println("The 38 % chance that you will bust if you hit.");
			if (score == 12)
				System.out.println("The 31 % chance that you will bust if you hit.");
			if (score <= 11)
				System.out.println("You can not bust if you hit.");

		}

	}

}