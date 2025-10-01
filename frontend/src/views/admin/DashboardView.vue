<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { RouterLink } from 'vue-router'

const authStore = useAuthStore()
const summary = ref(null)
const error = ref(null)

async function fetchSummary() {
  error.value = null
  try {
    const response = await fetch('/api/admin/summary', {
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    })

    if (!response.ok) {
      throw new Error('Failed to fetch dashboard summary')
    }
    summary.value = await response.json()
  } catch (err) {
    error.value = err.message
  }
}

onMounted(fetchSummary)
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-gray-800 mb-6">Admin Dashboard</h1>

    <div v-if="error" class="bg-red-100 text-red-700 p-4 rounded-md mb-6">
      <p>Error loading dashboard: {{ error }}</p>
    </div>

    <div v-if="summary" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-xl font-semibold text-gray-700 mb-2">Total Users</h2>
        <p class="text-4xl font-bold text-gray-900">{{ summary.totalUsers }}</p>
      </div>
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-xl font-semibold text-gray-700 mb-2">Total Orders</h2>
        <p class="text-4xl font-bold text-gray-900">{{ summary.totalOrders }}</p>
      </div>
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-xl font-semibold text-gray-700 mb-2">Total Revenue</h2>
        <p class="text-4xl font-bold text-gray-900">${{ summary.totalRevenue.toFixed(2) }}</p>
      </div>
    </div>

    <div class="mt-8 grid grid-cols-1 md:grid-cols-2 gap-6">
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-xl font-semibold mb-2">Manage Products</h2>
        <p class="text-gray-600 mb-4">Add, edit, and remove products from your store catalog.</p>
        <RouterLink :to="{ name: 'admin-products' }" class="font-semibold text-blue-600 hover:underline">
          Go to Products &rarr;
        </RouterLink>
      </div>
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-xl font-semibold mb-2">View Orders</h2>
        <p class="text-gray-600 mb-4">Review and manage customer orders.</p>
        <p class="text-gray-500 italic">(Coming Soon)</p>
      </div>
    </div>
  </div>
</template>