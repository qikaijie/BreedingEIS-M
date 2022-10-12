import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Cookies from 'js-cookie'
import ElementUILocale from 'element-ui/lib/locale'
import elementEnLocale from 'element-ui/lib/locale/lang/en' // element-ui lang
import elementZhLocale from 'element-ui/lib/locale/lang/zh-CN'// element-ui lang
import enLocale from './en'
import zhLocale from './zh'

Vue.use(VueI18n)

const UIlocales = {
  zh: elementZhLocale,
  en: elementEnLocale
}

// 通过判断lang语言标志符来return先对应的语言
const setUIlocales = lang =>{
  switch (lang) {
    case 'zh': return UIlocales.zh
    case 'en': return UIlocales.en
  }
}

const messages = {
  en: {
    ...enLocale,
    ...elementEnLocale
  },
  zh: {
    ...zhLocale,
    ...elementZhLocale
  }
}

const i18n = new VueI18n({
  // 设置语言 选项 en | zh
  locale: Cookies.get('language') || 'zh',
  // 设置文本内容
  messages
})

ElementUILocale.use(setUIlocales(Cookies.get('language') || 'zh')) // element ui 切换语言
export default i18n