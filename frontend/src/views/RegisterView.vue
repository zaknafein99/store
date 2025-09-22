<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const email = ref('')
const password = ref('')
const error = ref(null)
const successMessage = ref('')

async function handleRegister() {
  error.value = null
  successMessage.value = ''
  try {
    await authStore.register(email.value, password.value)
    successMessage.value = 'Registration successful! You can now log in.'
    setTimeout(() => {
        router.push({ name: 'login' })
    }, 2000);
  } catch (err) {
    error.value = err.message
  }
}
</script>

<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-md">
      <h2 class="text-2xl font-bold text-center text-gray-800">Create an Account</h2>

      <form @submit.prevent="handleRegister" class="space-y-6">
        <div>
          <label for="email" class="text-sm font-medium text-gray-700">Email</label>
          <input type="email" id="email" v-model="email" required class="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500">
        </div>

        <div>
          <label for="password" class="text-sm font-medium text-gray-700">Password</label>
          <input type="password" id="password" v-model="password" required class="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500">
        </div>

        <div v-if="error" class="text-sm text-red-600 bg-red-100 p-3 rounded-md">
          {{ error }}
        </div>

        <div v-if="successMessage" class="text-sm text-green-600 bg-green-100 p-3 rounded-md">
          {{ successMessage }}
        </div>

        <button type="submit" class="w-full py-2 px-4 font-semibold text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
          Register
        </button>
      </form>

      <p class="text-sm text-center text-gray-600">
        Already have an account?
        <router-link :to="{ name: 'login' }" class="font-medium text-blue-600 hover:underline">Log in</router-link>
      </p>
    </div>
  </div>
</template>
