package com.frame.java;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import com.flight.java.DateTime;
import com.flight.java.DbSelect;

public class PassengerOrder {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PassengerOrder window = new PassengerOrder();
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
	public PassengerOrder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u6211\u7684\u8BA2\u5355");
		frame.setBounds(250, 50, 950, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton_1 = new JButton("\u9000\u51FA\u767B\u5F55");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);

				Login window = new Login();
				window.getFrame().setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("华文楷体", Font.PLAIN, 40));
		btnNewButton_1.setBounds(1611, 0, 361, 49);
		frame.getContentPane().add(btnNewButton_1);

		JButton button = new JButton("\u641C\u7D22");
		button.setFont(new Font("华文楷体", Font.PLAIN, 40));
		button.setBounds(1717, 83, 173, 41);
		frame.getContentPane().add(button);

		JLabel lblNewLabel_8 = new JLabel("\u4EF7\u683C");
		lblNewLabel_8.setFont(new Font("华文楷体", Font.PLAIN, 40));
		lblNewLabel_8.setBounds(1423, 205, 92, 44);
		frame.getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("\u822A\u73ED\u72B6\u6001");
		lblNewLabel_9.setFont(new Font("华文楷体", Font.PLAIN, 40));
		lblNewLabel_9.setBounds(1611, 205, 169, 44);
		frame.getContentPane().add(lblNewLabel_9);

		JLabel label = new JLabel(
				"\u53CC\u51FB\u8BA2\u5355\u53EF\u4EE5\u8FDB\u5165\u53D6\u6D88\u8BA2\u5355\u9875\u9762");
		label.setForeground(Color.RED);
		label.setBounds(26, 401, 301, 15);
		frame.getContentPane().add(label);
		
		String[] columnNames = { "ID", "座位号", "起飞城市", "降落城市", "起飞时间", "降落时间",
				"预定日期" };
		com.flight.java.Order[] order = new DbSelect().OrderSelect(
				Login.PassengerId, "");
		String[][] o_ob2 =null;
		if(order!=null)
		{
			o_ob2 = new String[order.length][7];
		if (order != null) {
			for (int i = 0; i < order.length; i++) {
				o_ob2[i][0] = Integer.toString(order[i].getId());
				o_ob2[i][1] = Integer.toString(order[i].getSeat());
				o_ob2[i][2] = order[i].getFlightId().getStartCity();
				o_ob2[i][3] = order[i].getFlightId().getArrivalCity();
				o_ob2[i][4] = DateTime.GetDateTimeStr(order[i].getFlightId()
						.getStartTime());
				o_ob2[i][5] = DateTime.GetDateTimeStr(order[i].getFlightId()
						.getArrivalTime());
				o_ob2[i][6] = DateTime.GetDateTimeStr(order[i].getCreateDate());
			}

		}
		}else{
			o_ob2 = new String[1][7];
			label.setText("您还没有订单，快去添加吧!");
		}
		table = new JTable(o_ob2, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7902867902078729470L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {// 点击几次，这里是双击事件
					// 跳转到取消订单页面
					int row = table.getSelectedRow();
					String preId1 = table.getValueAt(row, 0).toString();
					// System.out.println(preId1);
					frame.setVisible(false);
					Login.OrderId = Integer.parseInt(preId1);
					CancelOrder window = new CancelOrder();
					window.getFrame().setVisible(true);
				}
			}

		});
		table.setBounds(44, 129, 823, 396);
		TableColumn column = null;
		int colunms = table.getColumnCount();
		for (int i = 0; i < colunms; i++) {
			column = table.getColumnModel().getColumn(i);
			/* 将每一列的默认宽度设置为100 */
			column.setPreferredWidth(100);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(Color.LIGHT_GRAY);
		table.setSelectionForeground(Color.yellow);
		table.setBounds(21, 143, 822, 363);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 24, 898, 367);

		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(table);
		JLabel lblNewLabel_10 = new JLabel("\u9000\u8BA2");
		lblNewLabel_10.setFont(new Font("华文楷体", Font.PLAIN, 40));
		lblNewLabel_10.setBounds(1843, 205, 86, 44);
		frame.getContentPane().add(lblNewLabel_10);

		JButton button_1 = new JButton("\u8FD4\u56DE");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				Research window = new Research();
				window.getFrame().setVisible(true);

			}
		});
		button_1.setFont(new Font("华文楷体", Font.PLAIN, 40));
		button_1.setBounds(905, 1246, 173, 41);
		frame.getContentPane().add(button_1);

		JButton button_2 = new JButton("\u8FD4\u56DE");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				Research wResearch = new Research();
				wResearch.getFrame().setVisible(true);
			}
		});
		button_2.setFont(new Font("仿宋", Font.PLAIN, 20));
		button_2.setBounds(376, 462, 189, 49);
		frame.getContentPane().add(button_2);

		

	}

	public JFrame getFrame() {
		return this.frame;
	}
}
