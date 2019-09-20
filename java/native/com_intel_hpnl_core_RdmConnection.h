/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_intel_hpnl_core_RdmConnection */

#ifndef _Included_com_intel_hpnl_core_RdmConnection
#define _Included_com_intel_hpnl_core_RdmConnection
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    init
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_hpnl_core_RdmConnection_init
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    get_local_name
 * Signature: (Ljava/nio/ByteBuffer;J)V
 */
JNIEXPORT void JNICALL Java_com_intel_hpnl_core_RdmConnection_get_1local_1name
  (JNIEnv *, jobject, jobject, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    get_local_name_length
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_get_1local_1name_1length
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    get_connection_id
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_hpnl_core_RdmConnection_get_1connection_1id
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    send
 * Signature: (IIJ)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_send
  (JNIEnv *, jobject, jint, jint, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    sendRequest
 * Signature: (IIJ)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_sendRequest
  (JNIEnv *, jobject, jint, jint, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    sendTo
 * Signature: (IIJJ)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_sendTo
  (JNIEnv *, jobject, jint, jint, jlong, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    sendBuf
 * Signature: (Ljava/nio/ByteBuffer;IIIJ)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_sendBuf
  (JNIEnv *, jobject, jobject, jint, jint, jint, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    sendBufWithRequest
 * Signature: (Ljava/nio/ByteBuffer;IIIJ)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_sendBufWithRequest
  (JNIEnv *, jobject, jobject, jint, jint, jint, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    sendBufTo
 * Signature: (Ljava/nio/ByteBuffer;IIIJJ)I
 */
JNIEXPORT jint JNICALL Java_com_intel_hpnl_core_RdmConnection_sendBufTo
  (JNIEnv *, jobject, jobject, jint, jint, jint, jlong, jlong);


/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    resolve_peer_name
 * Signature: (Ljava/nio/ByteBuffer;J)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_hpnl_core_RdmConnection_resolve_1peer_1name
  (JNIEnv *, jobject, jobject, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    deleteGlobalRef
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_hpnl_core_RdmConnection_deleteGlobalRef
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_intel_hpnl_core_RdmConnection
 * Method:    releaseRecvBuffer
 * Signature: (IJ)V
 */
JNIEXPORT void JNICALL Java_com_intel_hpnl_core_RdmConnection_releaseRecvBuffer
  (JNIEnv *, jobject, jint, jlong);

JNIEXPORT void JNICALL Java_com_intel_hpnl_core_RdmConnection_free
  (JNIEnv *env, jobject thisObj, jlong conPtr);

#ifdef __cplusplus
}
#endif
#endif
