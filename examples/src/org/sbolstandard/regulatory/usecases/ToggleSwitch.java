package org.sbolstandard.regulatory.usecases;

import java.net.URI;

import org.cidarlab.pigeon.Pigeon;
import org.cidarlab.pigeon.WeyekinPoster;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.util.SequenceOntology;
import org.sbolstandard.regulatory.AsRegulations;
import org.sbolstandard.regulatory.Regulation;
import org.sbolstandard.regulatory.RegulationExtension;
import org.sbolstandard.regulatory.RegulationTypes;
import org.sbolstandard.regulatory.RegulatoryDevice;
import org.sbolstandard.regulatory.RegulatoryFactory;

/*
 * In this example, we create a toggle switch device
 * the device, it's components, and annotations are created
 * furthermore, we define regulatory interactions
 * finally, visualize the Toggle switch using Pigeon
 */
public class ToggleSwitch {

	public ToggleSwitch() {
		
		// 1. we create an DNA component
		DnaComponent toggleSwitch = this.buildToggleSwitch();
		
		AsRegulations regulations = addRegulations(toggleSwitch);
		
		// 2. add regulations and visualize it
		Pigeon.draw(Pigeon.toPigeon(regulations));
	}
	
	private AsRegulations addRegulations(DnaComponent toggleSwitch) {

		AsRegulations asReg = RegulationExtension.getInstance().extend(toggleSwitch);

		// REGULATORY INTERACTIONS
		// 1. Repressor1 represses Promoter1
		Regulation regulation1 = RegulatoryFactory.createRegulation();
		regulation1.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/regulation1"));
		
		// with Sequence Annotations
		//regulation1.setRegulation(saRepressor1, RegulationTypes.getRepressingRegulation(), saPromoter1);
		
		// with Dna Components
		regulation1.setRegulation(
				toggleSwitch.getAnnotations().get(3).getSubComponent(),        // repressor1 
				RegulationTypes.getRepressingRegulation(),   				   // represses
				toggleSwitch.getAnnotations().get(1).getSubComponent());       // promoter1
		asReg.getRegulations().add(regulation1);
		
		Regulation regulation2 = RegulatoryFactory.createRegulation();
		regulation2.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/regulation2"));
		regulation2.setRegulation(
				toggleSwitch.getAnnotations().get(0).getSubComponent(),        // repressor2 
				RegulationTypes.getRepressingRegulation(),   				   // represses
				toggleSwitch.getAnnotations().get(2).getSubComponent());       // promoter2
		asReg.getRegulations().add(regulation2);
		
		return asReg;
	}
	
	private DnaComponent buildToggleSwitch() {
		
		// then, we create a toggle switch DNA component
		// toggle-switch ::= repressor + promoter + promoter + repressor + reporter

		DnaComponent toggleSwitch = SBOLFactory.createDnaComponent();
		toggleSwitch.setURI(URI.create("http://org.sbolstandard/ToggleSwitch"));
		toggleSwitch.setDisplayId("Toggle Switch");
		toggleSwitch.setName("Toggle Switch");
		toggleSwitch.setDescription("Toggle Switch");

		// create the toggle switch's sequence annotations
		SequenceAnnotation saRepressor2 = SBOLFactory.createSequenceAnnotation();		
		saRepressor2.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/anot#repressor2"));
		DnaComponent repressor2 = SBOLFactory.createDnaComponent();
		repressor2.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/repressor2"));
		repressor2.setDisplayId("repressor2");
		repressor2.setName("repressor2");
		repressor2.getTypes().add(SequenceOntology.CDS);
		saRepressor2.setSubComponent(repressor2);
		toggleSwitch.getAnnotations().add(saRepressor2);

		SequenceAnnotation saPromoter1 = SBOLFactory.createSequenceAnnotation();		
		saPromoter1.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/anot#promoter1"));
		DnaComponent promoter1 = SBOLFactory.createDnaComponent();
		promoter1.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/promoter1"));
		promoter1.setDisplayId("promoter1");
		promoter1.setName("promoter1");
		promoter1.getTypes().add(SequenceOntology.PROMOTER);
		saPromoter1.setSubComponent(promoter1);
		toggleSwitch.getAnnotations().add(saPromoter1);

		SequenceAnnotation saPromoter2 = SBOLFactory.createSequenceAnnotation();		
		saPromoter2.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/anot#promoter2"));
		DnaComponent promoter2 = SBOLFactory.createDnaComponent();
		promoter2.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/promoter2"));
		promoter2.setDisplayId("promoter2");
		promoter2.setName("promoter2");
		promoter2.getTypes().add(SequenceOntology.PROMOTER);
		saPromoter2.setSubComponent(promoter2);
		toggleSwitch.getAnnotations().add(saPromoter2);

		SequenceAnnotation saRepressor1 = SBOLFactory.createSequenceAnnotation();
		saRepressor1.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/anot#repressor1"));
		DnaComponent repressor1 = SBOLFactory.createDnaComponent();
		repressor1.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/repressor1"));
		repressor1.setDisplayId("repressor1");
		repressor1.setName("repressor1");
		repressor1.getTypes().add(SequenceOntology.CDS);
		saRepressor1.setSubComponent(repressor1);
		toggleSwitch.addAnnotation(saRepressor1);

		SequenceAnnotation saReporter = SBOLFactory.createSequenceAnnotation();		
		saReporter.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/anot#reporter"));
		DnaComponent reporter = SBOLFactory.createDnaComponent();
		reporter.setURI(URI.create("http://org.sbolstandard/ToggleSwitch/reporter"));
		reporter.setDisplayId("reporter");
		reporter.setName("reporter");
		reporter.getTypes().add(SequenceOntology.CDS);
		saReporter.setSubComponent(reporter);
		toggleSwitch.getAnnotations().add(saReporter);
		
		return toggleSwitch;
	}
	
	/**
	private static String toPigeonArc(String sSBOLRegulation) {
		if("REPRESSION".equals(sSBOLRegulation)) {
			return "rep";
		} else if ("INDUCTION".equals(sSBOLRegulation)) {
			return "ind";
		}
		return (String)null;
	}
	**/
	
	public static void main(String[] args) 
			throws Exception {	
		new ToggleSwitch();
	}
	
}
