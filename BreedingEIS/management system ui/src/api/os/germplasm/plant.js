import request from '@/utils/request'

// 查询植物列表
export function listSeedling(query) {
  return request({
    url: '/os/seedling/list',
    method: 'get',
    params: query
  })
}

// 查询植物详细
export function getSeedling(id) {
  return request({
    url: '/os/seedling/' + id,
    method: 'get'
  })
}

//获取植物二维码
export function getQRCode(id, lang) {
  return request({
    url: '/os/seedling/getQRCode/' + id + '?lang=' + lang,
    method: 'get'
  })
}

//新增植物
export function addSeedling(data) {
  return request({
    url: '/os/seedling/autoGenerate',
    method: 'post',
    data: data
  })
}

// 修改植物
export function updateSeedling(data) {
  return request({
    url: '/os/seedling/update',
    method: 'put',
    data: data
  })
}

//删除植物
export function delListSeedling(query) {
  return request({
    url: '/os/seedling/delList',
    method: 'get',
    params: query
  })
}

// 删除植物
export function delSeedling(id) {
  return request({
    url: '/os/seedling/del/' + id,
    method: 'get'
  })
}

// 导出植物
export function exportSeedling(query) {
  return request({
    url: '/os/seedling/export',
    method: 'get',
    params: query
  })
}
