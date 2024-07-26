package org.example.scheduleserver.service.impl;

import org.example.scheduleserver.client.PolicyCommandClient;
import org.example.scheduleserver.client.QuotationCommandClient;
import org.example.scheduleserver.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger logger = Logger.getLogger(ScheduleServiceImpl.class.getName());
    private final QuotationCommandClient quotationCommandClient;
    private final PolicyCommandClient policyCommandClient;

    @Autowired
    public ScheduleServiceImpl(QuotationCommandClient quotationCommandClient, PolicyCommandClient policyCommandClient) {
        this.quotationCommandClient = quotationCommandClient;
        this.policyCommandClient = policyCommandClient;
    }

    @Override
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Ho_Chi_Minh")
    public void quotationStatusScheduled() {
        quotationCommandClient.updateQuotationStatus();
        logger.info("Quotation status updated");
    }

    @Override
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Ho_Chi_Minh")
    public void policyUpdateScheduled() {
        policyCommandClient.policyUpdateScheduled();
        logger.info("Policy updated");
    }
}
