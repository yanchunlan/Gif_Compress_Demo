//
// Created by ycl on 2019/8/29.
//

#ifndef GIF_COMPRESS_DEMO_ANDROIDLOG_H
#define GIF_COMPRESS_DEMO_ANDROIDLOG_H


#define LOG_TAG "gif_native"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__);
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__);
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__);
#define LOGI(FORMAT, ...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,FORMAT,##__VA_ARGS__);
#define LOGD(FORMAT, ...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,FORMAT,##__VA_ARGS__);


#endif //GIF_COMPRESS_DEMO_ANDROIDLOG_H
