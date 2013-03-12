package org.cidarlab.pigeon;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.sbolstandard.core.CompositeDevice;
import org.sbolstandard.core.Device;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.PrimitiveDevice;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.util.SequenceOntology;
import org.sbolstandard.regulatory.Regulation;

public class PigeonGenerator {

	private static final String NEWLINE = System.getProperty("line.separator");

	public static String toPigeon(CompositeDevice cd) {
		StringBuilder sPigeon = new StringBuilder();
		
		for(Device d:cd.getDevices()) {

			if(d instanceof CompositeDevice) {
				sPigeon.append(toPigeon((CompositeDevice)cd));
			} else if(d instanceof PrimitiveDevice) {
				sPigeon.append(toPigeon((PrimitiveDevice)d));
			}
			sPigeon.append(".").append(NEWLINE);
		}
		
		return sPigeon.toString();
	}
	
	
	public static String toPigeon(PrimitiveDevice pd) {
		StringBuilder sPigeon = new StringBuilder();
		
		for(DnaComponent dc:pd.getComponents()) {
			sPigeon.append(PigeonGenerator.toPigeon(dc))
				.append(NEWLINE);
		}


		
		return sPigeon.toString();
	}
	
	public static String toPigeonArcs(List<Regulation> lst) {
		StringBuilder sPigeon = new StringBuilder();

		sPigeon.append("# Arcs").append(NEWLINE);

		if(null != lst) {
			for(Regulation reg:lst) {
				sPigeon.append(reg.getLeftComponent().getName())
					.append(" ")
					.append(toPigeonArc(reg.getRegulationType().getName()))
					.append(" ")
					.append(reg.getRightComponent().getName())
					.append(NEWLINE);
			}
		}
		
		return sPigeon.toString(); 
	}
	
	private static String toPigeonArc(String sSBOLRegulation) {
		if("REPRESSION".equals(sSBOLRegulation)) {
			return "rep";
		} else if ("INDUCTION".equals(sSBOLRegulation)) {
			return "ind";
		}
		return (String)null;
	}
		
	public static String toPigeon(DnaComponent component) {

		if(null == component) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		if(null == component.getAnnotations() || component.getAnnotations().isEmpty()) {
			Iterator<URI> it = component.getTypes().iterator();
			URI uri = it.next();
			sb.append(toPigeonType(uri)).append(" ").append(component.getDisplayId());
		} else {
			Iterator<SequenceAnnotation> it = component.getAnnotations().iterator();
			while(it.hasNext()) {
				SequenceAnnotation sa = it.next();
				sb.append(toPigeon(sa.getSubComponent())).append(NEWLINE);
			}
			
		}
		
		return sb.toString();
	}
	
	private static String toPigeonType(URI s) {
		if(SequenceOntology.PROMOTER.equals(s)) {
			return "p";
		} else if(SequenceOntology.CDS.equals(s)) {
			return "c";
		} else if(SequenceOntology.PRIMER_BINDING_SITE.equals(s)) {
			return "r";
		} else if(SequenceOntology.TERMINATOR.equals(s)) {
			return "t";
		}
		return (String)null;
	}
}
