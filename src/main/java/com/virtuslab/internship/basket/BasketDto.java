package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.ProductDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record BasketDto(@NotNull @NotEmpty List<@Valid ProductDto> products) {}
