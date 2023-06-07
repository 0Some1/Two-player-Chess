import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.*;

public class OnlineModInitialize {

	private JFrame frmNameAndStuff;
	private JTextField wPName;
	private JTextField plusTimeTextField;

	/**
	 * Launch the application.
	 */
	public static void run() {
		try {
			OnlineModInitialize window = new OnlineModInitialize();
			window.frmNameAndStuff.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the application.
	 */
	public OnlineModInitialize() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNameAndStuff = new JFrame();
		frmNameAndStuff.setTitle("Name and stuff");
		frmNameAndStuff.setBounds(100, 100, 414, 500);
		frmNameAndStuff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNameAndStuff.getContentPane().setLayout(null);
		frmNameAndStuff.setLocationRelativeTo(null);
		
		JLabel lblWhitePlayerName = new JLabel(" Player Name :");
		lblWhitePlayerName.setBounds(20, 50, 126, 14);
		frmNameAndStuff.getContentPane().add(lblWhitePlayerName);
		
		wPName = new JTextField();
		wPName.setBounds(172, 47, 126, 20);
		frmNameAndStuff.getContentPane().add(wPName);
		wPName.setColumns(10);
		
		JSlider slider = new JSlider();
		slider.setMaximum(10);
		slider.setMinimum(1);
		slider.setBounds(98, 101, 200, 53);
		slider.setValue(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		frmNameAndStuff.getContentPane().add(slider);
		
		
		JLabel lblTimer = new JLabel("Timer :");
		lblTimer.setBounds(20, 101, 60, 53);
		frmNameAndStuff.getContentPane().add(lblTimer);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setBackground(new Color(169, 169, 169));
		btnNewButton.setBounds(77, 303, 241, 62);
		frmNameAndStuff.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!wPName.getText().isEmpty()  && !plusTimeTextField.getText().isEmpty()){
					
					try {
						Socket socket=new Socket("localhost",9999);
						Scanner scanner=new Scanner(socket.getInputStream());
						JOptionPane.showMessageDialog(frmNameAndStuff,"Please Wait","please wait !",JOptionPane.PLAIN_MESSAGE);
						if(scanner.hasNextLine()){
							String s=scanner.nextLine();
							JOptionPane.showMessageDialog(frmNameAndStuff,s,"Game",JOptionPane.PLAIN_MESSAGE);
							if (s.contains("White")){
								OnlineBoard.run(socket,wPName.getText(),slider.getValue(),Integer.parseInt(plusTimeTextField.getText()),0);
								frmNameAndStuff.dispose();
							}else {
								OnlineBoard.run(socket,wPName.getText(),slider.getValue(),Integer.parseInt(plusTimeTextField.getText()),1);
								frmNameAndStuff.dispose();
							}

						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmNameAndStuff.dispose();
				Welcome.main(null);
			}
		});
		btnBack.setBackground(new Color(169, 169, 169));
		btnBack.setBounds(10, 424, 98, 26);
		frmNameAndStuff.getContentPane().add(btnBack);
		
		JLabel lblPlustime = new JLabel("PlusTime: ");
		lblPlustime.setBounds(20, 184, 88, 16);
		frmNameAndStuff.getContentPane().add(lblPlustime);
		
		plusTimeTextField = new JTextField();
		plusTimeTextField.setBounds(98, 184, 114, 20);
		frmNameAndStuff.getContentPane().add(plusTimeTextField);
		plusTimeTextField.setColumns(10);



	}
}
