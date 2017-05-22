package com.frame.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import com.flight.java.DateTime;
import com.flight.java.DbSelect;
import com.flight.java.DbUpdate;
import com.flight.java.Order;
import com.flight.java.Passenger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class CancelOrder {

	private JFrame frame;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CancelOrder window = new CancelOrder();
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
	public CancelOrder() {
		initialize();
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel(
				"\u4F60\u786E\u8BA4\u8981\u53D6\u6D88\u8BA2\u5355\u5417\uFF1F");
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setForeground(Color.RED);
		label.setBounds(112, 38, 208, 63);
		frame.getContentPane().add(label);

		JButton button = new JButton("\u8FD4\u56DE");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				PassengerOrder wMyOrder = new PassengerOrder();
				wMyOrder.getFrame().setVisible(true);
			}
		});
		button.setBounds(48, 183, 93, 47);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("\u786E\u8BA4\u53D6\u6D88");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				char[] pass=passwordField.getPassword();
				String pwd="";
				for (int i = 0; i < pass.length; i++) {
					pwd += pass[i];
					
				}
				Order o = new DbSelect().OrderSelect(Login.OrderId);

				boolean x = new DbUpdate().OrderUpdate(Login.OrderId, o
						.getPassengerId().getId(), o.getSeat(), o.getFlightId()
						.getId(), DateTime.GetDateTimeStr(o.getCreateDate()),
						"CANCEL") && Passenger.UnsubscribeFlight(o.getPassengerId().getId(),o.getFlightId().getId(),pwd);
				if (x) {
					frame.setVisible(false);
					PassengerOrder wMyOrder = new PassengerOrder();
					wMyOrder.getFrame().setVisible(true);
					AllDialog.Dialog(wMyOrder.getFrame(), "订单取消成功");
				} else {
					AllDialog.Dialog(frame, "订单取消失败");
				}
			}
		});
		button_1.setBounds(283, 183, 93, 47);
		frame.getContentPane().add(button_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(176, 122, 144, 29);
		frame.getContentPane().add(passwordField);
		
		JLabel label_1 = new JLabel("\u8F93\u5165\u5BC6\u7801");
		label_1.setBounds(112, 133, 54, 15);
		frame.getContentPane().add(label_1);
	}
}
