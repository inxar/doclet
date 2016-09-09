/*
 * @(#)Package11Writer.java	1.10 00/02/02
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
 * Class to generate file for each package contents.
 *
 * @see com.sun.javadoc.PackageDoc
 * @see com.sun.tools.doclets.HtmlDocWriter
 * @author Atul M Dambalkar
 */
public class Package11Writer extends Html11Writer {

    /**
     * The package information from Doclet API.
     */
    PackageDoc packagedoc;

    /**
     * Constructor.
     */
    public Package11Writer(String filename, 
                           PackageDoc packagedoc) throws IOException {
        super(filename);
        this.packagedoc = packagedoc;
    }

    /**
     * Generate a package page.
     *
     * @param package the package to generate.
     */
    public static void generate(PackageDoc pkg) throws DocletAbortException {
        Package11Writer packgen;
        String filename = "package-" + pkg.toString() + ".html";
        try {
            packgen = new Package11Writer(filename, pkg);
            packgen.generatePackageFile();
            packgen.close();
        } catch (IOException exc) {
     OneOne.configuration().oneonemessage.error("doclet.exception_encountered",                                                 exc.toString(), filename);
            throw new DocletAbortException();
        }
    }

    /**
     * Generate Individual Package File with Class/Interface/Exceptions and
     * Error Listing with the appropriate links. File names will be e.g.
     * "package-java.io.applet.html". Calls the methods from the sub-classes.
     */
    protected void generatePackageFile() throws IOException {
        String heading = getText("doclet.Package") + " " + 
                                packagedoc.toString();
        printHeader(heading);
        nav11Links();
        hrNoShade();
        h2(heading);

        generateClassListing();

        hrNoShade();
        nav11Links();
        printBottom();
        printFooter();
    }

    /**
     * Generate the class listing for all the classes in this package.
     */
    protected void generateClassListing() {
        generateClassKindListing(packagedoc.interfaces(), 
                                 "interface-index", 257, 38, 
                                 getText("doclet.Interface_Summary"));
        generateClassKindListing(packagedoc.ordinaryClasses(), 
                                 "class-index", 216, 37, 
                                 getText("doclet.Class_Summary"));
        generateClassKindListing(packagedoc.exceptions(), 
                                 "exception-index", 284, 38, 
                                 getText("doclet.Exception_Summary"));
        generateClassKindListing(packagedoc.errors(), 
                                 "error-index", 174, 38, 
                                 getText("doclet.Error_Summary"));
    }

    /** 
     * This method is used by generateClassListing method to generate
     * the Class/Interface... Listing.
     */
    protected void generateClassKindListing(ClassDoc[] arr, String gif, 
                                            int width, int height, 
                                            String label) {
        if(arr.length > 0) {
            Arrays.sort(arr, new ClassComparator());    
            h2();
            printImage(gif, label, width, height);
            h2End();
            menu();
            for (int i = 0; i < arr.length; i++) {
                if (OneOne.configuration().nodeprecated &&
                        arr[i].tags("deprecated").length > 0) {
                    continue;
                } 
                li();
                printClassLink(arr[i]);
                println();
            }
            menuEnd();
        }
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
    }

    protected void nav11LinkContents() {
        printHyperLink("packages.html", getText("doclet.All_Packages"));
        doubleSpace();
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



