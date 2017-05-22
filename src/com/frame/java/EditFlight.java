package com.frame.java;

import java.awt.Window;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;


import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.flight.java.DateTime;
import com.flight.java.DbDelete;
import com.flight.java.DbSelect;
import com.flight.java.DbUpdate;
import com.flight.java.Flight;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class EditFlight {

	private JFrame frame;
	private JTextField IdtextField;
	private JTextField STtextField;
	private JTextField ATtextField_1;
	private JTextField Capp;
	private JTextField PriceText;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public EditFlight() {
		initialize();
	}

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	private void initialize() {

		Flight f = new DbSelect().FlightSelect(Login.FlightId);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 728, 556);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel FlightId = new JLabel("\u822A\u73ED\u53F7");
		FlightId.setBounds(20, 22, 60, 29);
		frame.getContentPane().add(FlightId);

		JLabel StartCity = new JLabel("\u8D77\u98DE\u57CE\u5E02");
		StartCity.setBounds(20, 61, 60, 29);
		frame.getContentPane().add(StartCity);

		JLabel ArrCity = new JLabel("\u964D\u843D\u57CE\u5E02");
		ArrCity.setBounds(20, 116, 60, 29);
		frame.getContentPane().add(ArrCity);

		JLabel StartTime = new JLabel("\u8D77\u98DE\u65F6\u95F4");
		StartTime.setBounds(20, 173, 60, 29);
		frame.getContentPane().add(StartTime);

		JLabel Price = new JLabel("\u4EF7\u683C");
		Price.setBounds(20, 300, 60, 29);
		frame.getContentPane().add(Price);

		JLabel Seat = new JLabel("\u5BB9\u91CF");
		Seat.setBounds(20, 261, 48, 29);
		frame.getContentPane().add(Seat);

		JButton Modify = new JButton("\u786E\u8BA4\u4FEE\u6539");

		Modify.setBounds(160, 428, 153, 37);
		frame.getContentPane().add(Modify);

		JLabel ArrTime = new JLabel("\u964D\u843D\u65F6\u95F4");
		ArrTime.setBounds(20, 212, 60, 29);
		frame.getContentPane().add(ArrTime);

		IdtextField = new JTextField();
		IdtextField.setBounds(90, 26, 161, 21);
		frame.getContentPane().add(IdtextField);
		IdtextField.setColumns(10);

		final JComboBox ArrCitycomboBox = new JComboBox();
		ArrCitycomboBox.setModel(new DefaultComboBoxModel(new String[] {
				f.getArrivalCity(), "\u5317\u4EAC", "\u4E0A\u6D77",
				"\u5929\u6D25", "\u91CD\u5E86", "\u54C8\u5C14\u6EE8",
				"\u957F\u6625", "\u6C88\u9633", "\u547C\u548C\u6D69\u7279",
				"\u77F3\u5BB6\u5E84", "\u4E4C\u9C81\u6728\u9F50",
				"\u5170\u5DDE", "\u897F\u5B81", "\u897F\u5B89 ",
				"\u94F6\u5DDD", "\u90D1\u5DDE", "\u6D4E\u5357", "\u592A\u539F",
				"\u5408\u80A5", "\u957F\u6C99", "\u6B66\u6C49", "\u5357\u4EAC",
				"\u6210\u90FD", "\u8D35\u9633", "\u6606\u660E", "\u5357\u5B81",
				"\u62C9\u8428", "\u676D\u5DDE", "\u5357\u660C", "\u5E7F\u5DDE",
				"\u798F\u5DDE", "\u53F0\u5317", "\u6D77\u53E3", "\u9999\u6E2F",
				"\u6FB3\u95E8" }));
		ArrCitycomboBox.setToolTipText("");
		ArrCitycomboBox.setBounds(90, 112, 127, 37);
		frame.getContentPane().add(ArrCitycomboBox);

		final JComboBox StartCitycomboBox = new JComboBox();
		StartCitycomboBox.setModel(new DefaultComboBoxModel(new String[] {
				f.getStartCity(), "\u5317\u4EAC", "\u4E0A\u6D77",
				"\u5929\u6D25", "\u91CD\u5E86", "\u54C8\u5C14\u6EE8",
				"\u957F\u6625", "\u6C88\u9633", "\u547C\u548C\u6D69\u7279",
				"\u77F3\u5BB6\u5E84", "\u4E4C\u9C81\u6728\u9F50",
				"\u5170\u5DDE", "\u897F\u5B81", "\u897F\u5B89 ",
				"\u94F6\u5DDD", "\u90D1\u5DDE", "\u6D4E\u5357", "\u592A\u539F",
				"\u5408\u80A5", "\u957F\u6C99", "\u6B66\u6C49", "\u5357\u4EAC",
				"\u6210\u90FD", "\u8D35\u9633", "\u6606\u660E", "\u5357\u5B81",
				"\u62C9\u8428", "\u676D\u5DDE", "\u5357\u660C", "\u5E7F\u5DDE",
				"\u798F\u5DDE", "\u53F0\u5317", "\u6D77\u53E3", "\u9999\u6E2F",
				"\u6FB3\u95E8" }));
		StartCitycomboBox.setToolTipText("");
		StartCitycomboBox.setBounds(90, 57, 127, 37);
		frame.getContentPane().add(StartCitycomboBox);

		STtextField = new JTextField();
		STtextField.setBounds(90, 177, 153, 21);
		frame.getContentPane().add(STtextField);
		STtextField.setColumns(10);

		ATtextField_1 = new JTextField();
		ATtextField_1.setBounds(90, 216, 153, 21);
		frame.getContentPane().add(ATtextField_1);
		ATtextField_1.setColumns(10);

		Capp = new JTextField();
		Capp.setBounds(90, 265, 153, 21);
		frame.getContentPane().add(Capp);
		Capp.setColumns(10);

		PriceText = new JTextField();
		PriceText.setBounds(90, 304, 153, 21);
		frame.getContentPane().add(PriceText);
		PriceText.setColumns(10);

		JLabel Warning = new JLabel("");
		Warning.setFont(new Font("宋体", Font.PLAIN, 15));
		Warning.setForeground(Color.RED);
		Warning.setBounds(338, 36, 171, 37);

		frame.getContentPane().add(Warning);
		JLabel lblyyyymmddhhmmss = new JLabel(
				"\u65F6\u95F4\u683C\u5F0F\u4E3AYYYY-MM-DD-HH-MM-SS");
		lblyyyymmddhhmmss.setForeground(Color.GRAY);
		lblyyyymmddhhmmss.setBounds(307, 180, 180, 15);
		frame.getContentPane().add(lblyyyymmddhhmmss);

		JLabel label = new JLabel("\u4F8B\u5982:2017-05-02-00-00-00");
		label.setForeground(Color.GRAY);
		label.setBounds(307, 219, 180, 15);
		frame.getContentPane().add(label);

		JLabel FlightState = new JLabel("\u822A\u73ED\u72B6\u6001");
		FlightState.setBounds(20, 364, 54, 15);
		frame.getContentPane().add(FlightState);

		final JComboBox FlightStateCombox = new JComboBox();
		FlightStateCombox.setFont(new Font("宋体", Font.PLAIN, 14));
		FlightStateCombox.setModel(new DefaultComboBoxModel(new String[] {
				f.getFlightStatus(), "UNPUBLISHED", "AVAILABLE", "FULL",
				"TERMINATE" }));

		FlightStateCombox.setBounds(90, 353, 116, 37);
		frame.getContentPane().add(FlightStateCombox);
		Modify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 修改航班信息
				Flight f = new DbSelect().FlightSelect(Login.FlightId);
				String name = IdtextField.getText();
				String st = STtextField.getText();

				String at = ATtextField_1.getText();
				String sc = StartCitycomboBox.getItemAt(
						StartCitycomboBox.getSelectedIndex()).toString();
				String ac = ArrCitycomboBox.getItemAt(
						ArrCitycomboBox.getSelectedIndex()).toString();
				String ca = Capp.getText();
				String price = PriceText.getText();
				String FS = FlightStateCombox.getItemAt(
						FlightStateCombox.getSelectedIndex()).toString();
				boolean x = new DbUpdate().FlightUpdate(Login.FlightId, st, at,
						sc, ac, st, Float.parseFloat(price),
						f.getCurrentPassengers(), Integer.parseInt(ca), FS,
						f.GetPassengerString(f.getPassengerId()), name);
				if (x) {
					frame.setVisible(false);
					AdminFunction window = new AdminFunction();
					window.getAdminFrame().setVisible(true);
					AllDialog.Dialog(window.getAdminFrame(), "修改成功");
				} else {
					AllDialog.Dialog(frame, "修改失败");
				}
			}
		});
		if (f.getFlightStatus().equals("TERMINATE")) {
			IdtextField.disable();
			STtextField.disable();
			ATtextField_1.disable();
			Capp.disable();
			PriceText.disable();
			PriceText.disable();
			ArrCitycomboBox.disable();
			StartCitycomboBox.disable();
			FlightStateCombox.disable();
			Warning.setText("航班已锁定，无法更改");
		}
		IdtextField.setText(f.getFlightName());
		STtextField.setText(DateTime.GetDateTimeStr(f.getStartTime()));
		ATtextField_1.setText(DateTime.GetDateTimeStr(f.getArrivalTime()));
		Capp.setText(Integer.toString(f.getSeatCapacity()));
		PriceText.setText(Float.toString(f.getPrice()));

		JButton button = new JButton("\u8FD4\u56DE");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				AdminFunction w = new AdminFunction();
				w.getAdminFrame().setVisible(true);
			}
		});
		button.setBounds(394, 428, 115, 37);
		frame.getContentPane().add(button);
		
		JButton del = new JButton("\u5220\u9664\u822A\u73ED");
		del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Flight f=new DbSelect().FlightSelect(Login.FlightId);
				//System.out.println(Login.FlightId);
				if(f.getFlightStatus().equals("UNPUBLISHED"))
				{
				boolean x=new DbDelete().FlightDelete(Login.FlightId);
				if(x)
				{
					frame.setVisible(false);
					AdminFunction window=new AdminFunction();
					window.getAdminFrame().setVisible(true);
					AllDialog.Dialog(window.getAdminFrame(), "删除成功");
					
				}
				else {
					AllDialog.Dialog(frame, "删除失败");
				}
				}
				else{
					AllDialog.Dialog(frame, "航班已发布，不能删除");
				}
			}
		});
		del.setBounds(619, 10, 93, 23);
		frame.getContentPane().add(del);

	}

	public Window getAminFrame1() {
		// TODO Auto-generated method stub
		return frame;
	}
}
