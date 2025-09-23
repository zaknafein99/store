# Frontend (Vue 3 + Vite)

Modern Vue 3 app using Vite, Pinia, Vue Router, and Tailwind for an e‑commerce boutique UI.

## Features

- Product list and detail pages (public)
- Auth (login/register) with JWT persisted in `localStorage`
- Cart view backed by protected `/api/cart` endpoints
- Admin area scaffolding (CRUD placeholders)

## Tech Stack

- Vue 3, Pinia, Vue Router
- Vite dev/build
- Tailwind CSS (via `@tailwindcss/postcss` + `autoprefixer`)

## Dev Setup

- Node 20+ recommended
- Install deps and run dev server:

```bash
npm install
npm run dev
```

- Vite proxy forwards `/api` → `http://localhost:8080` (Spring Boot)

## Scripts

- `npm run dev` — start dev server
- `npm run build` — production build
- `npm run preview` — preview production build
- `npm run test:unit` — run unit tests (Vitest)

## Environment

- Dev server runs at `http://localhost:5173`
- Configure backend target in `vite.config.js` if needed
- Common ignores: `.env.local`, `.env.*.local`, `.eslintcache`

## Tailwind Notes

- Tailwind is enabled via `@tailwindcss/postcss` in `postcss.config.js`
- Entry CSS: `src/assets/main.css` (includes `@tailwind base`, `components`, `utilities`)

## Troubleshooting

- If CSS fails to load or shows MIME errors:
  - Remove Vite cache: `rm -rf node_modules/.vite`
  - Reinstall: `rm -rf node_modules package-lock.json && npm install`
  - Restart `npm run dev` and hard refresh the browser

## Project Structure

- `src/main.js` — app bootstrap
- `src/router/` — routes (public + requiresAuth)
- `src/stores/` — `auth` (JWT), `cart` (protected calls)
- `src/views/` — Home, ProductDetail, Cart, Auth, Admin/*
