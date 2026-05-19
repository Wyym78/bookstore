<template>
  <div class="auth-page">
    <div class="auth-container">
      <!-- 左侧品牌区 -->
      <div class="auth-brand">
        <div class="brand-content">
          <h1 class="brand-title">书斋</h1>
          <p class="brand-subtitle">开卷有益，读书让灵魂更有趣</p>
          <div class="brand-decor">
            <span class="decor-item">📖</span>
            <span class="decor-item">📚</span>
            <span class="decor-item">✒️</span>
          </div>
        </div>
        <div class="brand-pattern"></div>
      </div>

      <!-- 右侧表单区 -->
      <div class="auth-form-wrapper">
        <div class="auth-form-container">
          <div class="auth-header">
            <h2>登录书斋</h2>
            <p>欢迎回来，请输入您的账号信息</p>
          </div>

          <form @submit.prevent="handleLogin" class="auth-form">
            <div class="form-item">
              <label>用户名</label>
              <input
                v-model="formData.username"
                type="text"
                placeholder="请输入用户名"
                class="input"
                :class="{ 'input-error': errors.username }"
              />
              <span v-if="errors.username" class="error-msg">{{ errors.username }}</span>
            </div>

            <div class="form-item">
              <label>密码</label>
              <div class="password-input">
                <input
                  v-model="formData.password"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="请输入密码"
                  class="input"
                  :class="{ 'input-error': errors.password }"
                />
                <button type="button" class="toggle-pwd" @click="showPassword = !showPassword">
                  {{ showPassword ? '🙈' : '👁️' }}
                </button>
              </div>
              <span v-if="errors.password" class="error-msg">{{ errors.password }}</span>
            </div>

            <div class="form-options">
              <label class="remember-me">
                <input type="checkbox" v-model="rememberMe" />
                <span>记住我</span>
              </label>
              <a href="#" class="forgot-pwd">忘记密码？</a>
            </div>

            <button type="submit" class="btn-submit" :disabled="loading">
              <span v-if="loading" class="loading-spinner"></span>
              <span v-else>登 录</span>
            </button>
          </form>

          <div class="auth-footer">
            <p>
              还没有账号？
              <router-link to="/register" class="link">立即注册</router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const formData = reactive({
  username: '',
  password: ''
})

const errors = reactive({
  username: '',
  password: ''
})

const showPassword = ref(false)
const rememberMe = ref(false)
const loading = ref(false)

const validate = () => {
  let valid = true
  errors.username = ''
  errors.password = ''

  if (!formData.username.trim()) {
    errors.username = '请输入用户名'
    valid = false
  }

  if (!formData.password) {
    errors.password = '请输入密码'
    valid = false
  }

  return valid
}

const handleLogin = async () => {
  if (!validate()) return

  loading.value = true
  try {
    await userStore.login(formData)
    ElMessage.success('登录成功')
    const redirect = route.query.redirect || '/'
    router.push(redirect)
  } catch (error) {
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--color-paper);
  padding: var(--space-6);
}

.auth-container {
  width: 100%;
  max-width: 900px;
  display: flex;
  border-radius: var(--radius-xl);
  overflow: hidden;
  box-shadow: var(--shadow-xl);
  background: var(--color-paper-white);
}

/* 左侧品牌区 */
.auth-brand {
  flex: 1;
  background: linear-gradient(135deg, var(--color-ink) 0%, var(--color-ink-light) 100%);
  color: white;
  padding: var(--space-12);
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.brand-content {
  position: relative;
  z-index: 1;
  animation: fadeInUp 0.6s ease;
}

.brand-title {
  font-family: var(--font-display);
  font-size: var(--text-5xl);
  font-weight: 700;
  letter-spacing: 8px;
  margin-bottom: var(--space-4);
}

.brand-subtitle {
  font-size: var(--text-lg);
  opacity: 0.8;
  font-style: italic;
}

.brand-decor {
  margin-top: var(--space-8);
  display: flex;
  gap: var(--space-4);
}

.decor-item {
  font-size: 32px;
  animation: float 3s ease-in-out infinite;
}

.decor-item:nth-child(2) {
  animation-delay: 0.5s;
}

.decor-item:nth-child(3) {
  animation-delay: 1s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.brand-pattern {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image:
    radial-gradient(circle at 20% 80%, rgba(201, 64, 67, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(122, 158, 126, 0.2) 0%, transparent 50%);
}

/* 右侧表单区 */
.auth-form-wrapper {
  flex: 1;
  padding: var(--space-12);
  display: flex;
  align-items: center;
  justify-content: center;
}

.auth-form-container {
  width: 100%;
  max-width: 320px;
  animation: fadeIn 0.5s ease;
}

.auth-header {
  text-align: center;
  margin-bottom: var(--space-8);
}

.auth-header h2 {
  font-family: var(--font-display);
  font-size: var(--text-3xl);
  color: var(--color-ink);
  margin-bottom: var(--space-2);
}

.auth-header p {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: var(--space-5);
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: var(--space-2);
}

.form-item label {
  font-size: var(--text-sm);
  font-weight: 500;
  color: var(--color-text-secondary);
}

.password-input {
  position: relative;
}

.password-input .input {
  padding-right: 48px;
}

.toggle-pwd {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  opacity: 0.6;
  transition: opacity var(--transition-fast);
}

.toggle-pwd:hover {
  opacity: 1;
}

.error-msg {
  font-size: var(--text-xs);
  color: var(--color-vermillion);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  font-size: var(--text-sm);
  color: var(--color-text-secondary);
  cursor: pointer;
}

.remember-me input {
  accent-color: var(--color-vermillion);
}

.forgot-pwd {
  font-size: var(--text-sm);
  color: var(--color-vermillion);
}

.forgot-pwd:hover {
  text-decoration: underline;
}

.btn-submit {
  width: 100%;
  padding: var(--space-4);
  background: var(--color-vermillion);
  color: white;
  font-size: var(--text-base);
  font-weight: 600;
  border: none;
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: var(--space-2);
}

.btn-submit:hover:not(:disabled) {
  background: var(--color-vermillion-light);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(201, 64, 67, 0.3);
}

.btn-submit:active:not(:disabled) {
  transform: scale(0.98);
}

.btn-submit:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.loading-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.auth-footer {
  text-align: center;
  margin-top: var(--space-6);
  font-size: var(--text-sm);
  color: var(--color-text-muted);
}

.link {
  color: var(--color-vermillion);
  font-weight: 500;
}

.link:hover {
  text-decoration: underline;
}

@media (max-width: 768px) {
  .auth-container {
    flex-direction: column;
    max-width: 420px;
  }

  .auth-brand {
    padding: var(--space-8);
  }

  .brand-title {
    font-size: var(--text-4xl);
  }

  .auth-form-wrapper {
    padding: var(--space-8);
  }
}
</style>