package com.virtuslab.internship.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record ProductDto(@NotNull @Size(min = 1, max = 64) String name) {}
