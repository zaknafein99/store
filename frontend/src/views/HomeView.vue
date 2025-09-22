<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'

const products = ref([])
const error = ref(null)

onMounted(async () => {
  try {
    const response = await fetch('/api/products')
    if (!response.ok) {
      throw new Error('Failed to fetch products')
    }
    products.value = await response.json()
  } catch (err) {
    error.value = err.message
  }
})
</script>

<template>
  <main class="p-8">
    <div v-if="error" class="text-red-500">
      <p>Error loading products: {{ error }}</p>
    </div>

    <div v-if="products.length > 0" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <div v-for="product in products" :key="product.id" class="product-card border rounded-lg overflow-hidden shadow-sm hover:shadow-lg transition-shadow">
        <RouterLink :to="{ name: 'product-detail', params: { id: product.id } }">
          <div class="h-64 bg-gray-200 flex items-center justify-center">
             <img v-if="product.photos && product.photos.length > 0" :src="product.photos[0]" alt="Product image" class="w-full h-full object-cover">
             <span v-else class="text-gray-500">No Image</span>
          </div>
          <div class="p-4">
            <h3 class="font-semibold text-lg truncate">{{ product.name }}</h3>
            <p class="text-gray-700 mt-1">${{ product.price }}</p>
          </div>
        </RouterLink>
      </div>
    </div>

    <div v-else-if="!error" class="text-center">
      <p>Loading products...</p>
    </div>
  </main>
</template>
