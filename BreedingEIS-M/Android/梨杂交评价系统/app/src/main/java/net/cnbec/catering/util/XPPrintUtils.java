package net.cnbec.catering.util;

import android.content.Context;
import android.graphics.Bitmap;

import net.cnbec.catering.R;
import net.cnbec.catering.ui.main.activity.MainActivity;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.TaskCallback;
import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterTSC;

import java.util.ArrayList;
import java.util.List;

/**
 * XP-P210手持打印机
 */
public class XPPrintUtils {

    /**
     * 打印条码+文本
     * @param context
     * @param barCode
     * @param content
     */
    public static void printContent(Context context,String barCode,String content){
        if (MainActivity.BLUETOOTH_CONNECT == 2){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ToastUtil.show(context.getString(R.string.send_success));
                }

                @Override
                public void OnFailed() {
                    ToastUtil.show(context.getString(R.string.send_failed));
                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,20));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //线条
                    list.add(DataForSendToPrinterTSC.bar(10,10,200,3));
                    //条码
                    list.add(DataForSendToPrinterTSC.barCode(10,45,"128",100,1,0,2,2,barCode));
                    //文本,简体中文是TSS24.BF2,可参考编程手册中字体的代号
                    list.add(DataForSendToPrinterTSC.text(220,10,"TSS24.BF2",0,1,1,content));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            ToastUtil.show(context.getString(R.string.connect_first));
        }
    }

    /**
     * 打印文本
     * @param context
     * @param content
     */
    public static void printText(Context context,String content){

        if (MainActivity.BLUETOOTH_CONNECT == 2){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ToastUtil.show(context.getString(R.string.send_success));
                }

                @Override
                public void OnFailed() {
                    ToastUtil.show(context.getString(R.string.send_failed));

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,20));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //线条
//                    list.add(DataForSendToPrinterTSC.bar(10,10,200,3));
                    //条码
//                    list.add(DataForSendToPrinterTSC.barCode(10,15,"128",100,1,0,2,2,"abcdef12345"));
                    //文本
                    list.add(DataForSendToPrinterTSC.text(10,30,"TSS24.BF2",0,1,1,content));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            ToastUtil.show(context.getString(R.string.connect_first));
        }

    }


    /**
     * 打印条形码
     * @param context
     */
    public static void printBarcode(Context context,String content){
        if (MainActivity.BLUETOOTH_CONNECT == 2){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ToastUtil.show(context.getString(R.string.send_success));
                }

                @Override
                public void OnFailed() {
                    ToastUtil.show(context.getString(R.string.send_failed));

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,20));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //线条
//                    list.add(DataForSendToPrinterTSC.bar(10,10,200,3));
                    //条码
                    list.add(DataForSendToPrinterTSC.barCode(10,15,"128",100,1,0,2,2,content));
                    //文本
//                    list.add(DataForSendToPrinterTSC.text(10,30,"1",0,1,1,"abcasdjknf"));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            ToastUtil.show(context.getString(R.string.connect_first));
        }
    }

    /**
     * 打印二维码
     * @param context
     */
    public static void printQR(Context context,String content){
        if (MainActivity.BLUETOOTH_CONNECT == 2){

            MainActivity.myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ToastUtil.show(context.getString(R.string.send_success));
                }

                @Override
                public void OnFailed() {
                    ToastUtil.show(context.getString(R.string.send_failed));

                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    //设置标签纸大小
                    list.add(DataForSendToPrinterTSC.sizeBymm(50,20));
                    //设置间隙
                    list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                    //清除缓存
                    list.add(DataForSendToPrinterTSC.cls());
                    //设置方向
                    list.add(DataForSendToPrinterTSC.direction(0));
                    //具体参数值请参看编程手册
                    list.add(DataForSendToPrinterTSC.qrCode(10,20,"M",3,"A",0,"M1","S3",content));
                    //打印
                    list.add(DataForSendToPrinterTSC.print(1));

                    return list;
                }
            });

        }else {
            ToastUtil.show(context.getString(R.string.connect_first));
        }
    }

    /**
     * 光栅位图
     * @param context
     * @param bitmap
     */
    public static void printbitmap(Context context,Bitmap bitmap){
        Bitmap finalBitmap = bitmap;
        MainActivity.myBinder.WriteSendData(new TaskCallback() {
            @Override
            public void OnSucceed() {
                ToastUtil.show(context.getString(R.string.send_success));

            }

            @Override
            public void OnFailed() {
                ToastUtil.show(context.getString(R.string.send_failed));

            }
        }, new ProcessData() {
            @Override
            public List<byte[]> processDataBeforeSend() {

                List<byte[]> list = new ArrayList<>();
                //设置标签纸大小
                list.add(DataForSendToPrinterTSC.sizeBymm(50,30));
                //设置间隙
                list.add(DataForSendToPrinterTSC.gapBymm(2,0));
                //清除缓存
                list.add(DataForSendToPrinterTSC.cls());
                list.add(DataForSendToPrinterTSC.bitmap(10,10,0, finalBitmap, BitmapToByteData.BmpType.Threshold));
                list.add(DataForSendToPrinterTSC.print(1));

                return list;
            }
        });
    }


}
