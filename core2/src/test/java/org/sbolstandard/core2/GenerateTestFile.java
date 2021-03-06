package org.sbolstandard.core2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

public class GenerateTestFile
{

	public static void main(String[] args)
	{
		SBOLDocument document = new SBOLDocument();

		document.addNamespaceBinding(URI.create("http://myannotation.org"), "annot");
		document.addNamespaceBinding(URI.create("urn:bbn.com:tasbe:grn"), "grn");

		writeRdfFile(document, "sampleToggleSwitch.rdf");

	}

	public static void writeRdfFile(SBOLDocument document, String fileName)
	{
		try {
			SBOLWriter.writeRDF(document, new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
