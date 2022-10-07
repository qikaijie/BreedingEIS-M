import request from '@/utils/request'


// 查询植被记录列表
export function listPlantRecord(query) {
  return request({
    url: '/os/plant/record/list',
    method: 'get',
    params: query
  })
}

// 查询植被记录详细
export function getPlantRecord(id) {
  return request({
    url: '/os/plant/record/' + id,
    method: 'get'
  })
}

// 新增植被记录
export function addPlantRecord(data) {
  return request({
    url: '/os/plant/record',
    method: 'post',
    data: data
  })
}

// 修改植被记录
export function updatePlantRecord(data) {
  return request({
    url: '/os/plant/record',
    method: 'put',
    data: data
  })
}

// 删除植被记录
export function delPlantRecord(id) {
  return request({
    url: '/os/plant/record/' + id,
    method: 'delete'
  })
}

// 导出植被记录
export function exportPlantRecord(query) {
  return request({
    url: '/os/plant/record/export',
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
