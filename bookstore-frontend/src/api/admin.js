import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

request.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export const adminApi = {
  // 书籍管理
  getBookList(params) {
    return request.get('/admin/book/list', { params })
  },
  addBook(data) {
    return request.post('/admin/book/add', data)
  },
  updateBook(data) {
    return request.put('/admin/book/update', data)
  },
  deleteBook(id) {
    return request.delete(`/admin/book/${id}`)
  },
  updateBookStatus(id, status) {
    return request.put(`/admin/book/${id}/status`, null, { params: { status } })
  },

  // 分类管理
  addCategory(data) {
    return request.post('/admin/category/add', data)
  },
  updateCategory(data) {
    return request.put('/admin/category/update', data)
  },
  deleteCategory(id) {
    return request.delete(`/admin/category/${id}`)
  },

  // 订单管理
  getOrderList(params) {
    return request.get('/admin/order/list', { params })
  },
  shipOrder(id) {
    return request.put(`/admin/order/${id}/ship`)
  },
  deliverOrder(id) {
    return request.put(`/admin/order/${id}/deliver`)
  },

  // 用户管理
  getUserList(params) {
    return request.get('/admin/user/list', { params })
  },
  disableUser(id) {
    return request.put(`/admin/user/${id}/disable`)
  },
  enableUser(id) {
    return request.put(`/admin/user/${id}/enable`)
  },

  // 评论管理
  getReviewList(params) {
    return request.get('/admin/review/list', { params })
  },
  deleteReview(id) {
    return request.delete(`/admin/review/${id}`)
  },
  hideReview(id) {
    return request.put(`/admin/review/${id}/hide`)
  }
}

export default request