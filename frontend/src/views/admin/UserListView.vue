<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const users = ref([])
const error = ref(null)

async function fetchUsers() {
  error.value = null
  try {
    const response = await fetch('/api/admin/users', {
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    })
    if (!response.ok) {
      throw new Error('Failed to fetch users')
    }
    users.value = await response.json()
  } catch (err) {
    error.value = err.message
  }
}

async function toggleAdminStatus(user) {
  error.value = null
  try {
    const response = await fetch(`/api/admin/users/${user.id}/toggle-admin`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${authStore.token}`
      }
    })
    if (!response.ok) {
      throw new Error('Failed to toggle admin status')
    }
    const updatedUser = await response.json()
    const index = users.value.findIndex(u => u.id === updatedUser.id)
    if (index !== -1) {
      users.value[index].isAdmin = updatedUser.isAdmin
    }
  } catch (err) {
    error.value = err.message
  }
}

onMounted(fetchUsers)
</script>

<template>
  <div>
    <h1 class="text-3xl font-bold text-gray-800 mb-6">User Management</h1>

    <div v-if="error" class="bg-red-100 text-red-700 p-4 rounded-md mb-6">
      <p>Error loading users: {{ error }}</p>
    </div>

    <div class="bg-white shadow-md rounded-lg overflow-hidden">
      <table class="min-w-full leading-normal">
        <thead>
          <tr>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
              ID
            </th>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
              Email
            </th>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
              Admin
            </th>
            <th class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <p class="text-gray-900 whitespace-no-wrap">{{ user.id }}</p>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <p class="text-gray-900 whitespace-no-wrap">{{ user.email }}</p>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <span :class="user.isAdmin ? 'text-green-600' : 'text-red-600'">
                {{ user.isAdmin ? 'Yes' : 'No' }}
              </span>
            </td>
            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
              <button
                @click="toggleAdminStatus(user)"
                class="px-4 py-2 font-semibold text-white rounded-md"
                :class="user.isAdmin ? 'bg-yellow-500 hover:bg-yellow-600' : 'bg-green-500 hover:bg-green-600'"
                :disabled="user.email === authStore.user.email"
              >
                {{ user.isAdmin ? 'Revoke Admin' : 'Make Admin' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>