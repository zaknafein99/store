<script setup>
import { onMounted } from 'vue'
import { useCartStore } from '@/stores/cart'
import { RouterLink } from 'vue-router'

const cartStore = useCartStore()

onMounted(() => {
  cartStore.fetchCart()
})

function handleRemoveItem(productId) {
  cartStore.removeFromCart(productId)
}
</script>

<template>
  <div class="cart-view p-4 md:p-8">
    <h1 class="text-3xl font-bold mb-6 text-gray-800">Your Shopping Cart</h1>
    <div v-if="cartStore.items.length === 0" class="text-center py-12 bg-white rounded-lg shadow-md">
      <p class="text-xl text-gray-500">Your cart is empty.</p>
      <RouterLink :to="{ name: 'home' }" class="mt-4 inline-block bg-blue-600 text-white font-bold py-2 px-6 rounded hover:bg-blue-700 transition-colors">
        Continue Shopping
      </RouterLink>
    </div>
    <div v-else class="bg-white rounded-lg shadow-md">
      <div v-for="item in cartStore.items" :key="item.productId" class="flex items-center justify-between p-4 border-b">
        <div class="flex items-center">
          <img :src="item.photoUrl || 'https://via.placeholder.com/100'" alt="Product image" class="w-20 h-20 object-cover rounded-md mr-4">
          <div>
            <h2 class="font-semibold text-lg">{{ item.productName }}</h2>
            <p class="text-gray-600">Quantity: {{ item.quantity }}</p>
          </div>
        </div>
        <div class="text-right">
          <p class="font-semibold text-lg">${{ item.totalPrice.toFixed(2) }}</p>
          <button @click="handleRemoveItem(item.productId)" class="text-red-500 hover:text-red-700 text-sm font-medium mt-1">
            Remove
          </button>
        </div>
      </div>
      <div class="p-4 flex justify-end items-center bg-gray-50">
        <span class="text-xl font-bold text-gray-800">Total: ${{ cartStore.totalPrice.toFixed(2) }}</span>
        <button class="ml-4 bg-green-600 text-white font-bold py-2 px-6 rounded hover:bg-green-700 transition-colors">
          Proceed to Checkout
        </button>
      </div>
    </div>
  </div>
</template>
