package com.frame.java;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import com.flight.java.DateTime;
import com.flight.java.DbSelect;
import com.flight.java.Flight;
import com.flight.java.Order;
import com.flight.java.Passenger;

import java.awt.event.MouseEvent;

public class Research {

	private JFrame frame;
	private JTable Flight_Table;
	private JScrollPane scrollPane;

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
					Research window = new Research();
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
	public Research() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame();
		Passenger p = new DbSelect().PassengerSelect(Login.PassengerId);
		if (p != null) {
			String Frametext = "航班信息   欢迎您：" + p.getRealName();
			frame.setTitle(Frametext);
		} else {
			frame.setTitle("航班信息");
		}

		frame.setBounds(250, 50, 950, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		setTable(frame);
		JButton btnNewButton = new JButton("\u6211\u7684\u8BA2\u5355");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				PassengerOrder window = new PassengerOrder();
				window.getFrame().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnNewButton.setBounds(842, 5, 92, 41);
		frame.getContentPane().add(btnNewButton);

		JButton button = new JButton("\u641C\u7D22");
		button.setFont(new Font("华文楷体", Font.PLAIN, 14));
		button.setBounds(1717, 83, 173, 41);
		frame.getContentPane().add(button);

		JLabel lblNewLabel_8 = new JLabel("\u4EF7\u683C");
		lblNewLabel_8.setFont(new Font("华文楷体", Font.PLAIN, 14));
		lblNewLabel_8.setBounds(1423, 205, 92, 44);
		frame.getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("\u822A\u73ED\u72B6\u6001");
		lblNewLabel_9.setFont(new Font("华文楷体", Font.PLAIN, 14));
		lblNewLabel_9.setBounds(1674, 205, 169, 44);
		frame.getContentPane().add(lblNewLabel_9);

		JLabel StartTimeLabel = new JLabel("\u8D77\u98DE\u65F6\u95F4");
		StartTimeLabel.setFont(new Font("华文楷体", Font.PLAIN, 14));
		StartTimeLabel.setBounds(391, 14, 92, 42);
		frame.getContentPane().add(StartTimeLabel);

		final DateChooser dateChooser = new DateChooser(frame.getContentPane(),
				100);
		dateChooser.setBounds(366, 66, 126, 42);
		frame.getContentPane().add(dateChooser);

		JLabel EndTimeLabel = new JLabel("\u5230\u8FBE\u65F6\u95F4");
		EndTimeLabel.setBounds(546, 28, 54, 15);
		frame.getContentPane().add(EndTimeLabel);

		final DateChooser dateChooser2 = new DateChooser(
				frame.getContentPane(), 100);
		dateChooser2.setBounds(521, 66, 126, 42);
		frame.getContentPane().add(dateChooser2);

		JButton button_1 = new JButton("\u8FD4\u56DE");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);

				Login window = new Login();
				window.getFrame().setVisible(true);
			}
		});
		button_1.addMouseListener(new MouseAdapter() {

		});
		button_1.setFont(new Font("华文楷体", Font.PLAIN, 14));
		button_1.setBounds(869, 1208, 173, 41);
		frame.getContentPane().add(button_1);

		JLabel label = new JLabel("\u8D77\u98DE\u57CE\u5E02");
		label.setBounds(79, 21, 60, 29);
		frame.getContentPane().add(label);

		final JComboBox startCity = new JComboBox();
		startCity.setModel(new DefaultComboBoxModel(new String[] {
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
		startCity.setToolTipText("");
		startCity.setBounds(41, 62, 127, 37);
		frame.getContentPane().add(startCity);

		final JComboBox arrivalCity = new JComboBox();
		arrivalCity.setModel(new DefaultComboBoxModel(new String[] {
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
		arrivalCity.setToolTipText("");
		arrivalCity.setBounds(208, 62, 127, 37);
		frame.getContentPane().add(arrivalCity);

		JLabel label_1 = new JLabel("\u964D\u843D\u57CE\u5E02");
		label_1.setBounds(233, 21, 60, 29);
		frame.getContentPane().add(label_1);

		JButton search = new JButton("\u67E5\u8BE2");
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String s1 = startCity.getItemAt(startCity.getSelectedIndex())
						.toString();
				String s2 = arrivalCity.getItemAt(
						arrivalCity.getSelectedIndex()).toString();
				String date1 = dateChooser.getText();
				String date2 = dateChooser2.getText();
				com.flight.java.Flight[] flights2 = new DbSelect()
						.FlightSelect(date1, date2, s1, s2);
				if (flights2 != null) {
					setTable(frame, flights2);
				} else {
					AllDialog.Dialog(frame, "不存在符合条件的航班");
				}
			}
		});
		search.setFont(new Font("宋体", Font.PLAIN, 16));
		search.setBounds(700, 66, 69, 42);
		frame.getContentPane().add(search);

		JLabel label_2 = new JLabel(
				"\u63D0\u793A\uFF1A\u53CC\u51FB\u822A\u73ED\u5373\u53EF\u9884\u5B9A\u822A\u73ED");
		label_2.setForeground(Color.RED);
		label_2.setBounds(12, 510, 344, 15);
		frame.getContentPane().add(label_2);

		JButton fresh = new JButton("\u5237\u65B0\u5217\u8868");
		fresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setTable(frame);
			}
		});
		fresh.setBounds(837, 56, 87, 42);
		frame.getContentPane().add(fresh);

		JButton button_2 = new JButton("\u9000\u51FA\u767B\u5F55");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login.PassengerId = 0;
				Login.OrderId = 0;
				frame.setVisible(false);
				Login window = new Login();
				window.getFrame().setVisible(true);
			}
		});
		button_2.setBounds(800, 516, 109, 36);
		frame.getContentPane().add(button_2);
	}

	private void setTable(final JFrame frame) {
		String[] columnNames = { "ID", "航班号", "起飞城市", "降落城市", "起飞时间", "降落时间",
				"价格", "是否预定" };
		com.flight.java.Flight[] flights = new DbSelect().FlightSelectForPass();
		String[][] flight_ob = new String[flights.length][8];

		for (int i = 0; i < flights.length; i++) {
			flight_ob[i][0] = Integer.toString(flights[i].getId());
			flight_ob[i][1] = flights[i].getFlightName();
			flight_ob[i][2] = flights[i].getStartCity();
			flight_ob[i][3] = flights[i].getArrivalCity();
			flight_ob[i][4] = DateTime
					.GetDateTimeStr(flights[i].getStartTime());
			flight_ob[i][5] = DateTime.GetDateTimeStr(flights[i]
					.getArrivalTime());
			flight_ob[i][6] = String.valueOf(flights[i].getPrice());
			if (Order.IsHasOrder(Login.PassengerId, flights[i].getId())) {
				flight_ob[i][7] = "未预定";
			} else {
				flight_ob[i][7] = "已预定";
			}

			// System.out.println(flights[i].getFlightStatus());
		}
		// DefaultTableModel model=new DefaultTableModel(flight_ob,columnNames);
		Flight_Table = new JTable(flight_ob, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4737302915707325665L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		TableColumn column = null;
		int colunms = Flight_Table.getColumnCount();
		for (int i = 0; i < colunms; i++) {
			column = Flight_Table.getColumnModel().getColumn(i);
			/* 将每一列的默认宽度设置为100 */
			column.setPreferredWidth(100);
		}
		Flight_Table.getColumnModel().getColumn(0).setPreferredWidth(20);
		Flight_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Flight_Table.setSelectionBackground(Color.LIGHT_GRAY);
		Flight_Table.setSelectionForeground(Color.yellow);
		Flight_Table.setBounds(21, 143, 700, 363);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 143, 912, 363);

		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(Flight_Table);
		Flight_Table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {// 点击几次，这里是双击事件
					// 跳转到预定航班页面
					int row = Flight_Table.getSelectedRow();
					String preId1 = Flight_Table.getValueAt(row, 0).toString();
					// System.out.println(preId1);
					frame.setVisible(false);
					Login.FlightId = Integer.parseInt(preId1);
					ReserveFlight window = new ReserveFlight();
					window.getFrame().setVisible(true);
				}
			}
		});
	}

	private void setTable(final JFrame frame, Flight[] flights) {
		String[] columnNames = { "ID", "航班号", "起飞城市", "降落城市", "起飞时间", "降落时间",
				"价格", "是否预定" };
		String[][] flight_ob = new String[flights.length][8];

		for (int i = 0; i < flights.length; i++) {
			flight_ob[i][0] = Integer.toString(flights[i].getId());
			flight_ob[i][1] = flights[i].getFlightName();
			flight_ob[i][2] = flights[i].getStartCity();
			flight_ob[i][3] = flights[i].getArrivalCity();
			flight_ob[i][4] = DateTime
					.GetDateTimeStr(flights[i].getStartTime());
			flight_ob[i][5] = DateTime.GetDateTimeStr(flights[i]
					.getArrivalTime());
			flight_ob[i][6] = String.valueOf(flights[i].getPrice());
			if (Order.IsHasOrder(Login.PassengerId, flights[i].getId())) {
				flight_ob[i][7] = "未预定";
			} else {
				flight_ob[i][7] = "已预定";
			}
			// System.out.println(flights[i].getFlightStatus());
		}
		// DefaultTableModel model=new DefaultTableModel(flight_ob,columnNames);
		Flight_Table = new JTable(flight_ob, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5723427406160453043L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		TableColumn column = null;
		int colunms = Flight_Table.getColumnCount();
		for (int i = 0; i < colunms; i++) {
			column = Flight_Table.getColumnModel().getColumn(i);
			/* 将每一列的默认宽度设置为100 */
			column.setPreferredWidth(100);
		}
		Flight_Table.getColumnModel().getColumn(0).setPreferredWidth(20);
		Flight_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Flight_Table.setSelectionBackground(Color.LIGHT_GRAY);
		Flight_Table.setSelectionForeground(Color.yellow);
		Flight_Table.setBounds(21, 143, 700, 363);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 143, 912, 363);

		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(Flight_Table);
		Flight_Table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {// 点击几次，这里是双击事件
					// 跳转到预定航班页面
					int row = Flight_Table.getSelectedRow();
					String preId1 = Flight_Table.getValueAt(row, 0).toString();
					// System.out.println(preId1);
					frame.setVisible(false);
					Login.FlightId = Integer.parseInt(preId1);
					ReserveFlight window = new ReserveFlight();
					window.getFrame().setVisible(true);
				}
			}
		});
	}
}
