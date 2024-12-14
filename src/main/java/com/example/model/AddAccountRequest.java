package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AddAccountRequest {
    private BigDecimal amount;
    private LocalDate date;
    private String category;
    private String type;
    private String remarks;

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getRemarks() {
        return remarks;
    }
}
