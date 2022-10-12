//
//  MyLanguageManager.swift
//  LiZaJiao
//
//  Created by 顾杰 on 2022/9/25.
//  Copyright © 2022 GuJie. All rights reserved.
//

import Foundation

class MyLanguageManager: NSObject {

    fileprivate static let kChooseLanguageKey = "ChooseLanguage"
    /// 单例
    static let shared = MyLanguageManager()

    var language: Language
    private override init() {
        // 第一次初始语言, 看手机是什么语言
        language = MyLanguageManager.localeIsChinese() ? .Chinese : .English
        super.init()
    }
    enum Language: String {
        /// 请注意, 这个命名不是随意的, 是根据你本地的语言包,可以show in finder 看到. en.lproj / zh-Hans.lproj
        case Chinese = "zh-Hans"
        case English = "en"
        var code: String {
            return rawValue
        }
    }
    /// 判断手机语言是不是中文
    static func localeIsChinese() -> Bool {
        if let lang = Locale.preferredLanguages.first {
            return lang.hasPrefix("zh") ? true : false ;
        } else {
            return false
        }

    }


    /// 保存所选的语言
    static func saveLanguage(chooseLanguage:Language) {
        UserDefaults.standard.set(chooseLanguage.rawValue, forKey: MyLanguageManager.kChooseLanguageKey)
    }
    
    /// 获取上次保存的语言,如果从未保存过，获取回来的是Chinese
    static func currentLanguage() -> Language? {
       let langString = UserDefaults.standard.string(forKey: kChooseLanguageKey)
        guard let desLangString = langString else {
            return Language.Chinese
        }
      return Language(rawValue: desLangString)
    }

}
private var bundleByLanguageCode: [String: Foundation.Bundle] = [:]
extension MyLanguageManager.Language {
    var bundle: Foundation.Bundle? {
        /// 存起来, 避免一直创建
        if let bundle = bundleByLanguageCode[code] {
            return bundle
        } else {
            let mainBundle = Foundation.Bundle.main
            if let path = mainBundle.path(forResource: code, ofType: "lproj"),
                let bundle = Foundation.Bundle(path: path) {
                bundleByLanguageCode[code] = bundle
                return bundle
            } else {
                return nil
            }
        }
    }
}
/// 首先, 我们会在启动时设置成我们自己的Bundle,这样就可以做到,当使用时会调用这个方法.
class MyBundle: Foundation.Bundle {
    override func localizedString(forKey key: String, value: String?, table tableName: String?) -> String {
        if let bundle = MyLanguageManager.shared.language.bundle {
            return bundle.localizedString(forKey: key, value: value, table: tableName)
        } else {
            return super.localizedString(forKey: key, value: value, table: tableName)
        }
    }
}
