package org.sbolstandard.system.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.impl.SBOLObjectImpl;
import org.sbolstandard.core.impl.WrappedList;
import org.sbolstandard.core.impl.WrappedValue;
import org.sbolstandard.core.impl.XmlAdapters.URIAdapter;
import org.sbolstandard.system.Context;
import org.sbolstandard.system.Device;
import org.sbolstandard.system.Model;
import org.sbolstandard.system.SBOLSystem;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SBOLSystem", propOrder = {"displayId", "name", "description"})
public class SBOLSystemImpl extends SBOLObjectImpl implements SBOLSystem {
	@XmlElement(name = "type", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	protected final List<URIWrapper> types = new ArrayList<URIWrapper>();
	@XmlTransient
	protected final WrappedList<URI, URIWrapper> wrappedTypes = new WrappedList<URI, URIWrapper>(URIWrapper.class,
	                types);
	@XmlElement(required = true)
	protected String displayId;
	protected String name;
	protected String description;
	protected Model model;
	protected List<Device> devices = new ArrayList<Device>();
	protected List<Context> contexts = new ArrayList<Context>();
	
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
		

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Throwable> void accept(SBOLVisitor<T> visitor) {
		//GMGM Confifm this with Matt : 
		if (visitor instanceof SBOLSystemVisitor) {
			try {
				((SBOLSystemVisitor<T>) visitor).visit(this);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}




	@XmlAccessorType(XmlAccessType.PROPERTY)
	@XmlType(name = "")
	public static class URIWrapper extends WrappedValue<URI> {
		@XmlAttribute(name = "resource", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", required = true)
		@XmlJavaTypeAdapter(URIAdapter.class)
		@Override
		public URI getValue() {
			return super.getValue();
		}

		@Override
		public void setValue(URI value) {
			super.setValue(value);
		}
	}




	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public List<Device> getDevices() {
		return devices;
	}

	@Override
	public void addDevice(Device device) {
		devices.add(device);
	}

	@Override
	public void removeDevice(Device device) {
		devices.remove(device);
	}

	@Override
	public List<Context> getContexts() {
		return contexts;
	}

	@Override
	public void addContext(Context context) {
		contexts.add(context);
	}

	@Override
	public void removeContext(Context context) {
		contexts.remove(context);
	}
}
