package com.cg.hbm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ReviewRequestDTO {
	@NotBlank
    @Size(max = 255)
    private String reviewContent;

    @NotNull
    @PositiveOrZero
    private double rating;
}
