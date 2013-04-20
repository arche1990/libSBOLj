package org.sbolstandard.regulatory.usecases;

import java.net.URI;

import org.cidarlab.pigeon.Pigeon;
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
		PrimitiveDevice norGate = this.buildNORGate();
		
		// 2. add regulations
		AsRegulations regulations = this.addRegulations(norGate);
		
		// 3. visualize it
		Pigeon.draw(
				Pigeon.toPigeon(regulations));
	}
	
	public PrimitiveDevice buildNORGate() {
		// the NOR gate 
		// -- is a composite device		
		PrimitiveDevice nor = SBOLFactory.createPrimitiveDevice();
		
		// -- consists of four (4) primitive devices (cassettes)
		DnaComponent pLac = SBOLFactory.createDnaComponent();
		pLac.setURI(URI.create("http://org.sbolstandard/nor/pLac"));
		pLac.setDisplayId("pLac");
		pLac.setName("pLac");
		pLac.getTypes().add(SequenceOntology.PROMOTER);
		nor.addComponent(pLac);

		DnaComponent pLuxR = SBOLFactory.createDnaComponent();
		pLuxR.setURI(URI.create("http://org.sbolstandard/nor/pLuxR"));
		pLuxR.setDisplayId("pLuxR");
		pLuxR.setName("pLuxR");
		pLuxR.getTypes().add(SequenceOntology.PROMOTER);
		nor.addComponent(pLuxR);

		DnaComponent rbs1 = SBOLFactory.createDnaComponent();
		rbs1.setURI(URI.create("http://org.sbolstandard/nor/rbs1"));
		rbs1.setDisplayId("rbs1");
		rbs1.setName("rbs1");
		rbs1.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		nor.addComponent(rbs1);

		DnaComponent tetR = SBOLFactory.createDnaComponent();
		tetR.setURI(URI.create("http://org.sbolstandard/nor/tetR"));
		tetR.setDisplayId("tetR");
		tetR.setName("tetR");
		tetR.getTypes().add(SequenceOntology.CDS);
		nor.addComponent(tetR);

		DnaComponent rbs2 = SBOLFactory.createDnaComponent();
		rbs2.setURI(URI.create("http://org.sbolstandard/nor/rbs2"));
		rbs2.setDisplayId("rbs2");
		rbs2.setName("rbs2");
		rbs2.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		nor.addComponent(rbs2);

		DnaComponent rfp = SBOLFactory.createDnaComponent();
		rfp.setURI(URI.create("http://org.sbolstandard/nor/rfp"));
		rfp.setDisplayId("RFP");
		rfp.setName("RFP");
		rfp.getTypes().add(SequenceOntology.CDS);
		nor.addComponent(rfp);

		DnaComponent t1 = SBOLFactory.createDnaComponent();
		t1.setURI(URI.create("http://org.sbolstandard/nor/t1"));
		t1.setDisplayId("T1");
		t1.setName("T1");
		t1.getTypes().add(SequenceOntology.TERMINATOR);
		nor.addComponent(t1);

		DnaComponent pTet = SBOLFactory.createDnaComponent();
		pTet.setURI(URI.create("http://org.sbolstandard/nor/pTet"));
		pTet.setDisplayId("pTetR");
		pTet.setName("pTetR");
		pTet.getTypes().add(SequenceOntology.PROMOTER);
		nor.addComponent(pTet);

		DnaComponent rbs = SBOLFactory.createDnaComponent();
		rbs.setURI(URI.create("http://org.sbolstandard/nor/rbs"));
		rbs.setDisplayId("");
		rbs.setName("");
		rbs.getTypes().add(SequenceOntology.FIVE_PRIME_UTR);
		nor.addComponent(rbs);

		DnaComponent gfp = SBOLFactory.createDnaComponent();
		gfp.setURI(URI.create("http://org.sbolstandard/nor/gfp"));
		gfp.setDisplayId("GFP");
		gfp.setName("GFP");
		gfp.getTypes().add(SequenceOntology.CDS);
		nor.addComponent(gfp);

		DnaComponent t2 = SBOLFactory.createDnaComponent();
		t2.setURI(URI.create("http://org.sbolstandard/nor/t2"));
		t2.setDisplayId("T2");
		t2.setName("T2");
		t2.getTypes().add(SequenceOntology.TERMINATOR);
		nor.addComponent(t2);

		return nor;		
	}
	
	public AsRegulations addRegulations(PrimitiveDevice pd) {
		
		AsRegulations asReg = RegulationExtension.getInstance().extend(pd);

		/*** REGULATORY INTERACTIONS ***/

		Regulation reg = RegulatoryFactory.createRegulation();
		reg.setURI(URI.create("http://org.sbolstandard/nor/regulation3"));
		reg.setRegulation(
				pd.getComponents().get(3),                    // tetR  
				RegulationTypes.getRepressingRegulation(),    // represses 
				pd.getComponents().get(7));                   // pTetR   
		asReg.getRegulations().add(reg);

		return asReg;
	}
		
	public static void main(String[] args) {
		new NORGate();
	}
}
