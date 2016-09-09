/*
 * @(#)Configuration11.java	1.9 00/02/02
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
 * Configure the output based on the options.
 *
 * @author Robert Field.
 * @author Atul Dambalkar.
 */
public class Configuration11 extends Configuration {

    /**
     * Generate links to all the class references, irrespective of that class
     * being in this run or not, if -linkall option is used. 
     */
    public boolean linkall = false;
    public String footer = "";
    public String title = "";
    public boolean createindex = true;
    public boolean createtree = true;
    public boolean nodeprecated = false;

    public static MessageRetriever oneonemessage = null;
   
    public Configuration11() {
        if (oneonemessage == null) {
            oneonemessage = 
                     new MessageRetriever("com.sun.tools.javadoc.resources.oneone");
         }
    }

    public void setSpecificDocletOptions(RootDoc root) 
                                  throws DocletAbortException {
        String[][] options = root.options();
        for (int oi = 0; oi < options.length; ++oi) {
            String[] os = options[oi];
            String opt = os[0].toLowerCase();
            if (opt.equals("-footer")) {
                footer = os[1];
            } else if (opt.equals("-title")) {
                title = os[1];
            } else  if (opt.equals("-linkall")) {
                linkall = true;
            } else  if (opt.equals("-noindex")) {
                createindex = false;
            } else  if (opt.equals("-notree")) {
                createtree = false;
            } else  if (opt.equals("-nodeprecated")) {
                nodeprecated = true;
            }
        }
    }

    /**
     * Check for doclet added options here. 
     *
     * @return number of arguments to option. Zero return means
     * option not known.  Negative value means error occurred.
     */
    public int specificDocletOptionLength(String option) {
        if (option.equals("-nodeprecated") ||
            option.equals("-noindex") || 
            option.equals("-linkall") ||
            option.equals("-notree")) {
            return 1;
        } else if (option.equals("-help") ) {
            oneonemessage.notice("doclet.usage");
            return 1;
        } else if (option.equals("-x") ) {
            oneonemessage.notice("doclet.xusage");
            return -1; // so run will end
        } else if (option.equals("-footer") ||
                  (option.equals("-title"))) {
            return 2;
        } else {
            return 0;
        }
    }

    public boolean specificDocletValidOptions(String options[][],
                                              DocErrorReporter reporter) {
        return true;
    }
}

