package com.frame.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.flight.java.*;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
	static int FlightId = 0;
	static int AdminId = 0;
	static int PassengerId = 0;
	static int OrderId = 0;
	private JFrame frame;
	private JTextField RealName;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		TimeThread updateBegin = new TimeThread();
		Thread t1 = new Thread(updateBegin);
		t1.setPriority(Thread.MAX_PRIORITY);
		t1.start();
		frame = new JFrame();
		frame.setTitle("\u767B\u5F55\u9875\u9762");
		frame.setBounds(100, 100, 607, 423);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("\u7528\u6237\u540D");
		label.setBounds(49, 117, 108, 29);
		frame.getContentPane().add(label);

		RealName = new JTextField();
		RealName.setBounds(194, 114, 273, 35);
		frame.getContentPane().add(RealName);
		RealName.setColumns(10);

		JLabel label_1 = new JLabel("\u5BC6\u7801");
		label_1.setBounds(49, 200, 108, 29);
		frame.getContentPane().add(label_1);

		passwordField = new JPasswordField();
		passwordField.setBounds(194, 197, 273, 32);
		frame.getContentPane().add(passwordField);

		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				EnrollPassenger window = new EnrollPassenger();
				window.getFrame().setVisible(true);
			}
		});
		button.setBounds(106, 273, 153, 37);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("\u767B\u9646");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String user = RealName.getText();
				char[] pwd = passwordField.getPassword();
				String password = "";
				for (int i = 0; i < pwd.length; i++) {
					password += pwd[i];
				}
				boolean bo = Admin.CheckAdmin(user, password);
				if (bo) {
					// 管理员登录成功

					frame.setVisible(false);
					Login.AdminId = new DbSelect().AdminSelect(user).getId();
					AdminFunction window = new AdminFunction();
					window.getAdminFrame().setVisible(true);
				} else {
					boolean p = Passenger.CheckPwd(user, password);
					if (p) {
						// 用户登录成功
						Login.PassengerId = new DbSelect().PassengerSelect(
								user, password).getId();
						frame.setVisible(false);
						Research window = new Research();
						window.getFrame().setVisible(true);

					} else {
						AllDialog.Dialog(frame, "用户名或密码错误，请重试");
					}
				}

			}
		});
		button_1.setBounds(351, 273, 153, 37);
		frame.getContentPane().add(button_1);

	}

	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
