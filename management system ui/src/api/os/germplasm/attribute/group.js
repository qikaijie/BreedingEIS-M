import request from '@/utils/request'


// 查询属性分组列表
export function listAttributeGroup(query) {
  return request({
    url: '/os/attribute/group/list',
    method: 'get',
    params: query
  })
}

//查询分组下的属性集合
export function listAttributeExistent(query) {
  return request({
    url: '/os/attribute/list/existent',
    method: 'get',
    params: query
  })
}

//查询非分组下的属性集合，供分组下新增属性使用
export function listAttributeNonExistent(query) {
  return request({
    url: '/os/attribute/list/non-existent',
    method: 'get',
    params: query
  })
}

//查询所有的属性集合，属性信息附带属性组信息
export function listAttributeAll(query) {
  return request({
    url: '/os/attribute/list/all',
    method: 'get',
    params: query
  })
}

// 查询属性分组详细
export function getAttributeGroup(id) {
  return request({
    url: '/os/attribute/group/' + id,
    method: 'get'
  })
}

// 新增属性分组
export function addAttributeGroup(data) {
  return request({
    url: '/os/attribute/group/add',
    method: 'post',
    data: data
  })
}

// 修改属性分组
export function updateAttributeGroup(data) {
  return request({
    url: '/os/attribute/group/update',
    method: 'POST',
    data: data
  })
}

// 删除属性分组
export function delAttributeGroup(id) {
  return request({
    url: '/os/attribute/group/' + id,
    method: 'delete'
  })
}

// 导出属性分组
export function exportAttributeGroup(query) {
  return request({
    url: '/os/attribute/group/export',
    method: 'get',
    params: query
  })
}

// 隐藏/显示属性组
export function hiddenAttributeGroup(query) {
  return request({
    url: '/os/attribute/group/hidden',
    method: 'GET',
    params: query
  })
}

// 属性组下面的属性排序
export function sortRelationAttribute(data) {
  return request({
    url: '/os/attribute/group/relation/sort',
    method: 'POST',
    data: data
  })
}
