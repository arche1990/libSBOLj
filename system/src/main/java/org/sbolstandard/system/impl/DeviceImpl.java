package org.sbolstandard.system.impl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.impl.SBOLObjectImpl;
import org.sbolstandard.system.Device;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Model", propOrder = {"displayId", "name", "description"})
public class DeviceImpl extends SBOLObjectImpl implements Device {
	@XmlElement(required = true)
	protected String displayId;
	protected String name;
	protected String description;
	protected List<DnaComponent> dnaComponents = new ArrayList<DnaComponent>();
	
	/**
     * {@inheritDoc}
     */
	@Override
	public String getDisplayId() {
		return displayId;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void setDisplayId(String value) {
		this.displayId = value;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getName() {
		return name;
		
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void setName(String value) {
		this.name = value;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getDescription() {
		return description;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void setDescription(String value) {
		this.description = value;
	}

	@Override
	public <T extends Throwable> void accept(SBOLVisitor<T> visitor) throws T {
		if (visitor instanceof SBOLSystemVisitor) {
			try {
				((SBOLSystemVisitor<T>) visitor).visit(this);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DnaComponent> getDnaComponents() {
		return dnaComponents;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDnaComponent(DnaComponent component) {
		getDnaComponents().add(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDnaComponent(DnaComponent component) {
		getDnaComponents().remove(component);
	}

}
