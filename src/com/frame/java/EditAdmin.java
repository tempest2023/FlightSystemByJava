package com.frame.java;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.flight.java.Admin;
import com.flight.java.DbSelect;
import com.flight.java.DbUpdate;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditAdmin {

	private JFrame frame;
	private JPasswordField oldpass;
	private JPasswordField newpass;
	private JTextField newuser;
	private JTextField olduser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditAdmin window = new EditAdmin();
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
	public EditAdmin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 732, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("\u65B0\u5BC6\u7801");
		label.setBounds(168, 287, 108, 29);
		frame.getContentPane().add(label);

		JLabel lblNewLabel = new JLabel("\u65E7\u5BC6\u7801");
		lblNewLabel.setBounds(168, 237, 108, 29);
		frame.getContentPane().add(lblNewLabel);

		oldpass = new JPasswordField();
		oldpass.setBounds(324, 224, 193, 32);
		frame.getContentPane().add(oldpass);

		newpass = new JPasswordField();
		newpass.setBounds(324, 285, 193, 32);
		frame.getContentPane().add(newpass);

		JLabel label_1 = new JLabel("\u65B0\u7528\u6237\u540D");
		label_1.setBounds(168, 129, 108, 29);
		frame.getContentPane().add(label_1);

		newuser = new JTextField();
		newuser.setBounds(324, 126, 126, 35);
		frame.getContentPane().add(newuser);
		newuser.setColumns(10);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usernew = newuser.getText();
				String userold = olduser.getText();
				char[] p1 = oldpass.getPassword();
				char[] p2 = newpass.getPassword();
				String passold = "";
				String passnew = "";
				for (int i = 0; i < p1.length; i++) {
					passold += p1[i];
				}
				for (int i = 0; i < p2.length; i++) {
					passnew += p2[i];
				}
				if (Admin.CheckAdmin(userold, passold)) {
					boolean x = new DbUpdate().AdminUpdate(Login.AdminId,
							usernew, passnew);
					if (x) {
						frame.setVisible(false);
						AdminFunction window = new AdminFunction();
						window.getAdminFrame().setVisible(true);
					}
				} else {
					AllDialog.Dialog(frame, "¾ÉÃÜÂë´íÎó");
				}

			}
		});
		button.setBounds(297, 412, 153, 37);
		frame.getContentPane().add(button);

		JLabel label_2 = new JLabel("\u65E7\u7528\u6237\u540D");
		label_2.setBounds(168, 65, 54, 15);
		frame.getContentPane().add(label_2);

		olduser = new JTextField();
		olduser.setBounds(324, 62, 126, 35);
		frame.getContentPane().add(olduser);
		olduser.setColumns(10);
		Admin nowadmin = new DbSelect().AdminSelect(Login.AdminId);
		olduser.setText(nowadmin.getUsername());
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}
}
