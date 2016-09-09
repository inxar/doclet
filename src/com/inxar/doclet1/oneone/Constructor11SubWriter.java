/*
 * @(#)Constructor11SubWriter.java	1.10 00/02/02
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
public class Constructor11SubWriter extends ExecutableMemberSub11Writer {

    Constructor11SubWriter(Class11Writer writer) {
        super(writer);
    }

    public ProgramElementDoc[] members(ClassDoc cd) {
        return cd.constructors();
    }

    protected void printTags(ProgramElementDoc member) {
        ParamTag[] params = ((ConstructorDoc)member).paramTags();
        ThrowsTag[] thrown = ((ConstructorDoc)member).throwsTags();
        SeeTag[] sees = member.seeTags();
        if (params.length + thrown.length + sees.length > 0) {
            writer.dd();
            writer.dl();
            printParamTags(params);
            printThrowsTags(thrown);
            writer.printSeeTags(member);
            writer.dlEnd();
            writer.ddEnd();
        }
    }
                               
    protected void printConstructorBall(ConstructorDoc cons, boolean small) {
        writer.printBall("yellow-ball", "Yellow Ball", small);
    }
 
    public void printSummaryLabel(ClassDoc cd) {
        writer.printBanner("constructor-index", "doclet.Constructor_Index", 275, 38);
    }

    protected void printHeader(ClassDoc cd) {
        writer.anchor("constructors");
        writer.printBanner("constructors", "doclet.Constructors", 231, 38);
    }

    protected void printFooter(ClassDoc cd) {
    }

    protected void printSummaryType(ProgramElementDoc member) {
        printConstructorBall((ConstructorDoc)member, true);
    }

    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
        ConstructorDoc cons = (ConstructorDoc)member;
        String name = cons.name();
        writer.printHyperLink("#" + name + cons.signature(), name);
        writer.println(cons.flatSignature());
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        ConstructorDoc cons = (ConstructorDoc)member;
        String name = cons.name();
        writer.aName(name + cons.signature());
        printConstructorBall(cons, false);
        writer.aEnd();

        writer.bold(name);

        // Get the signature,
        // generate link for each parameter if it is a class.
        printSignature(cons);
        printFullComment(cons);
    }
}  
    
    
