package com.flight.java;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DbInsert {
	private DbConnect db = null;
	private Connection cn = null;
	private boolean re = false;
	private PreparedStatement pst = null;

	/*
	 * 按对象Insert
	 */
	public boolean PassengerInsert(Passenger p) {
		return new DbInsert().PassengerInsert(p.getRealName(),
				p.getIdentityId(), p.getPassword(),
				p.GetOrderString(p.getOrderList()));
	}

	public boolean AdminInsert(Admin admin) {
		return new DbInsert().AdminInsert(admin.getUsername(), admin.getPwd());
	}

	public boolean FlightInsert(Flight f) {
		return new DbInsert().FlightInsert(
				DateTime.GetDateTimeStr(f.getStartTime()),
				DateTime.GetDateTimeStr(f.getArrivalTime()), f.getStartCity(),
				f.getArrivalCity(), f.getDepartureDate(), f.getPrice(),
				f.getCurrentPassengers(), f.getSeatCapacity(),
				f.getFlightStatus(), f.GetPassengerString(f.getPassengerId()),
				f.getFlightName());
	}

	public boolean OrderInsert(Order o) {
		return new DbInsert().OrderInsert(o.getPassengerId().getId(),
				o.getSeat(), o.getFlightId().getId(),
				DateTime.GetDateTimeStr(o.getCreateDate()), o.getStatus());
	}

	/*
	 * 单个Insert方法们。所有Insert方法中的数组字段全部只接受 字符串。因为考虑到那些数组字段在Insert的时候应该都是空白的
	 */
	public boolean PassengerInsert(String RealName, String IdentityId,
			String Password, String OrderList) {
		// 给密码进行md5加密然后在存储
		Password = Encode.MD5(Password);
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("INSERT INTO `passenger`(`Id`, `RealName`, `IdentityId`, `Password`, `OrderList`) VALUES (NULL,'"
							+ RealName
							+ "','"
							+ IdentityId
							+ "','"
							+ Password
							+ "','" + OrderList + "');");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;

	}

	public boolean AdminInsert(String username, String pwd) {
		// 给密码进行md5加密然后在存储
		pwd = Encode.MD5(pwd);
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("INSERT INTO `admin`(`Id`, `Username`, `Password`) VALUES (NULL,'"
							+ username + "','" + pwd + "');");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;
	}

	public boolean OrderInsert(int PassengerId, int Seat, int FlightId,
			String CreateDate, String Status) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("INSERT INTO `order`(`Id`, `PassengerId`, `Seat`, `FlightId`, `CreateDate`, `Status`) VALUES (NULL,"
							+ PassengerId
							+ ","
							+ Seat
							+ ","
							+ FlightId
							+ ",'"
							+ CreateDate + "','" + Status + "');");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;
	}

	public boolean FlightInsert(String StartTime, String ArrivalTime,
			String StartCity, String ArrivalCity, String DepartureDate,
			float price, int CurrentPassengers, int SeatCapacity,
			String FlightStatus, String PassengerId, String FlightName) {
		// 数据规范检查
		if (!Flight.IsFlight(StartTime, ArrivalTime, StartCity, ArrivalCity,
				DepartureDate, price, CurrentPassengers, SeatCapacity,
				FlightStatus, PassengerId, FlightName)) {
			System.err.println("Flight数据不规范，无法添加");
			return false;
		}
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("INSERT INTO `flight`(`Id`, `StartTime`, `ArrivalTime`, `StartCity`, `ArrivalCity`, `DepartureDate`, `Price`, `CurrentPassengers`, `SeatCapacity`, `FlightStatus`, `PassengerId`, `FlightName`) VALUES (NULL,'"
							+ StartTime
							+ "','"
							+ ArrivalTime
							+ "','"
							+ StartCity
							+ "','"
							+ ArrivalCity
							+ "','"
							+ DepartureDate
							+ "',"
							+ price
							+ ","
							+ CurrentPassengers
							+ ","
							+ SeatCapacity
							+ ",'"
							+ FlightStatus
							+ "','"
							+ PassengerId
							+ "','"
							+ FlightName + "');");
			this.re = pst.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.re;

	}

	public static void main(String[] args) {
		/*
		 * Example 判斷Flight插入數據是否規範並選擇插入
		DbInsert d = new DbInsert();
		DbSelect s = new DbSelect();
		Flight xFlight = s.FlightSelect(1);
		boolean rex = false;
		System.out.println(DateTime.GetDateTimeStr(xFlight.getStartTime()));
		System.out.println(DateTime.GetDateTimeStr(xFlight.getArrivalTime()));
		DateTime _t1 = DateTime.GetDateTimeOb(DateTime.GetDateTimeStr(xFlight
				.getStartTime()));
		DateTime _t2 = DateTime.GetDateTimeOb(DateTime.GetDateTimeStr(xFlight
				.getArrivalTime()));
		System.out.println("起飞时间是否大于到达时间：" + DateTime.CompareDate(_t1, _t2));
		System.out.println("两时间差距是否大于2小时：" + DateTime.GetSub(_t1, _t2));
		rex = d.FlightInsert(DateTime.GetDateTimeStr(xFlight.getStartTime()),
				DateTime.GetDateTimeStr(xFlight.getArrivalTime()),
				xFlight.getStartCity(), "北安", xFlight.getDepartureDate(),
				xFlight.getPrice(), xFlight.getCurrentPassengers(),
				xFlight.getSeatCapacity(), xFlight.getFlightStatus(),
				xFlight.GetPassengerString(xFlight.getPassengerId()), "XZ98521");
		if (rex) {
			System.out.println("添加成功");
		} else {
			System.out.println("添加失败");
		}*/
		/*
		 * Passenger/Admin/Flight/Order Insert Example DbInsert d = new
		 * DbInsert(); DbSelect s=new DbSelect(); Flight
		 * xFlight=s.FlightSelect(1); Passenger passenger=s.PassengerSelect(2);
		 * Order o=s.OrderSelect(1); boolean rex=false;
		 * rex=d.OrderInsert(o.getPassengerId().getId(), o.getSeat(),
		 * o.getFlightId().getId(), DateTime.GetDateTimeStr(o.getCreateDate()),
		 * o.getStatus());
		 * if(rex){System.out.println("添加成功");}else{System.out.println("添加失败");}
		 * rex
		 * =d.FlightInsert(DateTime.GetDateTimeStr(xFlight.getStartTime()),DateTime
		 * .GetDateTimeStr(xFlight.getArrivalTime()),xFlight.getStartCity(),
		 * "北京", xFlight.getDepartureDate(),xFlight.getPrice(),
		 * xFlight.getCurrentPassengers(),xFlight.getSeatCapacity(),
		 * xFlight.getFlightStatus
		 * (),xFlight.GetPassengerString(xFlight.getPassengerId()),"XZ98521");
		 * if(rex){System.out.println("添加成功");}else{System.out.println("添加失败");}
		 * rex=d.AdminInsert("Admin888","admin888");
		 * if(rex){System.out.println("添加成功");}else{System.out.println("添加失败");}
		 * rex
		 * =d.PassengerInsert(passenger.getRealName(),passenger.getIdentityId(),
		 * "balabala", "");
		 * if(rex){System.out.println("添加成功");}else{System.out.println("添加失败");}
		 */
	}
}
