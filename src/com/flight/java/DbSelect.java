package com.flight.java;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DbSelect {
	private DbConnect db = null;
	private Connection cn = null;
	private ResultSet ret = null;
	private PreparedStatement pst = null;

	/*
	 * Admin登录检测 返回Admin 或 Null
	 */
	public Admin AdminSelect(String username) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("select * from admin where binary Username='"
							+ username + "';");
			this.ret = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				ret.beforeFirst();

				while (ret.next()) {
					Admin ad = new Admin(ret.getInt(1), ret.getString(2),
							ret.getString(3));
					this.ret.close();
					this.cn.close();
					return ad;
				}

			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Passenger的登录检测 返回Passenger或null
	 */
	public Passenger PassengerSelect(String RealName, String pwd) {
		pwd = Encode.MD5(pwd);
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("select * from passenger where RealName='"
							+ RealName + "' and Password='" + pwd + "';");
			this.ret = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				ret.beforeFirst();

				while (ret.next()) {
					Passenger ad = new Passenger(ret.getInt(1),
							ret.getString(2), ret.getString(3),
							ret.getString(4), ret.getString(5));
					this.ret.close();
					this.cn.close();
					return ad;
				}

			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * 高级关联查询
	 */
	// 1.根据乘客id和航班id，查询订单信息
	public Order OrderSelect(int pid, int fid) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			String _sql = "select * from `order` where PassengerId=" + pid
					+ " and FlightId=" + fid + ";";
			this.pst = cn.prepareStatement(_sql);
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				ret.beforeFirst();
				int _id = 1;
				int _i1 = 1, _i2 = 1, _i3 = 1;
				String _s1 = "", _s2 = "";
				while (ret.next()) {
					_id = ret.getInt(1);
					_i1 = ret.getInt(2);
					_i2 = ret.getInt(3);
					_i3 = ret.getInt(4);
					_s1 = ret.getString(5);
					_s2 = ret.getString(6);
				}
				this.ret.close();
				this.cn.close();
				Order p = new Order(_id, _i1, _i2, _i3, _s1, _s2);
				db.close();
				return p;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 2.根据航班id查询预定信息，返回BookingInfo数组
	public BookingInfo[] SelectFlightInfo(int Fid) {

		Flight f = this.FlightSelect(Fid);
		int[] pids = f.getPassengerId();
		if (pids != null) {
			BookingInfo[] re = new BookingInfo[pids.length];
			for (int i = 0; i < pids.length; i++) {
				Passenger p = this.PassengerSelect(pids[i]);
				Order o = this.OrderSelect(p.getId(), f.getId());
				BookingInfo b = new BookingInfo(f.getId(), p.getId(),
						p.getRealName(), p.getIdentityId(), o.getId(),
						o.getSeat(), o.getCreateDate(), o.getStatus());
				re[i] = b;
			}
			return re;
		}
		return null;
	}

	// 3.可以通过起飞城市、到达城市、起飞日期、到达日期查询航班
	public Flight[] FlightSelect(String StartTime, String ArrivalTime,
			String StartCity, String ArrivalCity) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			String _sql = "select * from flight where";
			if (StartTime != null && StartTime.length() > 0) {
				_sql += " StartTime like '" + StartTime + "%'";
				_sql += " and ";
			}
			if (StartCity != null && StartCity.length() > 0) {
				_sql += " StartCity = '" + StartCity + "'";
				_sql += " and ";
			}
			if (ArrivalCity != null && ArrivalCity.length() > 0) {
				_sql += " ArrivalCity = '" + ArrivalCity + "'";
				_sql += " and ";
			}
			if (ArrivalTime != null && ArrivalTime.length() > 0) {
				_sql += " ArrivalTime like '" + ArrivalTime + "%'";
				_sql += " and ";
			}
			_sql = _sql.substring(0, _sql.length() - 5);
			_sql += ";";
			if (StartTime.length() <= 0 && StartCity.length() <= 0
					&& ArrivalCity.length() <= 0 && ArrivalTime.length() <= 0) {
				_sql = "select * from flight;";
			}

			this.pst = cn.prepareStatement(_sql);
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				Flight[] ad = new Flight[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Flight x = new Flight(ret.getInt(1), ret.getString(2),
							ret.getString(3), ret.getString(4),
							ret.getString(5), ret.getString(6),
							ret.getFloat(7), ret.getInt(8), ret.getInt(9),
							ret.getString(10), ret.getString(11),
							ret.getString(12));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 4.可以通過航班号查询航班，可模糊查找
	public Flight[] FlightSelect(String FlightName) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			String _sql = "select * from flight where FlightName like '%"
					+ FlightName + "%';";
			this.pst = cn.prepareStatement(_sql);
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				Flight[] ad = new Flight[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Flight x = new Flight(ret.getInt(1), ret.getString(2),
							ret.getString(3), ret.getString(4),
							ret.getString(5), ret.getString(6),
							ret.getFloat(7), ret.getInt(8), ret.getInt(9),
							ret.getString(10), ret.getString(11),
							ret.getString(12));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 5.可以根据乘客id查询乘客所有的订单
	public Order[] PassengerOrders(int pid) {

		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("select * from `order` where PassengerId="
							+ pid + ";");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				Order[] ad = new Order[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Order x = new Order(ret.getInt(1), ret.getInt(2),
							ret.getInt(3), ret.getInt(4), ret.getString(5),
							ret.getString(6));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 6、查询是否有有效订单
	public Order OrderSelect(int pid, int fid, String mode) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			String _sql = "select * from `order` where PassengerId=" + pid
					+ " and FlightId=" + fid + " and Status='PAID';";
			this.pst = cn.prepareStatement(_sql);
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				ret.beforeFirst();
				int _id = 1;
				int _i1 = 1, _i2 = 1, _i3 = 1;
				String _s1 = "", _s2 = "";
				while (ret.next()) {
					_id = ret.getInt(1);
					_i1 = ret.getInt(2);
					_i2 = ret.getInt(3);
					_i3 = ret.getInt(4);
					_s1 = ret.getString(5);
					_s2 = ret.getString(6);
				}
				this.ret.close();
				this.cn.close();
				Order p = new Order(_id, _i1, _i2, _i3, _s1, _s2);
				db.close();
				return p;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Select All 方法们 返回对象数组
	 */
	public Admin[] AdminSelect() {

		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from admin;");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				Admin[] ad = new Admin[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Admin x = new Admin(ret.getInt(1), ret.getString(2),
							ret.getString(3));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Order[] OrderSelect() {

		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from `order`;");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				Order[] ad = new Order[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Order x = new Order(ret.getInt(1), ret.getInt(2),
							ret.getInt(3), ret.getInt(4), ret.getString(5),
							ret.getString(6));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public BookingInfo[] BookingInfoSelect() {

		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from `order`;");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				BookingInfo[] ad = new BookingInfo[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Order x = new Order(ret.getInt(1), ret.getInt(2),
							ret.getInt(3), ret.getInt(4), ret.getString(5),
							ret.getString(6));
					BookingInfo b = new BookingInfo(x.getFlightId(),
							x.getPassengerId(), x.getPassengerId()
									.getRealName(), x.getPassengerId()
									.getIdentityId(), x.getId(), x.getSeat(),
							x.getCreateDate(), x.getStatus());
					ad[_i] = b;
					_i++;
				}
				this.ret.close();
				this.cn.close();

				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BookingInfo[] BookingInfoSelect(int fid) {

		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("select * from `order` where FlightId="
							+ fid + ";");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				BookingInfo[] ad = new BookingInfo[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Order x = new Order(ret.getInt(1), ret.getInt(2),
							ret.getInt(3), ret.getInt(4), ret.getString(5),
							ret.getString(6));
					BookingInfo b = new BookingInfo(x.getFlightId(),
							x.getPassengerId(), x.getPassengerId()
									.getRealName(), x.getPassengerId()
									.getIdentityId(), x.getId(), x.getSeat(),
							x.getCreateDate(), x.getStatus());
					ad[_i] = b;
					_i++;
				}
				this.ret.close();
				this.cn.close();

				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Passenger[] PassengerSelect() {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from passenger;");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				Passenger[] ad = new Passenger[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Passenger x = new Passenger(ret.getInt(1),
							ret.getString(2), ret.getString(3),
							ret.getString(4), ret.getString(5));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Flight[] FlightSelect() {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from flight;");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				Flight[] ad = new Flight[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Flight x = new Flight(ret.getInt(1), ret.getString(2),
							ret.getString(3), ret.getString(4),
							ret.getString(5), ret.getString(6),
							ret.getFloat(7), ret.getInt(8), ret.getInt(9),
							ret.getString(10), ret.getString(11),
							ret.getString(12));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Flight[] FlightSelectForPass() {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("select * from flight where 	FlightStatus='AVAILABLE' or FlightStatus='FULL';");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ret.last();
			if (ret.getRow() > 0) {
				Flight[] ad = new Flight[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Flight x = new Flight(ret.getInt(1), ret.getString(2),
							ret.getString(3), ret.getString(4),
							ret.getString(5), ret.getString(6),
							ret.getFloat(7), ret.getInt(8), ret.getInt(9),
							ret.getString(10), ret.getString(11),
							ret.getString(12));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Select by Id 返回对象
	 */
	public Flight FlightSelect(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from flight where Id="
					+ id + ";");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		int _id = 1;
		String _s1 = "", _s2 = "", _s3 = "", _s4 = "", _s5 = "";
		float _p = 1f;
		int _i1 = 1, _i2 = 1;
		String _s6 = "", _s7 = "", _s8 = "";
		try {
			while (ret.next()) {
				_id = ret.getInt(1);
				_s1 = ret.getString(2);
				_s2 = ret.getString(3);
				_s3 = ret.getString(4);
				_s4 = ret.getString(5);
				_s5 = ret.getString(6);
				_p = ret.getFloat(7);
				_i1 = ret.getInt(8);
				_i2 = ret.getInt(9);
				_s6 = ret.getString(10);
				_s7 = ret.getString(11);
				_s8 = ret.getString(12);
			}
			this.ret.close();
			this.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(_id+" "+_s1+" "+_s2+" "+_s3+" "+_s4+" "+_s5+" "+_p+" "+_i1+" "+_i2+" "+_s6+" "+_s7);
		Flight f = new Flight(_id, _s1, _s2, _s3, _s4, _s5, _p, _i1, _i2, _s6,
				_s7, _s8);

		return f;
	}

	public Passenger PassengerSelect(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from passenger where Id="
					+ id + ";");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		int _id = 1;
		String _s1 = "", _s2 = "", _s3 = "", _s4 = "";
		try {
			while (ret.next()) {
				_id = ret.getInt(1);
				_s1 = ret.getString(2);
				_s2 = ret.getString(3);
				_s3 = ret.getString(4);
				_s4 = ret.getString(5);
			}
			this.ret.close();
			this.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Passenger p = new Passenger(_id, _s1, _s2, _s3, _s4);
		db.close();
		return p;
	}

	public Order OrderSelect(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from `order` where Id="
					+ id + ";");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		int _id = 1;
		int _i1 = 1, _i2 = 1, _i3 = 1;
		String _s1 = "", _s2 = "";
		try {
			while (ret.next()) {
				_id = ret.getInt(1);
				_i1 = ret.getInt(2);
				_i2 = ret.getInt(3);
				_i3 = ret.getInt(4);
				_s1 = ret.getString(5);
				_s2 = ret.getString(6);
			}
			this.ret.close();
			this.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Order p = new Order(_id, _i1, _i2, _i3, _s1, _s2);
		db.close();
		return p;

	}

	/**
	 * @PS 根据乘客Id返回其所有的Order
	 * @param pid
	 * @param s
	 * @return Order[]
	 */
	public Order[] OrderSelect(int pid, String s) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn
					.prepareStatement("select * from `order` where PassengerId="
							+ pid + " and Status='PAID';");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret.last();
			if (ret.getRow() > 0) {
				Order[] ad = new Order[ret.getRow()];
				ret.beforeFirst();
				/*
				 * 注意：getRow()方法并不是获得行数，而是获得ret当前指向位 所以需要将ret光标先跳到最后，获取row，在跳回来
				 */
				int _i = 0;
				while (ret.next()) {
					Order x = new Order(ret.getInt(1), ret.getInt(2),
							ret.getInt(3), ret.getInt(4), ret.getString(5),
							ret.getString(6));
					ad[_i] = x;
					_i++;
				}
				this.ret.close();
				this.cn.close();
				return ad;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Admin AdminSelect(int id) {
		this.db = new DbConnect();
		this.cn = this.db.Get_Connection();
		try {
			this.pst = cn.prepareStatement("select * from admin where Id=" + id
					+ ";");
			this.ret = pst.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		int _id = 1;
		String _s1 = "", _s2 = "";
		try {
			while (ret.next()) {
				_id = ret.getInt(1);
				_s1 = ret.getString(2);
				_s2 = ret.getString(3);
			}
			this.ret.close();
			this.cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Admin p = new Admin(_id, _s1, _s2);
		db.close();
		return p;

	}

	public static void main(String[] args) {
		// System.out.println(new DbSelect().OrderSelect(3,1));

		// System.out.println(new DbSelect().PassengerSelect("任涛", "balabala"));
		/*
		 * 高级查询Example
		 * 
		 * DbSelect sa = new DbSelect(); TestObject.print(sa.OrderSelect(1, 1));
		 * TestObject.print(sa.OrderSelect(2, 1)); Flight[]
		 * x=sa.FlightSelect("", "", "西安", "北京"); for (int i = 0; i < x.length;
		 * i++) { TestObject.print(x[i]); }
		 */
		/*
		 * Select All Example
		 * 
		 * DbSelect sa = new DbSelect(); Passenger[] p=sa.PassengerSelect();
		 * Admin[] a=sa.AdminSelect(); Flight[] f=sa.FlightSelect(); for(int
		 * i=0;i<f.length;i++) { TestObject.print(f[i]); }
		 * System.out.println(sa.ret+"\n"+sa.cn);
		 */
		/*
		 * 乘客对象Debug Passenger p=sa.PassengerSelect(1); TestObject.print(p);
		 */

		/*
		 * Admin对象Debug Admin a=sa.AdminSelect(1); TestObject.print(a);
		 */

		/*
		 * 航班对象Debug
		 * 
		 * Flight f=sa.FlightSelect(1); TestObject.print(f);
		 */

	}
}
