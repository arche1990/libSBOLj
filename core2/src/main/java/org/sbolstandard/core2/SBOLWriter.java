package org.sbolstandard.core2;

import static uk.ac.ncl.intbio.core.datatree.Datatree.DocumentRoot;
import static uk.ac.ncl.intbio.core.datatree.Datatree.NamedProperties;
import static uk.ac.ncl.intbio.core.datatree.Datatree.NamedProperty;
import static uk.ac.ncl.intbio.core.datatree.Datatree.NamespaceBindings;
import static uk.ac.ncl.intbio.core.datatree.Datatree.NestedDocument;
import static uk.ac.ncl.intbio.core.datatree.Datatree.TopLevelDocument;
import static uk.ac.ncl.intbio.core.datatree.Datatree.TopLevelDocuments;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.sbolstandard.core2.abstract_classes.Documented;
import org.sbolstandard.core2.abstract_classes.Identified;
import org.sbolstandard.core2.abstract_classes.Location;
import org.sbolstandard.core2.abstract_classes.TopLevel;

import uk.ac.intbio.core.io.turtle.TurtleIo;
import uk.ac.ncl.intbio.core.datatree.DocumentRoot;
import uk.ac.ncl.intbio.core.datatree.NamedProperty;
import uk.ac.ncl.intbio.core.datatree.NestedDocument;
import uk.ac.ncl.intbio.core.datatree.TopLevelDocument;
import uk.ac.ncl.intbio.core.io.CoreIoException;
import uk.ac.ncl.intbio.core.io.json.JsonIo;
import uk.ac.ncl.intbio.core.io.json.StringifyQName;
import uk.ac.ncl.intbio.core.io.rdf.RdfIo;

/**
 * @author Tramy Nguyen
 * @version 2.0
 *
 */
public class SBOLWriter {

	/*
	 * TODO: not all isSet() for the members are called.
	 */

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output file
	 * in RDF format.
	 * @param doc
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void write(SBOLDocument doc, File file) throws FileNotFoundException{
		FileOutputStream stream = new FileOutputStream(file);
		BufferedOutputStream buffer = new BufferedOutputStream(stream);
		try
		{
			write(doc, buffer);
		}
		catch (XMLStreamException e) { }
		catch (FactoryConfigurationError  e) { }
		catch (CoreIoException e) { }
		finally
		{
			try
			{
				try
				{
					stream.close();
				}
				finally { buffer.close(); }
			}
			catch (IOException e) { }
		}
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output stream
	 * in RDF format.
	 * @param doc
	 * @param out
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationError
	 * @throws CoreIoException
	 */
	public static void write(SBOLDocument doc, OutputStream out)
			throws XMLStreamException, FactoryConfigurationError, CoreIoException
	{
		writeRDF(new OutputStreamWriter(out),
				DocumentRoot( NamespaceBindings(doc.getNameSpaceBindings()),
						TopLevelDocuments(getTopLevelDocument(doc))));
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output
	 * file name in RDF format
	 * @param doc
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void write(SBOLDocument doc, String filename) throws FileNotFoundException
	{
		write(doc, new File(filename));
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output file
	 * in JSON format.
	 * @param doc
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void writeJSON(SBOLDocument doc, File file) throws FileNotFoundException{
		FileOutputStream stream = new FileOutputStream(file);
		BufferedOutputStream buffer = new BufferedOutputStream(stream);
		try
		{
			writeJSON(doc, buffer);
		}
		catch (XMLStreamException e) { }
		catch (FactoryConfigurationError  e) { }
		catch (CoreIoException e) { }
		catch (Throwable e) { e.printStackTrace();}
		finally
		{
			try
			{
				try { stream.close(); }
				finally { buffer.close(); }
			}
			catch (IOException e) { }
		}
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output stream
	 * in JSON format.
	 * @param doc
	 * @param out
	 * @throws FactoryConfigurationError
	 * @throws Exception
	 */
	public static void writeJSON(SBOLDocument doc, OutputStream out)
			throws FactoryConfigurationError, Exception {

		writeJSON(new OutputStreamWriter(out),
				DocumentRoot( NamespaceBindings(doc.getNameSpaceBindings()),
						TopLevelDocuments(getTopLevelDocument(doc))));

	}

	/**
	 * * Serializes a given SBOLDocument and outputs the data from the serialization to the given output
	 * file name in JSON format
	 * @param doc
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void writeJSON(SBOLDocument doc, String filename) throws FileNotFoundException
	{
		writeJSON(doc, new File(filename));
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output file
	 * in RDF format.
	 * @param doc
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void writeRDF(SBOLDocument doc, File file) throws FileNotFoundException
	{
		FileOutputStream stream = new FileOutputStream(file);
		BufferedOutputStream buffer = new BufferedOutputStream(stream);
		try
		{
			writeRDF(doc, buffer);
		}
		catch (XMLStreamException e) { }
		catch (FactoryConfigurationError  e) { }
		catch (CoreIoException e) { }
		finally
		{
			try
			{
				try
				{
					stream.close();
				}
				finally { buffer.close(); }
			}
			catch (IOException e) { }
		}
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output stream
	 * in RDF format.
	 * @param doc
	 * @param out
	 * @throws XMLStreamException
	 * @throws FactoryConfigurationError
	 * @throws CoreIoException
	 */
	public static void writeRDF(SBOLDocument doc, OutputStream out)
			throws XMLStreamException, FactoryConfigurationError, CoreIoException
	{
		writeRDF(new OutputStreamWriter(out),
				DocumentRoot( NamespaceBindings(doc.getNameSpaceBindings()),
						TopLevelDocuments(getTopLevelDocument(doc))));
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output
	 * file name in RDF format
	 * @param doc
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public static void writeRDF(SBOLDocument doc, String filename) throws FileNotFoundException
	{
		writeRDF(doc, new File(filename));
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output file
	 * in Turtle format.
	 * @param doc
	 * @param file
	 * @throws Throwable
	 */
	public static void writeTurtle(SBOLDocument doc, File file) throws Throwable{
		FileOutputStream stream 	= new FileOutputStream(file);
		BufferedOutputStream buffer = new BufferedOutputStream(stream);
		try
		{
			writeTurtle(doc, buffer);
		}
		catch (XMLStreamException e) { }
		catch (FactoryConfigurationError  e) { }
		catch (CoreIoException e) { }
		finally
		{
			try
			{
				try { stream.close(); }
				finally { buffer.close(); }
			}
			catch (IOException e) { }
		}
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output stream
	 * in Turtle format.
	 * @param doc
	 * @param out
	 * @throws FactoryConfigurationError
	 * @throws Exception
	 */
	public static void writeTurtle(SBOLDocument doc, OutputStream out)
			throws FactoryConfigurationError, Exception
	{
		writeTurtle(new OutputStreamWriter(out),
				DocumentRoot( NamespaceBindings(doc.getNameSpaceBindings()),
						TopLevelDocuments(getTopLevelDocument(doc))));
	}

	/**
	 * Serializes a given SBOLDocument and outputs the data from the serialization to the given output
	 * file name in Turtle format
	 * @param doc
	 * @param filename
	 * @throws Throwable
	 */
	public static void writeTurtle(SBOLDocument doc, String filename) throws Throwable
	{
		writeTurtle(doc, new File(filename));
	}

	private static void writeJSON(Writer stream, DocumentRoot<QName> document) throws Exception
	{
		HashMap<String, Object> config = new HashMap<String,Object>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonGenerator writer = Json.createGeneratorFactory(config).createGenerator(stream);
		JsonIo jsonIo = new JsonIo();
		jsonIo.createIoWriter(writer).write(StringifyQName.qname2string.mapDR(document));
		writer.flush();
		writer.close();
	}

	private static void writeRDF(Writer stream, DocumentRoot<QName> document) throws XMLStreamException, FactoryConfigurationError, CoreIoException
	{
		XMLStreamWriter xmlWriter = new IndentingXMLStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(stream));
		RdfIo rdfIo = new RdfIo();
		rdfIo.createIoWriter(xmlWriter).write(document);
		xmlWriter.flush();
		xmlWriter.close();
	}

	private static void writeTurtle(Writer stream, DocumentRoot<QName> document) throws Exception
	{
		PrintWriter printWriter = new PrintWriter(stream);
		TurtleIo turtleIo = new TurtleIo();
		turtleIo.createIoWriter(printWriter).write(document);
		printWriter.flush();
	}

	private static void formatCollections (List<Collection> collections, List<TopLevelDocument<QName>> topLevelDoc)
	{
		for(Collection c : collections)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonTopLevelData(list, c);
			if(!c.getMembers().isEmpty())//(c.isSetMembers())
			{
				for (URI member : c.getMembers())
				{
					list.add(NamedProperty(Sbol2Terms.Collection.hasMembers, member));
				}
			}

			topLevelDoc.add(TopLevelDocument(Sbol2Terms.Collection.Collection, c.getIdentity(), NamedProperties(list)));
		}
	}

	private static void formatCommonDocumentedData (List<NamedProperty<QName>> list, Documented d)
	{
		formatCommonIdentifiedData(list, d);
		if(d.getDisplayId() != null)
			list.add(NamedProperty(Sbol2Terms.Documented.displayId, d.getDisplayId()));
		if(d.isSetName())
			list.add(NamedProperty(Sbol2Terms.Documented.title, d.getName()));
		if(d.isSetDescription())
			list.add(NamedProperty(Sbol2Terms.Documented.description, d.getDescription()));
	}

	private static void formatCommonIdentifiedData (List<NamedProperty<QName>> list, Identified t)
	{
		if(t.getPersistentIdentity() != null)
			list.add(NamedProperty(Sbol2Terms.Identified.persistentIdentity, t.getPersistentIdentity()));

		if(t.getVersion() != null)
			list.add(NamedProperty(Sbol2Terms.Identified.version, t.getVersion()));

		if(t.isSetAnnotations())
		{
			for(Annotation annotation : t.getAnnotations())
			{
				list.add(annotation.getValue());
			}
		}
	}

	private static void formatCommonTopLevelData (List<NamedProperty<QName>> list, TopLevel t)
	{
		formatCommonDocumentedData(list,t);
	}

	private static void formatComponentDefinitions (List<ComponentDefinition> componentDefinitions, List<TopLevelDocument<QName>> topLevelDoc)
	{

		for(ComponentDefinition c : componentDefinitions)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonTopLevelData(list,c);
			if(c.getTypes() != null)
			{
				for(URI types : c.getTypes())
				{
					list.add(NamedProperty(Sbol2Terms.ComponentDefinition.type, types));
				}
			}

			if(c.getRoles() != null)
			{
				for (URI roles : c.getRoles())
				{
					list.add(NamedProperty(Sbol2Terms.ComponentDefinition.roles, roles));
				}
			}

			formatComponents(c.getComponents(),list);
			formatSequenceAnnotations(c.getSequenceAnnotations(),list);
			formatSequenceConstraints(c.getSequenceConstraints(),list);
			if(c.getSequence() != null)
				formatSequence(c.getSequence(), list);

			topLevelDoc.add(TopLevelDocument(Sbol2Terms.ComponentDefinition.ComponentDefinition, c.getIdentity(), NamedProperties(list)));
		}
	}

	/**
	 * formatFunctionalComponents for Module
	 * @param functionalInstantiation
	 * @param properties
	 */
	private static void formatFunctionalComponents(List<FunctionalComponent> functionalInstantiation,
			List<NamedProperty<QName>> properties)
	{
		for(FunctionalComponent f : functionalInstantiation)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonDocumentedData(list, f);

			if(f.getDefinition() != null)
				list.add(NamedProperty(Sbol2Terms.ComponentInstance.hasComponentDefinition, f.getDefinition()));
			if(f.getAccess() != null)
				list.add(NamedProperty(Sbol2Terms.ComponentInstance.access, f.getAccessURI()));
			if(f.getDirection() != null)
				list.add(NamedProperty(Sbol2Terms.FunctionalComponent.direction, f.getDirectionURI()));
			if(!f.getMapsTos().isEmpty())
			{
				List<NestedDocument<QName>> referenceList = getMapsTo(f.getMapsTos());

				for(NestedDocument<QName> n : referenceList)
				{
					list.add(NamedProperty(Sbol2Terms.ComponentInstance.hasMapsTo, n));
				}
			}

			properties.add(NamedProperty(Sbol2Terms.ModuleDefinition.hasfunctionalComponent,
					NestedDocument( Sbol2Terms.FunctionalComponent.FunctionalComponent,
							f.getIdentity(), NamedProperties(list))));
		}
	}

	/**
	 * formatInteractions for Module
	 * @param interactions
	 * @param properties
	 */
	private static void formatInteractions (List<Interaction> interactions,
			List<NamedProperty<QName>> properties)
	{
		for(Interaction i : interactions)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonDocumentedData(list, i);

			if(i.getTypes() != null)
			{
				for(URI type : i.getTypes())
				{
					list.add(NamedProperty(Sbol2Terms.Interaction.type, type));
				}
			}
			if(i.isSetParticipations())
			{
				List<NestedDocument<QName>> participantList = formatParticipations(i.getParticipations());
				for(NestedDocument<QName> n : participantList)
				{
					list.add(NamedProperty(Sbol2Terms.Interaction.hasParticipations, n));
				}
			}

			properties.add(NamedProperty(Sbol2Terms.ModuleDefinition.hasInteractions,
					NestedDocument( Sbol2Terms.Interaction.Interaction,
							i.getIdentity(), NamedProperties(list))));
		}
	}

	private static void formatModels (List<Model> models, List<TopLevelDocument<QName>> topLevelDoc)
	{
		for(Model m : models)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonTopLevelData(list,m);

			if(m.getSource() != null)
				list.add(NamedProperty(Sbol2Terms.Model.source, m.getSource()));
			if(m.getLanguage() != null)
				list.add(NamedProperty(Sbol2Terms.Model.language, m.getLanguage()));
			if(m.getFramework() != null)
				list.add(NamedProperty(Sbol2Terms.Model.framework, m.getFramework()));
			if(m.getRoles() != null)
			{
				for (URI role : m.getRoles())
				{
					list.add(NamedProperty(Sbol2Terms.Model.roles, role));
				}
			}

			topLevelDoc.add(TopLevelDocument(Sbol2Terms.Model.Model, m.getIdentity(), NamedProperties(list)));
		}
	}

	private static void formatModels(Set<URI> models, List<NamedProperty<QName>> list)
	{
		for(URI m : models)
		{
			list.add(NamedProperty(Sbol2Terms.ModuleDefinition.hasModels, m));
		}
	}

	/**
	 * getModule for Module
	 * @param module
	 * @param properties
	 */
	private static void formatModule (List<Module> module,
			List<NamedProperty<QName>> properties)
	{
		for(Module m : module)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonDocumentedData(list, m);

			if(m.getDefinition() != null)
				list.add(NamedProperty(Sbol2Terms.Module.hasDefinition, m.getDefinition()));
			if(!m.getMapsTos().isEmpty())
			{
				List<NestedDocument<QName>> referenceList = getMapsTo(m.getMapsTos());
				for(NestedDocument<QName> n : referenceList)
				{
					list.add(NamedProperty(Sbol2Terms.Module.hasMapsTo, n));
				}
			}

			properties.add(NamedProperty(Sbol2Terms.ModuleDefinition.hasModule,
					NestedDocument( Sbol2Terms.Module.Module,
							m.getIdentity(), NamedProperties(list))));
		}
	}

	private static void formatModuleDefinitions(List<ModuleDefinition> module, List<TopLevelDocument<QName>> topLevelDoc)
	{
		for (ModuleDefinition m : module)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonTopLevelData(list,m);
			if(m.getRoles() != null)
			{
				for (URI role : m.getRoles())
				{
					list.add(NamedProperty(Sbol2Terms.ModuleDefinition.roles, role));
				}
			}

			formatFunctionalComponents(m.getFunctionalComponents(),list);
			formatInteractions(m.getInteractions(),list);
			formatModels(m.getModels(),list);
			formatModule(m.getModules(),list);

			topLevelDoc.add(TopLevelDocument(Sbol2Terms.ModuleDefinition.ModuleDefinition, m.getIdentity(), NamedProperties(list)));
		}
	}

	private static List<NestedDocument<QName>> formatParticipations(List<Participation> participations)
	{
		List<NestedDocument<QName>> nestedDoc = new ArrayList<NestedDocument<QName>>();

		for(Participation p : participations)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonIdentifiedData(list, p);

			if(p.getRoles() != null)
				for(URI r : p.getRoles())
					list.add(NamedProperty(Sbol2Terms.Participation.role, r));
			if(p.getParticipant() != null)
				list.add(NamedProperty(Sbol2Terms.Participation.hasParticipant, p.getParticipant()));

			nestedDoc.add(NestedDocument(Sbol2Terms.Participation.Participation, p.getIdentity(), NamedProperties(list)));
		}

		return nestedDoc;
	}

	private static void formatSequence(URI sequence, List<NamedProperty<QName>> list)
	{
		list.add(NamedProperty(Sbol2Terms.ComponentDefinition.hasSequence, sequence));
	}


	private static void formatSequenceAnnotations(List<SequenceAnnotation> sequenceAnnotations,
			List<NamedProperty<QName>> properties)
	{
		for(SequenceAnnotation s : sequenceAnnotations)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonDocumentedData(list, s);
			if(s.getLocation() != null)
				list.add(getLocation(s.getLocation()));
			if(s.getComponent() != null)
				list.add(NamedProperty(Sbol2Terms.SequenceAnnotation.hasComponent, s.getComponent()));

			properties.add(NamedProperty(Sbol2Terms.ComponentDefinition.hasSequenceAnnotations,
					NestedDocument( Sbol2Terms.SequenceAnnotation.SequenceAnnotation,
							s.getIdentity(), NamedProperties(list))));
		}

	}

	private static void formatSequenceConstraints(List<SequenceConstraint> sequenceConstraint,
			List<NamedProperty<QName>> properties)
	{
		for(SequenceConstraint s : sequenceConstraint)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonIdentifiedData(list, s);

			if(s.getPersistentIdentity() != null)
				list.add(NamedProperty(Sbol2Terms.Identified.persistentIdentity, s.getPersistentIdentity()));
			if(s.getRestriction() != null)
				list.add(NamedProperty(Sbol2Terms.SequenceConstraint.restriction, s.getRestrictionURI()));
			if(s.getSubject() != null)
				list.add(NamedProperty(Sbol2Terms.SequenceConstraint.hasSubject, s.getSubject()));
			if(s.getObject() != null)
				list.add(NamedProperty(Sbol2Terms.SequenceConstraint.hasObject, s.getObject()));

			properties.add(NamedProperty(Sbol2Terms.ComponentDefinition.hasSequenceConstraints,
					NestedDocument( Sbol2Terms.SequenceConstraint.SequenceConstraint,
							s.getIdentity(), NamedProperties(list))));
		}

	}

	private static void formatSequences (List<Sequence> sequences, List<TopLevelDocument<QName>> topLevelDoc)
	{
		for(Sequence s : sequences)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonTopLevelData(list, s);
			if(s.getElements() != null)
				list.add(NamedProperty(Sbol2Terms.Sequence.elements, s.getElements()));
			if(s.getEncoding() != null)
				list.add(NamedProperty(Sbol2Terms.Sequence.encoding, s.getEncoding()));

			topLevelDoc.add(TopLevelDocument(Sbol2Terms.Sequence.Sequence, s.getIdentity(), NamedProperties(list)));
		}

	}

	private static void formatComponents(List<Component> components,
			List<NamedProperty<QName>> properties)
	{
		for(Component s : components)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();

			formatCommonDocumentedData(list, s);
			if(s.getAccess() != null)
				list.add(NamedProperty(Sbol2Terms.ComponentInstance.access, s.getAccessURI()));
			if(s.getDefinition() != null)
				list.add(NamedProperty(Sbol2Terms.ComponentInstance.hasComponentDefinition, s.getDefinition()));
			if(s.getMapsTos() != null)
			{
				List<NestedDocument<QName>> referenceList = getMapsTo(s.getMapsTos());
				for(NestedDocument<QName> n : referenceList)
				{
					list.add(NamedProperty(Sbol2Terms.ComponentInstance.hasMapsTo, n));
				}
			}
			properties.add(NamedProperty(Sbol2Terms.ComponentDefinition.hasComponent,
					NestedDocument( Sbol2Terms.Component.Component,
							s.getIdentity(), NamedProperties(list))));
		}
	}

	private static void formatGenericTopLevel (List<GenericTopLevel> topLevels, List<TopLevelDocument<QName>> topLevelDoc)
	{
		for(GenericTopLevel t : topLevels)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonTopLevelData(list, t);
			topLevelDoc.add(TopLevelDocument(t.getRdfType(), t.getIdentity(), NamedProperties(list)));
		}
	}

	private static NamedProperty<QName> getLocation(Location location)
	{
		List<NamedProperty<QName>> property = new ArrayList<NamedProperty<QName>>();
		formatCommonIdentifiedData(property, location);

		if(location instanceof Range)
		{
			Range range = (Range) location;
			property.add(NamedProperty(Sbol2Terms.Range.start, range.getStart()));
			property.add(NamedProperty(Sbol2Terms.Range.end, range.getEnd()));
			if(range.isSetOrientation())
				property.add(NamedProperty(Sbol2Terms.Range.orientation, range.getOrientationURI()));

			return NamedProperty(Sbol2Terms.Location.Location,
					NestedDocument(Sbol2Terms.Range.Range, range.getIdentity(), NamedProperties(property)));
		}
		else if(location instanceof MultiRange)
		{
			MultiRange multiRange = (MultiRange) location;
			for(Range r : multiRange.getRanges())
				property.add(NamedProperty(Sbol2Terms.MultiRange.hasRanges, r.getIdentity()));

			return NamedProperty(Sbol2Terms.Location.Location,
					NestedDocument(Sbol2Terms.MultiRange.MultiRange, multiRange.getIdentity(), NamedProperties(property)));
		}
		else if(location instanceof Cut)
		{
			Cut cut = (Cut) location;
			property.add(NamedProperty(Sbol2Terms.Cut.at, cut.getAt()));
			property.add(NamedProperty(Sbol2Terms.Cut.orientation, cut.getOrientationURI()));

			return NamedProperty(Sbol2Terms.Location.Location,
					NestedDocument(Sbol2Terms.Cut.Cut, cut.getIdentity(), NamedProperties(property)));
		}

		//TODO: This outer return should never occur. If so, ERR
		return NamedProperty(Sbol2Terms.Location.Location,
				NestedDocument(Sbol2Terms.Range.Range, location.getIdentity(), NamedProperties(property)));

	}

	private static List<NestedDocument<QName>> getMapsTo(List<MapsTo> references)
	{
		List<NestedDocument<QName>> nestedDoc = new ArrayList<NestedDocument<QName>>();

		for(MapsTo m : references)
		{
			List<NamedProperty<QName>> list = new ArrayList<NamedProperty<QName>>();
			formatCommonIdentifiedData(list, m);
			if(m.getRefinement() != null)
				list.add(NamedProperty(Sbol2Terms.MapsTo.refinement, m.getRefinement().name()));
			if(m.getRemote() != null)
				list.add(NamedProperty(Sbol2Terms.MapsTo.hasRemote, m.getRemote()));
			if(m.getLocal() != null)
				list.add(NamedProperty(Sbol2Terms.MapsTo.hasLocal, m.getLocal()));

			nestedDoc.add(NestedDocument(Sbol2Terms.MapsTo.MapsTo, m.getIdentity(), NamedProperties(list)));
		}

		return nestedDoc;
	}

	private static List<TopLevelDocument<QName>> getTopLevelDocument(SBOLDocument doc) {
		List<TopLevelDocument<QName>> topLevelDoc = new ArrayList<TopLevelDocument<QName>>();
		formatCollections(doc.getCollections(), topLevelDoc);
		formatModuleDefinitions(doc.getModuleDefinitions(), topLevelDoc);
		formatModels(doc.getModels(), topLevelDoc);
		formatComponentDefinitions(doc.getComponentDefinitions(), topLevelDoc);
		formatSequences(doc.getSequences(), topLevelDoc);
		formatGenericTopLevel(doc.getGenericTopLevels(), topLevelDoc);
		return topLevelDoc;
	}

}
