# ECommerce Platform Spring Boot Backend

This project is a scalable backend for an e-commerce platform built with Spring Boot, providing a RESTful API to manage key e-commerce functionalities. 
This backend supports both customers and sellers, including features like user authentication, product management, shopping cart, and order processing.

## Features

* User Registration & Authentication: Supports both customer and seller roles with JWT-based authentication.
* Product Management: CRUD operations for sellers to manage products.
* Order Management: Add items to the cart, create orders, and view order history.
* Inventory Management: Track stock for products.
* Role-Based Access: Endpoints and features are restricted based on user roles.

## Entity Relation Diagram

<img src="/src/main/resources/static/img/e-commerce-diagram-v6.png" alt="diagram">

## RESTful API Endpoints

### Authentication

- `POST /api/auth/register`
    - Register as a customer
- `POST /api/auth/seller/register`
    - Register as a seller
- `POST /api/auth/login`
    - Login to receive a JWT token for authenticated requests.

### Products

- `GET /api/products/all?pageNo={}&limit={}`
    - Retrieves a paginated list of all products.
- `GET /api/products/category/{categoryName}?pageNo={}&limit={}`
    - Retrieves products by category name with pagination.
- `GET /api/products/{productId}`
    - Retrieves details of a specific product by ID.
- `GET /api/products/seller/{sellerId}?pageNo={}&limit={}`
  - Retrieves products listed by a specific seller.

### Seller

- `POST /api/seller/addProduct`
    - Add a new product (seller access only).
- `GET /api/seller/products`
  - View all products created by the logged-in seller.
- `PUT /api/seller/updateProduct?id={}`
  - Update a product by ID (seller access only).
- `DELETE /api/seller/deleteProduct?id={}`
  - Delete a product by ID (seller access only).
- `POST /api/seller/addStock?productId={}&stock={}`
  - Increase stock for a specific product.
- `DELETE /api/seller/removeStock?productId={}&stock={}`
  - Decrease stock for a specific product.

### Customer

- `POST /api/customer/updateAddress`
  - Update address; an address is required to place orders.
- `POST /api/customer/addToCart?productId={}&quantity={}`
  - Add an item to the shopping cart.
- `DELETE /api/customer/removeFromCart?productId={}&quantity={}`
  - Remove an item or reduce quantity in the shopping cart.
- `GET /api/customer/getCart`
  - Retrieve details of the current cart.
- `POST /api/customer/createOrder`
  - Place an order with all items currently in the cart.
- `DELETE /api/customer/cancelOrder?orderId={}`
  - Cancel an order by its ID.
- `GET /api/customer/getOrders`
  - Retrieve all orders made by the logged-in customer.