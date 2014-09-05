package com.ds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

public class JarTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void test() {
		Runtime rt = Runtime.getRuntime();
		String execCmd = "java -jar target/DBStressTester-1.0-SNAPSHOT.jar";
		try {
			Process pr = rt.exec(execCmd);
			InputStream in = pr.getInputStream();
			
			BufferedReader bR = new BufferedReader(new InputStreamReader(in));
			String s = bR.readLine();
			System.out.println(s);
			assertTrue(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
