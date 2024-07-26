package org.example.policyquery.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.repository.AdditionalModificationEntityRepository;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policydomain.response.PolicyHistoryResponse;
import org.example.policydomain.response.PolicyResponse;
import org.example.policydomain.view.AdditionalModificationView;
import org.example.policydomain.view.PolicyView;
import org.example.policyquery.client.CustomerQueryClient;
import org.example.policyquery.request.AdditionalModificationPageRequest;
import org.example.policyquery.request.PolicyPageRequest;
import org.example.policyquery.response.additonal_modification.AdditionalModificationResponse;
import org.example.policyquery.service.PolicyEsService;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_constant.view.AdditionalModificationViewConstant;
import org.example.sharedlibrary.base_constant.view.PolicyViewConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyEsServiceImpl implements PolicyEsService {
    private static final Logger logger = LoggerFactory.getLogger(PolicyEsServiceImpl.class);

    private static final String QUOTATION_DISTRIBUTION_NAME = "quotationDistributionName";
    private static final String INSURANCE_COMPANY_NAME = "insuranceCompanyName";
    private static final String CUSTOMER_ID = "customerId";
    private static final String CUSTOMER = "customer";
    private static final String BENEFICIARY_ID = "beneficiaryId";
    private static final String BENEFICIARY = "beneficiary";
    private static final String PRODUCT = "product";
    private static final String INSURANCE_TYPE_MODEL = "insuranceTypeModel";
    private static final String ADDITIONAL_MODIFICATION_INTERNAL_TEMP_CODE = "AM %03dN";
    private static final String ADDITIONAL_MODIFICATION_CUSTOMER_TEMP_CODE = "AM %03d";
    private static final String MATURITY_DATE = "maturityDate";
    private final CustomerQueryClient customerQueryClient;
    private final PolicyEntityRepository entityRepository;
    private final AdditionalModificationEntityRepository modificationEntityRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public WrapperResponse getPolicyPage(PolicyPageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(),
                PageConstant.getSortBys(request.getSortRequests())
        );

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        if (request.getUserOffice() != null && !request.getUserOffice().isBlank()) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_USER_OFFICE).value(request.getUserOffice())));
        }
        if (request.getApartment() != null && !request.getApartment().isBlank()) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_APARTMENT).value(request.getApartment())));
        }
        if (request.getProductType() != null) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_PRODUCT_TYPE).value(request.getProductType().name())));
        }
        if (request.getApprovedAt() != null) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_APPROVED_AT).value(FieldValue.of(request.getApprovedAt()))));
        }

        if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
            boolQuery.should(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_POLICY_CODE).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_CUSTOMER_NAME_KEYWORD).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(PolicyViewConstant.POLICY_CUSTOMER_NAME).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.nested(nes -> nes.path(PolicyViewConstant.POLICY_MOTO_IDENTITY).query(q -> q
                    .term(t -> t.field(PolicyViewConstant.POLICY_MOTO_IDENTITY_NUMBER).value(request.getKeyword())))
            ));
            boolQuery.should(QueryBuilders.nested(nes -> nes.path(PolicyViewConstant.POLICY_MOTO_IDENTITY).query(q -> q
                    .term(t -> t.field(PolicyViewConstant.POLICY_MOTO_FRAME_NUMBER).value(request.getKeyword())))
            ));
        }
        if (request.getCreatedFrom() != null && request.getCreatedTo() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(PolicyViewConstant.POLICY_TIMESTAMP)
                    .gte(JsonData.of(request.getCreatedFrom().toInstant()))
                    .lte(JsonData.of(request.getCreatedTo().toInstant()))));

        } else if (request.getCreatedFrom() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(PolicyViewConstant.POLICY_TIMESTAMP)
                    .gte(JsonData.of(request.getCreatedFrom().toInstant()))));

        } else if (request.getCreatedTo() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(PolicyViewConstant.POLICY_TIMESTAMP)
                    .lte(JsonData.of(request.getCreatedTo().toInstant()))));
        }

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQUery = boolQuery.build()._toQuery();
        Query query = new StringQuery(nativeQUery.toString().substring(6)).setPageable(pageable);

        SearchHits<PolicyView> hits = elasticsearchTemplate.search(query, PolicyView.class);
        SearchPage<PolicyView> page = SearchHitSupport.searchPageFor(hits, pageable);
        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse getPolicyDetail(String policyId) {
        if (policyId == null || policyId.isBlank()) {
            return WrapperResponse.fail(
                    "Invalid Id", HttpStatus.BAD_REQUEST
            );
        }
        Optional<PolicyEntity> optional = entityRepository.findById(policyId);
        if (optional.isEmpty()) {
            return WrapperResponse.fail(
                    "Not Found", HttpStatus.NOT_FOUND
            );
        }
        PolicyEntity policyEntity = optional.get();

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQuery =
                co.elastic.clients.elasticsearch._types.query_dsl.Query.of(nq -> nq
                        .term(t -> t.field(PolicyViewConstant.POLICY_AGGREGATE_ID).value(policyEntity.getId())));

        Query query = new StringQuery(nativeQuery.toString().substring(6)).addSort(
                Sort.by(Sort.Direction.DESC, PolicyViewConstant.POLICY_VERSION)
        );
        SearchHits<PolicyHistoryResponse> hits = elasticsearchTemplate.search(query, PolicyHistoryResponse.class);
        PolicyResponse response = new PolicyResponse(policyEntity, hits.stream().map(SearchHit::getContent).toList());
        return WrapperResponse.success(HttpStatus.OK, response);
    }

    @Override
    public WrapperResponse getAdditionalModificationPage(AdditionalModificationPageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(),
                PageConstant.getSortBys(request.getSortRequests())
        );

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
            boolQuery.should(QueryBuilders.term(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_ID).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.term(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_CODE).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_STATUS).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_TYPE).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_TYPE_NAME).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_CREATED_AT).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_EFFECTIVE_DATE).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(AdditionalModificationViewConstant.ADDITIONAL_MODIFICATION_APPROVED_BY).query(request.getKeyword())));
        }
        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQUery = boolQuery.build()._toQuery();
        Query query = new StringQuery(nativeQUery.toString().substring(6)).setPageable(pageable);

        SearchHits<AdditionalModificationView> hits = elasticsearchTemplate.search(query, AdditionalModificationView.class);
        SearchPage<AdditionalModificationView> page = SearchHitSupport.searchPageFor(hits, pageable);
        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse getAdditionalModificationDetail(String additionalModificationId) {
        if (additionalModificationId == null || additionalModificationId.isBlank())
            return WrapperResponse.fail("Invalid Id", HttpStatus.BAD_REQUEST);

        Optional<AdditionalModificationEntity> modificationEntityOptional = modificationEntityRepository.findById(additionalModificationId);
        if (modificationEntityOptional.isEmpty()) return WrapperResponse.fail("Not found AM!", HttpStatus.NOT_FOUND);
        AdditionalModificationEntity additionalModificationEntity = modificationEntityOptional.get();

        Optional<PolicyEntity> policyEntityOptional = entityRepository.findById(additionalModificationEntity.getPolicyId());
        if (policyEntityOptional.isEmpty()) return WrapperResponse.fail("Not found Policy!", HttpStatus.NOT_FOUND);
        PolicyEntity policyEntity = policyEntityOptional.get();


        Optional<AdditionalModificationEntity> lastModificationEntityOptional = returnAdditionalModificationOptional(additionalModificationId);
        Map<String, Object> lastModificationMap = returnLastModificationMap(lastModificationEntityOptional);
        Map<String, Object> mapChanged = new HashMap<>();

        processAdditionalModificationData(lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);

        AdditionalModificationResponse response = new AdditionalModificationResponse();
        response.setAdditionalModificationEntity(additionalModificationEntity);
        response.setPolicyEntity(policyEntity);
        response.setMapChanged(mapChanged);
        return WrapperResponse.success(HttpStatus.OK, response);
    }

    private void processAdditionalModificationData(
            Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap,
            Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity

    ) {
        additionalModificationEntity.getAdditionalData().forEach(
                am -> {
                    Map<String, Object> aMMap;
                    aMMap = new HashMap<>(am);
                    if (aMMap.containsKey(QUOTATION_DISTRIBUTION_NAME)) {
                        handleKeyQuotationDistributeName(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                    if (aMMap.containsKey(INSURANCE_COMPANY_NAME)) {
                        handleKeyInsuranceCompanyName(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                    if (am.containsKey(CUSTOMER_ID)) {
                        handleKeyCustomerId(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                    if (am.containsKey(BENEFICIARY_ID)) {
                        handleKeyBeneficiaryId(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                    if (aMMap.containsKey(PRODUCT)) {
                        handleKeyProduct(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                    if (aMMap.containsKey(INSURANCE_TYPE_MODEL)) {
                        handleKeyInsuranceType(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                    if (aMMap.containsKey(MATURITY_DATE)) {
                        handleKeyMaturityDate(aMMap, lastModificationEntityOptional, lastModificationMap, mapChanged, policyEntity, additionalModificationEntity);
                    }
                }
        );
    }

    private void handleKeyMaturityDate(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap, Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(MATURITY_DATE)) {
            try {
                LocalDate localDate = LocalDate.parse(lastModificationMap.get(MATURITY_DATE).toString(), formatter);
                Date date = java.sql.Date.valueOf(localDate); // Convert LocalDate to Date
                policyEntity.setMaturityDate(date);
                policyEntity.setPolicyCode(lastModificationEntityOptional.get().getAdditionalModificationCode());
            } catch (Exception e) {
                logger.error("Error parsing maturity date from lastModificationMap", e);
            }
        }

        map.put(policyEntity.getPolicyCode(), policyEntity.getMaturityDate());
        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, MATURITY_DATE);

        try {
            Date date = Date.from(Instant.parse(aMMap.get(MATURITY_DATE).toString() + "Z"));
            policyEntity.setMaturityDate(date);
        } catch (Exception e) {
            logger.error("Error parsing maturity date from aMMap", e);
        }
        aMMap.remove(MATURITY_DATE);
    }

    @SuppressWarnings("unchecked")
    private void handleKeyInsuranceType(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap, Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(INSURANCE_TYPE_MODEL)) {

            policyEntity.setInsuranceTypeModel((List<Map<String, Object>>) lastModificationMap.get(INSURANCE_TYPE_MODEL));
            policyEntity.setPolicyCode(lastModificationEntityOptional.get().getAdditionalModificationCode());
        }
        map.put(policyEntity.getPolicyCode(), policyEntity.getProduct());

        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, INSURANCE_TYPE_MODEL);
        policyEntity.setInsuranceTypeModel((List<Map<String, Object>>) aMMap.get(INSURANCE_TYPE_MODEL));
        aMMap.remove(INSURANCE_TYPE_MODEL);
    }

    private void handleKeyBeneficiaryId(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap, Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String code = policyEntity.getPolicyCode();

        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(BENEFICIARY_ID)) {
            policyEntity.setBeneficiary(customerQueryClient.getCustomerModelById(lastModificationMap.get(BENEFICIARY_ID).toString()));
            code = lastModificationEntityOptional.get().getAdditionalModificationCode();
        }
        map.put(code, policyEntity.getBeneficiary());
        aMMap.put(BENEFICIARY, customerQueryClient.getCustomerModelById(additionalModificationEntity.getAdditionalData().get(0).get(BENEFICIARY_ID).toString()));
        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, BENEFICIARY);
        policyEntity.setBeneficiary(customerQueryClient.getCustomerModelById(aMMap.get(BENEFICIARY_ID).toString()));
        aMMap.remove(BENEFICIARY_ID);
        aMMap.remove(BENEFICIARY);
    }

    private void handleKeyCustomerId(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap, Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String code = policyEntity.getPolicyCode();

        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(CUSTOMER_ID)) {
            policyEntity.setCustomer(customerQueryClient.getCustomerModelById(lastModificationMap.get(CUSTOMER_ID).toString()));
            code = lastModificationEntityOptional.get().getAdditionalModificationCode();
        }
        map.put(code, policyEntity.getCustomer());
        aMMap.put(CUSTOMER, customerQueryClient.getCustomerModelById(additionalModificationEntity.getAdditionalData().get(0).get(CUSTOMER_ID).toString()));
        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, CUSTOMER);
        policyEntity.setCustomer(customerQueryClient.getCustomerModelById(aMMap.get(CUSTOMER_ID).toString()));
        aMMap.remove(CUSTOMER_ID);
        aMMap.remove(CUSTOMER);
    }

    private void handleKeyInsuranceCompanyName(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap,
                                               Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String code = policyEntity.getPolicyCode();

        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(INSURANCE_COMPANY_NAME)) {
            policyEntity.setInsuranceCompanyName(lastModificationMap.get(INSURANCE_COMPANY_NAME).toString());
            code = lastModificationEntityOptional.get().getAdditionalModificationCode();
        }
        map.put(code, policyEntity.getInsuranceCompanyName());
        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, INSURANCE_COMPANY_NAME);
        policyEntity.setInsuranceCompanyName(aMMap.get(INSURANCE_COMPANY_NAME).toString());
        aMMap.remove(INSURANCE_COMPANY_NAME);
    }

    private void handleKeyQuotationDistributeName(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap,
                                                  Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String code = policyEntity.getPolicyCode();

        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(QUOTATION_DISTRIBUTION_NAME)) {
            policyEntity.setQuotationDistributionName(lastModificationMap.get(QUOTATION_DISTRIBUTION_NAME).toString());
            code = lastModificationEntityOptional.get().getAdditionalModificationCode();
        }
        map.put(code, policyEntity.getQuotationDistributionName());
        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, QUOTATION_DISTRIBUTION_NAME);
        policyEntity.setQuotationDistributionName(aMMap.get(QUOTATION_DISTRIBUTION_NAME).toString());
        aMMap.remove(QUOTATION_DISTRIBUTION_NAME);
    }

    private void handleKeyProduct(Map<String, Object> aMMap, Optional<AdditionalModificationEntity> lastModificationEntityOptional, Map<String, Object> lastModificationMap,
                                  Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (lastModificationEntityOptional.isPresent() && lastModificationMap.containsKey(PRODUCT)) {
            policyEntity.setProduct(List.of(lastModificationMap));
            policyEntity.setPolicyCode(lastModificationEntityOptional.get().getAdditionalModificationCode());
        }
        map.put(policyEntity.getPolicyCode(), policyEntity.getProduct());

        putTempVariableCode(aMMap, mapChanged, policyEntity, additionalModificationEntity, list, map, PRODUCT);
        policyEntity.setProduct(List.of(aMMap));
    }

    private Optional<AdditionalModificationEntity> returnAdditionalModificationOptional(String additionalModificationId) {
        return modificationEntityRepository.findLastAMApprovedByAmId(additionalModificationId);
    }

    private Map<String, Object> returnLastModificationMap(Optional<AdditionalModificationEntity> optional) {
        AtomicReference<Map<String, Object>> lastModificationMapFind = new AtomicReference<>(new HashMap<>());
        optional.ifPresent(modificationEntity -> modificationEntity
                .getAdditionalData().forEach(lastModificationMapFind::set));
        return lastModificationMapFind.get();
    }

    private void putTempVariableCode(Map<String, Object> aMMap, Map<String, Object> mapChanged, PolicyEntity policyEntity, AdditionalModificationEntity additionalModificationEntity, List<Map<String, Object>> list, Map<String, Object> map, String mapCode) {
        long count = count(policyEntity.getId(), additionalModificationEntity.getModificationType()) + 1;
        if (ModificationType.INTERNAL_MODIFICATION.equals(additionalModificationEntity.getModificationType())) {
            if (additionalModificationEntity.getAdditionalModificationCode() == null) {
                map.put(String.format(ADDITIONAL_MODIFICATION_INTERNAL_TEMP_CODE, count), aMMap.get(mapCode));
            } else {
                map.put(additionalModificationEntity.getAdditionalModificationCode(), aMMap.get(mapCode));
            }
        } else {
            if (additionalModificationEntity.getAdditionalModificationCode() == null) {
                map.put(String.format(ADDITIONAL_MODIFICATION_CUSTOMER_TEMP_CODE, count), aMMap.get(mapCode));
            } else {
                map.put(additionalModificationEntity.getAdditionalModificationCode(), aMMap.get(mapCode));
            }
        }
        list.add(map);
        mapChanged.put(mapCode, list);
    }

    private long count(String policyId, ModificationType modificationType) {
        return modificationEntityRepository.countByPolicyIdAndModificationTypeIsAndAdditionalModificationStatusIs(
                policyId, modificationType, AdditionalModificationStatus.APPROVED);
    }

}
