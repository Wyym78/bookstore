<template>
  <div class="home-page">
    <!-- Hero 区域 -->
    <section class="hero">
      <div class="hero-bg">
        <div class="hero-pattern"></div>
      </div>
      <div class="hero-content">
        <h1 class="hero-title">发现你的下一本好书</h1>
        <p class="hero-subtitle">精选优质书籍，让阅读成为习惯</p>
        <div class="hero-search">
          <input
            v-model="searchKeyword"
            type="text"
            placeholder="搜索书名、作者、ISBN..."
            @keyup.enter="handleSearch"
          />
          <button @click="handleSearch">搜索</button>
        </div>
      </div>
    </section>

    <!-- 分类导航 -->
    <section class="categories">
      <div class="page-container">
        <div class="section-header">
          <h2>分类浏览</h2>
        </div>
        <div class="category-list">
          <div
            v-for="(cat, index) in categories"
            :key="cat.id"
            class="category-item"
            :style="{ animationDelay: `${index * 0.1}s` }"
            @click="goToCategory(cat.id)"
          >
            <span class="category-icon">{{ getCategoryIcon(cat.name) }}</span>
            <span class="category-name">{{ cat.name }}</span>
          </div>
        </div>
      </div>
    </section>

    <!-- 畅销书籍 -->
    <section class="books-section">
      <div class="page-container">
        <div class="section-header">
          <h2>畅销书籍</h2>
          <router-link to="/books" class="more-link">查看更多 →</router-link>
        </div>
        <div class="books-grid">
          <BookCard
            v-for="(book, index) in popularBooks"
            :key="book.id"
            :book="book"
            :style="{ animationDelay: `${index * 0.1}s` }"
            class="book-card-animate"
            @click="goToBook(book.id)"
          />
        </div>
      </div>
    </section>

    <!-- 新书上架 -->
    <section class="books-section new-books">
      <div class="page-container">
        <div class="section-header">
          <h2>新书上架</h2>
          <router-link to="/books?sort=new" class="more-link">查看更多 →</router-link>
        </div>
        <div class="books-scroll">
          <div
            v-for="(book, index) in newBooks"
            :key="book.id"
            class="book-scroll-item"
            :style="{ animationDelay: `${index * 0.1}s` }"
            @click="goToBook(book.id)"
          >
            <div class="book-cover">
              <img :src="book.coverUrl || '/placeholder-book.png'" :alt="book.title" />
            </div>
            <div class="book-info">
              <h4>{{ book.title }}</h4>
              <p>{{ book.author }}</p>
              <span class="price">¥{{ book.price }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 推荐阅读 -->
    <section class="recommend-section">
      <div class="page-container">
        <div class="section-header">
          <h2>阅读推荐</h2>
        </div>
        <div class="recommend-grid">
          <div class="recommend-item" v-for="(item, index) in recommends" :key="index">
            <div class="recommend-icon">{{ item.icon }}</div>
            <h3>{{ item.title }}</h3>
            <p>{{ item.desc }}</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { bookApi } from '@/api/book'
import { categoryApi } from '@/api/category'
import BookCard from '@/components/business/BookCard.vue'

const router = useRouter()

const searchKeyword = ref('')
const categories = ref([])
const popularBooks = ref([])
const newBooks = ref([])

const recommends = [
  { icon: '📖', title: '海量书库', desc: '涵盖文学、科技、历史等各类优质书籍' },
  { icon: '🚚', title: '快速配送', desc: '下单后24小时内发货，省内1-3天送达' },
  { icon: '💯', title: '正品保障', desc: '所有商品均为正品，假一赔十' },
  { icon: '🔄', title: '无忧退换', desc: '7天内可申请退换货，售后无忧' }
]

const getCategoryIcon = (name) => {
  const icons = {
    '文学': '📚',
    '科幻': '🚀',
    '技术': '💻',
    '儿童': '🎈',
    '历史': '🏛️'
  }
  return icons[name] || '📖'
}

const handleSearch = () => {
  router.push({ path: '/books', query: { keyword: searchKeyword.value } })
}

const goToCategory = (id) => {
  router.push({ path: '/books', query: { categoryId: id } })
}

const goToBook = (id) => {
  router.push({ path: `/book/${id}` })
}

onMounted(async () => {
  try {
    // 获取分类
    const catRes = await categoryApi.getList()
    categories.value = catRes.data || []

    // 获取书籍列表
    const bookRes = await bookApi.getList({ pageNum: 1, pageSize: 8 })
    const books = bookRes.data?.records || []
    popularBooks.value = books.slice(0, 4)
    newBooks.value = books.slice(4, 8)
  } catch (error) {
    console.error('Failed to load data:', error)
  }
})
</script>

<style scoped>
.home-page {
  overflow-x: hidden;
}

/* Hero 区域 */
.hero {
  position: relative;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.hero-bg {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, var(--color-ink) 0%, #2a2a2a 50%, var(--color-ink-light) 100%);
}

.hero-pattern {
  position: absolute;
  inset: 0;
  background-image:
    radial-gradient(circle at 30% 70%, rgba(201, 64, 67, 0.15) 0%, transparent 50%),
    radial-gradient(circle at 70% 30%, rgba(122, 158, 126, 0.1) 0%, transparent 50%);
}

.hero-content {
  position: relative;
  z-index: 1;
  text-align: center;
  color: white;
  animation: fadeInUp 0.8s ease;
}

.hero-title {
  font-family: var(--font-display);
  font-size: var(--text-5xl);
  font-weight: 700;
  letter-spacing: 4px;
  margin-bottom: var(--space-4);
}

.hero-subtitle {
  font-size: var(--text-xl);
  opacity: 0.8;
  margin-bottom: var(--space-10);
}

.hero-search {
  display: flex;
  max-width: 560px;
  margin: 0 auto;
  background: white;
  border-radius: var(--radius-full);
  overflow: hidden;
  box-shadow: var(--shadow-xl);
}

.hero-search input {
  flex: 1;
  padding: var(--space-5) var(--space-6);
  border: none;
  font-size: var(--text-base);
  color: var(--color-text-primary);
}

.hero-search input::placeholder {
  color: var(--color-text-muted);
}

.hero-search input:focus {
  outline: none;
}

.hero-search button {
  padding: var(--space-5) var(--space-10);
  background: var(--color-vermillion);
  color: white;
  font-size: var(--text-base);
  font-weight: 600;
  border: none;
  cursor: pointer;
  transition: background var(--transition-fast);
}

.hero-search button:hover {
  background: var(--color-vermillion-light);
}

/* 分类导航 */
.categories {
  padding: var(--space-12) 0;
  background: var(--color-paper-white);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-8);
}

.section-header h2 {
  font-family: var(--font-display);
  font-size: var(--text-2xl);
  color: var(--color-ink);
}

.more-link {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
  transition: color var(--transition-fast);
}

.more-link:hover {
  color: var(--color-vermillion);
}

.category-list {
  display: flex;
  gap: var(--space-6);
  overflow-x: auto;
  padding-bottom: var(--space-2);
}

.category-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--space-3);
  padding: var(--space-5) var(--space-6);
  background: var(--color-paper);
  border-radius: var(--radius-lg);
  cursor: pointer;
  transition: all var(--transition-base);
  animation: fadeInUp 0.5s ease backwards;
  min-width: 100px;
}

.category-item:hover {
  background: var(--color-vermillion);
  color: white;
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
}

.category-icon {
  font-size: 36px;
}

.category-name {
  font-size: var(--text-sm);
  font-weight: 500;
}

/* 书籍网格 */
.books-section {
  padding: var(--space-12) 0;
}

.books-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: var(--space-6);
}

.book-card-animate {
  animation: fadeInUp 0.5s ease backwards;
}

/* 新书上架 - 横向滚动 */
.new-books {
  background: var(--color-paper-dark);
}

.books-scroll {
  display: flex;
  gap: var(--space-6);
  overflow-x: auto;
  padding-bottom: var(--space-4);
  scroll-snap-type: x mandatory;
}

.book-scroll-item {
  flex: 0 0 220px;
  background: var(--color-paper-white);
  border-radius: var(--radius-lg);
  overflow: hidden;
  cursor: pointer;
  transition: transform var(--transition-base);
  scroll-snap-align: start;
  animation: fadeInUp 0.5s ease backwards;
}

.book-scroll-item:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
}

.book-scroll-item .book-cover {
  aspect-ratio: 3 / 4;
  overflow: hidden;
}

.book-scroll-item .book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-slow);
}

.book-scroll-item:hover .book-cover img {
  transform: scale(1.05);
}

.book-scroll-item .book-info {
  padding: var(--space-4);
}

.book-scroll-item h4 {
  font-family: var(--font-display);
  font-size: var(--text-sm);
  font-weight: 600;
  margin-bottom: var(--space-1);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.book-scroll-item p {
  font-size: var(--text-xs);
  color: var(--color-text-muted);
  margin-bottom: var(--space-2);
}

.book-scroll-item .price {
  font-family: var(--font-accent);
  font-size: var(--text-base);
  font-weight: 600;
  color: var(--color-copper);
}

/* 推荐区域 */
.recommend-section {
  padding: var(--space-16) 0;
}

.recommend-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: var(--space-6);
}

.recommend-item {
  text-align: center;
  padding: var(--space-8);
  background: var(--color-paper-white);
  border-radius: var(--radius-lg);
  border: 1px solid var(--color-border-light);
  transition: all var(--transition-base);
}

.recommend-item:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
  border-color: var(--color-vermillion);
}

.recommend-icon {
  font-size: 48px;
  margin-bottom: var(--space-4);
}

.recommend-item h3 {
  font-family: var(--font-display);
  font-size: var(--text-lg);
  margin-bottom: var(--space-2);
}

.recommend-item p {
  font-size: var(--text-sm);
  color: var(--color-text-muted);
}

@media (max-width: 1024px) {
  .books-grid,
  .recommend-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 768px) {
  .hero {
    height: 400px;
  }

  .hero-title {
    font-size: var(--text-3xl);
  }

  .hero-subtitle {
    font-size: var(--text-base);
  }

  .books-grid,
  .recommend-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .category-list {
    gap: var(--space-4);
  }

  .category-item {
    min-width: 80px;
    padding: var(--space-4);
  }
}
</style>