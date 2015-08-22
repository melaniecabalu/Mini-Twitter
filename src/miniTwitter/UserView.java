package miniTwitter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class UserView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UserView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserViewHi = new JLabel("User view hi");
		lblUserViewHi.setBounds(185, 83, 108, 75);
		contentPane.add(lblUserViewHi);
	}

}
