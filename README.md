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
- Add router guard for `meta.requiresAuth`
- Replace admin placeholders with real authenticated calls

=======
The Boutique — Full‑Stack Store

A small full‑stack e‑commerce demo. The frontend is a Vue 3 app (Vite, Pinia, Vue Router, Tailwind CSS). The backend is a Kotlin + Spring Boot API backed by PostgreSQL with JWT authentication and a simple checkout flow.

**Monorepo Layout**
- `frontend/` — Vue 3 + Vite app. Dev server proxies API calls to the backend at `/api`.
- `backend/` — Spring Boot service (Gradle). Exposes REST endpoints under `/api` and connects to PostgreSQL.

**Tech Stack**
- Frontend: Vue 3, Vite, Pinia, Vue Router, Tailwind CSS v4
- Backend: Kotlin, Spring Boot 3 (Web, Security, Data JPA), JJWT
- Database: PostgreSQL

**Requirements**
- Node.js >= 20
- Java >= 21 (Temurin/Zulu/etc.)
- PostgreSQL >= 14

**Quickstart (Development)**
- Start PostgreSQL and create a database/user that matches your local config.
  - Defaults in `backend/src/main/resources/application.properties`:
    - `spring.datasource.url=jdbc:postgresql://localhost:5432/boutique_db`
    - `spring.datasource.username=user`
    - `spring.datasource.password=password`
  - You can override any Spring property with environment variables (e.g. `SPRING_DATASOURCE_URL`).
- Run the backend API:
  - `cd backend`
  - `./gradlew bootRun`
  - API runs on `http://localhost:8080`
- Run the frontend app:
  - `cd frontend`
  - `npm install`
  - `npm run dev`
  - App runs on `http://localhost:5173` and proxies `/api` to the backend.

Open `http://localhost:5173` in your browser.

**Configuration**
Core properties (dev defaults live in `backend/src/main/resources/application.properties`):
- `spring.datasource.url` — PostgreSQL JDBC URL
- `spring.datasource.username` — DB user
- `spring.datasource.password` — DB password
- `app.jwt.secret` — Base64 secret for signing JWTs (use a long random value in production)
- `app.jwt.expiration-in-ms` — Token lifetime in milliseconds
- `mercadopago.access.token` — Access token for payments (dev placeholder)
- `app.frontend.url` — Allowed frontend origin

Seed helpers (optional):
- `app.admin.email`, `app.admin.password` — Used by startup seeders to create a dev admin user if missing.

**Build (Production)**
- Frontend: `cd frontend && npm run build` produces `frontend/dist`.
- Backend: `cd backend && ./gradlew build` produces a runnable jar under `backend/build/libs/`.

Serving options:
- Serve the frontend via any static host (e.g. NGINX, Netlify) and point it at the backend (`/api`).
- Or wire the backend to serve `frontend/dist` (copy files to a static resources folder or configure a resource handler).

**Tests**
- Frontend unit tests: `cd frontend && npm run test:unit`
- Backend tests: `cd backend && ./gradlew test`

**Notes & Gotchas**
- Tailwind CSS v4 is used in the frontend. Global styles are imported with `@import "tailwindcss";` in `frontend/src/assets/main.css`. Avoid heavy `@apply` usage inside that file to prevent build‑time errors.
- The Vite dev server proxies `/api` to `http://localhost:8080` (see `frontend/vite.config.js`). Ensure the backend is running when developing the frontend.

**API Overview**
The UI calls endpoints under `/api` (for example, the home view loads products from `GET /api/products`). Explore controllers and routes in `backend/src/main/kotlin` for the full list.

**Troubleshooting**
- CSS appears unstyled: ensure the frontend build completes without Tailwind errors and that `frontend/src/assets/main.css` uses the v4 import style.
- 401/403 responses: verify `app.jwt.secret` and login/registration flow; delete your local token and re‑login.
- DB connection errors: update `spring.datasource.*` to match your local PostgreSQL setup.

This project is for learning/demo purposes.

**How to Deploy**
- Frontend only (static hosting):
  - Run `cd frontend && npm ci && npm run build`.
  - Upload `frontend/dist` to your static host (e.g., Netlify, Vercel, S3/CloudFront, NGINX).
  - Configure the host to proxy `/api` to your backend origin.
- Backend (Jar on a VM/container):
  - Provision PostgreSQL and set env vars for Spring:
    - `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
    - `APP_JWT_SECRET`, `APP_JWT_EXPIRATION_IN_MS`
    - `APP_FRONTEND_URL` (CORS)
  - Build the jar: `cd backend && ./gradlew bootJar`.
  - Run: `java -jar build/libs/*.jar` (use a process manager like systemd or run in a container).
- One‑server setup (serve SPA from backend):
  - Build frontend: `cd frontend && npm ci && npm run build`.
  - Copy `frontend/dist` into a Spring static resources directory or configure a `ResourceHandler` to serve it.
  - Ensure SPA routing falls back to `index.html` for unknown paths.