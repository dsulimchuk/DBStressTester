package com.ds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oracle.jdbc.pool.OracleDataSource;

import org.aeonbits.owner.ConfigFactory;

import com.ds.DBStressTester.ResultTestObj;
import com.ds.DBStressTester.TesterConfig;
import com.ds.DBStressTester.Worker;

public class Main {
	static String connect_string;
	static TesterConfig cfg;
	static String callableStmt;
	static private List<ResultTestObj> resultList = new ArrayList<>();
	
	public static  void addResult(ResultTestObj obj) {
		synchronized (resultList) {
			resultList.add(obj);
		} 
		
			
	}
	static{
		cfg = ConfigFactory.create(TesterConfig.class);
		BufferedReader reader;
		System.out.println(cfg.stmtFile());
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(cfg.stmtFile()));
			reader.lines().forEachOrdered(s -> sb.append(s).append("\n"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		callableStmt = sb.toString();
		System.out.println(callableStmt);
		
		//static final String connect_string = "jdbc:oracle:thin:hr/hr@//localhost:1521/orcl.oracle.com";
		//static final String connect_string = "jdbc:oracle:thin:btk/btk@(description=(address_list=(address=(protocol=tcp)(host=b10.gs.local)(port=1521))(address=(protocol=tcp)(host=localhost)(port=1521)))(source_route=yes)(connect_data=(service_name=radartest)))";
		connect_string = 
				"jdbc:oracle:thin:"
				+cfg.user() 
				+ "/" 
				+ cfg.password() 
				+ "@"
				+cfg.url()
				+":"
				+cfg.port()
				+"/"
				+cfg.SID();
		System.out.println(connect_string);
		//System.exit(0);
		
	}
	
	public static void main(String[] args) {
		File f = new File(".");
		System.out.println(f.getAbsolutePath());
		try {
			OracleDataSource ods = new OracleDataSource();
			ods.setURL(connect_string);
			LinkedList<Worker> workersList = new LinkedList<>();
			for (int i = 0; i < cfg.threads(); i++) {
				workersList.add(new Worker(i,ods.getConnection(), callableStmt));
			}
			System.out.println("lets start");
			workersList.stream().forEach(t -> t.start());

			long timerStart = System.currentTimeMillis();
			boolean sendStopToAll = false;

			//main loop
			while (true) {
				long cntAlive = workersList.parallelStream().filter(t -> t.isAlive()).count();
				boolean isAlive = cntAlive > 0; //workersList.parallelStream().filter(t -> t.isAlive()).count();
				System.out.println(cntAlive);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!sendStopToAll  & ((System.currentTimeMillis() - timerStart) > (cfg.duration() * 1000))) {
					System.out.println("Timer - stop workers");
					workersList.parallelStream().forEach(t -> t.stopWorking());
					sendStopToAll = true;
				}
				if (!isAlive) {
					break;
				}
			}
			
			System.out.println(resultList.size());
			resultList.stream().sorted().forEach(System.out::println);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}