/*
 * Copyright (c) 2012 Michal Galdzicki
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

package org.sbolstandard.core;

import java.net.URI;
import java.util.Collection;
import java.util.List;

/**
 * The SBOL data model's DnaComponent.
 *
 * This objects of this type represent DNA components for biological engineering
 * which can be described by SequenceAnnotation objects and must specify their
 * DnaSequence object. DnaComponents are expected to be found inside
 * a SBOL Collection object.
 */

public interface DnaComponent 
	extends SBOLCoreObject {

	/**
     * Positions and directions of <code>SequenceFeature</code>[s] that describe
     * the DNA sequence.
     * @return 0 or more <code>SequenceAnnotation</code>[s] that describe the 
     * DNA composition
     * @see #addAnnotation
     */
    public List<SequenceAnnotation> getAnnotations();

    /**
     * New position and direction of a <code>SequenceFeature</code> that
     * describes the DNA sequence.
     * The DnaComponent could be left un-annotated, but that condition is not a very useful to users.
     * @param annotation a <code>SequenceAnnotation</code> that describes the DNA composition
     */
    public void addAnnotation(SequenceAnnotation annotation);
	public void removeAnnotation(SequenceAnnotation annotation);
	

    /**
     * DNA sequence which this DnaComponent object represents.
     * @return 1 {@link DnaSequence} specifying the DNA sequence of this DnaComponent
     * @see DnaSequence
     */
    public DnaSequence getDnaSequence();

    /**
     * DNA sequence which this DnaComponent object represents.
     * @param dnaSequence specify the DnaSequence of this DnaComponent
     */
    public void setDnaSequence(DnaSequence dnaSequence);

    /**
     * Sequence Ontology vocabulary provides a defined term for types of DNA
     * components.
     * TO DO: implement use of SO within libSBOLj.
     * @return a Sequence Ontology (SO) vocabulary term to describe the type of DnaComponent.
     */
    public Collection<URI> getTypes();

    /**
     * Sequence Ontology vocabulary provides a defined term for types of DNA
     * components.
     *
     * @param type Sequence Ontology URI specifying the type of the DnaComponent
     */
    public void addType(URI type);
	public void removeType(URI type);
}
