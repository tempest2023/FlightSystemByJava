package com.frame.java;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.flight.java.Admin;
import com.flight.java.DateTime;
import com.flight.java.DbSelect;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JRadioButton;

public class AdminFunction {

	private JFrame AdminFrame;
	private JTable Flight_Table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFunction window = new AdminFunction();
					window.AdminFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminFunction() {
		initialize();
	}

	public JFrame getAdminFrame() {
		return this.AdminFrame;
	}

	/**
	 * Initialize the contents of the AdminFrame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		AdminFrame = new JFrame();
		AdminFrame.setTitle("\u7BA1\u7406\u9875\u9762");
		AdminFrame.setResizable(false);
		AdminFrame.setBounds(100, 100, 859, 666);
		AdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AdminFrame.getContentPane().setLayout(null);

		JButton addAdmin = new JButton("\u6DFB\u52A0\u7BA1\u7406\u5458");
		addAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AdminFrame.setVisible(false);
				AddAdmin window = new AddAdmin();
				window.getFrame().setVisible(true);
			}
		});
		addAdmin.setBounds(271, 0, 153, 37);
		AdminFrame.getContentPane().add(addAdmin);

		final JRadioButton CheckDateRadio = new JRadioButton(
				"\u4E0D\u9009\u62E9\u65E5\u671F\u641C\u7D22");
		CheckDateRadio.setSelected(true);
		CheckDateRadio.setBounds(691, 63, 121, 23);
		AdminFrame.getContentPane().add(CheckDateRadio);

		JButton ModifyP = new JButton("\u4FEE\u6539\u4E2A\u4EBA\u4FE1\u606F");
		ModifyP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminFrame.setVisible(false);
				EditAdmin window = new EditAdmin();
				window.getFrame().setVisible(true);
			}
		});
		ModifyP.setBounds(448, 0, 177, 37);
		AdminFrame.getContentPane().add(ModifyP);

		JButton Out = new JButton("\u9000\u51FA\u767B\u5F55");
		Out.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login.AdminId = 0;
				AdminFrame.setVisible(false);
				Login window = new Login();
				window.getFrame().setVisible(true);
			}
		});
		Out.setBounds(665, 0, 147, 37);
		AdminFrame.getContentPane().add(Out);

		final JComboBox startCity = new JComboBox();
		startCity.setModel(new DefaultComboBoxModel(new String[] { " ",
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
		startCity.setBounds(21, 96, 127, 37);
		AdminFrame.getContentPane().add(startCity);

		final JComboBox arrivalCity = new JComboBox();
		arrivalCity.setModel(new DefaultComboBoxModel(new String[] { " ",
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
		arrivalCity.setBounds(228, 96, 127, 37);
		AdminFrame.getContentPane().add(arrivalCity);

		JLabel label = new JLabel("\u8D77\u59CB\u57CE\u5E02");
		label.setBounds(34, 60, 108, 29);
		AdminFrame.getContentPane().add(label);

		JLabel label_1 = new JLabel("\u964D\u843D\u57CE\u5E02");
		label_1.setBounds(238, 60, 108, 29);
		AdminFrame.getContentPane().add(label_1);

		JButton Exchange = new JButton("\u4EA4\u6362");
		Exchange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 获取JComboBox的值
				String s1 = startCity.getItemAt(startCity.getSelectedIndex())
						.toString();
				String s2 = arrivalCity.getItemAt(
						arrivalCity.getSelectedIndex()).toString();
				startCity.setSelectedItem(s2);
				arrivalCity.setSelectedItem(s1);
			}
		});
		Exchange.setFont(new Font("宋体", Font.PLAIN, 12));
		Exchange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		Exchange.setBounds(157, 96, 63, 37);
		AdminFrame.getContentPane().add(Exchange);

		JLabel StartLabel = new JLabel("\u8D77\u98DE\u65E5\u671F");
		StartLabel.setBounds(394, 60, 108, 29);
		AdminFrame.getContentPane().add(StartLabel);

		JLabel ArrLabel = new JLabel("\u5230\u8FBE\u65E5\u671F");
		ArrLabel.setBounds(571, 67, 54, 29);
		AdminFrame.getContentPane().add(ArrLabel);

		String[] columnNames = { "ID", "航班号", "起飞城市", "降落城市", "起飞时间", "降落时间",
				"价格", "航班状态" };
		com.flight.java.Flight[] flights = new DbSelect().FlightSelect();
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
			flight_ob[i][7] = flights[i].getFlightStatus();
			// System.out.println(flights[i].getFlightStatus());
		}
		// DefaultTableModel model=new DefaultTableModel(flight_ob,columnNames);
		Flight_Table = new JTable(flight_ob, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2386572100105572210L;

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
		Flight_Table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Flight_Table.setSelectionBackground(Color.LIGHT_GRAY);
		Flight_Table.setSelectionForeground(Color.yellow);
		Flight_Table.setBounds(21, 143, 822, 363);
		Flight_Table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {// 点击几次，这里是双击事件
					// 跳转到修改航班
					int row = Flight_Table.getSelectedRow();
					String preId1 = Flight_Table.getValueAt(row, 0).toString();
					// System.out.println(preId1);
					AdminFrame.setVisible(false);
					Login.FlightId = Integer.parseInt(preId1);
					EditFlight window = new EditFlight();
					window.getFrame().setVisible(true);

				}
			}
		});
		// AdminFrame.getContentPane().add(Flight);
		JButton newFlight = new JButton("\u521B\u5EFA\u65B0\u822A\u73ED");
		newFlight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminFrame.setVisible(false);

				CreatFlight window = new CreatFlight();
				window.getFrame().setVisible(true);
			}
		});
		newFlight.setBounds(183, 541, 190, 50);
		AdminFrame.getContentPane().add(newFlight);

		// 起飞日期
		final DateChooser dateChooser = new DateChooser(
				AdminFrame.getContentPane());
		dateChooser.setBounds(364, 96, 153, 37);
		AdminFrame.getContentPane().add(dateChooser);
		// 降落日期
		final DateChooser dateChooser2 = new DateChooser(
				AdminFrame.getContentPane());
		dateChooser2.setBounds(534, 96, 153, 37);
		AdminFrame.getContentPane().add(dateChooser2);

		// Flight_Table的容器
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 143, 800, 363);

		AdminFrame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(Flight_Table);

		JLabel Introedit = new JLabel(
				"\u63D0\u793A:\u53CC\u51FB\u8868\u683C\u4E2D\u7684\u6570\u636E\u53EF\u4EE5\u8FDB\u5165\u7F16\u8F91\u9875\u9762   ");
		Introedit.setFont(new Font("宋体", Font.ITALIC, 12));
		Introedit.setForeground(Color.GRAY);
		Introedit.setBounds(34, 516, 734, 15);
		AdminFrame.getContentPane().add(Introedit);
		JButton button = new JButton("\u641C\u7D22");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String s1 = startCity.getItemAt(startCity.getSelectedIndex())
						.toString();
				String s2 = arrivalCity.getItemAt(
						arrivalCity.getSelectedIndex()).toString();
				String date1 = dateChooser.getText();
				String date2 = dateChooser2.getText();
				if (CheckDateRadio.isSelected()) {
					if (s1.equals(" ") && s2.equals(" ")) {
						// show_table(AdminFrame,new DbSelect().FlightSelect());
						AllDialog.Dialog(AdminFrame, "您搜索的条件为空");
					} else {
						show_table(AdminFrame,
								new DbSelect().FlightSelect("", "", s1, s2));
					}
				} else {
					if (s1.equals(" ") && s2.equals(" ")) {
						show_table(AdminFrame, new DbSelect().FlightSelect(
								date1, date2, "", ""));

					} else {
						show_table(AdminFrame, new DbSelect().FlightSelect(
								date1, date2, s1, s2));
					}
				}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(694, 96, 81, 37);
		AdminFrame.getContentPane().add(button);

		JLabel hallolabel = new JLabel("New label");
		hallolabel.setFont(new Font("宋体", Font.PLAIN, 18));
		hallolabel.setForeground(Color.BLUE);
		hallolabel.setBounds(0, 578, 199, 50);
		AdminFrame.getContentPane().add(hallolabel);

		JButton SelectOrder = new JButton("\u67E5\u8BE2\u8BA2\u5355");
		SelectOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminFrame.setVisible(false);
				OrderShow w = new OrderShow();
				w.getFrame().setVisible(true);
			}
		});

		SelectOrder.setBounds(424, 541, 153, 50);
		AdminFrame.getContentPane().add(SelectOrder);

		JButton Booking = new JButton("\u67E5\u8BE2\u9884\u8BA2\u4FE1\u606F");
		Booking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdminFrame.setVisible(false);
				BookingShow wBookingShow = new BookingShow();
				wBookingShow.getFrame().setVisible(true);
			}
		});
		Booking.setBounds(616, 541, 152, 50);
		AdminFrame.getContentPane().add(Booking);

		if (Login.AdminId != 0) {
			Admin nowadmin = new DbSelect().AdminSelect(Login.AdminId);

			hallolabel.setText("您好，管理员：" + nowadmin.getUsername());
		}

	}

	public void show_table(final JFrame AdminFrame,
			com.flight.java.Flight[] flights) {
		String[] columnNames = { "ID", "航班号", "起飞城市", "降落城市", "起飞时间", "降落时间",
				"价格", "航班状态" };
		// com.flight.java.Flight[] flights = new DbSelect().FlightSelect();
		if (flights != null) {
			String[][] flight_ob = new String[flights.length][8];
			for (int i = 0; i < flights.length; i++) {
				flight_ob[i][0] = Integer.toString(flights[i].getId());
				flight_ob[i][1] = flights[i].getFlightName();
				flight_ob[i][2] = flights[i].getStartCity();
				flight_ob[i][3] = flights[i].getArrivalCity();
				flight_ob[i][4] = DateTime.GetDateTimeStr(flights[i]
						.getStartTime());
				flight_ob[i][5] = DateTime.GetDateTimeStr(flights[i]
						.getArrivalTime());
				flight_ob[i][6] = String.valueOf(flights[i].getPrice());
				flight_ob[i][7] = flights[i].getFlightStatus();
				// System.out.println(flights[i].getFlightStatus());
			}
			Flight_Table = new JTable(flight_ob, columnNames) {
				/**
		 * 
		 */
				private static final long serialVersionUID = 176942350943898960L;

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
			Flight_Table.setBounds(21, 143, 822, 363);
			Flight_Table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() == 2) {// 点击几次，这里是双击事件
						// 跳转到修改航班
						int row = Flight_Table.getSelectedRow();
						String preId1 = Flight_Table.getValueAt(row, 0)
								.toString();
						// System.out.println(preId1);
						AdminFrame.setVisible(false);
						Login.FlightId = Integer.parseInt(preId1);
						EditFlight window = new EditFlight();
						window.getFrame().setVisible(true);
					}
				}

			});
			scrollPane.setViewportView(Flight_Table);
		} else {
			AllDialog.Dialog(AdminFrame, "不存在符合条件的搜索信息");
		}
	}
}
