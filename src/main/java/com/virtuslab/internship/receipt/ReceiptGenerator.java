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

@Component
public class ReceiptGenerator {

    List<Discount> discountChain;

    public ReceiptGenerator() {
        initDiscountChain();
    }

    private void initDiscountChain(){
        discountChain=new LinkedList<>();
        discountChain.add(new FifteenPercentDiscount());
        discountChain.add(new TenPercentDiscount());
    }

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = convertBasketToReceiptEntries(basket);
        Receipt receipt =  new Receipt(receiptEntries);
        for(Discount discount: discountChain){
            receipt = discount.apply(receipt);
        }
        return receipt;
    }

    private List<ReceiptEntry> convertBasketToReceiptEntries(Basket basket){
        return basket.getProducts().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream().map(productEntry->new ReceiptEntry(productEntry.getKey(), productEntry.getValue().intValue()))
                .collect(Collectors.toList());
    }
}
