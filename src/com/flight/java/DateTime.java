package com.flight.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTime {
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int hour = 0;
	private int minute = 0;
	private int second = 0;

	public DateTime(String time) {
		// TODO Auto-generated constructor stub
		this.GetTime(this, time);
	}

	/*
	 * 由字符串获得一个DateTime对象
	 */
	public static DateTime GetDateTimeOb(String time) {
		DateTime x = new DateTime(time);
		return x;
	}

	/*
	 * 比较两个时间对象的时间差是否符合Flight设置要求 StartTime 和 ArrivalTime为t1,t2 StartTime在前
	 */
	public static boolean GetSub(DateTime t1, DateTime t2) {
		int y, m, d, h, min, s, y1, m1, d1, h1, min1, s1;
		if (DateTime.CompareDate(t1, t2)) {
			// t1更大
			y = t1.getYear();
			m = t1.getMonth();
			d = t1.getDay();
			h = t1.getHour();
			min = t1.getMinute();
			s = t1.getSecond();
			y1 = t2.getYear();
			m1 = t2.getMonth();
			d1 = t2.getDay();
			h1 = t2.getHour();
			min1 = t2.getMinute();
			s1 = t2.getSecond();
		} else {
			// t2更大
			y = t2.getYear();
			m = t2.getMonth();
			d = t2.getDay();
			h = t2.getHour();
			min = t2.getMinute();
			s = t2.getSecond();
			y1 = t1.getYear();
			m1 = t1.getMonth();
			d1 = t1.getDay();
			h1 = t1.getHour();
			min1 = t1.getMinute();
			s1 = t1.getSecond();
		}

		boolean re = false;

		if (y == y1) {
			if (m == m1) {
				if (d == d1) {
					if (s < s1) {
						min--;
						s = s + 60 - s1;
					} else {
						s = s - s1;
					}
					if (min < min1) {
						h--;
						min = min + 60 - min1;
					} else {
						min = min - min1;
					}
					h = h - h1;
					if (h > 2) {
						// 两航班相差2小时以上
						return true;
					}
					if (h == 2) {
						if (min > 0 || s > 0) {
							return true;
						}
					}
				} else {
					// 跨日航班
					d--;
					if (s < s1) {
						min--;
						s = s + 60 - s1;
					} else {
						s = s - s1;
					}
					if (min < min1) {
						h--;
						min = min + 60 - min1;
					} else {
						min = min - min1;
					}
					h = h + 24 - h1;
					if (d > 0) {
						return true;
					}
					if (h > 2) {
						// 两航班相差2小时以上
						return true;
					}
					return false;
				}
			} else {
				// 跨月航班
				m--;
				m = m - m1;
				d = d + DateTime.MonthDay(m) - d1;
				if (d < 0) {
					System.err.println("跨月航班计算错误");
					return false;
				}
				if (s < s1) {
					min--;
					s = s + 60 - s1;
				} else {
					s = s - s1;
				}
				if (min < min1) {
					h--;
					min = min + 60 - min1;
				} else {
					min = min - min1;
				}
				if (h < h1) {
					h = h + 24 - h1;
					d--;
				} else {
					h = h - h1;
				}
				if (d > 0 || m > 0) {
					return true;
				}
				if (h > 2) {
					// 两航班相差2小时以上
					return true;
				}
				return false;
			}
		} else {
			// 跨年航班
			y--;
			y = y - y1;
			m = m + 12 - m1;
			if (d < d1) {
				m--;
				d = d + DateTime.MonthDay(m) - d1;
			} else {
				d = d - d1;
			}
			if (s < s1) {
				min--;
				s = s + 60 - s1;
			} else {
				s = s - s1;
			}
			if (min < min1) {
				h--;
				min = min + 60 - min1;
			} else {
				min = min - min1;
			}
			if (h < h1) {
				h = h + 24 - h1;
				d--;
			} else {
				h = h - h1;
			}
			if (h > 2 || d > 0 || m > 0 || y > 0) {
				return true;
			}
		}
		return re;
	}

	/*
	 * 比较现在时间和t是否相差2小时 若t-NowDate<=2 hour,则返回true 表示应该更改航班状态 否则返回false
	 * 表示现在时间与起飞时间相差大于2小时，不需要更改
	 */
	public boolean UpdateStatus(DateTime t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		try {
			long x1 = sdf.parse(GetDateTimeStr(t)).getTime()
					- sdf.parse(GetDateTimeStr(this)).getTime();
			if ((x1 / 1000 / 60) <= 120) {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * 返回本月天数 参数:月份
	 */
	private static int MonthDay(int i) {
		switch (i) {
		// 有个bug在2月29日晚的航班发生。
		case 0:
			return 31;
		case 1:
			return 31;
		case 2:
			return 28;
		case 3:
			return 31;
		case 5:
			return 31;
		case 7:
			return 31;
		case 8:
			return 31;
		case 10:
			return 31;
		case 12:
			return 31;
		default:
			return 30;
		}

	}

	/*
	 * 由DateTime对象获得字符串
	 */
	public static String GetDateTimeStr(DateTime t) {
		String s = "";
		s += t.CheckLen(t.year) + "-" + t.CheckLen(t.month) + "-"
				+ t.CheckLen(t.day) + "-" + t.CheckLen(t.hour) + "-"
				+ t.CheckLen(t.minute) + "-" + t.CheckLen(t.second);
		return s;

	}

	private void GetTime(DateTime t, String time) {
		if (time.equals("") == false) {

			String[] _t;
			_t = time.split("-");
			// 此处的CheckLen并没有用，因为int类型会把0消除
			// 写完才发现没有用，尴尬
			t.year = Integer.parseInt(CheckLen(_t[0]));
			t.month = Integer.parseInt(CheckLen(_t[1]));
			t.day = Integer.parseInt(CheckLen(_t[2]));
			t.hour = Integer.parseInt(CheckLen(_t[3]));
			t.minute = Integer.parseInt(CheckLen(_t[4]));
			t.second = Integer.parseInt(CheckLen(_t[5]));
		}
	}

	/**
	 * @name 比较日期
	 * @param t1
	 *            第一个时间
	 * @param t2
	 *            第二个时间
	 * @return t1>t2返回true 否则 false PS:t1>t2则t1的时间更为靠后
	 */
	public static boolean CompareDate(DateTime t1, DateTime t2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		try {
			long x1 = sdf.parse(DateTime.GetDateTimeStr(t1)).getTime()
					- sdf.parse(DateTime.GetDateTimeStr(t2)).getTime();
			if (x1 > 0) {
				// t1>t2,t1的时间更为靠后
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private String CheckLen(String s) {
		if (s.length() < 2) {
			s = "0" + s;
		}
		return s;
	}

	private String CheckLen(int s) {
		String ss = Integer.toString(s);
		if (ss.length() < 2) {
			ss = "0" + ss;
		}
		return ss;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

}
