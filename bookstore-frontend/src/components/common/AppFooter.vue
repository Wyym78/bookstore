<template>
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-brand">
        <span class="footer-logo">📚 书斋</span>
        <p class="footer-slogan">开卷有益，读书让灵魂更有趣</p>
      </div>
      <div class="footer-links">
        <div class="footer-column">
          <h4>关于书斋</h4>
          <a href="#">关于我们</a>
          <a href="#">联系方式</a>
        </div>
        <div class="footer-column">
          <h4>帮助中心</h4>
          <a href="#">常见问题</a>
          <a href="#">配送说明</a>
        </div>
        <div class="footer-column">
          <h4>法律声明</h4>
          <a href="#">隐私政策</a>
          <a href="#">服务条款</a>
        </div>
      </div>
      <div class="footer-copyright">
        <p>© 2026 书斋网上书店 · 课程设计项目</p>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background: var(--color-ink);
  color: var(--color-text-inverse);
  padding: var(--space-12) 0 var(--space-6);
  margin-top: var(--space-16);
}

.footer-container {
  max-width: var(--max-width);
  margin: 0 auto;
  padding: 0 var(--space-6);
}

.footer-brand {
  text-align: center;
  margin-bottom: var(--space-8);
}

.footer-logo {
  font-family: var(--font-display);
  font-size: var(--text-3xl);
  font-weight: 700;
  letter-spacing: 4px;
}

.footer-slogan {
  margin-top: var(--space-2);
  font-size: var(--text-sm);
  color: rgba(254, 254, 254, 0.6);
  font-style: italic;
}

.footer-links {
  display: flex;
  justify-content: center;
  gap: var(--space-16);
  margin-bottom: var(--space-8);
}

.footer-column {
  text-align: center;
}

.footer-column h4 {
  font-family: var(--font-display);
  font-size: var(--text-base);
  font-weight: 600;
  margin-bottom: var(--space-4);
}

.footer-column a {
  display: block;
  font-size: var(--text-sm);
  color: rgba(254, 254, 254, 0.7);
  margin-bottom: var(--space-2);
  transition: color var(--transition-fast);
}

.footer-column a:hover {
  color: var(--color-vermillion);
}

.footer-copyright {
  text-align: center;
  padding-top: var(--space-6);
  border-top: 1px solid rgba(254, 254, 254, 0.1);
}

.footer-copyright p {
  font-size: var(--text-xs);
  color: rgba(254, 254, 254, 0.5);
}
</style>