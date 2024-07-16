package org.example.scheduleserver.service.impl;

import org.example.scheduleserver.client.QuotationCommandClient;
import org.example.scheduleserver.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final QuotationCommandClient quotationCommandClient;

    @Autowired
    public ScheduleServiceImpl(QuotationCommandClient quotationCommandClient) {
        this.quotationCommandClient = quotationCommandClient;
    }

    @Override
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Ho_Chi_Minh")
    public void quotationStatusScheduled() {
        quotationCommandClient.updateQuotationStatus();
        System.out.println("Update Quotation Status");
    }
}
