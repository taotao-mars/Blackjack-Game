//Deck 12.11
public class Deck {

	private Card[] d;

	public Deck(int num_decks) {

		this.d = new Card[52 * num_decks];
		int pos = 0;

		for (int k = 0; k < num_decks; k++) {

			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < 14; j++) {
					Card temp = new Card(i, j);
					this.d[pos] = temp;
					pos++;
				}
			}

		}
	}

	public void shuffle() {//shuffle cards
		Card temp = null;
		int random = 0;

		for (int i = 0; i < d.length; i++) {
			random = (int) (Math.random() * 51);
			temp = d[random];
			d[random] = d[i];
			d[i] = temp;

		}

	}

	public Card drawCard() {
		Card draw = null;

		for (int i = 0; i < this.d.length; i++) {
			if (this.d[i] != null) {
				draw = this.d[i];
				this.d[i] = null;
				break;
			}
		}

		return draw;
	}

	public int getSize() {
		int notempty = 0;

		for (int i = 0; i < this.d.length; i++) {
			if (this.d[i] != null) {
				notempty++;
			}
		}

		return notempty;
	}

}