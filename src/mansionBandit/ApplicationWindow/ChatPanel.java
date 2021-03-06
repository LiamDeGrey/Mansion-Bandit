package mansionBandit.ApplicationWindow;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The panel the player types multiplayer messages into
 * @author carrtheo
 *
 */
public class ChatPanel extends JPanel{

	private JTextField chatTextBox = new JTextField();
	JTextArea messageArea = new JTextArea();



	GameFrame gameframe;

	public ChatPanel(GameFrame gameFrame){
		super();

		gameframe = gameFrame;

		this.setBackground(Color.black);
		this.setLayout(new GridLayout(0,1,0,0));
		this.setLayout(null);


		//sets up panel
		messageArea.setEditable(false);

		//adds multiplayer textField and multiplayer message area
		messageArea.setVisible(true);
		messageArea.setOpaque(true);
		messageArea.setBackground(Color.black);
		messageArea.setForeground(Color.white);
		messageArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(messageArea);

		scrollPane.setVisible(true);

		scrollPane.setOpaque(false);
		this.add(scrollPane);

		scrollPane.setBounds(0,0,210,120);

		this.add(chatTextBox);

		chatTextBox.setBounds(0,120,120,30);
		JButton sendMessageButton = new JButton("Send");
		sendMessageButton.setBackground(Color.black);
		sendMessageButton.setForeground(Color.white);
		sendMessageButton.setActionCommand("sendMessage");
		sendMessageButton.addActionListener(gameframe);
		sendMessageButton.setBounds(120,120,90,30);
		this.add(sendMessageButton);
		messageArea.setVisible(true);

	}

	/**
	 * returns text written by player and resets text
	 * @return
	 */
	public String getText(){
		String text =  chatTextBox.getText();
		chatTextBox.setText("");
		return text;
	}

	/**
	 * adds the message to view of player messages
	 * @param chatMessage
	 */
	public void updateChat(String chatMessage){

		messageArea.append(chatMessage);

	}


}
