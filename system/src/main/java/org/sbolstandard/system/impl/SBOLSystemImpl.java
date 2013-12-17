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
		SBOLSystemVisitor systemVisitor=(SBOLSystemVisitor) visitor;
		
			try {
				systemVisitor.visit(this);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
}
