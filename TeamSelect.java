import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TeamSelect extends JFrame {
	//background image initialization
	ImageIcon teamSelect = new ImageIcon("TeamSelect.png");
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public TeamSelect() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//rebels button
		JButton rebelButton = new JButton("Rebels");
		rebelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					shipSelect frame = new shipSelect(1);
					frame.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		rebelButton.setBackground(Color.LIGHT_GRAY);
		rebelButton.setBounds(150, 350, 100, 40);
		contentPane.add(rebelButton);
		
		//empire button
		JButton empireButton = new JButton("Empire");
		empireButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					shipSelect frame = new shipSelect(0);
					frame.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		empireButton.setBackground(Color.LIGHT_GRAY);
		empireButton.setBounds(667, 350, 80, 40);
		contentPane.add(empireButton);
		
		//background image
		JLabel Screen = new JLabel("");
		Screen.setBounds(0, 0, 944, 501);
		getContentPane().add(Screen);
		Screen.setIcon(teamSelect);
	}
}
