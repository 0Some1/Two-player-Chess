import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Font;

public class Welcome {

	private JFrame frmChess;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frmChess.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Welcome() {
		//new Initialize();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess = new JFrame();
		frmChess.setResizable(false);
		frmChess.setTitle("Chess");
		frmChess.setForeground(new Color(0, 0, 0));
		frmChess.setBackground(new Color(0, 0, 0));
		frmChess.getContentPane().setBackground(new Color(0, 0, 0));
		frmChess.setBounds(100, 100, 823, 500);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChess.getContentPane().setLayout(null);
		frmChess.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(97, 140, 829, 380);
		lblNewLabel.setIcon(new ImageIcon(Welcome.class.getResource("/resources/welcome.png")));
		frmChess.getContentPane().add(lblNewLabel);
		
		JButton btnPlayOfflineMod = new JButton("Play Offline Mod");
		btnPlayOfflineMod.setBackground(new Color(220, 220, 220));
		btnPlayOfflineMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.dispose();
				OfflineModInitialize.run();
			}
		});
		btnPlayOfflineMod.setBounds(10, 293, 158, 43);
		frmChess.getContentPane().add(btnPlayOfflineMod);
		
		JButton btnPlayOnlineMod = new JButton("Play Online Mod");
		btnPlayOnlineMod.setBackground(new Color(192, 192, 192));
		btnPlayOnlineMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.dispose();
				OnlineModInitialize.run();
			}
		});
		btnPlayOnlineMod.setBounds(10, 349, 158, 43);
		frmChess.getContentPane().add(btnPlayOnlineMod);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBackground(new Color(169, 169, 169));
		btnExit.setBounds(10, 403, 158, 43);
		frmChess.getContentPane().add(btnExit);
		
		JLabel lblChessGame = new JLabel("Chess Game");
		lblChessGame.setBackground(new Color(0, 0, 0));
		lblChessGame.setFont(new Font("Dialog", Font.BOLD, 18));
		lblChessGame.setForeground(new Color(245, 245, 245));
		lblChessGame.setBounds(357, 12, 148, 65);
		frmChess.getContentPane().add(lblChessGame);
	}
}
