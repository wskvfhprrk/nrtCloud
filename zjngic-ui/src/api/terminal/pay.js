import request from '@/utils/request'

// 查询订单支付列表
export function listPay(query) {
  return request({
    url: '/terminal/pay/list',
    method: 'get',
    params: query
  })
}

// 查询订单支付详细
export function getPay(id) {
  return request({
    url: '/terminal/pay/' + id,
    method: 'get'
  })
}

// 新增订单支付
export function addPay(data) {
  return request({
    url: '/terminal/pay',
    method: 'post',
    data: data
  })
}

// 修改订单支付
export function updatePay(data) {
  return request({
    url: '/terminal/pay',
    method: 'put',
    data: data
  })
}

// 删除订单支付
export function delPay(id) {
  return request({
    url: '/terminal/pay/' + id,
    method: 'delete'
  })
}
