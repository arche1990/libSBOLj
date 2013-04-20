package org.sbolstandard.regulatory;

import org.sbolstandard.core.SBOLCoreObject;
import org.sbolstandard.core.extension.ExtensionProvider;
import org.sbolstandard.regulatory.impl.AsRegulationsImpl;

import java.util.ArrayList;

/**
 * Regulations associated with an SBOL Core object.
 *
 * @author Matthew Pocock
 */
public class RegulationExtension 
		implements ExtensionProvider<SBOLCoreObject> {
    // get an instance of this extension, implemented using a private Holder class to solve thread-safety and lazy
    // issues
    public static RegulationExtension getInstance() {
        return Holder.instance;
    }

    // the Holder class that stores our actual instance
    private static class Holder {
        private static RegulationExtension instance = new RegulationExtension();
    }

    @Override
    public AsRegulations extend(final SBOLCoreObject obj) {
        // todo: replace stub with something that tracks regulations associated with the device
        return new AsRegulationsImpl(obj, new ArrayList<Regulation>());
    }
}
