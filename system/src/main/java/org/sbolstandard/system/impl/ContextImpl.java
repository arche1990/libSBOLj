package org.sbolstandard.system.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.impl.SBOLObjectImpl;
import org.sbolstandard.core.impl.WrappedList;
import org.sbolstandard.core.impl.DnaComponentImpl.URIWrapper;
import org.sbolstandard.system.Context;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Context", propOrder = {"displayId", "name", "description"})
public class ContextImpl extends SBOLObjectImpl implements Context {
	
	@XmlElement(name = "type", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	protected final List<URIWrapper> types = new ArrayList<URIWrapper>();
	@XmlTransient
	protected final WrappedList<URI, URIWrapper> wrappedTypes = new WrappedList<URI, URIWrapper>(URIWrapper.class,
	                types);
	
	@XmlElement(required = true)
	protected String displayId;
	protected String name;
	protected String description;
	
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
	public List<URI> getTypes() {
		return this.wrappedTypes;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void addType(URI type) {
		getTypes().add(type);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void removeType(URI type) {
		getTypes().remove(type);
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

}
