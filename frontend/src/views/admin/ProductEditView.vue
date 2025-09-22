<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const product = ref({
  name: '',
  description: '',
  price: 0,
  material: '',
  sizes: '', // Will be split into an array
  colors: '', // Will be split into an array
  photos: '', // Will be split into an array
})
const error = ref(null)

const isEditing = computed(() => !!route.params.id)

onMounted(async () => {
  if (isEditing.value) {
    try {
      const response = await fetch(`/api/products/${route.params.id}`)
      if (!response.ok) throw new Error('Product not found')
      const data = await response.json()
      // Join array fields back into comma-separated strings for the form
      product.value = {
        ...data,
        sizes: data.sizes.join(', '),
        colors: data.colors.join(', '),
        photos: data.photos.join(', '),
      }
    } catch (err) {
      error.value = err.message
    }
  }
})

async function handleSubmit() {
  // IMPORTANT: This is a placeholder for form submission.
  // Real implementation requires sending authenticated requests.

  const payload = {
    ...product.value,
    price: parseFloat(product.value.price),
    sizes: product.value.sizes.split(',').map(s => s.trim()).filter(Boolean),
    colors: product.value.colors.split(',').map(c => c.trim()).filter(Boolean),
    photos: product.value.photos.split(',').map(p => p.trim()).filter(Boolean),
  }

  const url = isEditing.value ? `/api/admin/products/${route.params.id}` : '/api/admin/products'
  const method = isEditing.value ? 'PUT' : 'POST'

  alert(`(Placeholder) Submitting to ${method} ${url}. Auth needed. Payload: ${JSON.stringify(payload)}`)

  // Example of what the code will look like:
  // try {
  //   const response = await fetch(url, {
  //     method: method,
  //     headers: {
  //       'Content-Type': 'application/json',
  //       'Authorization': 'Basic ' + btoa('admin:password') // Placeholder auth
  //     },
  //     body: JSON.stringify(payload)
  //   });
  //   if (!response.ok) throw new Error('Submission failed');
  //   router.push({ name: 'admin-products' });
  // } catch (err) {
  //   error.value = 'Failed to save product: ' + err.message;
  // }
}
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-gray-800 mb-6">
      {{ isEditing ? 'Edit Product' : 'Add New Product' }}
    </h1>

    <form @submit.prevent="handleSubmit" class="bg-white p-8 rounded-lg shadow-md space-y-6">
      <div>
        <label for="name" class="block text-sm font-medium text-gray-700">Product Name</label>
        <input type="text" id="name" v-model="product.name" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </div>

      <div>
        <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
        <textarea id="description" v-model="product.description" rows="4" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"></textarea>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div>
          <label for="price" class="block text-sm font-medium text-gray-700">Price</label>
          <input type="number" step="0.01" id="price" v-model="product.price" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
        </div>
        <div>
          <label for="material" class="block text-sm font-medium text-gray-700">Material</label>
          <input type="text" id="material" v-model="product.material" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
        </div>
      </div>

      <div>
        <label for="colors" class="block text-sm font-medium text-gray-700">Colors (comma-separated)</label>
        <input type="text" id="colors" v-model="product.colors" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </div>

      <div>
        <label for="sizes" class="block text-sm font-medium text-gray-700">Sizes (comma-separated)</label>
        <input type="text" id="sizes" v-model="product.sizes" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </div>

      <div>
        <label for="photos" class="block text-sm font-medium text-gray-700">Photo URLs (comma-separated)</label>
        <input type="text" id="photos" v-model="product.photos" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
      </div>

      <div v-if="error" class="text-red-500 bg-red-100 p-3 rounded">
        {{ error }}
      </div>

      <div class="flex justify-end">
        <button type="submit" class="bg-blue-600 text-white font-bold py-2 px-6 rounded hover:bg-blue-700 transition-colors">
          {{ isEditing ? 'Update Product' : 'Create Product' }}
        </button>
      </div>
    </form>
  </div>
</template>
