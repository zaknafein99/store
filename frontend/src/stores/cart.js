import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useAuthStore } from './auth'

export const useCartStore = defineStore('cart', () => {
  const items = ref([])
  const totalPrice = ref(0)
  const authStore = useAuthStore()

  const cartCount = computed(() => {
    return items.value.reduce((count, item) => count + item.quantity, 0)
  })

  async function fetchCart() {
    if (!authStore.isAuthenticated) return;
    try {
      const response = await fetch('/api/cart', {
        headers: { 'Authorization': `Bearer ${authStore.token}` }
      })
      if (!response.ok) throw new Error('Could not fetch cart')
      const data = await response.json()
      items.value = data.items
      totalPrice.value = data.totalPrice
    } catch (error) {
      console.error('Failed to fetch cart:', error)
    }
  }

  async function addToCart(productId, quantity) {
    if (!authStore.isAuthenticated) {
      alert('Please log in to add items to your cart.')
      return
    }
    try {
      const response = await fetch('/api/cart/items', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${authStore.token}`
        },
        body: JSON.stringify({ productId, quantity })
      })
      if (!response.ok) throw new Error('Could not add item')
      const data = await response.json()
      items.value = data.items
      totalPrice.value = data.totalPrice
    } catch (error) {
      console.error('Failed to add to cart:', error)
    }
  }

  async function removeFromCart(productId) {
    if (!authStore.isAuthenticated) return;
    try {
      const response = await fetch(`/api/cart/items/${productId}`, {
        method: 'DELETE',
        headers: { 'Authorization': `Bearer ${authStore.token}` }
      })
      if (!response.ok) throw new Error('Could not remove item')
      const data = await response.json()
      items.value = data.items
      totalPrice.value = data.totalPrice
    } catch (error) {
      console.error('Failed to remove from cart:', error)
    }
  }

  return { items, totalPrice, cartCount, fetchCart, addToCart, removeFromCart }
})
