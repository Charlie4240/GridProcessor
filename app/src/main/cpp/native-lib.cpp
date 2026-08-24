#include <jni.h>
#include <string>
#include <vector>
#include "GridEngine.h"
#include "data_conversion.h"

extern "C" {

JNIEXPORT jlong JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeCreate(JNIEnv*, jobject) {
    return reinterpret_cast<jlong>(new GridEngine(12,12,12));
}
JNIEXPORT void JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeDestroy(JNIEnv*, jobject, jlong ptr) {
    delete reinterpret_cast<GridEngine*>(ptr);
}
JNIEXPORT jint JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeGetNodeCount(JNIEnv*, jobject, jlong ptr) {
    return reinterpret_cast<GridEngine*>(ptr)->getNodeCount();
}
JNIEXPORT jobject JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeGetNode(JNIEnv* env, jobject, jlong ptr, jint id) {
    GridEngine* engine = reinterpret_cast<GridEngine*>(ptr);
    Node node = engine->getNode(id);
    return convertNodeToJava(env, node);
}
JNIEXPORT jobjectArray JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeGetAllNodes(JNIEnv* env, jobject, jlong ptr) {
    GridEngine* engine = reinterpret_cast<GridEngine*>(ptr);
    std::vector<Node> nodes = engine->getAllNodes();
    return convertNodesToJava(env, nodes);
}
JNIEXPORT void JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeUpdateGates(JNIEnv*, jobject, jlong ptr) {
    reinterpret_cast<GridEngine*>(ptr)->updateGates();
}
JNIEXPORT jobject JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeForwardSearch(JNIEnv* env, jobject, jlong ptr, jstring input) {
    const char* utf = env->GetStringUTFChars(input, nullptr);
    std::string str(utf);
    env->ReleaseStringUTFChars(input, utf);
    SearchResult res = reinterpret_cast<GridEngine*>(ptr)->forwardSearch(str);
    return convertSearchResultToJava(env, res);
}
JNIEXPORT jobject JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeReverseSearch(JNIEnv* env, jobject, jlong ptr, jstring target) {
    const char* utf = env->GetStringUTFChars(target, nullptr);
    std::string str(utf);
    env->ReleaseStringUTFChars(target, utf);
    SearchResult res = reinterpret_cast<GridEngine*>(ptr)->reverseSearch(str);
    return convertSearchResultToJava(env, res);
}
JNIEXPORT jobject JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeRandomWalk(JNIEnv* env, jobject, jlong ptr, jint steps) {
    SearchResult res = reinterpret_cast<GridEngine*>(ptr)->randomWalk(steps);
    return convertSearchResultToJava(env, res);
}
JNIEXPORT void JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeTrainSequence(JNIEnv* env, jobject, jlong ptr, jobjectArray sequence) {
    int len = env->GetArrayLength(sequence);
    std::vector<std::string> vec;
    for(int i=0; i<len; ++i) {
        jstring s = (jstring)env->GetObjectArrayElement(sequence, i);
        const char* utf = env->GetStringUTFChars(s, nullptr);
        vec.push_back(std::string(utf));
        env->ReleaseStringUTFChars(s, utf);
    }
    reinterpret_cast<GridEngine*>(ptr)->trainSequence(vec);
}
JNIEXPORT void JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeLoadBuiltinLibraries(JNIEnv*, jobject, jlong ptr) {
    reinterpret_cast<GridEngine*>(ptr)->loadBuiltinLibraries();
}
JNIEXPORT jstring JNICALL Java_com_example_gridprocessor_engine_GridEngine_nativeRunBenchmarks(JNIEnv* env, jobject, jlong ptr) {
    std::string result = reinterpret_cast<GridEngine*>(ptr)->runBenchmarks();
    return env->NewStringUTF(result.c_str());
}

}