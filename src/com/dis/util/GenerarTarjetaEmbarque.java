package com.dis.util;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;


import com.dis.bean.EmbarqueDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerarTarjetaEmbarque {


    public static final String RESULT = "C://Users/Diego/Desktop/checkin/prueba/Boarding_Pass.pdf";
    public static final String RESOURCE = "C://Users/Diego/Desktop/checkin/prueba/img/codigobarras.PNG";
    

    public static InputStream generarPdf(EmbarqueDTO embarqueDTO) throws DocumentException, IOException {

    	InputStream inputStream=null;
    	
	    try {
	        
	       Document document = new Document(PageSize.A4);

	        //graba pdf en memoria
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);	        
	        document.open();
	        document.add(crearTabla(embarqueDTO));
	        document.close();
  
	        inputStream = new ByteArrayInputStream(baos.toByteArray());

	        /*//graba pdf en fisico
	        //FileOutputStream fos = new FileOutputStream(RESULT);
	       // fos.write(baos.toByteArray());;
	       // fos.close();

	        // Creando un archivo temporal
	        File temp = File.createTempFile("boarding_pass", ".pdf");
	        // Borra el archivo cuando el programa termina
	        temp.deleteOnExit();
	        // Escribe en el archivo temporal
	        FileOutputStream fout = new FileOutputStream(temp);
	        fout.write(baos.toByteArray());
	        fout.close();
	        //File file = new File(RESULT);

	        //abre el archivo
			Desktop.getDesktop().open(temp);*/

	        return inputStream;
	    } catch (Exception e) {

	        e.printStackTrace();
	    }
	    
	    
	    return null;

	}
	

	public static PdfPTable crearTabla(EmbarqueDTO embarqueDTO)throws DocumentException, MalformedURLException, IOException {
	    
		Font fontCabecera = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(255, 255, 255));
		Font fontSubCab = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(255, 255, 255));
		Font fontCampos = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(51, 51, 51));
		Font fontCampos2 = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(51, 51, 51));
	    Font fontInfo = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0));
	    Font fontInfoBold = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0, 0, 0));
	    Font fontInfoBold2 = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(0, 0, 0));
	    
	    //configuracion de borde de tabla pricipal
	    int bordeTablaIzq = (Rectangle.LEFT);
	    int bordeTablaDer = (Rectangle.RIGHT);
	    int bordeTablaInf = (Rectangle.BOTTOM);
	    BaseColor colorBorde = new BaseColor(153,153,153);
	    float espesorBorde = 1f;
	    
	    //generar codigo de barras
	   
	    byte[] bufferedImageByte=null;
	    
	    try {
	    	bufferedImageByte = GenerarCodigoBarras.generarBarCode(embarqueDTO.getNumTicket());
	    } catch (Exception e) {
			e.printStackTrace();
			System.out.print("Error al generar el codigo de barras");
		}
	    
	    Image codigobarras = Image.getInstance(bufferedImageByte);

	    codigobarras.scaleToFit(150, 60);
	    codigobarras.setAlignment(Element.ALIGN_MIDDLE);
	    
	    //tabla principal
	    PdfPTable table = new PdfPTable(8);
        table.setTotalWidth(550);
        table.setLockedWidth(true);
        table.setWidths(new float[]{1,1,1,1,1,1,1,1});
	    
        PdfPCell cell;

           
	    cell = new PdfPCell(new Phrase("LLAMITA AIRLINES",fontCabecera));
	    cell.setBackgroundColor(new BaseColor(3,61,125));
	    cell.setColspan(4);
	    cell.setMinimumHeight(30f);
        cell.setPaddingLeft(10);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("TARJETA DE EMBARQUE N° "+embarqueDTO.getNumTarjetaEmbarque(),fontSubCab));
	    cell.setBackgroundColor(new BaseColor(3,61,125));
	    cell.setColspan(4);
	    cell.setMinimumHeight(30f);
        cell.setPaddingRight(10);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("NOMBRE PASAJERO",fontCampos));
	    cell.setColspan(4);
	    cell.setPaddingLeft(15);
	    cell.setPaddingTop(15);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("CLASE",fontCampos));
	    cell.setColspan(1);
	    cell.setPaddingTop(15);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(codigobarras);
	    cell.setColspan(3);
	    cell.setRowspan(3);
	    cell.setPaddingRight(12);
	    cell.setPaddingTop(23);
	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    table.addCell(cell);
	   
	    cell = new PdfPCell(new Phrase(embarqueDTO.getApellidoPaterno()+"/"+embarqueDTO.getApellidoMaterno()+"/"+embarqueDTO.getNombre(),fontInfo));
	    cell.setColspan(4);
	    cell.setPaddingLeft(15);
	    cell.setPaddingTop(2);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    table.addCell(cell);
	   
	    cell = new PdfPCell(new Phrase(embarqueDTO.getClase(),fontInfo));
	    cell.setColspan(1);
	    cell.setPaddingTop(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);

	    cell = new PdfPCell(new Phrase("N° DE TICKET",fontCampos));
	    cell.setPaddingLeft(15);
	    cell.setPaddingTop(10);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getNumTicket(),fontInfoBold));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_TOP);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);

	    cell = new PdfPCell();
	    cell.setColspan(3);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("DESDE",fontCampos));
	    cell.setColspan(4);
	    cell.setPaddingLeft(15);
	    cell.setPaddingTop(13);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("HASTA",fontCampos));
	    cell.setColspan(4);
	    cell.setPaddingTop(13);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getOrigen(),fontInfo));
	    cell.setColspan(4);
	    cell.setPaddingLeft(15);
	    cell.setPaddingTop(2);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getDestino(),fontInfo));
	    cell.setColspan(4);
	    cell.setPaddingTop(2);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("AEROPUERTO",fontCampos2));
	    cell.setPaddingLeft(15);
	    cell.setPaddingTop(2);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getOrigenAeropuerto(),fontInfoBold2));
	    cell.setPaddingTop(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("TERMINAL",fontCampos2));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getOrigenAeropuertoTerminal(),fontInfoBold2));
	    cell.setPaddingTop(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("AEROPUERTO",fontCampos2));
	    cell.setPaddingTop(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getDestinoAeropuerto(),fontInfoBold2));
	    cell.setPaddingTop(2);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("TERMINAL",fontCampos2));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBorder(Rectangle.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getDestinoAeropuertoTerminal(),fontInfoBold2));
	    cell.setPaddingTop(2);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    table.addCell(cell);
	    
	    //seteando en la ultima fila la tabla vuelo
	    cell = new PdfPCell();
	    cell.addElement(crearTablaVuelo(embarqueDTO));
	    cell.setColspan(8);
	    cell.setPaddingLeft(15);
	    cell.setPaddingRight(15);
	    cell.setPaddingTop(10);
	    cell.setPaddingBottom(15);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorder(bordeTablaIzq);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderWidthBottom(espesorBorde);
	    table.addCell(cell);
	    	    
	    return table;
	}
	
	public static PdfPTable crearTablaVuelo(EmbarqueDTO embarqueDTO)throws DocumentException, MalformedURLException, IOException {
				
	    
		Font fontGrande = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(0, 0, 0));
		Font fontSubCab = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, new BaseColor(255, 255, 255));
		Font fontCampos = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(51, 51, 51));
		Font fontCampos2 = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL, new BaseColor(51, 51, 51));
	    Font fontInfo = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0, 0, 0));
	    Font fontInfoBold = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(0, 0, 0));
	    Font fontInfoBold2 = FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD, new BaseColor(0, 0, 0));
		
	    Font fontGrandeCell = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new BaseColor(255, 255, 255));
	    Font fontCamposCell = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(255, 255, 255));
	    Font fontInfoCell = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(255, 255, 255));
	    
        //tabla vuelo
	    PdfPTable tablaVuelo = new PdfPTable(6);
	    tablaVuelo.setTotalWidth(520);
	    tablaVuelo.setLockedWidth(true);
	    tablaVuelo.setWidths(new float[]{1,1,1,1,1,1});

	    //configuracion de color de fondo
	    BaseColor colorFondo = new BaseColor(227,227,227);
	    BaseColor colorFondoCell = new BaseColor(3,61,125);
	    
	    //configuracion de borde de tabla
	    int bordeTablaSup = (Rectangle.TOP);
	    int bordeTablaIzq = (Rectangle.LEFT);
	    int bordeTablaDer = (Rectangle.RIGHT);
	    int bordeTablaInf = (Rectangle.BOTTOM);
	    BaseColor colorBorde = new BaseColor(153,153,153);
	    float espesorBorde = 1f;
	    
        PdfPCell cell;
		
	    cell = new PdfPCell(new Phrase("VUELO",fontCampos));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaIzq);
        cell.setBorder(bordeTablaSup);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
        cell.setBorderWidthTop(espesorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("HORA PRESENTACIÓN AEROPUERTO",fontCamposCell));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_TOP);
	    cell.setBackgroundColor(colorFondoCell);
        cell.setBorder(bordeTablaSup);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthTop(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("HORA PRESENTACIÓN PUERTA EMBARQUE",fontCampos));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_TOP);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaSup);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthTop(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("SALIDA",fontCampos));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaSup);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthTop(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("PUERTA",fontCampos));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaSup);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthTop(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("FILA/ASIENTO",fontCampos));
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaSup);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthTop(espesorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getVuelo(),fontInfoBold));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaIzq);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getHoraAeropuerto(),fontGrandeCell));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondoCell);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getHoraEmbarque(),fontGrande));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
		
	    cell = new PdfPCell(new Phrase(embarqueDTO.getHoraSalida(),fontInfoBold));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase("Por confirmar en aeropuerto",fontInfoBold2));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getAsiento(),fontInfoBold));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell();
	    cell.setPaddingTop(10);
	    cell.setPaddingBottom(5);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaIzq);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthLeft(espesorBorde);
        cell.setBorderWidthBottom(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getFechaSalida(),fontInfoCell));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondoCell);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthBottom(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getFechaSalida(),fontInfo));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthBottom(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell(new Phrase(embarqueDTO.getFechaSalida(),fontInfo));
	    cell.setPaddingTop(2);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthBottom(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell();
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorderWidthRight(espesorBorde);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthBottom(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
	    cell = new PdfPCell();
	    cell.setPaddingTop(10);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(colorFondo);
        cell.setBorder(bordeTablaInf);
        cell.setBorder(bordeTablaDer);
        cell.setBorderColor(colorBorde);
        cell.setBorderWidthBottom(espesorBorde);
        cell.setBorderWidthRight(espesorBorde);
	    tablaVuelo.addCell(cell);
	    
		return tablaVuelo;
	}
}
