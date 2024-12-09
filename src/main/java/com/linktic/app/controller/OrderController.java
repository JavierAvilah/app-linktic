package com.linktic.app.controller;

import com.linktic.app.dto.detailOrder.DetailOrderDto;
import com.linktic.app.dto.order.CreateOrder;
import com.linktic.app.dto.order.OrderDto;
import com.linktic.app.dto.order.UpdateOrder;
import com.linktic.app.model.DetailOrder;
import com.linktic.app.model.Order;
import com.linktic.app.service.OrderService;
import com.linktic.app.util.crud.CruController;
import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Order")
@RequestMapping("api/order")
@SecurityRequirement(name = "JWT_Token")
public class OrderController extends CruController<Order,Long, CreateOrder, UpdateOrder, OrderDto> {
    @Autowired
    private OrderService orderService;

    protected OrderController(OrderService service) {
        super(service);
    }


    @GetMapping
    @Operation(summary = "Find entries of details order using search filters.")
    public Page<DetailOrderDto> findDetailsOrders(@Parameter(hidden = true) @Filter Specification<DetailOrder> spec
            , @Parameter(hidden = true) Pageable pageable
            , @RequestParam(required = true) Long idOrder, @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort){
        return orderService.findDetailOrder(idOrder,pageable);
    }
}
