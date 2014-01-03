package org.sbolstandard.system;

import java.net.URI;
import java.util.Collection;

import org.sbolstandard.core.SBOLNamedObject;
import org.sbolstandard.core.SBOLRootObject;


public interface Context extends SBOLNamedObject, SBOLRootObject {

	  public String getName();

	    public void setName(String name);
	    
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
		
		public String getDescription();

		public void setDescription(String description);

		    /**
		     * Identifier to display to users.
		     * @return a human readable identifier
		     */
		public String getDisplayId();

		public void setDisplayId(String displayId);

		
		
	    
}
