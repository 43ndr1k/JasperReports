package de.syntela;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsawade on 07.03.2016.
 */
public class CreateReport {
    final static Logger logger = Logger.getLogger(CreateReport.class);

    static String outputPfad = "C:\\jasperoutput\\";
    String filename = "";

    /**
     * Setzt den Pfad für die Ausgabe Datei
     * @param outputPfad
     */
    public static void setOutputPfad(String outputPfad) {
        CreateReport.outputPfad = outputPfad;
    }

    /**
     * Setzt den Filenamen der Jasper Datei
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Erzeugt aus einer JasperReports Vorlage (.jasper) und ener XML Datenquelle ein JasperPrint Element.
     * @param jasperfile
     * @param xmlfile
     * @return JasperPrint Object
     */
    public JasperPrint printFromXml(String jasperfile, String xmlfile) {
        JasperPrint print = null;
        String xmlPfad = "H:\\Bewilligungsbeispiele\\";
        String jasperPfad = "C:\\Users\\hsawade\\JaspersoftWorkspace\\Test\\";
        try {
            Map parameter = new HashMap();
            JRXmlDataSource jrds = new JRXmlDataSource(new File(xmlPfad + xmlfile), "*/*");
            Document document = JRXmlUtils.parse(new File(xmlPfad + xmlfile));
            parameter.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
            //parameter.put("parTest", "Test");
            print = JasperFillManager.fillReport(jasperPfad + jasperfile,parameter, jrds);
            logger.info("creaded JasperPrint File");
        } catch (JRException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return print;
    }

    /**
     * Erzeugt aus dem JasperPrint Element ein Pdf File
     * @param print Object
     */
    public void createPdf(JasperPrint print) {
        try {
            JasperExportManager.exportReportToPdfFile(print,outputPfad + filename + ".pdf");
            logger.info("created " + filename + ".pdf file");
        } catch (JRException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    /**
     * Erzeugt aus dem JasperPrint Element ein Xls File
      * @param print Object
     */
    public void createXls(JasperPrint print) {
        try {
            JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setExporterInput(new SimpleExporterInput(print));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPfad + filename + ".xls"));
            SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(false);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsExporter.setConfiguration(xlsReportConfiguration);
            xlsExporter.exportReport();
            logger.info("created " + filename + ".xls file");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
