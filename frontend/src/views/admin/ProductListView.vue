<script setup>
import { ref, onMounted } from 'vue'
import { RouterLink } from 'vue-router'

const products = ref([])
const error = ref(null)

// This fetch is public, but actions will require auth
onMounted(async () => {
  try {
    const response = await fetch('/api/products')
    if (!response.ok) throw new Error('Failed to fetch products')
    products.value = await response.json()
  } catch (err) {
    error.value = err.message
  }
})

async function deleteProduct(productId) {
  // IMPORTANT: This is a placeholder.
  // Real implementation requires sending authenticated requests (e.g., with a token).
  // We will add auth handling in a later step.
  if (confirm('Are you sure you want to delete this product?')) {
    alert(`(Placeholder) Deleting product ${productId}. Auth needed for this to work.`)
    // Example of what the code will look like:
    // try {
    //   const response = await fetch(`/api/admin/products/${productId}`, {
    //     method: 'DELETE',
    //     headers: { 'Authorization': 'Basic ' + btoa('admin:password') } // Placeholder auth
    //   });
    //   if (!response.ok) throw new Error('Failed to delete');
    //   products.value = products.value.filter(p => p.id !== productId);
    // } catch (err) {
    //   alert('Error deleting product: ' + err.message);
    // }
  }
}
</script>

<template>
  <div>
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-3xl font-bold text-gray-800">Manage Products</h1>
      <RouterLink :to="{ name: 'admin-product-new' }" class="bg-blue-600 text-white font-bold py-2 px-4 rounded hover:bg-blue-700 transition-colors">
        Add New Product
      </RouterLink>
    </div>

    <div v-if="error" class="text-red-500 bg-red-100 p-4 rounded mb-4">
      {{ error }}
    </div>

    <div class="bg-white shadow-md rounded-lg overflow-hidden">
      <table class="min-w-full leading-normal">
        <thead>
          <tr>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Product</th>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Price</th>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.id">
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <p class="text-gray-900 whitespace-no-wrap">{{ product.name }}</p>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <p class="text-gray-900 whitespace-no-wrap">${{ product.price }}</p>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm text-right">
              <RouterLink :to="{ name: 'admin-product-edit', params: { id: product.id } }" class="text-blue-600 hover:text-blue-900 mr-4">Edit</RouterLink>
              <button @click="deleteProduct(product.id)" class="text-red-600 hover:text-red-900">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
