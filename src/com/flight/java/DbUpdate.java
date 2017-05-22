package com.flight.java;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DbUpdate {
	private DbConnect db = null;
	private Connection cn = null;
	private boolean re = false;
	private PreparedStatement pst = null;

	/*
	 * 修改单个数据方法们
	 */
	public boolean PassengerUpdate(int id, String RealName, String IdentityId,
			String Password, String OrderList) {
		// 给密码进行md5加密然后在存储
		Password = Encode.MD5(Password);
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("UPDATE `passenger` SET `RealName`='"
							+ RealName + "',`IdentityId`='" + IdentityId
							+ "',`Password`='" + Password + "',`OrderList`='"
							+ OrderList + "' WHERE Id=" + id + ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;

	}

	public boolean AdminUpdate(int id, String username, String pwd) {
		// 给密码进行md5加密然后在存储
		pwd = Encode.MD5(pwd);
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("update admin SET Username='"
					+ username + "',Password='" + pwd + "' WHERE Id=" + id
					+ ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;

	}

	public boolean FlightUpdate(int id, String StartTime, String ArrivalTime,
			String StartCity, String ArrivalCity, String DepartureDate,
			float price, int CurrentPassengers, int SeatCapacity,
			String FlightStatus, String PassengerId, String FlightName) {
		if (!Flight.IsFlight(StartTime, ArrivalTime, StartCity, ArrivalCity,
				DepartureDate, price, CurrentPassengers, SeatCapacity,
				FlightStatus, PassengerId, FlightName)) {
			System.err.println("Flight数据不规范，无法更新");
			return false;
		}
		DbSelect select = new DbSelect();
		Flight f = select.FlightSelect(id);
		if (Flight.IsUpdateFlight(id, StartTime, ArrivalTime, StartCity,
				ArrivalCity, DepartureDate, price, CurrentPassengers,
				SeatCapacity, f.getFlightStatus(), PassengerId, FlightName) == 1) {
			// TERMINATE状态不能更改
			// System.err.println("Flight " + id + " 已鎖定，无法更新");
			return false;
		}
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		if (Flight.IsUpdateFlight(id, StartTime, ArrivalTime, StartCity,
				ArrivalCity, DepartureDate, price, CurrentPassengers,
				SeatCapacity, f.getFlightStatus(), PassengerId, FlightName) == 3) {
			// System.err.println("Flight " + id + " 更新了非重要数据");
			// AVAILABLE/FULL状态下，当前价格、容量、乘客数量、航班状态可以修改
			try {
				String _sql = "UPDATE flight SET Price=" + price
						+ ",CurrentPassengers=" + CurrentPassengers
						+ ",SeatCapacity=" + SeatCapacity + ", PassengerId='"
						+ PassengerId + "',FlightStatus='" + FlightStatus
						+ "' WHERE Id=" + id + ";";
				this.pst = cn.prepareStatement(_sql);

				this.re = pst.executeUpdate() == 1;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.re;
		}
		if (Flight.IsUpdateFlight(id, StartTime, ArrivalTime, StartCity,
				ArrivalCity, DepartureDate, price, CurrentPassengers,
				SeatCapacity, f.getFlightStatus(), PassengerId, FlightName) == 2) {
			// UNPUBLISHED状态可以任意更改
			try {
				this.pst = cn.prepareStatement("UPDATE flight SET StartTime='"
						+ StartTime + "',ArrivalTime='" + ArrivalTime
						+ "',StartCity='" + StartCity + "',ArrivalCity='"
						+ ArrivalCity + "',DepartureDate='" + DepartureDate
						+ "',Price=" + price + ",CurrentPassengers="
						+ CurrentPassengers + ",SeatCapacity=" + SeatCapacity
						+ ",FlightStatus='" + FlightStatus + "',PassengerId='"
						+ PassengerId + "',FlightName='" + FlightName
						+ "' WHERE Id=" + id + ";");
				this.re = pst.executeUpdate() == 1;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.re;

	}

	public boolean OrderUpdate(int id, int PassengerId, int Seat, int FlightId,
			String CreateDate, String Status) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("UPDATE `order` SET `PassengerId`="
					+ PassengerId + ",`Seat`=" + Seat + ",`FlightId`="
					+ FlightId + ",`CreateDate`='" + CreateDate
					+ "',`Status`='" + Status + "' WHERE Id=" + id + ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;

	}

	public boolean UpdateOrderList(int pid, String OrderList) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("UPDATE `passenger` SET `OrderList`='"
							+ OrderList + "' WHERE Id=" + pid + ";");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;
	}

	public static void main(String[] args) {

		/*
		 * 更新Passenger的航班Id（OrderList） System.out.println(new
		 * DbUpdate().UpdateOrderList(3, "1;"));
		 */
		/*
		 * Flight數據不規範Example DbSelect s = new DbSelect(); DbUpdate x = new
		 * DbUpdate(); Flight xFlight = s.FlightSelect(1); boolean r =
		 * x.FlightUpdate(xFlight.getId(),
		 * DateTime.GetDateTimeStr(xFlight.getStartTime()),
		 * DateTime.GetDateTimeStr(xFlight.getArrivalTime()),
		 * xFlight.getStartCity(), "NewYork", xFlight.getDepartureDate(), 900,
		 * xFlight.getCurrentPassengers(), xFlight.getSeatCapacity(),
		 * xFlight.getFlightStatus(),
		 * xFlight.GetPassengerString(xFlight.getPassengerId()),
		 * xFlight.getFlightName()); if (r) { DbSelect select = new DbSelect();
		 * Flight fx = select.FlightSelect(1); TestObject.print(fx);
		 * System.out.println("修改成功"); }
		 * 
		 * else { System.out.println("修改失败"); }
		 */
		/*
		 * Order表更新Example
		 * 
		 * DbUpdate x = new DbUpdate(); DbSelect s=new DbSelect(); Order
		 * o=s.OrderSelect(1); boolean r=x.OrderUpdate(o.getId(),
		 * o.getPassengerId().getId(), o.getSeat(), o.getFlightId().getId(),
		 * "2016-03-04-05-20-01", o.getStatus()); if(r)
		 * {System.out.println("修改成功");}else{System.out.println("修改失敗");}
		 */
		/*
		 * Passenger表更新Example
		 * 
		 * DbUpdate x = new DbUpdate(); DbSelect s=new DbSelect(); Passenger
		 * p=s.PassengerSelect(1); x.PassengerUpdate(p.getId(),
		 * "任涛",p.getIdentityId(),p.getPassword(),
		 * p.GetOrderString(p.getOrderList())); TestObject.print(p);
		 */
		/*
		 * Fligth表更新Example DbSelect s = new DbSelect(); Flight xFlight
		 * =s.FlightSelect(1); boolean r = x.FlightUpdate(xFlight.getId(),
		 * DateTime.GetDateTimeStr(xFlight.getStartTime()),
		 * DateTime.GetDateTimeStr(xFlight.getArrivalTime()),
		 * xFlight.getStartCity(), "北京", xFlight.getDepartureDate(),
		 * xFlight.getPrice(), xFlight.getCurrentPassengers(),
		 * xFlight.getSeatCapacity(), xFlight.getFlightStatus(),
		 * xFlight.GetPassengerString(xFlight.getPassengerId()),
		 * xFlight.getFlightName()); if (r) { DbSelect select = new DbSelect();
		 * Flight fx = select.FlightSelect(1); TestObject.print(fx);
		 * System.out.println("修改成功"); }
		 * 
		 * else { System.out.println("修改失败"); }
		 */

		/*
		 * Admin表更新Example
		 * 
		 * boolean r=x.AdminUpdate(1, "where is your home", "hell"); if (r) {
		 * DbSelect select=new DbSelect(); ResultSet set=select.AdminSelect();
		 * try { while (set.next()) {
		 * System.out.println(set.getInt(1)+"、"+set.getString
		 * (2)+":"+set.getString(3));
		 * 
		 * } } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * System.out.println("修改成功");
		 * 
		 * } else { System.out.println("修改失败"); }
		 */
	}
}
