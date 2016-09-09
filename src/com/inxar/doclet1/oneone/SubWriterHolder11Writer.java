/*
 * @(#)SubWriterHolder11Writer.java	1.7 00/02/02
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
 */
public abstract class SubWriterHolder11Writer extends Html11Writer {

    public SubWriterHolder11Writer(String filename) throws IOException {
        super(filename);
    }

    public void printTypeSummaryHeader() {
        tdIndex();
        font("-1");
    }

    public void printTypeSummaryFooter() {
        fontEnd();
        tdEnd();
    }

    protected void printCommentDef(Doc member) {
        printNbsps();
        printIndexComment(member); 
    }

    protected void printIndexComment(Doc member) {
        Tag[] deprs = member.tags("deprecated");
        String comment = firstSentence(member.commentText());
        if (comment == "") {
            comment = "&nbsp;";
        }
        println(comment);
        if (deprs.length > 0) {
            boldText("doclet.Deprecated");
        }
    }

    public void printBall(String ball, String alt, boolean small) {
        int size = 12;
        if (small) {
            size = 6;
            ball = ball + "-small";
        }
        printImage(ball, " o ", size, size);
    }

    public void printBanner(String gif, String nameKey, int width, int height) {
        h2();
        printImage(gif, getText(nameKey), width, height);
        h2End();
    }

    public void printSummaryHeader(Abstract11SubWriter mw, ClassDoc cd) {
        mw.printSummaryLabel(cd);  // icon
        dl();
    }

    public void printSummaryFooter(Abstract11SubWriter mw, ClassDoc cd) {
        dlEnd();
    }

    public void printSummaryMember(Abstract11SubWriter mw, ClassDoc cd, 
                                   ProgramElementDoc member) {
        dt();
        mw.printSummaryType(member);  // ball
        mw.printSummaryLink(cd, member);  // link & sig
        dd();
        printIndexComment(member);
    }

    public void printMemberHeader() {
    }

    public void printMemberFooter() {
    }
}




