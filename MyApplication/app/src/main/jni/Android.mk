LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := native_dkhai
LOCAL_SRC_FILES := native-lib.c
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)