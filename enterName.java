package lektioner;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 

class NameWindow extends JFrame implements ActionListener { 

	//Objects declared here are needed for methods and if-statements
	JLabel enterYourNameLabel = new JLabel("Please enter your name below");		
	static JTextField playerNameTextField = new JTextField(20);                     //text field where the user enters their name
	JButton continueButton = new JButton("Continue");				
	static JLabel exceptionLabel = new JLabel("");							//text that appears if the name has numbers or is too long
	String userName;

	ImageIcon background = new ImageIcon("blackjack.jpg"); 
	JLabel imageLabelAsBackgroundPanel = new JLabel(background); 	 //this background image will act as the background panel and other panels will be added to it

	public NameWindow() { 
		super("Blackjack");  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true); 

		imageLabelAsBackgroundPanel.setLayout(new BorderLayout());

		//Here the layout is made
		//Container
		Container contentArea = getContentPane(); 	
		contentArea.setLayout(new BorderLayout());					//A borderLayout is used to align the panels in the center of the screen

		//Center panel
		JPanel centerPanel = new JPanel();							
		centerPanel.setLayout(new FlowLayout());					//A flowLayout is used to make the objects be placed in the space that is available
		centerPanel.setOpaque(false);	

		JPanel gridPanel = new JPanel();							
		gridPanel.setLayout(new GridLayout(4,1,20,20));				//The layout is made with a gridLayout with 4 rows, one for each object. 
		gridPanel.setOpaque(false);

		gridPanel.add(enterYourNameLabel);
		gridPanel.add(playerNameTextField);
		gridPanel.add(continueButton);
		gridPanel.add(exceptionLabel);

		centerPanel.add(gridPanel);	
		imageLabelAsBackgroundPanel.add(centerPanel,  BorderLayout.CENTER);			//the centerPanel is placed in the center

		contentArea.add(imageLabelAsBackgroundPanel);

		//Setting fonts, text size and button size
		Font font = new Font("ROCKWELL", Font.BOLD, 30);
		enterYourNameLabel.setFont(font);
		exceptionLabel.setFont(font);
		continueButton.setFont(font);
		continueButton.setMaximumSize(new Dimension(10,70));

		//Setting colors
		continueButton.setBackground(Color.black);
		continueButton.setForeground(Color.white);
		enterYourNameLabel.setForeground(Color.white);
		exceptionLabel.setForeground(Color.red);

		//ActionListener
		continueButton.addActionListener(this);
	}  
	private void playSound(String soundName) {
		MenuWindow.playSound(soundName);
	}

	//this method checks if the name the user enters is valid. If it isn't, an exception label is displayed
	public static boolean checkIfNameIsValid(String name) {
		if (name.isEmpty()) {
			exceptionLabel.setText("You must write something!");		//if the name is blank, its not valid
			return false;
		}
		else if (name.length() > 10) {
			exceptionLabel.setText("You're name is too long!");			//if over 10 characters, its not valid. THis is because it messes up the layout
			return false;
		}
		else if (name.trim().length() == 0){				//if the name contains only whitespaces, its not valid
			return false;
		}
		return true;

	}

	public static String getUserName() {
		String userName = playerNameTextField.getText();
		return makeFirstLetterCapital(userName);		
	}
	private static String makeFirstLetterCapital(String userName) {
		String output;
		try {
			output = userName.substring(0, 1).toUpperCase() + userName.substring(1);		//makes the first letter of the string capital
			return output;
		}

		catch(Exception e) {
			return userName;
		}
	}

	public void actionPerformed(ActionEvent event) { 
		if(event.getSource()== continueButton) {
			String userName = getUserName();					//gets the userName and sees if its valid
			if(checkIfNameIsValid(userName) == false) {
				playerNameTextField.setText("");							
			}
			if(checkIfNameIsValid(userName) == true) {
				playSound("menuClickSound.wav");				
				bets bets = new bets();						//if it is valid, it will go to the next window
				bets.main(null);
				super.dispose();
			}
		}
	} 
}


public class enterName { 

	public static void main(String[] args) { 

		NameWindow nameWindow = new NameWindow(); 

	} 

} 