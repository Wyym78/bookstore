import api from './index'

export const orderApi = {
  create(data) {
    return api.post('/order/create', data)
  },
  getList(params) {
    return api.get('/order/list', { params })
  },
  getDetail(id) {
    return api.get(`/order/${id}`)
  },
  pay(id) {
    return api.put(`/order/${id}/pay`)
  },
  cancel(id) {
    return api.put(`/order/${id}/cancel`)
  },
  confirm(id) {
    return api.put(`/order/${id}/confirm`)
  }
}