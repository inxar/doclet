/*
 * @(#)Field11SubWriter.java	1.8 00/02/02
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
public class Field11SubWriter extends Abstract11SubWriter {

    Field11SubWriter(Class11Writer writer) {
        super(writer);
    }

    public ProgramElementDoc[] members(ClassDoc cd) {
        return cd.fields();
    }

    void printSignature(MemberDoc member) {
        FieldDoc field = (FieldDoc)member;
	writer.pre();
        printModifiers(field);
        printTypeLink(field.type());
        print(' ');
        bold(field.name());
	writer.preEnd();
    }

    protected void printDeprecatedLink(ProgramElementDoc member) {
        writer.printClassLink(member.containingClass(), member.name(), 
                              ((FieldDoc)member).qualifiedName());
    }

    protected void printFieldBall(FieldDoc field, boolean small) {
        if (field.isStatic()) {
            writer.printBall("blue-ball", "Blue Ball", small);
        } else {
            writer.printBall("magenta-ball", "Magenta Ball", small);
        }
    }    

    public void printSummaryLabel(ClassDoc cd) {
        writer.printBanner("variable-index", "doclet.Variable_Index", 207, 38);
    }

    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
        String name = member.name();
        writer.printClassLink(cd, name, name);
    }
  
    protected void printSummaryType(ProgramElementDoc member) {
        printFieldBall((FieldDoc)member, true);
    }

    protected void printHeader(ClassDoc cd) {
        writer.anchor("variables");
        writer.printBanner("variables", "doclet.Fields", 153, 38);
    }

    protected void printFooter(ClassDoc cd) {
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        FieldDoc field = (FieldDoc)member;
        writer.aName(field.name());
        printFieldBall(field, false);
        writer.aEnd();
        writer.bold(field.name());

        printSignature(field);
        printFullComment(member);
    }
}  
    
    
