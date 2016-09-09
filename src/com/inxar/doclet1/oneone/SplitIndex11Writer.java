/*
 * @(#)SplitIndex11Writer.java	1.7 00/02/02
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.inxar.doclet1.oneone;

import com.sun.javadoc.*;
import com.inxar.doclet1.*;
import java.io.*;
import java.util.*;

/**
 * Generate Files for all the Member Names with Indexing in 
 * Alphabetical Order. 
 *
 * @author Atul M Dambalkar
 */
public class SplitIndex11Writer extends AbstractIndex11Writer {

    public SplitIndex11Writer(String filename, 
                             IndexBuilder indexbuilder) throws IOException {
        super(filename, indexbuilder);
    }

    /**
     * Generate a single index file, listing all the members.
     *
     */
    public static void generate(IndexBuilder indexbuilder) 
                                throws DocletAbortException {
        SplitIndex11Writer indexgen;
        String filename = "";
        try {
            for (int i = 0; i < indexbuilder.elements().length; i++) {
                int j = i + 1;
                filename = "index-" + j +".html";
                indexgen = new SplitIndex11Writer(filename, indexbuilder);
                indexgen.generateIndexFile((Character)indexbuilder.elements()[i]);
                indexgen.close();
            }
        } catch (IOException exc) {
     OneOne.configuration().oneonemessage.error("doclet.exception_encountered",
                                                 exc.toString(), filename);
            throw new DocletAbortException();
        }
    }

    /**
     * Generate the contents of each index file, with Header, Footer, 
     * Member Field, Method and Constructor Description.
     *
     * @param str String referring to the alphabet for the index.
     */
    protected void generateIndexFile(Character unicode) throws IOException {
        printHeader(unicode + "-" + getText("doclet.Index"));
       
        nav11Links();
        hrNoShade();
        printLinksForIndexes();
        
        hrNoShade();
        
        generateContents(unicode, indexbuilder.getMemberList(unicode));

        printLinksForIndexes();
        hrNoShade();
        nav11Links();
        
        printBottom(); 
        printFooter();
    }

    /**
     * Print Links for all the Index Files per alphabet.
     */
    protected void printLinksForIndexes() {
        for (int i = 0; i < indexbuilder.elements().length; i++) {
            int j = i + 1;
            printHyperLink("index-" + j + ".html",
                           indexbuilder.elements()[i].toString());
            print(' ');
        }
    }
}
