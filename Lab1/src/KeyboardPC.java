import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import lejos.remote.nxt.NXTConnection;
import lejos.remote.nxt.*;

public class KeyboardPC extends JFrame {
	private static final long serialVersionUID = 6950578690702745288L;
	private static JLabel L1;
	private static ButtonHandler bh = new ButtonHandler();
	private static DataOutputStream outData;
	private static NXTConnection link;

	public KeyboardPC() {
		setTitle("Control");
		setBounds(400, 200, 500, 500);
		setLayout(new GridLayout(1, 3));
		this.addKeyListener(bh);

		L1 = new JLabel("Message");
		L1.setHorizontalAlignment(SwingConstants.CENTER);
		add(new JLabel(""));
		add(L1);
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
					L1.setText("Up");
					System.out.println("Up");
					//outData.writeInt(1);
				}
				if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyChar() == 's') {
					L1.setText("Down");
					System.out.println("Down");
					//outData.writeInt(2);
				}
				if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyChar() == 'a') {
					L1.setText("Left");
					System.out.println("Left");
					//outData.writeInt(3);
				}
				if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyChar() == 'd') {
					L1.setText("Right");
					System.out.println("Right");
					//outData.writeInt(4);
				}
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}

		public void keyTyped(KeyEvent ke) {
			try {
				/*
				 * if(ke.getKeyChar() == 'w')outData.writeInt(1);
				 * if(ke.getKeyChar() == 's')outData.writeInt(2);
				 * if(ke.getKeyChar() == 'a')outData.writeInt(3);
				 * if(ke.getKeyChar() == 'd')outData.writeInt(4);
				 * if(ke.getKeyChar() == 'i')outData.writeInt(6);
				 * if(ke.getKeyChar() == 'k')outData.writeInt(7);
				 * outData.flush();
				 */
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}

		}

		public void keyReleased(KeyEvent ke) {
			try {
				/*
				 * if(ke.getKeyChar() == 'w'){outData.writeInt(10);}
				 * if(ke.getKeyChar() == 's'){outData.writeInt(20);}
				 * if(ke.getKeyChar() == 'a'){outData.writeInt(30);}
				 * if(ke.getKeyChar() == 'd'){outData.writeInt(40);}
				 * if(ke.getKeyChar() == 'i'){outData.writeInt(60);}
				 * if(ke.getKeyChar() == 'k'){outData.writeInt(70);}
				 * if(ke.getKeyChar() == 'q'){System.exit(0);}
				 * outData.flush();
				 */
			} catch (Exception ioe) {
				System.out.println("\nIO Exception writeInt");
			}
		}

	}

	public void connect() {
		
		BTConnector bt = new BTConnector();
		
		//link = bt.connect("someip or name", NXTConnection.RAW);
		link = bt.waitForConnection(10000, NXTConnection.RAW);
		
		outData = link.openDataOutputStream();
		System.out.println("\nNXT is Connected");

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
