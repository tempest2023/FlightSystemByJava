package com.flight.java;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Passenger {
	private int id = 0;
	private String RealName = "";
	private String IdentityId = "";
	private String Password = "";
	private int[] OrderList;

	public Passenger(int id, String RealName, String IdentityId,

	String Password, String OrderList) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.RealName = RealName;
		this.IdentityId = IdentityId;
		this.Password = Password;
		this.OrderList = this.GetOrderList(OrderList);
	}

	/**
	 * @name 查询乘客订单
	 * @return 返回乘客的所有订单列表
	 */
	public Order[] SelectOrders(String pwd) {
		if (Passenger.CheckPwd(this.getRealName(), pwd)) {
			return new DbSelect().PassengerOrders(this.getId());
		} else {
			System.err.println("乘客密码错误");
			return null;
		}
	}

	/**
	 * @name 乘客登录验证函数
	 * @param RealName
	 * @param pwd
	 * @return true或false
	 */
	public static boolean CheckPwd(String RealName, String pwd) {
		DbSelect _s = new DbSelect();
		Passenger _a = _s.PassengerSelect(RealName, pwd);
		if (_a != null) {

			return true;

		}
		return false;

	}

	/**
	 * @name 乘客订票函数
	 * @param pid
	 *            乘客id
	 * @param fid
	 *            航班id
	 * @param pwd
	 *            乘客密码
	 * @return true或false PS:若想获取乘客的刚刚生成的订单，可以使用另一个重载函数
	 */
	public static boolean ReserveFlight(int pid, int fid, String pwd) {

		DbSelect select = new DbSelect();
		Flight f = select.FlightSelect(fid);
		if (f.getFlightStatus().equals("AVAILABLE")) {
			Passenger p = select.PassengerSelect(pid);
			if (Passenger.CheckPwd(p.getRealName(), pwd)) {
				// 一个乘客针对一个航班只能买一张票
				if (select.OrderSelect(pid, fid,"") == null) {
					DbInsert insert = new DbInsert();
					// 设置日期格式
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd-HH-mm-ss");
					String CreateDate = df.format(new Date());

					boolean re = insert.OrderInsert(p.getId(), p.getId(),
							f.getId(), CreateDate, "PAID");
					// 更新Flight的Passenger列表和CurrentPassengers数量和Passenger的OrderList
					re = re && Flight.ReserveFlight(pid, fid)
							&& p.UpdateOrderList(fid);
					if (re) {
						return true;
					}
				} else {
					System.err.println("订单已存在，无法重复订票");
				}
			} else {
				System.err.println("乘客密码错误");
				return false;
			}
		} else {
			System.err.println("航班状态异常，不可预订");
			return false;
		}
		return false;
	}

	/**
	 * @name 乘客订票函数
	 * @param pid
	 *            乘客id
	 * @param fid
	 *            航班id
	 * @param pwd
	 *            乘客密码
	 * @param mode
	 *            只是为了区别上面的函数，返回订单对象,随便赋值吧
	 * @return 返回刚刚创建的订单对象或null PS:一个乘客针对一个航班只能买一张票
	 */
	public static Order ReserveFlight(int pid, int fid, String pwd, int mode) {

		DbSelect select = new DbSelect();
		Flight f = select.FlightSelect(fid);

		if (f.getFlightStatus().equals("AVAILABLE")) {
			Passenger p = select.PassengerSelect(pid);

			if (Passenger.CheckPwd(p.getRealName(), pwd)) {
				if (select.OrderSelect(pid, fid) == null) {

					DbInsert insert = new DbInsert();
					// 设置日期格式
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd-HH-mm-ss");
					String CreateDate = df.format(new Date());

					boolean re = insert.OrderInsert(p.getId(), p.getId(),
							f.getId(), CreateDate, "PAID");
					// 更新Flight的Passenger列表和CurrentPassengers数量和Passenger的OrderList
					re = re && Flight.ReserveFlight(pid, fid)
							&& p.UpdateOrderList(fid);
					if (re) {
						return select.OrderSelect(pid, fid);
					}
				} else {
					System.err.println("订单已存在，无法重复订票");
				}
			} else {
				System.err.println("乘客密码错误");
				return null;
			}
		} else {
			System.err.println("航班状态异常，不可预订");
			return null;
		}
		return null;
	}

	/**
	 * @name 更新乘客的航班列表
	 * @param fid
	 *            航班Id
	 * @return true或false PS：明明是OrderList，里面存放的却是FlightId
	 *         想要得到Order的话，还要用PassengerId和FlightId去高级查询Order 真是多此一举=_=
	 */
	public boolean UpdateOrderList(int fid) {
		int[] _o = this.getOrderList();
		String OrderList = "";
		if (_o != null) {
			for (int i = 0; i < _o.length; i++) {
				OrderList += _o[i] + ";";
			}
		}
		OrderList += fid + ";";
		return new DbUpdate().UpdateOrderList(this.getId(), OrderList);
	}

	public boolean UpdateOrderList2(int fid) {
		int[] _o = this.getOrderList();
		String OrderList = "";
		if (_o != null) {
			for (int i = 0; i < _o.length; i++) {
				if (_o[i] != fid) {
					OrderList += _o[i] + ";";
				}
			}
		}
		return new DbUpdate().UpdateOrderList(this.getId(), OrderList);
	}

	/**
	 * @name 退订机票函数
	 * @param pid
	 *            乘客id
	 * @param fid
	 *            航班id
	 * @param pwd
	 *            乘客密码
	 * @return true或false 不同情况会在err流中写出
	 */
	public static boolean UnsubscribeFlight(int pid, int fid, String pwd) {
		DbSelect select = new DbSelect();
		Flight f = select.FlightSelect(fid);
		// 若航班已锁定，则不能退订
		if (!f.getFlightStatus().equals("TERMINATE")) {
			Passenger p = select.PassengerSelect(pid);
			if (Passenger.CheckPwd(p.getRealName(), pwd)) {
				// 检查是否存在订单
				if (select.OrderSelect(pid, fid) != null) {
					// 更新Flight的Passenger列表和CurrentPassengers数量和Passenger的OrderList
					boolean re = f.UnreserveFlight(pid, fid)
							&& p.UpdateOrderList2(fid);
					if (re) {
						return true;
					}
				} else {
					System.err.println("订单不存在，无法退订");
				}
			} else {
				System.err.println("乘客密码错误");
				return false;
			}
		} else {
			System.err.println("航班已锁定，不能退订");
			return false;
		}
		return false;

	}

	// get方法們
	public int getId() {
		return id;
	}

	public String getRealName() {
		return RealName;
	}

	public String getIdentityId() {
		return IdentityId;
	}

	public String getPassword() {
		return Password;
	}

	public int[] getOrderList() {
		return OrderList;
	}

	// 根據Id查詢整體信息并獲得Passenger的對象
	public Passenger GetPassengerById(int id) {
		DbSelect select = new DbSelect();
		return select.PassengerSelect(id);
	}

	public String GetOrderString(int[] oi) {
		String s = "";
		for (int i = 0; i < oi.length; i++) {
			s += Integer.toString(oi[i]) + ";";
		}
		return s;

	}

	// 把數據庫裡的OrderList String變成OrderId的數組
	public int[] GetOrderList(String _o) {
		if (_o.length() > 0) {
			String[] _s;
			_s = _o.split(";");
			int[] _t = new int[_s.length];
			for (int i = 0; i < _s.length; i++) {
				_t[i] = Integer.parseInt(_s[i]);
			}

			return _t;
		}
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(Passenger.UnsubscribeFlight(3, 2, "balabala"));

	}
}
