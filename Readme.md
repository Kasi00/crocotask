# Customer Notification Address Facade System

**Status**: ✅ Completed\
**Author**: Luka Vanishvili\
**Tech Stack**: Java (Spring Boot), Thymeleaf, PostgreSQL, Spring Security, HTML/CSS\
**Frontend**: Thymeleaf templates\
**Backend**: REST + MVC architecture\
**Database**: Relational (PostgreSQL)\
**Security**: Role-based with Spring Security,JwtAuthentication

---

## Ὅ8 Project Overview

This system is a centralized customer notification hub designed to manage customer contact information, notification preferences, and delivery statuses across different communication channels (Email, SMS, Postal). It acts as the single source of truth for other microservices to fetch and update customer delivery data securely and efficiently.

---

## ✯ Key Features

### 🧑‍💼 Admin User Management
!!! My Database creditenials are -> url : jdbc:postgresql://localhost:5432/crocobet_db  
username:croco_user  
password:croco123    

To get jwt Token Log in with this 2 admins(Choose one of them)

Admin 1 email:LAdmin@gmail.com password:admin11
  
 Admin 2 email:Luka.vanishvili.2@btu.edu.ge password:testPassword11
- Register and manage admin users
- Secure login with Spring Security
- Update admin passwords
- View/delete admin accounts

### 👥 Customer Management

- Create, edit, and delete customer records
- View all customers in a table
- Store full contact info (email, phone, etc.)

### 📨 Address Management

- Add and manage multiple addresses (Email, SMS, Postal) for each customer
- Update or delete addresses
- View customer-specific address lists

### ⚙️ Notification Preferences

- Record opt-in/opt-out preferences for each customer
- Preferences include Email, SMS, and Promotional messages
- Easy form to update preferences

### 📊 Notification Tracking

- Track delivery status: Delivered, Failed, Pending
- View delivery history per customer

### 🔍 Filtering & Search

- Filter and search customers by name, contact, or preference
- Paginated view support (optional if implemented)

### 📊 Reporting (Basic)

- View count of opt-in customers per notification type
- Delivery success rate insights (basic form)

---

## 🔐 Security

- Role-based access using Spring Security
- All routes under `/admin/**` protected
- In-memory or database-backed authentication
- Password update functionality for security hygiene

#### This Should be tested in postman or swagger All Api informations shown down below  ######
# 🔐 User Authentication & Admin Management API

Base URL: `/api/auth`

> ⚠️ All endpoints except `/login` require an **Authorization** header with a valid **Bearer token** from an authenticated admin.

---

## 🛂 Login

### `POST /api/auth/login`

Logs in an admin user and returns a JWT token.

**Request Body:**

```json
{
  "email": "LAdmin@gmail.com",
  "password": "admin11"
}
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 👤 Get All Admin Users

### `GET /api/auth/all`

**Authorization:** `Bearer <JWT>`

Retrieves a list of all registered admin users.

**Response:**

```json
[
  {
    "id": 1,
    "email": "admin1@example.com"
    
  },
  {
    "id": 2,
    "email": "admin2@example.com"
  }
]
```

---

## ➕ Register New Admin

### `POST /api/auth/register`

**Authorization:** `Bearer <JWT>`

Registers a new admin account.

**Request Body:**

```json
{
  "email": "newadmin@example.com",
  "password": "strongPassword123"
}
```

**Response:**

```http
200 OK
```

---

## 🔒 Update Admin Password

### `PUT /api/auth/{userId}/password`

**Authorization:** `Bearer <JWT>`

Updates the password for a given admin.

**Request Body:**

```json
{
  "newPassword": "newStrongPass456"
}
```

**Response:**

```http
200 OK
```

---

## 🗑️ Delete Admin

### `DELETE /api/auth/{userId}/delete`

**Authorization:** `Bearer <JWT>`

Deletes the admin with the given `userId`.

**Response:**

```http
200 OK
```

---

## 🧪 Testing Notes

- Use Postman or Swagger for endpoint testing.
- Ensure Spring Security is correctly configured for role-based access.
- Check JWT expiration and refresh token handling if implemented.
- Log in as a valid admin to access protected endpoints.

# ReportController API Documentation

## Overview

`ReportController` is a REST controller that provides endpoints to retrieve various types of reports related to notifications and customer opt-in statuses.  
All endpoints are secured and accessible **only** to users with the **ADMIN** role.
## Endpoints

### 1. Get Notification Report
- **URL:** `/api/reports/notifications`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Description:** Retrieves a report summarizing notifications.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** JSON object of type `ReportResponse`

---

### 2. Get Customer Opt-In Report

- **URL:** `/api/reports/customer-opt-in`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Description:** Retrieves a report about customer opt-in statuses.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** JSON object of type `CustomerOptInResponse`

---

### 3. Get Notification Status Report

- **URL:** `/api/reports/notification-status`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Description:** Retrieves a detailed report on notification statuses.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** JSON object of type `NotificationStatusReportResponse`

---

## Security

- All endpoints use method-level security with the `@PreAuthorize` annotation, restricting access to users with the `ADMIN` role.
- Unauthorized requests will receive a `403 Forbidden` response.

---

## Dependencies

- `ReportService` is the service layer responsible for fetching the report data.
- DTOs used in responses:
    - `ReportResponse`
    - `CustomerOptInResponse`
    - `NotificationStatusReportResponse`

---
## Example Request
curl -H "Authorization: Bearer <ADMIN_JWT_TOKEN>" \
-X GET https://yourdomain.com/api/reports/notifications
---

# NotificationController API Documentation

## Overview

The `NotificationController` manages notification statuses related to customers.  
All endpoints require users to have the **ADMIN** role.


## Endpoints

### 1. Get Notification Statuses for a Customer

- **URL:** `/api/notification/{customerId}`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `customerId` (Long): The unique identifier of the customer.
- **Description:** Retrieves all notification statuses associated with the specified customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** JSON array of `NotificationStatusResponse` objects.

---

### 2. Track Notification Status for a Customer

- **URL:** `/api/notification/{customerId}/trackStatus`
- **Method:** `POST`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `customerId` (Long): The unique identifier of the customer.
- **Request Body:** JSON object matching the `TrackStatusRequest` DTO containing tracking details.
- **Description:** Records or updates notification status tracking for the specified customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty (no content)

---

## Security

- All endpoints are protected with Spring Security's `@PreAuthorize` annotation allowing only users with the `ADMIN` role.
- Unauthorized access attempts will receive a `403 Forbidden` response.

---

## Dependencies

- `NotificationService`: Service layer responsible for business logic.
- DTO classes:
    - `NotificationStatusResponse`
    - `TrackStatusRequest`

---

## Example Requests

**GET notification statuses for customer with ID 42**

curl -H "Authorization: Bearer <ADMIN_JWT_TOKEN>" \
-X GET https://yourdomain.com/api/notification


# CustomerController API Documentation

## Overview

`CustomerController` manages customer-related operations including retrieval, filtering, creation, update, batch updates, preference management, and deletion.  
All endpoints are secured and accessible **only** to users with the **ADMIN** role.

---## Endpoints

### 1. Filter Customers
- **URL:** `/api/customer/filter`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Request Body:** JSON object of type `CustomerFilterRequest` containing filter criteria.
- **Description:** Returns a paginated list of customers matching the filter criteria.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** `Page<CustomerListResponse>`

---

### 2. Get Detailed Customer Profile

- **URL:** `/api/customer/{id}/customerProfile`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): Customer's unique identifier.
- **Description:** Retrieves detailed profile information of the specified customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** `CustomerDetailedResponse`

---

### 3. Get Customer Preferences

- **URL:** `/api/customer/{id}/preferences`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): Customer's unique identifier.
- **Description:** Retrieves the preference settings of the specified customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** `PreferenceResponse`

---

### 4. Get All Customers

- **URL:** `/api/customer`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Description:** Retrieves a list of all customers.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** List of `CustomerResponse`

---

### 5. Add New Customer

- **URL:** `/api/customer/add`
- **Method:** `POST`
- **Authorization:** Required (`ADMIN` role)
- **Request Body:** JSON object of type `CustomerRequest` with new customer details.
- **Description:** Adds a new customer to the system.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

### 6. Batch Update Customers

- **URL:** `/api/customer/batch-update`
- **Method:** `POST`
- **Authorization:** Required (`ADMIN` role)
- **Request Body:** JSON object of type `BatchCustomerUpdateRequest` containing batch update details.and it contains List of SingleCustomerUpdate request which contains :private Long customerId;
  private String fullName;
  private String phone;
  private boolean emailEnabled;
  private boolean smsEnabled;
  private boolean promotionalEnabled;
- **Description:** Performs batch updates on multiple customers.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** `BatchUpdateResponse`

---

### 7. Update Customer

- **URL:** `/api/customer/{id}/update`
- **Method:** `PUT`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): Customer's unique identifier.
- **Request Body:** JSON object of type `CustomerRequest` with updated customer data.
- **Description:** Updates the details of an existing customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

### 8. Update Customer Preferences

- **URL:** `/api/customer/{id}/updatePreference`
- **Method:** `PUT`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): Customer's unique identifier.
- **Request Body:** JSON object of type `UpdatePreferenceRequest`.
- **Description:** Updates the preference settings of a customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

### 9. Delete Customer

- **URL:** `/api/customer/{id}/delete`
- **Method:** `DELETE`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): Customer's unique identifier.
- **Description:** Deletes the specified customer from the system.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

## Security

- All endpoints are protected with the `@PreAuthorize(ADMIN)` annotation.
- Only users with `ADMIN` role can access these endpoints.
- Unauthorized access attempts will return `403 Forbidden`.

---

## Dependencies

- Service: `CustomerService`
- DTOs:
    - `CustomerFilterRequest`
    - `CustomerListResponse`
    - `CustomerDetailedResponse`
    - `PreferenceResponse`
    - `CustomerResponse`
    - `CustomerRequest`
    - `BatchCustomerUpdateRequest`
    - `BatchUpdateResponse`
    - `UpdatePreferenceRequest`

---

## Example Requests

**Filter customers**

---bash
curl -H "Authorization: Bearer <ADMIN_JWT_TOKEN>" \
     -H "Content-Type: application/json" \
     -X GET https://yourdomain.com/api/customer/filter \


Get detailed profile for customer with ID 10
curl -H "Authorization: Bearer <ADMIN_JWT_TOKEN>" \
-X GET https://yourdomain.com/api/customer/10/customerProfile


Add a new customer

curl -H "Authorization: Bearer <ADMIN_JWT_TOKEN>" \
-H "Content-Type: application/json" \
-X POST https://yourdomain.com/api/customer/add \
-d '{
"name": "John Doe",
"email": "john.doe@example.com",
"phone": "+1234567890"
}'




##AddressController API Documentation

## Overview

The `AddressController` handles all operations related to customer addresses, including retrieval, creation, update, and deletion of address records.  
All endpoints require users to have the **ADMIN** role.

---

## Endpoints

### 1. Get Addresses by Customer

- **URL:** `/api/address/{customerId}`
- **Method:** `GET`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `customerId` (Long): The unique identifier of the customer.
- **Description:** Retrieves all addresses associated with the specified customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** `List<AddressResponse>`

---

### 2. Add Address for Customer

- **URL:** `/api/address/{customerId}/add`
- **Method:** `POST`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `customerId` (Long): The unique identifier of the customer.
- **Request Body:** JSON object of type `AddressRequest` containing address details.
- **Description:** Adds a new address for the specified customer.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

### 3. Update Address

- **URL:** `/api/address/{id}/update`
- **Method:** `PUT`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): The unique identifier of the address to be updated.
- **Request Body:** JSON object of type `UpdateAddressRequest` with updated address data.
- **Description:** Updates an existing address record.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

### 4. Delete Address

- **URL:** `/api/address/{id}`
- **Method:** `DELETE`
- **Authorization:** Required (`ADMIN` role)
- **Path Parameters:**
    - `id` (Long): The unique identifier of the address to delete.
- **Description:** Deletes the specified address.
- **Response:**
    - **Status:** `200 OK`
    - **Body:** Empty

---

## Security

- All endpoints are protected using Spring Security with `@PreAuthorize(ADMIN)`.
- Unauthorized access will result in a `403 Forbidden` response.

---

## Dependencies

- `AddressService`: Business logic for address management.
- DTOs:
    - `AddressRequest`
    - `UpdateAddressRequest`
    - `AddressResponse`

---
Ensure that all request bodies match the structure defined in the corresponding DTOs.

All endpoints respond with 200 OK upon successful operation, with no body unless otherwise noted.

JWT tokens with proper admin roles must be used for authentication.


# AdminUIController Documentation

## Overview

`AdminUIController` is a Spring MVC controller for handling all admin-related web pages using Thymeleaf templates. It allows admin users to perform customer management, login, preference updates, view addresses and notification statuses, and more via a web UI.

This controller does **not** expose REST APIs; it serves Thymeleaf views instead.

---

## URL Prefix

There is **no base URL prefix** for this controller. All mappings are absolute (e.g., `/login`, `/customers`).

---

## Endpoints

---

### 🔐 Login Page

#### `GET /login`
- **Description:** Displays the login page.
- **Returns:** `login.html` template.

#### `POST /login`
- **Description:** Authenticates user using email and password.
- **Params:**
    - `email` (String)
    - `password` (String)
- **On Success:** Redirects to `/customers`
- **On Failure:** Redirects to `/login?error=true`

---

### 👥 Customer Management

#### `GET /customers`
- **Description:** Displays a list of all customers.
- **Model Attributes:**
    - `customers` – List of `CustomerResponse`
- **Returns:** `customers.html`

#### `GET /customers/new`
- **Description:** Shows form to create a new customer.
- **Model Attributes:**
    - `customerRequest` – Empty form DTO.
- **Returns:** `customers-new.html`

#### `POST /customers/new`
- **Description:** Submits new customer data to the backend.
- **Request Body:** `CustomerRequest` (via form binding)
- **Redirects:** `/customers`

#### `GET /customers/edit/{id}`
- **Path Param:** `id` – Customer ID
- **Description:** Shows form to edit a specific customer.
- **Model Attributes:**
    - `customer` – `CustomerPanelDto`
- **Returns:** `customer-form.html`

#### `POST /customers/update/{id}`
- **Path Param:** `id` – Customer ID
- **Form Params:**
    - `fullName`, `phone`, `email`
- **Description:** Updates customer data.
- **Redirects:** `/customers`

#### `GET /customers/delete/{id}`
- **Path Param:** `id` – Customer ID
- **Description:** Deletes a customer.
- **Redirects:** `/customers`

---

### ⚙️ Preferences

#### `GET /customers/preferences/{id}`
- **Path Param:** `id` – Customer ID
- **Model Attributes:**
    - `preferences` – `PreferenceResponse`
    - `customerId` – Long
- **Returns:** `preferences.html`

#### `POST /customers/preferences/update/{id}`
- **Path Param:** `id` – Customer ID
- **Form Data:** `UpdatePreferenceRequest` (via `@ModelAttribute`)
- **Redirects:** `/customers`

---

### 🏠 Address Management

#### `GET /customers/{id}/addresses`
- **Path Param:** `id` – Customer ID
- **Description:** Displays addresses associated with the customer.
- **Model Attributes:**
    - `addresses` – List of `AddressResponse`
    - `customerId` – Long
- **Returns:** `addresses.html`

---

### 📨 Notification Status

#### `GET /customers/{id}/notifications`
- **Path Param:** `id` – Customer ID
- **Description:** Shows notification statuses for the customer.
- **Model Attributes:**
    - `statuses` – List of `NotificationStatusResponse`
    - `customerId` – Long
- **Returns:** `notifications.html`

---

## Services Used

- **`CustomerService`** – Business logic for managing customer entities.
- **`AddressService`** – Handles address operations for customers.
- **`NotificationService`** – Retrieves notification statuses for customers.
- **`UserService`** – Authenticates users and generates JWT tokens.

---

## Session Attributes

The following session attributes are set on successful login:
- `JWT_TOKEN` – The user's access token.
- `USER_EMAIL` – The authenticated user's email.

---

## Thymeleaf Templates Used

| URL Path                          | Template Name     |
|----------------------------------|-------------------|
| `/login`                         | `login.html`      |
| `/customers`                     | `customers.html`  |
| `/customers/new`                 | `customers-new.html` |
| `/customers/edit/{id}`          | `customer-form.html` |
| `/customers/preferences/{id}`   | `preferences.html` |
| `/customers/{id}/addresses`     | `addresses.html`   |
| `/customers/{id}/notifications` | `notifications.html` |

---

## Notes

- All customer-related routes are protected logically; ensure security is enforced via filters or configuration.
- The controller prints debug information to the console. Consider replacing with a proper logging mechanism in production.
- Any service exceptions are caught and basic fallback behavior is provided (e.g., empty list rendering).

---

## Example Login Flow

1. **User accesses** `/login`
2. **User submits** login form with email/password
3. `POST /login` calls `UserService.login()`
4. If successful, JWT token and email are saved in the session.
5. Redirects to `/customers`





---

## 🗅️ User Interface (Thymeleaf Pages)

| Page                 | Path                            | Description             |
|----------------------|---------------------------------|-------------------------|
| `customers.html`     | `/customers`                    | List all customers      |
| `customer-form.html` | `/customers/new` & `/edit/{id}` | Add/edit customer       |
| `preferences.html`   | `/customers/preferences/{id}`   | View/update preferences |
| `addresses.html`     | `/customers/{id}/addresses`     | View all addresses      |
| `notifications.html` | `/customers/{id}/notifications` | Track delivery statuses |
| `login.html`         | `/login`                        | Login                   |


---

## 🛠️ Technical Architecture

```
Frontend (Thymeleaf)
      ↓
AdminController (Spring MVC)
      ↓
Service Layer
      ↓
DTOs ↔ Entities ↔ JPA Repository
      ↓
PostgreSQL (Database)
```

- Controller layer handles UI requests and responses
- Services contain business logic and delegate to repository
- DTOs used for clean communication between layers
- Spring Security guards protected routes

---

## 📁 Key Packages

```
com.example.crocotask
├── config # Spring configuration (e.g., CORS, Security, etc.)
├── controller # Spring MVC controllers (Admin-facing endpoints)
├── dto # Data Transfer Objects used between layers
├── entity # JPA entity classes representing DB models
├── exception # Custom exceptions and exception handlers
├── implementations # Service implementation classes
├── mapper # Mappers to convert between entities and DTOs
├── repository # JPA repositories for DB access
├── security # Spring Security configuration and filters
├── service # Service interfaces (business logic abstraction)
├── specifications # JPA Specifications for advanced filtering/searching
└── CrocotaskApplication # Main Spring Boot application entry point


resources
├── static # Static files (CSS, JS)
│
└── templates # Thymeleaf templates
├── addresses.html
├── customer-form.html
├── customers.html
├── customers-new.html
├── login.html
├── notifications.html
└── preferences.html

---

## 🚀 How to Run

```bash
# Run Spring Boot
./mvnw spring-boot:run
# OR
./gradlew bootRun
```

Then open your browser:

```
http://localhost:8080/login
```

Use an admin account to log in (or in-memory default: `email / password` if configured).

---


---

