<script setup>
import { RouterView, RouterLink } from 'vue-router'
</script>

<template>
  <div class="flex h-screen bg-gray-100">
    <aside class="w-64 bg-gray-800 text-white flex flex-col">
      <div class="p-4 text-2xl font-bold border-b border-gray-700">
        Boutique Admin
      </div>
      <nav class="flex-1 p-4 space-y-2">
        <RouterLink
          :to="{ name: 'admin-dashboard' }"
          class="block px-4 py-2 rounded hover:bg-gray-700"
          active-class="bg-blue-600"
        >
          Dashboard
        </RouterLink>
        <RouterLink
          :to="{ name: 'admin-products' }"
          class="block px-4 py-2 rounded hover:bg-gray-700"
          active-class="bg-blue-600"
        >
          Products
        </RouterLink>
        <RouterLink
          :to="{ name: 'admin-users' }"
          class="block px-4 py-2 rounded hover:bg-gray-700"
          active-class="bg-blue-600"
        >
          Users
        </RouterLink>
      </nav>
    </aside>
    <main class="flex-1 p-8 overflow-y-auto">
      <RouterView />
    </main>
  </div>
</template>
