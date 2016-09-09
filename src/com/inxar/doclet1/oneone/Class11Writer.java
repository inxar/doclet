/*
 * @(#)Class11Writer.java	1.13 00/02/02
 *
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
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
 * Generate the Class Information Page.
     * @see com.sun.javadoc.ClassDoc 
     * @see java.util.Collections
     * @see java.util.List
     * @see java.util.ArrayList
     * @see java.util.HashMap
 *
 * @author Atul M Dambalkar
 * @author Robert Field
 */
public class Class11Writer extends SubWriterHolder11Writer {

    protected ClassDoc classdoc;

    protected ClassTree classtree;

    protected String prev;

    protected String next;

    public Class11Writer(String filename, ClassDoc classdoc,
                       String prev, String next, 
                       ClassTree classtree) throws IOException {
        super(filename);
        this.classdoc = classdoc;
        this.classtree = classtree;
        this.prev = prev;
        this.next = next;
    }

    public void printClassLink(ClassDoc cd, String where, String tag) {
        if (cd == classdoc) {
            if (where == null) {
                where = "_top_";
            }
            printHyperLink("", where, tag);
        } else {
            super.printClassLink(cd, where, tag);
        }
    }
    
    /**
     * Generate a class page.
     *
     * @param prev the previous class to generated, or null if no previous.
     * @param classdoc the class to generate.
     * @param next the next class to be generated, or null if no next.
     */
    public static void generate(ClassDoc classdoc,
                                String prev, String next, 
                                ClassTree classtree) 
                                throws DocletAbortException {
            Class11Writer clsgen;
            String filename = classdoc + ".html";
            try {
                clsgen = new Class11Writer(filename, classdoc, 
                                           prev, next,
                                           classtree);
                clsgen.generateClassFile();
                clsgen.close();
            } catch (IOException exc) {
     OneOne.configuration().oneonemessage.error("doclet.exception_encountered",
                                                 exc.toString(), filename);
                throw new DocletAbortException();
            }
    }

    /**
     * Generate the class file contents.
     */
    public void generateClassFile() {
        String label = getText(classdoc.isInterface()? 
                               "doclet.Interface" : 
                               "doclet.Class") + " " +
                               classdoc.qualifiedName();
        printHeader(label);
        nav11Links();
        hrNoShade();
        h2(label);

        // if this is a class (not an interface) then generate 
        // the super class tree.
        if (!classdoc.isInterface()) {
            pre();
            printTreeForClass(classdoc);
            preEnd();
        }

        hrNoShade();
        printClassDescription();
        printDeprecated();
        // generate documentation for the class.
        String comment = classdoc.commentText();
        if (comment.length() > 0) {
            print(comment);
            p();
        }
        // Print Information about all the tags here
        generateTagInfo(classdoc);
        hrNoShade();
        p();

        anchor("index");
	
	printAllMembers();

        hrNoShade();
        nav11Links();
        printBottom();
        printFooter();
    }

    /**
     * Print summary and detail information for the specified members in the 
     * class.
     */
    protected void printAllMembers() {
        Method11SubWriter methW = new Method11SubWriter(this);
        Constructor11SubWriter consW = new Constructor11SubWriter(this);
        Field11SubWriter fieldW = new Field11SubWriter(this);
        Class11SubWriter innerW = new Class11SubWriter(this);

	innerW.printMembersSummary(classdoc);
	fieldW.printMembersSummary(classdoc);
	consW.printMembersSummary(classdoc);
	methW.printMembersSummary(classdoc);

	fieldW.printMembers(classdoc);
	consW.printMembers(classdoc);
	methW.printMembers(classdoc);
    }

    /**
     * Print the class description regarding iterfaces implemented, classes
     * inheritted.
     */
    protected void printClassDescription() {
        boolean isInterface = classdoc.isInterface();
        dl();
        dt();

        print(classdoc.modifiers() + " ");  

        if (!isInterface) {
            printText("doclet.class");
            print(' ');
        }
        bold(classdoc.name());

        if (!isInterface) {
            ClassDoc superclass = classdoc.superclass();
            if (superclass != null) {
                dt();
                printText("doclet.extends");
                print(' ');
                printClassLink(superclass);
            }
        }

        ClassDoc[] implIntfacs = classdoc.interfaces();
        if (implIntfacs != null && implIntfacs.length > 0) {
            dt();
            printText(isInterface? "doclet.extends" : "doclet.implements");
            print(' ');
            printClassLink(implIntfacs[0]);
            for (int i = 1; i < implIntfacs.length; i++) {
                print(", ");
                printClassLink(implIntfacs[i]);
            }
        }
        dlEnd();
    }
   
    /**
     * Mark the class as deprecated if it is.
     */
    protected void printDeprecated() {
        Tag[] deprs = classdoc.tags("deprecated");
        if (deprs.length > 0) {
            String text = deprs[0].text();
            boldText("doclet.Note_0_is_deprecated",  classdoc.name());
            if (text.length() > 0) {
                italics(text);
            }
            p();
        }
    }

    /**
     * Generate the step like diagram for the class hierarchy.
     */
    protected void printStep(int indent) {
        String spc = spaces(8 * indent - 4);
        print(spc);
        println("|");
        print(spc);
        print("+----");
    }

    /**
     * Print the class hierarchy tree for this class only.
     */
    protected int printTreeForClass(ClassDoc cd) {
        ClassDoc sup = cd.superclass();
        int indent = 0;
        if (sup != null) {
            indent = printTreeForClass(sup);
            printStep(indent);
        }
        if (cd.equals(classdoc)) {
            print(cd.qualifiedName());
        } else {
            printQualifiedClassLink(cd);
        }
        println();
        return indent + 1;
    }

    /**
     * Print this package link
     */
    protected void nav11LinkPackage() {
        printPackageLink(classdoc.containingPackage(), 
                         getText("doclet.This_Package"));
        doubleSpace();
    }

    /** 
     * Print packages contents link
     */ 
    protected void nav11LinkContents() {
        printHyperLink("packages.html", getText("doclet.All_Packages"));
        doubleSpace();
    }   

    /**
     * Print class/interface hierarchy link
     */
    protected void nav11LinkTree() {
        printHyperLink("tree.html", getText("doclet.Class_Hierarchy"));
        doubleSpace();
    }

    /**
     * Print link for previous file.
     *
     * @param next String previous link name
     */
    public void nav11LinkPrevious() {
        String tag = getText("doclet.Previous");
        if (prev != null) {
            printHyperLink(prev, tag) ;
        } else {
            print(tag);
        }
        doubleSpace();
    }

    /**
     * Print link for next file.
     *
     * @param next String next link name
     */
    public void nav11LinkNext() {
        String tag = getText("doclet.Next");
        if (next != null) {
            printHyperLink(next, tag) ;
        } else {
            print(tag);
        }
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
}


