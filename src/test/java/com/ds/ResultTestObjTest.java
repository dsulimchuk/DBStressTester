package com.ds;


import com.ds.DBStressTester.ResultTestObj;

import junit.framework.TestCase;



public class ResultTestObjTest extends TestCase {
	ResultTestObj obj;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testResultTestObj() {
		obj = new ResultTestObj(1, 1, 100);
		assertNotNull("constructor return null", obj);
	}

	public void testDuration() {
		obj = new ResultTestObj(1, 1, 100);
		assertEquals(obj.duration(), 99);
	}

	public void testToString() {
		obj = new ResultTestObj(1, 1, 100);
		assertTrue(obj.toString().length() > 0);
	}

	public void testCompareToWithGreater() {
		obj = new ResultTestObj(1, 1, 100);
		ResultTestObj objGreater = new ResultTestObj(1, 1, 101);
		assertEquals(obj.compareTo(objGreater), -1);
	}
	public void testCompareToWithSmaller() {
		obj = new ResultTestObj(1, 1, 100);
		ResultTestObj objGreater = new ResultTestObj(1, 0, 101);
		assertEquals(obj.compareTo(objGreater), 1);
	}
	public void testCompareToWithEqual() {
		obj = new ResultTestObj(1, 1, 100);
		ResultTestObj objGreater = new ResultTestObj(1, 1, 100);
		assertEquals(obj.compareTo(objGreater), 0);
	}

}
