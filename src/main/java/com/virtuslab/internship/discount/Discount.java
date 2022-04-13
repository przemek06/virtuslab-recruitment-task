package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public abstract class Discount {
    public abstract Receipt apply(Receipt receipt);
}
