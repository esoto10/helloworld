package com.dis.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;


import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import net.sourceforge.jbarcodebean.BarcodeException;
import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code128;

public class GenerarCodigoBarras {

	public static byte[] generarBarCode(String numTicket) throws IOException, BarcodeException{
				
		   JBarcodeBean barcode = new JBarcodeBean();

		    // nuestro tipo de codigo de barra
		    //barcode.setCodeType(new Interleaved25());
		    barcode.setCodeType(new Code128());

		    // nuestro valor a codificar y algunas configuraciones mas
		    barcode.setCode(numTicket);
		    barcode.setCheckDigit(true);
		    
		    //guarda en memoria
		    BufferedImage bufferedImage = barcode.draw(new BufferedImage(150, 60, BufferedImage.TYPE_INT_RGB));
		    
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ImageIO.write(bufferedImage, "png", baos);
		    baos.flush();
		    byte[] imageInByte = baos.toByteArray();
		    baos.close();
		    
		    // guardar en disco como png
		    //File file = new File("C://codebar.png");
		    //ImageIO.write(bufferedImage, "png", file);
		    
		    return imageInByte;
	}
	
}



