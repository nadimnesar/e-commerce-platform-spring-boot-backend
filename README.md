# ECommerce Platform Spring Boot Backend

This is a scalable backend for an e-commerce platform using Spring Boot. It provides a comprehensive set of APIs to
manage essential functionalities of an online e-commerce store.

## Entity Relation Diagram

<img src="/src/main/resources/static/img/e-commerce-diagram-v5.png" alt="diagram">

## RESTful Endpoints

### Base Url

- localhost:8080

### Authentication

- `POST /api/auth/register`
    - Register as a customer
- `POST /api/auth/seller/register`
    - Register as a seller
- `POST /api/auth/login`
    - Login and get new JWT token

### Products

- `GET /api/products/all?pageNo={}&limit={}`
    - Get all products with pagination.
- `GET /api/products/category/{categoryName}?pageNo={}&limit={}`
    - Get all products by category name with pagination.
- `GET /api/products/{productId}`
    - Get product by id.
- `GET /api/products/seller/{sellerId}?pageNo={}&limit={}`
  - Get all products by seller id.

### Seller

- `POST /api/seller/addProduct`
    - Add a new product, can access by only seller.
- `GET /api/seller/products`
  - Show my products, can view own products only.
- `PUT /api/seller/updateProduct?id={}`
  - Update a product by id, can update own product only.
- `DELETE /api/seller/deleteProduct?id={}`
  - Delete a product by id, can delete own product only.

### Customer

- `POST /api/customer/updateAddress`
  - Update address, without address can't order.
- `POST /api/customer/addToCart?productId={}&quantity={}`
  - Add to cart by product id.
- `DELETE /api/customer/removeFromCart?productId={}&quantity={}`
  - Remove from cart by product id.
- `GET /api/customer/getCart`
  - Get Cart Details
- Place order, all items from cart.
- Cancel order by id.
- Get all orders, can view own orders only.