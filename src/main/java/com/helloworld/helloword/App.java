package com.helloworld.helloword;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Hello world!
 *
 */
public class App {
	public static final String DEST = "D:\\java Submission\\Work\\helloword\\blank.pdf";

	public static void main(String[] args) throws IOException {

    	print();
	}
	
	public static void print() throws  IOException {
		File file = new File("D:/java Submission/Work/helloword/What is Java.pdf");
		PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false, false);
        PDFont pdfFont = PDType1Font.HELVETICA;
        float fontSize = 10;
        float leading = 1.5f * fontSize;
        PDRectangle mediabox = page.getMediaBox();
        float margin = 72;
        float width = mediabox.getWidth() - 2*margin;
        float startX = mediabox.getLowerLeftX() + margin;
        float startY = mediabox.getUpperRightY() - margin - pdfFont.getBoundingBox().getUpperRightY() * fontSize / 1000 ;
        String text = "I am trying to create a PDF file with a lot of text contents in the document. I am using PDFBox.I am trying to create a PDF file with a lot of text contents in the document. I am trying to create a PDF file with a lot of text contents in the document.I am trying to create a PDF file with a lot of text contents in the document. I am using PDFBox.I am trying to create a PDF file with a lot of text contents in the document. I am trying to create a PDF file with a lot of text contents in the document."; 
        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;
        while (text.length() > 0)
        {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
            System.out.printf("'%s' - %f of %f\n", subString, size, width);
            if (size > width)
            {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                System.out.printf("'%s' is line\n", subString);
                lastSpace = -1;
            }
            else if (spaceIndex == text.length())
            {
                lines.add(text);
                System.out.printf("'%s' is line\n", text);
                text = "";
            }
            else
            {
                lastSpace = spaceIndex;
            }
        }
        
        contentStream.beginText();
        contentStream.setFont(pdfFont, fontSize);
        contentStream.newLineAtOffset(startX, startY);
        int linecount=0;
        for (String line: lines)
        {
        	linecount++;
            float charSpacing = 0;
            if (line.length() > 1)
            {
                float size = fontSize * pdfFont.getStringWidth(line) / 1000;
                float free = width - size;
                if (free > 0)
                {
                    charSpacing = free / (line.length() - 1);
                    System.out.println(lines.size());
                }
            }
            contentStream.setCharacterSpacing(charSpacing);
//            contentStream.appendRawCommands("0 Tc\n");
            if(linecount==lines.size()) {
            	 contentStream.setCharacterSpacing(0);
            }
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -leading);
        }
        contentStream.endText(); 
        contentStream.close();

        doc.save(new File("D:/java Submission/Work/helloword/new.pdf"));
		
	doc.close();
	}
	

}
