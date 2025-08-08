import react from '@vitejs/plugin-react'
import { defineConfig, loadEnv } from 'vite'

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  // Load env file based on `mode` in the current working directory
  const env = loadEnv(mode, process.cwd(), 'VITE_')

  return {
    plugins: [react()],
    define: {
      'process.env': env,
    },
    server: {
      proxy: {
        '/api': {
          target: 'http://localhost:8080/api/',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
        '^/fallback/.*': {
          target: 'http://jsonplaceholder.typicode.com',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/fallback/, ''),
        },
      },
    },
  }
})
