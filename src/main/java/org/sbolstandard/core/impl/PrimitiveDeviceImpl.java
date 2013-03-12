package org.sbolstandard.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.PrimitiveDevice;

/**
 * @author Ernst Oberortner
 */
public class PrimitiveDeviceImpl 
		extends DeviceImpl 
		implements PrimitiveDevice {

	protected final List<DnaComponentWrapper> components = 
		new ArrayList<DnaComponentWrapper>();
	
	protected final WrappedList<DnaComponentImpl, DnaComponentWrapper> wrappedComponents = 
			new WrappedList<DnaComponentImpl, DnaComponentWrapper>(
	                DnaComponentWrapper.class, components);

	/**
     * {@inheritDoc}
     */
	public List<DnaComponent> getComponents() {
		return (List) wrappedComponents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComponent(DnaComponent component) {
		this.getComponents().add(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeComponent(DnaComponent component) {
		this.getComponents().remove(component);
	}
	
	public static class DnaComponentWrapper extends WrappedValue<DnaComponentImpl> {
		@Override
		public DnaComponentImpl getValue() {
			return super.getValue();
		}

		@Override
		public void setValue(DnaComponentImpl value) {
			super.setValue(value);
		}
	}
}
