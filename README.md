# e-commerce-platform-spring-boot-backend

## Contribute
If you want to contribute, feel free to reach out to me via email at [nadimnesar.cse@gmail.com](mailto:nadimnesar.cse@gmail.com) or text me [Linkedin profile](https://www.linkedin.com/in/nadimnesar/).

## Entity Relation Diagram
<img src="/src/main/resources/static/img/e-commerce-diagram-v4.png" alt="diagram">

## Endpoints
### Authentication
1. `POST /api/auth/register` : Register as a Customer
2. `POST /api/auth/seller/register` : Register as a Seller
3. `POST /api/auth/login` : Login and get JWT token
### Products
1. `GET /api/products/all` : Get All Products
2. `GET /api/products/all?pageNo={}&limit={}` : Get All Products with Pagination
3. `GET /api/products/category/{categoryName}` : Get Product By Category Name
4. `GET /api/products/category/{categoryName}?pageNo={}&limit={}` : Get Product By Category Name with Pagination
5. `GET /api/products/{productId}` : Get Product By ID
### Seller
1. `POST /api/seller/addProduct` : Add a new product, can access by only seller.