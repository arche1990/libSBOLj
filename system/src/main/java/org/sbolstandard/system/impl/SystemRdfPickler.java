package org.sbolstandard.system.impl;

import static org.sbolstandard.core.rdf.RdfPicklers.all;
import static org.sbolstandard.core.rdf.RdfPicklers.byProperty;
import static org.sbolstandard.core.rdf.RdfPicklers.identity;
import static org.sbolstandard.core.rdf.RdfPicklers.notNull;
import static org.sbolstandard.core.rdf.RdfPicklers.nullable;
import static org.sbolstandard.core.rdf.RdfPicklers.property;
import static org.sbolstandard.core.rdf.RdfPicklers.value;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.sbolstandard.core.Collection;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLNamedObject;
import org.sbolstandard.core.SBOLObject;
import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.rdf.CoreRdfPicklers;
import org.sbolstandard.core.rdf.RdfEntityPickler;
import org.sbolstandard.core.rdf.RdfPicklers;
import org.sbolstandard.core.rdf.RdfPropertyPickler;
import org.sbolstandard.core.rdf.CoreRdfPicklers.IO;
import org.sbolstandard.system.SBOLSystem;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;

public class SystemRdfPickler implements RdfEntityPickler<SBOLDocument> {
	  private static SystemRdfPickler instance = null;
	  
	  private final String baseURI;

	  // URIs for resources
	  private final String dnaSequence;
	  private final String sequenceAnnotation;
	  private final String dnaComponent;
	  private final String collection;
	  
	  private final RdfEntityPickler<SBOLNamedObject> sbolNamedObjectPickler;
	  
	  public static SystemRdfPickler instance() throws IOException, IntrospectionException {
		    if(instance == null) {
		      Properties props = new Properties();
		      
 
		      
		      props.load(RdfPicklers.class.getResourceAsStream("/org.sbolstandard.core.rdf/SbolRdfPicklers.properties")); 
		      for(String key : props.stringPropertyNames()) {
		    	  String value = props.getProperty(key);
		    	  System.out.println(key + " => " + value);
		    	}
		    	  
		      //props.load(RdfPicklers.class.getResourceAsStream("orgSbolRdfPicklers.properties"));
		      instance = new SystemRdfPickler(props);
		    }

		    return instance;
		  }

	  public SystemRdfPickler(Properties props) throws IntrospectionException {
		    // initialization order is important here
		    baseURI = getProperty(props, "baseURI");
		    dnaSequence = getProperty(props, "DnaSequence");
		    sequenceAnnotation = getProperty(props, "SequenceAnnotation");
		    dnaComponent = getProperty(props, "DnaComponent");
		    collection = getProperty(props, "Collection");

		    sbolNamedObjectPickler = mkSbolNamedObjectPickler(props);
		  }

	  
	  
	 public void pickle(final Model model, SBOLDocument document) 
	 {
		 for(SBOLObject o : document.getContents()) {
		      o.accept(new SBOLSystemVisitor<RuntimeException>() {
		        @Override
		        public void visit(SBOLDocument doc) throws RuntimeException {
		          // skip
		        }

		        @Override
		        public void visit(Collection coll) throws RuntimeException {
		         
		        }

		        @Override
		        public void visit(DnaComponent component) throws RuntimeException {
		         
		        }

		        @Override
		        public void visit(DnaSequence sequence) throws RuntimeException {
		          
		        }

		        @Override
		        public void visit(SequenceAnnotation annotation) throws RuntimeException {
		          // skip - there should be none of these at top-level
		        }
		        
		        @Override
		        public void visit(SBOLSystem system) throws RuntimeException {
		        	sbolNamedObjectPickler.pickle(model, system);   
		        }

		      });
		    }	    				 
	 }
	 
	 
	  public RdfEntityPickler<SBOLNamedObject> getSystemPickler() {
		    return sbolNamedObjectPickler;
		  }
	  
	 
	 private RdfEntityPickler<SBOLNamedObject> mkSbolNamedObjectPickler(Properties props) throws IntrospectionException {
		    Properties cProps = propertiesFor(props, "SBOLNamedObject");

		    RdfPropertyPickler<SBOLNamedObject, String> name =
		            value(identity, property(getProperty(cProps, "name")));
		    RdfPropertyPickler<SBOLNamedObject, String> displayId =
		            value(identity, property(getProperty(cProps, "displayId")));
		    RdfPropertyPickler<SBOLNamedObject, String> description =
		            value(identity, property(getProperty(cProps, "description")));

		    return RdfPicklers.all(
		            byProperty(SBOLNamedObject.class, "name", notNull(name)),
		            byProperty(SBOLNamedObject.class, "displayId", nullable(displayId)),
		            byProperty(SBOLNamedObject.class, "description", nullable(description)));
		  }
	 
	 private static Properties propertiesFor(Properties properties, String pfx) {
		    Properties result = new Properties();
		    String pfxDot = pfx + ".";
		    int pfxDotLen = pfxDot.length();

		    for(Map.Entry<Object, Object> ent : properties.entrySet()) {
		      String key = (String) ent.getKey();
		      if(key.startsWith(pfxDot)) {
		        result.put(key.substring(pfxDotLen), ent.getValue());
		      }
		    }

		    return result;
		  }
	 
	 private static String getProperty(Properties props, String propName) {
		    String prop = (String) props.get(propName);
		    if(prop == null) throw new IllegalArgumentException("Missing property: " + propName);
		    
		    return prop;
		  }
	 
	 
	  public String getDnaComponent() {
		    return dnaComponent;
		  }
	  
	  public String getBaseUri() {
		    return baseURI;
		  }

	  public IO getIO() {
		    String format = "RDF/XML-ABBREV";
		    return getIO(format, getDnaComponent());
		  }

		  public IO getIO(final String format, final String ... topLevel) {
		    return new IO() {
		      public void write(Model model, Writer rdfOut) throws IOException
		      {
		        RDFWriter writer = model.getWriter(format);
		        writer.setProperty("tab","3");

		        Resource[] topLevelResources = new Resource[topLevel.length];
		        for(int i = 0; i < topLevel.length; i++) {
		          topLevelResources[i] = model.createResource(topLevel[i]);
		        }

		        writer.setProperty("prettyTypes", topLevelResources);
		        writer.write(model, rdfOut, getBaseUri());
		      }

		      @Override
		      public void write(SBOLDocument document, Writer rdfOut) throws IOException {
		        Model model = ModelFactory.createDefaultModel();
		        model.setNsPrefix("sbol", getBaseUri());
		        pickle(model, document);
		        write(model, rdfOut);
		      }
		    };
		  }
		  
}
