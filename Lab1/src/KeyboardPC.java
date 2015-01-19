import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import lejos.remote.nxt.*;

public class KeyboardPC extends JFrame {
	private static final long serialVersionUID = 6950578690702745288L;
	private static JLabel msg;
	private static ButtonHandler bh = new ButtonHandler();
	private static DataOutputStream outData;
	private static NXTConnection link;

	public KeyboardPC() {
		
		setTitle("Control");
		setBounds(400, 200, 500, 500);
		setLayout(new GridLayout(1, 3));
		this.addKeyListener(bh);

		msg = new JLabel("Message");
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		add(new JLabel(""));
		add(msg);
		add(new JLabel(""));
	}

	public static void main(String[] args) {
		KeyboardPC remoteKeyboard = new KeyboardPC();
		remoteKeyboard.connect();
		remoteKeyboard.setVisible(true);
		remoteKeyboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static class ButtonHandler implements KeyListener {

		public void keyPressed(KeyEvent ke) {
			try {
				if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyChar() == 'w') {
					msg.setText("Up");
					System.out.println("Up");
					outData.writeInt(10);outData.flush();
				}
				if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyChar() == 's') {
					msg.setText("Down");
					System.out.println("Down");
					outData.writeInt(20);outData.flush();
				}
				if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyChar() == 'a') {
					msg.setText("Left");
					System.out.println("Left");
					outData.writeInt(30);outData.flush();
				}
				if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyChar() == 'd') {
					msg.setText("Right");
					System.out.println("Right");
					outData.writeInt(40);outData.flush();
				}
				if(ke.getKeyChar() == 'q') {
					disconnect();
					msg.setText("Disconnected");
				}
				Thread.sleep(100);
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}

		public void keyTyped(KeyEvent ke) {}

		public void keyReleased(KeyEvent ke) {}

	}

	public void connect() {
		
		BTConnector bt = new BTConnector();
		link = bt.connect("00:16:53:44:C2:C6", NXTConnection.RAW);
		outData = link.openDataOutputStream();
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
