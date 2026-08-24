#pragma once
#include <string>
#include <vector>
#include "Node.h"
struct SearchPath { std::vector<int> nodeIds; float score; };
struct SearchResult { SearchPath path; float startScore,middleScore,endScore,transitionScore,libraryScore,historyScore,reverseScore,modeScore,finalScore; };
class GridEngine;
SearchResult forwardSearch(const GridEngine& engine, const std::string& input);
SearchResult reverseSearch(const GridEngine& engine, const std::string& target);
SearchResult randomWalk(const GridEngine& engine, int steps);