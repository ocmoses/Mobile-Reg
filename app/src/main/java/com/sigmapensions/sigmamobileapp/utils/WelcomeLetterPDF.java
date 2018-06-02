package com.sigmapensions.sigmamobileapp.utils;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sigmapensions.sigmamobileapp.R;

public class WelcomeLetterPDF {
	private Font normalFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
	private Font boldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
	private Document welcomeLetter;
	private Context context;
	private FileOutputStream fos;
	private Image companyLogo;
	private String contributorAccount;
	private String contributorFileID;
	private String contributorName;
	private String contributorPhone;
	private String contributorEmail;
	private Image signature;
	private Image passport;
	private String rsaPin;
	private PdfWriter writer;


	public WelcomeLetterPDF(Context context, FileOutputStream fos, Image companyLogo, Image signature, Image passport, 
			String contributorAccount, String contributorFileID, String contributorName, String contributorPhone, String contributorEmail, String rsaPin){
		this.context = context;
		this.fos = fos;
		this.companyLogo = companyLogo;
		this.contributorAccount = contributorAccount;
		this.contributorFileID = contributorFileID;
		this.contributorName = contributorName;
		this.contributorPhone = contributorPhone;
		this.contributorEmail = contributorEmail;
		this.signature = signature;
		this.passport = passport;
		this.rsaPin = rsaPin;
	}

	public void generateWelcomeLetter(){
		welcomeLetter = new Document();
		try {
			writer = PdfWriter.getInstance(welcomeLetter, fos);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		welcomeLetter.open();
		setDocumentMargin(welcomeLetter, 5);
		addMetaData(welcomeLetter);
		addContent(welcomeLetter);
		welcomeLetter.close();
	}
	private void setDocumentMargin(Document document, int i) {
		document.left(5);
		document.top(5);
		document.right(5);
		document.bottom(5);
	}

	private void addContent(Document document) {
		Chunk chunk = new Chunk();
		Phrase phrase = new Phrase();
		PdfPTable table = new PdfPTable(2);
		PdfPCell cell = new PdfPCell();


		try {

			//initialize company logo
			companyLogo.scaleAbsolute(100, 40);
			chunk = new Chunk(companyLogo, 0, 0, true);
			phrase = new Phrase(chunk);
			cell = new PdfPCell(phrase);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			table.setWidthPercentage(100);
			table.setWidths(new int[]{4, 3});
			table.addCell(cell);

			//now add the right address

			chunk = new Chunk(context.getResources().getString(R.string.bold_sigma) + "\n\n", boldFont);
			phrase = new Phrase();
			phrase.add(chunk);

			chunk = new Chunk(context.getResources().getString(R.string.top_right_address), normalFont);
			phrase.add(chunk);
			cell = new PdfPCell(phrase);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);

			document.add(table);
			document.add(new Paragraph("\n\n"));

			//			//now to add the date
			chunk = new Chunk(new SimpleDateFormat("dd MMMM, yyyy").format(new Date()) + "\n", normalFont);
			document.add(chunk);

			//to add the name
			chunk = new Chunk(contributorName + "\n", boldFont);
			document.add(chunk);

			//now to add tel no
			chunk = new Chunk(context.getResources().getString(R.string.tel_no) + "       ", boldFont);
			document.add(chunk);
			chunk = new Chunk(contributorPhone, normalFont);
			document.add(chunk);

			//Now for the table below tel no
			table = new PdfPTable(2);
			phrase = new Phrase(context.getResources().getString(R.string.second_table_left_cell), normalFont);
			cell = new PdfPCell(phrase);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			chunk = new Chunk(context.getResources().getString(R.string.file_id_no) + contributorFileID + "\n\n", boldFont);
			phrase = new Phrase();
			phrase.add(chunk);
			chunk = new Chunk(context.getResources().getString(R.string.second_table_right_cell), normalFont);
			phrase.add(chunk);
			cell = new PdfPCell(phrase);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			table.setWidthPercentage(100f);

			document.add(table);

			//to add email
			chunk = new Chunk(context.getResources().getString(R.string.contributor_email), boldFont);
			document.add(chunk);
			chunk = new Chunk(contributorEmail + "       \n", normalFont);
			document.add(chunk);
			
			//now say hello
			chunk = new Chunk("Dear " + contributorName + ",\n\n", boldFont);
			document.add(chunk);
			
			//Add the body (first section)
			phrase = new Phrase(context.getResources().getString(R.string.first_body_section), normalFont);
			document.add(phrase);
			
			//Next, the table of passport and pin
			table = new PdfPTable(2);
			table.setWidthPercentage(100f);
			passport.scaleAbsolute(50, 75);
			chunk = new Chunk(passport, 100, 0, true);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			phrase = new Phrase("\n\n\n\nAccount No: " + contributorAccount + "\n\nRSA PIN: " + rsaPin, boldFont);
			cell = new PdfPCell(phrase);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Rectangle.ALIGN_CENTER);
			table.addCell(cell);
			
			document.add(table);
			
			//Next, the second part of the body
			phrase = new Phrase(context.getResources().getString(R.string.second_body_section), normalFont);
			document.add(phrase);
			
			//Now for the bank details
			table = new PdfPTable(2);
			table.setWidths(new int[]{1, 2});
			cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.bank_table_left_cell), normalFont));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(context.getResources().getString(R.string.bank_table_right_cell), normalFont));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			document.add(table);
			
			//Now to add the final body
			phrase = new Phrase(context.getResources().getString(R.string.last_body_section) + "\n\n", normalFont);
			document.add(phrase);
			
			//This is where the signature goes
			signature.scaleAbsolute(60, 25);
			chunk = new Chunk(signature, 0, 0, true);
			
			document.add(new Phrase(chunk));
			
			//This is where name of the signee goes
			phrase = new Phrase("\n\n" + context.getResources().getString(R.string.signee), boldFont);
			document.add(phrase);
			

		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}


	private void addMetaData(Document document) {
		document.addTitle("Welcome Letter PDF");
		document.addAuthor("Sigma Pensions Limited.");
		document.addCreationDate();
	}

}
