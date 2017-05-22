package com.frame.java;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.flight.java.DbInsert;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreatFlight {

	private JFrame frame;
	private JTextField PriceText;
	private JTextField seatCapacityText;
	private JTextField flightnameText;
	private JTextField ArrTimeText;
	private JTextField StartTimeText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatFlight window = new CreatFlight();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Create the application.
	 */
	public CreatFlight() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 738, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("\u8D77\u98DE\u57CE\u5E02");
		label.setBounds(43, 75, 108, 29);
		frame.getContentPane().add(label);

		final JComboBox StartCitycomboBox = new JComboBox();
		StartCitycomboBox.setModel(new DefaultComboBoxModel(new String[] {
				"\u5317\u4EAC", "\u4E0A\u6D77", "\u5929\u6D25", "\u91CD\u5E86",
				"\u54C8\u5C14\u6EE8", "\u957F\u6625", "\u6C88\u9633",
				"\u547C\u548C\u6D69\u7279", "\u77F3\u5BB6\u5E84",
				"\u4E4C\u9C81\u6728\u9F50", "\u5170\u5DDE", "\u897F\u5B81",
				"\u897F\u5B89 ", "\u94F6\u5DDD", "\u90D1\u5DDE",
				"\u6D4E\u5357", "\u592A\u539F", "\u5408\u80A5", "\u957F\u6C99",
				"\u6B66\u6C49", "\u5357\u4EAC", "\u6210\u90FD", "\u8D35\u9633",
				"\u6606\u660E", "\u5357\u5B81", "\u62C9\u8428", "\u676D\u5DDE",
				"\u5357\u660C", "\u5E7F\u5DDE", "\u798F\u5DDE", "\u53F0\u5317",
				"\u6D77\u53E3", "\u9999\u6E2F", "\u6FB3\u95E8" }));
		StartCitycomboBox.setToolTipText("");
		StartCitycomboBox.setBounds(188, 75, 126, 35);
		frame.getContentPane().add(StartCitycomboBox);

		JLabel label2 = new JLabel("\u964D\u843D\u57CE\u5E02");
		label2.setBounds(43, 133, 108, 29);
		frame.getContentPane().add(label2);

		final JComboBox ArrCitycomboBox = new JComboBox();
		ArrCitycomboBox.setModel(new DefaultComboBoxModel(new String[] {
				"\u5317\u4EAC", "\u4E0A\u6D77", "\u5929\u6D25", "\u91CD\u5E86",
				"\u54C8\u5C14\u6EE8", "\u957F\u6625", "\u6C88\u9633",
				"\u547C\u548C\u6D69\u7279", "\u77F3\u5BB6\u5E84",
				"\u4E4C\u9C81\u6728\u9F50", "\u5170\u5DDE", "\u897F\u5B81",
				"\u897F\u5B89 ", "\u94F6\u5DDD", "\u90D1\u5DDE",
				"\u6D4E\u5357", "\u592A\u539F", "\u5408\u80A5", "\u957F\u6C99",
				"\u6B66\u6C49", "\u5357\u4EAC", "\u6210\u90FD", "\u8D35\u9633",
				"\u6606\u660E", "\u5357\u5B81", "\u62C9\u8428", "\u676D\u5DDE",
				"\u5357\u660C", "\u5E7F\u5DDE", "\u798F\u5DDE", "\u53F0\u5317",
				"\u6D77\u53E3", "\u9999\u6E2F", "\u6FB3\u95E8" }));
		ArrCitycomboBox.setToolTipText("");
		ArrCitycomboBox.setBounds(188, 141, 126, 35);
		frame.getContentPane().add(ArrCitycomboBox);

		final JComboBox FlightStateCombox = new JComboBox();
		FlightStateCombox.setFont(new Font("宋体", Font.PLAIN, 14));
		FlightStateCombox.setModel(new DefaultComboBoxModel(new String[] {
				"UNPUBLISHED", "AVAILABLE", "FULL", "TERMINATE" }));
		FlightStateCombox.setBounds(558, 79, 126, 31);
		frame.getContentPane().add(FlightStateCombox);

		JLabel label_2 = new JLabel("\u8D77\u98DE\u65F6\u95F4");
		label_2.setBounds(43, 212, 62, 29);
		frame.getContentPane().add(label_2);

		JLabel lblNewLabel = new JLabel("\u4EF7\u683C");
		lblNewLabel.setBounds(43, 306, 44, 29);
		frame.getContentPane().add(lblNewLabel);

		PriceText = new JTextField();
		PriceText.setFont(new Font("宋体", Font.PLAIN, 14));
		PriceText.setBounds(188, 306, 126, 31);
		frame.getContentPane().add(PriceText);
		PriceText.setColumns(10);

		JLabel label_6 = new JLabel("\u5143");
		label_6.setBounds(341, 306, 33, 30);
		frame.getContentPane().add(label_6);

		JLabel flightNamelabel = new JLabel("\u822A\u73ED\u53F7\uFF1A");
		flightNamelabel.setBounds(43, 36, 62, 29);
		frame.getContentPane().add(flightNamelabel);

		JButton Create = new JButton("\u786E\u8BA4\u521B\u5EFA");
		Create.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String FlightName = flightnameText.getText();
				String StartCity = StartCitycomboBox.getItemAt(
						StartCitycomboBox.getSelectedIndex()).toString();
				String ArrCity = ArrCitycomboBox.getItemAt(
						ArrCitycomboBox.getSelectedIndex()).toString();
				String StartTime = StartTimeText.getText();
				String ArrTime = ArrTimeText.getText();
				int Seat = Integer.parseInt(seatCapacityText.getText());
				String FlightStatus = FlightStateCombox.getItemAt(
						FlightStateCombox.getSelectedIndex()).toString();
				Float price = Float.parseFloat(PriceText.getText());
				boolean x = new DbInsert().FlightInsert(StartTime, ArrTime,
						StartCity, ArrCity, ArrTime, price, 0, Seat,
						FlightStatus, "", FlightName);
				if (x) {
					frame.setVisible(false);
					AdminFunction window = new AdminFunction();
					window.getAdminFrame().setVisible(true);
					AllDialog.Dialog(window.getAdminFrame(), "创建成功");
				} else {
					AllDialog.Dialog(frame, "创建失败，请重试");
				}
			}
		});
		Create.setBounds(480, 390, 153, 37);
		frame.getContentPane().add(Create);

		seatCapacityText = new JTextField();
		seatCapacityText.setFont(new Font("宋体", Font.PLAIN, 14));
		seatCapacityText.setBounds(558, 36, 126, 29);
		frame.getContentPane().add(seatCapacityText);
		seatCapacityText.setColumns(10);

		JLabel label_1 = new JLabel("\u5BB9\u91CF");
		label_1.setBounds(439, 36, 55, 29);
		frame.getContentPane().add(label_1);

		flightnameText = new JTextField();
		flightnameText.setFont(new Font("宋体", Font.PLAIN, 14));
		flightnameText.setText("");
		flightnameText.setBounds(188, 27, 126, 35);
		frame.getContentPane().add(flightnameText);
		flightnameText.setColumns(10);

		JLabel label_3 = new JLabel("\u964D\u843D\u65F6\u95F4");
		label_3.setBounds(43, 264, 54, 15);
		frame.getContentPane().add(label_3);

		ArrTimeText = new JTextField();
		ArrTimeText.setFont(new Font("宋体", Font.PLAIN, 14));
		ArrTimeText.setBounds(188, 261, 126, 35);
		frame.getContentPane().add(ArrTimeText);
		ArrTimeText.setColumns(10);

		StartTimeText = new JTextField();
		StartTimeText.setFont(new Font("宋体", Font.PLAIN, 14));
		StartTimeText.setBounds(188, 212, 126, 29);
		frame.getContentPane().add(StartTimeText);
		StartTimeText.setColumns(10);

		JLabel label_4 = new JLabel(
				"\u65F6\u95F4\u683C\u5F0F\u4E3AYYYY-MM-DD-HH-MM-SS");
		label_4.setForeground(Color.GRAY);
		label_4.setBounds(359, 226, 286, 15);
		frame.getContentPane().add(label_4);

		JLabel label_5 = new JLabel("\u4F8B\u5982:2017-05-02-00-00-00");
		label_5.setForeground(Color.GRAY);
		label_5.setBounds(359, 264, 180, 15);
		frame.getContentPane().add(label_5);

		JLabel lblNewLabel_1 = new JLabel("\u822A\u73ED\u72B6\u6001");
		lblNewLabel_1.setBounds(439, 89, 54, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JButton button = new JButton("\u8FD4\u56DE");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				AdminFunction windowAdmin = new AdminFunction();
				windowAdmin.getAdminFrame().setVisible(true);
			}
		});
		button.setBounds(188, 390, 126, 37);
		frame.getContentPane().add(button);

	}

}
