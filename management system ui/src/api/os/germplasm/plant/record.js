import request from '@/utils/request'


// 根据植物id查询植被记录列表
export function listBySeedlingId(query) {
  return request({
    url: '/os/seedling/record/listBySeedlingId',
    method: 'get',
    params: query
  })
}

// 查询植被记录列表
export function listSeedlingRecord(query) {
  return request({
    url: '/os/seedling/record/list',
    method: 'get',
    params: query
  })
}

// 查询植被记录详细
export function getSeedlingRecord(id) {
  return request({
    url: '/os/seedling/record/' + id,
    method: 'get'
  })
}

// 新增植被记录
export function addSeedlingRecord(data) {
  return request({
    url: '/os/seedling/record',
    method: 'post',
    data: data
  })
}

// 修改植被记录
export function updateSeedlingRecord(data) {
  return request({
    url: '/os/seedling/record',
    method: 'put',
    data: data
  })
}

// 删除植被记录
export function delSeedlingRecord(id) {
  return request({
    url: '/os/seedling/record/' + id,
    method: 'delete'
  })
}

// 导出植被记录
export function exportSeedlingRecord(query) {
  return request({
    url: '/os/seedling/record/export',
    method: 'get',
    params: query
  })
}

//下载用户导入模板
export function importTemplate() {
  return request({
    url: '/os/plant/record/importTemplate',
    method: 'get'
  })
}
