package com.financial.demo.controller;

import com.financial.demo.DemoApplication;
import com.financial.demo.exceptions.InvalidCurrencyAmountException;
import com.financial.demo.exceptions.InvalidCurrencyTypeException;
import com.financial.demo.exceptions.MissingInputParametersException;
import com.financial.demo.model.Conversion;
import com.financial.demo.service.ConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping(DemoApplication.API_START_URL)
public class ConversionController {

    @Autowired
    ConversionService conversionService;

    //Exchange rate API
    @RequestMapping(method = RequestMethod.GET, value = "/exchange/rate")
    public ResponseEntity<Object> getExchangeRate(@RequestParam(value = "sourceCurrency") String sourceCurrency,
                                      @RequestParam(value = "targetCurrency") String targetCurrency) throws IOException {
        if(!conversionService.checkCurrencyValidity(targetCurrency)){
            throw new InvalidCurrencyTypeException("Invalid currency");
        }
        Double temp = conversionService.sendCurrencyRateRequest(sourceCurrency, targetCurrency);
        return new ResponseEntity<>(Collections.singletonMap("exchangeRate",temp), HttpStatus.OK) ;
    }
    //Conversion API
    @RequestMapping(method = RequestMethod.POST, value = "/exchange/rate/amount")
    public ResponseEntity<Object> calculateExchangeRateAmount(@RequestParam(value = "sourceCurrency") String sourceCurrency,
                                                              @RequestParam(value = "targetCurrency") String targetCurrency,
                                                              @RequestParam(value = "amount") Double amount) throws IOException {
        if(!conversionService.checkCurrencyValidity(targetCurrency)){
            throw new InvalidCurrencyTypeException("Invalid currency");
        } else if (amount <= 0){
            throw new InvalidCurrencyAmountException("Invalid negative currency amount");
        }
         return new ResponseEntity<>(conversionService.conversionAmount(sourceCurrency, targetCurrency, amount).toMap(), HttpStatus.OK);
    }
    //Conversion List API
    @RequestMapping(method = RequestMethod.GET, value = "/transactions")
    public Page<Conversion> getAllTransactionsByIdOrDate(@RequestParam(value = "id", required = false) Long id,
                                                         @RequestParam(value = "date", required = false) String date,
                                                         @RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "id") String sortBy){
        if(id == null && date == null){
            throw new MissingInputParametersException("Input parameters are missing");
        }
        return conversionService.getAllTransactionsByIdOrDate(id,conversionService.setDate(date),pageNo,pageSize,sortBy);
    }
}

@ControllerAdvice
class ExceptionHelper{
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = {MissingInputParametersException.class})
    public ResponseEntity<Object>  handleMissingParametersException(MissingInputParametersException ex){
        logger.error("Missing Input Parameters Exception", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {InvalidCurrencyTypeException.class})
    public ResponseEntity<Object> handleInvalidCurrencyTypeException(InvalidCurrencyTypeException ex){
        logger.error("Invalid currency exception", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {InvalidCurrencyAmountException.class})
    public ResponseEntity<Object> handleInvalidCurrencyAmountException(InvalidCurrencyAmountException ex){
        logger.error("Invalid currency amount exception - Please enter a positive amount", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
