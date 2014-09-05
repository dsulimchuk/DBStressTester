package com.ds.DBStressTester;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
@Sources({"file:TesterConfig.properties"})
public interface TesterConfig extends Config{
	
	String user();
	String password();
	String url();
	
	@DefaultValue("1521")
	int port();
	String SID();
	
	int threads();
	String stmtFile();
	int duration();
}
