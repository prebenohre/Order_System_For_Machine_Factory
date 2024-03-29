package no.kristiania.ordersystemformachinefactory.controller;

import no.kristiania.ordersystemformachinefactory.DTO.AddMachineToOrderDto;
import no.kristiania.ordersystemformachinefactory.DTO.AddOrderToCustomerDto;
import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<Order>> getOrdersByPage(
            @PathVariable int pageNumber,
            @RequestParam(defaultValue = "10") int size) {
        Page<Order> page = orderService.getOrdersPageable(pageNumber, size);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/{id}/addMachine")
    public ResponseEntity<Order> addMachineToOrder(@PathVariable Long id, @RequestBody AddMachineToOrderDto dto) {
        Order updatedOrder = orderService.addMachineToOrder(id, dto.getMachineId());
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/createForCustomer")
    public ResponseEntity<Order> createOrderForCustomer(@RequestBody AddOrderToCustomerDto addOrderToCustomerDto) {
        return ResponseEntity.ok(orderService.createOrderForCustomer(addOrderToCustomerDto));
    }
}
