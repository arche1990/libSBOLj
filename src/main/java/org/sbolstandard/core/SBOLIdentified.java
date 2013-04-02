package org.sbolstandard.core;

public interface SBOLIdentified {
    /**
     * Identifier to display to users.
     * @return a human readable identifier
     */
    String getDisplayId();

    /**
     * Identifier to display to users.
     * @param displayId a human readable identifier
     */
    void setDisplayId(String displayId);
}
