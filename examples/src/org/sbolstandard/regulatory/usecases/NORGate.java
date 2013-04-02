package org.sbolstandard.regulatory.usecases;

import java.net.URI;

import org.cidarlab.pigeon.PigeonGenerator;
import org.cidarlab.pigeon.WeyekinPoster;
import org.sbolstandard.core.CompositeDevice;
import org.sbolstandard.core.Device;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.PrimitiveDevice;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.util.SequenceOntology;
import org.sbolstandard.regulatory.AsRegulations;
import org.sbolstandard.regulatory.Regulation;
import org.sbolstandard.regulatory.RegulationExtension;
import org.sbolstandard.regulatory.RegulationTypes;
import org.sbolstandard.regulatory.RegulatoryFactory;

public class NORGate {

	public NORGate() {
		
		// 1. build the device
		CompositeDevice device = this.build();
		
		// 2. add regulations
		AsRegulations regulations = this.addRegulations(device);
		
		// 3. visualize it
		this.visualizeNOR(
				device,
				regulations);
	}
	
	public CompositeDevice build() {
		// the NOR gate 
		// -- is a composite device		
		CompositeDevice nor = SBOLFactory.createCompositeDevice();
		
		// -- consists of four (4) primitive devices (cassettes)
		nor.addDevice(this.cassette1());		
		nor.addDevice(this.cassette2());
		nor.addDevice(this.cassette3());
		nor.addDevice(this.cassette4());

		return nor;		
	}
	
	public AsRegulations addRegulations(CompositeDevice nor) {
		
		AsRegulations asReg = RegulationExtension.getInstance().extend(nor);

		/*** REGULATORY INTERACTIONS ***/

		Regulation regulation1 = RegulatoryFactory.createRegulation();
		regulation1.setURI(URI.create("http://org.sbolstandard/nor/regulation1"));
		regulation1.setRegulation(
				((PrimitiveDevice)nor.getDevices().get(2)).getComponents().get(2),  // luxR  
				RegulationTypes.getRepressingRegulation(),                          // represses 
				((PrimitiveDevice)nor.getDevices().get(0)).getComponents().get(1)); // pLuxR   
		asReg.getRegulations().add(regulation1);
		

		Regulation regulation2 = RegulatoryFactory.createRegulation();
		regulation2.setURI(URI.create("http://org.sbolstandard/nor/regulation3"));
		regulation2.setRegulation(
				((PrimitiveDevice)nor.getDevices().get(3)).getComponents().get(2),  // lacI  
				RegulationTypes.getRepressingRegulation(),                          // represses 
				((PrimitiveDevice)nor.getDevices().get(0)).getComponents().get(0)); // pLacI   
		asReg.getRegulations().add(regulation2);

		Regulation regulation3 = RegulatoryFactory.createRegulation();
		regulation3.setURI(URI.create("http://org.sbolstandard/nor/regulation3"));
		regulation3.setRegulation(
				((PrimitiveDevice)nor.getDevices().get(0)).getComponents().get(3),  // tetR  
				RegulationTypes.getRepressingRegulation(),                          // represses 
				((PrimitiveDevice)nor.getDevices().get(1)).getComponents().get(0)); // pTetR   
		asReg.getRegulations().add(regulation3);

		return asReg;
	}
		
	// CASSETTE1: pLac + pLuxR + RBS + tetR + RBS + RFP + Terminator
	private Device cassette1() {
		PrimitiveDevice cassette = SBOLFactory.createPrimitiveDevice();
		DnaComponent pLac = SBOLFactory.createDnaComponent();
		pLac.setURI(URI.create("http://org.sbolstandard/nor/cassette1/pLac"));
		pLac.setDisplayId("pLac");
		pLac.setName("pLac");
		pLac.getTypes().add(SequenceOntology.PROMOTER);
		cassette.addComponent(pLac);

		DnaComponent pLuxR = SBOLFactory.createDnaComponent();
		pLuxR.setURI(URI.create("http://org.sbolstandard/nor/cassette1/pLuxR"));
		pLuxR.setDisplayId("pLuxR");
		pLuxR.setName("pLuxR");
		pLuxR.getTypes().add(SequenceOntology.PROMOTER);
		cassette.addComponent(pLuxR);

		DnaComponent rbs = SBOLFactory.createDnaComponent();
		rbs.setURI(URI.create("http://org.sbolstandard/nor/cassette1/RBS"));
		rbs.setDisplayId("");
		rbs.setName("");
		rbs.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		cassette.addComponent(rbs);

		DnaComponent tetR = SBOLFactory.createDnaComponent();
		tetR.setURI(URI.create("http://org.sbolstandard/nor/cassette1/tetR"));
		tetR.setDisplayId("tetR");
		tetR.setName("tetR");
		tetR.getTypes().add(SequenceOntology.CDS);
		cassette.addComponent(tetR);

		cassette.addComponent(rbs);

		DnaComponent rfp = SBOLFactory.createDnaComponent();
		rfp.setURI(URI.create("http://org.sbolstandard/nor/cassette1/rfp"));
		rfp.setDisplayId("RFP");
		rfp.setName("RFP");
		rfp.getTypes().add(SequenceOntology.CDS);
		cassette.addComponent(rfp);

		DnaComponent terminator = SBOLFactory.createDnaComponent();
		terminator.setURI(URI.create("http://org.sbolstandard/nor/cassette1/terminator"));
		terminator.setDisplayId("");
		terminator.setName("");
		terminator.getTypes().add(SequenceOntology.TERMINATOR);
		cassette.addComponent(terminator);
	
		return cassette;
	}
	
	// CASSETTE2: pTet + RBS + GFP + Terminator
	private Device cassette2() {

		PrimitiveDevice cassette = SBOLFactory.createPrimitiveDevice();

		DnaComponent pTet = SBOLFactory.createDnaComponent();
		pTet.setURI(URI.create("http://org.sbolstandard/nor/cassette2/pTet"));
		pTet.setDisplayId("pTetR");
		pTet.setName("pTetR");
		pTet.getTypes().add(SequenceOntology.PROMOTER);
		cassette.addComponent(pTet);

		DnaComponent rbs = SBOLFactory.createDnaComponent();
		rbs.setURI(URI.create("http://org.sbolstandard/nor/cassette2/rbs"));
		rbs.setDisplayId("");
		rbs.setName("");
		rbs.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		cassette.addComponent(rbs);

		DnaComponent gfp = SBOLFactory.createDnaComponent();
		gfp.setURI(URI.create("http://org.sbolstandard/nor/cassette2/gfp"));
		gfp.setDisplayId("GFP");
		gfp.setName("GFP");
		gfp.getTypes().add(SequenceOntology.CDS);
		cassette.addComponent(gfp);

		DnaComponent terminator = SBOLFactory.createDnaComponent();
		terminator.setURI(URI.create("http://org.sbolstandard/nor/cassette2/terminator"));
		terminator.setDisplayId("");
		terminator.setName("");
		terminator.getTypes().add(SequenceOntology.TERMINATOR);
		cassette.addComponent(terminator);

		return cassette;
	}
	
	// CASSETTE3: pConst1 + RBS + luxR + Terminator
	private Device cassette3() {

		PrimitiveDevice cassette = SBOLFactory.createPrimitiveDevice();
		DnaComponent pConst1 = SBOLFactory.createDnaComponent();
		pConst1.setURI(URI.create("http://org.sbolstandard/nor/cassette3/pConst1"));
		pConst1.setDisplayId("pConst1");
		pConst1.setName("pConst1");
		pConst1.getTypes().add(SequenceOntology.PROMOTER);
		cassette.addComponent(pConst1);

		DnaComponent rbs = SBOLFactory.createDnaComponent();
		rbs.setURI(URI.create("http://org.sbolstandard/nor/cassette3/rbs"));
		rbs.setDisplayId("");
		rbs.setName("");
		rbs.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		cassette.addComponent(rbs);

		DnaComponent luxR = SBOLFactory.createDnaComponent();
		luxR.setURI(URI.create("http://org.sbolstandard/nor/cassette3/luxR"));
		luxR.setDisplayId("luxR");
		luxR.setName("luxR");
		luxR.getTypes().add(SequenceOntology.CDS);
		cassette.addComponent(luxR);

		DnaComponent terminator = SBOLFactory.createDnaComponent();
		terminator.setURI(URI.create("http://org.sbolstandard/nor/cassette3/terminator"));
		terminator.setDisplayId("");
		terminator.setName("");
		terminator.getTypes().add(SequenceOntology.TERMINATOR);
		cassette.addComponent(terminator);

		return cassette;
	}

	// CASSETTE4: pConst2 + RBS + lacI + Terminator
	private Device cassette4() {

		PrimitiveDevice cassette = SBOLFactory.createPrimitiveDevice();
		DnaComponent pConst2 = SBOLFactory.createDnaComponent();
		pConst2.setURI(URI.create("http://org.sbolstandard/nor/cassette4/pConst2"));
		pConst2.setDisplayId("pConst2");
		pConst2.setName("pConst2");
		pConst2.getTypes().add(SequenceOntology.PROMOTER);
		cassette.addComponent(pConst2);

		DnaComponent rbs = SBOLFactory.createDnaComponent();
		rbs.setURI(URI.create("http://org.sbolstandard/nor/cassette4/rbs"));
		rbs.setDisplayId("");
		rbs.setName("");
		rbs.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		cassette.addComponent(rbs);

		DnaComponent lacI = SBOLFactory.createDnaComponent();
		lacI.setURI(URI.create("http://org.sbolstandard/nor/cassette4/lacI"));
		lacI.setDisplayId("lacI");
		lacI.setName("lacI");
		lacI.getTypes().add(SequenceOntology.CDS);
		cassette.addComponent(lacI);

		DnaComponent terminator = SBOLFactory.createDnaComponent();
		terminator.setURI(URI.create("http://org.sbolstandard/nor/cassette4/terminator"));
		terminator.setDisplayId("");
		terminator.setName("");
		terminator.getTypes().add(SequenceOntology.TERMINATOR);
		cassette.addComponent(terminator);

		return cassette;
	}
	
	private void visualizeNOR(Device nor, AsRegulations asReg) {

		// Visualization of the Toggle Switch and its regulatory interactions
		String NEWLINE = System.getProperty("line.separator");
		String sPigeon = PigeonGenerator.toPigeon(nor);
		sPigeon += "# Arcs"+NEWLINE;

		for(Regulation reg:asReg.getRegulations()) {
			sPigeon += reg.getLeftComponent().getName()+" "+
					toPigeonArc(reg.getRegulationType().getName())+" "+
					reg.getRightComponent().getName()+NEWLINE;
		}

		//System.out.println(sPigeon);

		WeyekinPoster.setPigeonText(sPigeon);
		WeyekinPoster.postMyBird();
	}
	
	private String toPigeonArc(String sSBOLRegulation) {
		if("REPRESSION".equals(sSBOLRegulation)) {
			return "rep";
		} else if ("INDUCTION".equals(sSBOLRegulation)) {
			return "ind";
		}
		return (String)null;
	}

	public static void main(String[] args) {
		new NORGate();
	}
}
