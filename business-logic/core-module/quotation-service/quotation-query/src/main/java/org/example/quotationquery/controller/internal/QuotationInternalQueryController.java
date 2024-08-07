package org.example.quotationquery.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationquery.service.QuotationService;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/quotations")
@RequiredArgsConstructor
public class QuotationInternalQueryController {

    private final QuotationService quotationService;

    @GetMapping("/isCreateNewVersionAble")
    public boolean isCreateNewVersionAble(@RequestParam String quotationId) {
        return quotationService.isCreateNewVersionAble(quotationId);

    }

    @GetMapping("/getQuotationByQuotationId")
    public QuotationEntity getQuotationByQuotationId(@RequestParam String quotationId) {
        return quotationService.getQuotationByQuotationId(quotationId);
    }

    @GetMapping("/get-quotation-code/{productCode}")
    public String getQuotationCode(@PathVariable String productCode) {
        return quotationService.getQuotationCode(productCode);
    }

    @GetMapping("/exitsById/{quotationId}")
    public boolean exitsById(@PathVariable String quotationId) {
        return quotationService.exitsById(quotationId);
    }

    @PostMapping("/exitsByUserModel/{quotationId}")
    public boolean exitsByUserModel(@RequestBody UserModel userModel, @PathVariable String quotationId) {
        return quotationService.exitsByUserModel(userModel, quotationId);
    }

    @PostMapping("/findAllIdsByQuotationStatus")
    public List<String> findAllIdsByQuotationStatus(@RequestBody QuotationScheduleStatusCommand command) {
        return quotationService.findAllIdsByQuotationStatus(command);
    }

    @GetMapping("/findAllIdsByCustomerId")
    public List<String> findAllIdsByCustomerId(@RequestParam String customerId) {
        return quotationService.findIdsByCustomerId(customerId);
    }

    @GetMapping("/findAllQuotationsNotApproveByCustomerId")
    public List<String> findAllQuotationsNotApproveByCustomerId(@RequestParam String customerId) {
        return quotationService.findAllQuotationsNotApproveByCustomerId(customerId);
    }

    @GetMapping("/isApproved")
    public boolean isApproved(@RequestParam String quotationId) {
        return quotationService.isApproved(quotationId);
    }

    @GetMapping("/getQuotationVersion")
    public int getQuotationVersion(@RequestParam String quotationCode) {
        return quotationService.getQuotationVersion(quotationCode);
    }

    @GetMapping("/getAllQuotationIdsOtherVersionNotApprovedAndIsNot")
    public List<String> getAllQuotationIdsOtherVersionNotApprovedAndIsNot(@RequestParam String quotationCode, @RequestParam String quotationId) {
        return quotationService.getAllQuotationIdsOtherVersionNotApproved(quotationCode, quotationId);
    }
}
