package com.dark.hat.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import com.dark.hat.app.models.entity.Factura;
import com.dark.hat.app.models.entity.ItemFactura;

import lombok.extern.log4j.Log4j2;

//factura/ver.xlsx con el .xlsx diferencia que se trata de 
//otro componente pero que se va a trabajar con excel
@Log4j2
@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("[FacturaXlsxView][inicio]");
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		
		MessageSourceAccessor mensaje = getMessageSourceAccessor();
		
		Factura factura = (Factura)model.get("factura");
		Sheet sheet = workbook.createSheet("Factura Spring");
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(mensaje.getMessage("text.factura.ver.datos.cliente"));
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre().concat(" ").concat(factura.getCliente().getApellido()));
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(mensaje.getMessage("text.factura.ver.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(mensaje.getMessage("text.cliente.factura.folio").concat(" :").concat(factura.getId().toString()));
		sheet.createRow(6).createCell(0).setCellValue(mensaje.getMessage("text.cliente.factura.descripcion").concat(" :").concat(factura.getDescripcion()));
		sheet.createRow(7).createCell(0).setCellValue(mensaje.getMessage("text.cliente.factura.fecha").concat(" :").concat(factura.getCreateAt().toString()));
		
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(mensaje.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(mensaje.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(mensaje.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(mensaje.getMessage("text.factura.form.item.total"));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int rownum=10;
		for (ItemFactura item : factura.getItems()) {
			Row fila = sheet.createRow(rownum++);
			
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);

			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tbodyStyle);
		}
		
		Row filaTotal = sheet.createRow(rownum);
		
		cell = filaTotal.createCell(2);
		cell.setCellValue(mensaje.getMessage("text.factura.form.total"));
		cell.setCellStyle(tbodyStyle);

		cell = filaTotal.createCell(3);
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tbodyStyle);
	}

}
