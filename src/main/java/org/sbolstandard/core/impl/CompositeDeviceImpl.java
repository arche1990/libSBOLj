package org.sbolstandard.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.sbolstandard.core.CompositeDevice;
import org.sbolstandard.core.Device;

public class CompositeDeviceImpl 
	extends DeviceImpl 
	implements CompositeDevice {

	protected final List<DeviceWrapper> devices = 
			new ArrayList<DeviceWrapper>();
		
	protected final WrappedList<DeviceImpl, DeviceWrapper> wrappedDevices = 
			new WrappedList<DeviceImpl, DeviceWrapper>(
	                DeviceWrapper.class, devices);

	/**
     * {@inheritDoc}
     */
	@Override
	public List<Device> getDevices() {
		return (List) wrappedDevices;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDevice(Device device) {
		this.getDevices().add(device);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDevice(Device device) {
		this.getDevices().remove(devices);
	}
	
	public static class DeviceWrapper extends WrappedValue<DeviceImpl> {
		@Override
		public DeviceImpl getValue() {
			return super.getValue();
		}

		@Override
		public void setValue(DeviceImpl value) {
			super.setValue(value);
		}
	}
}
