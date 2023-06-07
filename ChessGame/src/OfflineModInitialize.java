import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class OfflineModInitialize {

	private JFrame frmNameAndStuff;
	private JTextField wPName;
	private JTextField bPName;
	private JTextField plusTimeTextField;

	/**
	 * Launch the application.
	 */
	public static void run() {
		try {
			OfflineModInitialize window = new OfflineModInitialize();
			window.frmNameAndStuff.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the application.
	 */
	public OfflineModInitialize() {
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
		
		JLabel lblWhitePlayerName = new JLabel("White Player Name :");
		lblWhitePlayerName.setBounds(20, 50, 126, 14);
		frmNameAndStuff.getContentPane().add(lblWhitePlayerName);
		
		wPName = new JTextField();
		wPName.setBounds(172, 47, 126, 20);
		frmNameAndStuff.getContentPane().add(wPName);
		wPName.setColumns(10);
		
		JLabel lblBlackPlayerName = new JLabel("Black Player Name :");
		lblBlackPlayerName.setBounds(20, 85, 126, 14);
		frmNameAndStuff.getContentPane().add(lblBlackPlayerName);
		
		bPName = new JTextField();
		bPName.setColumns(10);
		bPName.setBounds(172, 82, 126, 20);
		frmNameAndStuff.getContentPane().add(bPName);
		
		JSlider slider = new JSlider();
		slider.setMaximum(10);
		slider.setMinimum(1);
		slider.setBounds(98, 137, 200, 53);
		slider.setValue(1);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		frmNameAndStuff.getContentPane().add(slider);
		
		
		JLabel lblTimer = new JLabel("Timer :");
		lblTimer.setBounds(20, 137, 67, 53);
		frmNameAndStuff.getContentPane().add(lblTimer);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setBackground(new Color(169, 169, 169));
		btnNewButton.setBounds(77, 303, 241, 62);
		frmNameAndStuff.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!wPName.getText().isEmpty() && !bPName.getText().isEmpty() && !plusTimeTextField.getText().isEmpty()){
					frmNameAndStuff.dispose();
					OfflineBoard.run(wPName.getText(),bPName.getText(),slider.getValue(),Integer.parseInt(plusTimeTextField.getText()));
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
		lblPlustime.setBounds(20, 231, 88, 16);
		frmNameAndStuff.getContentPane().add(lblPlustime);
		
		plusTimeTextField = new JTextField();
		plusTimeTextField.setBounds(98, 229, 114, 20);
		frmNameAndStuff.getContentPane().add(plusTimeTextField);
		plusTimeTextField.setColumns(10);
	}
}
