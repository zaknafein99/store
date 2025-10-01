# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Common Commands

### Development Startup
```bash
# Backend (requires PostgreSQL running with boutique_db database)
cd backend
./gradlew bootRun

# Frontend (in another terminal)
cd frontend
npm install
npm run dev
```

### Testing
```bash
# Backend tests
cd backend
./gradlew test

# Frontend unit tests  
cd frontend
npm run test:unit
```

### Building
```bash
# Backend build
cd backend
./gradlew build

# Frontend build
cd frontend
npm run build
```

### Single Test Execution
```bash
# Run specific backend test class
cd backend
./gradlew test --tests "DemoApplicationTests"

# Run specific frontend test file
cd frontend
npm run test:unit src/components/__tests__/HelloWorld.spec.js
```

## Architecture Overview

### Monorepo Structure
- `backend/` - Kotlin Spring Boot 3 REST API
- `frontend/` - Vue 3 SPA with Vite dev server

### Backend Architecture (Spring Boot + Kotlin)

**Package Structure:**
- `com.example.boutique.domain` - JPA entities (Product, Customer, Order, OrderItem)
- `com.example.boutique.repository` - Spring Data JPA repositories
- `com.example.boutique.service` - Business logic layer
- `com.example.boutique.web` - REST controllers under `/api` prefix
- `com.example.boutique.security` - JWT authentication (JwtTokenProvider, JwtAuthenticationFilter)
- `com.example.boutique.config` - Spring configuration and seeders
- `com.example.boutique.dto` - Data Transfer Objects

**Key Domain Concepts:**
- Shopping cart is implemented as an Order with status `CART`
- Products support multiple sizes, colors, and photo URLs (stored as collections)
- JWT-based authentication with role-based access control
- MercadoPago integration for payment processing

**Security Model:**
- Public endpoints: `GET /api/products/**`, `POST /api/auth/**`
- Admin endpoints: `/api/admin/**` requires `ROLE_ADMIN`
- All other endpoints require authentication
- JWT tokens stored in `Authorization: Bearer` header

### Frontend Architecture (Vue 3 + Composition API)

**Key Technologies:**
- Vue 3 with Composition API
- Pinia for state management
- Vue Router with lazy-loaded routes
- Tailwind CSS v4 for styling
- Vite dev server with API proxy to backend

**Store Pattern:**
- `auth.js` - JWT token management, login/logout, user state
- `cart.js` - Shopping cart operations, syncs with backend `/api/cart`

**Routing:**
- Public routes: `/`, `/product/:id`, `/login`, `/register`
- Protected routes: `/cart` (requires auth), `/admin/**` (admin panel)
- Route guards check `meta.requiresAuth` (implementation pending)

## Database Configuration

Default PostgreSQL setup (override with environment variables):
- URL: `jdbc:postgresql://localhost:5432/boutique_db` 
- Username: `user`
- Password: `password`

Environment variables for production:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME` 
- `SPRING_DATASOURCE_PASSWORD`
- `APP_JWT_SECRET` (Base64-encoded secret)
- `APP_FRONTEND_URL` (for CORS)

## Known TODOs & Gaps

1. **CustomUserDetailsService** - Currently imported but not implemented; needed for proper Spring Security user loading
2. **OrderStatus.CART** - CartService expects this enum value but it exists in the enum
3. **Route Guards** - Frontend needs navigation guard implementation for `meta.requiresAuth`
4. **Admin Role Mapping** - Customer entity has `isAdmin` field but role mapping to Spring Security authorities needs implementation
5. **Checkout Flow** - CheckoutController and PaymentService.createPreference need to be wired together

## Development Notes

- Backend runs on port 8080, frontend on 5173
- Vite proxies `/api` requests to backend automatically
- JWT tokens stored in localStorage (frontend)
- Tailwind CSS v4 uses `@import "tailwindcss";` in `main.css`
- Admin seeder creates default admin user if configured via `app.admin.email/password`
- MercadoPago SDK v2.5.0 included but needs proper access token configuration

## Access Points

- Application: http://localhost:5173
- API: http://localhost:8080/api
- Admin panel: http://localhost:5173/admin (placeholder UI)