import request from '@/utils/request'

// 查询终端机器列表
export function listTerminal(query) {
  return request({
    url: '/terminal/terminal/list',
    method: 'get',
    params: query
  })
}

// 查询终端机器详细
export function getTerminal(id) {
  return request({
    url: '/terminal/terminal/' + id,
    method: 'get'
  })
}

// 新增终端机器
export function addTerminal(data) {
  return request({
    url: '/terminal/terminal',
    method: 'post',
    data: data
  })
}

// 修改终端机器
export function updateTerminal(data) {
  return request({
    url: '/terminal/terminal',
    method: 'put',
    data: data
  })
}

// 删除终端机器
export function delTerminal(id) {
  return request({
    url: '/terminal/terminal/' + id,
    method: 'delete'
  })
}
