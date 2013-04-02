package org.sbolstandard.core;

/**
 * Base for SBOL things that have names, display IDs and descriptions.
 *
 * @author Matthew Pocock
 */
public interface SBOLNamed {
    /**
     * Recognizable human identifier, it is often ambiguous.(eg. Mike's Arabidopsis Project A;
     * Sleight, et al. (2010) J.Bioeng; BBF RFC 10 features; Bookmarked Parts).
     * @return its name, commonly used to refer to this Collection
     */
    String getName();

    /**
     * Common name of Collection should confer what is contained in the Collection.
     *(eg. Mike's Arabidopsis Project A;
     * Sleight, et al. (2010) J.Bioeng; BBF RFC 10 features; Bookmarked Parts).
     * @param name commonly used to refer to this Collection (eg. Project A)
     */
    void setName(String name);
}
