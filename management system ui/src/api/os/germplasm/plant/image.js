import request from '@/utils/request'

// 查询植物图片、视频记录列表
export function listSeedlingImage(query) {
  return request({
    url: '/os/seedlingImage/list',
    method: 'get',
    params: query
  })
}

// 查询植物图片、视频记录详细
export function getPlantImage(id) {
  return request({
    url: '/os/plant/image/' + id,
    method: 'get'
  })
}

// 新增植物图片、视频记录
export function addPlantImage(data) {
  return request({
    url: '/os/plant/image',
    method: 'post',
    data: data
  })
}

// 修改植物图片、视频记录
export function updatePlantImage(data) {
  return request({
    url: '/os/plant/image',
    method: 'put',
    data: data
  })
}

// 删除植物图片、视频记录
export function delPlantImage(id) {
  return request({
    url: '/os/plant/image/' + id,
    method: 'delete'
  })
}

// 导出植物图片、视频记录
export function exportPlantImage(query) {
  return request({
    url: '/os/plant/image/export',
    method: 'get',
    params: query
  })
}
