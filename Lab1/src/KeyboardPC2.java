import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import lejos.remote.nxt.*;

public class KeyboardPC2 extends JFrame {
	private static final long serialVersionUID = 6950578690702745288L;
	private static JLabel msg;
	private static ButtonHandler bh = new ButtonHandler();
	private static DataOutputStream outData;
	private static NXTConnection link;

	private static KeyState upState;
	private static KeyState downState;
	private static KeyState leftState;
	private static KeyState rightState;

	public KeyboardPC2() {

		setTitle("Control");
		setBounds(400, 200, 500, 500);
		setLayout(new GridLayout(1, 3));
		this.addKeyListener(bh);

		msg = new JLabel("Message");
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		add(new JLabel(""));
		add(msg);
		add(new JLabel(""));

		connect();

	}

	public static void main(String[] args) {
		KeyboardPC2 remoteKeyboard = new KeyboardPC2();
		remoteKeyboard.setVisible(true);
		remoteKeyboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static class ButtonHandler implements KeyListener {
		ButtonHandler() {
		}

		public void keyPressed(KeyEvent ke) {
		}

		public void keyTyped(KeyEvent ke) {
			try {
				if (ke.getKeyChar() == 'w') {
					msg.setText("Forward");
					outData.writeInt(1);
				}
				if (ke.getKeyChar() == 's') {
					msg.setText("Backward");
					outData.writeInt(2);
				}
				if (ke.getKeyChar() == 'a') {
					msg.setText("Left");
					outData.writeInt(4);
				}
				if (ke.getKeyChar() == 'd') {
					msg.setText("Right");
					outData.writeInt(3);
				}
				if (ke.getKeyChar() == 'i') {
					msg.setText("Increase Speed");
					outData.writeInt(6);
				}
				if (ke.getKeyChar() == 'k') {
					msg.setText("Decrease Speed");
					outData.writeInt(7);
				}
				if (ke.getKeyChar() == 'q') {
					disconnect();
					msg.setText("Disconnected");
				}
				
		         
		        outData.flush(); 
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}

		public void keyReleased(KeyEvent ke) {
			try {
				if (ke.getKeyChar() == 'w') {
					outData.writeInt(10);
				}
				if (ke.getKeyChar() == 's') {
					outData.writeInt(20);
				}
				if (ke.getKeyChar() == 'a') {
					outData.writeInt(40);
				}
				if (ke.getKeyChar() == 'd') {
					outData.writeInt(30);
				}
				if (ke.getKeyChar() == 'i') {
					outData.writeInt(60);
				}
				if (ke.getKeyChar() == 'k') {
					outData.writeInt(70);
				}
				
		         
		        outData.flush(); 
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}

	}

	public void connect() {

		BTConnector bt = new BTConnector();
		link = bt.connect("00:16:53:44:C2:C6", NXTConnection.RAW);
		outData = link.openDataOutputStream();
		msg.setText("Connected!!!");
		System.out.println("Connected :') ");

	}

	public static void disconnect() {
		try {
			outData.close();
			link.close();
		} catch (IOException ioe) {
			System.out.println("\nIO Exception writing bytes");
		}
		System.out.println("\nClosed data streams");
	}
}
