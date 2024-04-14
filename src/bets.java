package lektioner;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import java.io.File;
import javax.sound.sampled.AudioInputStream;  //http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

class moneyAndBets{			       //money and betsTotal are placed in a separate class in order to make them accesible imn the game class as well
	static int betsTotal = 0;	
	static int money = 2000;
	static int tempMoney;

	public static void setMoney(int newMoney) {				//sets the value of money
		money = newMoney;
	}	
	public static void setBetsTotal(int newBets) {			//sets the betsTotal
		betsTotal = newBets;
	}	
	public static int getMoney() {							//gets the value of money
		return money;
	}
	public static int getBetsTotal() {						//gets the value of betsTotal
		return betsTotal;
	}
	public static void setTempMoneyBeforeReset(int newMoney) {		//sets the initial money before placing bets. Used for the reset button
		tempMoney = newMoney;
	}
	public static int getTempMoneyBeforeReset() {					//gets the initial amount of money
		return tempMoney;
	}

}

class BetsWindow extends JFrame implements ActionListener { 
	//ImageIcons, JLabels and JButtons that are needed in methods are declared here in order to make the accessible
	ImageIcon background = new ImageIcon("blackjack.jpg"); 
	JLabel imageLabelAsBackgroundPanel = new JLabel(background);  //this background image will act as the background panel and other panels will be added to it

	JLabel betPlacingLabel = new JLabel("Place your bets");
	JLabel playerBets = new JLabel("You are betting ");

	ImageIcon ten = new ImageIcon("bluepoker.png"); 		
	JButton betTen = new JButton(ten); 						//a button for a poker chip worth 10

	ImageIcon hundred = new ImageIcon("redpoker1.png"); 	
	JButton betHundred = new JButton(hundred); 				//a button for a poker chip worth 100

	ImageIcon fiveHundred = new ImageIcon("purplepoker.png"); 
	JButton betFiveHundred = new JButton(fiveHundred); 		//a button for a poker chip worth 500

	ImageIcon thousand = new ImageIcon("blackpoker.png"); 
	JButton betThousand = new JButton(thousand); 			//a button for a poker chip worth 1000

	ImageIcon dealImage = new ImageIcon("dealbutton4.png");		//button for finalizing the bet and dealing the cards
	JButton dealButton = new JButton(dealImage); 

	ImageIcon resetImage = new ImageIcon("resetbutton2.png");
	JButton resetBetButton = new JButton(resetImage);		//a button for reseting the bets

	JLabel quantityTen;				//these labels show the amount of a certain chip the player is betting
	JLabel quantityHundred;
	JLabel quantityFiveHundred;
	JLabel quantityThousand;

	int tenCounter = 0;					//counters that count how many times each button has been pressed
	int hundredCounter = 0;
	int fiveHundredCounter = 0;
	int thousandCounter = 0;

	//Calling and declaring the variables from the moneyAndBets class
	static int money = moneyAndBets.getMoney();
	static int betsTotal = moneyAndBets.getBetsTotal();
	static JLabel cashTotal = new JLabel("Cash total: " + moneyAndBets.getMoney());
	static int tempMoney;

	//Panels
	Container contentArea = getContentPane(); 
	JPanel borderQuantitiesAndChipsPanel = new JPanel();
	JPanel gridChipsEastPanel = new JPanel();
	JPanel gridQuantitiesWestPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel resetPanel = new JPanel();

	Font font = new Font("ROCKWELL", Font.BOLD, 30);
	Font font2 = new Font("ROCKWELL", Font.BOLD, 35);

	public BetsWindow() { 
		super("BlackJack"); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setUndecorated(true);
		setVisible(true);  

		//Background panel
		imageLabelAsBackgroundPanel.setLayout(new BorderLayout());

		//Right Panel - where the quantity labels and bet poker chips are placed
		rightPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));		//with the flowLayout, the objects will be placed in order in the available space
		rightPanel.setOpaque(false);									//makes the panel transparent

		JPanel gridBetsAndResetPanel = new JPanel((new GridLayout(2,1,1,1)));		//this panel separates the chips and quantityLabels(bets) from the resetButton
		gridBetsAndResetPanel.setOpaque(false);

		resetPanel.setLayout(new FlowLayout());							//another panel is made for the resetButton so that it is placed correctly
		resetPanel.setOpaque(false);
		resetPanel.add(resetBetButton, BorderLayout.CENTER);

		borderQuantitiesAndChipsPanel.setLayout(new BorderLayout());	//border panel is made for aligning the quantityLabels and chips left and right respectively																
		borderQuantitiesAndChipsPanel.setOpaque(false);	

		gridChipsEastPanel.setLayout(new GridLayout(4,1,1,1));			//a grid panel for the poker chips with 4 rows, one for each chip. 
		gridChipsEastPanel.setOpaque(false);

		gridQuantitiesWestPanel.setLayout(new GridLayout(4,1,1,1));		//a grid panel for the quantityLabels with 4 rows, one for each label	
		gridQuantitiesWestPanel.setOpaque(false);

		borderQuantitiesAndChipsPanel.add(gridChipsEastPanel, BorderLayout.EAST);				//adding gridChipsEastPanel to the right side
		borderQuantitiesAndChipsPanel.add(gridQuantitiesWestPanel, BorderLayout.WEST);			//adding gridQuantitiesWestPanel to the left side(of the right panel)

		gridBetsAndResetPanel.add(borderQuantitiesAndChipsPanel);								//adding nested panels to the main panels
		gridBetsAndResetPanel.add(resetPanel);
		rightPanel.add(gridBetsAndResetPanel);
		imageLabelAsBackgroundPanel.add(rightPanel, BorderLayout.EAST);

		//Left Panel - where the poker chips buttons are placed 
		JPanel leftPanel = new JPanel();							
		leftPanel.setLayout(new FlowLayout(4,4,4));					
		leftPanel.setOpaque(false);

		JPanel gridLeftPanel = new JPanel();					
		gridLeftPanel.setLayout(new GridLayout(4,1,1,1) );				//the left side is separated into 4 rows of 4 buttons with a gridLayout
		gridLeftPanel.setOpaque(false);

		gridLeftPanel.add(betTen); 										//adding the buttons to the gridLeftPanel
		gridLeftPanel.add(betHundred); 
		gridLeftPanel.add(betFiveHundred);
		gridLeftPanel.add(betThousand); 

		leftPanel.add(gridLeftPanel);
		imageLabelAsBackgroundPanel.add(leftPanel, BorderLayout.WEST);

		//Center panel - where the text labels are placed
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());	
		centerPanel.setOpaque(false);

		JPanel boxTextPanel = new JPanel();							
		LayoutManager layout = new BoxLayout(boxTextPanel, BoxLayout.Y_AXIS);		//creates a box layout in the vertical direction
		boxTextPanel.setLayout(layout);
		boxTextPanel.setOpaque(false);

		boxTextPanel.add(betPlacingLabel); 
		boxTextPanel.add(Box.createVerticalStrut(60));								//Creates a space of 60 pixels between each label
		boxTextPanel.add(cashTotal); 
		boxTextPanel.add(Box.createVerticalStrut(60));
		boxTextPanel.add(playerBets);

		centerPanel.add(boxTextPanel);
		imageLabelAsBackgroundPanel.add(centerPanel, BorderLayout.CENTER);
		contentArea.add(imageLabelAsBackgroundPanel);

		//Bottom Panel - where the deal button is placed
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setOpaque(false);

		bottomPanel.add(dealButton); 
		centerPanel.add(bottomPanel, BorderLayout.SOUTH);

		//Actionlisteners
		betTen.addActionListener(this);  
		betHundred.addActionListener(this);  
		betFiveHundred.addActionListener(this);  
		betThousand.addActionListener(this);  
		resetBetButton.addActionListener(this);
		dealButton.addActionListener(this);

		//Setting buttons to transparent
		removeButtonBackground(betTen);
		removeButtonBackground(betHundred);
		removeButtonBackground(betFiveHundred);
		removeButtonBackground(betThousand);
		removeButtonBackground(dealButton);
		removeButtonBackground(resetBetButton);

		//Fonts for text
		betPlacingLabel.setFont(font);
		cashTotal.setFont(font);
		playerBets.setFont(new Font("ROCKWELL", Font.BOLD, 40));

		//Colors for text
		betPlacingLabel.setForeground(Color.white);
		playerBets.setForeground(Color.red);
		cashTotal.setForeground(Color.white);

		//Setting sizes
		dealButton.setMaximumSize(new Dimension(100,100));
		resetBetButton.setPreferredSize(new Dimension(130,130));

		//Setting center alignment of text labels
		betPlacingLabel.setAlignmentX(CENTER_ALIGNMENT);
		cashTotal.setAlignmentX(CENTER_ALIGNMENT);
		playerBets.setAlignmentX(CENTER_ALIGNMENT);

		//Setting some objects to invisible
		dealButton.setVisible(false);
		playerBets.setVisible(false);
		resetBetButton.setVisible(false);

		moneyAndBets.setTempMoneyBeforeReset(moneyAndBets.getMoney());
		int tempMoney = moneyAndBets.getTempMoneyBeforeReset();   //getting the value of temp Money and setting  it to the variable

		moneyAndBets.setBetsTotal(0);						//sets the bets to 0 at the start of the game
		betsTotal = moneyAndBets.getBetsTotal();			//the value of 0 is set to betsTotal

		resetMethod();

		checkIfEnoughMoney(betTen,10);				//When the player presses play again in the game window, these must run in order to make some chips disabled
		checkIfEnoughMoney(betHundred, 100);
		checkIfEnoughMoney(betFiveHundred, 500);
		checkIfEnoughMoney(betThousand, 1000);

		setContentPane(contentArea); 
	}
	//This method is called from the menu class
	private void removeButtonBackground(JButton button) {
		MenuWindow.removeButtonBackground(button);
	}  

	//PlaceBet method is called when the player bets a chip
	private void placeBet(int amount, ImageIcon imageOfChip, int counter) {
		if(money >= amount && counter == 1) {		
			moneyAndBets.setMoney(money -= amount);								//the value of the chip is subtracted from the player's money
			moneyAndBets.setBetsTotal(betsTotal += amount);						//the value of the chip is added to the total bets

			JLabel	pokerChip = new JLabel(imageOfChip);	//a new pokerChip is made, with the same image as the one the user clicked
			gridChipsEastPanel.add(pokerChip); 				//adds the chip to the right side 

			playerBets.setText("You are betting " + moneyAndBets.getBetsTotal());		//updates text labels
			cashTotal.setText("Cash total: " + moneyAndBets.getMoney());

			setContentPane(contentArea); 
		}
		else if(money >= amount && counter > 1) {			//if the counter is more than 1, a poker chip doesn't have to be made, but the labels are still updated
			moneyAndBets.setMoney(money -= amount);
			moneyAndBets.setBetsTotal(betsTotal += amount);

			playerBets.setText("You are betting " + moneyAndBets.getBetsTotal());
			cashTotal.setText("Cash total: " + moneyAndBets.getMoney());

			setContentPane(contentArea); 
		}

	}
	//method that sets the quantity label's text size, color and font
	private void quantityLabelTextAndFont(JLabel quantity) {
		quantity.setFont(font2);
		quantity.setForeground(Color.white);
	}
	//counterChecker is for adding the quantityLabels that tell how many of each chip is bet by the user
	private void counterChecker(int amount, int counter) {
		if(counter == 1) {												//the first time a chip is bet
			if(amount == 10) {
				if(quantityTen == null) {
					quantityTen = new JLabel();
					gridQuantitiesWestPanel.add(quantityTen);			//adding the quantity label to the gridQuantitiesWestPanel
				}	
				quantityTen.setText(Integer.toString(counter) + " x  ");		//updating the quantity for the text label
				quantityLabelTextAndFont(quantityTen);							//calls a method that sets font and text size
			}
			else if(amount == 100) {
				if(quantityHundred == null) {
					quantityHundred = new JLabel();
					gridQuantitiesWestPanel.add(quantityHundred);
				}
				quantityHundred.setText(Integer.toString(counter) + " x  ");
				quantityLabelTextAndFont(quantityHundred);
			}
			else if(amount == 500) {
				if(quantityFiveHundred == null) {
					quantityFiveHundred = new JLabel();
					gridQuantitiesWestPanel.add(quantityFiveHundred);
				}
				quantityFiveHundred.setText(Integer.toString(counter) + " x  ");
				quantityLabelTextAndFont(quantityFiveHundred);
			}
			else if(amount == 1000) {
				if(quantityThousand == null) {
					quantityThousand = new JLabel();
					gridQuantitiesWestPanel.add(quantityThousand);
				}
				quantityThousand.setText(Integer.toString(counter) + " x  ");
				quantityLabelTextAndFont(quantityThousand);
			}
		}
		else if(counter > 1) {			//if more than one of a certain chip is bet, the text only needs to be updated
			if(amount == 10) {
				quantityTen.setText(Integer.toString(counter) + " x  ");
			}
			else if(amount == 100) {
				quantityHundred.setText(Integer.toString(counter) + " x  ");
			}
			else if(amount == 500) {
				quantityFiveHundred.setText(Integer.toString(counter) + " x  ");
			}
			else if(amount == 1000) {
				quantityThousand.setText(Integer.toString(counter) + " x  ");
			}
		}

	}

	//Method for setting the button to opaque when there isn't enough money
	public void checkIfEnoughMoney(JButton button, int chipValue) {
		ImageIcon blueOpaque = new ImageIcon("blueOpaque2.png");			//image icons of opaque poker chips
		ImageIcon redOpaque = new ImageIcon("redOpaque2.png");
		ImageIcon purpleOpaque = new ImageIcon("purpleOpaque2.png");
		ImageIcon blackOpaque = new ImageIcon("blackOpaque2.png");

		if(money < chipValue) {		//if the money available is less than the value of the chip, the chip will be disabled
			if(chipValue == 10) {
				button.setIcon(blueOpaque);
				betTen.setEnabled(false);
			}
			else if(chipValue == 100) {
				button.setIcon(redOpaque);
				betHundred.setEnabled(false);
			}
			else if(chipValue == 500) {
				button.setIcon(purpleOpaque);
				betFiveHundred.setEnabled(false);
			}
			else if(chipValue == 1000) {
				button.setIcon(blackOpaque);
				betThousand.setEnabled(false);
			}
		}
	}
	public void playAgainMethod() {
		quantityTen = null;					
		quantityHundred = null;
		quantityFiveHundred = null;
		quantityThousand = null;
	}

	//this method runs when the user clicks the reset button
	public void resetMethod() {  
		betsTotal = 0; 
		moneyAndBets.setBetsTotal(0);			//resetting bets and player money
		money = moneyAndBets.tempMoney;
		moneyAndBets.setMoney(money);
		cashTotal.setText("Cash total: " + money);

		tenCounter = 0;						//resetting counters
		hundredCounter = 0;
		fiveHundredCounter = 0;
		thousandCounter = 0;

		betTen.setIcon(ten);				//resetting image icons
		betHundred.setIcon(hundred);
		betFiveHundred.setIcon(fiveHundred);
		betThousand.setIcon(thousand);

		betTen.setEnabled(true);			//enabling poker chip buttons
		betHundred.setEnabled(true);
		betFiveHundred.setEnabled(true);
		betThousand.setEnabled(true);

		dealButton.setVisible(false);		//hiding other buttons
		playerBets.setVisible(false);
		resetBetButton.setVisible(false);

		gridChipsEastPanel.removeAll();		//removes all items from the panels on the right i.e. the quantity labels and bet chips
		gridQuantitiesWestPanel.removeAll();

		gridChipsEastPanel.revalidate();
		gridQuantitiesWestPanel.revalidate();

		gridChipsEastPanel.repaint();
		gridQuantitiesWestPanel.repaint();

		quantityTen = null;					//setting to null for the counterChecker method
		quantityHundred = null;
		quantityFiveHundred = null;
		quantityThousand = null;

		setContentPane(contentArea); 

	}
	//Sound method called from menu class
	public void playSound(String soundName) {
		MenuWindow.playSound(soundName);
	}

	public static JLabel getCashTotal() {
		return cashTotal;

	}

	public void actionPerformed(ActionEvent event) { 
		if (event.getSource() == betTen) { 	
			tenCounter += 1;						//adds one to the counter
			placeBet(10, ten, tenCounter);			//places bet
			counterChecker(10, tenCounter);			//updates quantity labels
			playSound("chipSound.wav");				//plays sound of placing a chip
		}
		if (event.getSource() == betHundred) { 
			hundredCounter += 1;
			placeBet(100, hundred, hundredCounter);
			counterChecker(100, hundredCounter);
			playSound("chipSound.wav");
		}
		if (event.getSource() == betFiveHundred) { 
			fiveHundredCounter += 1;
			placeBet(500, fiveHundred, fiveHundredCounter);
			counterChecker(500, fiveHundredCounter);
			playSound("chipSound.wav");
		}
		if (event.getSource() == betThousand) { 
			thousandCounter += 1;
			placeBet(1000, thousand, thousandCounter);
			counterChecker(1000, thousandCounter);
			playSound("chipSound.wav");
		} 
		if(event.getSource() == resetBetButton) {
			resetMethod();
		}
		if(betsTotal > 0) {					//if the bets is more than 0, the text label, dealButton and resetButton will become visible
			dealButton.setVisible(true);
			playerBets.setVisible(true);
			resetBetButton.setVisible(true);
		}
		if (event.getSource() == dealButton) { 
			game game = new game();
			game.main(null);
			super.dispose();
		}

		checkIfEnoughMoney(betTen,10);				//Every time a button is pressed, the checkIfEnoughMoney method will run
		checkIfEnoughMoney(betHundred, 100);
		checkIfEnoughMoney(betFiveHundred, 500);
		checkIfEnoughMoney(betThousand, 1000);
	} 

}
public class bets { 

	public static void main(String[] args) { 

		BetsWindow betsWindow = new BetsWindow (); 
	}
}

