package com.frame.java;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.flight.java.*;

/**
 * 这里是程序主入口，开启Login登录窗口 非常重要的事情是程序开始运行时自动加载守护线程来更新航班的FlighStatus
 * 
 * @author RT
 * 
 */
public class TimeThread implements Runnable {
	/*
	 * 控制线程的flag PS:不使用Thread.stop方法终止线程是因为stop会强制终止线程 会导致没有做完的操作终止
	 */
	public volatile boolean ThreadFlag = true;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		while (ThreadFlag) {
			// System.out.println("我在運行ing"+df.format(new Date()));
			DateTime NowDate = new DateTime(df.format(new Date()));
			Flight.AutoUpdateStatus(NowDate);
			// 1s*60*5=5min
			int time = 1000 * 60 * 1;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*
	 * public static void main(String[] args) {
	 * 
	 * 
	 * long ing=0; while(true) { ing++; //System.out.println("我是主线程"); try {
	 * Thread.sleep(100); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace();
	 * 
	 * } if((ing % 1000)==0) { //PS:因为有Sleep，所以会运行几分钟
	 * updateBegin.ThreadFlag=false; System.out.println("线程已经终止"); break; } } }
	 */

}
