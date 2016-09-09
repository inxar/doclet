/*
 * @(#)PackageIndex11Writer.java	1.8 00/02/02
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
 * Write out the package index.
 *
 * @see com.sun.javadoc.PackageDoc
 * @see com.sun.tools.doclets.HtmlDocWriter
 * @author Atul M Dambalkar 
 */
public class PackageIndex11Writer extends Html11Writer {

    /**
     * Array of Packages.
     */
    protected PackageDoc[] packages;

    /**
     * Constructor.
     */
    public PackageIndex11Writer(String filename,
                                RootDoc root) throws IOException {
        super(filename);
        packages = root.specifiedPackages();
        Arrays.sort(packages);
    }

    /**
     * Generate the package index.
     *
     * @param root the root of the doc tree.
     */
    public static void generate(RootDoc root) throws DocletAbortException {
        PackageIndex11Writer packgen;

        String filename = "packages.html";
        try {
            packgen = new PackageIndex11Writer(filename, root);
            packgen.generatePackageIndexFile();
            packgen.close();
        } catch (IOException exc) {
     OneOne.configuration().oneonemessage.error("doclet.exception_encountered",
                                                 exc.toString(), filename);
            throw new DocletAbortException();
        }
    }
 
    /**
     * Generate the contants in the package index file. Call appropriate
     * methods from the sub-class in order to generate 1.1 or Frame or Non
     * Frame format.
     */ 
    protected void generatePackageIndexFile() throws IOException {
        printHeader(getText("doclet.Package_Summary")); 
        printNavigationBarHeader();

        printIndexContents("doclet.Package_Summary");

        printNavigationBarFooter();
        printFooter();
    }
    
    /**
     * Generate code for package index contents. Call appropriate methods from
     * the sub-classes.
     */
    protected void printIndexContents(String textKey) {
        if (packages.length > 0) {
            printIndexHeader(textKey);
            for(int i = 0; i < packages.length; i++) {
                PackageDoc packagedoc = packages[i];
                printIndexRow(packagedoc);
            }
            printIndexFooter();
        }
    }

    /**
     * Print the configuration title. 
     */
    protected void printConfigurationTitle() {
        if (OneOne.configuration().title.length() > 0) {
            center();
            h2();
            print(OneOne.configuration().title);
            h2End();
            centerEnd();
        }
    }

    protected void printIndexRow(PackageDoc packagedoc) {
        li();
        print(' ');
        printText("doclet.package");
        print(' ');
        bold();
        printPackageLink(packagedoc);
        boldEnd();
    }

    protected void printIndexHeader(String textKey) {
        h1();
        printImage("package-index", getText(textKey), 
                   238, 37);  
        h1End();
        menu();
    } 

    protected void printIndexFooter() {
        menuEnd();
        p();
    } 

    protected void printNavigationBarHeader() {
        nav11Links();
	hrNoShade();
        printConfigurationTitle();
    }

    protected void printNavigationBarFooter() {
        hrNoShade();
        nav11Links();
        printBottom();
    }

    /**
     * Print class/interface hierarchy link
     */
    protected void nav11LinkTree() {
        printHyperLink("tree.html", getText("doclet.Class_Hierarchy"));
        doubleSpace();
    }
                                
    /**
     * Print link for generated index file, 
     * depending upon the user option.
     */
    protected void nav11LinkIndex() {
        printHyperLink("index-1.html", getText("doclet.Index"));
        doubleSpace();
    }

    /**
     * Print help file link, considering the user options.
     */
    protected void nav11LinkHelp() {
        printHyperLink( "help.html", getText("doclet.Help"));
    }

    protected void nav11LinkContents() {
    }

    protected void nav11LinkPackage() {
    }

    protected void nav11LinkPrevious() {
    }

    protected void nav11LinkNext() {
    }
}



