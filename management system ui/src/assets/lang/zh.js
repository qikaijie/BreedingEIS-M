export default {
  login: {
    title: '云上后稷',
    name: '资源与育种评价信息系统',
    logIn: '登录',
    logIning: '登 录 中...',
    username: '账号',
    password: '密码',
    code:'验证码',
    rememberMe:'记住密码',
    introduction: '简介',
    tip: '登录状态已过期，您可以继续留在该页面，或者重新登',
    tipTitle: '系统提示',
    reLogin: '重新登录',
    download: 
    {
    	title: '下载APP',
    	android: 'Android版本下载',
    	ios: 'iPhone版本下载'
    },
    sgeneSearch: 
    {
    	title: 'S基因搜索',
    	speciesName: '品种名',
    	speciesEnName: '品种英文名',
    	s1: 's1',
    	s2: 's2',
    	s3: 's3',
    	s4: 's4',
    	sGene: 's基因',
    	bloomStartDate: '盛花期始',
    	bloomEndDate: '盛花期末',
    	pollenAbortion: '花粉败育',
    	recommended: '优先推荐',
    	source: '来源',
    	area: '主栽地区',
    	updateDate: '更新时间'
    },
    pearSpeciesSearch: 
    {
    	title: '梨品种搜索',
    	speciesName: '品种名',
    	maleParent: '父本',
    	femaleParent: '母本',
    	company: '选育单位',
    	remark: '备注',
    	reference: '参考文献'
    },
    tip: {
    	username: '用户名不能为空',
    	password: '密码不能为空',
    	code: '验证码不能为空'
    }
  },
  menu: {
	index: {
		title: {
			home: '首页',
			usercenter: '个人中心',
			layoutsettings: '布局设置',
			logout: '退出',
			gaojie: '高接数据'
		},
		label: {
			huanying: '欢迎使用资源与育种评价信息系统',
			xiazai: '下载<<农业种质资源与育种评价信息系统简介>>'
		},
		tips: {
			logout: '确定注销并退出系统吗？',
		}
	},
	breeding: {
		breeding: {
			title: {
				xinzeng: '新增育种群体',
				xiugai: '修改育种群体',
				import:'育种数据导入',
				importresult:'导入结果',
				zajiaoinfo: '育种信息',
				quzhonginfo: '取种信息',
				bozhonginfo: '播种信息',
				dingzhiinfo: '定植信息'
			},
			label: {
				qinben1:'亲本1',
				qinben2:'亲本2',
				nianfen:'年份',
				zajiaodaihao:'育种代号',
				muben:'母本',
				mubenlaiyuan:'母本来源',
				fuben:'父本',
				fubenlaiyuan:'父本来源',
				operator: '操作人员',
				zajiaoriqi:'育种日期',
				caiguoriqi:'采果日期',
				quzhongriqi:'取种日期',
				zhongzishuliang:'种子数量',
				bozhongriqi:'播种日期',
				bozhongmethod: '播种方式',
				cengjiriqi:'种子层积时间',
				dingzhimethod:'定植方式',
				dingzhisite:'定植地点',
				dingzhicount:'定植总数',
				dingzhiriqi:'定植日期',
				leader:'负责人',
				contact:'联系信息',
				chakantiaoma:'查看条码',
			},
			button: {
				add: '新增育种群体'
			},
			tips: {
				checkmuben:'请输入母本',
				checkfuben:'请输入父本',
				checkzajiaoriqi:'请输入育种日期',
				checkzajiaodaihao:'请输入育种代号',
				exportconfirm:'是否确认导出所有育种群体库数据项?',
				deleteconfirm:'是否确认删除育种群体库数据项?',
				
			}
		},
		tiaomaku: {
			title: {
				viewerweima: '查看二维码',
				createtiaoma: '生成条码',
			},
			label: {
				year: '引进年份',
				zajiaozuhe: '育种群体',
				type: '类型',
				code:'条码',
				tiaoma: '条码',
				duanma: '短码',
				changma: '长码',
				createtime: '创建时间',
				plantbase: '基地代号',
				plantarea: '苗圃代号',
				plantridge: '垄数',
				plantrow: '每垄行数',
				maxnum: '最大株数',
				hybrid: '育种群体',
				fieldnumber: '组合代号',
				
			},
			button: {
				createtiaoma: '生成条码',
				createerweima: '生成二维码',
				exporterweima: '导出二维码',
				exporttiaoma: '导出条码信息',
				download: '下载二维码',
				generate: '定位',
				creategaojiema: '高接码',
			},
			tips: {
				checkzajiaozuhe: '请先选择育种群体',
				deleteconfirm: '是否确认删除当前搜索到的所有植物信息?',
				exporttiaomaconfirm: '是否确认导出当前搜索到的所有植物信息?',
				exporterweimaconfirm: '是否确认导出当前搜索到的所有植物二维码文件?',
				plantbase: '请输入基地代号',
				plantarea: '请输入苗圃代号',
				plantridge: '请输入正确的垄数',
				plantrow: '请输入每垄行数',
				maxnum: '请输入最大株数',
				fieldnumber: '请输入组合代号',
				
			}
		},
		recodelist: {
			title: {
				importrecode: '填报数据导入',
				importresult: '导入结果',
				viewimg: '查看图片',
				
			},
			label: {
				nianfen: '年份',
				zajiaozuhe: '育种群体',
				tiaoma: '条码信息',
				timesection: '时间区间',
				timeto: '至',
				starttime: '开始日期',
				endtime: '结束日期',
				time: '时间',
				creater: '填报人',
				evaluationcontent: '评价内容',
				image: '图片',
			},
			button: {
				
			},
			tips: {
				exportconfirm: '是否确认导出搜索的填报记录列表数据?',
			}
		},
		collect: {
			title: {
				
			},
			label: {
				tiaoma: '条码信息',
				zajiaozuhe: '育种群体',
				creater: '收藏人',
				date: '收藏日期',

			},
			button: {
				cancel: '取消收藏',
			},
			tips: {
				cancel: '是否确认取消收藏?',
			}
		},
		statistical: {
			title: {
				
			},
			label: {
				year: '年份',
				zajiaozuhe: '育种群体',
				tiaoma: '条码信息',

			},
			button: {
				
			},
			tips: {
				
			}
		}
	},
	germplasm: {
		resources: {
			title: {
				xuanyu: '选育品种',
				add: '新增种质资源',
				import: '种质资源导入',
				importresult: '导入结果',
			},
			label: {
				name: '名称',
				source: '来源',
				year: '引进年份',	
				alias: '别名',
				type: '资源类型',
				sourcearea: '来源地',
				yinjinfangshi: '引进方式',
				pumingcheng: '保存圃名称',
				baocunfangshi: '保存方式',
				count: '保存数量',
				unit: '（株）',
				baohu: '品种保护',
				baocunnum: '保存编号',
				characterization: '特性描述',
				application: '应用情况',
				img: '图片',
				xuanyudanwei: '选育单位',
				xuanyufangfa: '选育方法',
				yuchengyear: '育成年份',
				chakatiaoma: '查看条码',
				yuanlianxiren: '原引地联系人',
				yinjinren: '引进人',
				username: '姓名',
				phone: '联系方式',

			},
			button: {
				add: '新增种质资源',
			},
			tips: {
				checkbaocunnum: '请输入编号',
				checkname: '请输入种质资源名称',
				checkyear: '请输入引进年份',
				exportconfirm:'是否确认导出所有种质资源数据项?',
				deleteconfirm:'是否确认删除种质资源数据项?',
			}
		},
		tiaomaku: {
			title: {
				viewerweima: '查看二维码',
				createtiaoma: '生成条码',
			},
			label: {
				year: '引进年份',
				name: '种质资源名称',
				tiaoma: '条码',
				duanma: '短码',
				changma: '长码',
				createtime: '创建时间',
				plantbase: '基地代号',
				plantarea: '苗圃代号',
				plantridge: '垄数',
				plantrow: '每垄行数',
				maxnum: '最大株数',
				hybrid: '育种群体',
				fieldnumber: '组合代号',
				
			},
			button: {
				createtiaoma: '生成条码',
				createerweima: '生成二维码',
				exporterweima: '导出二维码',
				exporttiaoma: '导出条码信息',
				download: '下载二维码',
				generate: '定位',

			},
			tips: {
				checkzajiaozuhe: '请先选择育种群体',
				deleteconfirm: '是否确认删除当前搜索到的所有植物信息?',
				exporttiaomaconfirm: '是否确认导出当前搜索到的所有植物信息?',
				exporterweimaconfirm: '是否确认导出当前搜索到的所有植物二维码文件?',
				plantbase: '请输入基地代号',
				plantarea: '请输入苗圃代号',
				plantridge: '请输入垄数',
				plantrow: '请输入每垄行数',
				maxnum: '请输入最大株数',
				fieldnumber: '请输入组合代号',
				
			}
		},
		recodelist: {
			title: {
				importrecode: '填报数据导入',
				importresult: '导入结果',
				viewimg: '查看图片',
				
			},
			label: {
				nianfen: '年份',
				name: '种质资源名称',
				tiaoma: '条码信息',
				timesection: '时间区间',
				timeto: '至',
				starttime: '开始日期',
				endtime: '结束日期',
				time: '时间',
				creater: '填报人',
				evaluationcontent: '评价内容',
				image: '图片',
			},
			button: {
				
			},
			tips: {
				exportconfirm: '是否确认导出搜索的填报记录列表数据?',
			}
		},
		collect: {
			title: {
				
			},
			label: {
				tiaoma: '条码信息',
				name: '种质资源名称',
				creater: '收藏人',
				date: '收藏日期',

			},
			button: {
				cancel: '取消收藏',
			},
			tips: {
				cancel: '是否确认取消收藏?',
			}
		},
		statistical: {
			title: {
				
			},
			label: {
				year: '年份',
				zajiaozuhe: '育种群体',
				tiaoma: '条码信息',

			},
			button: {
				
			},
			tips: {
				
			}
		}
	},
	properties: {
		species: {
			title: {
				add: '添加物种',
				edit: '修改物种',
				typelist: '分类列表',
				typeadd: '添加分类',
				typeedit: '修改分类',
			},
			label: {
				speciesname: '物种名称',
				typename: '分类名称',
				xingzhuang: '选择性状',
				xingzhuangname: '性状名称',
				xingzhuangtype: '已加入分类',

			},
			button: {
				add: '添加物种',
				addtype: '添加分类',
			},
			tips: {
				checktypename: '请输入分类名称',
				checkspeciesname: '请选中物种',
				checkadditive: '请选择属性',
				checkdelete: '是否确认删除该属性分组?',
				show: '显示成功',
				hidden: '隐藏成功',
			}
		},
		additive: {
			title: {
				add: '添加属性',
				edit: '修改属性',
			},
			label: {
				xingzhuangname: '性状名称',
				type: '类型',
				content: '内容',
				createtime: '创建时间',
				speciesname: '选择物种',
			},
			button: {
				add: '添加性状',
				import: '导入性状',
			},
			tips: {
				checkoption: '请输入选项',
			}
		},
		classification: {
			title: {
				list: '性状列表',
			},
			label: {
				xingzhuangname: '性状名称',
				type: '类型',
				content: '内容',
			},
			button: {
				
			},
			tips: {
				orderby: '排序成功，列表已更新。',
			}
		}
	},
	feedback: {
		title: {
			feedback: '处理意见反馈',
			add: '添加意见反馈',
		},
		label: {
			content: '反馈内容',
			type: '状态',
			creater: '反馈人',
			channel: '反馈渠道',
			reply: '答复',

		},
		button: {
			handle: '处理',
		},
		tips: {
			export: '是否确认导出所有意见反馈数据项?',
		}
	},
	user: {
		title: '个人信息',
		info: '基本资料',
		name: '用户名称',
		nickname: '用户昵称',
		phone: '手机号码',
		email: '用户邮箱',
		sex: '性别',
		dept: '所属部门',
		role: '所属角色',
		date: '创建日期',
		spaces: '物种',
		password: {
			title: '修改密码',
			oldpass: '旧密码',
			newpass: '新密码',
			twopass: '确认密码',
			tips: {
				oldpass: '旧密码不能为空',
				newpass: '新密码不能为空',
				twopass: '确认密码不能为空'
			}
		},
		avatar: {
			title: '修改头像',
			upload: '上传',
			tips: '点击上传头像'
		}
	}
  },
  common: {
	name: '资源与育种评价信息系统',
	title: {
		warning:'警告',
	},
	button: {
	  ok:'确定',
	  cancle:'取消',
	  submit: '提交',
	  add:'新增',
	  edit:'修改',
	  view:'查看',
	  delete:'删除',
	  search:'查询',
	  reset:'重置',
	  import:'导入',
	  export:'导出',
	  upload:'点击上传',
	  show:'显示',
	  hidden:'隐藏',
	  downloadtemplate:'下载模板',
	},
	label: {
	  sequence:'序号',
	  operation:'操作',
	  orderby:'排序',
	  remarks:'备注',
	  selectupdate:'是否更新已经存在的用户数据',
	  pending: '待处理',
	  processed: '已处理',
	  input: '输入框',
      text: '文本框',
      radio: '单选',
      checkbox: '多选',
      date: '日期'
	},
	tips: {
		checkint:'请输入整数',
		checkphone:'请输入正确的联系方式',
		success:'操作成功',
		fail:'操作失败',
		uploadoperate:'将文件拖到此处，或',
		importfileext:'提示：仅允许导入“xls”或“xlsx”格式文件！',
	}
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有'
  },
  settings: {
    title: '系统布局配置',
    theme: '主题色',
    tagsView: '开启 Tags-View',
    fixedHeader: '固定 Header',
    sidebarLogo: '侧边栏 Logo'
  }
}