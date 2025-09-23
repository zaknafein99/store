# Store — Full Stack App

Vue 3 + Spring Boot boutique shop with products, auth, cart, and admin tooling.

## Overview

- Frontend: Vue 3, Vite, Pinia, Vue Router, Tailwind
- Backend: Kotlin, Spring Boot 3, Spring Security (JWT), JPA, PostgreSQL, MercadoPago SDK

## Quickstart

- Backend
  - Ensure PostgreSQL is running with a `boutique_db` database and credentials in `backend/src/main/resources/application.properties`
  - Java 21 toolchain
  - Run:

    ```bash
    cd backend
    ./gradlew bootRun
    ```

- Frontend
  - Node 20+
  - Run:

    ```bash
    cd frontend
    npm install
    npm run dev
    ```

- Access
  - App: http://localhost:5173 (Vite proxies `/api` to `http://localhost:8080`)

## Key Endpoints

- Public: `GET /api/products`, `GET /api/products/{id}`
- Auth: `POST /api/auth/register`, `POST /api/auth/login` → returns JWT
- Cart (auth): `GET /api/cart`, `POST /api/cart/items`, `DELETE /api/cart/items/{productId}`
- Admin (ROLE_ADMIN): `POST/PUT/DELETE /api/admin/products` ...

## Notes

- JWT filter null-safety adjustments in place
- MercadoPago preference creation available (wire to checkout controller)
- Frontend uses Tailwind via `@tailwindcss/postcss`

## Next Steps

- Implement `CustomUserDetailsService` and define roles/authorities
- Add `CART` to `OrderStatus` (or adjust CartService)
- Add router guard for `meta.requiresAuth`
- Replace admin placeholders with real authenticated calls

