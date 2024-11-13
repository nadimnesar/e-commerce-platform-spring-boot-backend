package com.nadimnesar.ecommerce.service;

import com.nadimnesar.ecommerce.auth.model.User;
import com.nadimnesar.ecommerce.dto.AddressDto;
import com.nadimnesar.ecommerce.enums.OrderStatus;
import com.nadimnesar.ecommerce.model.*;
import com.nadimnesar.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, ProductRepository productRepository,
                           CartItemRepository cartItemRepository, CartRepository cartRepository,
                           OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    private Customer getCustomerInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return customerRepository.findByUserId(((User) userDetails).getId());
    }

    public ResponseEntity<?> updateAddress(AddressDto addressDto) {
        Customer customer = getCustomerInfo();
        customer.getAddress().setLine1(addressDto.getLine1());
        customer.getAddress().setLine2(addressDto.getLine2());
        customer.getAddress().setCity(addressDto.getCity());
        customer.getAddress().setState(addressDto.getState());
        customer.getAddress().setPostalCode(addressDto.getPostalCode());
        customer.getAddress().setCountry(addressDto.getCountry());
        customerRepository.save(customer);
        return new ResponseEntity<>("Address added successfully.", HttpStatus.CREATED);
    }

    public ResponseEntity<?> addToCart(Integer productId, Integer quantity) {
        if (quantity < 1)
            return new ResponseEntity<>("Product quantity should be positive value.", HttpStatus.BAD_REQUEST);

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>("Product id is not correct.", HttpStatus.BAD_REQUEST);
        }

        Product product = optionalProduct.get();

        if (product.getStock() < quantity) {
            return new ResponseEntity<>("Product quantity should be less than stock.", HttpStatus.BAD_REQUEST);
        }

        Customer customer = getCustomerInfo();
        Cart cart = customer.getCart();

        if (!cart.getItems().isEmpty()) {
            for (CartItem cartItem : customer.getCart().getItems()) {
                if (cartItem.getProduct().getId().equals(productId)) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    cartItemRepository.save(cartItem);

                    cart.setTotal(cart.getTotal() + (quantity * cartItem.getProduct().getPrice()));
                    cartRepository.save(cart);

                    customer.setCart(cart);
                    customerRepository.save(customer);
                    return new ResponseEntity<>("Product added to cart successfully.", HttpStatus.CREATED);
                }
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        cart.getItems().add(cartItem);
        cart.setTotal(cart.getTotal() + (quantity * product.getPrice()));
        cartRepository.save(cart);

        customer.setCart(cart);
        customerRepository.save(customer);
        return new ResponseEntity<>("Product added to cart successfully.", HttpStatus.CREATED);
    }

    public ResponseEntity<?> removeFromCart(Integer productId, Integer quantity) {
        if (quantity < 1)
            return new ResponseEntity<>("Product quantity should be positive value.", HttpStatus.BAD_REQUEST);

        Customer customer = getCustomerInfo();
        Cart cart = customer.getCart();

        if (cart.getItems().isEmpty()) {
            return new ResponseEntity<>("Cart is empty.", HttpStatus.BAD_REQUEST);
        }

        for (CartItem cartItem : customer.getCart().getItems()) {
            if (cartItem.getProduct().getId().equals(productId)) {
                if (cartItem.getQuantity() < quantity) {
                    return new ResponseEntity<>(
                            "Quantity to remove exceeds the product's quantity in the cart.", HttpStatus.BAD_REQUEST);
                }

                cartItem.setQuantity(cartItem.getQuantity() - quantity);
                cartItemRepository.save(cartItem);

                if (cartItem.getQuantity() == 0) {
                    cart.getItems().remove(cartItem);
                    cartRepository.save(cart);
                    cartItemRepository.delete(cartItem);
                } else {
                    cartItemRepository.save(cartItem);
                }

                cart.setTotal(cart.getTotal() - (quantity * cartItem.getProduct().getPrice()));
                cartRepository.save(cart);

                customer.setCart(cart);
                customerRepository.save(customer);
                return new ResponseEntity<>("Product removed from cart successfully.", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("Product not found in cart.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getCart() {
        Customer customer = getCustomerInfo();
        Cart cart = customer.getCart();

        if (cart.getItems().isEmpty()) {
            return new ResponseEntity<>("Cart is empty.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<?> createOrder() {
        Customer customer = getCustomerInfo();

        if (customer.getCart().getItems().isEmpty()) {
            return new ResponseEntity<>("Cart is empty.", HttpStatus.BAD_REQUEST);
        } else if (customer.getAddress().getCountry() == null ||
                customer.getAddress().getCity() == null ||
                customer.getAddress().getState() == null ||
                customer.getAddress().getPostalCode() == null ||
                customer.getAddress().getLine1() == null) {
            return new ResponseEntity<>("Please update full address!", HttpStatus.BAD_REQUEST);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setCart(customer.getCart());
        order.setDate(new Date());
        order.setStatus(OrderStatus.Pending);
        order.setShippingAddress(customer.getAddress());
        orderRepository.save(order);

        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setTotal(0.0);
        cartRepository.save(cart);

        customer.setCart(cart);
        customer.getOrders().add(order);
        customerRepository.save(customer);

        return new ResponseEntity<>("Order created successfully.", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getOrders() {
        Customer customer = getCustomerInfo();

        if (customer.getOrders().isEmpty()) {
            return new ResponseEntity<>("No order placed yet.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customer.getOrders(), HttpStatus.OK);
    }

    public ResponseEntity<?> cancelOrder(@RequestParam Integer orderId) {
        Customer customer = getCustomerInfo();
        for (Order order : customer.getOrders()) {
            if (order.getId().equals(orderId)) {
                if (order.getStatus().equals(OrderStatus.Cancelled)) {
                    return new ResponseEntity<>("Order already cancelled.", HttpStatus.BAD_REQUEST);
                }
                order.setStatus(OrderStatus.Cancelled);
                orderRepository.save(order);
                customerRepository.save(customer);
                return new ResponseEntity<>("Order cancelled successfully.", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("This order id not belongs to you.", HttpStatus.BAD_REQUEST);
    }
}
