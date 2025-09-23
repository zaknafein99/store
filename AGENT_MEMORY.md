Project Memory — Store (Vue + Spring Boot)

Summary
- Purpose: Boutique e-commerce with browse, cart, auth, and admin CRUD.
- Tech:
  - Frontend: Vue 3, Vite, Pinia, Vue Router, Tailwind.
  - Backend: Kotlin, Spring Boot 3, Spring Security (JWT), JPA, PostgreSQL, MercadoPago SDK.
- Layout:
  - `frontend/` Vite app (views, stores, router).
  - `backend/` Spring app (domain, repos, services, controllers, security).

Backend
- Build/Run
  - Entry: backend/src/main/kotlin/com/example/demo/DemoApplication.kt:1
  - Gradle, Java 21 (toolchain) — backend/build.gradle:12
  - Run: `cd backend && ./gradlew bootRun` (PostgreSQL required).
- Config
  - DB/JWT/MercadoPago: backend/src/main/resources/application.properties:1
  - Dev DB: `jdbc:postgresql://localhost:5432/boutique_db` user `user` pass `password`.
- Domain
  - Product/Customer/Order/OrderItem — collections for sizes/colors/photos.
- Repos
  - ProductRepository, CustomerRepository, OrderRepository (findByCustomerAndStatus).
- Services
  - Product, Customer (register + hashing), Cart (cart per user), Admin (CRUD), Payment (MercadoPago preference).
- Web/API
  - Products: GET /api/products, GET /api/products/{id}
  - Auth: POST /api/auth/register, POST /api/auth/login → JWT
  - Cart: GET /api/cart, POST /api/cart/items, DELETE /api/cart/items/{productId}
  - Admin: POST/PUT/DELETE /api/admin/products(...)
  - Checkout: POST /api/checkout/create-preference (placeholder)
- Security
  - Paths: `/api/auth/**` + GET products → public; `/api/admin/**` → ROLE_ADMIN; others authenticated.
  - JWT: JwtTokenProvider, JwtAuthenticationFilter.
  - Updated: JwtAuthenticationFilter null-safety fixed (guards nullable token).
  - Missing: CustomUserDetailsService (imported but not implemented).

Frontend
- Run
  - `cd frontend && npm install && npm run dev`
  - Vite proxy: `/api` → `http://localhost:8080` (frontend/vite.config.js:1)
- Router
  - `/`, `/product/:id`, `/cart` (requiresAuth), `/login`, `/register`, `/admin/*`.
- Stores
  - `auth` (JWT in localStorage, login/register calls).
  - `cart` (uses Bearer token for /api/cart).
- Views
  - Home/Product detail (public fetch), Cart (auth cart), Admin (placeholders for write ops).
- Recent frontend changes
  - PostCSS: switched to `@tailwindcss/postcss` plugin; config simplified — frontend/postcss.config.js:1
  - Removed `vite-plugin-vue-devtools` from plugins for stability — frontend/vite.config.js:1
  - Dependencies set to `latest` by request — frontend/package.json:1
  - Added ignores: `.env.local`, `.env.*.local`, `.eslintcache` — frontend/.gitignore:1

Known Gaps / TODOs
- Implement CustomUserDetailsService (load by email, expose authorities).
- OrderStatus mismatch: CartService expects `CART`; enum lacks it — backend/src/main/kotlin/com/example/boutique/domain/Order.kt:1
- Roles: Customer lacks roles/admin flag; add and map to authorities for `/api/admin/**`.
- Checkout: Wire CheckoutController to PaymentService.createPreference using current cart Order entity.
- Frontend guard: Add router navigation guard for `meta.requiresAuth`.
- Admin UI: Replace placeholders with authenticated calls (Bearer + role).

Quickstart
- Backend
  - Ensure Postgres is running and credentials match properties.
  - `cd backend && ./gradlew bootRun`
- Frontend
  - `cd frontend && npm install && npm run dev`
- Access
  - App: http://localhost:5173 (proxied API at :8080)

Notable Versions/Adjustments
- Java toolchain: 21 (updated).
- MercadoPago SDK: 2.6.0 (resolves from mavenCentral).
- Kotlin/JWT: Jwt null-safety fix in filter.

File Pointers
- Security config: backend/src/main/kotlin/com/example/boutique/config/SecurityConfig.kt:1
- JWT: backend/src/main/kotlin/com/example/boutique/security/JwtTokenProvider.kt:1
- JWT Filter: backend/src/main/kotlin/com/example/boutique/security/JwtAuthenticationFilter.kt:1
- Controllers: backend/src/main/kotlin/com/example/boutique/web/*.kt
- Entities: backend/src/main/kotlin/com/example/boutique/domain/*.kt
- Frontend router: frontend/src/router/index.js:1
- Frontend stores: frontend/src/stores/auth.js:1, frontend/src/stores/cart.js:1
