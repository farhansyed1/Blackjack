package lektioner;
import java.awt.*; 
import java.awt.event.*; //Import event libraries 
import javax.swing.*; 
import java.util.Random;

class GameWindow extends JFrame implements ActionListener { 

	static 	int money = moneyAndBets.getMoney(); //the money variable is called from the moneyAndBets class

	//JLabels
	JLabel yourBetLabel = new JLabel("Your bet:");
	JLabel betsTotalLabel = new JLabel(Integer.toString(moneyAndBets.getBetsTotal()));		//gets the current value of betsTotal
	JLabel winOrLoseLabel = new JLabel();
	JLabel moneyWonOrLostLabel = new JLabel();
	JLabel cashTotal = BetsWindow.getCashTotal();

	//Tells who's hand
	JLabel dealersHandLabel = new JLabel("Dealer's hand");
	JLabel playerHandLabel = new JLabel();						//this is blank because it is changed to the name the user entered

	//Sums
	JLabel dealerSumLabel = new JLabel(); 						//player's sum
	JLabel playerSumLabel = new JLabel(); 						//dealer's sum

	ImageIcon backgroundImage = new ImageIcon("blackjack.jpg"); 		//background image
	JLabel imageLabelAsBackgroundPanel = new JLabel(backgroundImage);	//this background image will act as the background panel and other panels will be added to it

	//Buttons
	ImageIcon hitImage = new ImageIcon("hitbutton1.png");
	JButton hitButton = new JButton(hitImage);					//hit button
	ImageIcon standImage = new ImageIcon("standbutton1.png");
	JButton standButton = new JButton(standImage);				//stand button

	ImageIcon backOfCard = new ImageIcon("cardback.jpg");  

	ImageIcon playAgainImage = new ImageIcon("playagainbutton2.png");  
	JButton playAgain = new JButton(playAgainImage);

	ImageIcon cashOutImage = new ImageIcon("cashoutbutton3.png");  
	JButton cashOut = new JButton(cashOutImage);					//button for leaving the game

	//Variables
	int playerTotal;
	int dealerTotal;
	int counter = 0;

	//Panels
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel boxCenterPanel = new JPanel();
	Container contentArea = getContentPane(); 
	ImageIcon iconForHiddenCard; 
	int valueOfHiddenCard;

	int gameOver;			//a JOptionPane that will be called later

	//For the deck
	Card[] deck = createDeck();					//calls the createDeck method and gives its contents to the Card[] deck array
	int currentCard = -1;						//starts at -1 because the first card is drawn at 0
	JLabel dealer2ndCard = new JLabel();

	public GameWindow() { 
		super("BlackJack"); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setUndecorated(true);
		setVisible(true);  												

		//Container		
		contentArea.setLayout(new BorderLayout(8,6));	

		//BackgroundPanel
		imageLabelAsBackgroundPanel.setLayout(new BorderLayout());

		//Right panel - where the sums and hit and stand buttons are placed
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		rightPanel.setOpaque(false);								//makes panel transparent

		JPanel gridRightPanel = new JPanel();
		gridRightPanel.setLayout(new GridLayout(6,1,1,1));			//grid panel with 6 rows for the 6 objects
		gridRightPanel.setOpaque(false);		

		gridRightPanel.add(dealersHandLabel);
		gridRightPanel.add(dealerSumLabel);
		gridRightPanel.add(hitButton);
		gridRightPanel.add(standButton);
		gridRightPanel.add(playerHandLabel);
		gridRightPanel.add(playerSumLabel);

		rightPanel.add(gridRightPanel);
		imageLabelAsBackgroundPanel.add(rightPanel, BorderLayout.EAST);		//adding the right panel to the east side

		//Left Panel - where the yourBetsLabel and betsTotalLabel are placed
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER,1,300));			//the 300 in the parameter creates a vertical space of 300 pixels from the top
		leftPanel.setOpaque(false);

		JPanel gridLeftPanel = new JPanel(new GridLayout(2,1,1,10));		//two rows, one for yourBetLabel and one for betsTotalLabel
		gridLeftPanel.setOpaque(false);

		gridLeftPanel.add(yourBetLabel);
		gridLeftPanel.add(betsTotalLabel);
		leftPanel.add(gridLeftPanel);
		imageLabelAsBackgroundPanel.add(leftPanel, BorderLayout.WEST);		//adds leftPanel to the west side

		//Center panel - where the text labels that tell if the user wins or loses are placed. PlayAgain and cashOut buttons are also here
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BorderLayout());	

		LayoutManager layout = new BoxLayout(boxCenterPanel, BoxLayout.Y_AXIS);		//box layout for making the text labels appear on new lines
		boxCenterPanel.setLayout(layout);
		boxCenterPanel.setOpaque(false);

		boxCenterPanel.add(winOrLoseLabel);
		boxCenterPanel.add(moneyWonOrLostLabel);
		boxCenterPanel.add(cashTotal);

		JPanel borderCenterPanel = new JPanel(new BorderLayout());        	//border layout so that objects can be placed both at the top and bottom
		borderCenterPanel.setOpaque(false);

		JPanel flowPlayAgainCashOutPanel = new JPanel(new FlowLayout());		//flowLayout so buttons are placed from left to right
		flowPlayAgainCashOutPanel.setOpaque(false);

		flowPlayAgainCashOutPanel.add(playAgain);
		flowPlayAgainCashOutPanel.add(cashOut);

		borderCenterPanel.add(flowPlayAgainCashOutPanel, BorderLayout.SOUTH);			//playAgain and cashOut buttons are placed at the bottom of the centerPanel
		borderCenterPanel.add(boxCenterPanel, BorderLayout.NORTH);						//the text labels are placed at the top of the center panel

		centerPanel.add(borderCenterPanel);

		imageLabelAsBackgroundPanel.add(centerPanel, BorderLayout.CENTER);

		//Top Panel - where dealer cards are placed
		topPanel.setOpaque(false);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  //flowLayout so that the cards are placed left to right

		centerPanel.add(topPanel, BorderLayout.NORTH);

		//Bottom Panel - where the user cards are placed
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setOpaque(false);

		centerPanel.add(bottomPanel, BorderLayout.SOUTH);

		contentArea.add(imageLabelAsBackgroundPanel);

		//Fonts
		Font font = new Font("ROCKWELL", Font.BOLD, 30);
		dealersHandLabel.setFont(font);
		yourBetLabel.setFont(font);
		betsTotalLabel.setFont(font);
		playerHandLabel.setFont(font);
		playerSumLabel.setFont(new Font("ROCKWELL", Font.BOLD, 40));
		dealerSumLabel.setFont(new Font("ROCKWELL", Font.BOLD, 40));
		winOrLoseLabel.setFont(font);
		moneyWonOrLostLabel.setFont(font);
		cashTotal.setFont(font);

		//Setting colors
		dealersHandLabel.setForeground(Color.white);
		dealersHandLabel.setForeground(Color.white);
		dealerSumLabel.setForeground(Color.white);
		yourBetLabel.setForeground(Color.white);
		playerHandLabel.setForeground(Color.white);
		playerSumLabel.setForeground(Color.white);
		betsTotalLabel.setForeground(Color.white);
		winOrLoseLabel.setForeground(Color.white);
		moneyWonOrLostLabel.setForeground(Color.white);
		cashTotal.setForeground(Color.white);

		//Setting center alignment
		dealersHandLabel.setHorizontalAlignment(JLabel.CENTER);
		dealerSumLabel.setHorizontalAlignment(JLabel.CENTER);
		playerHandLabel.setHorizontalAlignment(JLabel.CENTER);
		playerSumLabel.setHorizontalAlignment(JLabel.CENTER);
		yourBetLabel.setHorizontalAlignment(JLabel.CENTER);
		betsTotalLabel.setHorizontalAlignment(JLabel.CENTER);
		winOrLoseLabel.setAlignmentX(CENTER_ALIGNMENT);
		moneyWonOrLostLabel.setAlignmentX(CENTER_ALIGNMENT);
		cashTotal.setAlignmentX(CENTER_ALIGNMENT);

		//Making buttons transparent
		removeButtonBackground(playAgain);
		removeButtonBackground(cashOut);
		removeButtonBackground(hitButton);
		removeButtonBackground(standButton);

		//Actionlisteners
		hitButton.addActionListener(this);  
		standButton.addActionListener(this);  
		cashOut.addActionListener(this);
		playAgain.addActionListener(this);

		//Setting buttons invisible
		hitButton.setVisible(false);
		standButton.setVisible(false);
		cashTotal.setVisible(false);
		playAgain.setVisible(false);
		cashOut.setVisible(false);

		String userName = NameWindow.getUserName();			//gets the userName from the NameWindow class
		playerHandLabel.setText(userName + "'s" + " hand");			//updating the label with the user's name

		deck = shuffle(deck); 							//the deck goes through the shuffle method

		firstCards();			//deals out the first 4 cards
		blackJackChecker(); 	//checks if the first 2 cards give a blackjack
	}  

	//This method is called from the menu class and makes buttons with images transparent
	private void removeButtonBackground(JButton button) {
		MenuWindow.removeButtonBackground(button);
	}  
	//This method is called from the menu class and plays sounds
	public void playSound(String soundName) {
		MenuWindow.playSound(soundName);
	}
	//Method that creates the deck
	private Card[] createDeck() {
		Card[] deck = new Card[52];								//array size is 52
		String[] suitLetters = new String[] {"H","D","C","S"};	//the letters for the image names. Array with 4 placeholders

		for(int suit=0; suit<4; suit++) {						//this for loop repeats 4 times
			String letter = suitLetters[suit];					//the first suit H from the suitLetters array is set as the letter variable
			for(int number=1;number <= 13; number++) {			//this loop repeats 13 times for the 13 different card ranks
				String imageIconName = obtainCardImageFileName(letter, number);		//the name of the imageIcon is formed by going this method
				ImageIcon imageIcon = new ImageIcon(imageIconName); 				//the string name of the imageIcon is set as the imageIcon

				int rank = calculateValue(number);					//now the rank(value of the card) is calculated with the method
				Card card = new Card(imageIcon, rank);				//a card is placed through the Card method in the Card class. It sets its imageIcon and rank
				int i = suit * 13 + (number -1);					//at suit 0, cards are 0-12, suit 1 is 13-25, suit 2 is 26-39, suit 3 is 40-52. This is not their values, this is 13 cards in the 4 sets
				deck[i] = card;										//i is passed through the deck and then each card (with an imageIcon and rank) is placed in the deck
			}
		}

		return deck;												//returns the deck back to the variable that called this method
	}
	//this method calculates the value of each card
	private int calculateValue(int number) {
		if( 1 < number && number <= 10) { 							//numbers 1-10 have the same value is their number e.g number 1 has value 1
			return number;
		}
		else if(number == 1) {										// if the number is 1, the value is 11 (it's an ace)
			return 11;
		}
		else {			
			return 10;												//all the face cards 11-13 (J, Q, K) have the value 10
		}
	}
	//this method forms the name of the imageIcon, using the number and the letters H, D, C, S. it then adds .jpg to the end
	private String obtainCardImageFileName(String letter, int number) {
		return convertNumberToCardValue(number) + letter + ".jpg";				//the number is passed through this method to make certain numbers correspond to face cards
	}
	//numbers 1 and 11-13 have different image names. That's why this method must be used to get the correct names
	private String convertNumberToCardValue(int f) {
		if(f == 13) {
			return "K"; 
		} 
		else if(f==12) {
			return "Q";
		}
		else if(f==11) {
			return "J";
		} 
		else if (f==1) {
			return "A";
		}
		return Integer.toString(f);
	}
	//Method for shuffling the deck
	public Card[] shuffle (Card[] deck) 
	{ 
		Random random = new Random(); 			//uses the imported java.util.Random;
		for (int i = 0; i < 52; i++) 			//for loop runs 52 times
		{ 
			int r = i + random.nextInt(52 - i); 	//r is given a random placeholder
			
			Card temp = deck[r];    		//here the temp variable saves the r's placeholder
			deck[r] = deck[i]; 				// i(from 1-52) takes r's placeholder
			deck[i] = temp; 				// the old i placeholder is the new temp

		}
		return deck; 						//when the shuffling is done, the deck is returned to the where the method was called from
	} 	
	//takes the top card from the deck by using the currentCard counter. 
	private Card takeTopCard(Card[] deck) {								
		currentCard++;											//the first time, this becomes 0. The next time it will be 1 and so on
		Card topCard = deck[currentCard];						//topCard is the deck's first card 0
		return topCard;											//returns the topCard to addCards(). The 
	}
	
	//If the sum will exceed 21, this method will make the ace's value 1 instead of 11
	public void aceChecker(int cardValue, int total) {
		if(total + cardValue > 21) {							
			cardValue = 1;										//this is to make sure it happens
		}	
	}
	//addCard() adds another card to the table. 
	public Card addCard(int playerOrDealer) {
		Card aCard = takeTopCard(deck);
		ImageIcon icon = aCard.getImageIcon();
		JLabel cardLabel = new JLabel(icon);

		if(playerOrDealer == 1) { 			//player card. Used for first 2 player cards and when pressing hit
			bottomPanel.add(cardLabel);
			int playerCardValue = aCard.getValue();			//gets the value from the getValue method in Card class
			if(playerCardValue == 11) {						//if the card's value is 11, it will check if the total will exceed 21
				if(playerTotal + playerCardValue > 21) {						//if total + the current card's value is over 21,
					playerCardValue = aCard.setAceValue(playerCardValue); 		 //then this will set the card's value to 1 instead of 11
					playerCardValue = 1;										//this is to make sure it happens
				}
			}
			playerTotal += playerCardValue;								//adds the card's value to the total
		}
		if(playerOrDealer == 2) {			//dealers normal card. Used for first card and when player has pressed stand
			topPanel.add(cardLabel);
			int dealerCardValue = aCard.getValue();
			if(dealerCardValue == 11) {
				if(dealerTotal + dealerCardValue > 21) {
					dealerCardValue = aCard.setAceValue(dealerCardValue);
					dealerCardValue = 1;
				}
			}
			dealerTotal += dealerCardValue;
		}
		if(playerOrDealer == 3) { 			 //dealer's hidden card. Used only at start
			cardLabel = dealer2ndCard;
			cardLabel.setIcon(backOfCard);				//sets the icon to the back of the card 
			topPanel.add(cardLabel);					//adds the card to the top panel
			iconForHiddenCard = icon;						//the real icon is saved here
			valueOfHiddenCard = aCard.getValue();			//the value is saved here
		}

		playerSumLabel.setText(Integer.toString(playerTotal));
		dealerSumLabel.setText(Integer.toString(dealerTotal));
		playSound("cardPlace1.wav");

		return aCard;
	}
	//This method places the first 4 cards on the table
	private void firstCards() {
		addCard(1);
		addCard(1);
		addCard(2);
		addCard(3);
		hitButton.setVisible(true);
		standButton.setVisible(true);
	}
	//When the player presses stand, this method is used for drawing the dealer's cards
	private void dealersTurn() {
		dealer2ndCard.setIcon(iconForHiddenCard);			//sets the icon to the front of the card
		if(dealerTotal + valueOfHiddenCard > 21) {			//the ace checker
			valueOfHiddenCard = Card.setAceValue(valueOfHiddenCard);
			valueOfHiddenCard = 1;
		}
		if(dealerTotal + valueOfHiddenCard == 21) {		//if its a blackjack, this runs and the user loses
			youLose(3);
		}
		dealerTotal += valueOfHiddenCard;
		playSound("cardShove1.wav");
		dealerSumLabel.setText(Integer.toString(dealerTotal));
		while (dealerTotal < 17) {								//the dealer must draw cards until their sum is at least 17
			addCard(2);	
		}

	}
	public void blackJackChecker() {						//if the player gets a blackjack, this runs
			if(playerTotal == 21) {
				youWin(3);
			}
	}

	//While the player still uses the hit button, these win-lose checks are used
	public void hitScoreChecker() { 		
		if(playerTotal > 21) {
			youLose(1);
		}
		else if(playerTotal == 21) {
			youWin(2);
		}
	}
	//After the player has pressed stand, these checks are used
	public void standScoreChecker() {    
		if(dealerTotal > 21) {
			youWin(1);
		}
		else if(playerTotal < dealerTotal) {
			youLose(2);
		}
		else if(playerTotal > dealerTotal) {
			youWin(2);
		}
		else if(dealerTotal == 21) {
			youLose(3);
		}
		else if(playerTotal == dealerTotal) {
			push();
		}
	}
	//youLose() is called from the hit or stand score checkers. It displays different losing outcomes
	private void youLose(int identifier) {
		if(identifier == 1) {
			winOrLoseLabel.setText("You busted and went over 21.");					//When the user hits and goes over 21
		}
		else if(identifier == 2) {
			winOrLoseLabel.setText("The dealer's sum is greater than yours.");		//When the dealer has drawn their cards and has a sum greater than the user
		}
		else if (identifier == 3) {
			winOrLoseLabel.setText("The dealer got a Blackjack");					//When the dealer gets a Blackjack
		}
		moneyWonOrLostLabel.setText("You lose " + moneyAndBets.getBetsTotal() + ".");
		money = moneyAndBets.getMoney();
		cashTotal.setText("Cash total: " + moneyAndBets.getMoney());
		moneyAndBets.setMoney(money);

		cashTotal.setVisible(true);
		playAgain.setVisible(true);
		cashOut.setVisible(true);

		hitButton.removeActionListener(this);										//removes actionlisteners to prevent the user from taking more cards
		standButton.removeActionListener(this);

		playSound("loseSound.wav");

		if(money == 0) {																//if the user runs out of money, it's a game over	
			int gameOver; 
			Object[] options = {"PLAY AGAIN", "LEAVE"};						//array that holds the two button names

			int response = JOptionPane.showOptionDialog(contentArea, "YOU RAN OUT OF MONEY. WOULD YOU LIKE TO PLAY AGAIN OR LEAVE?", "GAME OVER",		//the different text messages
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,	null, options, options[0]); 
			if(response == JOptionPane.YES_OPTION){										//this is an optionpane that runs if the user runs out of money
				moneyAndBets.setMoney(2000);										//resets the money
				moneyAndBets.setBetsTotal(0);									//if the option is yes, the game restarts
				mainMenu mainMenu = new mainMenu();  							//starts the main menu window and restarts the game
				mainMenu.main(null);
				dispose();
			}
			if(response == JOptionPane.NO_OPTION){						//if the option is no, the user quits the game
				System.exit(0);
			}
			playAgain.setEnabled(false);
		}
	}
	//youWin() is called from the hit or stand score checkers. It displays different winning outcomes
	private void youWin(int identifier) {
		if(identifier == 1) {
			winOrLoseLabel.setText("The dealer busted and went over 21.");			//When the dealer hits and goes over 21
		}
		else if(identifier == 2) {
			winOrLoseLabel.setText("Your sum is greater than the dealer's!");		//When the dealer has drawn their cards but has a sum less than the user
		}
		else if(identifier == 3) {
			winOrLoseLabel.setText("You won with a Blackjack!");					//When the player gets a blackjack
		}
		moneyWonOrLostLabel.setText("You win " + moneyAndBets.getBetsTotal() + "!");	//updating winnings
		money = moneyAndBets.getMoney();
		money += (moneyAndBets.getBetsTotal()*2);				//bets is doubled and is added to money
		moneyAndBets.setMoney(money);
		cashTotal.setText("Cash total: " + money);

		moneyAndBets.setBetsTotal(0);							//sets the betsTotal to 0
		cashTotal.setVisible(true);
		playAgain.setVisible(true);
		cashOut.setVisible(true);

		hitButton.removeActionListener(this);
		standButton.removeActionListener(this);
		playSound("cheer.wav");
	}
	//A push occurs after the dealer has taken their cards and has a sum equal to the player
	private void push() {
		winOrLoseLabel.setText("It's a push!");
		moneyWonOrLostLabel.setText("You don't win or lose any money.");
		money = moneyAndBets.getMoney();
		money += moneyAndBets.getBetsTotal();
		cashTotal.setText("Cash total: " + money);
		moneyAndBets.setMoney(money);

		cashTotal.setVisible(true);
		playAgain.setVisible(true);
		cashOut.setVisible(true);

		hitButton.removeActionListener(this);
		standButton.removeActionListener(this);
	}

	public void actionPerformed(ActionEvent event) { 
		if(event.getSource() == hitButton) { 
			addCard(1);
			hitScoreChecker();						//checks if user busts or gets 21
		}
		if(event.getSource() == standButton) { 
			dealersTurn();
			standScoreChecker();				//score checker after the dealer has had their turn
		}

		if(event.getSource() == cashOut) { 
			int optionToQuit;							//this is an optionPane that gives the option to quit. 
			String cash = Integer.toString(money);
			int response = optionToQuit = JOptionPane.showConfirmDialog(this, "Do you want to leave with " + cash + "?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(response == JOptionPane.YES_OPTION){			//if the user presses yes, the game quits
				System.exit(0);
			}
		}

		if(event.getSource() == playAgain) { 		//if they press playAgain, money is set to the new amount and betsTotal to 0
			moneyAndBets.setBetsTotal(0);
			moneyAndBets.setMoney(money);
			BetsWindow transistionToNextScreen = new BetsWindow();
			dispose();
		}
	} 

}

public class game { 

	public static void main(String[] args) { 

		GameWindow gameWindow = new GameWindow (); 
	}
}

