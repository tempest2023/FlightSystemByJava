package com.flight.java;

enum Flight_Status {
	UNPUBLISHED("UNPUBLISHED"), AVAILABLE("AVAILABLE"), FULL("FULL"), TERMINATE(
			"TERMINATE");
	private String status;

	private Flight_Status(String status) {
		// TODO Auto-generated constructor stub
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

};

public class Flight {
	private int id = 0;
	private DateTime StartTime = null;
	private DateTime ArrivalTime = null;
	private String StartCity = "";
	private String ArrivalCity = "";
	private String DepartureDate = "";
	private float price = 0f;
	private int CurrentPassengers = 0;
	private int SeatCapacity = 0;
	private String FlightStatus = "";
	private int[] PassengerId;
	private String FlightName = "";

	public Flight(int id, String StartTime, String ArrivalTime,
			String StartCity, String ArrivalCity, String DepartureDate,
			float price, int CurrentPassengers, int SeatCapacity,
			String FlightStatus, String PassengerId, String FlightName) {
		this.id = id;
		this.StartTime = DateTime.GetDateTimeOb(StartTime);
		this.ArrivalTime = DateTime.GetDateTimeOb(ArrivalTime);
		this.StartCity = StartCity;
		this.ArrivalCity = ArrivalCity;
		this.DepartureDate = DepartureDate;
		this.price = price;
		this.CurrentPassengers = CurrentPassengers;
		this.SeatCapacity = SeatCapacity;
		this.FlightStatus = FlightStatus;
		this.PassengerId = this.GetPassengerList(PassengerId);
		this.FlightName = FlightName;
	}

	/*
	 * 航班状态检查 如果判断出错返回0 航班已终止返回1，航班锁定无法更改 航班未发布返回2，航班可以任意更改 航班已发布返回3，航班可以部分更改
	 */
	public static int IsUpdateFlight(int id, String StartTime,
			String ArrivalTime, String StartCity, String ArrivalCity,
			String DepartureDate, float price, int CurrentPassengers,
			int SeatCapacity, String FlightStatus, String PassengerId,
			String FlightName) {
		/*
		 * 规范条件： 1、TERMINATE状态不能更改 2、UNPUBLISHED状态可以任意更改
		 * 3、AVAILABLE/FULL状态下，起飞城市、到达城市、起飞时间、起飞日期等 重要信息不可修改，当前价格、容量、航班状态等信息可以修改
		 */

		// PS:使用枚举类的时候要想获取枚举名的值，需要使用getStatus函数
		if (FlightStatus.equals(Flight_Status.TERMINATE.getStatus())) {
			return 1;
		}
		if (FlightStatus.equals(Flight_Status.UNPUBLISHED.getStatus())) {
			return 2;
		}
		if (FlightStatus.equals(Flight_Status.FULL.getStatus())
				|| FlightStatus.equals(Flight_Status.AVAILABLE.getStatus())) {
			return 3;
		}
		return 0;
	}

	// 数据规范检查 数据规范返回true
	public static boolean IsFlight(String StartTime, String ArrivalTime,
			String StartCity, String ArrivalCity, String DepartureDate,
			float price, int CurrentPassengers, int SeatCapacity,
			String FlightStatus, String PassengerId, String FlightName) {
		/*
		 * 航班基本數據是否規範 數據篩選條件： 1、出發時間小於到達時間 2、到達時間比出發時間大2小時以上 3、出發地點和到達地點不相同
		 */
		DateTime starTime = new DateTime(StartTime);
		DateTime arrivalTime = new DateTime(ArrivalTime);

		if (!DateTime.CompareDate(arrivalTime, starTime)) {
			return false;

		}
		if (!DateTime.GetSub(arrivalTime, starTime)) {
			return false;
		}
		if (StartCity.equals(ArrivalCity)) {
			return false;
		}
		return true;
	}

	/*
	 * 航班乘客信息关联查询 根据航班id查询某一航班所有的预订信息 包括乘客姓名、身份证号、座位号、预订时间、订单状态
	 * 返回一组BookingInfo对象数组
	 */
	public static BookingInfo[] SelectFlightInfo(int id) {
		DbSelect sel = new DbSelect();
		Flight f = sel.FlightSelect(id);
		int[] pids = f.getPassengerId();
		BookingInfo[] re = new BookingInfo[pids.length];
		for (int i = 0; i < pids.length; i++) {
			Passenger p = sel.PassengerSelect(pids[i]);
			Order o = sel.OrderSelect(p.getId(), f.getId());
			BookingInfo b = new BookingInfo(f.getId(), p.getId(),
					p.getRealName(), p.getIdentityId(), o.getId(), o.getSeat(),
					o.getCreateDate(), o.getStatus());
			re[i] = b;
		}
		//
		return re;
	}

	/*
	 * 当乘客预订机票后，更新航班相关信息 返回true或false
	 */
	public static boolean ReserveFlight(int pid, int fid) {
		DbUpdate update = new DbUpdate();
		DbSelect select = new DbSelect();
		Flight xFlight = select.FlightSelect(fid);
		String flightStatus = xFlight.getCurrentPassengers() + 1 == xFlight
				.getSeatCapacity() ? Flight_Status.FULL.getStatus() : xFlight
				.getFlightStatus();
		int[] pids = xFlight.getPassengerId();
		String passengerIdString = "";
		if (pids != null) {
			for (int i = 0; i < pids.length; i++) {
				passengerIdString += pids[i] + ";";
			}
		}
		passengerIdString += pid + ";";
		boolean r = update.FlightUpdate(xFlight.getId(),
				DateTime.GetDateTimeStr(xFlight.getStartTime()),
				DateTime.GetDateTimeStr(xFlight.getArrivalTime()),
				xFlight.getStartCity(), xFlight.getArrivalCity(),
				xFlight.getDepartureDate(), xFlight.getPrice(),
				(xFlight.getCurrentPassengers() + 1),
				xFlight.getSeatCapacity(), flightStatus, passengerIdString,
				xFlight.getFlightName());
		if (r) {
			return true;
		}
		return false;

	}

	/*
	 * 当乘客退订机票后，更新航班相关信息 返回true或false
	 */
	public boolean UnreserveFlight(int pid, int fid) {
		DbUpdate update = new DbUpdate();
		DbSelect select = new DbSelect();
		Flight xFlight = select.FlightSelect(fid);
		String flightStatus = xFlight.getCurrentPassengers() - 1 < xFlight
				.getSeatCapacity() ? Flight_Status.AVAILABLE.getStatus()
				: xFlight.getFlightStatus();
		int[] pids = xFlight.getPassengerId();
		String passengerIdString = "";
		if (pids != null) {
			for (int i = 0; i < pids.length; i++) {
				if (pids[i] != pid) {
					passengerIdString += pids[i] + ";";
				}
			}
		}

		boolean r = update.FlightUpdate(xFlight.getId(),
				DateTime.GetDateTimeStr(xFlight.getStartTime()),
				DateTime.GetDateTimeStr(xFlight.getArrivalTime()),
				xFlight.getStartCity(), xFlight.getArrivalCity(),
				xFlight.getDepartureDate(), xFlight.getPrice(),
				(xFlight.getCurrentPassengers() - 1),
				xFlight.getSeatCapacity(), flightStatus, passengerIdString,
				xFlight.getFlightName());
		if (r) {
			return true;
		}
		return false;

	}

	public static void AutoUpdateStatus(DateTime NowDate) {
		DbSelect sel = new DbSelect();
		Flight[] flights = sel.FlightSelect();
		DbUpdate up = new DbUpdate();
		for (int i = 0; i < flights.length; i++) {
			if (flights[i].getFlightStatus().equals(
					Flight_Status.AVAILABLE.getStatus())
					|| flights[i].getFlightStatus().equals(
							Flight_Status.FULL.getStatus())) {
				if (NowDate.UpdateStatus(flights[i].getStartTime())) {
					// 修改航班状态为TERMINATE
					boolean re = up
							.FlightUpdate(
									flights[i].id,
									DateTime.GetDateTimeStr(flights[i].StartTime),
									DateTime.GetDateTimeStr(flights[i].ArrivalTime),
									flights[i].StartCity,
									flights[i].ArrivalCity,
									flights[i].DepartureDate,
									flights[i].price,
									flights[i].CurrentPassengers,
									flights[i].SeatCapacity,
									Flight_Status.TERMINATE.getStatus(),
									flights[i]
											.GetPassengerString(flights[i].PassengerId),
									flights[i].FlightName);
					if (!re) {
						System.err.println("航班状态自动更新出错");
					}
				}
			}
		}
	}

	// get方法们
	public int getId() {
		return id;
	}

	public DateTime getStartTime() {
		return StartTime;
	}

	public DateTime getArrivalTime() {
		return ArrivalTime;
	}

	public String getStartCity() {
		return StartCity;
	}

	public String getArrivalCity() {
		return ArrivalCity;
	}

	public String getDepartureDate() {
		return DepartureDate;
	}

	public float getPrice() {
		return price;
	}

	public int getCurrentPassengers() {
		return CurrentPassengers;
	}

	public int getSeatCapacity() {
		return SeatCapacity;
	}

	public String getFlightStatus() {
		return FlightStatus;
	}

	public int[] getPassengerId() {
		return PassengerId;
	}

	public String getFlightName() {
		return FlightName;
	}

	// 返回passages的Id序列
	public int[] GetPassengerList(String _o) {
		if (_o.length() > 0) {
			String[] _s;
			_s = _o.split(";");
			int[] _t = new int[_s.length];
			for (int i = 0; i < _s.length; i++) {
				_t[i] = Integer.parseInt(_s[i]);
			}
			return _t;
		} else {
			return null;
		}
	}

	public String GetPassengerString(int[] i) {
		String s = "";
		if (i != null) {
			for (int j = 0; j < i.length; j++) {
				s += Integer.toString(i[j]) + ";";
			}
		}
		return s;
	}

	// 返回一个标准DateTime对象
	public static DateTime GetTime(String time) {
		DateTime x = new DateTime(time);
		return x;
	}

	public static void main(String[] args) {
		/*
		 * BookingInfo[] r=Flight.SelectFlightInfo(1);
		 * 
		 * for (int i = 0; i < r.length; i++) { TestObject.print(r[i]); }
		 */
	}
}
