<template>
  <div class="books-page">
    <div class="page-container">
      <div class="books-layout">
        <!-- 左侧边栏 -->
        <aside class="sidebar">
          <div class="sidebar-section">
            <h3>分类</h3>
            <ul class="category-list">
              <li
                :class="{ active: !selectedCategoryId }"
                @click="selectCategory(null)"
              >
                全部
              </li>
              <li
                v-for="cat in categories"
                :key="cat.id"
                :class="{ active: selectedCategoryId === cat.id }"
                @click="selectCategory(cat.id)"
              >
                {{ cat.name }}
              </li>
            </ul>
          </div>
        </aside>

        <!-- 右侧内容 -->
        <main class="books-content">
          <!-- 工具栏 -->
          <div class="toolbar">
            <div class="toolbar-left">
              <span class="result-count">共 {{ total }} 本书</span>
            </div>
            <div class="toolbar-right">
              <div class="sort-options">
                <span
                  :class="{ active: sortBy === 'default' }"
                  @click="changeSort('default')"
                >推荐</span>
                <span
                  :class="{ active: sortBy === 'price_asc' }"
                  @click="changeSort('price_asc')"
                >价格↑</span>
                <span
                  :class="{ active: sortBy === 'price_desc' }"
                  @click="changeSort('price_desc')"
                >价格↓</span>
                <span
                  :class="{ active: sortBy === 'new' }"
                  @click="changeSort('new')"
                >新品</span>
              </div>
            </div>
          </div>

          <!-- 书籍网格 -->
          <div v-if="loading" class="books-grid">
            <div v-for="i in 8" :key="i" class="book-skeleton">
              <div class="skeleton cover"></div>
              <div class="skeleton title"></div>
              <div class="skeleton author"></div>
            </div>
          </div>

          <div v-else-if="books.length" class="books-grid">
            <BookCard
              v-for="book in books"
              :key="book.id"
              :book="book"
              @click="goToBook(book.id)"
            />
          </div>

          <div v-else class="empty-state">
            <span class="empty-icon">📚</span>
            <h3>暂无书籍</h3>
            <p>没有找到相关书籍，请尝试其他分类或关键词</p>
          </div>

          <!-- 分页 -->
          <div v-if="totalPages > 1" class="pagination">
            <button
              class="page-btn prev"
              :disabled="pageNum === 1"
              @click="changePage(pageNum - 1)"
            >
              ‹
            </button>
            <button
              v-for="page in visiblePages"
              :key="page"
              :class="['page-btn', { active: page === pageNum, ellipsis: page === '...' }]"
              :disabled="page === '...'"
              @click="page !== '...' && changePage(page)"
            >
              {{ page }}
            </button>
            <button
              class="page-btn next"
              :disabled="pageNum === totalPages"
              @click="changePage(pageNum + 1)"
            >
              ›
            </button>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { bookApi } from '@/api/book'
import { categoryApi } from '@/api/category'
import BookCard from '@/components/business/BookCard.vue'

const router = useRouter()
const route = useRoute()

const books = ref([])
const categories = ref([])
const loading = ref(false)
const selectedCategoryId = ref(null)
const sortBy = ref('default')
const pageNum = ref(1)
const pageSize = ref(12)
const total = ref(0)

const totalPages = computed(() => Math.ceil(total.value / pageSize.value))

const visiblePages = computed(() => {
  const pages = []
  const total = totalPages.value
  const current = pageNum.value

  if (total <= 7) {
    for (let i = 1; i <= total; i++) pages.push(i)
  } else {
    if (current <= 3) {
      for (let i = 1; i <= 5; i++) pages.push(i)
      pages.push('...')
      pages.push(total)
    } else if (current >= total - 2) {
      pages.push(1)
      pages.push('...')
      for (let i = total - 4; i <= total; i++) pages.push(i)
    } else {
      pages.push(1)
      pages.push('...')
      for (let i = current - 1; i <= current + 1; i++) pages.push(i)
      pages.push('...')
      pages.push(total)
    }
  }
  return pages
})

const fetchBooks = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value
    }

    if (route.query.keyword) {
      params.keyword = route.query.keyword
    }

    if (selectedCategoryId.value) {
      params.categoryId = selectedCategoryId.value
    }

    const res = await bookApi.getList(params)
    books.value = res.data?.records || []
    total.value = res.data?.total || 0
  } catch (error) {
    console.error('Failed to fetch books:', error)
  } finally {
    loading.value = false
  }
}

const fetchCategories = async () => {
  try {
    const res = await categoryApi.getList()
    categories.value = res.data || []
  } catch (error) {
    console.error('Failed to fetch categories:', error)
  }
}

const selectCategory = (id) => {
  selectedCategoryId.value = id
  pageNum.value = 1
  router.replace({ query: id ? { categoryId: id } : {} })
  fetchBooks()
}

const changeSort = (sort) => {
  sortBy.value = sort
  fetchBooks()
}

const changePage = (page) => {
  pageNum.value = page
  fetchBooks()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const goToBook = (id) => {
  router.push(`/book/${id}`)
}

watch(
  () => route.query,
  (query) => {
    if (query.categoryId) {
      selectedCategoryId.value = Number(query.categoryId)
    } else {
      selectedCategoryId.value = null
    }
    pageNum.value = 1
    fetchBooks()
  }
)

onMounted(() => {
  if (route.query.categoryId) {
    selectedCategoryId.value = Number(route.query.categoryId)
  }
  fetchCategories()
  fetchBooks()
})
</script>

<style scoped>
.books-page {
  padding: var(--space-8) 0 var(--space-16);
}

.books-layout {
  display: flex;
  gap: var(--space-8);
}

/* 侧边栏 */
.sidebar {
  width: 200px;
  flex-shrink: 0;
}

.sidebar-section {
  position: sticky;
  top: calc(var(--header-height) + var(--space-8));
}

.sidebar-section h3 {
  font-family: var(--font-display);
  font-size: var(--text-lg);
  margin-bottom: var(--space-4);
  padding-bottom: var(--space-3);
  border-bottom: 2px solid var(--color-vermillion);
}

.category-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-1);
}

.category-list li {
  padding: var(--space-3) var(--space-4);
  font-size: var(--text-sm);
  color: var(--color-text-secondary);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.category-list li:hover {
  background: var(--color-paper-dark);
  color: var(--color-text-primary);
}

.category-list li.active {
  background: var(--color-vermillion);
  color: white;
  font-weight: 500;
}

/* 内容区 */
.books-content {
  flex: 1;
  min-width: 0;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: var(--space-6);
  margin-bottom: var(--space-6);
  border-bottom: 1px solid var(--color-border);
}

.result-count {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
}

.sort-options {
  display: flex;
  gap: var(--space-4);
}

.sort-options span {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
  cursor: pointer;
  padding: var(--space-2) var(--space-3);
  border-radius: var(--radius-md);
  transition: all var(--transition-fast);
}

.sort-options span:hover {
  color: var(--color-text-primary);
}

.sort-options span.active {
  color: var(--color-vermillion);
  background: rgba(201, 64, 67, 0.1);
  font-weight: 500;
}

/* 书籍网格 */
.books-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: var(--space-6);
}

/* 骨架屏 */
.book-skeleton {
  background: var(--color-paper-white);
  border-radius: var(--radius-lg);
  padding: var(--space-4);
}

.skeleton {
  background: linear-gradient(
    90deg,
    var(--color-paper-dark) 25%,
    var(--color-paper-white) 50%,
    var(--color-paper-dark) 75%
  );
  background-size: 200% 100%;
  animation: shimmer 1.5s infinite;
  border-radius: var(--radius-sm);
}

.skeleton.cover {
  aspect-ratio: 3 / 4;
  margin-bottom: var(--space-3);
}

.skeleton.title {
  height: 20px;
  margin-bottom: var(--space-2);
}

.skeleton.author {
  height: 14px;
  width: 60%;
}

@keyframes shimmer {
  0% { background-position: -200% 0; }
  100% { background-position: 200% 0; }
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: var(--space-16) 0;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: var(--space-4);
}

.empty-state h3 {
  font-family: var(--font-display);
  font-size: var(--text-xl);
  margin-bottom: var(--space-2);
}

.empty-state p {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
}

/* 分页 */
.pagination {
  display: flex;
  justify-content: center;
  gap: var(--space-2);
  margin-top: var(--space-10);
}

.page-btn {
  min-width: 40px;
  height: 40px;
  padding: 0 var(--space-3);
  font-size: var(--text-sm);
  color: var(--color-text-secondary);
  background: var(--color-paper-white);
  border: 1px solid var(--color-border);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.page-btn:hover:not(:disabled) {
  border-color: var(--color-vermillion);
  color: var(--color-vermillion);
}

.page-btn.active {
  background: var(--color-vermillion);
  border-color: var(--color-vermillion);
  color: white;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-btn.ellipsis {
  border: none;
  background: transparent;
  cursor: default;
}

@media (max-width: 1024px) {
  .books-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .books-layout {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
  }

  .sidebar-section {
    position: static;
  }

  .category-list {
    flex-direction: row;
    flex-wrap: wrap;
    gap: var(--space-2);
  }

  .category-list li {
    padding: var(--space-2) var(--space-4);
    background: var(--color-paper-white);
  }

  .books-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>