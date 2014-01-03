package org.sbolstandard.system.impl;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.impl.SBOLObjectImpl;
import org.sbolstandard.system.Model;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Model", propOrder = {"displayId", "name", "description"})
public class ModelImpl extends SBOLObjectImpl implements Model {
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

	private URI source;
	/**
     * {@inheritDoc}
     */
	@Override
	public URI getSource() {
		return source;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void setSource(URI source) {
		this.source = source;
	}
	
	private URI language;
	public URI getLanguage()
	{
		return language;
	}
	public void setLanguage(URI language)
	{
		this.language=language;
	}

	private URI framework;
	public URI getFramework()
	{
		return framework;
	}
	public void setFramework(URI framework)
	{
		this.framework=framework;
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
