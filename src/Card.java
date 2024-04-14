package lektioner;

import javax.swing.ImageIcon;

public class Card {
	ImageIcon icon;
	int value;
	
	public Card(ImageIcon icon, int value) {
		this.icon = icon;      //gives icon to the card 
		this.value = value;		//gives value to the card (when this method is called in the createDeck() ) 
	}
	
	public ImageIcon getImageIcon() {			//gets the image icon
		return icon;
	}
	
	public int getValue() {						//gets the value of the card
		return value;
	}
	public static int setAceValue(int playerCardValue) {		//sets the value of the card to 1, if it is an ace and the total will exceed 21
		return 1;
	}
	
}
