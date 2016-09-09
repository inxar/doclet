/*
 * @(#)Html11Writer.java	1.11 00/02/02
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
import java.text.MessageFormat;


/** 
* Class for the Html Format Code Generation specific to JavaDoc.
* This Class contains methods related to the Html Code Generation which 
* are used by the Sub-Classes: PackageIndexWriter, PackageWriter, 
* ClassWriter which are Standard Doclets.
* Super-Class is HtmlWriter.
*
* @since JDK1.2
* @author Atul M Dambalkar
* @author Robert Field
*/

public abstract class Html11Writer extends HtmlDocWriter {

    /**
     * Constructor. Initializes the destination file name through1 the super
     * class HtmlWriter.A
     *
     * @param filename String file name.
     */
    public Html11Writer(String filename) throws IOException {
        super(filename);
    }

    /**
     * Print the html file header.
     * 
     * @param title String title for the generated html file.
     */
    public void printHeader(String title) {
        printPartialHeader(title);
        body();
        anchor("_top_");
        println();
    }

    /**
     * Print the user specified bottom.
     */ 
    public void printBottom() {
        if (OneOne.configuration().footer.length() > 0) {
            hrNoShade();
            print(OneOne.configuration().footer); 
        }
    } 

    /**
     * Print the html tag for image with all the parameters.
     */
    public void printImage(String imggif, String imgname, 
                             int width, int height) {
        img(imggif, imgname, width, height);
    }

    protected void nav11Links() {
        pre();
        nav11LinkContents();
        nav11LinkTree();
        nav11LinkPackage();
        nav11LinkPrevious();
        nav11LinkNext();
        nav11LinkIndex();
        nav11LinkHelp();
        preEnd();
    }

    protected abstract void nav11LinkContents() ;
    protected abstract void nav11LinkTree();
    protected abstract void nav11LinkPackage();
    protected abstract void nav11LinkPrevious();
    protected abstract void nav11LinkNext();
    protected abstract void nav11LinkIndex();
    protected abstract void nav11LinkHelp();

    public void doubleSpace() {    
        print("  ");
    }

    public void tdIndex() {
        print("<td align=right valign=top width=1%>");
    }

    /**
     * Print link for individual package file.
     */
    public void printPackageLink(PackageDoc pkg, String linkLabel) {
        printHyperLink("package-" + pkg.name() + ".html", linkLabel);
    }

    /**
     * Print link for individual package file.
     */
    public void printPackageLink(PackageDoc pkg) {
        printPackageLink(pkg, pkg.name());
    }

    /**
     * Print Class Link with the generated file name with position.
     */
    public void printClassLink(ClassDoc cd, String where, String label) {
        if (OneOne.configuration().linkall || cd.isIncluded()) {
            printHyperLink(cd.qualifiedName() + ".html", where, label);
        } else {
            print(label);
        }
    }
    
    /**
     * Print Class Link with the generated file name without position.
     */
    public void printClassLink(ClassDoc cd, String label) {
        printClassLink(cd, "", label);
    }
   
    /**
     * Print Class link.
     */ 
    public void printClassLink(ClassDoc cd) {
        printClassLink(cd, cd.isIncluded()? cd.name(): cd.qualifiedName());
    }

    /**
     * Print Class link, with tag as qualified name.
     */
    public void printQualifiedClassLink(ClassDoc cd) {
        printClassLink(cd, cd.qualifiedName());
    }

    /**
     * Print Class link, with only class name as the link and prefixing
     * plain package name.
     */
    public void printPreQualifiedClassLink(ClassDoc cd) {
        printPkgName(cd);
        printClassLink(cd, cd.name());
    }
    
    /**
     * Print Class link, with only class name as the bold link and prefixing
     * plain package name.
     */
    public void printPreQualifiedBoldClassLink(ClassDoc cd) {
        printPkgName(cd);
        printClassLink(cd, "", "<b>" + cd.name() + "</b>");
    }
   
    public void printText(String key) {
        print(OneOne.configuration().oneonemessage.getText(key));
    }

    public void printText(String key, String a1) {
        print(OneOne.configuration().oneonemessage.getText(key, a1));
    }

    public void boldText(String key) {
        bold(OneOne.configuration().oneonemessage.getText(key));
    }

    public void boldText(String key, String a1) {
        bold(OneOne.configuration().oneonemessage.getText(key, a1));
    }

    public String getText(String key) {
        return OneOne.configuration().oneonemessage.getText(key);
    }

    public void notice(String key, String a1) {
        OneOne.configuration().oneonemessage.notice(key, a1);
    }

    public void notice(String key, String a1, String a2) {
        OneOne.configuration().oneonemessage.notice(key, a1, a2);
    }

    public void error(String key, String a1) {
        OneOne.configuration().oneonemessage.notice(key, a1);
    }

    public void error(String key, String a1, String a2) {
        OneOne.configuration().oneonemessage.notice(key, a1, a2);
    }

    /**
     * Print link for any doc element.
     */
    public void printDocLink(Doc doc, String label) {
        if (doc instanceof PackageDoc) {
            printPackageLink((PackageDoc)doc, label);
        } else if (doc instanceof ClassDoc) {
            printClassLink((ClassDoc)doc, label);
        } else if (doc instanceof ExecutableMemberDoc) {
            ExecutableMemberDoc emd = (ExecutableMemberDoc)doc;
            printClassLink(emd.containingClass(), 
                           emd.name()+emd.signature(), label);
        } else if (doc instanceof MemberDoc) {
            MemberDoc md = (MemberDoc)doc;
            printClassLink(md.containingClass(), md.name(), label);
        } else if (doc instanceof RootDoc) {
            printHyperLink("packages.html", label);
        } else {
            print(label);
        }
    }

   /**
     * Print the see tags information given the doc comment.
     *
     * @param doc Doc doc
     * @see com.sun.javadoc.Doc
     */
    public void printSeeTags(Doc doc) {
       SeeTag[] sees = doc.seeTags();
       if (sees.length > 0) {
           dt();
           boldText("doclet.See_Also");
           dd();
           for (int i = 0; i < sees.length; ++i) {
               SeeTag see = sees[i];
               ClassDoc refClass = see.referencedClass();
               MemberDoc refMem = see.referencedMember();
               String refMemName = see.referencedMemberName();
               if (i > 0) {
                   print(", ");
               }
               if (refClass == null) {
                   // nothing to link to, just use text
                   print(see.text());
               } else if (refMemName == null) {
                   // class reference
                   printClassLink(refClass);
               } else if (refMem == null) {
                   // can't find the member reference
                   print(see.text());
               } else {
                   // member reference
                   printDocLink(refMem, refMemName);
               }
           }
       }
    }

    /**
     * Print tag information
     */
    public void generateTagInfo(Doc doc) {
        Tag[] sinces = doc.tags("since");
        Tag[] sees = doc.seeTags();
        Tag[] authors;
        Tag[] versions;
        if (configuration.showauthor) {
            authors = doc.tags("author");
        } else {
            authors = new Tag[0];
        }
        if (configuration.showversion) {
            versions = doc.tags("version");
        } else {
            versions = new Tag[0];
        }
        if (sinces.length > 0
            || sees.length > 0
            || authors.length > 0
            || versions.length > 0 ) {
            dl();
            if (sinces.length > 0) {
                // There is going to be only one Since tag.
                dt();
                boldText("doclet.Since");
                print(' ');
                dd();
                println(sinces[0].text());
                ddEnd();
            }
            if (versions.length > 0) {
                // There is going to be only one Version tag.
                dt();
                boldText("doclet.Version");
                print(' ');
                dd();
                println(versions[0].text());
                ddEnd();
            }
            for (int i = 0; i < authors.length; ++i) {
                dt();
                boldText("doclet.Author");
                print(' ');
                dd();
                println(authors[i].text());
                ddEnd();
            }
            printSeeTags(doc);
            dlEnd();
        }
    }

    /**
     * Return the first sentence of a string, where a sentence ends
     * with a period followed be white space.
     */
    public String firstSentence(String s) {
        if (s == null)
            return null;
        int len = s.length();
        boolean period = false;
        for (int i = 0 ; i < len ; i++) {
            switch (s.charAt(i)) {
                case '.':
                    period = true;
                    break;
                case ' ':
                case '\t':
                case '\n':
                    if (period) {
                        return s.substring(0, i);
                    }   
                    break;
                default:
                    period = false;
            }
        }
        return s;
    }
}
