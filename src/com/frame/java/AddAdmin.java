package com.frame.java;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import com.flight.java.DbInsert;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddAdmin {

	private JFrame frame;
	private JTextField adminName;
	private JPasswordField adminPass;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public AddAdmin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 724, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("\u7BA1\u7406\u5458\u59D3\u540D");
		label.setBounds(113, 137, 136, 29);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setBounds(141, 214, 68, 29);
		frame.getContentPane().add(label_1);

		adminName = new JTextField();
		adminName.setBounds(356, 134, 126, 35);
		frame.getContentPane().add(adminName);
		adminName.setColumns(10);

		adminPass = new JPasswordField();
		adminPass.setBounds(356, 211, 126, 35);
		frame.getContentPane().add(adminPass);

		JButton Create = new JButton("\u786E\u8BA4\u521B\u5EFA");
		Create.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = adminName.getText();
				char[] pass = adminPass.getPassword();
				String pas = "";
				for (int i = 0; i < pass.length; i++) {
					pas += pass[i];

				}
				boolean x = new DbInsert().AdminInsert(name, pas);
				if (x) {
					// AllDialog.Dialog(frame, "添加成功");
					frame.setVisible(false);
					AdminFunction window = new AdminFunction();
					window.getAdminFrame().setVisible(true);
					AllDialog.Dialog(window.getAdminFrame(), "添加成功");
				} else {
					AllDialog.Dialog(frame, "添加失败，请重试");
				}
			}
		});
		Create.setBounds(275, 354, 153, 37);
		frame.getContentPane().add(Create);
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
