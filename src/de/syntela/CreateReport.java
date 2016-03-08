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
 * Diese Klasse behandelt das erstellen der Reports in verschiedene Formate.
 */
public class CreateReport {
    final static Logger logger = Logger.getLogger(CreateReport.class);

    /**
     * Pfad wo die Output Daten gepeichert werden sollen.
     */
    static String outputPfad = "C:\\jasperoutput\\";
    String filename = "";

    /**
     * Setzt den Pfad für die Ausgabe Datei.
     * @param outputPfad Ausgabepfad
     */
    public static void setOutputPfad(String outputPfad) {
        CreateReport.outputPfad = outputPfad;
    }

    /**
     * Setzt den Filenamen der Jasper Datei.
     * @param filename Filename der Jasper Datei
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Erzeugt aus einer JasperReport Vorlage (.jasper) und einer XML Datenquelle ein JasperPrint Objekt.
     * @param jasperfile Jasper Report File.
     * @param xmlfile XML Quelldatei.
     * @return JasperPrint Object
     */
    public JasperPrint printFromXml(String jasperfile, String xmlfile) {
        JasperPrint print = null;
        String xmlPfad = "H:\\Bewilligungsbeispiele\\";
        String jasperPfad = "C:\\Users\\hsawade\\JaspersoftWorkspace\\Test\\";
        try {
            Map<String, Object> parameter = new HashMap<>();
            File file = new File(xmlPfad + xmlfile);
            JRXmlDataSource jrds = new JRXmlDataSource(file, "*/*");
            Document document = JRXmlUtils.parse(file);
            parameter.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
            //parameter.put("parTest", "Test");
            print = JasperFillManager.fillReport(jasperPfad + jasperfile,parameter, jrds);
        } catch (JRException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return print;
    }

    /**
     * Erzeugt aus dem JasperPrint Objekt ein Pdf File.
     * @param print Object für den Jasper Report
     */
    public void createPdf(JasperPrint print) {
        try {
            JasperExportManager.exportReportToPdfFile(print,outputPfad + filename + ".pdf");
        } catch (JRException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * Erzeugt aus dem JasperPrint Objekt ein Xls File.
      * @param print Object für den Jesper Report.
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
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
