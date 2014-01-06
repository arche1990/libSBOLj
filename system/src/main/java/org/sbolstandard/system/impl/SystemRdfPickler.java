package org.sbolstandard.system.impl;

import static org.sbolstandard.core.rdf.RdfPicklers.all;
import static org.sbolstandard.core.rdf.RdfPicklers.byProperty;
import static org.sbolstandard.core.rdf.RdfPicklers.collection;
import static org.sbolstandard.core.rdf.RdfPicklers.identity;
import static org.sbolstandard.core.rdf.RdfPicklers.notNull;
import static org.sbolstandard.core.rdf.RdfPicklers.nullable;
import static org.sbolstandard.core.rdf.RdfPicklers.object;
import static org.sbolstandard.core.rdf.RdfPicklers.property;
import static org.sbolstandard.core.rdf.RdfPicklers.type;
import static org.sbolstandard.core.rdf.RdfPicklers.value;
import static org.sbolstandard.core.rdf.RdfPicklers.walkTo;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Map;
import java.util.Properties;

import org.sbolstandard.core.Collection;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLNamedObject;
import org.sbolstandard.core.SBOLObject;
import org.sbolstandard.core.SBOLRootObject;
import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.StrandType;
import org.sbolstandard.core.rdf.CoreRdfPicklers;
import org.sbolstandard.core.rdf.RdfEntityPickler;
import org.sbolstandard.core.rdf.RdfPicklers;
import org.sbolstandard.core.rdf.RdfPropertyPickler;
import org.sbolstandard.core.rdf.CoreRdfPicklers.IO;
import org.sbolstandard.system.Context;
import org.sbolstandard.system.Device;
import org.sbolstandard.system.SBOLSystem;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;

public class SystemRdfPickler extends CoreRdfPicklers  {
	  private static SystemRdfPickler instance = null;
	  
	  private final RdfEntityPickler<SBOLSystem> systemObjectPickler;
	  private final RdfEntityPickler<Context> contextPickler;
	  private final RdfEntityPickler<Device> devicePickler;
	  private final RdfEntityPickler<org.sbolstandard.system.Model> modelPickler;
	  private final RdfEntityPickler<SBOLRootObject> rootObjectPickler;
	  
	  	  
	  public static SystemRdfPickler instance() throws IOException, IntrospectionException {
		    if(instance == null) {
		      Properties props = new Properties();
		      
		      props.load(RdfPicklers.class.getResourceAsStream("/org.sbolstandard.core.rdf/SbolRdfPicklers.properties")); 
		      /*for(String key : props.stringPropertyNames()) {
		    	  String value = props.getProperty(key);
		    	  System.out.println(key + " => " + value);
		    	}*/
		    	  
		      instance = new SystemRdfPickler(props);
		    }
		    return instance;
		  }

	  public SystemRdfPickler(Properties props) throws IntrospectionException,IOException {
		    super(props);
		    devicePickler = mkDevicePickler(props); 		    
		    contextPickler = mkContextPickler(props); 
		    modelPickler=mkModelPickler(props);
		    systemObjectPickler=mkSystemObjectPickler(props);		 
		    rootObjectPickler=mkRootObjectPickler(props);
		    
		  }
	  
	  private RdfEntityPickler<SBOLRootObject> mkRootObjectPickler(Properties props) throws IntrospectionException,IOException {
			
	  
		  
		  return null;
	  }
	 public void pickle(final Model model, SBOLDocument document)  throws Exception
	 {
		 for(SBOLObject o : document.getContents()) {
		      o.accept(new SBOLSystemVisitor<Exception>() {
		        @Override
		        public void visit(SBOLDocument doc) throws RuntimeException {
		          // skip
		        }

		        @Override
		        public void visit(Collection coll) throws RuntimeException {
		         
		        }

		        @Override
		        public void visit(DnaComponent component) throws Exception {
		        	 CoreRdfPicklers.instance().getNamedObjectPickler().pickle(model, component);
		        }

		        @Override
		        public void visit(DnaSequence sequence) throws RuntimeException {
		        	dnaSequencePickler.pickle(model, sequence);
		        }

		        @Override
		        public void visit(SequenceAnnotation annotation) throws RuntimeException {
		          // skip - there should be none of these at top-level
		        }
		        
		        @Override
		        public void visit(SBOLSystem system) throws RuntimeException {
		        	systemObjectPickler.pickle(model, system);
		        
		        	//sbolNamedObjectPickler.pickle(model, system);   
		        }

				@Override
				public void visit(Context context) throws RuntimeException {
					//sbolNamedObjectPickler.pickle(model, context); 
				}

				@Override
				public void visit(org.sbolstandard.system.Model m) throws RuntimeException {
					//sbolNamedObjectPickler.pickle(model, m);
				}
				
				@Override
				public void visit(Device device) throws RuntimeException {
					//sbolNamedObjectPickler.pickle(model, device);
				}

		      });
		    }	    				 
	 }	 
	  	 		 
	 private RdfEntityPickler<SBOLSystem> mkSystemObjectPickler(Properties props) throws IntrospectionException,IOException {
		 RdfPropertyPickler<SBOLSystem, org.sbolstandard.system.Model> model =  object(identity, property("http://sbols.org/v1#model"), identity);
		 RdfPropertyPickler<SBOLSystem, Context> context =object(identity, property("http://sbols.org/v1#context"), identity);
		 RdfPropertyPickler<SBOLSystem, Device> device=object(identity, property("http://sbols.org/v1#device"), identity);
		 		 
		 return all(
				  byProperty(SBOLSystem.class, "models", notNull(collection(all(model, walkTo(modelPickler))))),				  
				  byProperty(SBOLSystem.class, "contexts", notNull(collection(all(context, walkTo(contextPickler))))),
				  byProperty(SBOLSystem.class, "devices", notNull(collection(all(device, walkTo(devicePickler))))),
				  CoreRdfPicklers.instance().getNamedObjectPickler(),
				  type( CoreRdfPicklers.instance().getBaseUri() +  "System", identity)
				  );	 	 
	 }
	 
	 private RdfEntityPickler<Context> mkContextPickler(Properties props) throws IntrospectionException,IOException {
		   RdfPropertyPickler<Context, String> name=value(identity, property("http://sbols.org/v1#name"));		   
		    return all(		    	  		    	    
		    		type( CoreRdfPicklers.instance().getBaseUri()+  "Context", identity),
		    		 CoreRdfPicklers.instance().getNamedObjectPickler()
		            );		          
		  }
	 
	 private RdfEntityPickler<Device> mkDevicePickler(Properties props) throws IntrospectionException,IOException {
		   RdfPropertyPickler<Device, DnaComponent> component=object(identity, property("http://sbols.org/v1#component"), identity);			
		    RdfPropertyPickler<Device, String> name=value(identity, property("http://sbols.org/v1#name"));

		    return all(
		            type(CoreRdfPicklers.instance().getBaseUri() +  "Device", identity),
		            byProperty(Device.class, "dnaComponents", notNull(collection(all(component, walkTo(CoreRdfPicklers.instance().getDnaComponentPickler()))))),
		            CoreRdfPicklers.instance().getNamedObjectPickler()		            
		            );		          
		  }
	 
	 private RdfEntityPickler<org.sbolstandard.system.Model> mkModelPickler(Properties props) throws IntrospectionException,IOException {
		    
		    RdfPropertyPickler<org.sbolstandard.system.Model, String> name=value(identity, property("http://sbols.org/v1#name"));
		    RdfPropertyPickler<org.sbolstandard.system.Model, URI> source=object(identity, property("http://sbols.org/v1#source"),RdfPicklers.uri);			   
		    RdfPropertyPickler<org.sbolstandard.system.Model, URI> language=object(identity, property("http://sbols.org/v1#language"),RdfPicklers.uri);			   
		    RdfPropertyPickler<org.sbolstandard.system.Model, URI> framework=object(identity, property("http://sbols.org/v1#framework"),RdfPicklers.uri);			   

		    return all(
		            type(CoreRdfPicklers.instance().getBaseUri() +  "Model", identity),
		            byProperty(org.sbolstandard.system.Model.class, "framework", nullable(framework)),
		            byProperty(org.sbolstandard.system.Model.class, "language", nullable(language)),		            
		            byProperty(org.sbolstandard.system.Model.class, "source", nullable(source)),		            
		            CoreRdfPicklers.instance().getNamedObjectPickler()		            
		            );		          
		  }
	 	 		 
	  public IO getIO() throws Exception{
		    String format = "RDF/XML-ABBREV";		    
		    return SystemRdfPickler.instance().getIO(format, CoreRdfPicklers.instance().getDnaComponent(), "http://sbols.org/v1#Context", "http://sbols.org/v1#Device","http://sbols.org/v1#Model");
		  }	 
}
