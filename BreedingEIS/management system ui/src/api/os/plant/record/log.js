import request from '@/utils/request'

// 查询植物记录日志列表
export function listPlantRecordLog(query) {
  return request({
    url: '/os/plant/record/log/list',
    method: 'get',
    params: query
  })
}

// 查询植物记录日志详细
export function getPlantRecordLog(id) {
  return request({
    url: '/os/plant/record/log/' + id,
    method: 'get'
  })
}

// 新增植物记录日志
export function addPlantRecordLog(data) {
  return request({
    url: '/os/plant/record/log',
    method: 'post',
    data: data
  })
}

// 修改植物记录日志
export function updatePlantRecordLog(data) {
  return request({
    url: '/os/plant/record/log',
    method: 'put',
    data: data
  })
}

// 删除植物记录日志
export function delPlantRecordLog(id) {
  return request({
    url: '/os/plant/record/log/' + id,
    method: 'delete'
  })
}

// 导出植物记录日志
export function exportPlantRecordLog(query) {
  return request({
    url: '/os/plant/record/log/export',
    method: 'get',
    params: query
  })
}
