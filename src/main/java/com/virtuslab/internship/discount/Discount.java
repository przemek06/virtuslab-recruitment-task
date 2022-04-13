package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

/**
 * Class that describes and applies some discount to the receipts, based on their properties
 */
public abstract class Discount {
    /**
     * Calculate a proper discount for receipt and create new receipt, the same as the previous one, but with a discount
     * included in its total price (if it applies).
     *
     * @param receipt the receipt needed for calculating the discount.
     * @return new receipt which has the same fields as the parameter receipt, except for total price.
     */
    public abstract Receipt apply(Receipt receipt);
}
