package vn.online.shop.onlineshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.online.shop.onlineshop.common.mapper.OrderMapper;
import vn.online.shop.onlineshop.repository.IOrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final IOrderRepository orderRepository;

    private final OrderMapper orderMapper;
}
