#pragma once
#include <jni.h>
#include "Node.h"
#include "Search.h"
jobject convertNodeToJava(JNIEnv* env, const Node& node);
jobject convertSearchResultToJava(JNIEnv* env, const SearchResult& res);
jobjectArray convertNodesToJava(JNIEnv* env, const std::vector<Node>& nodes);