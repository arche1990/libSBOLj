package org.cidarlab.pigeon;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.sbolstandard.core.CompositeDevice;
import org.sbolstandard.core.Device;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.PrimitiveDevice;
import org.sbolstandard.core.SBOLObject;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.util.SequenceOntology;
import org.sbolstandard.regulatory.AsRegulations;
import org.sbolstandard.regulatory.Regulation;

public class Pigeon {

	private static final String NEWLINE = System.getProperty("line.separator");

	public static String toPigeon(AsRegulations regulations) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(Pigeon.toPigeon(regulations.getExtended()))
			.append(toPigeonArcs(regulations.getRegulations()));

		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	public static String toPigeon(SBOLObject obj) {
		StringBuilder sb = new StringBuilder();
		if(null != obj) {
			if(obj instanceof Device) {
				sb.append(toPigeon((Device)obj));
			} else if(obj instanceof DnaComponent) {
				sb.append(toPigeon((DnaComponent)obj));
			}
		}
		sb.append("# Arcs").append(NEWLINE);
		return sb.toString();
	}
	
	
	public static void draw(String sPigeon) {
		WeyekinPoster.setPigeonText(sPigeon);
		WeyekinPoster.postMyBird();
	}
		
	private static String toPigeon(Device d) {
		if(null != d) {
			if(d instanceof CompositeDevice) {
				return toPigeon((CompositeDevice)d);
			} else if(d instanceof PrimitiveDevice) {
				return toPigeon((PrimitiveDevice)d);
			}
		}
		
		return null;
	}
	
	private static String toPigeon(CompositeDevice cd) {
		StringBuilder sPigeon = new StringBuilder();

		int i=0; 
		
		for(Device d:cd.getDevices()) {
			if(d instanceof CompositeDevice) {
				sPigeon.append(toPigeon((CompositeDevice)cd)).append(NEWLINE);				
			} else if(d instanceof PrimitiveDevice) {
				sPigeon.append(toPigeon((PrimitiveDevice)d));
			}
			
			if(++i < cd.getDevices().size()) {
				sPigeon.append(".").append(NEWLINE);
				sPigeon.append(".").append(NEWLINE);
			}
		}
		return sPigeon.toString();
	}
	
	
	private static String toPigeon(PrimitiveDevice pd) {
		StringBuilder sPigeon = new StringBuilder();
		
		for(DnaComponent dc:pd.getComponents()) {
			sPigeon.append(Pigeon.toPigeon(dc)).append(NEWLINE);
		}
		
		return sPigeon.toString();
	}
	
	private static String toPigeonArcs(List<Regulation> lst) {
		StringBuilder sPigeon = new StringBuilder();

		if(null != lst) {
			for(Regulation reg:lst) {
				sPigeon.append(reg.getLeft().getName())
					.append(" ")
					.append(toPigeonArc(reg.getRegulationType().getName()))
					.append(" ")
					.append(reg.getRight().getName())
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
		
	private static String toPigeon(DnaComponent component) {
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
				sb.append(toPigeon(sa.getSubComponent()));
				
				if(it.hasNext()) {
					sb.append(NEWLINE);
				}
			}			
		}

		return sb.toString();
	}
	
	private static String toPigeonType(URI s) {
		if(SequenceOntology.PROMOTER.equals(s)) {
			return "p";
		} else if(SequenceOntology.CDS.equals(s)) {
			return "c";
		} else if(SequenceOntology.PRIMER_BINDING_SITE.equals(s) ||
				SequenceOntology.FIVE_PRIME_UTR.equals(s)) {
			return "r";
		} else if(SequenceOntology.TERMINATOR.equals(s)) {
			return "t";
		}
		return (String)null;
	}
}
