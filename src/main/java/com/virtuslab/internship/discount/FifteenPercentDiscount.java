package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

/**
 * Class specifies the discount for the receipts that have at least three grain products in their entries. This
 * discount should subtract 15% of the original total price when it's applicable.
 */
public class FifteenPercentDiscount extends Discount {

    public static String NAME = "FifteenPercentDiscount";

    /**
     * If the discount should be applied, create new receipt, the same as the receipt given in parameters, but with
     * total price equal to 85% of an original price
     *
     * @param receipt the receipt needed for calculating the discount.
     * @return either new receipt with discounted total price, or unchanged receipt given in parameters
     */
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

    /**
     * Determine if a discount should be applied based on the number of grain products in entries of the receipt. If
     * amount of grain products is at least 3, then discount should be applied. In other case, it should not be applied.
     *
     * @param receipt receipt for which we want to determine, if a discount should be applied
     * @return true if discount should be applied, false otherwise
     */
    private boolean shouldApply(Receipt receipt) {
        int numberOfGrainProducts = receipt.entries().stream()
                .filter(entry -> entry.product().type().equals(Product.Type.GRAINS))
                .mapToInt(ReceiptEntry::quantity)
                .sum();
        return numberOfGrainProducts >= 3;
    }
}
