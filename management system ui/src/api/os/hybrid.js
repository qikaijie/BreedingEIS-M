import request from '@/utils/request'

// 查询杂交组合库列表
export function listHybrid(query) {
  return request({
    url: '/os/hybrid/list',
    method: 'get',
    params: query
  })
}

// 通过年份查询杂交组合库列表
export function listHybridByYear(query) {
  return request({
    url: '/os/hybrid/list_by_year',
    method: 'get',
    params: query
  })
}


// 查询杂交组合库详细
export function getHybrid(id) {
  return request({
    url: '/os/hybrid/' + id,
    method: 'get'
  })
}

// 新增杂交组合库
export function addHybrid(data) {
  return request({
    url: '/os/hybrid/add',
    method: 'post',
    data: data
  })
}

// 修改杂交组合库
export function updateHybrid(data) {
  return request({
    url: '/os/hybrid',
    method: 'put',
    data: data
  })
}

// 删除杂交组合库
export function delHybrid(id) {
  return request({
    url: '/os/hybrid/' + id,
    method: 'delete'
  })
}

// 导出杂交组合库
export function exportHybrid(query) {
  return request({
    url: '/os/hybrid/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/os/hybrid/importTemplate',
    method: 'get'
  })
}
