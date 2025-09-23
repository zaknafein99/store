<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

function handleLogout() {
  authStore.logout()
}
</script>

<template>
  <div id="app-layout" class="min-h-screen">
    <header class="sticky top-0 z-30 bg-white/90 backdrop-blur supports-[backdrop-filter]:bg-white/60 border-b">
      <nav class="container-nice py-4 flex justify-between items-center">
        <div>
          <RouterLink :to="{ name: 'home' }" class="text-2xl font-bold text-gray-900 hover:text-blue-600">
            The Boutique
          </RouterLink>
        </div>
        <div class="flex items-center gap-4">
          <RouterLink :to="{ name: 'home' }" class="text-gray-700 hover:text-blue-600">Shop</RouterLink>

          <!-- This will be the cart link, to be implemented -->
          <RouterLink to="/cart" class="text-gray-700 hover:text-blue-600">Cart</RouterLink>

          <template v-if="!authStore.isAuthenticated">
            <RouterLink :to="{ name: 'login' }" class="text-gray-700 hover:text-blue-600">Login</RouterLink>
            <RouterLink :to="{ name: 'register' }" class="btn-primary">
              Register
            </RouterLink>
          </template>
          <template v-else>
             <span class="text-gray-700 hidden sm:inline">Welcome, {{ authStore.user.email }}</span>
             <button @click="handleLogout" class="btn-secondary">
               Logout
             </button>
          </template>
        </div>
      </nav>
    </header>

    <main class="container-nice py-8">
      <RouterView />
    </main>

    <footer class="mt-12 border-t bg-white">
      <div class="container-nice py-6 text-sm text-gray-600 flex justify-between items-center">
        <span>© {{ new Date().getFullYear() }} The Boutique</span>
        <span class="text-gray-400">Built with Vue & Spring</span>
      </div>
    </footer>
  </div>
</template>

<style>
/* We are now using Tailwind, so we can remove the old scoped styles.
   Global styles can go in main.css if needed. */
/* Fonts are loaded via index.html and applied in main.css */
</style>
