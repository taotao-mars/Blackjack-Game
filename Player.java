//Player 12.11.1
public class Player {
	// this file include double down, split and Insurance

	private String name;
	private boolean isDealer;
	private Hand h;
	private double bank;
	private double bet;
	private int hWinround; // win = 1; lose = 0;
	private int sWinround; // same
	private boolean canSplit;
	private Hand split;
	private double splitBet;
	private boolean splitcomplete;
	private double insurance;
	private boolean canInsure = false;
	private boolean doInsure;
	private boolean cInsurance; // boolean

	public Player(String playerName, boolean isDealer, double bank) {
		this.name = playerName;
		this.isDealer = isDealer;
		this.h = new Hand();
		this.bank = bank;
		this.bet = 0;
		this.hWinround = 3;
		this.sWinround = 3;
		this.canSplit = false;
		this.split = new Hand();
		this.splitBet = 0;
		this.splitcomplete = false;
		this.doInsure = false;
		this.insurance = 0;

	}

	public String getName() {
		return this.name;
	}

	public boolean isDealer() {
		return this.isDealer;
	}

	public void setDealer(boolean b) {
		this.isDealer = b;

	}

	public Hand getHand() {
		return this.h;
	}

	public double getBank() {
		return this.bank;
	}

	public int getWinH() {
		return this.hWinround;
	}

	public int getWinS() {
		return this.sWinround;
	}

	public double getBetH() {
		return this.bet;
	}

	public double getBetS() {
		return this.splitBet;
	}

	public void startRound(Deck deck) {
		if (this.isDealer == true) {
			Card c1 = deck.drawCard();
			c1.turnFaceUp();
			Card c2 = deck.drawCard();
			c2.turnFaceDown();
			h.addCard(c1);
			h.addCard(c2);

			System.out.println("Dealer: " + this.name);
			System.out.println(h.toStringDealer());
			System.out.println();
			int countHand = this.h.getScore();
			if (countHand == 21) {
				System.out.println("Dealer hit BlackJack! the players insured are safe.");
				cInsurance = true;
			}
			if (c1.getFace() == 1) {
				System.out.println("");
				System.out.println("Dealer got an ace. Players can purchase insurance.");
				this.canInsure = true;
			}
		}

		else if (this.isDealer == false) {
			System.out.println("Player: " + this.name);
			System.out.println("Place your bet:");
			this.bet = IO.readInt();
			// make bets
			if (canInsure == true) {
				System.out.println("Would you like to buy insurance? (Y/N)");
				this.doInsure = IO.readBoolean();

				if (doInsure == true) {
					this.insurance = this.bank / 2;
				}
			}
			Card c1 = deck.drawCard();
			Card c2 = deck.drawCard();
			c1.turnFaceUp();
			c2.turnFaceUp();
			h.addCard(c1);
			h.addCard(c2);
			if (h.getCard(0).getValue() == h.getCard(1).getValue()) {
				this.canSplit = true;
			}

			System.out.println(h.toStringPlayer());
			System.out.println();

		}

	}

	public void playRound(Deck deck) {
		int totalscore = h.getScore();
		System.out.println(this.name + " play round:");
		System.out.println(h.toStringPlayer());
		System.out.println("");
		System.out.println("\t" + this.name + "'s current score: " + h.getScore());
		System.out.println("");

		if (this.isDealer == true) {
			System.out.println("DEALER play");
			while (totalscore < 17) {
				Card c = deck.drawCard();
				c.turnFaceUp();
				h.addCard(c);
				System.out.println("\t" + "Drew a " + c.toString());
				System.out.println("\t" + this.name + "'s current score: " + h.getScore());

				if (h.getScore() > 21)
					System.out.println("Dealer busts!");
				if (h.getScore() == 21)
					System.out.println("Dealer hit BlackJack! All players insured are safe.");
				cInsurance = true;
				if (h.getScore() >= 17)
					return;

			}

		}

		if (this.isDealer == false) {

			if (canSplit == false) {

				boolean doubledown = false;
				System.out.println("Do you want to double down?  current bet is:" + this.bet + "     Y/N");
				doubledown = IO.readBoolean();
				if (canInsure == true) {
					System.out.println("Would you like to buy insurance? ");
					this.doInsure = IO.readBoolean();

					if (doInsure == true) {
						this.insurance = this.bank / 2;
					}
				}
				if (doubledown == false) {
					System.out.println("Hit?  Y/N");
					boolean hit = IO.readBoolean();

					while (hit == true) {
						Card c = deck.drawCard();
						c.turnFaceUp();
						h.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + h.getScore());

						if (h.getScore() == 21) {
							System.out.println(this.name + " has blackjack!");
							return;
						}

						if (h.getScore() > 21) {
							System.out.println("You busted.");
							return;
						}

						System.out.println("Hit? ");
						hit = IO.readBoolean();
					}
				}
				if (doubledown == true) {
					this.bet = this.bet + this.bet;
					Card c = deck.drawCard();
					c.turnFaceUp();
					h.addCard(c);
					System.out.println("\t" + "Drew a " + c.toString());
					System.out.println("\t" + this.name + "'s current score: " + h.getScore());

					if (h.getScore() == 21) {
						System.out.println(this.name + " has blackjack!");
						return;
					}

					if (h.getScore() > 21) {
						System.out.println("You busted.");
						return;
					}
				}
			}

			splitcomplete = false;
			boolean doSplit = false;
			if (canSplit == true) {
				System.out.println("Do you want to split?");
				doSplit = IO.readBoolean();

				if (doSplit == true) {
					this.bet = this.splitBet;

					this.split.addCard(h.getCard(1));
					Card temp = h.getCard(0);
					h.discardAll();
					h.addCard(temp);
					split.addCard(deck.drawCard());
					h.addCard(deck.drawCard());
					System.out.println(h.toStringPlayer());
					System.out.println("\t" + "This hand's score: " + h.getScore());
					System.out.println("");
					System.out.println(split.toStringPlayer());
					System.out.println("\t" + "This hand's score: " + split.getScore());

					boolean doubledown = false;
					System.out.println("Do you want to double down the first hand?   Your current bet is:" + this.bet
							+ "     Y/N");
					doubledown = IO.readBoolean();

					if (doubledown == false) {
						System.out.println("Hit?  Y/N");
						boolean hit = IO.readBoolean();

						while (hit == true) {
							Card c = deck.drawCard();
							c.turnFaceUp();
							h.addCard(c);
							System.out.println("\t" + "Drew a " + c.toString());
							System.out.println("\t" + this.name + "'s current score: " + h.getScore());

							if (h.getScore() == 21) {
								System.out.println(this.name + " has blackjack!");
								return;
							}

							if (h.getScore() > 21) {
								System.out.println("You busted.");
								return;
							}

							System.out.println("Hit?  Y/N");
							hit = IO.readBoolean();
						}
					}

					if (doubledown == true) {
						this.bet = this.bet + this.bet;
						Card c = deck.drawCard();
						c.turnFaceUp();
						h.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + h.getScore());

						if (h.getScore() == 21) {
							System.out.println(this.name + " has blackjack!");
							return;
						}

						if (h.getScore() > 21) {
							System.out.println("You busted.");
							return;
						}
					}

					doubledown = false;
					System.out.println("Do you want to double down the second hand?   Your current bet is:"
							+ this.splitBet + "     Y/N");
					doubledown = IO.readBoolean();

					if (doubledown == false) {
						System.out.println("Hit?  Y/N");
						boolean hit = IO.readBoolean();

						while (hit == true) {
							Card c = deck.drawCard();
							c.turnFaceUp();
							split.addCard(c);
							System.out.println("\t" + "Got a " + c.toString());
							System.out.println("\t" + this.name + "'s current score: " + split.getScore());

							if (split.getScore() == 21) {
								System.out.println(this.name + " has blackjack!");
								return;
							}

							if (split.getScore() > 21) {
								System.out.println("You busted.");
								return;
							}

							System.out.println("Hit? ");
							hit = IO.readBoolean();
						}
					}

					if (doubledown == true) {
						this.splitBet = this.splitBet + this.splitBet;
						Card c = deck.drawCard();
						c.turnFaceUp();
						split.addCard(c);
						System.out.println("\t" + "Drew a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + split.getScore());

						if (split.getScore() == 21) {
							System.out.println(this.name + " has blackjack!");
							return;
						}

						if (split.getScore() > 21) {
							System.out.println("You busted.");
							return;
						}

						splitcomplete = true;
					}

				}

				if (doSplit == false) {

					boolean doubledown = false;
					System.out.println(
							"Do you want to double down the first hand?  Your current bet is:" + this.bet + "     Y/N");
					doubledown = IO.readBoolean();

					if (doubledown == false) {
						System.out.println("Hit?  Y/N");
						boolean hit = IO.readBoolean();

						while (hit == true) {
							Card c = deck.drawCard();
							c.turnFaceUp();
							h.addCard(c);
							System.out.println("\t" + "Drew a " + c.toString());
							System.out.println("\t" + this.name + "'s current score: " + h.getScore());

							if (h.getScore() == 21) {
								System.out.println(this.name + " has blackjack!");
								return;
							}

							if (h.getScore() > 21) {
								System.out.println("You busted.");
								return;
							}

							System.out.println("Hit?  Y/N");
							hit = IO.readBoolean();
						}
					}

					if (doubledown == true) {
						this.bet = this.bet + this.bet;
						Card c = deck.drawCard();
						c.turnFaceUp();
						h.addCard(c);
						System.out.println("\t" + "Got a " + c.toString());
						System.out.println("\t" + this.name + "'s current score: " + h.getScore());

						if (h.getScore() == 21) {
							System.out.println(this.name + " has blackjack!");
							return;
						}

						if (h.getScore() > 21) {
							System.out.println("You busted.");
							return;
						}
					}

				}

			}

		}

		return;

	}

	public void finishRound(int dealerScore) {// finish round and print the
		// result

		if (this.isDealer == false) {

			System.out.print(this.name + ":  ");

			if (splitcomplete == false) {// not split
				if (h.getScore() > 21) {
					System.out.println("You lose because you busted!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 0;
				}

				else if (dealerScore > 21) {
					System.out.println("You win because the Dealer busted!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 1;
				}

				else if (h.getScore() < dealerScore) {
					System.out.println("You lose because the Dealer bigger than you!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 0;

				} else if (h.getScore() > dealerScore) {
					System.out.println("You win because the Dealer busted!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 1;
				}

				else if (h.getScore() == dealerScore) {
					System.out.println("Same score, so that's draw!!!");
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 2;
				}
			}

			if (splitcomplete == true) { // not split
				System.out.println("\t" + "First hand: ");
				if (h.getScore() > 21) {
					System.out.println("You lose because you busted!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 0;
				}

				else if (dealerScore > 21) {
					System.out.println("You win because the Dealer busted!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 1;
				}

				else if (h.getScore() < dealerScore) {
					System.out.println("You lose because the Dealer bigger than you!");
					this.bank = this.bank - this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 0;

				} else if (h.getScore() > dealerScore) {
					System.out.println("You win because you beat the Dealer!");
					this.bank = this.bank + this.bet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 1;
				}

				else if (h.getScore() == dealerScore) {
					System.out.println("Same score, so that's draw!!!");
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.hWinround = 2;
				}

				System.out.println("\t" + "Second hand: ");
				if (split.getScore() > 21) {
					System.out.println("You lose because you busted!");
					this.bank = this.bank - this.splitBet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.sWinround = 0;
				}

				else if (dealerScore > 21) {
					System.out.println("You win because the Dealer busted!");
					this.bank = this.bank + this.splitBet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.sWinround = 1;
				}

				else if (split.getScore() < dealerScore) {
					System.out.println("You lose because the Dealer beat you!");
					this.bank = this.bank - this.splitBet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.sWinround = 0;

				} else if (split.getScore() > dealerScore) {
					System.out.println("You win!  You beat the Dealer!");
					this.bank = this.bank + this.splitBet;
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.sWinround = 1;
				}

				else if (split.getScore() == dealerScore) {
					System.out.println("Same score, so that's draw!!!");
					System.out.println("\t" + this.name + "'s Current bank value: " + this.bank);
					this.sWinround = 2;
				}

			}

		}

		h.discardAll();
		split.discardAll();

		return;

	}

	public void lastRoundDealer(double totalbets) { // dealer last round and
		// print bank value

		this.bank = this.bank + totalbets;
		System.out.println("\t" + "Dealer: " + this.name + "'s Current bank value: " + this.bank);

	}

	public void Insurance() {
		System.out.println("Would you like to buy insurance? (Y/N)");
		boolean insure = IO.readBoolean();

		if (insure == true) {
			insurance = this.bank / 2; // insurance is half of your bet.
		}
	}

}
