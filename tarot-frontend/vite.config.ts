import react from '@vitejs/plugin-react'
import { UserConfig, defineConfig, loadEnv } from 'vite'

// https://vitejs.dev/config/
export default defineConfig(({ mode }: UserConfig) => {
  // Load env file based on `mode` in the current working directory
  const env = loadEnv(mode || 'development', process.cwd(), 'VITE_')

  return {
    plugins: [react()],
    define: {
      'process.env': env,
    },
    server: {
      proxy: {
        '/api': {
          target: 'http://localhost:8080', // Remove /api from target
          changeOrigin: true,
          secure: false, // Add this for local development
          rewrite: (path: string) => path.replace(/^\/api/, ''),
          headers: {
            Connection: 'keep-alive', // Important for Firebase auth
          },
        },
      },
    },
    headers: {
      'Cross-Origin-Opener-Policy': 'same-origin-allow-popups',
      'Cross-Origin-Embedder-Policy': 'require-corp',
    },
  }
})
