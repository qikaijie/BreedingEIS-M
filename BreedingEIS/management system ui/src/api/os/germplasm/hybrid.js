import request from '@/utils/request'

//种质信息列表查询
export function listGermplasm(query) {
  return request({
    url: '/os/germplasm/list',
    method: 'get',
    params: query
  })
}

//添加种质详细信息
export function addGermplasm(data) {
  return request({
    url: '/os/germplasm',
    method: 'post',
    data: data
  })
}

// 修改种质详细信息
export function updateGermplasm(data) {
  return request({
    url: '/os/germplasm',
    method: 'put',
    data: data
  })
}

// 通过名称查询种质信息列表
export function listByName(data) {
  return request({
    url: '/os/germplasm/listByName',
    method: 'GET',
    params: data
  })
}



// 通过年份查询杂交组合库列表
export function listGermplasmByYear(query) {
  return request({
    url: '/os/germplasm/listByYear',
    method: 'get',
    params: query
  })
}


// 查询杂交组合库详细
export function getGermplasm(id) {
  return request({
    url: '/os/germplasm/' + id,
    method: 'get'
  })
}



// 删除杂交组合库
export function delGermplasm(id) {
  return request({
    url: '/os/germplasm/' + id,
    method: 'delete'
  })
}

// 导出杂交组合库
export function exportGermplasm(query) {
  return request({
    url: '/os/germplasm/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/os/germplasm/importTemplate',
    method: 'get'
  })
}
