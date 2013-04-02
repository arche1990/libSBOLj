package org.sbolstandard.core;

public interface SBOLDescribed {
	
    /**
     * Text which is for users to read and interpret what this Collection is.
     * (eg "Collecting parts which could be used to build honey production directly into
     * mouse-ear cress"; "T9002 and I7101 variants from Sleight 2010, designs aim to
     * improve stability over evolutionary time"; "features useful when working with
     * BBF RFC 10"; "Totally sick Parts I found browsing the web -- SBOL rules
     * -- organize these later").
     *
     * Informative description which allows human users to interpret the likely
     * members of this Collection.
     * @return Human readable text describing the Collection
     */
    String getDescription();

    /**
     * Text with an informative statement about the reason for grouping the Collection members.
     * (eg "Collecting parts which could be used to build honey production directly into
     * mouse-ear cress"; "T9002 and I7101 variants from Sleight 2010, designs aim to
     * improve stability over evolutionary time"; "features useful when working with
     * BBF RFC 10"; "Totally sick Parts I found browsing the web -- SBOL rules
     * -- organize these later").
     *
     * @param description Human readable text describing the Collection
     */
    void setDescription(String description);

}
