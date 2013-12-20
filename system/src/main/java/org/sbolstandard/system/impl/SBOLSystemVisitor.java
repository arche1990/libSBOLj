package org.sbolstandard.system.impl;

import org.sbolstandard.core.Collection;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLVisitable;
import org.sbolstandard.core.SBOLVisitor;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.impl.SBOLVisitableImpl;
import org.sbolstandard.system.Context;
import org.sbolstandard.system.Device;
import org.sbolstandard.system.Model;
import org.sbolstandard.system.SBOLSystem;

public interface SBOLSystemVisitor<T extends Throwable> extends SBOLVisitor<T> {	 
//public interface SBOLSystemVisitorextends SBOLVisitor<Throwable> {
	public void visit(SBOLSystem system) throws T;
     
    public void visit(Context context) throws T;

	public void visit(Model model) throws T;
	
	public void visit(Device device) throws T;
}

/*
public interface SBOLVisitor<T extends Throwable> {
	public void visit(SBOLDocument doc) throws T;

	public void visit(Collection coll) throws T;

	public void visit(DnaComponent component) throws T;

	public void visit(DnaSequence sequence) throws T;

	public void visit(SequenceAnnotation annotation) throws T;
	*/
