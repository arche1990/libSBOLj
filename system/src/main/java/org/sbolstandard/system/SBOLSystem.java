package org.sbolstandard.system;



import org.sbolstandard.core.SBOLNamedObject;
import org.sbolstandard.core.SBOLRootObject;
import java.util.List;

/**
 * The SBOL data model's DnaComponent.
 *
 * This objects of this type represent DNA components for biological engineering
 * which can be described by SequenceAnnotation objects and must specify their
 * DnaSequence object. DnaComponents are expected to be found inside
 * a SBOL Collection object.
 */

public interface SBOLSystem extends SBOLNamedObject, SBOLRootObject {
    public String getDescription();

    public void setDescription(String description);

    /**
     * Identifier to display to users.
     * @return a human readable identifier
     */
    public String getDisplayId();

    public void setDisplayId(String displayId);

    public String getName();

    public void setName(String name);
    
    //Models - will be in the Modelling extension
    //	Model(uri)
    public List<Model> getModels();
    
    public void AddModel(Model model);
    
    public void removeModel(Model model);
    
    //Devices - will be in the core
    //	Device(uri,dnaComponents)
    public List<Device> getDevices();

    public void addDevice(Device device);
    
	public void removeDevice(Device device);
    
    //Contexts - will be in the Context extension
    // Context(uri)
    public List<Context> getContexts();

    public void addContext(Context context);
    
	public void removeContext(Context context);
}
