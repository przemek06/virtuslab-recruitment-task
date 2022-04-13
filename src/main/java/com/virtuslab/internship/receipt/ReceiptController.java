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

/**
 * Class which exposes to the web REST endpoints representing all of receipt's and basket's logic
 */
@RestController
public class ReceiptController {

    ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * Take an object - representing basket info - from HTTP POST request's body, and if it is valid, delegate it to the
     * service layer for processing. Return receipt received from service layer, wrapped in response entity with status
     * 200 OK.
     *
     * @param cart Basket information to be processed
     * @return Receipt based on the basket's information
     */
    @PostMapping(value = "/api/receipt", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Receipt> createReceipt(@RequestBody @Valid BasketDto cart) {
        return ResponseEntity.ok(receiptService.createReceipt(cart));
    }

    /**
     * Handle an exception which may occur in the service layer if a product specified in basket's information
     * doesn't exist.
     *
     * @return response entity with status NOT FOUND and message explaining which product does not exist.
     */
    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<String> handleNoSuchProductException(NoSuchProductException e) {
        return ResponseEntity.status(404).body(String.format(NO_SUCH_PRODUCT_RESPONSE, e.getNotExistingProduct()));
    }

    /**
     * Handle an exception which occurs when basket object sent by a client is not valid.
     *
     * @return response entity with status BAD REQUEST and message explaining that basket object was not valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions() {
        return ResponseEntity.status(400).body(BASKET_NOT_VALID_RESPONSE);
    }

}
