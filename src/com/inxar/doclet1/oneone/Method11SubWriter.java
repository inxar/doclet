/*
 * @(#)Method11SubWriter.java	1.10 00/02/02
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
 * Write method information in the old JDK 1.1 style.
 *
 * @author Robert Field
 */
public class Method11SubWriter extends ExecutableMemberSub11Writer {

    Method11SubWriter(Class11Writer writer) {
        super(writer);
    }

    public ProgramElementDoc[] members(ClassDoc cd) {
        return cd.methods();
    }

    protected void printReturnTag(Tag[] returns) {
        if (returns.length > 0) {
            writer.dt();
            writer.boldText("doclet.Returns");
            writer.dd();
            writer.print(returns[0].text());
        }
    }

    protected void printTags(ProgramElementDoc member) {
        MethodDoc method = (MethodDoc)member;
        ParamTag[] params = method.paramTags();
        Tag[] returns = method.tags("return");
        ThrowsTag[] thrown = method.throwsTags();
        SeeTag[] sees = method.seeTags();
        ClassDoc[] intfacs = member.containingClass().interfaces();
        ClassDoc overridden = method.overriddenClass();
        if (params.length + returns.length + thrown.length 
            + intfacs.length + sees.length > 0 ||
            overridden != null) {
            writer.dd();
            writer.dl();
            printParamTags(params);
            printReturnTag(returns);
            printThrowsTags(thrown);
            printOverridden(overridden, method);
            writer.printSeeTags(method);
            writer.dlEnd();
            writer.ddEnd();
        }
    }
                               
    protected void printSignature(ExecutableMemberDoc member) {
        displayLength = 0;
	writer.pre();
	printModifiers(member);
	printReturnType((MethodDoc)member);
	bold(member.name());
	printParameters(member);
	printExceptions(member);
	writer.preEnd();
    }
      
    protected void printReturnType(MethodDoc method) {
        Type type = method.returnType();
        if (type != null) {
            printTypeLink(type);
            print(' ');
        }
    }
    
    protected void printMethodBall(MethodDoc method, boolean small) {
        if(method.isStatic()) {
            writer.printBall("green-ball", "Green Ball", small);
        } else {
            writer.printBall("red-ball", "Red Ball", small);
        }
    }

    public void printSummaryLabel(ClassDoc cd) {
        writer.printBanner("method-index", "doclet.Method_Index", 207, 38);
    }

    protected void printSummaryType(ProgramElementDoc member) {
        printMethodBall((MethodDoc)member, true);
    }

    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
        MethodDoc method = (MethodDoc)member;
        String name = method.name();
        writer.printHyperLink("#" + name + method.signature(), name);
        writer.println(method.flatSignature());
    }

    /**
     * Reproduce 1.1 bug.  Remove soon.
     */
    protected void printOverridden(ClassDoc overridden, MethodDoc method) {
        if (overridden != null) {
            String name = method.name();
            writer.dt();
            writer.boldText("doclet.Overrides");
            writer.dd();
	    // bug: no test
	    writer.printHyperLink(overridden.qualifiedName() + ".html", 
                                  name + method.signature(), name);
            writer.print(' ');
            writer.printText("doclet.in_class");
            writer.print(' ');
            writer.printClassLink(overridden);
        }
    }
                               
    protected void printHeader(ClassDoc cd) {
        writer.anchor("methods");
        writer.printBanner("methods", "doclet.Methods", 151, 38);
    }

    protected void printFooter(ClassDoc cd) {
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        MethodDoc method = (MethodDoc)member;
        String name = method.name();
        writer.aName(name + method.signature());
        printMethodBall(method, false);
        writer.aEnd();

        writer.aName(name);
        writer.bold(name);
        writer.aEnd();

        // Get the signature,
        // generate link for each parameter if it is a class.
        printSignature(method);
        printFullComment(method);
    }
}  
    
    
