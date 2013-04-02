package org.sbolstandard.core;

import java.util.List;

public interface CompositeDevice 
	extends Device  {

	List<Device> getDevices();
	void addDevice(Device device);
	void removeDevice(Device device);

}
