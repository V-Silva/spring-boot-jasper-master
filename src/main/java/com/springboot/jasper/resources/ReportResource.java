package com.springboot.jasper.resources;

import com.springboot.jasper.enums.ExportReportType;
import com.springboot.jasper.model.Car;
import com.springboot.jasper.model.Report;
import com.springboot.jasper.services.ReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;


import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Description(value = "Jasper Report Resource Handler")
@RestController
@RequestMapping("/api/report")
public class ReportResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportResource.class);

    private ReportService reportService;
    
    @Autowired
    private ApplicationContext appContext;


    /**
     * Constructor dependency injector.
     * @param reportService - report service dependency
     */
    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }


    /**
     * Endpoint for generating simple PDF report
     *
     * @param report - report data
     * @return byte array resource (generated file in bytes)
     */
    @PostMapping(value = "/pdf", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> generateSimplePDFReport(@Valid @RequestBody Report report)
    {
        LOGGER.info("Payload for generating simple PDF report: {}", report);
        try
        {
            ByteArrayResource byteArrayResource = reportService.generateSimpleReport(report, ExportReportType.PDF);
            return new ResponseEntity<>(byteArrayResource, HttpStatus.OK);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for generating simple DOCx report
     *
     * @param report - report data
     * @return byte array resource (generated file in bytes)
     */
    @PostMapping(value = "/docx", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> generateSimpleDOCxReport(@Valid @RequestBody Report report)
    {
        LOGGER.info("Payload for generating simple DOCx report: {}", report);
        try
        {
            ByteArrayResource byteArrayResource = reportService.generateSimpleReport(report, ExportReportType.DOCX);
            return new ResponseEntity<>(byteArrayResource, HttpStatus.OK);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for generating report with table data source
     *
     * @param reportDataSource - report data source
     * @return byte array resource (generated file in bytes)
     */
    @PostMapping(value = "/data-source", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByteArrayResource> generateDataSourceReport(@Valid @RequestBody List<Report> reportDataSource)
    {
        LOGGER.info("Payload for generating report with data source: {}", reportDataSource);
        try
        {
            ByteArrayResource byteArrayResource = reportService.generateDataSourceReport(
                    reportDataSource, ExportReportType.PDF
            );
            return new ResponseEntity<>(byteArrayResource, HttpStatus.OK);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public void generateDataSourceReports()
    {
        	    String report = "src/main/resources/report2.xml";
                JasperReport jreport;
				try {
					jreport = JasperCompileManager.compileReport(report);
					Car i20 = new Car(new Long(1),"i20",90000);
	        	    Car creta = new Car(new Long(2),"Create",185000);
	        	    Car rapid = new Car(new Long(3),"rapid",115000);
	        	    List<Car> carsList = new ArrayList<Car>();
	        	    carsList.add(i20);
	        	    carsList.add(creta);
	        	    carsList.add(rapid);
	                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(carsList);
	                HashMap params = new HashMap();
	                JasperPrint jprint = JasperFillManager.fillReport(jreport, params, ds);
	                JasperExportManager.exportReportToPdfFile(jprint,
	                        "src/main/resources/report2.pdf");
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
    }
}
