package org.sbolstandard.regulatory.usecases;

import java.net.URI;

import org.cidarlab.pigeon.Pigeon;
import org.sbolstandard.core.CompositeDevice;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.PrimitiveDevice;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.util.SequenceOntology;
import org.sbolstandard.regulatory.AsRegulations;
import org.sbolstandard.regulatory.Regulation;
import org.sbolstandard.regulatory.RegulationExtension;
import org.sbolstandard.regulatory.RegulationTypes;
import org.sbolstandard.regulatory.RegulatoryFactory;

public class Repressilator {

	public Repressilator() {
	
		// in this example, 
		// a repressilator is a Composite device
		// consisting of two primitive devices
		// i.e. Repressilator and Reporter
		
		// first, create the composite device
		CompositeDevice repressilator = this.buildRepressilatorDevice();

		// second, we add regulatory interactions to the repressilator device
		AsRegulations regulations = this.addRegulations(repressilator);
		
		// finally, we visualize the repressilator
		Pigeon.draw(
			Pigeon.toPigeon(regulations));
	}
	
	private CompositeDevice buildRepressilatorDevice() {
		CompositeDevice repressilator = SBOLFactory.createCompositeDevice();
		
		//PrimitiveDevice repressilator = SBOLFactory.createPrimitiveDevice();
		repressilator.setURI(URI.create("http://sbolstandard.org/repressilator"));
		repressilator.setDisplayId("repressilator");
		repressilator.setName("repressilator");

		repressilator.getDevices().add(this.createRepressilatorDevice());
		repressilator.getDevices().add(this.createReporterDevice());
		
		return repressilator;
	}
	
	public PrimitiveDevice createRepressilatorDevice() {
		PrimitiveDevice repressilator = SBOLFactory.createPrimitiveDevice();
		repressilator.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator"));
		repressilator.setDisplayId("repressilator");
		repressilator.setName("repressilator");
		
		/** ANNOTATIONS **/

	    // pCI 
	    DnaComponent pCI = SBOLFactory.createDnaComponent();
	    pCI.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator/pCI"));
	    pCI.setDisplayId("pCI-1");
	    pCI.setName("pCI-1");
	    pCI.getTypes().add(SequenceOntology.PROMOTER);

	    repressilator.getComponents().add(pCI);
	    
	    // lacI
	    DnaComponent lacI = SBOLFactory.createDnaComponent();
	    lacI.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator/lacI"));
	    lacI.setDisplayId("lacI");
	    lacI.setName("lacI");
	    lacI.getTypes().add(SequenceOntology.CDS);

	    repressilator.getComponents().add(lacI);

		// pLacI
		DnaComponent pLacI = SBOLFactory.createDnaComponent();
		pLacI.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator/pLac4"));
		pLacI.setDisplayId("pLacI");
		pLacI.setName("pLacI");
		pLacI.getTypes().add(SequenceOntology.PROMOTER);

	    repressilator.getComponents().add(pLacI);
		
		// tetR
	    DnaComponent tetR = SBOLFactory.createDnaComponent();
	    tetR.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator/tetR"));
	    tetR.setDisplayId("tetR");
	    tetR.setName("tetR");
	    tetR.getTypes().add(SequenceOntology.CDS);

	    repressilator.getComponents().add(tetR);

	    // pTetR
		DnaComponent pTetR = SBOLFactory.createDnaComponent();
		pTetR.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator/pTetR"));
		pTetR.setDisplayId("pTetR");
		pTetR.setName("pTetR");
		pTetR.getTypes().add(SequenceOntology.PROMOTER);
		
	    repressilator.getComponents().add(pTetR);

	    // CI
	    DnaComponent CI = SBOLFactory.createDnaComponent();
	    CI.setURI(URI.create("http://sbolstandard.org/repressilator/repressilator/CI"));
	    CI.setDisplayId("CI");
	    CI.setName("CI");
	    CI.getTypes().add(SequenceOntology.CDS);

	    repressilator.getComponents().add(CI);

	    return repressilator;
	}
	
	public PrimitiveDevice createReporterDevice() {
		PrimitiveDevice reporter = SBOLFactory.createPrimitiveDevice();
		reporter.setURI(URI.create("http://sbolstandard.org/repressilator/reporter"));
		reporter.setDisplayId("reporter");
		reporter.setName("reporter");


		/** PROMOTER **/
	    DnaComponent pCI = SBOLFactory.createDnaComponent();
	    pCI.setURI(URI.create("http://sbolstandard.org/repressilator/reporter/pCI"));
	    pCI.setDisplayId("pCI-2");
	    pCI.setName("pCI-2");
	    pCI.getTypes().add(SequenceOntology.PROMOTER);
		
		reporter.getComponents().add(pCI);
	    
		/** GFP (REPORTER) **/
	    DnaComponent GFP = SBOLFactory.createDnaComponent();
	    GFP.setURI(URI.create("http://sbolstandard.org/repressilator/reporter/GFP"));
	    GFP.setDisplayId("GFP");
	    GFP.setName("GFP");
	    GFP.getTypes().add(SequenceOntology.CDS);

		reporter.getComponents().add(GFP);

	    return reporter;
	}

	public AsRegulations addRegulations(CompositeDevice cd) {
		
		AsRegulations regulations = RegulationExtension.getInstance().extend(cd);
		
		/*** REGULATORY INTERACTIONS ***/
		Regulation reg1 = RegulatoryFactory.createRegulation();
		reg1.setURI(URI.create("http://org.sbolstandard/repressilator/reg1"));
		reg1.setRegulation(
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(1),  	    // lacI  
				RegulationTypes.getRepressingRegulation(),              				// represses 
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(2)); 	    // pLacI   		
		regulations.getRegulations().add(reg1);


		Regulation reg2 = RegulatoryFactory.createRegulation();
		reg2.setURI(URI.create("http://org.sbolstandard/repressilator/reg2"));
		reg2.setRegulation(
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(3),    	// tetR  
				RegulationTypes.getRepressingRegulation(),              				// represses 
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(4)); 	    // pTetR   		
		regulations.getRegulations().add(reg2);

		Regulation reg3 = RegulatoryFactory.createRegulation();
		reg3.setURI(URI.create("http://org.sbolstandard/repressilator/reg3"));
		reg3.setRegulation(
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(5),  	    // CI  
				RegulationTypes.getRepressingRegulation(),              				// represses 
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(0)); 	    // pCI   		
		regulations.getRegulations().add(reg3);

		Regulation reg4 = RegulatoryFactory.createRegulation();
		reg4.setURI(URI.create("http://org.sbolstandard/repressilator/reg4"));
		reg4.setRegulation(
				((PrimitiveDevice)cd.getDevices().get(0)).getComponents().get(5),  	    // CI  
				RegulationTypes.getRepressingRegulation(),              				// represses 
				((PrimitiveDevice)cd.getDevices().get(1)).getComponents().get(0)); 	    // pCI  
																						// (of the reporter component)	
		regulations.getRegulations().add(reg4);
		
		return regulations;					
	}	
	
	public static void main(String[] args) {
		new Repressilator();		
	}
}
