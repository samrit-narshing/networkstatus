/*@Copyright :SiamSecure Consulting Co., Ltd.
2521/38 BizTown Soi 3,
Ladprao, Wangthonglang,
Bangkok 10310 THAILAND
Tel : (66) 2 539 5703
Fax : (66) 2 539 5704
*/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author samrit
 */
public class JasperReportModule {

    boolean forExcel = false;
    /**
     * <p>Prefix to the resource name for compiled reports.</p>
     */
//    private static final String PREFIX = "\\WEB-INF\\reports\\";
    private static final String PREFIX = "/WEB-INF/reports/";
    /**
     * <p>Suffix to the resource name for compiled reports.</p>
     */
    private static final String SUFFIX = ".jasper";
    private static final String[] VALID_TYPES = {"text/html", // Standard HTML representation
        "application/pdf", "application/rtf", "generate_html", "application/vnd.ms-excel"// Adobe Portable Document Format
    };

    public void jasperReport(String name, String type, JRBeanCollectionDataSource ds, Map params, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String reportName) {

        // Validate that we recognize the report type
        // before potentially wasting time filling the
        // report with data
        boolean found = false;
        for (String VALID_TYPES1 : VALID_TYPES) {
            if (VALID_TYPES1.equals(type)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Invalid report type '" + type + "' requested");
        }

        // Look up the compiled report design resource


        ServletContext econtext = httpServletRequest.getSession().getServletContext();


        InputStream stream = econtext.getResourceAsStream(PREFIX + name + SUFFIX);
        if (stream == null) {
            throw new IllegalArgumentException("Unknown report name '" + name + "' requested");
        }


        JasperPrint jasperPrint = null;
        try {

            jasperPrint = JasperFillManager.fillReport(stream, params, ds);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }

        // Configure the exporter to be used, along with the custom
        // parameters specific to the exporter type
        JRExporter exporter = null;
        JRXlsExporter xlsExporter = null;
        HttpServletResponse response = httpServletResponse;

        //response.setHeader("Content-Disposition", "attachment; filename=" + "Generated Report");



        try {
            response.setContentType(type);
            if ("application/pdf".equals(type)) {
                response.setHeader("Content-disposition", "attachment; filename=\"S-CHILD_"+reportName+"_" + DateConverter.getCurrentDate() + ".pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                        jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                        response.getOutputStream());
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, PREFIX + name + SUFFIX);
                Map fontMap = new HashMap();
                exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                exporter.exportReport();

            } else if ("application/rtf".equals(type)) {
                response.setHeader("Content-disposition", "attachment; filename=\"S-CHILD_"+reportName+"_" + DateConverter.getCurrentDate() + ".rtf\"");
                exporter = new JRRtfExporter();
                exporter.setParameters(params);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                exporter.exportReport();

            } else if ("application/vnd.ms-excel".equals(type)) {
                forExcel = true;
                response.setHeader("Content-Disposition", "attachment;filename=" + "S-CHILD_"+reportName+"_" + DateConverter.getCurrentDate()+ ".xls");
                xlsExporter = new JRXlsExporter();
                xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                xlsExporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
                xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                xlsExporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);
                xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                xlsExporter.exportReport();
                
                
              
//                    JRXlsExporter exporterXLS = new JRXlsExporter();
//                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//                    exporterXLS.exportReport();
            
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
                response.flushBuffer();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
            }
        }

        // Tell JavaServer Faces that no output is required

    }
}
