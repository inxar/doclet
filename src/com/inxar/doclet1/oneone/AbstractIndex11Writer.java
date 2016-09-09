/*
 * @(#)AbstractIndex11Writer.java	1.12 00/02/02
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
 * Generate Files for all the Member Names with Indexing in 
 * Alphabetical Order. 
 *
 * @author Atul M Dambalkar
 */
public class AbstractIndex11Writer extends Html11Writer {

    /**
     * The index of all the members with unicode character.
     */
    protected IndexBuilder indexbuilder;

    /**
     * Constructor.
     */
    protected AbstractIndex11Writer(String filename, 
                             IndexBuilder indexbuilder) throws IOException {
        super(filename);
        this.indexbuilder = indexbuilder;
    }

    /**
     * Generate the member information for the unicode character along with the
     * list of the members.  
     *
     * @param unicode Unicode for which member list information to be generated.
     * @param memberlist List of members for the unicode character.
     */
    protected void generateContents(Character unicode, List memberlist) {
        anchor("_" + unicode + "_");
        h2();
        bold(unicode.toString());
        h2End();
        dl();
        for (int i = 0; i < memberlist.size(); i++) {
            Doc element = (Doc)memberlist.get(i);
            if (element instanceof MemberDoc) {
                printDescription((MemberDoc)element);
           } else if (element instanceof PackageDoc) {
                printDescription((PackageDoc)element);
            } else if (element instanceof ClassDoc) {
                printDescription((ClassDoc)element);
            }
        }
        dlEnd();
        hrNoShade();
    }


    /**
     * Print the description for the package passed.
     */
    protected void printDescription(PackageDoc pd) {
        dt();
        printPackageLink(pd);
        print(' ');
        print("package " + pd.name());
        dd();
        print(firstSentence(pd.commentText()));
    }


    /**
     * Print the description for the class passed.
     */
    protected void printDescription(ClassDoc cd) {
        dt();
        printClassLink(cd); 
        print(' ');
        printClassInfo(cd);
        dd();
        print(firstSentence(cd.commentText())); 
    }

    /**
     * What is the classkind?
     */
    protected void printClassInfo(ClassDoc cd) {
        if (cd.isOrdinaryClass()) {
            printText("doclet.class");
        } else if (cd.isInterface()) {
            printText("doclet.interface");
        } else if (cd.isException()) {
            printText("doclet.exception");
        } else {   // error
            printText("doclet.error");
        }
        print(' ');
        printPreQualifiedClassLink(cd);
        print('.');
    }


    /**
     * Generate Description for Class, Field, Method or Constructor.
     * for Java.* Packages Class Members
     *
     * @param member MemberDoc for the member of the Class Kind.
     * @see com.sun.javadoc.ClassDoc#classKind
     * @see com.sun.javadoc.MemberDoc
     */
    protected void printDescription(MemberDoc element) {
        String name = element.name();
        ClassDoc containing = element.containingClass();
        String qualname = containing.qualifiedName();
        String baseClassName = containing.name();
        dt();
        printDocLink(element, name);
        if (element instanceof ExecutableMemberDoc) {
            print(((ExecutableMemberDoc)element).flatSignature());
        }
        println('.');
        printMemberDesc(element);
        print(' ');
        printText(containing.isInterface()?  
                             "doclet.interface" :  
                             "doclet.class");
        print(" ");
        printPreQualifiedClassLink(containing);
        println();
        dd();
        print(firstSentence(element.commentText())); 
        println();
    }

    /**
     * Print description about the Static/Method/Constructor for a member.
     * 
     * @param member MemberDoc for the member within the Class Kind.
     * @see com.sun.javadoc.MemberDoc
     */
    protected void printMemberDesc(MemberDoc member) {
        if (member.isField()) {
            if (member.isStatic()) {
                printText("doclet.Static_variable_in");
            } else {
                printText("doclet.Variable_in");
            }
        } else if (member.isConstructor()) {
            printText("doclet.Constructor_for");
        } else if (member.isMethod()) {
            if (member.isStatic()) {
                printText("doclet.Static_method_in");
            } else {
                printText("doclet.Method_in");
            }
        }               
    }

    /**
     * Print class/interface hierarchy link
     */
    protected void nav11LinkTree() {
        printHyperLink("tree.html", getText("doclet.Class_Hierarchy"));
    }
                                
    /**
     * Print packages contents link
     */
    protected void nav11LinkContents() {
        printHyperLink("packages.html", getText("doclet.All_Packages"));
        doubleSpace(); 
    }
                                
    protected void nav11LinkIndex() {
    }

    protected void nav11LinkHelp() {
    }

    protected void nav11LinkPackage() {
    }

    protected void nav11LinkPrevious() {
    }

    protected void nav11LinkNext() {
    }
}
