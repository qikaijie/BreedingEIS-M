export default {
  login: {
	    title: '',
	    name: 'Breeding evaluation information system (BreedingEIS)',
	    logIn: 'Sign in',
	    logIning: 'Logging in...',
	    username: 'Account',
	    password: 'Password',
	    code:'Verification Code',
	    rememberMe:'Forget password',
	    introduction: 'Introduction',
	    tip: 'The login status has expired, you can continue to stay on this page, or re login.',
	    tipTitle: 'System prompt',
	    reLogin: 're login',
	    download: 
	    {
	    	title: 'Download app',
	    	android: 'Android app Download',
	    	ios: 'IOS app Download'
	    },
	    sgeneSearch: 
	    {
	     title: 'Search S-gene',
	     speciesName: 'Vaiety',
	     speciesEnName: 'Vaiety-En',
	     s1: 's1',
	     s2: 's2',
	     s3: 's3',
	     s4: 's4',
	     sGene: 'S-gene',
	     bloomStartDate: 'Full-bloom stage start',
	     bloomEndDate: 'Full-bloom stage end',
	     pollenAbortion: 'Pollen abortion',
	     recommended: 'Featured first',
	     source: 'Source',
	     area: 'Area',
	     updateDate: 'Update date'
	    },
	    pearSpeciesSearch: 
	    {
	     title: 'Search vaiety',
	     speciesName: 'Vaiety',
	     maleParent: 'Male parent',
	     femaleParent: 'Female parent',
	     company: 'Breeding units',
	     remark: 'Remark',
	     reference: 'References'
	    },
	    tip: {
	    	username: 'Account cannot be null.',
	    	password: 'Password cannot be null.',
	    	code: 'Verification Code cannot be null.'
	    }
  },
  menu: {
	index: {
		title: {
			home: 'Home',
			usercenter: 'Personal Center',
			layoutsettings: 'Layout Settings',
			logout: 'Logout',
			gaojie: 'gaojie Data'
		},
		label: {
			huanying: 'Welcome to the Breeding evaluation information system/Breeding EIS.',
			xiazai: 'Download <<Introduction to Agricultural Breeding evaluation information system/Breeding EIS>>'
		},
		tips: {
			logout: 'Are you sure to log off and exit the system?',
		}
	},
	breeding: {
		breeding: {
			title: {
				xinzeng: 'NEW Breeding population',
				xiugai: 'Modify Breeding population',
				import:'Import Breeding population',
				importresult:'Import results',
				zajiaoinfo: 'Breeding information',
				quzhonginfo: 'Seed taking information',
				bozhonginfo: 'Seeding information',
				dingzhiinfo: 'Colonization information'
			},
			label: {
				qinben1:'Parent 1',
				qinben2:'Parent 2',
				nianfen:'Year',
				zajiaodaihao:'Breeding code',
				muben:'Female parent',
				mubenlaiyuan:'Maternal source',
				fuben:'Male parent',
				fubenlaiyuan:'Paternal origin',
				operator: 'operator',
				zajiaoriqi:'Pollination date',
				caiguoriqi:'Fruit picking date',
				quzhongriqi:'Seed collection date',
				zhongzishuliang:'Number of seeds',
				bozhongriqi:'Sowing date',
				bozhongmethod: 'seeding method',
				cengjiriqi:'Stratification time',
				dingzhimethod:'Colonization method',
				dingzhisite:'Colonization site',
				dingzhicount:'Colonization quantity',
				dingzhiriqi:'Colonization date',
				leader:'Leader',
				contact:'Contact information',
				chakantiaoma:'View barcode'
			},
			button: {
				add: 'NEW Breeding population'
			},
			tips: {
				checkmuben:'Please enter the Female parent.',
				checkfuben:'Please enter the Male parent.',
				checkzajiaoriqi:'Please enter the pollination date.',
				checkzajiaodaihao:'Please enter the Breeding code.',
				exportconfirm:'Are you sure to export all Breeding population library data items?',
				deleteconfirm:'Are you sure to delete the data item of Breeding population library?',
			}
		},
		tiaomaku: {
			title: {
				viewerweima: 'View QR code',
				createtiaoma: 'Generate barcode',
			},
			label: {
				year: 'Year',
				zajiaozuhe: 'Breeding',
				type: 'type',
				code:'code',
				tiaoma: 'bar code',
				duanma: 'Short code',
				changma: 'Long code',
				createtime: 'create time',
				plantbase: 'Base code',
				plantarea: 'Nursery code',
				plantridge: 'Ridge number',
				plantrow: 'Row number',
				maxnum: 'Max number',
				hybrid: 'Breeding',
				fieldnumber: 'Combination code',
			},
			button: {
				createtiaoma: 'Generate barcode',
				createerweima: 'Generate QR code',
				exporterweima: 'Export QR code',
				exporttiaoma: 'Export barcode',
				download: 'Download QR code',
				generate: 'generate',
				creategaojiema: 'high connection code',
			},
			tips: {
				checkzajiaozuhe: 'Please select a Breeding population.',
				deleteconfirm: 'Are you sure to delete all the currently searched plant information?',
				exporttiaomaconfirm: 'Are you sure to export all the plant information currently searched?',
				exporterweimaconfirm: 'Are you sure to export all plant QR code files currently searched?',
				plantbase: 'Please enter the base code.',
				plantarea: 'Please enter nursery code.',
				plantridge: 'Please enter the correct number of ridges.',
				plantrow: 'Please enter the number of rows per ridge.',
				maxnum: 'Please enter the maximum number of plants.',
				fieldnumber: 'Please enter the combination code.',
				
			}
		},
		recodelist: {
			title: {
				importrecode: 'Filling data import',
				importresult: 'Import results',
				viewimg: 'View picture',
			},
			label: {
				nianfen: 'Year',
				zajiaozuhe: 'Breeding',
				tiaoma: 'Barcode',
				timesection: 'Time interval',
				timeto: 'to',
				starttime: 'Start time',
				endtime: 'End time',
				time: 'Time',
				creater: 'Filled by',
				evaluationcontent: 'Evaluation content',
				image: 'Picture',

			},
			button: {
				
			},
			tips: {
				exportconfirm: 'Are you sure to export the filled record list data of search?',
			}
		},
		collect: {
			title: {
				
			},
			label: {
				tiaoma: 'Barcode information',
				zajiaozuhe: 'Breeding population',
				creater: 'Collector',
				date: 'Collection date',
			},
			button: {
				cancel: 'Cancel collection',
			},
			tips: {
				cancel: 'Are you sure you want to cancel the collection?',
			}
		},
		statistical: {
			title: {
				
			},
			label: {
				year: 'Year',
				zajiaozuhe: 'Breeding',
				tiaoma: 'Barcode information',

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
				xuanyu: 'Breeding varieties',
				add: 'New germplasm resources',
				import: 'Germplasm resources import',
				importresult: 'Import result',
			},
			label: {
				name: 'Name',
				source: 'Source',
				year: 'Introduction year',	
				alias: 'Alias',
				type: 'Type',
				sourcearea: 'Source area',
				yinjinfangshi: 'Introduction mode',
				pumingcheng: 'Nursery name',
				baocunfangshi: 'Mode',
				count: 'Quantity',
				unit: '(plant)',
				baohu: 'Variety protection',
				baocunnum: 'Save number',
				characterization: 'Characteristic description',
				application: 'Application status',
				img: 'Picture',
				xuanyudanwei: 'Breeding unit',
				xuanyufangfa: 'Breeding method',
				yuchengyear: 'Breeding year',
				chakatiaoma: 'View barcode',
				yuanlianxiren: 'Original contact person',
				yinjinren: 'Introducer',
				username: 'Name',
				phone: 'Contact information',
			},
			button: {
				add: 'New',
			},
			tips: {
				checkbaocunnum: 'Please enter the number.',
				checkname: 'Please enter the name of germplasm resources.',
				checkyear: 'Please enter the year of Introduction.',
				exportconfirm: 'are you sure to export all germplasm resources data items?',
				deleteconfirm: 'are you sure to delete the germplasm resource data item?',
			}
		},
		tiaomaku: {
			title: {
				viewerweima: 'View QR code',
				createtiaoma: 'Generate barcode',
			},
			label: {
				year: 'Year',
				name: 'Name',
				tiaoma: 'bar code',
				duanma: 'Short code',
				changma: 'Long code',
				createtime: 'create time',
				plantbase: 'Base code',
				plantarea: 'Nursery code',
				plantridge: 'Ridge number',
				plantrow: 'Row number',
				maxnum: 'Max number',
				hybrid: 'Breeding',
				fieldnumber: 'Combination code',
			},
			button: {
				createtiaoma: 'Generate barcode',
				createerweima: 'Generate QR code',
				exporterweima: 'Export QR code',
				exporttiaoma: 'Export barcode',
				download: 'Download QR code',
				generate: 'generate',

			},
			tips: {
				checkzajiaozuhe: 'Please select a Breeding population.',
				deleteconfirm: 'Are you sure to delete all the currently searched plant information?',
				exporttiaomaconfirm: 'Are you sure to export all the plant information currently searched?',
				exporterweimaconfirm: 'Are you sure to export all plant QR code files currently searched?',
				plantbase: 'Please enter the base code.',
				plantarea: 'Please enter nursery code.',
				plantridge: 'Please enter the number of ridges.',
				plantrow: 'Please enter the number of rows per ridge.',
				maxnum: 'Please enter the maximum number of plants.',
				fieldnumber: 'Please enter the combination code.',
				
			}
		},
		recodelist: {
			title: {
				importrecode: 'Filling data import',
				importresult: 'Import results',
				viewimg: 'View picture',
			},
			label: {
				nianfen: 'Year',
				name: 'Name',
				tiaoma: 'Barcode',
				timesection: 'Time interval',
				timeto: 'to',
				starttime: 'Start time',
				endtime: 'End time',
				time: 'Time',
				creater: 'Filled by',
				evaluationcontent: 'Evaluation content',
				image: 'Picture',

			},
			button: {
				
			},
			tips: {
				exportconfirm: 'Are you sure to export the filled record list data of search?',
			}
		},
		collect: {
			title: {
				
			},
			label: {
				tiaoma: 'Barcode information',
				name: 'Name',
				creater: 'Collector',
				date: 'Collection date',
			},
			button: {
				cancel: 'Cancel collection',
			},
			tips: {
				cancel: 'Are you sure you want to cancel the collection?',
			}
		},
		statistical: {
			title: {
				
			},
			label: {
				year: 'Year',
				zajiaozuhe: 'Breeding',
				tiaoma: 'Barcode information',

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
				add: 'Add species',
				typelist: 'Classification list',
				typeadd: 'Add category',
				typeedit: 'Modify classification',
			},
			label: {
				speciesname: 'Species name',
				typename: 'Classification name',
				xingzhuang: 'Selection character',
				xingzhuangname: 'Properties name',
				xingzhuangtype: 'Added classification',

			},
			button: {
				add: 'Add species',
				addtype: 'Add category',
			},
			tips: {
				checktypename: 'Please enter classification name.',
				checkspeciesname: 'Please select a species.',
				checkadditive: 'Please select an attribute.',
				checkdelete: 'Are you sure to delete this attribute group?',
				show: 'Display succeeded.',
				hidden: 'Hiding succeeded.',
			}
		},
		additive: {
			title: {
				add: 'Add attribute',
				edit: 'Modify attribute',
			},
			label: {
				xingzhuangname: 'Properties name',
				type: 'Type',
				content: 'Content',
				createtime: 'Creation time',
				speciesname: 'Select species',
			},
			button: {
				add: 'Additive Properties',
				import: 'Import Properties',
			},
			tips: {
				checkoption: 'Please enter an option.',
			}
		},
		classification: {
			title: {
				list: 'Properties list',
			},
			label: {
				xingzhuangname: 'Properties name',
				type: 'Type',
				content: 'Content',
			},
			button: {
				
			},
			tips: {
				
			}
		}
	},
	feedback: {
		title: {
			feedback: 'Handling feedback',
			add: 'Add feedback',
		},
		label: {
			content: 'Feedback content',
			type: 'State',
			creater: 'Feedback person',
			channel: 'Feedback channel',
			reply: 'Reply',

		},
		button: {
			handle: 'handle',
		},
		tips: {
			export: 'Are you sure to export all feedback data items?',
		}
	},
	user: {
		title: 'Personal information',
		info: 'Basic information',
		name: 'User name',
		nickname: 'User nickname',
		phone: 'Mobile number',
		email: 'Email',
		sex: 'Gender',
		dept: 'Department',
		role: 'Role',
		date: 'Creation date',
		spaces: 'Species',
		password: {
			title: 'Modify password',
			oldpass: 'Old password',
			newpass: 'New password',
			twopass: 'Confirm password',
			tips: {
				oldpass: 'The old password cannot be empty',
				newpass: 'The new password cannot be empty',
				twopass: 'The confirmation password cannot be empty'
			}
		},
		avatar: {
			title: 'Modify avatar',
			upload: 'upload',
			tips: 'Click to upload the avatar'
		}
	}
  },
  common: {
	name: 'Breeding EIS',
	title: {
		warning:'warning',
	},
	button: {
	  ok:'OK',
	  cancle:'Cancel',
	  submit: 'submit',
	  add:'Add',
	  edit:'Modify',
	  view:'Check',
	  delete:'Delete',
	  search:'Query',
	  reset:'Reset',
	  import:'Import',
	  export:'Export',
	  upload:'Upload',
	  show:'Show',
	  hidden:'Hidden',
	  downloadtemplate:'Download template',
	},
	label: {
	  sequence:'Serial number',
	  operation:'Operation',
	  orderby:'Sort',
	  remarks:'Remarks',
	  selectupdate:'Update existing user data',
	  pending: 'Pending',
	  processed: 'Processed',
	  input: 'input box',
      text: 'textbox',
      radio: 'radio',
      checkbox: 'multi-choice',
      date: 'date'
	},
	tips: {
		checkint:'Please enter an integer.',
		checkphone:'Please enter the correct contact information.',
		success:'Operation succeeded.',
		fail:'operation failed.',
		uploadoperate:'Drag the file here, or ',
		importfileext:'Tip: only \"XLS\" or \"xlsx\" format files can be imported!',
	}
  },
  tagsView: {
    refresh: 'Refresh',
    close: 'Close',
    closeOthers: 'Close Others',
    closeAll: 'Close All'
  },
  settings: {
    title: 'Page style setting',
    theme: 'Theme Color',
    tagsView: 'Open Tags-View',
    fixedHeader: 'Fixed Header',
    sidebarLogo: 'Sidebar Logo'
  }
}