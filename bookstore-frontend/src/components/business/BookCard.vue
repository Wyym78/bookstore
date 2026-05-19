<template>
  <div class="book-card" @click="$emit('click')">
    <div class="book-cover">
      <img :src="book.coverUrl || '/placeholder-book.png'" :alt="book.title" />
      <div class="book-overlay">
        <span class="view-detail">查看详情</span>
      </div>
    </div>
    <div class="book-info">
      <h3 class="book-title">{{ book.title }}</h3>
      <p class="book-author">{{ book.author }}</p>
      <div class="book-footer">
        <span class="book-price">¥{{ book.price }}</span>
        <span v-if="book.stock <= 10 && book.stock > 0" class="stock-warning">仅剩{{ book.stock }}</span>
        <span v-else-if="book.stock === 0" class="stock-out">缺货</span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  book: {
    type: Object,
    required: true
  }
})

defineEmits(['click'])
</script>

<style scoped>
.book-card {
  background: var(--color-paper-white);
  border-radius: var(--radius-lg);
  overflow: hidden;
  cursor: pointer;
  transition: transform var(--transition-base), box-shadow var(--transition-base);
}

.book-card:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-lg);
}

.book-card:hover .book-overlay {
  opacity: 1;
}

.book-card:hover .book-cover img {
  transform: scale(1.05);
}

.book-cover {
  position: relative;
  aspect-ratio: 3 / 4;
  overflow: hidden;
  background: var(--color-paper-dark);
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-slow);
}

.book-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgba(26, 26, 26, 0.8) 0%, transparent 60%);
  display: flex;
  align-items: flex-end;
  justify-content: center;
  padding-bottom: var(--space-4);
  opacity: 0;
  transition: opacity var(--transition-base);
}

.view-detail {
  padding: var(--space-2) var(--space-4);
  background: var(--color-vermillion);
  color: white;
  font-size: var(--text-sm);
  border-radius: var(--radius-full);
  transform: translateY(10px);
  transition: transform var(--transition-base);
}

.book-card:hover .view-detail {
  transform: translateY(0);
}

.book-info {
  padding: var(--space-4);
}

.book-title {
  font-family: var(--font-display);
  font-size: var(--text-base);
  font-weight: 600;
  color: var(--color-ink);
  margin-bottom: var(--space-1);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.book-author {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
  margin-bottom: var(--space-3);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.book-footer {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.book-price {
  font-family: var(--font-accent);
  font-size: var(--text-lg);
  font-weight: 600;
  color: var(--color-copper);
}

.stock-warning {
  font-size: var(--text-xs);
  color: var(--color-vermillion);
  background: rgba(201, 64, 67, 0.1);
  padding: 2px 8px;
  border-radius: var(--radius-full);
}

.stock-out {
  font-size: var(--text-xs);
  color: var(--color-text-muted);
  background: var(--color-paper-dark);
  padding: 2px 8px;
  border-radius: var(--radius-full);
}
</style>