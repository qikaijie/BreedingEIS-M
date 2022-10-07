import request from '@/utils/request'

// 查询用户收藏植物列表
export function listCollect(query) {
  return request({
    url: '/os/plantCollect/list',
    method: 'get',
    params: query
  })
}

// 查询用户收藏植物详细
export function getCollect(id) {
  return request({
    url: '/os/collect/' + id,
    method: 'get'
  })
}

// 新增用户收藏植物
export function addCollect(data) {
  return request({
    url: '/os/collect',
    method: 'post',
    data: data
  })
}

// 修改用户收藏植物
export function updateCollect(data) {
  return request({
    url: '/os/collect',
    method: 'put',
    data: data
  })
}

// 删除用户收藏植物
export function delCollect(id) {
  return request({
    url: '/os/plantCollect/' + id,
    method: 'delete'
  })
}

// 导出用户收藏植物
export function exportCollect(query) {
  return request({
    url: '/os/collect/export',
    method: 'get',
    params: query
  })
}

// 取消收藏
export function delPlantCollect(query) {
  return request({
    url: '/os/plantCollect/del',
    method: 'get',
    params: query
  })
}
