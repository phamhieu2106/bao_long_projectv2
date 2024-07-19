package org.example.quotationcommand.client;

import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "quotation-query", path = "/internal/api/quotations")
public interface QuotationQueryClient {

    @GetMapping("/get-quotation-code/{productCode}")
    String getQuotationCode(@PathVariable String productCode);

    @GetMapping("/exitsById/{quotationId}")
    boolean exitsById(@PathVariable String quotationId);

    @GetMapping("/exitsByUserModel/{quotationId}")
    boolean exitsByUserModel(@RequestBody UserModel userModel, @PathVariable String quotationId);

    @PostMapping("/findAllIdsByQuotationStatus")
    List<String> findAllIdsByQuotationStatus(@RequestBody QuotationScheduleStatusCommand command);

    @GetMapping("/findAllIdsByCustomerId")
    List<String> findAllIdsByCustomerId(@RequestParam String customerId);

    @GetMapping("/findAllQuotationsNotApproveByCustomerId")
    List<String> findAllQuotationsNotApproveByCustomerId(@RequestParam String customerId);

    @GetMapping("/isApproved")
    boolean isApproved(@RequestParam String quotationId);

    @GetMapping("/isCreateNewVersionAble")
    boolean isCreateNewVersionAble(@RequestParam String quotationId);

    @GetMapping("/getQuotationVersion")
    int getQuotationVersion(@RequestParam String quotationId);
}
