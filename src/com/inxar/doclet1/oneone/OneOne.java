/*
 * @(#)OneOne.java	1.10 00/02/02
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
import java.util.*;
import java.io.*;

/**
 * The class with "start" method, calls individual Writers.
 *
 * @author Atul M Dambalkar
 * @author Robert Field
 */
public class OneOne {

    /**
     * The "start" method as required by Javadoc.
     *
     * @param Root
     * @see com.sun.javadoc.Root
     * @return boolean
     */
    public static boolean start(RootDoc root) throws IOException {
        try { 
            configuration().setOptions(root);

            (new OneOne()).startGeneration(root);
        } catch (DocletAbortException exc) {
            return false; // message has already been displayed
        }
        return true;
    }
        
    /**
     * Return the configuration instance. Create if it doesn't exist.
     * Override this method to use a different
     * configuration.
     */
    public static Configuration11 configuration() {
        if (HtmlDocWriter.configuration == null) {
            HtmlDocWriter.configuration = new Configuration11();
        }
        return (Configuration11)HtmlDocWriter.configuration;
    }

    /**
     * Start the generation of files. Call generate methods in the individual
     * writers, which will in turn genrate the documentation files. Call the
     * TreeWriter generation first to ensure the Class Hierarchy is built
     * first and then can be used in the later generation.
     *
     * For old 1.1 format.
     *
     * @see com.sun.javadoc.Root
     */
    protected void startGeneration(RootDoc root) throws DocletAbortException {
        boolean nodeprecated = configuration().nodeprecated;
        ClassTree classtree = new ClassTree(root, nodeprecated);
        IndexBuilder indexbuilder = new IndexBuilder(root, nodeprecated);
        PackageDoc[] packages = root.specifiedPackages();
        Arrays.sort(packages);

        if (configuration().createtree) {
            Tree11Writer.generate(classtree, root);
        }

        if (configuration().createindex) {
            SplitIndex11Writer.generate(indexbuilder);
        }

        if (packages.length > 0) {
            PackageIndex11Writer.generate(root);
        }

        for(int i = 0; i < packages.length; i++) {
            Package11Writer.generate(packages[i]);
        }

        generateClassFiles(root, classtree);
       
    }

    protected void generateClassFiles(RootDoc root, ClassTree classtree) 
                                      throws DocletAbortException {
        ClassDoc[] classes = root.specifiedClasses();
        List incl = new ArrayList();
        for (int i = 0; i < classes.length; i++) {
            ClassDoc cd = classes[i];
            if (cd.isIncluded()) {
                incl.add(cd);
            }
        }
        ClassDoc[] inclClasses = new ClassDoc[incl.size()];
        for (int i = 0; i < inclClasses.length; i++) {
            inclClasses[i] = (ClassDoc)incl.get(i);
        }
        generateClassCycle(inclClasses, classtree);
        PackageDoc[] packages = root.specifiedPackages();
        for (int i = 0; i < packages.length; i++) {
            PackageDoc pkg = packages[i];
            generateClassCycle(pkg.interfaces(), classtree);
            generateClassCycle(pkg.ordinaryClasses(), classtree);
            generateClassCycle(pkg.exceptions(), classtree);
            generateClassCycle(pkg.errors(), classtree);
        }
    }

    protected String classFileName(ClassDoc cd) {
        return cd.qualifiedName() + ".html";
    }

    /**
     * Instantiate ClassWriter for each Class within the ClassDoc[]
     * passed to it and generate Documentation for that.
     */
    protected void generateClassCycle(ClassDoc[] arr, ClassTree classtree) 
                                      throws DocletAbortException {
        Arrays.sort(arr, new ClassComparator());
        for(int i = 0; i < arr.length; i++) {
            String prev = (i == 0)? 
                          null:
                          classFileName(arr[i-1]);
            ClassDoc curr = arr[i];
            String next = (i+1 == arr.length)? 
                          null:
                          classFileName(arr[i+1]);

            Class11Writer.generate(curr, prev, next, classtree);
        }
    }

    /**
     * Check for doclet added options here. 
     *
     * @return number of arguments to option. Zero return means
     * option not known.  Negative value means error occurred.
     */
    public static int optionLength(String option) {
        return configuration().optionLength(option);
    }

    /**
     * Check that options have the correct arguments here. 
     * <P>
     * Printing option related error messages (using the provided
     * DocErrorReporter) is the responsibility of this method.
     *
     * @return true if the options are valid.
     */
    public static boolean validOptions(String options[][], 
                                       DocErrorReporter reporter) {
        return configuration().validOptions(options, reporter);
    }
}
        

