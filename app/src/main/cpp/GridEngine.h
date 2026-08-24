#pragma once
#include <vector>
#include <memory>
#include <string>
#include "Node.h"
#include "Search.h"
#include "Library.h"
#include "PersonalLibrary.h"
#include "HistoryStore.h"
#include "Benchmarks.h"
#include "VulkanCompute.h"

class GridEngine {
public:
    GridEngine(int dimX, int dimY, int dimZ);
    ~GridEngine();
    int getNodeCount() const;
    Node getNode(int id) const;
    std::vector<Node> getAllNodes() const;
    void setNodeState(int id, uint8_t state);
    void updateGates();
    std::vector<uint8_t> encodeChar(char c) const;
    char decodeChar(const std::vector<uint8_t>& bits) const;
    SearchResult forwardSearch(const std::string& input);
    SearchResult reverseSearch(const std::string& target);
    SearchResult randomWalk(int steps);
    float computeScore(const SearchPath& path);
    void loadBuiltinLibraries();
    void trainSequence(const std::vector<std::string>& sequence);
    PersonalLibrary& getPersonalLibrary();
    void recordFrame(const std::vector<uint8_t>& gridData);
    std::vector<std::vector<uint8_t>> retrieveHistory(int count);
    bool hasGPU() const;
    void setUseGPU(bool enable);
    std::string runBenchmarks();
private:
    struct Impl;
    std::unique_ptr<Impl> pImpl;
};