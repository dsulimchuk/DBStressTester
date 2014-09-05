package com.ds.DBStressTester;


public class ResultTestObj implements Comparable<ResultTestObj>{
	private int workerNumber;
	private long timeStart;
	private long timeStop;
	public ResultTestObj(int workerNumber, long timeStart, long timeStop) {
		this.workerNumber = workerNumber;
		this.timeStart = timeStart;
		this.timeStop = timeStop;
	}
	
	public long duration(){
		return timeStop - timeStart;
	}

	@Override
	public String toString() {
		return "ResultTestObj [workerNumber=" + workerNumber + ", duration=" + duration() +"ms., tStart="
				+ timeStart + ", tStop=" + timeStop + "]";
	}

	@Override
	public int compareTo(ResultTestObj o) {
		if (timeStart == o.timeStart) {
			return Long.compare(timeStop, o.timeStop);
		} else {
			return Long.compare(timeStart, o.timeStart);
		}
		//return Integer.compare(workerNumber, o.workerNumber);
	}
	
	
	
}
