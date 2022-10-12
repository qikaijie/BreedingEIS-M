import request from '@/utils/request'

// 查询sgene列表
export function listSgene(query) {
  return request({
    url: '/os/sgene/list',
    method: 'get',
    params: query
  })
}

// 查询sgene详细
export function getSgene(id) {
  return request({
    url: '/os/sgene/' + id,
    method: 'get'
  })
}

// 新增sgene
export function addSgene(data) {
  return request({
    url: '/os/sgene',
    method: 'post',
    data: data
  })
}

// 修改sgene
export function updateSgene(data) {
  return request({
    url: '/os/sgene',
    method: 'put',
    data: data
  })
}

// 删除sgene
export function delSgene(id) {
  return request({
    url: '/os/sgene/' + id,
    method: 'delete'
  })
}

// 导出sgene
export function exportSgene(query) {
  return request({
    url: '/os/sgene/export',
    method: 'get',
    params: query
  })
}