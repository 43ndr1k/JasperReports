package de.syntela;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {

    final static Logger logger = Logger.getLogger("Main Class");

    public static void main(String[] args) {
        logger.info("begin creating...");




        logger.info("creating end");
        logger.info("================================================================================================");
    }

    public JasperPrint print() {

        JasperPrint print = null;
        try {
            Map parameter = new HashMap();
            JRXmlDataSource jrds = new JRXmlDataSource(new File("H:\\Bewilligungsbeispiele\\test.xml"), "*/*");
            Document document = JRXmlUtils.parse(new File("H:\\Bewilligungsbeispiele\\test.xml"));
            parameter.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
            parameter.put("parTest", "Test");
            print = JasperFillManager.fillReport("C:\\Users\\hsawade\\JaspersoftWorkspace\\Test\\xmlSample2.jasper",parameter, jrds);
        } catch (JRException e) {
            e.printStackTrace();
        }

        return print;
    }



}
