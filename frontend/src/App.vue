<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

function handleLogout() {
  authStore.logout()
}
</script>

<template>
  <div id="app-layout" class="min-h-screen bg-gray-50">
    <header class="bg-white shadow-md">
      <nav class="container mx-auto px-6 py-4 flex justify-between items-center">
        <div>
          <RouterLink :to="{ name: 'home' }" class="text-2xl font-bold text-gray-800 hover:text-blue-600">
            The Boutique
          </RouterLink>
        </div>
        <div class="flex items-center space-x-4">
          <RouterLink :to="{ name: 'home' }" class="text-gray-600 hover:text-blue-600">Shop</RouterLink>

          <!-- This will be the cart link, to be implemented -->
          <RouterLink to="/cart" class="text-gray-600 hover:text-blue-600">Cart</RouterLink>

          <template v-if="!authStore.isAuthenticated">
            <RouterLink :to="{ name: 'login' }" class="text-gray-600 hover:text-blue-600">Login</RouterLink>
            <RouterLink :to="{ name: 'register' }" class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700">
              Register
            </RouterLink>
          </template>
          <template v-else>
             <span class="text-gray-700">Welcome, {{ authStore.user.email }}</span>
             <button @click="handleLogout" class="bg-gray-200 px-4 py-2 rounded-md hover:bg-gray-300">
               Logout
             </button>
          </template>
        </div>
      </nav>
    </header>

    <main class="container mx-auto p-6">
      <RouterView />
    </main>
  </div>
</template>

<style>
/* We are now using Tailwind, so we can remove the old scoped styles.
   Global styles can go in main.css if needed. */
body {
  font-family: 'Inter', sans-serif; /* A nice modern font, assuming it's imported in index.html or main.css */
}
</style>
