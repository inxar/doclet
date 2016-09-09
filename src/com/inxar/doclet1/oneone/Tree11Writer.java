/*
 * @(#)Tree11Writer.java	1.10 00/02/02
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
import java.lang.*;
import java.util.*;

/**
 * Generate Class Hierarchy page for all the Classes.  Use ClassTree for
 * getting the Tree.
 *
 * @see java.util.HashMap
 * @see java.util.List
 * @see com.sun.javadoc.Type
 * @see com.sun.javadoc.ClassDoc
 * @author Atul M Dambalkar
 */
public class Tree11Writer extends AbstractTree11Writer {

    private PackageDoc[] packages;

    /**
     * Constructor.
     * 
     * @param file String filename
     */
    public Tree11Writer(String filename, ClassTree classtree, RootDoc root)  
                                               throws IOException {
        super(filename, classtree);
        packages = root.specifiedPackages();
        Arrays.sort(packages);
    }

    /** 
     * Static method to be called by the Standard doclet.
     */ 
    public static void generate(ClassTree classtree, RootDoc root) 
                                throws DocletAbortException {
        Tree11Writer treegen;
        String filename = "tree.html";
        try {
            treegen = new Tree11Writer(filename, classtree, root); 
            treegen.generateTreeFile();
            treegen.close();
        } catch (IOException exc) {
     OneOne.configuration().oneonemessage.error("doclet.exception_encountered",
                                                 exc.toString(), filename);
            throw new DocletAbortException();
        }
    }

    /**
     * Generate the Tree File Contents.
     */
    public void generateTreeFile() throws IOException {
        printHeader(getText("doclet.Class_Hierarchy"));
        printTreeHeader();

        generateTree(classtree.baseclasses(), "doclet.Class_Hierarchy"); 
        generateTree(classtree.baseinterfaces(), "doclet.Interface_Hierarchy"); 

        printTreeFooter();
    }

    /**
     * Print the navigation bar links at the top.
     */
    protected void printTreeHeader() {
        nav11Links();
        hrNoShade();
    } 

    /**
     * Print the navigation bar links at the bottom.
     */
    protected void printTreeFooter() {
        hrNoShade(); 
        nav11Links();
        printBottom();
        printFooter();
    } 
}
