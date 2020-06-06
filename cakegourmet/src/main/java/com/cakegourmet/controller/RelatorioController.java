package com.cakegourmet.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cakegourmet.service.IReportService;

@Controller
@RequestMapping("/cakegourmet")
public class RelatorioController {
	
	private static final Logger logger = LoggerFactory.getLogger(RelatorioController.class);
	
	@Autowired
	private IReportService reportService;
		
	@RequestMapping(value="relatorio", method=RequestMethod.GET)
	public String relatorio() {
		
	    return "relatorio";
	}
	
	@RequestMapping(value = "/relatorio", method = RequestMethod.POST)
    public void upload(HttpServletResponse response) {
		
        try {

        	StringBuffer csvRow = reportService.generateReport();
        	response.setContentType("application/csv");
			response.setHeader("content-disposition","attachment; filename = relatorio-produtos.csv");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			ServletOutputStream  writer = response.getOutputStream();
            
			writer.print(csvRow.toString());
			writer.flush();
		    writer.close();
		    		    
		} catch (Exception e) {
			String errorMessage = String.format("Erro ao exportar relatorio - repositorio. [%s]", e.getMessage());
			logger.error(errorMessage, e);
		}
	
    }
	
}
