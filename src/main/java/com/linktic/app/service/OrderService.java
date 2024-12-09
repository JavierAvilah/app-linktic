package com.linktic.app.service;

import com.linktic.app.dto.detailOrder.CreateDetailOrder;
import com.linktic.app.dto.detailOrder.DetailOrderDto;
import com.linktic.app.dto.order.CreateOrder;
import com.linktic.app.dto.order.OrderDto;
import com.linktic.app.dto.order.UpdateOrder;
import com.linktic.app.dto.product.ProductDto;
import com.linktic.app.exception.EntityNotFoundException;
import com.linktic.app.exception.ExportFileError;
import com.linktic.app.model.DetailOrder;
import com.linktic.app.model.Order;
import com.linktic.app.model.Product;
import com.linktic.app.repository.DetailOrderRepository;
import com.linktic.app.repository.OrderRepository;
import com.linktic.app.repository.ProductRepository;
import com.linktic.app.util.Converter;
import com.linktic.app.util.ExportFileXlsx;
import com.linktic.app.util.SpecificationUtils;
import com.linktic.app.util.Validator;
import com.linktic.app.util.crud.CrudService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements CrudService<Order, Long, CreateOrder, UpdateOrder, OrderDto> {

    @Autowired
    private Validator validator;
    @Autowired
    private Converter converter;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DetailOrderRepository detailOrderRepository;

    @Transactional
    @Override
    public OrderDto create(@NonNull CreateOrder dto) {

        validator.validate(dto);
        Set<CreateDetailOrder> setUnico = deleteDuplicates(dto.getDetailOrder());
        Set<DetailOrder> detailOrders=new HashSet<>();
        Double total=0.0;


        Order order=Order.builder()
                .numberOrder(generarNumeroAleatorio())
                .typeOrder(dto.getTypeOrder())
                .description(dto.getDescription())
                .build();
        for (CreateDetailOrder createDetailOrder:setUnico) {
            validator.validate(createDetailOrder);
            Product product= productRepository.findById(createDetailOrder.getProductId())
                    .orElseThrow(()-> new EntityNotFoundException(Product.class,createDetailOrder.getProductId()));
            DetailOrder detailOrder=DetailOrder.builder()
                    .product(product)
                    .quantity(createDetailOrder.getQuantity())
                    .order(order)
                    .build();
            detailOrders.add(detailOrder);
            total+= product.getPrice()* createDetailOrder.getQuantity();
        }
        order.setTotal(total);
        order.setDetailOrders(detailOrders);
        orderRepository.save(order);


        return converter.map(order,OrderDto.class);
    }

    private int generarNumeroAleatorio() {
        Random random = new Random();
        // Generar un número aleatorio entre 10000 y 99999
        return 10000 + random.nextInt(90000);
    }

    private Set<CreateDetailOrder> deleteDuplicates(List<CreateDetailOrder> list){
        return  new HashSet<>(list.stream()
                .collect(Collectors.toMap(
                        CreateDetailOrder::getProductId,
                        detail -> detail,
                        (detail1, detail2) -> detail1
                ))
                .values());
    }
    @Transactional
    @Override
    public OrderDto update(@NonNull UpdateOrder dto) {
        validator.validate(dto);
        Order order=orderRepository.findById(dto.getId())
                .orElseThrow(()-> new EntityNotFoundException(Order.class,dto.getId()));

        dto.getTypeOrder().ifPresent(order::setTypeOrder);
        dto.getDescription().ifPresent(order::setDescription);
        if (dto.getIdDetailsOrdersDelete()!=null && !dto.getIdDetailsOrdersDelete().isEmpty()){
            dto.getIdDetailsOrdersDelete().stream().forEach(idDetailOrder->{
                DetailOrder detailOrder= detailOrderRepository.findByIdAndOrder_Id(idDetailOrder,order.getId())
                        .orElseThrow(()-> new EntityNotFoundException(DetailOrder.class,idDetailOrder));
                detailOrder.setOrder(null);
                order.getDetailOrders().remove(detailOrder);
                order.setTotal(order.getTotal()-(detailOrder.getProduct().getPrice()*detailOrder.getQuantity()));
            });
        }
        if (dto.getDetailOrderAdd()!=null && !dto.getDetailOrderAdd().isEmpty()  ){
            for (CreateDetailOrder createDetailOrder:dto.getDetailOrderAdd()) {
                validator.validate(createDetailOrder);
                Product product= productRepository.findById(createDetailOrder.getProductId())
                        .orElseThrow(()-> new EntityNotFoundException(Product.class,createDetailOrder.getProductId()));
                DetailOrder detailOrder=DetailOrder.builder()
                        .product(product)
                        .quantity(createDetailOrder.getQuantity())
                        .order(order)
                        .build();
                order.getDetailOrders().add(detailOrder);
                order.setTotal(order.getTotal()+(product.getPrice()*detailOrder.getQuantity()));
            }
        }
        orderRepository.save(order);

        return converter.map(order,OrderDto.class);
    }


    @Override
    public Page<OrderDto> find(Specification<Order> spec, @NonNull Pageable pageable) {
        return converter.mapPage(orderRepository.findAll(SpecificationUtils.distinct(spec),pageable), OrderDto.class);
    }
    @Transactional
    public Page<DetailOrderDto>findDetailOrder(Long idOrder, @NonNull Pageable pageable){
        return converter.mapPage(detailOrderRepository.findByOrder_Id(idOrder,pageable), DetailOrderDto.class);
    }

    @Override
    public void delete(@NonNull List<Long> idList) {
        idList.stream().forEach(id->{orderRepository.deleteById(id);});
    }

    @Override
    public InputStream generateExportFile() {
        List<OrderDto>bodegas=converter.mapList(orderRepository.findAll(),OrderDto.class);
        try {
            return ExportFileXlsx.createExport(bodegas);
        } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
            throw new ExportFileError(e.getMessage());}
    }
}
