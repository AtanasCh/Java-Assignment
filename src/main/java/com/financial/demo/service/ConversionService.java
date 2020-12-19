package com.financial.demo.service;

import com.financial.demo.model.Conversion;
import org.json.JSONObject;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Date;

public interface ConversionService {

    Double sendCurrencyRateRequest(String sourceCurrency, String targetCurrency) throws IOException;

    JSONObject conversionAmount(String sourceCurrency, String targetCurrency, Double amount) throws IOException;

    Page<Conversion> getAllTransactionsByIdOrDate(Long id, Date date, Integer pageNo, Integer pageSize, String sortBy);

    Date setDate(String temp);

    boolean checkCurrencyValidity(String temp);

}
