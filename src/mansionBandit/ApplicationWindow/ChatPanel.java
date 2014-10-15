package mansionBandit.ApplicationWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
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

	JLabel player1Label = new JLabel("h");
	JLabel player2Label = new JLabel("2");
	JLabel player3Label = new JLabel("3");
	JLabel player4Label = new JLabel("4");

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
		JScrollPane scrollPane = new JScrollPane();


		scrollPane.add(messageArea);
		scrollPane.setVisible(true);

		scrollPane.setOpaque(false);
		scrollPane.setBackground(Color.black);
		this.add(scrollPane);

		scrollPane.setBounds(0,0,210,120);

		messageArea.setVisible(true);
		messageArea.setOpaque(true);
		this.add(messageArea);
		this.add(chatTextBox);

		chatTextBox.setBounds(0,120,120,30);
		JButton sendMessageButton = new JButton("Send");
		sendMessageButton.setBackground(Color.black);
		sendMessageButton.setForeground(Color.white);
		sendMessageButton.setActionCommand("sendMessage");
		sendMessageButton.addActionListener(gameframe);
		sendMessageButton.setBounds(120,120,90,30);
		this.add(sendMessageButton);


	}

	public String getText(){
		String text =  chatTextBox.getText();
		chatTextBox.setText("");
		return text;
	}

	public void updateChat(String chatMessage){

	messageArea.append(chatMessage);

	}


}
