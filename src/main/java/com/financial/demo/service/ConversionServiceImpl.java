package com.financial.demo.service;

import com.financial.demo.dao.ConversionDAO;
import com.financial.demo.model.Conversion;
import com.financial.demo.model.CurrencyType;
import com.financial.demo.model.StatusType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
public class ConversionServiceImpl implements ConversionService {

    public static final String ACCESS_KEY = "16a4a4a3b7b90c8fa7220d8dc2e58f67";
    public static final String BASE_URL = "http://api.currencylayer.com/";
    public static final String ENDPOINT = "live";

    @Autowired
    private ConversionDAO conversionDAO;

    //Exchange rate API
    public Double sendCurrencyRateRequest(String sourceCurrency, String targetCurrency) throws IOException {

        HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=" + targetCurrency);
        HttpClient httpClient = HttpClients.createDefault();
        Double currencyExchangeRate = 0.0;

            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            currencyExchangeRate = exchangeRates.getJSONObject("quotes").getDouble("" + sourceCurrency + targetCurrency + "");

        return currencyExchangeRate ;
    }

    //Conversion API
    public JSONObject conversionAmount(String sourceCurrency, String targetCurrency, Double amount) throws IOException {
        Conversion conversion = new Conversion();
        HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=" + targetCurrency);
        HttpClient httpClient = HttpClients.createDefault();
        JSONObject transactionAmount = new JSONObject();

        HttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        JSONObject sourceTargetExchangeRates = new JSONObject(EntityUtils.toString(entity));
        Date timeStampDate = new Date((long) (sourceTargetExchangeRates.getLong("timestamp") * 1000));

        //Exchange rate
        double exchangeRate = sourceTargetExchangeRates.getJSONObject("quotes").getDouble("" + sourceCurrency + targetCurrency + "");
        conversion.setExchangeRate(exchangeRate);
        // Source and target amounts
        conversion.setSourceAmount(amount);
        conversion.setTargetAmount(amount * exchangeRate);
        // Source and Target currency types
        CurrencyType sourceCurrencyType = CurrencyType.valueOf(sourceCurrency);
        conversion.setSourceCurrency(sourceCurrencyType);
        CurrencyType targetCurrencyType = CurrencyType.valueOf(targetCurrency);
        conversion.setTargetCurrency(targetCurrencyType);
        // Time
        conversion.setTransactionDate(timeStampDate);
        //Change status of transaction
        conversion.setTransactionStatus(StatusType.COMPLETED);

        conversion = conversionDAO.save(conversion);

        transactionAmount.put("amount",conversion.getTargetAmount());
        transactionAmount.put("id",conversion.getId());
        return transactionAmount;
    }

    //Conversion List API
    public Page<Conversion> getAllTransactionsByIdOrDate(Long id, Date date, Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Conversion> transactionsPage = null;
        if(!Objects.isNull(id)){
            transactionsPage = conversionDAO.findAllById(id, paging);
        } else if (date != null){
            transactionsPage = conversionDAO.findAllByTransactionDate(date, paging);
        }
            return transactionsPage;
    }

    public Date setDate(String temp){
        Date date = null;
        if(temp != null) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(temp);
            } catch (ParseException | java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public boolean checkCurrencyValidity(String temp){
        for (CurrencyType currencyType : CurrencyType.values()){
            if(currencyType.name().equals(temp)){
                return true;
            }
        }
        return false;
    }
}
