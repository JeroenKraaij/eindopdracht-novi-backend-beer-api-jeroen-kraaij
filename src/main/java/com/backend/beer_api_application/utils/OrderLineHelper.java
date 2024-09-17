package com.backend.beer_api_application.utils;

import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineHelper {

    public static void addOrderLine(Order order, OrderLine orderLine) {
        if (orderLine != null) {
            order.getOrderLines().add(orderLine);
            orderLine.setOrder(order);
        }
    }

    public static void removeOrderLine(Order order, OrderLine orderLine) {
        if (orderLine != null) {
            order.getOrderLines().remove(orderLine);
            orderLine.setOrder(null);
        }
    }
}