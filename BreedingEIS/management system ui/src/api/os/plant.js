import request from '@/utils/request'

// 查询植物列表
export function listPlant(query) {
  return request({
    url: '/os/plant/list',
    method: 'get',
    params: query
  })
}

// 查询植物列表
export function listByHybridId(query) {
  return request({
    url: '/os/plant/listByHybridId',
    method: 'get',
    params: query
  })
}

// 查询植物详细
export function getPlant(id) {
  return request({
    url: '/os/plant/' + id,
    method: 'get'
  })
}

//获取植物二维码
export function getQRCode(id, lang) {
  return request({
    url: '/os/plant/getQRCode/' + id + '?lang=' + lang,
    method: 'get'
  })
}

// 新增植物
export function addPlant(data) {
  return request({
    url: '/os/plant/autoGenerate',
    method: 'post',
    data: data
  })
}

// 修改植物
export function updatePlant(data) {
  return request({
    url: '/os/plant/update',
    method: 'put',
    data: data
  })
}

//删除植物
export function delListPlant(query) {
  return request({
    url: '/os/plant/delList',
    method: 'get',
    params: query
  })
}

// 删除植物
export function delPlant(id) {
  return request({
    url: '/os/plant/del/' + id,
    method: 'get'
  })
}

// 导出植物
export function exportPlant(query) {
  return request({
    url: '/os/plant/export',
    method: 'get',
    params: query
  })
}
