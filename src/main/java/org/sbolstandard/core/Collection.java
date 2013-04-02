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


/**
* The SBOL data model's Collection for RDF and Json.
 *
 * The objects of this type represent an organizational container which helps
 * users and developers conceptualize a set of DNA components and SequenceFeatures
 * as a group. Any combination of these objects can be added to a Collection,
 * annotated with a displayID, name and description and be shared on the web.
 * Such collections could be a set of restriction enzyme recognition
 * sites, such as the features commonly used for BBF RFC 10 BioBricks. A Collection
 * could contain all the DNA components used in a specific project, lab, or any
 * custom grouping specified by the user.
 */

public interface Collection 
	extends SBOLRootObject {

    /**
     * Elements that are intended as engineering components in synthetic biology.
     *
     * For example, standard biological parts, BioBricks, oligo components,
     * vector plasmids, genomes, or any other DNA segment of interest as a building
     * block of biological systems.
     *
     * @return 0 or more <code>DnaComponent</code>[s] that are in this Collection
     */
    public java.util.Collection<DnaComponent> getComponents();

    /**
     * Defined DNA segment for engineering biological systems, which belongs to
     * this Collection.
     *
     * Any one of the following, standard biological parts, BioBricks, oligo components,
     * vector plasmids, genomes, or any other DNA segment of interest as a building
     * block of biological systems.
     *
     * @param component a <code>DnaComponent</code> that should be a member of this collection
     */
    public void addComponent(DnaComponent component);
	public void removeComponent(DnaComponent component);

}
