#include <string.h>
#include <stdio.h>
#include <jni.h>

extern "C" {
JNIEXPORT jstring Java_com_example_myndk_MainActivity_helloworld(
		JNIEnv* env, jobject thiz);
}

JNIEXPORT jstring Java_com_example_myndk_MainActivity_helloworld(
		JNIEnv* env, jobject thiz) {
	return env->NewStringUTF("Hello, Android NDK");
}
