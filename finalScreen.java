import javax.swing.*;

public class finalScreen extends JFrame {
	//background image
	ImageIcon backgroundIcon = new ImageIcon();
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public finalScreen(int playerWins) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// background image
		JLabel Screen = new JLabel("");
		Screen.setBounds(0, 0, 944, 501);
		getContentPane().add(Screen);
		if (playerWins == 1) {
			backgroundIcon = new ImageIcon("playerWins.png");
			Screen.setIcon(backgroundIcon);
		} else {
			backgroundIcon = new ImageIcon("playerLoses.png");
			Screen.setIcon(backgroundIcon);
		}
		
	}

}
