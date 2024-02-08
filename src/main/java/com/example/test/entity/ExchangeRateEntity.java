package com.example.test.entity;

import com.example.test.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "exchange_rate", schema = "bank")
@IdClass(ExchangeRateEntityPK.class)
@Data
@NoArgsConstructor
public class ExchangeRateEntity {
    @Id
    @Column(name = "currency_from", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currencyFrom;

    @Id
    @Column(name = "currency_to", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currencyTo;

    @Basic
    @Column(name = "rate", nullable = false, precision = 2)
    private BigDecimal rate;

    @Basic
    @Column(name = "date_of_update", nullable = false)
    private Timestamp dateOfUpdate;
}
