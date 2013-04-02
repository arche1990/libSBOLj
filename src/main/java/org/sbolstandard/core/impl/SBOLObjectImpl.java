/*
 * Copyright (c) 2012 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sbolstandard.core.impl;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.sbolstandard.core.SBOLObject;
import org.sbolstandard.core.SBOLVisitor;

/**
 * JAXB implementation of an SBOL object. 
 * 
 * @author Evren Sirin
 */
@XmlTransient
public abstract class SBOLObjectImpl 
		extends SBOLVisitableImpl 
		implements SBOLObject {
	
    @XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", required = true)
    @XmlJavaTypeAdapter(XmlAdapters.URIAdapter .class)
    @XmlSchemaType(name = "anyURI")
    protected URI uri;
    
	@XmlElement(required = true)
	protected String displayId;
	protected String name;
	protected String description;

	@Override
	public <T extends Throwable> void accept(SBOLVisitor<T> visitor) throws T {
		//visitor.visit(this);
	}

    /**
     * {@inheritDoc}
     */
	public String getDisplayId() {
		return displayId;
	}

    /**
     * {@inheritDoc}
     */
	public void setDisplayId(String value) {
		this.displayId = value;
	}

    /**
     * {@inheritDoc}
     */
	public String getName() {
		return name;
	}

    /**
     * {@inheritDoc}
     */
	public void setName(String value) {
		this.name = value;
	}

    /**
     * {@inheritDoc}
     */
	public String getDescription() {
		return description;
	}

    /**
     * {@inheritDoc}
     */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
     * {@inheritDoc}
     */
    public URI getURI() {
        return uri;
    }

    /**
     * {@inheritDoc}
     */
    public void setURI(URI value) {
        this.uri = value;
    }
}
