package com.frame.java;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AllDialog {

	static JFrame j = new JFrame();

	public static void Dialog(Frame f, String s) {
		JOptionPane.showMessageDialog(f, s, "ÌבÊ¾", JOptionPane.PLAIN_MESSAGE);
	}
}
