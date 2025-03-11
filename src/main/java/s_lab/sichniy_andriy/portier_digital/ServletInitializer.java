package s_lab.sichniy_andriy.portier_digital;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Logger logger = LoggerFactory.getLogger("MAIN LOGGER");
        logger.info("SpringBoot Servlet Initializing");
        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
        return application.sources(PortierDigitalApplication.class);
    }

}
