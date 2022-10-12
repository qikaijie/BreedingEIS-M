package net.cnbec.catering.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Describe: //存储打印机的一些设置
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/31
 */

@Entity
public class PrinterEntity {
    @Id
    public long id;
    public String ip;
    @Generated(hash = 1044866672)
    public PrinterEntity(long id, String ip) {
        this.id = id;
        this.ip = ip;
    }
    @Generated(hash = 724277662)
    public PrinterEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
