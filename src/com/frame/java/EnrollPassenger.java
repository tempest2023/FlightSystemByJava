package com.frame.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JPasswordField;

import com.flight.java.DbInsert;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EnrollPassenger {

	private JFrame frame;
	private JTextField nameText;
	private JTextField IdentityText;
	private JPasswordField passwordField;

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnrollPassenger window = new EnrollPassenger();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnrollPassenger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setFont(new Font("华文楷体", Font.PLAIN, 12));
		frame.setTitle("\u6CE8\u518C");
		frame.setBounds(100, 100, 634, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel realName = new JLabel("\u59D3        \u540D");
		realName.setFont(new Font("华文楷体", Font.PLAIN, 14));
		realName.setBounds(123, 73, 175, 38);
		frame.getContentPane().add(realName);

		JLabel identityID = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7");
		identityID.setFont(new Font("华文楷体", Font.PLAIN, 14));
		identityID.setBounds(123, 134, 175, 53);
		frame.getContentPane().add(identityID);

		JLabel password = new JLabel("\u5BC6        \u7801");
		password.setFont(new Font("华文楷体", Font.PLAIN, 14));
		password.setBounds(123, 210, 175, 45);
		frame.getContentPane().add(password);

		nameText = new JTextField();
		nameText.setBounds(322, 77, 146, 38);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);

		IdentityText = new JTextField();
		IdentityText.setBounds(322, 145, 146, 38);
		frame.getContentPane().add(IdentityText);
		IdentityText.setColumns(10);

		JButton btnNewButton = new JButton("\u53D6\u6D88");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frame.setVisible(false);
				Login window = new Login();
				window.getFrame().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("华文楷体", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnNewButton.setBounds(136, 304, 146, 41);
		frame.getContentPane().add(btnNewButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(322, 217, 146, 38);
		frame.getContentPane().add(passwordField);

		JButton button = new JButton("\u6CE8\u518C");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String realName = nameText.getText();
				String iden = IdentityText.getText();
				char[] pa = passwordField.getPassword();
				String pwd = "";
				for (int i = 0; i < pa.length; i++) {
					pwd += pa[i];
				}
				if (realName.equals("") || iden.equals("") || pwd.equals("")) {
					AllDialog.Dialog(frame, "输入信息不全");
				} else {
					boolean x = new DbInsert().PassengerInsert(realName, iden,
							pwd, "");
					if (x) {
						frame.setVisible(false);
						Login window = new Login();
						window.getFrame().setVisible(true);
						AllDialog.Dialog(window.getFrame(), "注册成功");
					} else {
						AllDialog.Dialog(frame, "注册失败");
					}

				}
			}
		});
		button.setFont(new Font("华文楷体", Font.PLAIN, 14));
		button.setBounds(322, 304, 146, 41);
		frame.getContentPane().add(button);

	}
}
