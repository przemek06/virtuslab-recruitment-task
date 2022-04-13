package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.BasketDto;
import com.virtuslab.internship.product.NoSuchProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.virtuslab.internship.receipt.Constants.BASKET_NOT_VALID_RESPONSE;
import static com.virtuslab.internship.receipt.Constants.NO_SUCH_PRODUCT_RESPONSE;

@RestController
public class ReceiptController {

    ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping(value = "/api/receipt", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Receipt> createReceipt(@RequestBody @Valid BasketDto cart){
        return ResponseEntity.ok(receiptService.createReceipt(cart));
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<String> handleNoSuchProductException(){
        return ResponseEntity.status(404).body(NO_SUCH_PRODUCT_RESPONSE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(){
        return ResponseEntity.status(400).body(BASKET_NOT_VALID_RESPONSE);
    }

}
