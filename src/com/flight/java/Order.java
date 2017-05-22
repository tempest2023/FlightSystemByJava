package com.flight.java;

public class Order {
	private int id = 0;
	private Passenger PassengerId = null;
	private int Seat = 0;
	private Flight FlightId = null;
	private DateTime CreateDate;
	private String Status = "";

	/**
	 * 
	 * @param pid
	 * @param fid
	 * @return
	 * 返回True表示没有重复的订单，可以下订单
	 * 返回False表示有重复的订单
	 */
	public static boolean IsHasOrder(int pid,int fid)
	{
		boolean re=false;
		Order x=new DbSelect().OrderSelect(pid, fid,"yes");
		if(x==null)
		{
			re=true;
		}
		return re;
	}
	public Order(int id, int PassengerId, int Seat, int FlightId,
			String CreateDate, String Status) {
		// TODO Auto-generated constructor stub
		DbSelect dbSelect = new DbSelect();

		this.id = id;
		this.PassengerId = dbSelect.PassengerSelect(PassengerId);
		this.Seat = Seat;
		this.FlightId = dbSelect.FlightSelect(FlightId);
		DateTime d = new DateTime(CreateDate);
		this.CreateDate = d;
		this.Status = Status;
	}

	public int getId() {
		return id;
	}

	public Passenger getPassengerId() {
		return PassengerId;
	}

	public int getSeat() {
		return Seat;
	}

	public Flight getFlightId() {
		return FlightId;
	}

	public DateTime getCreateDate() {
		return CreateDate;
	}

	public String getStatus() {
		return Status;
	}

}
