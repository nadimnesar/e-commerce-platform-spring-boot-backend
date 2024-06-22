# e-commerce-platform-spring-boot-backend

## Contribute
If you want to contribute, feel free to reach out to me via email at [nadimnesar.cse@gmail.com](mailto:nadimnesar.cse@gmail.com) or text me [Linkedin profile](https://www.linkedin.com/in/nadimnesar/).

## Entity Relation Diagram
<img src="/src/main/resources/static/img/e-commerce-diagram.png" alt="diagram">

## Endpoints
### Products
1. `GET /api/products/all` : Get All Products
2. `GET /api/products/all?pageNo={}&limit={}` : Get All Products with Pagination
3. `GET /api/products/{categoryName}` : Get Product By Category Name
4. `GET /api/products/{categoryName}?pageNo={}&limit={}` : Get Product By Category Name with Pagination
5. `GET /api/product/{productId}` : Get Product By ID
### Seller