<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'

const products = ref([])
const error = ref(null)
const loaded = ref(false)

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
  loaded.value = true
})
</script>

<template>
  <main>
    <!-- Hero -->
    <section class="relative overflow-hidden rounded-2xl bg-gradient-to-r from-blue-50 to-emerald-50 border mb-8">
      <div class="container-nice py-12">
        <div class="max-w-2xl">
          <h1 class="text-3xl sm:text-4xl font-extrabold tracking-tight text-gray-900">Discover your new favorites</h1>
          <p class="mt-3 text-gray-600">Thoughtfully curated apparel with quality materials and timeless design.</p>
          <RouterLink :to="{ name: 'home' }" class="mt-6 inline-block btn-primary">Shop collection</RouterLink>
        </div>
      </div>
    </section>
    <div v-if="error" class="text-red-500">
      <p>Error loading products: {{ error }}</p>
    </div>

    <div v-if="loaded && products.length > 0" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <div v-for="product in products" :key="product.id" class="card overflow-hidden">
        <RouterLink :to="{ name: 'product-detail', params: { id: product.id } }">
          <div class="h-64 bg-gray-100 flex items-center justify-center">
             <img v-if="product.photos && product.photos.length > 0" :src="product.photos[0]" alt="Product image" class="w-full h-full object-cover">
             <span v-else class="text-gray-500">No Image</span>
          </div>
          <div class="p-4">
            <h3 class="font-semibold text-lg truncate text-gray-900">{{ product.name }}</h3>
            <p class="text-gray-700 mt-1">${{ Number(product.price).toFixed(2) }}</p>
          </div>
        </RouterLink>
      </div>
    </div>

    <div v-else-if="loaded && !error" class="text-center text-gray-600">
      <p>No products available yet.</p>
    </div>

    <div v-else class="text-center text-gray-600">
      <p>Loading products...</p>
    </div>
  </main>
</template>
