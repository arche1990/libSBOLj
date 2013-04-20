package org.sbolstandard.regulatory.tests;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.cidarlab.pigeon.Pigeon;
import org.cidarlab.pigeon.WeyekinPoster;
import org.sbolstandard.core.CompositeDevice;
import org.sbolstandard.core.Device;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.PrimitiveDevice;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.util.SequenceOntology;
import org.sbolstandard.regulatory.Regulation;
import org.sbolstandard.regulatory.RegulationTypes;
import org.sbolstandard.regulatory.RegulatoryFactory;

public class Tester {

	// first, we create an SBOL document
	private SBOLDocument toggleSwitchDoc;

	private final int N;
	
	public Tester(int N) {
		this.toggleSwitchDoc = SBOLFactory.createDocument();
		this.N = N;
	}
	
	private void testDnaComponent() {
		// 1 DNAComponent with N Sequence Annotations (i.e. N sub components)
		
		DnaComponent dc = SBOLFactory.createDnaComponent();
		
		for(int i=1; i<=N; i++) {
			SequenceAnnotation sa = SBOLFactory.createSequenceAnnotation();
			sa.setURI(URI.create("http://org.sbolstandard/t1/dc_sa_"+i));

			// for every annotation we have to create a sub-component
			DnaComponent subDc = SBOLFactory.createDnaComponent();
			subDc.setURI(URI.create("http://org.sbolstandard/t1/dc_sa_"+i+"/subDc"));
			subDc.setDisplayId("subdc_"+i);
			subDc.setName("subdc_"+i);
			subDc.getTypes().add(SequenceOntology.PROMOTER);
			sa.setSubComponent(subDc);
			
			dc.getAnnotations().add(sa);
		}
		
		// specify regulatory interactions
		List<Regulation> lst = new ArrayList<Regulation>();
		for(int i=0; i<N; i++) {
			Regulation reg = RegulatoryFactory.createRegulation();
			reg.setURI(URI.create("http://org.sbolstandard/t1/reg_"+(i+1)));
			if(i<N-1) {
				reg.setRegulation(dc.getAnnotations().get(i).getSubComponent(), 
					RegulationTypes.getRepressingRegulation(), 
					dc.getAnnotations().get(i+1).getSubComponent());
			} else {
				reg.setRegulation(dc.getAnnotations().get(i).getSubComponent(), 
						RegulationTypes.getRepressingRegulation(), 
						dc.getAnnotations().get(0).getSubComponent());
			}
			
			lst.add(reg);			
		}
	}
	
	
	// t2 builds a PRIMITIVE DEVICE composed of N Dna components
	private void testPrimitiveDevice() {
		
		PrimitiveDevice pd = SBOLFactory.createPrimitiveDevice();
		pd.setURI(URI.create("http://org.sbolstandard/t2/pd"));
		
		for(int i=1; i<=N; i++) {
			// create a DC
			DnaComponent dc = SBOLFactory.createDnaComponent();
			dc.setURI(URI.create("http://org.sbolstandard/t2/pd/dc_"+i));
			dc.setDisplayId("dc_"+i);
			dc.setName("dc_"+i);
			dc.getTypes().add(SequenceOntology.PROMOTER);

			
			// add the DC to the PD
			pd.getComponents().add(dc);
		}
		
		// then, let's add some regulatory interactions
		// specify regulatory interactions
		List<Regulation> lst = new ArrayList<Regulation>();
		for(int i=0; i<N; i++) {
			Regulation reg = RegulatoryFactory.createRegulation();
			reg.setURI(URI.create("http://org.sbolstandard/t2/reg_"+(i+1)));
			if(i<N-1) {
				reg.setRegulation(
						pd.getComponents().get(i), 
						RegulationTypes.getRepressingRegulation(), 
						pd.getComponents().get(i+1));
			} else {
				reg.setRegulation(
						pd.getComponents().get(i), 
						RegulationTypes.getRepressingRegulation(), 
						pd.getComponents().get(0));
			}
			
			lst.add(reg);			
		}
	}
	
	// t3 constructs a COMPOSITE DEVICE 
	// consisting of N primitive device 
	// consisting of N DNA components
	private void testCompositeDevice() {
		CompositeDevice cd = SBOLFactory.createCompositeDevice();
		cd.setURI(URI.create("http://org.sbolstandard/t3/cd"));		
		
		// the CD consists of N PDs
		for(int i=1; i<=N; i++) {
			// create a PD
			PrimitiveDevice pd = SBOLFactory.createPrimitiveDevice();
			pd.setURI(URI.create("http://org.sbolstandard/t3/cd/pd"+i));
			pd.setDisplayId("pd"+i);
			pd.setName("pd"+i);

			
			// every PD consists of N DCs
			for(int j=1; j<=N; j++) {
				// create a DC
				DnaComponent dc = SBOLFactory.createDnaComponent();
				dc.setURI(URI.create("http://org.sbolstandard/t3/cd/pd"+i+"/dc"+j));
				dc.setDisplayId("pd"+i+"/dc"+j);
				dc.setName("pd"+i+"/dc"+j);
				dc.getTypes().add(SequenceOntology.PROMOTER);
				
				// add the DC to the PD
				pd.getComponents().add(dc);
			}

			// add the PD to the CD
			cd.addDevice(pd);
		}
		
		List<Regulation> lst = new ArrayList<Regulation>();
		for(int i=0; i<N-1; i++) {
			Regulation reg = RegulatoryFactory.createRegulation();
			reg.setURI(URI.create("http://org.sbolstandard/t3/reg_"+(i+1)));
			
			reg.setRegulation(
					((PrimitiveDevice)cd.getDevices().get(i)).getComponents().get(i), 
					RegulationTypes.getRepressingRegulation(), 
					((PrimitiveDevice)cd.getDevices().get(i+1)).getComponents().get(i));
			
			lst.add(reg);
		}
	}
	

	public static void main(String[] args) {
		Tester t = new Tester(5);
		
		t.testDnaComponent();		
		t.testPrimitiveDevice();		
		t.testCompositeDevice();
	}

}
