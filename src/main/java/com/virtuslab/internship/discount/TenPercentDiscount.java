package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

/**
 * Class specifies the discount for the receipts that have a total price of over 50. This discount should subtract
 * 10% of the original total price when it's applicable.
 */
public class TenPercentDiscount extends Discount {

    public static String NAME = "TenPercentDiscount";

    /**
     * If the discount should be applied, create new receipt, the same as the receipt given in parameters, but with
     * total price equal to 90% of an original price
     *
     * @param receipt the receipt needed for calculating the discount.
     * @return either new receipt with discounted total price, or unchanged receipt given in parameters
     */
    @Override
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.9));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    /**
     * Determine if a discount should be applied based on the receipt's total price. If total price is above 50,
     * then discount should be applied. In other case, it should not be applied.
     *
     * @param receipt receipt for which we want to determine, if a discount should be applied
     * @return true if discount should be applied, false otherwise
     */
    private boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) > 0;
    }
}
