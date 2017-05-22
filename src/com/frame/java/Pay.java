package com.frame.java;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import com.flight.java.Flight;
import com.flight.java.Order;
import com.flight.java.Passenger;

public class Pay {

	private JFrame frame;
	private JPasswordField passwordField;
	private JButton back;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pay window = new Pay();
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
	public Pay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u652F\u4ED8\u9875\u9762");
		frame.setBounds(550, 100, 350, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel(
				"\u786E\u8BA4\u652F\u4ED8\u8BF7\u8F93\u5165\u5BC6\u7801");
		label.setBounds(10, 64, 129, 73);
		frame.getContentPane().add(label);

		JButton sure = new JButton("\u786E\u8BA4\u652F\u4ED8");
		sure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				char[] cpwd = passwordField.getPassword();
				String pwd = "";
				for (int i = 0; i < cpwd.length; i++) {
					pwd += cpwd[i];
				}
				if (Order.IsHasOrder(Login.PassengerId, Login.FlightId)) {
					boolean x = Passenger.ReserveFlight(Login.PassengerId,
							Login.FlightId, pwd);
					//Passenger 的预定航班会自动添加订单
					x = x && Flight.ReserveFlight(Login.PassengerId,Login.FlightId);
					if (x) {
						frame.setVisible(false);
						Research window = new Research();
						window.getFrame().setVisible(true);
						AllDialog.Dialog(window.getFrame(), "订票成功");
					} else {
						AllDialog.Dialog(frame, "您的支付密码错误，请重试");
					}
				} else {
					AllDialog.Dialog(frame, "订单重复，不能再购买");
				}
			}
		});
		sure.setBounds(41, 157, 93, 38);
		frame.getContentPane().add(sure);

		passwordField = new JPasswordField();
		passwordField.setBounds(181, 90, 129, 21);
		frame.getContentPane().add(passwordField);

		back = new JButton("\u8FD4\u56DE");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				ReserveFlight window = new ReserveFlight();
				window.getFrame().setVisible(true);
			}
		});
		back.setBounds(178, 157, 103, 38);
		frame.getContentPane().add(back);
	}

	public Window getFrame() {
		// TODO 自动生成的方法存根
		return frame;
	}
}
