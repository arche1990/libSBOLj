package org.sbolstandard.system;

import java.util.List;

import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLNamedObject;
import org.sbolstandard.core.SBOLRootObject;


public interface Device extends SBOLNamedObject, SBOLRootObject {
	
    public List<DnaComponent> getDnaComponents();

    public void addDnaComponent(DnaComponent component);
    
	public void removeDnaComponent(DnaComponent component);
	
	  public String getName();

	    public void setName(String name);
	    
}
