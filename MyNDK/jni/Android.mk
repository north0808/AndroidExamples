LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MyNDK
LOCAL_SRC_FILES := MyNDK.cpp

include $(BUILD_SHARED_LIBRARY)
