package com.ds.DBStressTester;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.ds.Main;

public class Worker extends Thread {
    private String query;
    private Connection conn;
    private int workerNumber;
    private CallableStatement call;
    private boolean stop = false;

    public Worker(int workerNumber, Connection conn, String query) {
	super();
	this.query = query;
	this.conn = conn;
	this.workerNumber = workerNumber;
	try {
	    call = conn.prepareCall(query);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public void stopWorking() {
	stop = true;
    }

    @Override
    public void run() {
	while (!stop) {
	    try {
		long timeStart = System.currentTimeMillis();
		call.execute();
		long timeStop = System.currentTimeMillis();
		Main.addResult(new ResultTestObj(workerNumber, timeStart, timeStop));

	    } catch (SQLException e) {
		e.printStackTrace();
	    }

	}
	try {
	    call.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
