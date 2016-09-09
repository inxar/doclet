/*
 * @(#)Class11SubWriter.java	1.7 00/02/02
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

/**
 *
 * @author Robert Field
 */
public class Class11SubWriter extends Abstract11SubWriter {

    Class11SubWriter(Class11Writer writer) {
        super(writer);
    }

    public ProgramElementDoc[] members(ClassDoc cd) {
        return cd.innerClasses();
    }

    protected void printHeader(ClassDoc cd) {
        // N.A.
    }

    protected void printFooter(ClassDoc cd) {
        // N.A.
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        // N.A.
    }

    protected void printDeprecatedLink(ProgramElementDoc member) {
        writer.printClassLink((ClassDoc)member);
    }

    protected void printInnerClassBall(ClassDoc cd, boolean small) {
        if (cd.isStatic()) {
            writer.printBall("blue-ball", "Blue Ball", small);
        } else {
            writer.printBall("magenta-ball", "Magenta Ball", small);
        }
    }    

    public void printSummaryLabel(ClassDoc cd) {
            writer.printBanner("class-index", "doclet.Class_Index", 216, 37);
    }

    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
            writer.printClassLink((ClassDoc)member);
    }
  
    protected void printSummaryType(ProgramElementDoc member) {
            printInnerClassBall((ClassDoc)member, true);
    }
}  
    
    
