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
	 * ����״̬��� ����жϳ�������0 ��������ֹ����1�����������޷����� ����δ��������2���������������� �����ѷ�������3��������Բ��ָ���
	 */
	public static int IsUpdateFlight(int id, String StartTime,
			String ArrivalTime, String StartCity, String ArrivalCity,
			String DepartureDate, float price, int CurrentPassengers,
			int SeatCapacity, String FlightStatus, String PassengerId,
			String FlightName) {
		/*
		 * �淶������ 1��TERMINATE״̬���ܸ��� 2��UNPUBLISHED״̬�����������
		 * 3��AVAILABLE/FULL״̬�£���ɳ��С�������С����ʱ�䡢������ڵ� ��Ҫ��Ϣ�����޸ģ���ǰ�۸�����������״̬����Ϣ�����޸�
		 */

		// PS:ʹ��ö�����ʱ��Ҫ���ȡö������ֵ����Ҫʹ��getStatus����
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

	// ���ݹ淶��� ���ݹ淶����true
	public static boolean IsFlight(String StartTime, String ArrivalTime,
			String StartCity, String ArrivalCity, String DepartureDate,
			float price, int CurrentPassengers, int SeatCapacity,
			String FlightStatus, String PassengerId, String FlightName) {
		/*
		 * ������������Ƿ�Ҏ�� �����Y�x�l���� 1�����l�r�gС춵��_�r�g 2�����_�r�g�ȳ��l�r�g��2С�r���� 3�����l���c�͵��_���c����ͬ
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
	 * ����˿���Ϣ������ѯ ���ݺ���id��ѯĳһ�������е�Ԥ����Ϣ �����˿�����������֤�š���λ�š�Ԥ��ʱ�䡢����״̬
	 * ����һ��BookingInfo��������
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
	 * ���˿�Ԥ����Ʊ�󣬸��º��������Ϣ ����true��false
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
	 * ���˿��˶���Ʊ�󣬸��º��������Ϣ ����true��false
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
					// �޸ĺ���״̬ΪTERMINATE
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
						System.err.println("����״̬�Զ����³���");
					}
				}
			}
		}
	}

	// get������
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

	// ����passages��Id����
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

	// ����һ����׼DateTime����
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