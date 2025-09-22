<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useCartStore } from '@/stores/cart'

const route = useRoute()
const cartStore = useCartStore()
const product = ref(null)
const quantity = ref(1)
const error = ref(null)

onMounted(async () => {
  try {
    const productId = route.params.id
    const response = await fetch(`/api/products/${productId}`)
    if (!response.ok) {
      throw new Error('Product not found')
    }
    product.value = await response.json()
  } catch (err) {
    error.value = err.message
  }
})
</script>

<template>
  <div class="product-detail p-8">
    <div v-if="error" class="text-red-500">
      <p>Error loading product: {{ error }}</p>
    </div>
    <div v-if="product" class="max-w-4xl mx-auto">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div>
          <!-- For now, just showing the first photo -->
          <img v-if="product.photos && product.photos.length > 0" :src="product.photos[0]" alt="Product image" class="w-full h-auto object-cover rounded-lg shadow-md">
          <div v-else class="w-full h-96 bg-gray-200 rounded-lg flex items-center justify-center">
            <span class="text-gray-500">No Image</span>
          </div>
        </div>
        <div>
          <h1 class="text-4xl font-bold mb-2">{{ product.name }}</h1>
          <p class="text-2xl text-gray-800 mb-4">${{ product.price }}</p>
          <p class="text-gray-600 mb-6">{{ product.description }}</p>

          <div class="mb-4">
            <h3 class="font-semibold mb-2">Colors</h3>
            <div class="flex gap-2">
              <span v-for="color in product.colors" :key="color" class="px-3 py-1 bg-gray-200 text-sm rounded-full">{{ color }}</span>
            </div>
          </div>

          <div class="mb-6">
            <h3 class="font-semibold mb-2">Sizes</h3>
            <div class="flex gap-2">
              <span v-for="size in product.sizes" :key="size" class="px-3 py-1 bg-gray-200 text-sm rounded-full">{{ size }}</span>
            </div>
          </div>

          <div class="flex items-center gap-4 mb-6">
            <label for="quantity" class="font-semibold">Quantity:</label>
            <input type="number" id="quantity" v-model.number="quantity" min="1" class="w-20 p-2 border border-gray-300 rounded-md text-center">
          </div>

          <button @click="cartStore.addToCart(product.id, quantity)" class="w-full bg-blue-600 text-white font-bold py-3 px-6 rounded-lg hover:bg-blue-700 transition-colors">
            Add to Cart
          </button>
        </div>
      </div>
    </div>
    <div v-else-if="!error" class="text-center">
      <p>Loading product...</p>
    </div>
  </div>
</template>
