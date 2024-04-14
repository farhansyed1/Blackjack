package lektioner;
import java.awt.*; 
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*; 

class MenuWindow extends JFrame implements ActionListener { 

	//Here only variables and objects that need to be accessible within methods and statements are declared
	//Buttons
	JButton playGameButton = new JButton("Play Game"); 						//the three main menu buttons
	JButton howToPlayButton = new JButton("How to Play");
	JButton quitGameButton = new JButton("Quit Game");

	//Images and JLabels
	ImageIcon titleImage = new ImageIcon("blackjacktitle3.png"); 			//the logo
	JLabel title = new JLabel(titleImage);

	ImageIcon backImage = new ImageIcon("redback.png"); 			//image of the back button
	JButton backButton = new JButton(backImage);
	JLabel instructions = new JLabel();								

	ImageIcon background = new ImageIcon("blackjack.jpg"); 
	JLabel imageLabelAsBackgroundPanel = new JLabel(background); 	//this background image will act as the background panel and other panels will be added to it

	//Container and Panels	
	Container contentArea = getContentPane();	
	JPanel boxCenterPanel = new JPanel();

	public MenuWindow() { 
		super("");  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);		 //Sets full screen
		setUndecorated(true);							//hides the title bar
		setVisible(true); 

		//Here the layout is made with several panels. Objects are added to the panels. 
		//Background panel
		imageLabelAsBackgroundPanel.setLayout(new BorderLayout());

		//Box center panel - where the playGame, howtoPlay and quitGame buttons are placed
		LayoutManager layout = new BoxLayout(boxCenterPanel, BoxLayout.Y_AXIS);  		//setting a box layout
		boxCenterPanel.setLayout(layout);
		boxCenterPanel.setOpaque(false);									//makes the panel transparent, allowing the background image to be seen

		boxCenterPanel.add(title);
		boxCenterPanel.add(instructions);
		boxCenterPanel.add(playGameButton);
		boxCenterPanel.add(Box.createVerticalStrut(20)); 					//Creates space of 20 pixels between each button 
		boxCenterPanel.add(howToPlayButton);
		boxCenterPanel.add(Box.createVerticalStrut(20));
		boxCenterPanel.add(quitGameButton);

		//Box left panel - where the back button is placed
		JPanel boxLeftPanel = new JPanel();
		LayoutManager layout2 = new BoxLayout(boxLeftPanel, BoxLayout.Y_AXIS);
		boxLeftPanel.setLayout(layout2);
		boxLeftPanel.setOpaque(false);

		boxLeftPanel.add(backButton); 
		
		imageLabelAsBackgroundPanel.add(boxCenterPanel, BorderLayout.CENTER);		//adding boxCenter to the center of the screen
		imageLabelAsBackgroundPanel.add(boxLeftPanel, BorderLayout.WEST);			//adding boxLeft to the left of the screen

		contentArea.add(imageLabelAsBackgroundPanel);
	
		//Setting sizes
		playGameButton.setMaximumSize(new Dimension(300,70));
		howToPlayButton.setMaximumSize(new Dimension(300,70));
		quitGameButton.setMaximumSize(new Dimension(300,70));

		//Hiding the button background and borders
		removeButtonBackground(backButton);

		//Setting colors of buttons
		playGameButton.setBackground(Color.black);
		howToPlayButton.setBackground(Color.black);
		quitGameButton.setBackground(Color.black);
		playGameButton.setForeground(Color.white);
		howToPlayButton.setForeground(Color.white);
		quitGameButton.setForeground(Color.white);
		instructions.setForeground(Color.white);

		//Font for buttons
		Font font = new Font("ROCKWELL", Font.BOLD, 30);
		playGameButton.setFont(font);
		howToPlayButton.setFont(font);
		quitGameButton.setFont(font);
		instructions.setFont(new Font("ROCKWELL", Font.BOLD, 18));

		//Aligning the objects in the center of the screen
		title.setAlignmentX(CENTER_ALIGNMENT);
		playGameButton.setAlignmentX(CENTER_ALIGNMENT);
		howToPlayButton.setAlignmentX(CENTER_ALIGNMENT);
		quitGameButton.setAlignmentX(CENTER_ALIGNMENT);
		instructions.setAlignmentX(CENTER_ALIGNMENT);
		backButton.setAlignmentX(CENTER_ALIGNMENT);

		//stackoverflow.com/questions/685521/multiline-text-in-jlabel		
		//https://www.tutorialspoint.com/how-can-we-implement-a-jlabel-text-with-different-color-and-font-in-java
		//Here the text for the instructions is written. <br> creates a new line and <font> changes the color and size. 
		String text = "<html> <font size= '8' color=black> Starting Off </font> <br> "
				+ "First, place your bets. You will start with 2000 cash. <br>"
				+ "When you press the deal cards button, the game will begin. <br>"
				+ "<font size= '8' color=red> The Game </font> <br>"					//A different color and size for the headings
				+ "The objective of Blackjack is to beat the dealer’s hand without going over the sum of 21. <br>"
				+ "You will be given two cards. The sum of their values will appear to the right. <br>"
				+ "The dealer will also get two cards but one will be faced down. The value of the known card will be displayed. <br> <br> "
				+ "Cards 1-10 have values corresponding to their card numbers. <br> "
				+ "The face cards Jack, Queen, and King all have the value 10.<br> "
				+ "The ace is normally worth 11, but if the sum will exceed 21 its value is decreased to 1. <br> <br>"
				+ "In consideration of the sum of your cards, you decide whether you want to hit or stand. <br>"
				+ "If you hit, you will take another card and may do so as many times you like, but you must not go over the sum of 21. <br>"
				+ "If you stand, you believe you are as close to 21 as you can get. The dealer's turn begins and they will hit until their sum is over 17.<br>"
				+ "<font size= '8' color=black> Outcomes </font> <br>"
				+ "You win if: 1.  the dealer goes over 21.<br>"
				+ "	<p style='margin-left:78px'> 2. you have a greater sum than the dealer </p>"
				+ " <p style='margin-left:78px'> 3. you get a Blackjack </p>"
				+ "You lose if: 1. you go over 21. "
				+ "	<p style='margin-left:80px'> 2. the dealer's sum is greater than yours. </p>"
				+ " <p style='margin-left:80px'> 3. the dealer gets a Blackjack. </p>"
				+ "A push occurs when you and the dealer have the same sum. <br>"
				+ "A win pays double your bet, and a loss results in you losing the money you bet. <br>"
				+ "<font size= '8' color=red> The Blackjack </font> <br>"
				+ " If the sum of your first two cards is 21, you have a Blackjack. This results in an automatic win. <br>"
				+ "Be aware, the dealer may also get a Blackjack."
				+ "</html>";

		instructions.setText(text);		

		//Adds actionListeners for the method actionPerformed
		playGameButton.addActionListener(this);
		howToPlayButton.addActionListener(this);
		quitGameButton.addActionListener(this);
		backButton.addActionListener(this);

		//Setting objects to invisible
		backButton.setVisible(false);
		instructions.setVisible(false);

	}  

	public void actionPerformed(ActionEvent event) { 
		if(event.getSource()== quitGameButton) {
			playSound("menuClickSound.wav");				//plays a sound when the user clicks on the button
			System.exit(0);									//leaves the game
		}
		if(event.getSource()== howToPlayButton) {			//hides objects and shows the howToPlay objects
			playSound("menuClickSound.wav");				
			playGameButton.setVisible(false);
			howToPlayButton.setVisible(false);
			quitGameButton.setVisible(false);
			title.setVisible(false);
			backButton.setVisible(true);
			instructions.setVisible(true);

		}
		if(event.getSource()== playGameButton) {	
			playSound("menuClickSound.wav");
			enterName enterName = new enterName();				//goes to the next window
			enterName.main(null);
			super.dispose();									//gets rid of this window

		}
		if(event.getSource()== backButton) {
			instructions.setVisible(false);
			backButton.setVisible(false);
			playGameButton.setVisible(true);
			howToPlayButton.setVisible(true);
			quitGameButton.setVisible(true);
			title.setVisible(true);
		}
	} 
	//Here there are methods that are used in other classes
	//http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
	public static void playSound(String soundName){				//this method allows sounds to be played e.g when placing cards
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
			Clip clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start();
		}
		catch(Exception ex) {
			System.out.println("");
			ex.printStackTrace();
		}
	}
	//This method removes the background and borders of buttons with images, in order to make them transparent
	public static void removeButtonBackground(JButton button) {
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusable(false);
	}  

}

public class mainMenu { 

	public static void main(String[] args) { 

		MenuWindow menuWindow = new MenuWindow(); 


	} 

} 