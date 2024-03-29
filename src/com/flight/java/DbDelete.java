package com.flight.java;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DbDelete {
	private DbConnect db = null;
	private Connection cn = null;
	private boolean re = false;
	private PreparedStatement pst = null;

	// ����idɾ���������ݵķ�����
	public boolean FlightDelete(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			//����ɾ��ֻ��ɾ��δ��������ֹ����
			this.pst = cn.prepareStatement("DELETE FROM `flight` WHERE Id="+id+" and FlightStatus='UNPUBLISHED';");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;
	}

	public boolean AdminDelete(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("DELETE FROM `admin` WHERE Id=" + id
					+ ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;
	}

	public boolean OrderDelete(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("DELETE FROM `order` WHERE Id=" + id
					+ ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;
	}

	public boolean PassengerDelete(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("DELETE FROM `passenger` WHERE Id="
					+ id + ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;

	}

	public static void main(String[] args) {
		/*
		 * Admin/Passenger/Flight/Order��ɾ������Example DbDelete d=new DbDelete();
		 * 
		 * if(d.AdminDelete(3)) { System.out.println("ɾ���ɹ�"); } else {
		 * System.out.println("ɾ��ʧ��"); } if(d.PassengerDelete(3)) {
		 * System.out.println("ɾ���ɹ�"); } else { System.out.println("ɾ��ʧ��"); }
		 * if(d.FlightDelete(3)) { System.out.println("ɾ���ɹ�"); } else {
		 * System.out.println("ɾ��ʧ��"); } if(d.OrderDelete(3)){
		 * System.out.println("ɾ���ɹ�"); } else { System.out.println("ɾ��ʧ��"); }
		 */
	}
}
