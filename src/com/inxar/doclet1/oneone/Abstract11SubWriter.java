/*
 * @(#)Abstract11SubWriter.java	1.7 00/02/02
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
import java.lang.reflect.Modifier;

/**
 *
 * @author Robert Field
 */
public abstract class Abstract11SubWriter {

    protected final SubWriterHolder11Writer writer;

    /**
     * temp var.
     *
     * track how long the displayed (non-html) contents are.
     */
    protected int displayLength;

    Abstract11SubWriter(SubWriterHolder11Writer writer) {
        this.writer = writer;
    }

    /*** abstracts ***/

    public abstract ProgramElementDoc[] members(ClassDoc cd);

    public abstract void printSummaryLabel(ClassDoc cd);

    protected abstract void printSummaryType(ProgramElementDoc member);

    protected abstract void printSummaryLink(ClassDoc cd, ProgramElementDoc member);

    protected abstract void printHeader(ClassDoc cd);

    protected abstract void printFooter(ClassDoc cd);

    protected abstract void printMember(ClassDoc cd, ProgramElementDoc elem);

    protected abstract void printDeprecatedLink(ProgramElementDoc member);

    /***  ***/

    protected void print(String str) {
        writer.print(str);
        displayLength += str.length();
    }

    protected void print(char ch) {
        writer.print(ch);
        displayLength++;
    }

    protected void bold(String str) {
        writer.bold(str);
        displayLength += str.length();
    }

    protected void printClassLink(ClassDoc cd) {
        writer.printClassLink(cd);
        displayLength += cd.name().length();
    }

    protected void printTypeLinkNoDimension(Type type) {
        ClassDoc cd = type.asClassDoc();
	if (cd == null) {
	    print(type.typeName());
	} else {
	    printClassLink(cd);
	}
    }

    protected void printTypeLink(Type type) {
        printTypeLinkNoDimension(type);
        print(type.dimension());
    }

    /**
     * Return a string describing the access modifier flags.
     * Don't include native or synchronized.
     *
     * The modifier names are returned in canonical order, as
     * specified by <em>The Java Language Specification</em>.
     */
    protected String modifierString(MemberDoc member) {
        int ms = member.modifierSpecifier();
        int no = Modifier.NATIVE | Modifier.SYNCHRONIZED;
	return Modifier.toString(ms & ~no);
    }

    protected void printModifiers(MemberDoc member) {
        String mod;
        if (false /* 1.1 bug */) {
            mod = member.modifiers();  // includes 'native', 'synchronized'
        } else {
            mod = modifierString(member);
        }
        if(mod.length() > 0) {
            print(mod);
            print(' ');
        }
    }

    protected void printTypedName(Type type, String name) {
        if (type != null) {
            printTypeLinkNoDimension(type);
        }
        if(name.length() > 0) {
            writer.print(' ');
            writer.print(name);
        }
        if (type != null) {
            writer.print(type.dimension());
        }
    }

    protected String makeSpace(int len) {
        StringBuffer sb = new StringBuffer(len);
        for(int i = 0; i < len; i++) {
            sb.append(' ');
	}
        return sb.toString();
    }

    /**
     * Print 'static' if static and type link.
     */ 
    protected void printStaticAndType(boolean isStatic, Type type) {
        writer.printTypeSummaryHeader();
        if (isStatic) {
            print("static&nbsp;");
        }
        if (type != null) {
            printTypeLink(type); 
        }
        writer.printTypeSummaryFooter();
    }

    protected void printComment(ProgramElementDoc member) {
        String comment = member.commentText();
        if (comment.length() > 0) {
            writer.printNbsps();
            print(comment);
        }
    }

    protected void printTags(ProgramElementDoc member) {
        if (member.seeTags().length > 0) {
            writer.dd();
            writer.dl();
            writer.printSeeTags(member);
            writer.dlEnd();
            writer.ddEnd();
        }
    }

    protected String name(ProgramElementDoc member) {
        return member.name();
    }

    protected void printDeprecated(ProgramElementDoc member) {
        Tag[] deprs = member.tags("deprecated");
        if (deprs.length > 0) {
            String text = deprs[0].text();
	    writer.dd();
            writer.boldText("doclet.Note_0_is_deprecated",  name(member));
            if (text.length() > 0) {
                writer.italics(text);
            }
            writer.p();
        }
    }

    protected void printHead(MemberDoc member) {
        writer.h3();
        writer.print(member.name());
        writer.h3End();
    }

    protected void printFullComment(ProgramElementDoc member) {
        writer.dl();
        printDeprecated(member);
        printComment(member);
        printTags(member);
        writer.dlEnd();
    }

    /**
     * Forward to containing writer
     */
    public void printSummaryHeader(ClassDoc cd) {
        writer.printSummaryHeader(this, cd);
    }

    /**
     * Forward to containing writer
     */
    public void printSummaryFooter(ClassDoc cd) {
        writer.printSummaryFooter(this, cd);
    }

    /**
     * Forward to containing writer
     */
    public void printSummaryMember(ClassDoc cd, ProgramElementDoc member) {
        writer.printSummaryMember(this, cd, member);
    }

    public void printMembersSummary(ClassDoc cd) {
        ProgramElementDoc[] members = members(cd);
        if (members.length > 0) {
            Arrays.sort(members);
            printSummaryHeader(cd);
            for (int i = 0; i < members.length; ++i) {
                printSummaryMember(cd, members[i]);
            }
            printSummaryFooter(cd);
        }
    }

    public void printMembers(ClassDoc cd) {
        ProgramElementDoc[] members = members(cd);
        if (members.length > 0) {
            printHeader(cd);
            for (int i = 0; i < members.length; ++i) {
                if (i > 0) {
                    writer.printMemberHeader();
                }
                printMember(cd, members[i]);
                writer.printMemberFooter();
            }
            printFooter(cd);
        }
    }
}  
    
    
