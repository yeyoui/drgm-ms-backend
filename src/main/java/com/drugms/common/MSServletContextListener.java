package com.drugms.common;

import com.drugms.service.impl.WhPrchsInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.concurrent.*;

@WebListener
@Slf4j
public class MSServletContextListener implements ServletContextListener {
    private Logger logger= LoggerFactory.getLogger("MSServletContextListener");
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("MSServletContextListener监听到项目创建");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("MSServletContextListener监听到项目销毁");
    }
}
