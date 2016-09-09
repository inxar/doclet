/*
 * @(#)SubWriterHolderWriter.java	1.21 00/02/02
 *
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.inxar.doclet1.standard;

import com.inxar.doclet1.*;
import com.sun.javadoc.*;
import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * This abstract class exists to provide functionality needed in the
 * the formatting of member information.  Since AbstractSubWriter and its
 * subclasses control this, they would be the logical place to put this.
 * However, because each member type has its own subclass, subclassing
 * can not be used effectively to change formatting.  The concrete
 * class subclass of this class can be subclassed to change formatting.
 *
 * @see AbstractSubWriter
 * @see ClassWriter
 *
 * @author Robert Field
 * @author Atul M Dambalkar
 */
public abstract class SubWriterHolderWriter extends HtmlStandardWriter {
    
    public SubWriterHolderWriter(String filename) throws IOException {
        super(filename);
    }


    public SubWriterHolderWriter(String path, String filename, String relpath) 
                                 throws IOException, DocletAbortException {
        super(path, filename, relpath);
    }

    public void printTypeSummaryHeader() {
        tdIndex();
        font("-1");
        code();
    }

    public void printTypeSummaryFooter() {
        codeEnd();
        fontEnd();
        tdEnd();
    }

    public void printSummaryHeader(AbstractSubWriter mw, ClassDoc cd) {
        mw.printSummaryAnchor(cd);
	tableSpecialStart();
        tableIndexSummary();
        tableHeaderStart("#CCCCFF");
	print("<FONT CLASS=\"TableHeaderFont\"");
        mw.printSummaryLabel(cd);
	fontEnd();
        tableHeaderEnd();
    }

    public void printSummaryHeader(AbstractSubWriter mw, String resource, ClassDoc cd) {
        mw.printSummaryAnchor(cd);
	tableSpecialStart();
        tableIndexSummary();
        tableHeaderStart("#CCCCFF");
	print("<FONT CLASS=\"TableHeaderFont\"");
	boldText(resource);
	fontEnd();
        //mw.printSummaryLabel(cd);
        tableHeaderEnd();
    }

    public void printTableHeadingBackground(String str) {
	tableSpecialStart();
        tableIndexDetail();
        tableHeaderStart("#CCCCFF", 1);
	print("<FONT CLASS=\"TableHeaderFont\"");
        bold(str);
	fontEnd();
        tableHeaderEnd();
        tableEnd();
	tableSpecialEnd();
    }
 
    public void printInheritedSummaryHeader(AbstractSubWriter mw, ClassDoc cd) {
        mw.printInheritedSummaryAnchor(cd);
	tableSpecialStart();
        tableIndexSummary();
        tableInheritedHeaderStart("#EEEEFF");
	print("<FONT CLASS=\"TableHeaderFont\"");
        mw.printInheritedSummaryLabel(cd);
	fontEnd();
        tableInheritedHeaderEnd();
	tableSpecialEnd();
        trBgcolorStyle("white", "TableRowColor");
        summaryRow(0);
        code();
    }

    public void printSummaryFooter(AbstractSubWriter mw, ClassDoc cd) {
        tableEnd();
	tableSpecialEnd();
        space();
    }

    public void printInheritedSummaryFooter(AbstractSubWriter mw, ClassDoc cd) {
        codeEnd();
        summaryRowEnd();
        trEnd(); 
        tableEnd();
        space();
    }

    protected void printCommentDef(Doc member) {
        printNbsps();
        printIndexComment(member); 
    }

    protected void printIndexComment(Doc member) {
        Tag[] deprs = member.tags("deprecated");
        boolean deprecated = false;
        if (deprs.length > 0) {
            boldText("doclet.Deprecated"); space();
            printInlineDeprecatedComment(deprs[0]);
            return;
        } else {
            ClassDoc cd = ((ProgramElementDoc)member).containingClass();
            if (cd != null && cd.tags("deprecated").length > 0) {
                boldText("doclet.Deprecated"); space();
            } 
        }
        printSummaryComment(member);
    }

    public void printSummaryMember(AbstractSubWriter mw, ClassDoc cd, 
                                   ProgramElementDoc member) {
        printSummaryLinkType(mw, member);
        /*if (cd == null) {
            cd = member.containingClass();
        }*/
        mw.printSummaryLink(cd, member);
        printSummaryLinkComment(mw, member);     
    }

    public void printSummaryLinkType(AbstractSubWriter mw, 
                                     ProgramElementDoc member) {
        trBgcolorStyle("white", "TableRowColor");
        mw.printSummaryType(member);
	if (member.isConstructor())
	    summaryRowConstructor();
	else
	    summaryRow(0);
        code();
    }

    /**
     * Print the summary table row cell attribute width.
     *
     * @param width Width of the table cell.
     */
    public void summaryRowConstructor() {
	print("<TD WIDTH=\"100%\" COLSPAN=\"2\"");
    } 

    public void printSummaryLinkComment(AbstractSubWriter mw,
                                        ProgramElementDoc member) {
        codeEnd();
        println(); br();
        printCommentDef(member);
        summaryRowEnd();
        trEnd(); 
    }

    public void printInheritedSummaryMember(AbstractSubWriter mw, ClassDoc cd, 
                                            ProgramElementDoc member) {
        mw.printInheritedSummaryLink(cd, member);
    }

    public void printMemberHeader() {
        hrNoShade();
    }

    public void printMemberFooter() {
    }

    public void printMemberHeader2() {
        hrNoShade();
    }

    public void printMemberFooter2() {
    }

}




