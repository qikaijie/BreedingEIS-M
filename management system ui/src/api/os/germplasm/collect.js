import request from '@/utils/request'

// 查询用户收藏植物列表
export function listCollect(query) {
  return request({
    url: '/os/seedlingCollect/list',
    method: 'get',
    params: query
  })
}

// 删除用户收藏植物
export function delCollect(id) {
  return request({
    url: '/os/seedlingCollect/' + id,
    method: 'delete'
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


// 导出用户收藏植物
export function exportCollect(query) {
  return request({
    url: '/os/collect/export',
    method: 'get',
    params: query
  })
}
