import request from '@/utils/request'

// 查询属性池列表
export function listAttribute(query) {
  return request({
    url: '/os/attribute/list',
    method: 'get',
    params: query
  })
}

// 查询属性池详细
export function getAttribute(id) {
  return request({
    url: '/os/attribute/' + id,
    method: 'get'
  })
}

// 新增属性池
export function addAttribute(data) {
  return request({
    url: '/os/attribute/add',
    method: 'post',
    data: data
  })
}

// 修改属性池
export function updateAttribute(data) {
  return request({
    url: '/os/attribute/update',
    method: 'post',
    data: data
  })
}

// 删除属性池
export function delAttribute(id) {
  return request({
    url: '/os/attribute/' + id,
    method: 'delete'
  })
}

// 导出属性池
export function exportAttribute(query) {
  return request({
    url: '/os/attribute/export',
    method: 'get',
    params: query
  })
}

// 导入属性池
export function importAttribute(query) {
  return request({
    url: '/os/attribute/export',
    method: 'get',
    params: query
  })
}
