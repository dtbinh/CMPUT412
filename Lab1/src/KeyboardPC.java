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

	
	private static KeyState upState;
	private static KeyState downState;
	private static KeyState leftState;
	private static KeyState rightState;
	private static boolean connected=true; 
	
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
		
		upState = new KeyState('w');
		downState = new KeyState('s');
		leftState = new KeyState('a');
		rightState = new KeyState('d');
		
		Thread thread = new Thread() {
			public void run() {
				while (connected) {
					try {
						Thread.sleep(16);
						updateStates();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		
	}

	public static void main(String[] args) {
		KeyboardPC remoteKeyboard = new KeyboardPC();
		remoteKeyboard.connect();
		remoteKeyboard.setVisible(true);
		remoteKeyboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void updateStates() throws IOException{
		if(upState.isChanged()){
			boolean up = upState.isPressed();
			System.out.println("Up = "+up);
			if(up)
				outData.writeInt(10);
			else
				outData.writeInt(-10);
			outData.flush();
		}
		if(downState.isChanged()){
			boolean down = downState.isPressed();
			if(down)
				outData.writeInt(20);
			else
				outData.writeInt(-20);
			outData.flush();
			System.out.println("down "+down);
		}
		if(leftState.isChanged()){
			boolean left = leftState.isPressed();
			if(left)
				outData.writeInt(30);
			else
				outData.writeInt(-30);
			outData.flush();
			
			System.out.println("left "+left);
		}
		if(rightState.isChanged()){
			boolean right = rightState.isPressed();
			if(right)
				outData.writeInt(40);
			else
				outData.writeInt(-40);
			outData.flush();
			System.out.println("right = "+right);
		}
		
		if (!upState.isPressed() &&
				!downState.isPressed() &&
				!leftState.isPressed() &&
				!rightState.isPressed()) {
			outData.writeInt(50);
			outData.flush();
		}
	}
	

	private static class ButtonHandler implements KeyListener {
		ButtonHandler() {
		}
		
		public void keyPressed(KeyEvent ke) {
			
			upState.check(true, ke.getKeyChar());
			downState.check(true, ke.getKeyChar());
			leftState.check(true, ke.getKeyChar());
			rightState.check(true, ke.getKeyChar());
			
			
			try {
				if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyChar() == 'w') {
					msg.setText("Up");
				}
				if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyChar() == 's') {
					msg.setText("Down");
				}
				if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyChar() == 'a') {
					msg.setText("Left");
				}
				if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyChar() == 'd') {
					msg.setText("Right");
				}
		
				if(ke.getKeyChar() == 'q') {
					disconnect();
					connected=false;
					msg.setText("Disconnected");
				}
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}

		public void keyTyped(KeyEvent ke) {}

		public void keyReleased(KeyEvent ke) {
			upState.check(false, ke.getKeyChar());
			downState.check(false, ke.getKeyChar());
			leftState.check(false, ke.getKeyChar());
			rightState.check(false, ke.getKeyChar());
		}

	}

	public void connect() {
		
		BTConnector bt = new BTConnector();
		link = bt.connect("00:16:53:44:C2:C6", NXTConnection.RAW);
		outData = link.openDataOutputStream();
		connected = true;
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
