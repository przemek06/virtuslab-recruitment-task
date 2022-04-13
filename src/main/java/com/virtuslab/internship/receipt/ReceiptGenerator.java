package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.Discount;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class that generates receipts based on baskets, applying all specified discounts.
 */
@Component
public class ReceiptGenerator {

    /**
     * Discounts that should be applied in defined order.
     */
    List<Discount> discountChain;

    public ReceiptGenerator() {
        initDiscountChain();
    }


    /**
     * Define the discounts and the order in which they should be applied
     */
    private void initDiscountChain(){
        discountChain=new LinkedList<>();
        discountChain.add(new FifteenPercentDiscount());
        discountChain.add(new TenPercentDiscount());
    }

    /**
     * Convert basket of products to list of receipt entries, use them to generate a receipt and then apply all the
     * discounts from discount chain to this receipt.
     * @param basket basket of products used to create the receipt
     * @return receipt created from the basket of products with all discounts from discount chain applied.
     */
    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = convertBasketToReceiptEntries(basket);
        Receipt receipt =  new Receipt(receiptEntries);
        for(Discount discount: discountChain){
            receipt = discount.apply(receipt);
        }
        return receipt;
    }

    /**
     * Convert basket of product to a list of receipt entries that are needed to create a receipt.
     * @param basket basket that's supposed to be converted to the list of receipt entries.
     * @return list of receipt entries created from the basket
     */
    private List<ReceiptEntry> convertBasketToReceiptEntries(Basket basket){
        return basket.getProducts().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream().map(productEntry->new ReceiptEntry(productEntry.getKey(), productEntry.getValue().intValue()))
                .collect(Collectors.toList());
    }
}
