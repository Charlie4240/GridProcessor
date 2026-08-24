#include "data_conversion.h"
#include <vector>

jobject convertNodeToJava(JNIEnv* env, const Node& node) {
    jclass cls = env->FindClass("com/example/gridprocessor/data/NodeData");
    jmethodID ctor = env->GetMethodID(cls, "<init>", "(IIIIBFFZ[IFIIIBBF)V");
    jintArray nArr = env->NewIntArray(6);
    jint* nElems = env->GetIntArrayElements(nArr, nullptr);
    for(int i=0; i<6; ++i) nElems[i] = node.neighbors[i];
    env->ReleaseIntArrayElements(nArr, nElems, 0);
    jobject obj = env->NewObject(cls, ctor,
        node.id, node.x, node.y, node.z,
        (jbyte)node.binaryState, node.activation, node.threshold,
        (jboolean)node.gateOpen, nArr, node.weight,
        node.patternId, node.libraryId, node.visitCount,
        (jbyte)node.previousState, (jbyte)node.nextState, node.candidateScore);
    env->DeleteLocalRef(nArr);
    return obj;
}

jobject convertSearchResultToJava(JNIEnv* env, const SearchResult& res) {
    jclass pathCls = env->FindClass("com/example/gridprocessor/data/PathData");
    jmethodID pathCtor = env->GetMethodID(pathCls, "<init>", "(Ljava/util/List;F)V");
    jclass listCls = env->FindClass("java/util/ArrayList");
    jmethodID listCtor = env->GetMethodID(listCls, "<init>", "()V");
    jobject list = env->NewObject(listCls, listCtor);
    jmethodID add = env->GetMethodID(listCls, "add", "(Ljava/lang/Object;)Z");
    for(int id : res.path.nodeIds) {
        jobject idObj = env->NewObject(env->FindClass("java/lang/Integer"), env->GetMethodID(env->FindClass("java/lang/Integer"), "<init>", "(I)V"), id);
        env->CallBooleanMethod(list, add, idObj);
        env->DeleteLocalRef(idObj);
    }
    jobject pathObj = env->NewObject(pathCls, pathCtor, list, res.path.score);
    jclass resultCls = env->FindClass("com/example/gridprocessor/data/SearchResult");
    jmethodID resultCtor = env->GetMethodID(resultCls, "<init>", "(Lcom/example/gridprocessor/data/PathData;FFFFFFFFF)V");
    jobject result = env->NewObject(resultCls, resultCtor,
        pathObj,
        res.startScore, res.middleScore, res.endScore,
        res.transitionScore, res.libraryScore, res.historyScore,
        res.reverseScore, res.modeScore, res.finalScore);
    env->DeleteLocalRef(list);
    env->DeleteLocalRef(pathObj);
    return result;
}

jobjectArray convertNodesToJava(JNIEnv* env, const std::vector<Node>& nodes) {
    jclass cls = env->FindClass("com/example/gridprocessor/data/NodeData");
    jobjectArray arr = env->NewObjectArray(nodes.size(), cls, nullptr);
    for(size_t i=0; i<nodes.size(); ++i) {
        jobject obj = convertNodeToJava(env, nodes[i]);
        env->SetObjectArrayElement(arr, i, obj);
        env->DeleteLocalRef(obj);
    }
    return arr;
}