package com.backend.beer_api_application.services;

import com.backend.beer_api_application.exceptions.OrderLineNotFoundException;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    // Create or update an OrderLine
    @Transactional
    public OrderLine addOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    // Find an OrderLine by ID
    @Transactional(readOnly = true)
    public OrderLine findOrderLineById(Long id) {
        return orderLineRepository.findById(id)
                .orElseThrow(() -> new OrderLineNotFoundException("OrderLine with ID " + id + " not found"));
    }

    // Find all OrderLines
    @Transactional(readOnly = true)
    public List<OrderLine> findAllOrderLines() {
        return orderLineRepository.findAll();
    }

    // Update an OrderLine
    @Transactional
    public OrderLine updateOrderLine(Long id, OrderLine updatedOrderLine) {
        OrderLine existingOrderLine = findOrderLineById(id);  // Fetch the existing order line

        // Update the fields of the existing order line with the new values
        existingOrderLine.setBeer(updatedOrderLine.getBeer());
        existingOrderLine.setQuantity(updatedOrderLine.getQuantity());
        existingOrderLine.setPriceAtPurchase(updatedOrderLine.getPriceAtPurchase());

        return orderLineRepository.save(existingOrderLine);  // Save the updated order line
    }

    // Delete an OrderLine by ID
    @Transactional
    public void deleteOrderLineById(Long id) {
        if (!orderLineRepository.existsById(id)) {
            throw new OrderLineNotFoundException("OrderLine with ID " + id + " not found");
        }
        orderLineRepository.deleteById(id);
    }
}
