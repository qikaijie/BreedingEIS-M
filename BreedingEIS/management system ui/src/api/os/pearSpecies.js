import request from '@/utils/request'

// 查询梨物种列表
export function listPearSpecies(query) {
  return request({
    url: '/os/pearSpecies/list',
    method: 'get',
    params: query
  })
}

// 查询梨物种详细
export function getPearSpecies(id) {
  return request({
    url: '/os/pearSpecies/' + id,
    method: 'get'
  })
}

// 新增梨物种
export function addPearSpecies(data) {
  return request({
    url: '/os/pearSpecies',
    method: 'post',
    data: data
  })
}

// 修改梨物种
export function updatePearSpecies(data) {
  return request({
    url: '/os/pearSpecies',
    method: 'put',
    data: data
  })
}

// 删除梨物种
export function delPearSpecies(id) {
  return request({
    url: '/os/pearSpecies/' + id,
    method: 'delete'
  })
}

// 导出梨物种
export function exportPearSpecies(query) {
  return request({
    url: '/os/pearSpecies/export',
    method: 'get',
    params: query
  })
}