package com.backend.beer_api_application.utils;

import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderCalculator {

    public static void calculateTotalAmounts(Order order) {
        BigDecimal totalExclVat = BigDecimal.ZERO;
        BigDecimal totalInclVat = BigDecimal.ZERO;

        for (OrderLine orderLine : order.getOrderLines()) {
            BigDecimal lineTotalExclVat = orderLine.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderLine.getQuantity()));
            totalExclVat = totalExclVat.add(lineTotalExclVat);

            BigDecimal lineTotalInclVat = lineTotalExclVat.multiply(BigDecimal.valueOf(1.21)); // Assuming 21% VAT
            totalInclVat = totalInclVat.add(lineTotalInclVat);
        }

        order.setTotalAmountExcludingVat(totalExclVat);
        order.setTotalAmountIncludingVat(totalInclVat);
    }
}