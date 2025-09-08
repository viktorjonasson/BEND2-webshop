# BEND2 Webshop

A Spring Boot webshop application that provides a shopping experience with user authentication, product browsing, and purchase history.

## Features

- **User Authentication**: Secure login and registration system with Spring Security
- **Product Catalog**: Browse products imported from FakeStore API
- **Purchase Management**: Track and view purchase history

## Tech Stack

- **Backend**: Spring Boot, Java 17
- **Database**: MySQL with JPA/Hibernate
- **Security**: Spring Security
- **Frontend**: Thymeleaf, HTML/CSS
- **Build Tool**: Gradle
- **External API**: FakeStore API for product data

## Getting Started

### Prerequisites

- Java 17 or higher
- MySQL database

## Key Components

### Models
- **Product**: Stores product information with ratings
- **AppUser**: User accounts with authentication
- **Purchase**: Links users with their purchased products
- **UserRole**: Role-based authorization

### Controllers
- **ProductsController**: Display product catalog
- **PurchasesController**: Manage user purchases
- **LoginController**: Handle authentication
- **RegisterController**: User registration

### Services
- **ProductImportService**: Fetches products from FakeStore API
- **ProductService**: Product business logic
- **PurchaseService**: Purchase management
- **UserService**: User management

## Data Initialization

The application automatically seeds the database on startup with:
- Default user accounts and roles
- Product data from FakeStore API
- Sample purchase records