package de.syntela;

import net.sf.jasperreports.engine.JasperPrint;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {

    final static Logger logger = Logger.getLogger("Main Class");

    public static void main(String[] args) {
        logger.info("begin creating...");

        CommandLineValues values = new CommandLineValues(args);
        CmdLineParser parser = new CmdLineParser(values);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.exit(1);
        }

        CreateReport createReport = new CreateReport();
        createReport.setFilename(values.getJasperFile());

        JasperPrint print = createReport.printFromXml(values.getJasperFile(), values.getXmlFile());
        createReport.createPdf(print);
        createReport.createXls(print);
        logger.info("creating end");
        logger.info("================================================================================================");

    }

}
