package org.sbolstandard.system;

import java.net.URI;

import org.sbolstandard.core.SBOLNamedObject;
import org.sbolstandard.core.SBOLRootObject;


public interface Model extends SBOLNamedObject, SBOLRootObject {


	  public String getName();

	  public void setName(String name);
	   

	  public URI getSource();
	  public void setSource(URI uri);
	  
	  public URI getLanguage();
	  public void setLanguage(URI uri);

	  public URI getFramework();
	  public void setFramework(URI uri);

	  
	  public String getDescription();

	    public void setDescription(String description);

	    /**
	     * Identifier to display to users.
	     * @return a human readable identifier
	     */
	    public String getDisplayId();

	    public void setDisplayId(String displayId);
	    
	    

	
	    
	  
		
}
