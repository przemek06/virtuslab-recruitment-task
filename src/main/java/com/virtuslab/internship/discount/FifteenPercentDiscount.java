package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class FifteenPercentDiscount extends Discount {

    public static String NAME = "FifteenPercentDiscount";

    @Override
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        int numberOfGrainProducts = receipt.entries().stream()
                .filter(entry->entry.product().type().equals(Product.Type.GRAINS))
                .mapToInt(ReceiptEntry::quantity)
                .sum();
        return numberOfGrainProducts>=3;
    }
}
