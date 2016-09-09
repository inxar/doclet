/*
 * @(#)AbstractTree11Writer.java	1.12 00/02/02
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
 * Abstract class to print the class hierarchy page for all the Classes.  
 * Sub-classes to generate the Interface Tree and Class Tree Part. 
 *
 * @author Atul M Dambalkar
 */
public class AbstractTree11Writer extends Html11Writer {

    /**
     * The class and interface tree built.
     */
    protected final ClassTree classtree;

    /**
     * Constructor.
     * 
     * @param file String filename
     */
    protected AbstractTree11Writer(String filename, ClassTree classtree)
                                      throws IOException {
        super(filename);
        this.classtree = classtree;
    }

    /**
     * Generate each line for level info regarding classes.
     * Recurses itself to generate subclasses info.
     * To iterate is human, to recurse is divine - L. Peter Deutsch.
     *
     * @param parent the superclass or superinterface of the list.
     * @param list list of the sub-classes at this level.
     */
    protected void generateLevelInfo(ClassDoc parent, List list) {
        if (list.size() > 0) {
            ul();
            for (int i = 0; i < list.size(); i++) {
                ClassDoc local = (ClassDoc)list.get(i);
                printPartialInfo(local);
                printExtendsImplements(parent, local);
                generateLevelInfo(local, classtree.subs(local));   // Recurse 
            }
            ulEnd();
        }
    }

    /**
     * Generate Tree method, to be called by the TreeWriter. Call appropriate
     * methods from the sub-classes.
     */
    protected void generateTree(List list, String heading) {
        if (list.size() > 0) {
            ClassDoc cd = (ClassDoc)list.get(0);   
            printTreeHeading(heading);
            generateLevelInfo(cd.isClass()? (ClassDoc)list.get(0): null, list);
        }
    }

    /**
     * Print the information regarding the classes which this class extends or
     * implements. 
     *
     * @param cd The classdoc under consideration.
     */
    protected void printExtendsImplements(ClassDoc parent, ClassDoc cd) {
        ClassDoc[] interfaces = cd.interfaces();
        if (interfaces.length > (cd.isInterface()? 1 : 0)) {
            Arrays.sort(interfaces);
            if (cd.isInterface()) {
                print("(" + getText("doclet.also") + " extends ");
            } else {
                print("(implements ");
            }
            boolean printcomma = false;
            for (int i = 0; i < interfaces.length; i++) {
                if (parent != interfaces[i]) {
                    if (printcomma) {
                        print(", ");
                    }
                    printPreQualifiedClassLink(interfaces[i]);
                    printcomma = true;
                }
            }
            println(")");
        }
    }

    /**
     * Print informatioon about the class kind.
     *
     * @param cd classdoc.
     */
    protected void printPartialInfo(ClassDoc cd) {
        boolean isInterface = cd.isInterface();
        li("circle");
        print(isInterface? "interface " : "class ");
        printPreQualifiedBoldClassLink(cd);
    } 

    /**
     * Print the heading for the tree depending upon if it's Interface Tree or
     * Class Tree.
     */
    protected void printTreeHeading(String heading) {
        h2();
        println(getText(heading));
        h2End();
    }

    protected void nav11LinkContents() {
        printHyperLink("packages.html", getText("doclet.All_Packages"));
        doubleSpace();
    }

    /**
     * Print link for generated index file,
     * depending upon the user option. 
     */ 
    protected void nav11LinkIndex() {
        printHyperLink("index-1.html", getText("doclet.Index"));
    }
     
    protected void nav11LinkHelp() {
    }

    protected void nav11LinkPackage() {
    }

    protected void nav11LinkPrevious() {
    }

    protected void nav11LinkNext() {
    }

    protected void nav11LinkTree() {
    }
}
