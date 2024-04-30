package com.account.management.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(setterPrefix = "with")
public class AddressDto {
    @NonNull
    private String addressLine1;
    private String addressLine2;
    @NonNull
    private String postcode;
    @NonNull
    private String county;
    @NonNull
    private String country;
}
