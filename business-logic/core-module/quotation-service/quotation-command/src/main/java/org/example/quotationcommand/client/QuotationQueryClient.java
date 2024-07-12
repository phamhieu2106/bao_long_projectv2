package org.example.quotationcommand.client;

import org.example.sharedlibrary.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "quotation-query", path = "/internal/api/quotations")
public interface QuotationQueryClient {

    @GetMapping("/get-quotation-code/{productCode}")
    String getQuotationCode(@PathVariable String productCode);

    @GetMapping("/exitsById/{quotationId}")
    boolean exitsById(@PathVariable String quotationId);

    @GetMapping("/exitsByUserModel/{quotationId}")
    boolean exitsByUserModel(@RequestBody UserModel userModel, @PathVariable String quotationId);
}
