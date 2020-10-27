#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <string.h>
#include <jni.h>

#define LOG_TAG "log_dkhai"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)


char* Jstring2CStr(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String"); //String
    jstring strencode = (*env)->NewStringUTF(env, "GB2312"); // Lấy một chuỗi java "GB2312"
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
                                        "(Ljava/lang/String;)[B"); //[ String.getBytes("gb2312");
    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
                                                           strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr); // độ dài của mảng byte
    jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc(alen + 1);         //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);  //
    return rtn;
}

int login(char* username, char* password) {
    // 1, so sánh từng ký tự với các ký tự của mật khẩu thực
    char* realuser = "abc";
    char* realpwd = "123";
    int i = 0;
    for (; *(realuser + i) != '\0'; i++) {
        if (*(username + i) != *(realuser + i)) { // Nếu một ký tự không bằng nhau, trả về 404;
            return 404;
        }
    }
    printf("----------------\n");
    int j = 0;
    for (; *(realpwd + j) != '\0'; j++) {
        if (*(password + j) != *(realpwd + j)) {
            return 404;
        }
    }
    return 200;
}

jint Java_com_dkhai_myapplication_MainActivity_callLogin( JNIEnv* env, jobject obj, jstring juser, jstring jpwd)
{
    // 1, so sánh từng ký tự với các ký tự của mật khẩu thực
    char* cuser = Jstring2CStr(env, juser);
    char* cpwd = Jstring2CStr(env, jpwd);
    LOGI("JNI_log : cuser=%s", cuser);
    LOGI("JNI_log : cpwd=%s", cpwd);

    // 2, gọi đăng nhập để đăng nhập
    int resultcode = login(cuser, cpwd);
    LOGI("JNI_log : resultcode = %d", resultcode);

    // 3, quay lại lệnh gọi trên java
    return resultcode;
}