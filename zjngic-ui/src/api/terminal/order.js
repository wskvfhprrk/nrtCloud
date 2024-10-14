import request from '@/utils/request'

// 查询原始订单列表
export function listOrder(query) {
  return request({
    url: '/terminal/order/list',
    method: 'get',
    params: query
  })
}

// 查询原始订单详细
export function getOrder(id) {
  return request({
    url: '/terminal/order/' + id,
    method: 'get'
  })
}

// 新增原始订单
export function addOrder(data) {
  return request({
    url: '/terminal/order',
    method: 'post',
    data: data
  })
}

// 修改原始订单
export function updateOrder(data) {
  return request({
    url: '/terminal/order',
    method: 'put',
    data: data
  })
}

// 删除原始订单
export function delOrder(id) {
  return request({
    url: '/terminal/order/' + id,
    method: 'delete'
  })
}
