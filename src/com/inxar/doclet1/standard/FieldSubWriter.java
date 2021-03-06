/*
 * @(#)FieldSubWriter.java	1.22 00/02/02
 *
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.inxar.doclet1.standard;

import java.util.*;
import com.inxar.doclet1.*;
import com.sun.javadoc.*;

/**
 *
 * @author Robert Field
 * @author Atul M Dambalkar
 */
public class FieldSubWriter extends AbstractSubWriter {

    public FieldSubWriter(SubWriterHolderWriter writer, ClassDoc classdoc) {
        super(writer, classdoc);
    }

    public FieldSubWriter(SubWriterHolderWriter writer) {
        super(writer);
    }

    public int getMemberKind() {
        return VisibleMemberMap.FIELDS;
    }

    public void printSummaryLabel(ClassDoc cd) {
        writer.boldText("doclet.Field_Summary"); 
    }

    public void printClassSummaryLabel(ClassDoc cd) { 
        writer.boldText("doclet.Class_Field_Summary");  
    } 
 
    public void printInstanceSummaryLabel(ClassDoc cd) { 
        writer.boldText("doclet.Instance_Field_Summary");  
    } 
 
    public void printSummaryAnchor(ClassDoc cd) {
        writer.anchor("field_summary");
    }

    public void printInheritedSummaryAnchor(ClassDoc cd) {
        writer.anchor("fields_inherited_from_class_" + cd.qualifiedName());
    }

    public void printInheritedSummaryLabel(ClassDoc cd) {
        String classlink = writer.getPreQualifiedClassLink(cd);
        writer.bold();
        writer.printText(cd.isClass()? 
                            "doclet.Fields_Inherited_From_Class":
                            "doclet.Fields_Inherited_From_Interface", 
                         classlink);
        writer.boldEnd();
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

    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
        String name = member.name();
        writer.bold();
	writer.code();
        writer.printClassLink(cd, name, name, false);
	writer.codeEnd();
        writer.boldEnd();
    }
  
    protected void printInheritedSummaryLink(ClassDoc cd, 
                                             ProgramElementDoc member) {
        String name = member.name();
        writer.printClassLink(cd, name, name, false);
    }

    protected void printSummaryType(ProgramElementDoc member) {
        FieldDoc field = (FieldDoc)member;
        printModifierAndType(field, field.type());
    }

    protected void printHeader(ClassDoc cd) {
          writer.anchor("field_detail");
          writer.printTableHeadingBackground(writer.
                                                getText("doclet.Field_Detail"));

//          writer.anchor("field_detail");
//          writer.bold(writer.getText("doclet.Field_Detail"));

    }

    protected void printBodyHtmlEnd(ClassDoc cd) {
    }

    protected void printMember(ProgramElementDoc member) {
        FieldDoc field = (FieldDoc)member;
        writer.anchor(field.name());
        printHead(field);
        printSignature(field);
        printFullComment(field);
    }

    protected void printDeprecatedLink(ProgramElementDoc member) {
        writer.printClassLink(member.containingClass(), member.name(), 
                              ((FieldDoc)member).qualifiedName());
    }

    protected void printNavSummaryLink(ClassDoc cd, boolean link) {
        if (link) {
            writer.printHyperLink("", (cd == null)?
                                          "field_summary":
                                          "fields_inherited_from_class_" +
                                           cd.qualifiedName(),
                                  writer.getText("doclet.navField"));
        } else {               
            writer.printText("doclet.navField");
        }
    }

    protected void printNavDetailLink(boolean link) {
        if (link) {
            writer.printHyperLink("", "field_detail",
                                  writer.getText("doclet.navField"));
        } else {
            writer.printText("doclet.navField");
        } 
    }

    public void printConstantMembersSummary() {
        List members = new LinkedList(members(classdoc));
	ListIterator iterator = members.listIterator();
	while (iterator.hasNext()) {
	    FieldDoc member = (FieldDoc)iterator.next();
	    if (member.isStatic() && member.isFinal())
		continue;
	    else
		iterator.remove();
	}

        if (members.size() > 0) {
            printSummaryHeader("doclet.Constant_Summary", classdoc);
            Collections.sort(members);
            for (int i = 0; i < members.size(); ++i) {
                printSummaryMember(classdoc, 
                                   (ProgramElementDoc)(members.get(i)));
            }   
            printSummaryFooter(classdoc);
        }
    }

    public void printClassMembersSummary() {
        List members = new LinkedList(members(classdoc));
	ListIterator iterator = members.listIterator();
	while (iterator.hasNext()) {
	    FieldDoc member = (FieldDoc)iterator.next();
	    if (!member.isStatic() || member.isFinal())
		iterator.remove();
	}

        if (members.size() > 0) {
            printSummaryHeader("doclet.Class_Field_Summary", classdoc);
            Collections.sort(members);
            for (int i = 0; i < members.size(); ++i) {
                printSummaryMember(classdoc, 
                                   (ProgramElementDoc)(members.get(i)));
            }   
            printSummaryFooter(classdoc);
        }
    }

    public void printInstanceMembersSummary() {
        List members = new LinkedList(members(classdoc));
	ListIterator iterator = members.listIterator();
	while (iterator.hasNext()) {
	    FieldDoc member = (FieldDoc)iterator.next();
	    if (member.isStatic())
		iterator.remove();
	}

        if (members.size() > 0) {
            printSummaryHeader("doclet.Instance_Field_Summary", classdoc);
            Collections.sort(members);
            for (int i = 0; i < members.size(); ++i) {
                printSummaryMember(classdoc, 
                                   (ProgramElementDoc)(members.get(i)));
            }   
            printSummaryFooter(classdoc);
        }
    }


}  
    
    
