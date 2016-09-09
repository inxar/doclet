/*
 * @(#)ExecutableMemberSub11Writer.java	1.7 00/02/02
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

/**
 * Print method and constructor info.
 *
 * @author Robert Field
 */
public abstract class ExecutableMemberSub11Writer extends Abstract11SubWriter {

    ExecutableMemberSub11Writer(SubWriterHolder11Writer writer) {
        super(writer);
    }

    protected void printSignature(ExecutableMemberDoc member) {
        displayLength = 0;
	writer.pre();
	printModifiers(member);
	bold(member.name());
	printParameters(member);
	printExceptions(member);
	writer.preEnd();
    }
      
    protected void printDeprecatedLink(ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        writer.printClassLink(emd.containingClass(), 
                              emd.name() + emd.signature(), emd.qualifiedName());
    }
  
    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        String name = emd.name();
	writer.bold();
	writer.printClassLink(cd, name + emd.signature(), name);
	writer.boldEnd();
        displayLength = name.length();
	printParameters(emd);
    }
 
    protected void printParam(Parameter param) {
        printTypedName(param.type(), param.name());
    }

    protected void printParameters(ExecutableMemberDoc member) {
        String indent = "";
        print('(');
        Parameter[] params = member.parameters();
        if (params.length > 0) {
            indent = makeSpace(displayLength);
            printParam(params[0]);
        }

        for (int i = 1; i < params.length; i++) {
            writer.print(',');
            writer.print('\n');
            writer.print(indent);
            printParam(params[i]);
        }

        writer.print(')');
    }

    protected void printExceptions(ExecutableMemberDoc member) {
        ClassDoc[] except = member.thrownExceptions();
        if(except.length > 0) {
            writer.print(' ');
            writer.printText("doclet.throws");
            writer.print(' ');
            printClassLink(except[0]);

            for(int i = 1; i < except.length; i++) {
                writer.print(", ");
                printClassLink(except[i]);
            }
        }
    }

    protected void printParamTags(ParamTag[] params) {
        if (params.length > 0) {
            writer.dt();
            writer.boldText("doclet.Parameters");
            for (int i = 0; i < params.length; ++i) {
                ParamTag pt = params[i];
                writer.dd();
                writer.code();
                print(pt.parameterName());
                writer.codeEnd();
                print(" - ");
                writer.println(pt.parameterComment());
            }
        }
    }

    protected void printThrowsTags(ThrowsTag[] thrown) {
        if (thrown.length > 0) {
            writer.dt();
            writer.boldText("doclet.Throws");
            for (int i = 0; i < thrown.length; ++i) {
                ThrowsTag tt = thrown[i];
                writer.dd();
                ClassDoc cd = tt.exception();
                if (cd == null) {
                    writer.print(tt.exceptionName());
                } else {
                    printClassLink(cd);
                }
                print(" - ");
                print(tt.exceptionComment());
            }
        }
    }

    protected String name(ProgramElementDoc member) {
        return member.name() + "()";
    }

    protected void printFooter(ClassDoc cd) {
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        String name = emd.name();
        writer.anchor(name + emd.signature());

        printHead(emd);
        printSignature(emd);
        printFullComment(emd);
    }
}  
    
    
