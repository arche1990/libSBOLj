package org.sbolstandard.regulatory.impl;

import org.sbolstandard.core.SBOLObject;
import org.sbolstandard.regulatory.AsRegulations;
import org.sbolstandard.regulatory.Regulation;

import java.util.List;

/**
 * Simple implementation of AsRegulations.
 *
 * @author Matthew Pocock
 */
public class AsRegulationsImpl implements AsRegulations {
    private final SBOLObject extended;
    private final List<Regulation> regulations;

    public AsRegulationsImpl(SBOLObject extended, List<Regulation> regulations) {
        this.extended = extended;
        this.regulations = regulations;
    }

    public SBOLObject getExtended() {
        return extended;
    }

    public List<Regulation> getRegulations() {
        return regulations;
    }
}
