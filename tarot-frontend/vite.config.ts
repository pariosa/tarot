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
          target: 'http://localhost:8080',
          changeOrigin: true,
          secure: false,
          rewrite: (path) => path.replace(/^\/api/, ''),
          headers: {
            'Cross-Origin-Opener-Policy': 'same-origin-allow-popups',
            'Cross-Origin-Embedder-Policy': 'unsafe-none',
          },
        },
      },
      headers: {
        'Cross-Origin-Opener-Policy': 'same-origin-allow-popups',
        'Cross-Origin-Embedder-Policy': 'unsafe-none',
      },
    },
  }
})
