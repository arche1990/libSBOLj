package org.sbolstandard.core2;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.sbolstandard.core2.abstract_classes.Documented;

/**
 * 
 * @author Ernst Oberortner
 * @author Nicholas Roehner
 * @version 2.0
 */
public class ModuleInstantiation extends Documented {
	
	private List<RefersTo> references;
	private Module instantiatedModule;
	
	public ModuleInstantiation(URI identity, Module instantiatedModule) {
		super(identity);
		this.instantiatedModule = instantiatedModule;
		this.references = new ArrayList<RefersTo>();
	}

	public Module getInstantiatedModule() {
		return instantiatedModule;
	}

	public void setInstantiatedModule(Module instantiatedModule) {
		this.instantiatedModule = instantiatedModule;
	}

	public List<RefersTo> getReferences() {
		return references;
	}

	public void setReferences(List<RefersTo> references) {
		this.references = references;
	}

	
	
	//	private Module instantiatedModule;
//	
//	/**
//	 * @param identity an identity for the module instantiation
//	 * @param displayId a display ID for the module instantiation
//	 * @param instantiatedModule the module to be instantiated
//	 */
//	public ModuleInstantiation(URI identity, String displayId, Module instantiatedModule) {
//		super(identity, displayId);
//		this.instantiatedModule = instantiatedModule;
//	}
//
//	/**
//	 * 
//	 * @return the instantiated module
//	 */
//	public Module getInstantiatedModule() {
//		return instantiatedModule;
//	}

}