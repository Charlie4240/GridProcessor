#include "GridEngine.h"
#include <algorithm>
#include <numeric>
#include <cmath>
#include <unordered_map>
#include <chrono>
#include <queue>

struct GridEngine::Impl {
    int dimX, dimY, dimZ;
    std::vector<Node> nodes;
    std::unique_ptr<PersonalLibrary> personalLib;
    std::unique_ptr<HistoryStore> history;
    std::unique_ptr<VulkanCompute> gpu;
    bool useGPU = false;

    Impl(int x, int y, int z) : dimX(x), dimY(y), dimZ(z),
        personalLib(std::make_unique<PersonalLibrary>()),
        history(std::make_unique<HistoryStore>()),
        gpu(std::make_unique<VulkanCompute>()) {
        int total = x*y*z;
        nodes.resize(total);
        int id=0;
        for(int iz=0; iz<z; ++iz)
            for(int iy=0; iy<y; ++iy)
                for(int ix=0; ix<x; ++ix) {
                    Node& n = nodes[id];
                    n.id = id; n.x=ix; n.y=iy; n.z=iz;
                    n.binaryState = 0;
                    n.activation = 0.0f;
                    n.threshold = 0.5f;
                    n.gateOpen = false;
                    n.weight = 1.0f;
                    n.patternId = -1;
                    n.libraryId = -1;
                    n.visitCount = 0;
                    n.previousState = 0;
                    n.nextState = 0;
                    n.candidateScore = 0.0f;
                    n.neighbors = {-1,-1,-1,-1,-1,-1};
                    if(ix>0) n.neighbors[0] = id-1;
                    if(ix<x-1) n.neighbors[1] = id+1;
                    if(iy>0) n.neighbors[2] = id-x;
                    if(iy<y-1) n.neighbors[3] = id+x;
                    if(iz>0) n.neighbors[4] = id-x*y;
                    if(iz<z-1) n.neighbors[5] = id+x*y;
                    id++;
                }
    }

    void updateGates() {
        for(auto& node : nodes) {
            float sum=0;
            for(int nid : node.neighbors) if(nid>=0) {
                const auto& nb = nodes[nid];
                sum += nb.activation * nb.weight * (nb.binaryState ? 1.0f : 0.0f);
            }
            node.gateOpen = (sum >= node.threshold);
        }
    }

    std::vector<uint8_t> encodeChar(char c) const {
        std::vector<uint8_t> bits(8);
        for(int i=0; i<8; ++i) bits[i] = (c >> (7-i)) & 1;
        return bits;
    }
    char decodeChar(const std::vector<uint8_t>& bits) const {
        char c=0;
        for(int i=0; i<8; ++i) c = (c<<1) | bits[i];
        return c;
    }

    SearchResult forwardSearch(const std::string& input) {
        SearchResult res;
        std::vector<int> path;
        int current = 0;
        path.push_back(current);
        for(size_t i=0; i<input.size() && i<5; ++i) {
            int best = -1;
            float bestScore = -1;
            for(int nid : nodes[current].neighbors) {
                if(nid>=0) {
                    float score = nodes[nid].activation * nodes[nid].weight;
                    if(score > bestScore) {
                        bestScore = score;
                        best = nid;
                    }
                }
            }
            if(best == -1) break;
            path.push_back(best);
            current = best;
        }
        res.path.nodeIds = path;
        res.path.score = 0.8f;
        res.finalScore = 0.75f;
        res.startScore = 0.9f; res.middleScore=0.8f; res.endScore=0.7f;
        res.transitionScore=0.6f; res.libraryScore=0.5f; res.historyScore=0.4f;
        res.reverseScore=0.3f; res.modeScore=0.2f;
        return res;
    }

    SearchResult reverseSearch(const std::string& target) {
        SearchResult res = forwardSearch(target);
        std::reverse(res.path.nodeIds.begin(), res.path.nodeIds.end());
        return res;
    }

    SearchResult randomWalk(int steps) {
        SearchResult res;
        std::vector<int> path;
        int current = 0;
        path.push_back(current);
        for(int i=0; i<steps && i<10; ++i) {
            std::vector<int> valid;
            for(int nid : nodes[current].neighbors) if(nid>=0) valid.push_back(nid);
            if(valid.empty()) break;
            int idx = rand() % valid.size();
            current = valid[idx];
            path.push_back(current);
        }
        res.path.nodeIds = path;
        res.path.score = 0.5f;
        res.finalScore = 0.5f;
        return res;
    }

    void loadBuiltinLibraries() {
        personalLib->train({"the","cat","is"});
        personalLib->train({"python","edit","run"});
    }
    void trainSequence(const std::vector<std::string>& seq) {
        personalLib->train(seq);
    }
    PersonalLibrary& getPersonalLibrary() { return *personalLib; }

    void recordFrame(const std::vector<uint8_t>& data) {
        history->recordFrame(data);
    }
    std::vector<std::vector<uint8_t>> retrieveHistory(int count) {
        return history->retrieveHistory(count);
    }

    bool hasGPU() const { return gpu->isAvailable(); }
    void setUseGPU(bool enable) { useGPU = enable; }

    std::string runBenchmarks() {
        auto start = std::chrono::steady_clock::now();
        for(int i=0; i<1000; ++i) {
            updateGates();
        }
        auto end = std::chrono::steady_clock::now();
        long ms = std::chrono::duration_cast<std::chrono::milliseconds>(end-start).count();
        return "Benchmark: 1000 gate updates in " + std::to_string(ms) + " ms";
    }
};

GridEngine::GridEngine(int x,int y,int z) : pImpl(std::make_unique<Impl>(x,y,z)) {}
GridEngine::~GridEngine() = default;
int GridEngine::getNodeCount() const { return pImpl->nodes.size(); }
Node GridEngine::getNode(int id) const { return (id>=0 && id<(int)pImpl->nodes.size()) ? pImpl->nodes[id] : Node{}; }
std::vector<Node> GridEngine::getAllNodes() const { return pImpl->nodes; }
void GridEngine::setNodeState(int id, uint8_t state) { if(id>=0 && id<(int)pImpl->nodes.size()) pImpl->nodes[id].binaryState = state; }
void GridEngine::updateGates() { pImpl->updateGates(); }
std::vector<uint8_t> GridEngine::encodeChar(char c) const { return pImpl->encodeChar(c); }
char GridEngine::decodeChar(const std::vector<uint8_t>& bits) const { return pImpl->decodeChar(bits); }
SearchResult GridEngine::forwardSearch(const std::string& input) { return pImpl->forwardSearch(input); }
SearchResult GridEngine::reverseSearch(const std::string& target) { return pImpl->reverseSearch(target); }
SearchResult GridEngine::randomWalk(int steps) { return pImpl->randomWalk(steps); }
float GridEngine::computeScore(const SearchPath& path) { return path.score; }
void GridEngine::loadBuiltinLibraries() { pImpl->loadBuiltinLibraries(); }
void GridEngine::trainSequence(const std::vector<std::string>& seq) { pImpl->trainSequence(seq); }
PersonalLibrary& GridEngine::getPersonalLibrary() { return pImpl->getPersonalLibrary(); }
void GridEngine::recordFrame(const std::vector<uint8_t>& data) { pImpl->recordFrame(data); }
std::vector<std::vector<uint8_t>> GridEngine::retrieveHistory(int count) { return pImpl->retrieveHistory(count); }
bool GridEngine::hasGPU() const { return pImpl->hasGPU(); }
void GridEngine::setUseGPU(bool enable) { pImpl->setUseGPU(enable); }
std::string GridEngine::runBenchmarks() { return pImpl->runBenchmarks(); }